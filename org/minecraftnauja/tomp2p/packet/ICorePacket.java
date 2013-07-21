package org.minecraftnauja.tomp2p.packet;

import java.io.Serializable;

import net.tomp2p.peers.PeerAddress;

import org.minecraftnauja.tomp2p.peer.PeerBase;

/**
 * Packets for internal tasks.
 */
public interface ICorePacket extends Serializable {

	/**
	 * Handle this packet.
	 * 
	 * @param peer
	 *            peer instance.
	 * @param sender
	 *            the sender.
	 * @param request
	 *            the request.
	 * @return the response.
	 */
	public Object handle(PeerBase peer, PeerAddress sender, Object request);

}
