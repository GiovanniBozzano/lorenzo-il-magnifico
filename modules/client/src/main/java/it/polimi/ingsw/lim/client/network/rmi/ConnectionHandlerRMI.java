package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
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
		this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
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
			Client.getLogger().log(Level.INFO, exception.getLocalizedMessage(), exception);
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
			return;
		}
		Client.getInstance().setUsername(username);
		CommonUtils.setNewWindow(Utils.SCENE_LOBBY, null, null, new Thread(this::sendRequestRoomList));
	}

	@Override
	public synchronized void sendRegistration(String username, String password)
	{
		super.sendRegistration(username, password);
		try {
			this.clientSession = this.login.sendRegistration(username, CommonUtils.encrypt(password), CommonUtils.VERSION, this.serverSession);
		} catch (RemoteException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			Client.getInstance().disconnect(false, true);
			return;
		} catch (AuthenticationFailedException exception) {
			Client.getLogger().log(Level.INFO, exception.getLocalizedMessage(), exception);
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
			return;
		}
		Client.getInstance().setUsername(username);
		CommonUtils.setNewWindow(Utils.SCENE_LOBBY, null, null, new Thread(this::sendRequestRoomList));
	}

	@Override
	public synchronized void sendHeartbeat()
	{
		try {
			if (this.clientSession != null) {
				this.clientSession.sendHeartbeat();
			} else {
				this.login.sendHeartbeat();
			}
		} catch (RemoteException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			Client.getInstance().disconnect(false, true);
		}
	}

	@Override
	public synchronized void sendRequestRoomList()
	{
		try {
			this.clientSession.sendRoomListRequest();
		} catch (RemoteException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			Client.getInstance().disconnect(false, true);
		}
	}

	@Override
	public synchronized void sendRoomCreation(String name)
	{
		super.sendRoomCreation(name);
		try {
			this.clientSession.sendRoomCreation(name);
		} catch (RemoteException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			Client.getInstance().disconnect(false, true);
		}
	}

	@Override
	public synchronized void sendRoomEntry(int id)
	{
		super.sendRoomEntry(id);
		try {
			this.clientSession.sendRoomEntry(id);
		} catch (RemoteException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			Client.getInstance().disconnect(false, true);
		}
	}

	@Override
	public synchronized void sendRoomExit()
	{
		try {
			this.clientSession.sendRoomExit();
		} catch (RemoteException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			Client.getInstance().disconnect(false, true);
		}
		super.sendRoomExit();
	}

	@Override
	public synchronized void sendChatMessage(String text)
	{
		try {
			this.clientSession.sendChatMessage(text);
		} catch (RemoteException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			Client.getInstance().disconnect(false, true);
		}
	}
}
