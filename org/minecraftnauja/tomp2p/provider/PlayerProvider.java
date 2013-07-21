package org.minecraftnauja.tomp2p.provider;

import java.net.InetAddress;
import java.util.List;

import org.minecraftnauja.p2p.exception.AlreadyRunningException;
import org.minecraftnauja.p2p.provider.event.ICallback;
import org.minecraftnauja.p2p.provider.player.PlayerProviderBase;
import org.minecraftnauja.p2p.provider.player.task.GetAddressBase;
import org.minecraftnauja.p2p.provider.player.task.GetPlayersBase;
import org.minecraftnauja.p2p.provider.player.task.IGetAddress;
import org.minecraftnauja.p2p.provider.player.task.IGetPlayers;
import org.minecraftnauja.tomp2p.peer.IPeer;

/**
 * Implementation of the player provider.
 */
public final class PlayerProvider extends PlayerProviderBase {

	/**
	 * Peer instance.
	 */
	private final IPeer peer;

	/**
	 * Initializing constructor.
	 * 
	 * @param peer
	 *            peer instance.
	 */
	public PlayerProvider(IPeer peer) {
		super();
		this.peer = peer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IGetAddress getAddress(String channel, String player,
			ICallback<IGetAddress> callback) {
		try {
			GetAddress task = new GetAddress(channel, player);
			task.start(callback);
			return task;
		} catch (AlreadyRunningException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IGetPlayers getPlayers(String channel, InetAddress address,
			ICallback<IGetPlayers> callback) {
		try {
			GetPlayers task = new GetPlayers(channel, address);
			task.start(callback);
			return task;
		} catch (AlreadyRunningException e) {
			return null;
		}
	}

	/**
	 * Task for getting a player address.
	 */
	private final class GetAddress extends GetAddressBase {

		/**
		 * Initializing constructor.
		 * 
		 * @param channel
		 *            the channel.
		 * @param player
		 *            the player.
		 */
		public GetAddress(String channel, String player) {
			super(PlayerProvider.this, channel, player);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized void start(final ICallback<IGetAddress> callback)
				throws AlreadyRunningException {
			address = null;
			if (callback != null) {
				callback.onStarted(this);
			}
			fireGetAddress(this);
			address = peer.getAddress(player);
			if (callback != null) {
				callback.onCompleted(this);
			}
			fireGotAddress(this);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isRunning() {
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void cancel() {
		}

	}

	/**
	 * Task for getting players.
	 */
	private final class GetPlayers extends GetPlayersBase {

		/**
		 * Initializing constructor.
		 * 
		 * @param channel
		 *            the channel.
		 * @param address
		 *            the address.
		 */
		public GetPlayers(String channel, InetAddress address) {
			super(PlayerProvider.this, channel, address);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized void start(final ICallback<IGetPlayers> callback)
				throws AlreadyRunningException {
			players = null;
			error = null;
			if (callback != null) {
				callback.onStarted(this);
			}
			fireGetPlayer(this);
			try {
				List<String> l = peer.getPlayers(peer.getPlayers(address));
				if (l != null) {
					players = l.toArray(new String[l.size()]);
				} else {
					players = new String[0];
				}
				if (callback != null) {
					callback.onCompleted(this);
				}
				fireGotPlayer(this);
			} catch (Exception e) {
				error = e;
				if (callback != null) {
					callback.onException(this);
				}
				fireGetPlayerException(this);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isRunning() {
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void cancel() {
		}

	}

}
