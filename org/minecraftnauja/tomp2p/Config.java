package org.minecraftnauja.tomp2p;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import net.tomp2p.p2p.Peer;
import net.tomp2p.storage.StorageDisk;
import net.tomp2p.storage.StorageMemory;

import org.minecraftnauja.tomp2p.config.IPeerConfig;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Mod configuration.
 */
public class Config {

	/**
	 * Loads the configuration.
	 * 
	 * @param event
	 *            initialization event.
	 * @return the configuration.
	 */
	public static Config load(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());
		config.load();
		Config c = new Config();
		c.load(config, event);
		config.save();
		return c;
	}

	/**
	 * Server configuration.
	 */
	public Server server;

	/**
	 * Client configuration.
	 */
	public Client client;

	/**
	 * Loads the configuration.
	 * 
	 * @param config
	 *            the configuration.
	 * @param event
	 *            initialization event.
	 */
	private void load(Configuration config, FMLPreInitializationEvent event) {
		server = new Server();
		switch (event.getSide()) {
		case SERVER:
			server.load(config, event);
			break;
		case CLIENT:
			client = new Client();
			client.load(config, event);
			break;
		}
		FMLLog.log(TomP2P.MOD_ID, Level.INFO, "%s", this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Config [server=" + server + ", client=" + client + "]";
	}

	/**
	 * Server configuration.
	 */
	public static class Server {

		/**
		 * Category in configuration.
		 */
		private static final String CATEGORY = "SERVER";

		/**
		 * Address.
		 */
		public String address = "127.0.0.1";

		/**
		 * External address for clients.
		 */
		public String externalAddress = "127.0.0.1";

		/**
		 * Port.
		 */
		public int port = 4000;

		/**
		 * Behind a firewall or not.
		 */
		public boolean behindFirewall = false;

		/**
		 * Storage type.
		 */
		public StorageType storageType = StorageType.None;

		/**
		 * Storage directory.
		 */
		public String storage = null;

		/**
		 * Loads the configuration.
		 * 
		 * @param config
		 *            the configuration.
		 * @param event
		 *            initialization event.
		 */
		public void load(Configuration config, FMLPreInitializationEvent event) {
			address = config.get(CATEGORY, "Address", address).getString();
			externalAddress = config.get(CATEGORY, "ExternalAddress",
					externalAddress).getString();
			port = config.get(CATEGORY, "Port", port).getInt(port);
			behindFirewall = config.get(CATEGORY, "BehindFirewall",
					behindFirewall).getBoolean(behindFirewall);
			storage = config.get(CATEGORY, "Storage", "none").getString();
			// No storage.
			if (storage.equalsIgnoreCase("none")) {
				storageType = StorageType.None;
				storage = null;
			} else if (storage.equalsIgnoreCase("memory")) {
				// Storage in memory.
				storageType = StorageType.Memory;
				storage = null;
			} else {
				// Storage on disk.
				storageType = StorageType.Disk;
				File f = new File(storage);
				if (!f.exists()) {
					if (!f.mkdirs()) {
						FMLLog.log(TomP2P.MOD_ID, Level.SEVERE,
								"Could not make storage directory %s", storage);
					}
				}
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "Server [address=" + address + ", externalAddress="
					+ externalAddress + ", port=" + port + ", behindFirewall="
					+ behindFirewall + ", storageType=" + storageType
					+ ", storage=" + storage + "]";
		}

	}

	/**
	 * Client configuration.
	 */
	public static class Client {

		/**
		 * Category in configuration.
		 */
		private static final String CATEGORY = "CLIENT";

		/**
		 * Address.
		 */
		public String address = "127.0.0.1";

		/**
		 * Port.
		 */
		public int port = 4563;

		/**
		 * Behind a firewall or not.
		 */
		public boolean behindFirewall = false;

		/**
		 * Storage type.
		 */
		public StorageType storageType = StorageType.Memory;

		/**
		 * Storage directory.
		 */
		public String storage = null;

		/**
		 * Loads the configuration.
		 * 
		 * @param config
		 *            the configuration.
		 * @param event
		 *            initialization event.
		 */
		public void load(Configuration config, FMLPreInitializationEvent event) {
			address = config.get(CATEGORY, "Address", address).getString();
			port = config.get(CATEGORY, "Port", port).getInt(port);
			behindFirewall = config.get(CATEGORY, "BehindFirewall",
					behindFirewall).getBoolean(behindFirewall);
			storage = config.get(CATEGORY, "Storage", "memory").getString();
			// Storage in memory.
			if (storage.equalsIgnoreCase("memory")) {
				storageType = StorageType.Memory;
				storage = null;
			} else {
				// Storage on disk, creates the storage directory.
				storageType = StorageType.Disk;
				File f = new File(storage);
				if (!f.exists()) {
					if (!f.mkdirs()) {
						FMLLog.log(TomP2P.MOD_ID, Level.SEVERE,
								"Could not make storage directory %s", storage);
					}
				}
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "Client [address=" + address + ", port=" + port
					+ ", behindFirewall=" + behindFirewall + ", storageType="
					+ storageType + ", storage=" + storage + "]";
		}

	}

	/**
	 * Enum for the types of storage.
	 */
	public static enum StorageType {

		/**
		 * No storage.
		 */
		None {

			public void apply(Peer peer, IPeerConfig config) {
			}

		},
		/**
		 * Storage on disk.
		 */
		Disk {

			public void apply(Peer peer, IPeerConfig config) {
				peer.getPeerBean().setStorage(
						new StorageDisk(config.getStorage()));
			}

		},
		/**
		 * Storage in memory.
		 */
		Memory {

			public void apply(Peer peer, IPeerConfig config) {
				peer.getPeerBean().setStorage(new StorageMemory());
			}

		};

		/**
		 * Applies the configuration to given peer.
		 * 
		 * @param peer
		 *            the peer.
		 * @param config
		 *            the configuration.
		 */
		public abstract void apply(Peer peer, IPeerConfig config);

	}

}
