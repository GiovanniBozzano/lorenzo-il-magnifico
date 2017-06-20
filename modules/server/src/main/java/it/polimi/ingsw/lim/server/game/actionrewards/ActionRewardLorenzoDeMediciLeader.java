package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseLorenzoDeMediciLeader;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.player.Player;

import java.util.HashMap;
import java.util.Map;

public class ActionRewardLorenzoDeMediciLeader extends ActionReward
{
	public ActionRewardLorenzoDeMediciLeader(String description)
	{
		super(description, ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER);
	}

	@Override
	public ExpectedAction createExpectedAction(GameHandler gameHandler, Player player)
	{
		Map<Integer, Integer> availableLeaderCards = new HashMap<>();
		for (Player otherPlayer : gameHandler.getTurnOrder()) {
			if (otherPlayer != player) {
				for (LeaderCard leaderCard : otherPlayer.getPlayerCardHandler().getLeaderCards()) {
					if (leaderCard.isPlayed()) {
						availableLeaderCards.put(otherPlayer.getIndex(), leaderCard.getIndex());
					}
				}
			}
		}
		return new ExpectedActionChooseLorenzoDeMediciLeader(availableLeaderCards);
	}
}
