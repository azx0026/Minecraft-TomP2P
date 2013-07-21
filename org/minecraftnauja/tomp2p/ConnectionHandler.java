package org.minecraftnauja.tomp2p;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

import org.minecraftnauja.p2p.P2P;
import org.minecraftnauja.tomp2p.peer.IClient;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

/**
 * Handle players connections to establish p2p connections.
 */
public class ConnectionHandler implements IConnectionHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connectionClosed(INetworkManager manager) {
		// Client side, connection closed, shutdown the p2p client.
		if (TomP2P.side == Side.CLIENT) {
			((IClient) P2P.get(P2P.CLIENT_PROVIDER)).stop();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler,
			INetworkManager manager) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			INetworkManager manager) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, INetworkManager manager) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, INetworkManager manager) {
	}

}
