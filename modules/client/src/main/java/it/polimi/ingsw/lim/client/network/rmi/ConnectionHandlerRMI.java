package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.network.rmi.IAuthentication;
import it.polimi.ingsw.lim.common.network.rmi.IClientSession;
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
	private IAuthentication login;
	private IClientSession clientSession;

	@Override
	public void run()
	{
		try {
			this.login = (IAuthentication) Naming.lookup("rmi://" + Client.getInstance().getIp() + ":" + Client.getInstance().getPort() + "/lorenzo-il-magnifico");
			this.serverSession = new ServerSession();
		} catch (NotBoundException | MalformedURLException | RemoteException exception) {
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
			Client.getLogger().log(Level.INFO, "Could not connect to host", exception);
			return;
		}
		this.getHeartbeat().scheduleAtFixedRate(Connection::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
		CommonUtils.setNewWindow(Utils.SCENE_AUTHENTICATION, null, null, null);
	}

	@Override
	public void disconnect(boolean notifyServer)
	{
		super.disconnect(notifyServer);
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

	@Override
	public synchronized void sendLogin(String username, String password)
	{
		super.sendLogin(username, password);
		try {
			this.clientSession = this.login.sendLogin(username, CommonUtils.encrypt(password), CommonUtils.VERSION, this.serverSession);
		} catch (RemoteException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			Client.getInstance().disconnect(false, true);
			return;
		} catch (AuthenticationFailedException exception) {
			Client.getLogger().log(Level.INFO, exception.getMessage(), exception);
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
		}
		Client.getInstance().setUsername(username);
		CommonUtils.setNewWindow(Utils.SCENE_LOBBY, null, null, new Thread(Connection::sendRequestRoomList));
	}

	public ServerSession getServerSession()
	{
		return this.serverSession;
	}

	public IAuthentication getLogin()
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
