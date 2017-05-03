package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.utils.RoomInformations;

public interface IConnection
{
	void disconnect();

	void sendRoomList();

	void sendRoomEntryConfirmation(RoomInformations roomInformations);

	void sendRoomCreationConfirmation(RoomInformations roomInformations);

	void sendLogMessage(String text);

	void sendChatMessage(String text);

	void handleRequestRoomList();

	void handleRoomCreation(String name);

	void handleRoomEntry(int id);

	void handleRoomExit();

	void handleChatMessage(String text);

	int getId();

	String getName();
}
