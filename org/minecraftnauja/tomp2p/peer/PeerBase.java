package org.minecraftnauja.tomp2p.peer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import net.tomp2p.futures.FutureDHT;
import net.tomp2p.futures.FutureResponse;
import net.tomp2p.p2p.Peer;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.peers.PeerMap;
import net.tomp2p.rpc.ObjectDataReply;
import net.tomp2p.storage.Data;
import net.tomp2p.utils.Utils;

import org.minecraftnauja.p2p.provider.ProviderBase;
import org.minecraftnauja.p2p.provider.file.IFileProvider;
import org.minecraftnauja.p2p.provider.packet.IPacket;
import org.minecraftnauja.p2p.provider.packet.IPacketProvider;
import org.minecraftnauja.p2p.provider.player.IPlayerProvider;
import org.minecraftnauja.tomp2p.config.IPeerConfig;
import org.minecraftnauja.tomp2p.packet.ICorePacket;
import org.minecraftnauja.tomp2p.packet.PacketGetPlayer;
import org.minecraftnauja.tomp2p.provider.FileProvider;
import org.minecraftnauja.tomp2p.provider.PacketProvider;
import org.minecraftnauja.tomp2p.provider.PlayerProvider;

/**
 * Base for peers.
 * 
 * @param <T>
 *            type of the configuration.
 */
public abstract class PeerBase<T extends IPeerConfig> extends ProviderBase
		implements IPeer<T>, ObjectDataReply {

	/**
	 * Its instance.
	 */
	private Peer peer;

	/**
	 * Its configuration.
	 */
	private T config;

	/**
	 * The player provider.
	 */
	private final PlayerProvider playerProvider;

	/**
	 * The file provider.
	 */
	private final FileProvider fileProvider;

	/**
	 * The packet provider.
	 */
	private final PacketProvider packetProvider;

	/**
	 * Default constructor.
	 */
	public PeerBase() {
		super();
		playerProvider = new PlayerProvider(this);
		fileProvider = new FileProvider(this);
		packetProvider = new PacketProvider(this);
	}

	/**
	 * Gets its instance.
	 * 
	 * @return its instance.
	 */
	public synchronized Peer getPeer() {
		return peer;
	}

	/**
	 * Sets its instance.
	 * 
	 * @param peer
	 *            new value.
	 */
	protected synchronized void setPeer(Peer peer) {
		if (this.peer != null) {
			this.peer.setObjectDataReply(null);
		}
		this.peer = peer;
		if (peer != null) {
			peer.setObjectDataReply(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized T getConfig() {
		return config;
	}

	/**
	 * Sets its configuration.
	 * 
	 * @param config
	 *            new value.
	 */
	protected synchronized void setConfig(T config) {
		this.config = config;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized boolean isRunning() {
		return peer != null && peer.isRunning();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized boolean isShutdown() {
		return peer == null || peer.isShutdown();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPlayerProvider getPlayerProvider() {
		return playerProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IFileProvider getFileProvider() {
		return fileProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPacketProvider getPacketProvider() {
		return packetProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object reply(PeerAddress sender, Object request) throws Exception {
		if (request instanceof ICorePacket) {
			ICorePacket p = (ICorePacket) request;
			return p.handle(this, sender, request);
		} else if (request instanceof IPacket) {
			IPacket p = (IPacket) request;
			p.setSource(packetProvider);
			p.setAddress(sender.getInetAddress());
			packetProvider.fireReceived(p);
			return null;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FutureDHT put(String location, Object data) throws IOException {
		return peer.put(Number160.createHash(location)).setData(new Data(data))
				.setRefreshSeconds(2).setDirectReplication().start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FutureDHT get(String location) {
		return peer.get(Number160.createHash(location)).start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FutureResponse sendDirect(PeerAddress address, Object object) {
		return peer.sendDirect(address).setObject(object).start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PeerAddress getPeerAddress(String player) {
		if (getId().equals(player)) {
			return peer.getPeerAddress();
		} else {
			Number160 id = Number160.createHash(player);
			PeerMap pm = peer.getPeerBean().getPeerMap();
			for (PeerAddress pa : pm.getAll()) {
				if (pa.getID().equals(id)) {
					return pa;
				}
			}
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InetAddress getAddress(String player) {
		PeerAddress pa = getPeerAddress(player);
		if (pa == null) {
			return null;
		} else {
			return pa.getInetAddress();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PeerAddress> getPlayers(InetAddress address) {
		if (address == null) {
			return null;
		} else {
			List<PeerAddress> l = new ArrayList<PeerAddress>();
			if (peer.getPeerAddress().getInetAddress().equals(address)) {
				l.add(peer.getPeerAddress());
			}
			PeerMap pm = peer.getPeerBean().getPeerMap();
			for (PeerAddress pa : pm.getAll()) {
				if (pa.getInetAddress().equals(address)) {
					l.add(pa);
				}
			}
			return l;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPlayer(PeerAddress address) throws Exception {
		FutureResponse fr = peer.sendDirect(address)
				.setObject(new PacketGetPlayer()).start()
				.awaitUninterruptibly();
		if (fr.isFailed()) {
			throw new Exception(fr.getFailedReason());
		} else {
			return (String) Utils.decodeJavaObject(fr.getResponse()
					.getPayload1());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getPlayers(List<PeerAddress> address) throws Exception {
		if (address == null) {
			return null;
		} else {
			List<String> l = new ArrayList<String>();
			for (PeerAddress pa : address) {
				try {
					String s = getPlayer(pa);
					if (s != null) {
						l.add(s);
					}
				} catch (Exception e) {
				}
			}
			return l;
		}
	}

}
