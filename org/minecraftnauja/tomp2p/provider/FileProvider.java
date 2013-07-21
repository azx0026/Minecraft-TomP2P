package org.minecraftnauja.tomp2p.provider;

import java.io.File;

import net.tomp2p.futures.BaseFutureListener;
import net.tomp2p.futures.Cancellable;
import net.tomp2p.futures.FutureDHT;

import org.minecraftnauja.p2p.exception.AlreadyRunningException;
import org.minecraftnauja.p2p.provider.event.ICallback;
import org.minecraftnauja.p2p.provider.file.FileProviderBase;
import org.minecraftnauja.p2p.provider.file.task.DownloadBase;
import org.minecraftnauja.p2p.provider.file.task.IDownload;
import org.minecraftnauja.p2p.provider.file.task.IUpload;
import org.minecraftnauja.p2p.provider.file.task.UploadBase;
import org.minecraftnauja.tomp2p.peer.IPeer;

import com.google.common.io.Files;

/**
 * Implementation of the file provider.
 */
public final class FileProvider extends FileProviderBase {

	/**
	 * Peer instance.
	 */
	private final IPeer peer;

	/**
	 * Initializing constructor.
	 * 
	 * @param peer
	 *            peer instance.
	 */
	public FileProvider(IPeer peer) {
		super();
		this.peer = peer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IUpload upload(final String channel, final File file,
			final String name, final ICallback<IUpload> callback) {
		try {
			FileUpload task = new FileUpload(channel, file, name);
			task.start(callback);
			return task;
		} catch (AlreadyRunningException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IDownload download(final String channel, final String name,
			final File file, final ICallback<IDownload> callback) {
		try {
			FileDownload task = new FileDownload(channel, name, file);
			task.start(callback);
			return task;
		} catch (AlreadyRunningException e) {
			return null;
		}
	}

	/**
	 * Task for file uploading.
	 */
	private final class FileUpload extends UploadBase {

		/**
		 * If the task is running.
		 */
		private FutureDHT running;

		/**
		 * Initializing constructor.
		 * 
		 * @param channel
		 *            the channel.
		 * @param file
		 *            the file.
		 * @param name
		 *            the name.
		 */
		public FileUpload(String channel, File file, String name) {
			super(FileProvider.this, channel, name, file);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized void start(final ICallback<IUpload> callback)
				throws AlreadyRunningException {
			if (running != null) {
				throw new AlreadyRunningException(this);
			}
			try {
				error = null;
				if (callback != null) {
					callback.onStarted(this);
				}
				fireUpload(FileUpload.this);
				// Puts the file.
				running = peer.put(name, Files.toByteArray(file));
				running.addCancellation(new Cancellable() {
					public void cancel() {
						// Cancelled the file upload.
						running = null;
						if (callback != null) {
							callback.onCancelled(FileUpload.this);
						}
						fireUploadCancelled(FileUpload.this);
					}
				});
				running.addListener(new BaseFutureListener<FutureDHT>() {
					public void operationComplete(FutureDHT future)
							throws Exception {
						// File uploaded.
						running = null;
						if (callback != null) {
							callback.onCompleted(FileUpload.this);
						}
						fireUploaded(FileUpload.this);
					}

					public void exceptionCaught(Throwable t) throws Exception {
						// Could not upload the file.
						running = null;
						error = t;
						if (callback != null) {
							callback.onException(FileUpload.this);
						}
						fireUploadException(FileUpload.this);
					}
				});
			} catch (Exception e) {
				// Could not read the file or put it.
				running = null;
				error = e;
				if (callback != null) {
					callback.onException(this);
				}
				fireUploadException(this);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized boolean isRunning() {
			return running != null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized void cancel() {
			if (running != null) {
				running.shutdown();
			}
		}

	}

	/**
	 * Task for file downloading.
	 */
	private final class FileDownload extends DownloadBase {

		/**
		 * If the task is running.
		 */
		private FutureDHT running;

		/**
		 * Initializing constructor.
		 * 
		 * @param channel
		 *            the channel.
		 * @param name
		 *            the name.
		 * @param file
		 *            the file.
		 */
		public FileDownload(String channel, String name, File file) {
			super(FileProvider.this, channel, name, file);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized void start(final ICallback<IDownload> callback)
				throws AlreadyRunningException {
			if (running != null) {
				throw new AlreadyRunningException(this);
			}
			if (callback != null) {
				callback.onStarted(this);
			}
			fireDownload(this);
			// Downloads the file.
			running = peer.get(name);
			running.addCancellation(new Cancellable() {
				public void cancel() {
					// Cancelled the file upload.
					running = null;
					if (callback != null) {
						callback.onCancelled(FileDownload.this);
					}
					fireDownloadCancelled(FileDownload.this);
				}
			});
			running.addListener(new BaseFutureListener<FutureDHT>() {
				public void operationComplete(FutureDHT future)
						throws Exception {
					// File downloaded.
					try {
						// Makes the directory.
						File f = file.getParentFile();
						if (f != null && f.isDirectory() && !f.exists()) {
							f.mkdirs();
						}
						// Writes it to disk.
						Files.write((byte[]) future.getData().getObject(), file);
						// Notifies listeners.
						running = null;
						if (callback != null) {
							callback.onCompleted(FileDownload.this);
						}
						fireDownloaded(FileDownload.this);
					} catch (Exception e) {
						// Could not write.
						running = null;
						if (callback != null) {
							callback.onException(FileDownload.this);
						}
						fireDownloadException(FileDownload.this);
					}
				}

				public void exceptionCaught(Throwable t) throws Exception {
					// File not downloaded.
					running = null;
					if (callback != null) {
						callback.onException(FileDownload.this);
					}
					fireDownloadException(FileDownload.this);
				}
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized boolean isRunning() {
			return running != null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized void cancel() {
			if (running != null) {
				running.shutdown();
			}
		}

	}

}
