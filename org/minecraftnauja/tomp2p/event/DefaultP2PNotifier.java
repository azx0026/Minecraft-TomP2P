package org.minecraftnauja.tomp2p.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.minecraftnauja.tomp2p.peer.IClient;
import org.minecraftnauja.tomp2p.peer.IServer;

/**
 * Notifier for the mod.
 */
public class DefaultP2PNotifier implements P2PNotifier {

	/**
	 * The listeners.
	 */
	private final List<P2PListener> listeners;

	/**
	 * Default constructor.
	 */
	public DefaultP2PNotifier() {
		listeners = new CopyOnWriteArrayList<P2PListener>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListener(P2PListener listener) {
		listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeListener(P2PListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notifies the listeners that a server is being started.
	 * 
	 * @param server
	 *            the server.
	 */
	public void notifyServerStarting(IServer server) {
		ServerEvent e = null;
		for (P2PListener l : listeners) {
			if (e == null)
				e = new ServerEvent(this, server);
			l.onServerStarting(e);
		}
	}

	/**
	 * Notifies the listeners that a server has been started.
	 * 
	 * @param server
	 *            the server.
	 */
	public void notifyServerStarted(IServer server) {
		ServerEvent e = null;
		for (P2PListener l : listeners) {
			if (e == null)
				e = new ServerEvent(this, server);
			l.onServerStarted(e);
		}
	}

	/**
	 * Notifies the listeners that a server has been shutdown.
	 * 
	 * @param server
	 *            the server.
	 */
	public void notifyServerShutdown(IServer server) {
		ServerEvent e = null;
		for (P2PListener l : listeners) {
			if (e == null)
				e = new ServerEvent(this, server);
			l.onServerShutdown(e);
		}
	}

	/**
	 * Notifies the listeners that a client is being started.
	 * 
	 * @param client
	 *            the client.
	 */
	public void notifyClientStarting(IClient client) {
		ClientEvent e = null;
		for (P2PListener l : listeners) {
			if (e == null)
				e = new ClientEvent(this, client);
			l.onClientStarting(e);
		}
	}

	/**
	 * Notifies the listeners that a client has been started.
	 * 
	 * @param client
	 *            the client.
	 */
	public void notifyClientStarted(IClient client) {
		ClientEvent e = null;
		for (P2PListener l : listeners) {
			if (e == null)
				e = new ClientEvent(this, client);
			l.onClientStarted(e);
		}
	}

	/**
	 * Notifies the listeners that a client has been shutdown.
	 * 
	 * @param client
	 *            the client.
	 */
	public void notifyClientShutdown(IClient client) {
		ClientEvent e = null;
		for (P2PListener l : listeners) {
			if (e == null)
				e = new ClientEvent(this, client);
			l.onClientShutdown(e);
		}
	}

}
