package org.minecraftnauja.tomp2p.config;

/**
 * Interface for servers configurations.
 */
public interface IServerConfig extends IPeerConfig {

	/**
	 * Gets the address.
	 * 
	 * @return the address.
	 */
	public String getAddress();

	/**
	 * Gets the external address.
	 * 
	 * @return the external address.
	 */
	public String getExternalAddress();

}
