package org.minecraftnauja.tomp2p.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.tomp2p.futures.FutureDHT;

import org.minecraftnauja.tomp2p.peer.IPeer;

/**
 * Default peer notifier.
 */
public class DefaultPeerNotifier implements PeerNotifier {

	/**
	 * The listeners.
	 */
	private final List<PeerListener> listeners;

	/**
	 * Default constructor.
	 */
	public DefaultPeerNotifier() {
		listeners = new CopyOnWriteArrayList<PeerListener>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListener(PeerListener listener) {
		listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeListener(PeerListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notifies the listeners that a get operation is complete.
	 * 
	 * @param event
	 *            the event.
	 */
	public void notifyGet(GetEvent event) {
		for (PeerListener l : listeners) {
			l.onGet(event);
		}
	}

	/**
	 * Notifies the listeners that a get operation failed.
	 * 
	 * @param event
	 *            the event.
	 */
	public void notifyGetFailed(GetEvent event) {
		for (PeerListener l : listeners) {
			l.onGetFailed(event);
		}
	}

	/**
	 * Notifies the listeners that a put operation is complete.
	 * 
	 * @param event
	 *            the event.
	 */
	public void notifyPut(PutEvent event) {
		for (PeerListener l : listeners) {
			l.onPut(event);
		}
	}

	/**
	 * Notifies the listeners that a put operation failed.
	 * 
	 * @param event
	 *            the event.
	 */
	public void notifyPutFailed(PutEvent event) {
		for (PeerListener l : listeners) {
			l.onPutFailed(event);
		}
	}

}
