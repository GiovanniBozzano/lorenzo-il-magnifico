package it.polimi.ingsw.lim.server.network;

import it.polimi.ingsw.lim.common.utils.RoomInformations;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public interface IConnection
{
	void disconnect(boolean flag, String message);

	void sendHeartbeat();

	void sendRoomList(List<RoomInformations> rooms);

	void sendRoomCreationFailure();

	void sendRoomEntryConfirmation(RoomInformations roomInformations);

	void sendRoomEntryOther(String name);

	void sendRoomExitOther(String name);

	void sendLogMessage(String text);

	void sendChatMessage(String text);

	void handleRoomListRequest();

	void handleRoomCreation(String name);

	void handleRoomEntry(int id);

	void handleRoomExit();

	void handleChatMessage(String text);

	int getId();

	String getUsername();

	void setUsername(String name);

	ScheduledExecutorService getHeartbeat();
}
