package it.polimi.ingsw.lim.client.network;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class ConnectionHandler extends Thread
{
	private final ScheduledExecutorService heartbeat = Executors.newSingleThreadScheduledExecutor();

	@Override
	public abstract void run();

	protected ScheduledExecutorService getHeartbeat()
	{
		return this.heartbeat;
	}
}
