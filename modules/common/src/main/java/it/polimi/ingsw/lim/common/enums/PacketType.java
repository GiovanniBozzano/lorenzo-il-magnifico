package it.polimi.ingsw.lim.common.enums;

public enum PacketType
{
	HANDSHAKE,
	DISCONNECTION_LOG_MESSAGE,
	DISCONNECTION_ACKNOWLEDGEMENT,
	LOG_MESSAGE,
	CHAT_MESSAGE,
	HANDSHAKE_CONFIRMATION,
	ROOM_LIST_REQUEST,
	ROOM_LIST,
	ROOM_ENTRY,
	ROOM_ENTRY_CONFIRMATION,
	ROOM_ENTRY_OTHER,
	ROOM_EXIT,
	ROOM_EXIT_OTHER,
	ROOM_CREATION,
	ROOM_CREATION_FAILURE,
	HEARTBEAT
}
