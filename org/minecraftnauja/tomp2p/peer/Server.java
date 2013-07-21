package org.minecraftnauja.tomp2p.peer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;

import net.tomp2p.connection.Bindings;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerMaker;
import net.tomp2p.peers.Number160;

import org.minecraftnauja.p2p.P2P;
import org.minecraftnauja.tomp2p.TomP2P;
import org.minecraftnauja.tomp2p.config.IServerConfig;

import cpw.mods.fml.common.FMLLog;

/**
 * Server.
 */
public class Server extends PeerBase<IServerConfig> implements IServer {

	/**
	 * Its identifier.
	 */
	public static final String PEER_ID = "Server";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return P2P.SERVER_PROVIDER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return P2P.SERVER_PROVIDER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void start(IServerConfig config)
			throws UnknownHostException, IOException {
		if (isRunning()) {
			return;
		}
		FMLLog.log(TomP2P.MOD_ID, Level.INFO, "Server: starting with %s...",
				config);
		fireStarting();
		Bindings b = new Bindings();
		b.addAddress(InetAddress.getByName(config.getAddress()));
		Peer peer = new PeerMaker(Number160.createHash(PEER_ID)).setBindings(b)
				.setPorts(config.getPort()).setEnableIndirectReplication(true)
				.makeAndListen();
		setPeer(peer);
		setConfig(config);
		peer.getConfiguration().setBehindFirewall(config.isBehindFirewall());
		config.getStorageType().apply(peer, config);
		// Notifies.
		FMLLog.log(TomP2P.MOD_ID, Level.INFO, "Server: started");
		fireStarted();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void stop() {
		if (isRunning()) {
			fireStopping();
			getPeer().shutdown();
			setPeer(null);
			setConfig(null);
			fireStopped();
		}
	}

}
