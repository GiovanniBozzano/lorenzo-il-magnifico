package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.gui.ControllerAuthentication;
import it.polimi.ingsw.lim.client.gui.ControllerConnection;
import it.polimi.ingsw.lim.client.gui.ControllerRoom;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.network.rmi.IAuthentication;
import it.polimi.ingsw.lim.common.network.rmi.IClientSession;
import it.polimi.ingsw.lim.common.utils.AuthenticationInformations;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import javafx.application.Platform;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
			this.serverSession = new ServerSession();
			this.login = (IAuthentication) Naming.lookup("rmi://" + Client.getInstance().getIp() + ":" + Client.getInstance().getPort() + "/lorenzo-il-magnifico");
		} catch (NotBoundException | MalformedURLException | RemoteException exception) {
			Client.getLogger().log(Level.INFO, "Could not connect to host.", exception);
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
			Platform.runLater(() -> {
				Client.getInstance().getWindowInformations().getStage().getScene().getRoot().requestFocus();
				((ControllerConnection) Client.getInstance().getWindowInformations().getController()).showDialog("Could not connect to host");
			});
			return;
		}
		this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
		CommonUtils.setNewWindow(Utils.SCENE_AUTHENTICATION, null);
	}

	@Override
	public void disconnect(boolean notifyServer)
	{
		super.disconnect(notifyServer);
		if (notifyServer && this.clientSession != null) {
			try {
				this.clientSession.sendDisconnect();
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
	}

	@Override
	public synchronized void sendLogin(String username, String password, RoomType roomType)
	{
		super.sendLogin(username, password, roomType);
		try {
			AuthenticationInformations authenticationInformations = this.login.sendLogin(CommonUtils.VERSION, username, CommonUtils.encrypt(password), roomType, this.serverSession);
			this.clientSession = authenticationInformations.getClientSession();
			Client.getInstance().setUsername(username);
			CommonUtils.setNewWindow(Utils.SCENE_ROOM, null, new Thread(() -> Platform.runLater(() -> ((ControllerRoom) Client.getInstance().getWindowInformations().getController()).setRoomInformations(authenticationInformations.getRoomInformations().getRoomType(), authenticationInformations.getRoomInformations().getPlayerNames()))));
		} catch (RemoteException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			Client.getInstance().disconnect(false, true);
		} catch (AuthenticationFailedException exception) {
			Client.getLogger().log(Level.INFO, exception.getLocalizedMessage(), exception);
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
			Platform.runLater(() -> ((ControllerAuthentication) Client.getInstance().getWindowInformations().getController()).showDialog(exception.getLocalizedMessage()));
		}
	}

	@Override
	public synchronized void sendRegistration(String username, String password, RoomType roomType)
	{
		super.sendRegistration(username, password, roomType);
		try {
			AuthenticationInformations authenticationInformations = this.login.sendRegistration(CommonUtils.VERSION, username, CommonUtils.encrypt(password), roomType, this.serverSession);
			this.clientSession = authenticationInformations.getClientSession();
			Client.getInstance().setUsername(username);
			CommonUtils.setNewWindow(Utils.SCENE_ROOM, null, new Thread(() -> Platform.runLater(() -> ((ControllerRoom) Client.getInstance().getWindowInformations().getController()).setRoomInformations(authenticationInformations.getRoomInformations().getRoomType(), authenticationInformations.getRoomInformations().getPlayerNames()))));
		} catch (RemoteException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			Client.getInstance().disconnect(false, true);
		} catch (AuthenticationFailedException exception) {
			Client.getLogger().log(Level.INFO, exception.getLocalizedMessage(), exception);
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
			Platform.runLater(() -> ((ControllerAuthentication) Client.getInstance().getWindowInformations().getController()).showDialog(exception.getLocalizedMessage()));
		}
	}

	@Override
	public synchronized void sendHeartbeat()
	{
		super.sendHeartbeat();
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
	public synchronized void sendChatMessage(String text)
	{
		super.sendChatMessage(text);
		try {
			this.clientSession.sendChatMessage(text);
		} catch (RemoteException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			Client.getInstance().disconnect(false, true);
		}
	}
}
