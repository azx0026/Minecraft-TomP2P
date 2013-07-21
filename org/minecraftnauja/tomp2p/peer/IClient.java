package org.minecraftnauja.tomp2p.peer;

import java.io.IOException;

import org.minecraftnauja.tomp2p.config.ClientConfig;
import org.minecraftnauja.tomp2p.config.IClientConfig;

/**
 * Interface for clients.
 */
public interface IClient extends IPeer<IClientConfig> {

	/**
	 * Starts the client.
	 * 
	 * @param id
	 *            peer identifier.
	 * @param config
	 *            client configuration.
	 * @throws IOException
	 *             error with IO.
	 */
	public void start(String id, ClientConfig config) throws IOException;

	/**
	 * Stops the client.
	 */
	public void stop();

}
