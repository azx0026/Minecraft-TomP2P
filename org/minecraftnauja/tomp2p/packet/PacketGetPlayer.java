package org.minecraftnauja.tomp2p.packet;

import net.tomp2p.peers.PeerAddress;

import org.minecraftnauja.tomp2p.peer.PeerBase;

/**
 * Packet for getting a player's name.
 */
public class PacketGetPlayer implements ICorePacket {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object handle(PeerBase peer, PeerAddress sender, Object request) {
		return peer.getId();
	}

}
