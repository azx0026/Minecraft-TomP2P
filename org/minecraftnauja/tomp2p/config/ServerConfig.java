package org.minecraftnauja.tomp2p.config;

import org.minecraftnauja.tomp2p.Config.StorageType;

/**
 * Server configuration.
 */
public class ServerConfig extends PeerConfigBase implements IServerConfig {

	/**
	 * External server address.
	 */
	protected final String externalAddress;

	/**
	 * Initializing constructor.
	 * 
	 * @param address
	 *            server address.
	 * @param externalAddress
	 *            external server address.
	 * @param port
	 *            server port.
	 * @param behindFirewall
	 *            behind a firewall or not.
	 * @param storageType
	 *            storage type.
	 * @param storage
	 *            storage directory.
	 */
	public ServerConfig(String address, String externalAddress, int port,
			boolean behindFirewall, StorageType storageType, String storage) {
		super(address, port, behindFirewall, storageType, storage);
		this.externalAddress = externalAddress;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getExternalAddress() {
		return externalAddress;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "ServerConfig [address=" + address + ", externalAddress="
				+ externalAddress + ", port=" + port + ", behindFirewall="
				+ behindFirewall + ", storageType=" + storageType
				+ ", storage=" + storage + "]";
	}

}
