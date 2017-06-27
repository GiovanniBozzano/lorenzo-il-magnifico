package it.polimi.ingsw.lim.server.network;

import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.gui.ControllerMain;
import it.polimi.ingsw.lim.server.network.rmi.Authentication;
import it.polimi.ingsw.lim.server.network.socket.ConnectionSocket;
import it.polimi.ingsw.lim.server.utils.Utils;
import javafx.application.Platform;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;

public class ConnectionHandler extends Thread
{
	private final int rmiPort;
	private final int socketPort;
	private Registry registry;
	private Authentication login;
	private volatile boolean keepGoing = true;

	public ConnectionHandler(int rmiPort, int socketPort)
	{
		this.setName("ConnectionHandler");
		this.rmiPort = rmiPort;
		this.socketPort = socketPort;
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
			WindowFactory.getInstance().enableWindow();
			return;
		}
		try (ServerSocket serverSocket = new ServerSocket(this.socketPort)) {
			WindowFactory.getInstance().setNewWindow(Utils.SCENE_MAIN, () -> {
				Utils.displayToLog("Server waiting on RMI port " + this.rmiPort + " and Socket port " + this.socketPort);
				Server.getInstance().setExternalIp(Utils.getExternalIpAddress());
				Platform.runLater(() -> ((ControllerMain) WindowFactory.getInstance().getCurrentWindow()).getConnectionLabel().setText(Server.getInstance().getExternalIp() == null ? "External IP: Offline, RMI port: " + Server.getInstance().getRmiPort() + ", Socket port: " + Server.getInstance().getSocketPort() : "External IP: " + Server.getInstance().getExternalIp() + ", RMI port: " + Server.getInstance().getRmiPort() + ", Socket port: " + Server.getInstance().getSocketPort()));
				if (Server.getInstance().getExternalIp() != null) {
					Utils.displayToLog("Your external IP address is: " + Server.getInstance().getExternalIp());
				}
			});
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
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			try {
				this.registry.unbind("lorenzo-il-magnifico");
				UnicastRemoteObject.unexportObject(this.registry, true);
				UnicastRemoteObject.unexportObject(this.login, true);
			} catch (RemoteException | NotBoundException nestedException) {
				Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, nestedException);
			}
			WindowFactory.getInstance().enableWindow();
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
}
