package org.minecraftnauja.tomp2p.peer;

import java.io.IOException;
import java.net.UnknownHostException;

import org.minecraftnauja.tomp2p.config.IServerConfig;

/**
 * Interface for servers.
 */
public interface IServer extends IPeer<IServerConfig> {

	/**
	 * Starts the server.
	 * 
	 * @param config
	 *            server configuration.
	 * @throws UnknownHostException
	 *             the host is unknown.
	 * @throws IOException
	 *             error with IO.
	 */
	public void start(IServerConfig config) throws UnknownHostException,
			IOException;

	/**
	 * Stops the server.
	 */
	public void stop();

}
