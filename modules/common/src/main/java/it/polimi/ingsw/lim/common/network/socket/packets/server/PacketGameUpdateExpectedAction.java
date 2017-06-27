package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PacketGameUpdateExpectedAction extends Packet
{
	private final GameInformations gameInformations;
	private final List<PlayerInformations> playersInformations;
	private final Map<Integer, Boolean> ownLeaderCardsHand;
	private final ExpectedAction expectedAction;

	public PacketGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, ExpectedAction expectedAction)
	{
		super(PacketType.GAME_UPDATE_EXPECTED_ACTION);
		this.gameInformations = gameInformations;
		this.playersInformations = new ArrayList<>(playersInformations);
		this.ownLeaderCardsHand = new HashMap<>(ownLeaderCardsHand);
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

	public Map<Integer, Boolean> getOwnLeaderCardsHand()
	{
		return this.ownLeaderCardsHand;
	}

	public ExpectedAction getExpectedAction()
	{
		return this.expectedAction;
	}
}
