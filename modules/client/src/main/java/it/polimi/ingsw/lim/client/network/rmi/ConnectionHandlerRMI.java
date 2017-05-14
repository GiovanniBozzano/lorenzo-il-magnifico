package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.common.network.rmi.IClientSession;
import it.polimi.ingsw.lim.common.network.rmi.ILogin;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ConnectionHandlerRMI extends ConnectionHandler
{
	private ServerSession serverSession;
	private ILogin login;
	private IClientSession clientSession;

	@Override
	public void run()
	{
		try {
			this.login = (ILogin) Naming.lookup("rmi://" + Client.getInstance().getIp() + ":" + Client.getInstance().getPort() + "/lorenzo-il-magnifico");
			this.serverSession = new ServerSession();
		} catch (NotBoundException | MalformedURLException | RemoteException exception) {
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
			Client.getLogger().log(Level.INFO, "Could not connect to host", exception);
			return;
		}
		Client.getInstance().setConnected(true);
		this.getHeartbeat().scheduleAtFixedRate(Connection::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
		CommonUtils.setNewWindow("/fxml/SceneLogin.fxml", null, null, null);
	}

	public void disconnect(boolean notifyServer)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		this.getHeartbeat().shutdownNow();
		try {
			UnicastRemoteObject.unexportObject(this.serverSession, true);
		} catch (NoSuchObjectException exception) {
			Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		if (!notifyServer) {
			try {
				if (this.clientSession != null) {
					this.clientSession.sendDisconnect();
				}
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
	}

	public ServerSession getServerSession()
	{
		return this.serverSession;
	}

	public ILogin getLogin()
	{
		return this.login;
	}

	public IClientSession getClientSession()
	{
		return this.clientSession;
	}

	public void setClientSession(IClientSession clientSession)
	{
		this.clientSession = clientSession;
	}
}
