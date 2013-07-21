package org.minecraftnauja.tomp2p;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import org.minecraftnauja.p2p.P2P;
import org.minecraftnauja.tomp2p.config.ClientConfig;
import org.minecraftnauja.tomp2p.peer.IClient;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

/**
 * Handle packets.
 */
public class PacketHandler implements IPacketHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals(TomP2P.MOD_ID) && TomP2P.side == Side.CLIENT) {
			try {
				ByteArrayInputStream bis = new ByteArrayInputStream(packet.data);
				DataInputStream dis = new DataInputStream(bis);
				String address = dis.readUTF();
				int port = dis.readInt();
				FMLLog.log(TomP2P.MOD_ID, Level.INFO,
						"Received server config, address=%s, port=%d", address,
						port);
				connect(((EntityPlayer) player).username, address, port);
			} catch (IOException e) {
				FMLLog.log(TomP2P.MOD_ID, Level.SEVERE, e, "Corrupted packet");
			}
		}
	}

	/**
	 * Connects to the server.
	 * 
	 * @param username
	 *            player's name.
	 * @param serverAddress
	 *            its address.
	 * @param serverPort
	 *            its port.
	 */
	private void connect(final String username, final String serverAddress,
			final int serverPort) {
		// Client side, logged in, start p2p client.
		FMLLog.log(TomP2P.MOD_ID, Level.INFO,
				"Starts the p2p client in new thread");
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					Config.Client c = TomP2P.config.client;
					ClientConfig cc = new ClientConfig(c.address, c.port,
							serverAddress, serverPort, c.behindFirewall,
							c.storageType, c.storage);
					((IClient) P2P.get(P2P.CLIENT_PROVIDER))
							.start(username, cc);
				} catch (Exception e) {
					FMLLog.log(TomP2P.MOD_ID, Level.SEVERE, e,
							"Could not start the p2p client");
				}
			}
		});
		t.setPriority(Thread.NORM_PRIORITY);
		t.start();
	}

}
