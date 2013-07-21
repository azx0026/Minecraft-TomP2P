package org.minecraftnauja.tomp2p.event;

import java.io.IOException;

import net.tomp2p.futures.FutureDHT;
import net.tomp2p.storage.Data;

import org.minecraftnauja.tomp2p.peer.IPeer;

/**
 * Event for when a get operation is complete.
 */
public class GetEvent extends PeerEvent {

	/**
	 * The channel.
	 */
	protected final String channel;

	/**
	 * Requested location.
	 */
	protected final String location;

	/**
	 * Requested domain.
	 */
	protected final String domain;

	/**
	 * Requested content.
	 */
	protected final String content;

	/**
	 * Get operation.
	 */
	private final FutureDHT futureDHT;

	/**
	 * The error.
	 */
	private final Throwable throwable;

	/**
	 * Initializing constructor.
	 * 
	 * @param source
	 *            the source.
	 * @param peer
	 *            the peer.
	 * @param channel
	 *            the channel.
	 * @param location
	 *            requested location.
	 * @param domain
	 *            requested domain.
	 * @param content
	 *            requested content.
	 * @param futureDHT
	 *            the get operation.
	 */
	public GetEvent(Object source, IPeer peer, String channel, String location,
			String domain, String content, FutureDHT futureDHT) {
		this(source, peer, channel, location, domain, content, futureDHT, null);
	}

	/**
	 * Initializing constructor.
	 * 
	 * @param source
	 *            the source.
	 * @param peer
	 *            the peer.
	 * @param channel
	 *            the channel.
	 * @param location
	 *            requested location.
	 * @param domain
	 *            requested domain.
	 * @param content
	 *            requested content.
	 * @param throwable
	 *            the error.
	 */
	public GetEvent(Object source, IPeer peer, String channel, String location,
			String domain, String content, Throwable throwable) {
		this(source, peer, channel, location, domain, content, null, throwable);
	}

	/**
	 * Initializing constructor.
	 * 
	 * @param source
	 *            the source.
	 * @param peer
	 *            the peer.
	 * @param channel
	 *            the channel.
	 * @param location
	 *            requested location.
	 * @param domain
	 *            requested domain.
	 * @param content
	 *            requested content.
	 * @param futureDHT
	 *            the get operation.
	 * @param throwable
	 *            the error.
	 */
	private GetEvent(Object source, IPeer peer, String channel,
			String location, String domain, String content,
			FutureDHT futureDHT, Throwable throwable) {
		super(source, peer);
		this.channel = channel;
		this.location = location;
		this.domain = domain;
		this.content = content;
		this.futureDHT = futureDHT;
		this.throwable = throwable;
	}

	/**
	 * Gets the channel.
	 * 
	 * @return the channel.
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * Gets the requested location.
	 * 
	 * @return the requested location.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Gets the requested domain.
	 * 
	 * @return the requested domain.
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Gets the requested content.
	 * 
	 * @return the requested content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Gets the data.
	 * 
	 * @return the data.
	 * @throws ClassNotFoundException
	 *             the data has an unkown class.
	 * @throws IOException
	 *             error with IO.
	 */
	public Object getData() throws ClassNotFoundException, IOException {
		if (futureDHT != null) {
			Data data = futureDHT.getData();
			if (data != null) {
				return data.getObject();
			}
		}
		return null;
	}

	/**
	 * Gets the error.
	 * 
	 * @return the error.
	 */
	public Throwable getError() {
		return throwable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "GetEvent [channel=" + channel + ", location=" + location
				+ ", domain=" + domain + ", content=" + content + ", peer="
				+ peer + ", source=" + source + "]";
	}

}
