package org.minecraftnauja.tomp2p.config;

import org.minecraftnauja.tomp2p.Config.StorageType;

/**
 * Interface for peers configurations.
 */
public interface IPeerConfig {

	/**
	 * Gets the address.
	 * 
	 * @return the address.
	 */
	public String getAddress();

	/**
	 * Gets the port.
	 * 
	 * @return the port.
	 */
	public int getPort();

	/**
	 * Indicates if the peer is behind a firewall or not.
	 * 
	 * @return if the peer is behind a firewall or not.
	 */
	public boolean isBehindFirewall();

	/**
	 * Gets the storage type.
	 * 
	 * @return the storage type.
	 */
	public StorageType getStorageType();

	/**
	 * Gets the storage directory.
	 * 
	 * @return the storage directory.
	 */
	public String getStorage();

}
