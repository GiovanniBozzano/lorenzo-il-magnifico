package it.polimi.ingsw.lim.server.view.gui;

import it.polimi.ingsw.lim.common.utils.WindowFactory;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.utils.Utils;
import it.polimi.ingsw.lim.server.view.IInterfaceHandler;
import javafx.application.Platform;
import javafx.stage.Stage;

public class InterfaceHandlerGUI implements IInterfaceHandler
{
	@Override
	public void start()
	{
		// This method is empty because it is not implemented by the GUI.
	}

	@Override
	public void start(Stage stage)
	{
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_START, () -> {
			Server.getInstance().getCliScanner().close();
			Server.getInstance().getCliListener().shutdownNow();
		}, stage);
	}

	@Override
	public void stop()
	{
		Platform.runLater(() -> WindowFactory.getInstance().closeWindow());
	}

	@Override
	public void setupSuccess(int rmiPort, int socketPort)
	{
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_MAIN, () -> {
			Server.getInstance().getInterfaceHandler().displayToLog("Server waiting on RMI port " + rmiPort + " and Socket port " + socketPort);
			Server.getInstance().setExternalIp(Utils.getExternalIpAddress());
			Platform.runLater(() -> ((ControllerMain) WindowFactory.getInstance().getCurrentWindow()).getConnectionLabel().setText(Server.getInstance().getExternalIp() == null ? "External IP: Offline, RMI port: " + Server.getInstance().getRmiPort() + ", Socket port: " + Server.getInstance().getSocketPort() : "External IP: " + Server.getInstance().getExternalIp() + ", RMI port: " + Server.getInstance().getRmiPort() + ", Socket port: " + Server.getInstance().getSocketPort()));
			if (Server.getInstance().getExternalIp() != null) {
				Server.getInstance().getInterfaceHandler().displayToLog("Your external IP address is: " + Server.getInstance().getExternalIp());
			}
		});
	}

	@Override
	public void setupError()
	{
		WindowFactory.getInstance().enableWindow();
	}

	@Override
	public void displayToLog(String text)
	{
		if (!WindowFactory.getInstance().isWindowOpen(ControllerMain.class)) {
			return;
		}
		Platform.runLater(() -> {
			if (((ControllerMain) WindowFactory.getInstance().getCurrentWindow()).getLogTextArea().getText().length() < 1) {
				((ControllerMain) WindowFactory.getInstance().getCurrentWindow()).getLogTextArea().appendText(text);
			} else {
				((ControllerMain) WindowFactory.getInstance().getCurrentWindow()).getLogTextArea().appendText("\n" + text);
			}
		});
	}

	@Override
	public void handleCommandExecuted()
	{
		// This method is empty because it is not implemented by the GUI.
	}
}
