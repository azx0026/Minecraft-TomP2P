package org.minecraftnauja.tomp2p.config;

import org.minecraftnauja.tomp2p.Config.StorageType;

/**
 * Client configuration.
 */
public class ClientConfig extends PeerConfigBase implements IClientConfig {

	/**
	 * Server address.
	 */
	protected final String serverAddress;

	/**
	 * Server port.
	 */
	protected final int serverPort;

	/**
	 * Initializing constructor.
	 * 
	 * @param address
	 *            client address.
	 * @param port
	 *            client port.
	 * @param serverAddress
	 *            server address.
	 * @param serverPort
	 *            server port.
	 * @param behindFirewall
	 *            behind a firewall or not.
	 * @param storageType
	 *            storage type.
	 * @param storage
	 *            storage directory.
	 */
	public ClientConfig(String address, int port, String serverAddress,
			int serverPort, boolean behindFirewall, StorageType storageType,
			String storage) {
		super(address, port, behindFirewall, storageType, storage);
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getServerAddress() {
		return serverAddress;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "ClientConfig [address=" + address + ", port=" + port
				+ ", serverAddress=" + serverAddress + ", serverPort="
				+ serverPort + ", behindFirewall=" + behindFirewall
				+ ", storageType=" + storageType + ", storage=" + storage + "]";
	}

}
