package org.minecraftnauja.tomp2p.event;

import java.util.EventListener;

/**
 * Interface for mod listeners.
 */
public interface P2PListener extends EventListener {

	/**
	 * Event called when a server is being started.
	 * 
	 * @param event
	 *            the event.
	 */
	public void onServerStarting(ServerEvent event);

	/**
	 * Event called when a server has been started.
	 * 
	 * @param event
	 *            the event.
	 */
	public void onServerStarted(ServerEvent event);

	/**
	 * Event called when a server has been shutdown.
	 * 
	 * @param event
	 *            the event.
	 */
	public void onServerShutdown(ServerEvent event);

	/**
	 * Event called when a client is being started.
	 * 
	 * @param event
	 *            the event.
	 */
	public void onClientStarting(ClientEvent event);

	/**
	 * Event called when a client has been started.
	 * 
	 * @param event
	 *            the event.
	 */
	public void onClientStarted(ClientEvent event);

	/**
	 * Event called when a client has been shutdown.
	 * 
	 * @param event
	 *            the event.
	 */
	public void onClientShutdown(ClientEvent event);

}
