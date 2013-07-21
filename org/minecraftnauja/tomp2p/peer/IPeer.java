package org.minecraftnauja.tomp2p.peer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import net.tomp2p.futures.FutureDHT;
import net.tomp2p.futures.FutureResponse;
import net.tomp2p.peers.PeerAddress;

import org.minecraftnauja.p2p.provider.IProvider;
import org.minecraftnauja.tomp2p.config.IPeerConfig;

/**
 * Interface for peers.
 * 
 * @param <T>
 *            type of the configuration.
 */
public interface IPeer<T extends IPeerConfig> extends IProvider {

	/**
	 * Gets its identifier.
	 * 
	 * @return its identifier.
	 */
	public String getId();

	/**
	 * Gets its configuration.
	 * 
	 * @return its configuration.
	 */
	public T getConfig();

	/**
	 * Indicates if the peer is running.
	 * 
	 * @return if the peer is running.
	 */
	public boolean isRunning();

	/**
	 * Indicates if the peer is shutdown.
	 * 
	 * @return if the peer is shutdown.
	 */
	public boolean isShutdown();

	/**
	 * Puts data in the DHT.
	 * 
	 * @param location
	 *            key.
	 * @param data
	 *            value.
	 * @return the {@code FutureDHT}.
	 * @throws IOException
	 *             error with IO.
	 * 
	 */
	public FutureDHT put(String location, Object data) throws IOException;

	/**
	 * Gets data from the DHT.
	 * 
	 * @param location
	 *            key.
	 * @return the {@code FutureDHT}.
	 */
	public FutureDHT get(String location);

	/**
	 * Sends an object to the peer at given address.
	 * 
	 * @param address
	 *            the address.
	 * @param object
	 *            the object.
	 * @return the {@code FutureResponse}.
	 */
	public FutureResponse sendDirect(PeerAddress address, Object object);

	/**
	 * Gets the address of a player.
	 * 
	 * @param player
	 *            the player.
	 * @return its address.
	 */
	public PeerAddress getPeerAddress(String player);

	/**
	 * Gets the address of a player.
	 * 
	 * @param player
	 *            the player.
	 * @return its address.
	 */
	public InetAddress getAddress(String player);

	/**
	 * Gets players at given address.
	 * 
	 * @param address
	 *            the address.
	 * @return the players.
	 */
	public List<PeerAddress> getPlayers(InetAddress address);

	/**
	 * Gets player at given address.
	 * 
	 * @param address
	 *            the address.
	 * @return the player.
	 */
	public String getPlayer(PeerAddress address) throws Exception;

	/**
	 * Gets players at given address.
	 * 
	 * @param address
	 *            the address.
	 * @return the players.
	 */
	public List<String> getPlayers(List<PeerAddress> address) throws Exception;

}
