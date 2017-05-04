package it.polimi.ingsw.lim.common.rmi;

import it.polimi.ingsw.lim.common.utils.RoomInformations;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerSession extends Remote
{
	void sendRoomList(List<RoomInformations> rooms) throws RemoteException;

	void sendRoomCreationFailure() throws RemoteException;

	void sendRoomEntryConfirmation(RoomInformations roomInformations) throws RemoteException;

	void sendRoomEntryOther(String name) throws RemoteException;

	void sendRoomExitOther(String name) throws RemoteException;

	void sendLogMessage(String text) throws RemoteException;

	void sendChatMessage(String text) throws RemoteException;
}
