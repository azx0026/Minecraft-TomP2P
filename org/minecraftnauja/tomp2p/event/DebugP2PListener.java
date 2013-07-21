package org.minecraftnauja.tomp2p.event;

import java.util.logging.Level;

import org.minecraftnauja.tomp2p.TomP2P;

import cpw.mods.fml.common.FMLLog;

/**
 * Listener for debug purpose.
 */
public class DebugP2PListener implements P2PListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServerStarting(ServerEvent event) {
		FMLLog.log(TomP2P.MOD_ID, Level.INFO, "onServerStarting %s", event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServerStarted(ServerEvent event) {
		FMLLog.log(TomP2P.MOD_ID, Level.INFO, "onServerStarted %s", event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServerShutdown(ServerEvent event) {
		FMLLog.log(TomP2P.MOD_ID, Level.INFO, "onServerShutdown %s", event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClientStarting(ClientEvent event) {
		FMLLog.log(TomP2P.MOD_ID, Level.INFO, "onClientStarting %s", event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClientStarted(ClientEvent event) {
		FMLLog.log(TomP2P.MOD_ID, Level.INFO, "onClientStarted %s", event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClientShutdown(ClientEvent event) {
		FMLLog.log(TomP2P.MOD_ID, Level.INFO, "onClientShutdown %s", event);
	}

}
