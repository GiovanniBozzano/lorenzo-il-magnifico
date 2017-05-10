package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.network.ConnectionHandler;

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
	private String externalIp;
	private ConnectionHandler connectionHandler;
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
		this.connectionHandler = new ConnectionHandler(rmiPort, socketPort);
		this.connectionHandler.start();
	}

	/**
	 * Disconnects all Clients, waiting for every thread to terminate properly.
	 */
	@Override
	public void stop()
	{
		if (this.connectionHandler == null) {
			return;
		}
		Connection.broadcastChatMessage("Server shutting down...");
		if (this.connectionHandler.getRegistry() != null) {
			try {
				this.connectionHandler.getRegistry().unbind("lorenzo-il-magnifico");
				UnicastRemoteObject.unexportObject(this.connectionHandler.getRegistry(), true);
				UnicastRemoteObject.unexportObject(this.connectionHandler.getHandshake(), true);
			} catch (RemoteException | NotBoundException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
		if (this.connectionHandler != null) {
			this.connectionHandler.close();
			try (Socket socket = new Socket("localhost", this.socketPort)) {
				socket.close();
			} catch (IOException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
			try {
				this.connectionHandler.join();
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

	public String getExternalIp()
	{
		return this.externalIp;
	}

	public void setExternalIp(String externalIp)
	{
		this.externalIp = externalIp;
	}

	public int getSocketPort()
	{
		return this.socketPort;
	}

	public ConnectionHandler getConnectionHandler()
	{
		return this.connectionHandler;
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
