package org.minecraftnauja.tomp2p.event;

/**
 * Interface for the mod notifier.
 */
public interface P2PNotifier {

	/**
	 * Adds a listener for events associated to given mod identifier.
	 * 
	 * @param listener
	 *            the listener to add.
	 */
	public void addListener(P2PListener listener);

	/**
	 * Removes a listener for events associated to given mod identifier.
	 * 
	 * @param listener
	 *            the listener to remove.
	 */
	public void removeListener(P2PListener listener);

}
