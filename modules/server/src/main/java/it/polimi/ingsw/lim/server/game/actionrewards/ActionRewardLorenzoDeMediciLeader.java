package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseLorenzoDeMediciLeader;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionRewardLorenzoDeMediciLeader extends ActionReward
{
	public ActionRewardLorenzoDeMediciLeader(String description)
	{
		super(description, ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER);
	}

	@Override
	public ExpectedAction createExpectedAction(Player player)
	{
		Map<Integer, List<Integer>> availableLeaderCards = new HashMap<>();
		for (Player otherPlayer : player.getRoom().getGameHandler().getTurnOrder()) {
			if (otherPlayer != player) {
				for (LeaderCard leaderCard : otherPlayer.getPlayerCardHandler().getLeaderCards()) {
					if (leaderCard.isPlayed()) {
						if (!availableLeaderCards.containsKey(otherPlayer.getIndex())) {
							availableLeaderCards.put(otherPlayer.getIndex(), new ArrayList<>());
						}
						availableLeaderCards.get(otherPlayer.getIndex()).add(leaderCard.getIndex());
					}
				}
			}
		}
		return new ExpectedActionChooseLorenzoDeMediciLeader(availableLeaderCards);
	}
}
