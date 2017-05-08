package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.network.ConnectionListener;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;

public class Server extends Instance
{
	private int rmiPort;
	private int socketPort;
	private ConnectionListener connectionListener;
	private final ConcurrentLinkedQueue<Connection> connections = new ConcurrentLinkedQueue<>();
	private int connectionId;
	private final ConcurrentLinkedQueue<Room> rooms = new ConcurrentLinkedQueue<>();
	private int roomId;

	/**
	 * Initializes RMI and Socket Servers and, if successful, opens the main screen.
	 * @param rmiPort the port of the RMI Server.
	 * @param socketPort the port of the Socket Server.
	 */
	public void setup(int rmiPort, int socketPort)
	{
		this.rmiPort = rmiPort;
		this.socketPort = socketPort;
		this.connectionId = 0;
		this.roomId = 0;
		this.getWindowInformations().getStage().getScene().getRoot().setDisable(true);
		this.connectionListener = new ConnectionListener(rmiPort, socketPort);
		this.connectionListener.start();
	}

	/**
	 * Disconnects all Clients, waiting for every thread to terminate properly.
	 */
	@Override
	public void stop()
	{
		if (this.connectionListener == null) {
			return;
		}
		Connection.broadcastChatMessage("Server shutting down...");
		if (this.connectionListener.getRegistry() != null) {
			try {
				this.connectionListener.getRegistry().unbind("lorenzo-il-magnifico");
				UnicastRemoteObject.unexportObject(this.connectionListener.getRegistry(), true);
				UnicastRemoteObject.unexportObject(this.connectionListener.getHandshake(), true);
			} catch (RemoteException | NotBoundException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
		if (this.connectionListener != null) {
			this.connectionListener.close();
			try (Socket socket = new Socket("localhost", this.socketPort)) {
				socket.close();
			} catch (IOException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
			try {
				this.connectionListener.join();
			} catch (InterruptedException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				Thread.currentThread().interrupt();
			}
		}
	}

	public static Server getInstance()
	{
		return (Server) Instance.getInstance();
	}

	public int getRmiPort()
	{
		return this.rmiPort;
	}

	public int getSocketPort()
	{
		return this.socketPort;
	}

	public ConnectionListener getConnectionListener()
	{
		return this.connectionListener;
	}

	public Queue<Connection> getConnections()
	{
		return this.connections;
	}

	public int getConnectionId()
	{
		return this.connectionId++;
	}

	public Queue<Room> getRooms()
	{
		return this.rooms;
	}

	public int getRoomId()
	{
		return this.roomId++;
	}
}
