package it.polimi.ingsw.lim.server;

import javafx.stage.Stage;

public interface IInterfaceHandler
{
	void start();

	void start(Stage stage);

	void stop();

	void setupSuccess(int rmiPort, int socketPort);

	void setupError();

	void displayToLog(String text);

	void handleCommandExecuted();
}
