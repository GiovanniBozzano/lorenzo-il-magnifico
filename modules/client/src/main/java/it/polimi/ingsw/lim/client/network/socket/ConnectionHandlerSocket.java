package it.polimi.ingsw.lim.client.network.socket;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.client.gui.ControllerAuthentication;
import it.polimi.ingsw.lim.client.gui.ControllerGame;
import it.polimi.ingsw.lim.client.gui.ControllerRoom;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformations;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.network.AuthenticationInformations;
import it.polimi.ingsw.lim.common.network.AuthenticationInformationsGame;
import it.polimi.ingsw.lim.common.network.socket.AuthenticationInformationsLobbySocket;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.*;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ConnectionHandlerSocket extends ConnectionHandler
{
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private PacketListener packetListener;

	@Override
	public void run()
	{
		try {
			this.socket = new Socket(Client.getInstance().getIp(), Client.getInstance().getPort());
			this.socket.setSoTimeout(12000);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.in = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException exception) {
			Client.getDebugger().log(Level.OFF, "Could not connect to host.", exception);
			ConnectionHandler.handleConnectionError();
			return;
		}
		this.packetListener = new PacketListener();
		this.packetListener.start();
		this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
		ConnectionHandler.handleConnectionSuccess();
	}

	@Override
	public synchronized void disconnect(boolean notifyServer)
	{
		super.disconnect(notifyServer);
		if (this.packetListener != null) {
			this.packetListener.end();
		}
		try {
			if (this.in != null) {
				this.in.close();
			}
			if (this.out != null) {
				this.out.close();
			}
			if (this.socket != null) {
				this.socket.close();
			}
		} catch (IOException exception) {
			Client.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public synchronized void sendHeartbeat()
	{
		super.sendHeartbeat();
		new Packet(PacketType.HEARTBEAT).send(this.out);
	}

	@Override
	public synchronized void sendLogin(String username, String password, RoomType roomType)
	{
		super.sendLogin(username, password, roomType);
		new PacketLogin(username, CommonUtils.encrypt(password), roomType).send(this.out);
	}

	@Override
	public synchronized void sendRegistration(String username, String password, RoomType roomType)
	{
		super.sendRegistration(username, password, roomType);
		new PacketRegistration(username, CommonUtils.encrypt(password), roomType).send(this.out);
	}

	private synchronized void sendDisconnectionAcknowledgement()
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		new Packet(PacketType.DISCONNECTION_ACKNOWLEDGEMENT).send(this.out);
	}

	@Override
	public synchronized void sendRoomTimerRequest()
	{
		super.sendRoomTimerRequest();
		new Packet(PacketType.ROOM_TIMER_REQUEST).send(this.out);
	}

	@Override
	public synchronized void sendChatMessage(String text)
	{
		super.sendChatMessage(text);
		new PacketChatMessage(text).send(this.out);
	}

	@Override
	public synchronized void sendGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex)
	{
		super.sendGamePersonalBonusTilePlayerChoice(personalBonusTileIndex);
		new PacketGamePersonalBonusTilePlayerChoice(personalBonusTileIndex).send(this.out);
	}

	@Override
	public synchronized void sendGameLeaderCardPlayerChoice(int leaderCardIndex)
	{
		super.sendGameLeaderCardPlayerChoice(leaderCardIndex);
		new PacketGameLeaderCardPlayerChoice(leaderCardIndex).send(this.out);
	}

	@Override
	public synchronized void sendGameExcommunicationPlayerChoice(boolean excommunicated)
	{
		super.sendGameExcommunicationPlayerChoice(excommunicated);
		new PacketGameExcommunicationPlayerChoice(excommunicated).send(this.out);
	}

	@Override
	public synchronized void sendGameAction(ActionInformations action)
	{
		super.sendGameAction(action);
		new PacketGameAction(action).send(this.out);
	}

	@Override
	public synchronized void sendGoodGame(int playerIndex)
	{
		super.sendGoodGame(playerIndex);
		new PacketGoodGame(playerIndex).send(this.out);
	}

	@Override
	public void handleDisconnectionLogMessage(String text)
	{
		super.handleDisconnectionLogMessage(text);
		this.sendDisconnectionAcknowledgement();
	}

	void handleAuthenticationConfirmation(AuthenticationInformations authenticationInformations)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerAuthentication.class)) {
			return;
		}
		GameStatus.getInstance().setup(authenticationInformations.getDevelopmentCardsBuildingInformations(), authenticationInformations.getDevelopmentCardsCharacterInformations(), authenticationInformations.getDevelopmentCardsTerritoryInformations(), authenticationInformations.getDevelopmentCardsVentureInformations(), authenticationInformations.getLeaderCardsInformations(), authenticationInformations.getExcommunicationTilesInformations(), authenticationInformations.getPersonalBonusTilesInformations());
		if (!authenticationInformations.isGameStarted()) {
			Client.getInstance().setUsername(((AuthenticationInformationsLobbySocket) authenticationInformations).getUsername());
			if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
				WindowFactory.getInstance().setNewWindow(Utils.SCENE_ROOM, () -> Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).setRoomInformations(((AuthenticationInformationsLobbySocket) authenticationInformations).getRoomInformations().getRoomType(), ((AuthenticationInformationsLobbySocket) authenticationInformations).getRoomInformations().getPlayerNames())));
			} else {
				Client.getLogger().log(Level.INFO, "Waiting for other players...");
			}
		} else {
			GameStatus.getInstance().setCurrentExcommunicationTiles(((AuthenticationInformationsGame) authenticationInformations).getExcommunicationTiles());
			GameStatus.getInstance().setCurrentCouncilPrivilegeRewards(((AuthenticationInformationsGame) authenticationInformations).getCouncilPrivilegeRewards());
			Map<Integer, PlayerData> playersData = new HashMap<>();
			for (Entry<Integer, PlayerIdentification> entry : ((AuthenticationInformationsGame) authenticationInformations).getPlayersIdentifications().entrySet()) {
				playersData.put(entry.getKey(), new PlayerData(entry.getValue().getUsername(), entry.getValue().getColor()));
			}
			GameStatus.getInstance().setCurrentPlayerData(playersData);
			GameStatus.getInstance().setOwnPlayerIndex(((AuthenticationInformationsGame) authenticationInformations).getOwnPlayerIndex());
			if (((AuthenticationInformationsGame) authenticationInformations).isGameInitialized()) {
				GameStatus.getInstance().updateGameStatus(((AuthenticationInformationsGame) authenticationInformations).getGameInformations(), ((AuthenticationInformationsGame) authenticationInformations).getPlayersInformations(), ((AuthenticationInformationsGame) authenticationInformations).getOwnLeaderCardsHand());
				WindowFactory.getInstance().setNewWindow(Utils.SCENE_GAME, () -> {
					if (((AuthenticationInformationsGame) authenticationInformations).getTurnPlayerIndex() != ((AuthenticationInformationsGame) authenticationInformations).getOwnPlayerIndex()) {
						GameStatus.getInstance().setCurrentTurnPlayerIndex(((AuthenticationInformationsGame) authenticationInformations).getTurnPlayerIndex());
						if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
							Platform.runLater(((ControllerGame) WindowFactory.getInstance().getCurrentWindow())::setOtherTurn);
						} else {
							Client.getLogger().log(Level.INFO, "{0}'s turn...", new Object[] { GameStatus.getInstance().getCurrentPlayersData().get(((AuthenticationInformationsGame) authenticationInformations).getTurnPlayerIndex()).getUsername() });
						}
					} else {
						GameStatus.getInstance().setCurrentAvailableActions(((AuthenticationInformationsGame) authenticationInformations).getAvailableActions());
						if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
							Platform.runLater(((ControllerGame) WindowFactory.getInstance().getCurrentWindow())::setOwnTurn);
						} else {
							Client.getLogger().log(Level.INFO, "Your turn...");
						}
					}
				});
			} else {
				WindowFactory.getInstance().setNewWindow(Utils.SCENE_GAME);
			}
		}
	}

	ObjectInputStream getIn()
	{
		return this.in;
	}
}
