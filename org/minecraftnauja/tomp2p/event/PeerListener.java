package org.minecraftnauja.tomp2p.event;

import java.util.EventListener;

import org.minecraftnauja.tomp2p.callback.GetCallback;
import org.minecraftnauja.tomp2p.callback.PutCallback;

/**
 * Interface for peers listeners.
 */
public interface PeerListener extends EventListener, GetCallback, PutCallback {

}
