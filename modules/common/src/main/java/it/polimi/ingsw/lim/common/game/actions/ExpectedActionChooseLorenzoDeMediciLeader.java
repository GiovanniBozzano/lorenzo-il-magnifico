package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

import java.util.HashMap;
import java.util.Map;

public class ExpectedActionChooseLorenzoDeMediciLeader extends ExpectedAction
{
	private final Map<Integer, Integer> availableLeaderCards;

	public ExpectedActionChooseLorenzoDeMediciLeader(Map<Integer, Integer> availableLeaderCards)
	{
		super(ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER);
		this.availableLeaderCards = new HashMap<>(availableLeaderCards);
	}

	public Map<Integer, Integer> getAvailableLeaderCards()
	{
		return this.availableLeaderCards;
	}
}
