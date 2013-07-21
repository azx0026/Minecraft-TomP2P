package org.minecraftnauja.tomp2p.event;

import org.minecraftnauja.tomp2p.peer.IPeer;

/**
 * Event related to a peer.
 * 
 * @param <T>
 *            type of the peer.
 */
public abstract class PeerEvent<T extends IPeer> extends P2PEvent {

	/**
	 * Peer.
	 */
	protected final T peer;

	/**
	 * Initializing constructor.
	 * 
	 * @param source
	 *            the source.
	 * @param peer
	 *            the peer.
	 */
	public PeerEvent(Object source, T peer) {
		super(source);
		this.peer = peer;
	}

	/**
	 * Gets the peer.
	 * 
	 * @return the peer.
	 */
	public T getPeer() {
		return peer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "PeerEvent [peer=" + peer + ", source=" + source + "]";
	}

}
