package it.polimi.ingsw.lim.server.network;

import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.network.rmi.Authentication;
import it.polimi.ingsw.lim.server.network.socket.ConnectionSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class ConnectionHandler extends Thread
{
	private final int rmiPort;
	private final int socketPort;
	private Registry registry;
	private Authentication login;
	private boolean connected = false;
	private volatile boolean keepGoing = true;

	public ConnectionHandler(int rmiPort, int socketPort)
	{
		this.setName("ConnectionHandler");
		this.rmiPort = rmiPort;
		this.socketPort = socketPort;
	}

	private static void handleSetupError()
	{
		Server.getInstance().getDatabaseSaver().shutdown();
		Server.getInstance().setDatabaseSaver(Executors.newSingleThreadExecutor());
		Server.getInstance().getDatabaseKeeper().shutdownNow();
		Server.getInstance().setDatabaseKeeper(Executors.newSingleThreadScheduledExecutor());
		Server.getInstance().getDatabase().closeConnection();
		Server.getInstance().getInterfaceHandler().setupError();
	}

	@Override
	public void run()
	{
		try {
			this.registry = LocateRegistry.createRegistry(this.rmiPort);
			this.login = new Authentication();
			this.registry.rebind("lorenzo-il-magnifico", this.login);
		} catch (RemoteException exception) {
			Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			ConnectionHandler.handleSetupError();
			return;
		}
		try (ServerSocket serverSocket = new ServerSocket(this.socketPort)) {
			Server.getInstance().getInterfaceHandler().setupSuccess(this.rmiPort, this.socketPort);
			this.connected = true;
			while (this.keepGoing) {
				try {
					Socket socket = serverSocket.accept();
					if (!this.keepGoing) {
						socket.close();
						serverSocket.close();
					} else {
						Server.getInstance().getConnections().add(new ConnectionSocket(socket));
					}
				} catch (IOException exception) {
					Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
					Server.getInstance().stop();
				}
			}
		} catch (IOException exception) {
			Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			try {
				this.registry.unbind("lorenzo-il-magnifico");
				UnicastRemoteObject.unexportObject(this.registry, true);
				UnicastRemoteObject.unexportObject(this.login, true);
			} catch (RemoteException | NotBoundException nestedException) {
				Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, nestedException);
			} finally {
				ConnectionHandler.handleSetupError();
			}
		}
	}

	public synchronized void end()
	{
		this.keepGoing = false;
	}

	public Registry getRegistry()
	{
		return this.registry;
	}

	public Authentication getLogin()
	{
		return this.login;
	}

	public boolean isConnected()
	{
		return this.connected;
	}
}
