package org.minecraftnauja.tomp2p.event;

import java.io.IOException;

import net.tomp2p.futures.FutureDHT;

import org.minecraftnauja.tomp2p.peer.IPeer;

/**
 * Event for when a put operation is complete.
 */
public class PutEvent extends PeerEvent {

	/**
	 * The channel.
	 */
	protected final String channel;

	/**
	 * The location.
	 */
	protected final String location;

	/**
	 * The domain.
	 */
	protected final String domain;

	/**
	 * The content.
	 */
	protected final String content;

	/**
	 * The data.
	 */
	protected final Object data;

	/**
	 * Put operation.
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
	 *            the location.
	 * @param domain
	 *            the domain.
	 * @param content
	 *            the content.
	 * @param data
	 *            the data.
	 * @param futureDHT
	 *            the get operation.
	 */
	public PutEvent(Object source, IPeer peer, String channel, String location,
			String domain, String content, Object data, FutureDHT futureDHT) {
		this(source, peer, channel, location, domain, content, data, futureDHT,
				null);
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
	 *            the location.
	 * @param domain
	 *            the domain.
	 * @param content
	 *            the content.
	 * @param data
	 *            the data.
	 * @param throwable
	 *            the error.
	 */
	public PutEvent(Object source, IPeer peer, String channel, String location,
			String domain, String content, Object data, Throwable throwable) {
		this(source, peer, channel, location, domain, content, data, null,
				throwable);
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
	 *            the location.
	 * @param domain
	 *            the domain.
	 * @param content
	 *            the content.
	 * @param data
	 *            the data.
	 * @param futureDHT
	 *            the get operation.
	 * @param throwable
	 *            the error.
	 */
	private PutEvent(Object source, IPeer peer, String channel,
			String location, String domain, String content, Object data,
			FutureDHT futureDHT, Throwable throwable) {
		super(source, peer);
		this.channel = channel;
		this.location = location;
		this.domain = domain;
		this.content = content;
		this.data = data;
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
	 * Gets the location.
	 * 
	 * @return the location.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Gets the domain.
	 * 
	 * @return the domain.
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Gets the content.
	 * 
	 * @return the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Gets the data.
	 * 
	 * @return the data.
	 */
	public Object getData() {
		return data;
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
		return "PutEvent [channel=" + channel + ", location=" + location
				+ ", domain=" + domain + ", content=" + content + ", data="
				+ data + ", peer=" + peer + ", source=" + source + "]";
	}

}
