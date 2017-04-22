package it.polimi.ingsw.lim.client.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.rmi.IServerSession;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.Unreferenced;
import java.util.logging.Level;

public class ServerSession extends UnicastRemoteObject implements IServerSession, Unreferenced
{
	public ServerSession() throws RemoteException
	{
	}

	@Override
	public void unreferenced()
	{
		Client.getLogger().log(Level.INFO, "The connection has been closed.");
		Client.setInstance(null);
		Platform.runLater(() -> {
			ObservableList<Node> children = Client.getInstance().getStage().getScene().getRoot().getChildrenUnmodifiable();
			children.get(0).setDisable(false);
			((Button) ((HBox) children.get(1)).getChildren().get(0)).setText("CONNECT");
			((Button) ((HBox) children.get(1)).getChildren().get(0)).setOnAction((ActionEvent event) -> new Client(Client.getInstance().getStage(), ConnectionType.RMI, ((TextField) ((GridPane) children.get(0)).getChildren().get(1)).getText(), Integer.parseInt(((TextField) ((GridPane) children.get(0)).getChildren().get(3)).getText()), ((TextField) ((GridPane) children.get(0)).getChildren().get(5)).getText()));
		});
	}

	@Override
	public void disconnect() throws RemoteException
	{
		Client.getInstance().disconnect();
	}

	@Override
	public void sendLogMessage(String text) throws RemoteException
	{
		Client.getLogger().log(Level.INFO, text);
	}

	@Override
	public void sendChatMessage(String text) throws RemoteException
	{
		Client.getLogger().log(Level.INFO, text);
	}
}
