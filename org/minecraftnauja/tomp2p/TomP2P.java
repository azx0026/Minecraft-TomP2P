package org.minecraftnauja.tomp2p;

import java.util.logging.Level;

import org.minecraftnauja.p2p.P2P;
import org.minecraftnauja.tomp2p.config.ServerConfig;
import org.minecraftnauja.tomp2p.peer.Client;
import org.minecraftnauja.tomp2p.peer.IServer;
import org.minecraftnauja.tomp2p.peer.Server;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerAboutToStart;
import cpw.mods.fml.common.Mod.ServerStopped;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "TomP2P", name = "TomP2P", version = "1.0.0", dependencies = "required-after:P2P")
@NetworkMod(clientSideRequired = false, serverSideRequired = false, channels = { "TomP2P" }, connectionHandler = ConnectionHandler.class, packetHandler = PacketHandler.class)
public class TomP2P {

	/**
	 * Mod identifier.
	 */
	public static final String MOD_ID = "TomP2P";

	/**
	 * Mod instance.
	 */
	@Instance("TomP2P")
	public static TomP2P instance;

	/**
	 * Mod configuration.
	 */
	public static Config config;

	/**
	 * Side.
	 */
	public static Side side;

	/**
	 * {@inheritDoc}
	 */
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		config = Config.load(event);
		side = event.getSide();
		P2P.register(new Server());
		if (side == Side.CLIENT) {
			P2P.register(new Client());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Init
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerPlayerTracker(new PlayerTracker());
	}

	/**
	 * Called when the Minecraft server started.
	 * 
	 * @param event
	 *            the event.
	 */
	@ServerAboutToStart
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {
		try {
			Config.Server c = config.server;
			ServerConfig sc = new ServerConfig(c.address, c.externalAddress,
					c.port, c.behindFirewall, c.storageType, c.storage);
			((IServer) P2P.get(P2P.SERVER_PROVIDER)).start(sc);
		} catch (Exception e) {
			FMLLog.log(MOD_ID, Level.SEVERE, e, "Can't start the server");
		}
	}

	/**
	 * Called when the Minecraft server stopped.
	 * 
	 * @param event
	 *            the event.
	 */
	@ServerStopped
	public void serverStopped(FMLServerStoppedEvent event) {
		((IServer) P2P.get(P2P.SERVER_PROVIDER)).stop();
	}

}
