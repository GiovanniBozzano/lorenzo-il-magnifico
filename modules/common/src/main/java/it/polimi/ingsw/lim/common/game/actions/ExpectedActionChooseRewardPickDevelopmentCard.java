package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;

import java.util.ArrayList;
import java.util.List;

public class ExpectedActionChooseRewardPickDevelopmentCard extends ExpectedAction
{
	private final List<AvailableActionChooseRewardPickDevelopmentCard> availableActions;

	public ExpectedActionChooseRewardPickDevelopmentCard(List<AvailableActionChooseRewardPickDevelopmentCard> availableActions)
	{
		super(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD);
		this.availableActions = new ArrayList<>(availableActions);
	}

	public List<AvailableActionChooseRewardPickDevelopmentCard> getAvailableActions()
	{
		return this.availableActions;
	}
}
