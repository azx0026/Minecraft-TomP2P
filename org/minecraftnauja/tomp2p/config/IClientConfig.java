package org.minecraftnauja.tomp2p.config;

/**
 * Interface for clients configurations.
 */
public interface IClientConfig extends IPeerConfig {

	/**
	 * Gets the server address.
	 * 
	 * @return the server address.
	 */
	public String getServerAddress();

	/**
	 * Gets the server port.
	 * 
	 * @return the server port.
	 */
	public int getServerPort();

}
