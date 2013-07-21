package org.minecraftnauja.tomp2p.config;

import org.minecraftnauja.tomp2p.Config.StorageType;

/**
 * Peer configuration.
 */
public abstract class PeerConfigBase implements IPeerConfig {

	/**
	 * Address.
	 */
	protected final String address;

	/**
	 * Port.
	 */
	protected final int port;

	/**
	 * Behind a firewall or not.
	 */
	protected final boolean behindFirewall;

	/**
	 * Storage type.
	 */
	protected final StorageType storageType;

	/**
	 * Storage directory.
	 */
	protected final String storage;

	/**
	 * Initializing constructor.
	 * 
	 * @param address
	 *            address.
	 * @param port
	 *            port.
	 * @param behindFirewall
	 *            behind a firewall or not.
	 * @param storageType
	 *            storage type.
	 * @param storage
	 *            storage directory.
	 */
	public PeerConfigBase(String address, int port, boolean behindFirewall,
			StorageType storageType, String storage) {
		super();
		this.address = address;
		this.port = port;
		this.behindFirewall = behindFirewall;
		this.storageType = storageType;
		this.storage = storage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAddress() {
		return address;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPort() {
		return port;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBehindFirewall() {
		return behindFirewall;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StorageType getStorageType() {
		return storageType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStorage() {
		return storage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Config [address=" + address + ", port=" + port
				+ ", behindFirewall=" + behindFirewall + ", storageType="
				+ storageType + ", storage=" + storage + "]";
	}

}
