package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardProductionStart;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.player.Player;

public class ActionRewardProductionStart extends ActionReward
{
	private final int value;
	private final boolean applyModifiers;

	public ActionRewardProductionStart(String description, int value, boolean applyModifiers)
	{
		super(description, ActionType.CHOOSE_REWARD_PRODUCTION_START);
		this.value = value;
		this.applyModifiers = applyModifiers;
	}

	@Override
	public ExpectedAction createExpectedAction(GameHandler gameHandler, Player player)
	{
		return new ExpectedActionChooseRewardProductionStart(this.value);
	}

	public int getValue()
	{
		return this.value;
	}

	public boolean isApplyModifiers()
	{
		return this.applyModifiers;
	}
}