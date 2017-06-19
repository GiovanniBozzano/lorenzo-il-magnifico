package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class PacketGameUpdateExpectedAction extends Packet
{
	private final GameInformations gameInformations;
	private final List<PlayerInformations> playersInformations;
	private final List<Integer> ownLeaderCardsHand;
	private final ExpectedAction expectedAction;

	public PacketGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<Integer> ownLeaderCardsHand, ExpectedAction expectedAction)
	{
		super(PacketType.GAME_UPDATE_EXPECTED_ACTION);
		this.gameInformations = gameInformations;
		this.playersInformations = new ArrayList<>(playersInformations);
		this.ownLeaderCardsHand = new ArrayList<>(ownLeaderCardsHand);
		this.expectedAction = expectedAction;
	}

	public GameInformations getGameInformations()
	{
		return this.gameInformations;
	}

	public List<PlayerInformations> getPlayersInformations()
	{
		return this.playersInformations;
	}

	public List<Integer> getOwnLeaderCardsHand()
	{
		return this.ownLeaderCardsHand;
	}

	public ExpectedAction getExpectedAction()
	{
		return this.expectedAction;
	}
}
