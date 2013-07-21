package org.minecraftnauja.tomp2p.provider;

import net.tomp2p.futures.BaseFutureListener;
import net.tomp2p.futures.Cancellable;
import net.tomp2p.futures.FutureResponse;
import net.tomp2p.peers.PeerAddress;

import org.minecraftnauja.p2p.exception.AlreadyRunningException;
import org.minecraftnauja.p2p.provider.event.ICallback;
import org.minecraftnauja.p2p.provider.packet.IPacket;
import org.minecraftnauja.p2p.provider.packet.PacketProviderBase;
import org.minecraftnauja.p2p.provider.packet.task.ISendTo;
import org.minecraftnauja.p2p.provider.packet.task.SendToBase;
import org.minecraftnauja.tomp2p.peer.IPeer;

/**
 * Implementation of the packet provider.
 */
public final class PacketProvider extends PacketProviderBase {

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
	public PacketProvider(IPeer peer) {
		super();
		this.peer = peer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ISendTo sendTo(String channel, IPacket packet, String player,
			ICallback<ISendTo> callback) {
		try {
			packet.setChannel(channel);
			packet.setSender(peer.getId());
			SendTo task = new SendTo(channel, packet, player);
			task.start(callback);
			return task;
		} catch (AlreadyRunningException e) {
			return null;
		}
	}

	/**
	 * Task for sending a packet to a player.
	 */
	private final class SendTo extends SendToBase {

		/**
		 * If the task is running.
		 */
		private boolean running;

		/**
		 * The task.
		 */
		private FutureResponse task;

		/**
		 * Initializing constructor.
		 * 
		 * @param channel
		 *            the channel.
		 * @param packet
		 *            the packet.
		 * @param player
		 *            the player.
		 */
		public SendTo(String channel, IPacket packet, String player) {
			super(PacketProvider.this, channel, packet, player);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized void start(final ICallback<ISendTo> callback)
				throws AlreadyRunningException {
			if (running) {
				throw new AlreadyRunningException(this);
			}
			try {
				error = null;
				if (callback != null) {
					callback.onStarted(this);
				}
				fireSendTo(SendTo.this);
				// Puts the file.
				PeerAddress pa = peer.getPeerAddress(player);
				task = peer.sendDirect(pa, packet);
				task.addCancellation(new Cancellable() {
					public void cancel() {
						// Cancelled the file upload.
						task = null;
						running = false;
						if (callback != null) {
							callback.onCancelled(SendTo.this);
						}
						fireSendToCancelled(SendTo.this);
					}
				});
				task.addListener(new BaseFutureListener<FutureResponse>() {
					public void operationComplete(FutureResponse future)
							throws Exception {
						// File uploaded.
						task = null;
						running = false;
						if (callback != null) {
							callback.onCompleted(SendTo.this);
						}
						fireSentTo(SendTo.this);
					}

					public void exceptionCaught(Throwable t) throws Exception {
						// Could not upload the file.
						task = null;
						running = false;
						error = t;
						if (callback != null) {
							callback.onException(SendTo.this);
						}
						fireSendToException(SendTo.this);
					}
				});
			} catch (Exception e) {
				// Could not read the file or put it.
				task = null;
				running = false;
				error = e;
				if (callback != null) {
					callback.onException(this);
				}
				fireSendToException(this);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized boolean isRunning() {
			return running;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized void cancel() {
			if (running && task != null) {

			}
		}

	}

}
