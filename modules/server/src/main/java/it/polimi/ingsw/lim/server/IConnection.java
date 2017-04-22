package it.polimi.ingsw.lim.server;

public interface IConnection
{
	void disconnect();

	void sendLogMessage(String text);

	void sendChatMessage(String text);

	void handleChatMessage(String text);
}
