package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.network.AuthenticationInformations;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.server.*;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
			Utils.displayToLog("Socket connection accepted from: " + socket.getInetAddress().getHostAddress());
		} catch (IOException exception) {
			Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			this.disconnect(true, null);
		}
	}

	@Override
	public synchronized void disconnect(boolean waitPacketListener, String message)
	{
		super.disconnect(waitPacketListener, message);
		if (message != null) {
			this.sendDisconnectionLogMessage(message);
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
		Utils.displayToLog("Socket Player" + (this.getUsername() != null ? " " + this.getUsername() : "") + " disconnected.");
	}

	@Override
	public void sendHeartbeat()
	{
		new Packet(PacketType.HEARTBEAT).send(this.out);
	}

	void sendAuthenticationConfirmation(AuthenticationInformations authenticationInformations)
	{
		new PacketAuthenticationConfirmation(authenticationInformations).send(this.out);
	}

	void sendAuthenticationFailure(String text)
	{
		new PacketAuthenticationFailure(text).send(this.out);
	}

	@Override
	public void sendRoomEntryOther(String name)
	{
		new PacketRoomEntryOther(name).send(this.out);
	}

	@Override
	public void sendRoomExitOther(String name)
	{
		new PacketRoomExitOther(name).send(this.out);
	}

	@Override
	public void sendRoomTimer(int timer)
	{
		new PacketRoomTimer(timer).send(this.out);
	}

	@Override
	public void sendLogMessage(String text)
	{
		new PacketLogMessage(text).send(this.out);
	}

	private void sendDisconnectionLogMessage(String text)
	{
		new PacketDisconnectionLogMessage(text).send(this.out);
	}

	@Override
	public void sendChatMessage(String text)
	{
		new PacketChatMessage(text.replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "")).send(this.out);
	}

	@Override
	public void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerIdentification> playersData, int ownPlayerIndex)
	{
		new PacketGameStarted(excommunicationTiles, playersData, ownPlayerIndex).send(this.out);
	}

	@Override
	public void sendGameDisconnectionOther(int playerIndex)
	{
		new PacketGameDisconnectionOther(playerIndex).send(this.out);
	}

	@Override
	public void sendGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTiles)
	{
		new PacketGamePersonalBonusTileChoiceRequest(availablePersonalBonusTiles).send(this.out);
	}

	@Override
	public void sendGamePersonalBonusTileChoiceOther(int choicePlayerIndex)
	{
		new PacketGamePersonalBonusTileChoiceOther(choicePlayerIndex).send(this.out);
	}

	@Override
	public void sendGamePersonalBonusTileChosen(int choicePlayerIndex)
	{
		new PacketGamePersonalBonusTileChosen(choicePlayerIndex).send(this.out);
	}

	@Override
	public void sendGameLeaderCardChoiceRequest(List<Integer> availableLeaderCards)
	{
		new PacketGameLeaderCardChoiceRequest(availableLeaderCards).send(this.out);
	}

	@Override
	public void sendGameLeaderCardChosen(int choicePlayerIndex, boolean closeDialog)
	{
		new PacketGameLeaderCardChosen(choicePlayerIndex, closeDialog).send(this.out);
	}

	@Override
	public void sendGameUpdate(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<AvailableAction> availableActions)
	{
		new PacketGameUpdate(gameInformations, playersInformations, availableActions).send(this.out);
	}

	@Override
	public void sendGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, ExpectedAction expectedAction)
	{
		new PacketGameUpdateExpectedAction(gameInformations, playersInformations, expectedAction).send(this.out);
	}

	@Override
	public void sendGameUpdateOtherTurn(GameInformations gameInformations, List<PlayerInformations> playersInformations, int turnPlayerIndex)
	{
		new PacketGameUpdateOtherTurn(gameInformations, playersInformations, turnPlayerIndex).send(this.out);
	}

	ObjectInputStream getIn()
	{
		return this.in;
	}
}
