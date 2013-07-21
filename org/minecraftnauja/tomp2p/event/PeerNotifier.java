package org.minecraftnauja.tomp2p.event;

/**
 * Interface for peers notifiers.
 */
public interface PeerNotifier {

	/**
	 * Adds given listener.
	 * 
	 * @param listener
	 *            the listener to add.
	 */
	public void addListener(PeerListener listener);

	/**
	 * Removes given listener.
	 * 
	 * @param listener
	 *            the listener to remove.
	 */
	public void removeListener(PeerListener listener);

}
