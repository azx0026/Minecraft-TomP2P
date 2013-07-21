package org.minecraftnauja.tomp2p.event;

import java.util.EventObject;

/**
 * Event produced by the P2P mod.
 */
public abstract class P2PEvent extends EventObject {

	/**
	 * Initializing constructor.
	 * 
	 * @param source
	 *            the source.
	 */
	public P2PEvent(Object source) {
		super(source);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "P2PEvent [source=" + source + "]";
	}

}
