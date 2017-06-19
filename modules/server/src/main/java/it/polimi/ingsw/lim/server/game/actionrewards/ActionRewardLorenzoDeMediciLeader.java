package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseLorenzoDeMediciLeader;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.HashMap;
import java.util.Map;

public class ActionRewardLorenzoDeMediciLeader extends ActionReward
{
	public ActionRewardLorenzoDeMediciLeader(String description)
	{
		super(description, ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER);
	}

	@Override
	public ExpectedAction createExpectedAction(GameHandler gameHandler, Connection player)
	{
		Map<Integer, Integer> availableLeaderCards = new HashMap<>();
		Room room = Room.getPlayerRoom(player);
		if (room == null) {
			return null;
		}
		for(Connection currentPlayer : room.getPlayers()){
			for(LeaderCard leaderCard : currentPlayer.getPlayerHandler().getPlayerCardHandler().getLeaderCards()){
				if(leaderCard.isPlayed()){
					availableLeaderCards.put(currentPlayer.getPlayerHandler().getIndex(), leaderCard.getIndex());
				}
			}
		}
		return new ExpectedActionChooseLorenzoDeMediciLeader(availableLeaderCards);
	}
}
