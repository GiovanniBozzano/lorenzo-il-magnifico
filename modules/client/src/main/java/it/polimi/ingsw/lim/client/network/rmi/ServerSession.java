package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerData;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class ServerSession extends UnicastRemoteObject implements IServerSession
{
	ServerSession() throws RemoteException
	{
		super();
	}

	@Override
	public void sendDisconnect() throws RemoteException
	{
		Client.getInstance().disconnect(false, false);
	}

	@Override
	public void sendHeartbeat() throws RemoteException
	{
		// This method is empty because it is only called to check the connection.
	}

	@Override
	public void sendRoomEntryOther(String name) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleRoomEntryOther(name);
	}

	@Override
	public void sendRoomExitOther(String name) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleRoomExitOther(name);
	}

	@Override
	public void sendRoomTimer(int timer) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleRoomTimer(timer);
	}

	@Override
	public void sendLogMessage(String text) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleLogMessage(text);
	}

	@Override
	public void sendChatMessage(String text) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleChatMessage(text);
	}

	@Override
	public void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerData> playersData) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameStarted(excommunicationTiles, playersData);
	}

	@Override
	public void sendGameUpdate(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<AvailableAction> availableActions) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameUpdate(gameInformations, playersInformations, availableActions);
	}

	@Override
	public void sendGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, ExpectedAction expectedAction) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameUpdateExpectedAction(gameInformations, playersInformations, expectedAction);
	}

	@Override
	public void sendGameUpdateOtherTurn(GameInformations gameInformations, List<PlayerInformations> playersInformations) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameUpdateOtherTurn(gameInformations, playersInformations);
	}
}
