package it.polimi.ingsw.lim.server;

public interface IConnection
{
	void disconnect();

	void sendRoomList();

	void sendLogMessage(String text);

	void sendChatMessage(String text);

	void handleRequestRoomList();

	void handleRoomEntry(int id);

	void handleRoomExit();

	void handleChatMessage(String text);

	int getId();

	String getName();
}
