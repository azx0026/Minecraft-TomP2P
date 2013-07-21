package org.minecraftnauja.tomp2p;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 * Tracker for players.
 */
public class PlayerTracker implements IPlayerTracker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPlayerLogin(EntityPlayer player) {
		// Sends server configuration.
		try {
			FMLLog.log(TomP2P.MOD_ID, Level.INFO,
					"Player logged in, sending server config");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			// Localhost or not.
			EntityPlayerMP p = (EntityPlayerMP) player;
			String hs = p.getPlayerIP();
			if (hs.contains("localhost") || hs.contains("127.0.0.1")
					|| hs.startsWith("192.168")) {
				// Localhost, send local address.
				dos.writeUTF(TomP2P.config.server.address);
			} else {
				// Send external address.
				dos.writeUTF(TomP2P.config.server.externalAddress);
			}
			//
			dos.writeInt(TomP2P.config.server.port);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = TomP2P.MOD_ID;
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
		} catch (IOException e) {
			FMLLog.log(TomP2P.MOD_ID, Level.SEVERE, e,
					"Could not send server config to player");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPlayerLogout(EntityPlayer player) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPlayerRespawn(EntityPlayer player) {
	}

}
