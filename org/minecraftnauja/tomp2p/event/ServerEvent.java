package org.minecraftnauja.tomp2p.event;

import org.minecraftnauja.tomp2p.peer.IServer;

/**
 * Event related to a server.
 */
public class ServerEvent extends PeerEvent<IServer> {

	/**
	 * Initializing constructor.
	 * 
	 * @param source
	 *            the source.
	 * @param server
	 *            the server.
	 */
	public ServerEvent(Object source, IServer server) {
		super(source, server);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "ServerEvent [peer=" + peer + ", source=" + source + "]";
	}

}
