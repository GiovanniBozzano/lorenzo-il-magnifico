package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformation;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformation;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.network.AuthenticationInformation;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.server.*;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.network.Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ConnectionSocket extends Connection
{
	private final Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private PacketListener packetListener;

	public ConnectionSocket(Socket socket)
	{
		this.socket = socket;
		try {
			this.socket.setSoTimeout(12000);
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.out.flush();
			this.packetListener = new PacketListener(this);
			this.packetListener.start();
			this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
			Server.getInstance().getInterfaceHandler().displayToLog("Socket connection accepted from: " + socket.getInetAddress().getHostAddress());
		} catch (IOException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			this.disconnect(true, null);
		}
	}

	@Override
	public synchronized void sendChatMessage(String text)
	{
		new PacketChatMessage(text.replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "")).send(this.out);
	}

	@Override
	public synchronized void disconnect(boolean waitPacketListener, String text)
	{
		super.disconnect(waitPacketListener, text);
		if (text != null) {
			this.sendDisconnectionLogMessage(text);
			try {
				this.socket.setSoTimeout(3000);
				Packet packet;
				do {
					packet = (Packet) this.in.readObject();
				}
				while (packet.getPacketType() != PacketType.DISCONNECTION_ACKNOWLEDGEMENT);
			} catch (IOException | ClassNotFoundException exception) {
				Server.getDebugger().log(Level.INFO, "Connection acknowledgement failed.", exception);
			}
		}
		if (waitPacketListener) {
			this.packetListener.end();
		}
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
		if (waitPacketListener) {
			try {
				this.packetListener.join();
			} catch (InterruptedException exception) {
				Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				Thread.currentThread().interrupt();
			}
		}
		Server.getInstance().getInterfaceHandler().displayToLog("Socket Player" + (this.getUsername() != null ? ' ' + this.getUsername() : "") + " disconnected.");
	}

	@Override
	public synchronized void sendHeartbeat()
	{
		new Packet(PacketType.HEARTBEAT).send(this.out);
	}

	@Override
	public synchronized void sendRoomEntryOther(String name)
	{
		new PacketRoomEntryOther(name).send(this.out);
	}

	@Override
	public synchronized void sendRoomExitOther(String name)
	{
		new PacketRoomExitOther(name).send(this.out);
	}

	@Override
	public synchronized void sendRoomTimer(int timer)
	{
		new PacketRoomTimer(timer).send(this.out);
	}

	@Override
	public synchronized void sendDisconnectionLogMessage(String text)
	{
		new PacketDisconnectionLogMessage(text).send(this.out);
	}

	@Override
	public synchronized void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, List<ResourceAmount>> councilPrivilegeRewards, Map<Integer, PlayerIdentification> playersData, int ownPlayerIndex)
	{
		new PacketGameStarted(excommunicationTiles, councilPrivilegeRewards, playersData, ownPlayerIndex).send(this.out);
	}

	@Override
	public synchronized void sendGameLogMessage(String text)
	{
		new PacketGameLogMessage(text).send(this.out);
	}

	@Override
	public synchronized void sendGameTimer(int timer)
	{
		new PacketGameTimer(timer).send(this.out);
	}

	@Override
	public synchronized void sendGameDisconnectionOther(int playerIndex)
	{
		new PacketGameDisconnectionOther(playerIndex).send(this.out);
	}

	@Override
	public synchronized void sendGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTiles)
	{
		new PacketGamePersonalBonusTileChoiceRequest(availablePersonalBonusTiles).send(this.out);
	}

	@Override
	public synchronized void sendGamePersonalBonusTileChoiceOther(int choicePlayerIndex)
	{
		new PacketGamePersonalBonusTileChoiceOther(choicePlayerIndex).send(this.out);
	}

	@Override
	public synchronized void sendGamePersonalBonusTileChosen(int choicePlayerIndex)
	{
		new PacketGamePersonalBonusTileChosen(choicePlayerIndex).send(this.out);
	}

	@Override
	public synchronized void sendGameLeaderCardChoiceRequest(List<Integer> availableLeaderCards)
	{
		new PacketGameLeaderCardChoiceRequest(availableLeaderCards).send(this.out);
	}

	@Override
	public synchronized void sendGameExcommunicationChoiceRequest(Period period)
	{
		new PacketGameExcommunicationChoiceRequest(period).send(this.out);
	}

	@Override
	public synchronized void sendGameExoommunicationChoiceOther()
	{
		new Packet(PacketType.GAME_EXCOMMUNICATION_CHOICE_OTHER).send(this.out);
	}

	@Override
	public synchronized void sendGameUpdate(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand, Map<ActionType, List<Serializable>> availableActions)
	{
		new PacketGameUpdate(gameInformation, playersInformation, ownLeaderCardsHand, availableActions).send(this.out);
	}

	@Override
	public synchronized void sendGameUpdateExpectedAction(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand, ExpectedAction expectedAction)
	{
		new PacketGameUpdateExpectedAction(gameInformation, playersInformation, ownLeaderCardsHand, expectedAction).send(this.out);
	}

	@Override
	public synchronized void sendGameUpdateOtherTurn(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand, int turnPlayerIndex)
	{
		new PacketGameUpdateOtherTurn(gameInformation, playersInformation, ownLeaderCardsHand, turnPlayerIndex).send(this.out);
	}

	@Override
	public synchronized void sendGameEnded(Map<Integer, Integer> playersScores, Map<Integer, Integer> playerIndexesVictoryPointsRecord)
	{
		new PacketGameEnded(playersScores, playerIndexesVictoryPointsRecord).send(this.out);
	}

	synchronized void sendAuthenticationConfirmation(AuthenticationInformation authenticationInformation)
	{
		new PacketAuthenticationConfirmation(authenticationInformation).send(this.out);
	}

	synchronized void sendAuthenticationFailure(String text)
	{
		new PacketAuthenticationFailure(text).send(this.out);
	}

	synchronized void sendGameActionFailed(String text)
	{
		new PacketGameActionFailed(text).send(this.out);
	}

	ObjectInputStream getIn()
	{
		return this.in;
	}
}
