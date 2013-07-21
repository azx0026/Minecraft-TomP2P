package org.minecraftnauja.tomp2p.callback;

import org.minecraftnauja.tomp2p.event.GetEvent;


/**
 * Callback for when a get operation is complete.
 */
public interface GetCallback {

	/**
	 * Called when a get operation is complete.
	 * 
	 * @param event
	 *            the event.
	 */
	public void onGet(GetEvent event);

	/**
	 * Called when a get operation failed.
	 * 
	 * @param event
	 *            the event.
	 */
	public void onGetFailed(GetEvent event);

}
