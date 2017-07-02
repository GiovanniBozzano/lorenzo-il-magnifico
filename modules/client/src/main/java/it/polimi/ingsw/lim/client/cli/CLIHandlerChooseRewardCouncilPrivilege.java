package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.*;
import java.util.logging.Level;

public class CLIHandlerChooseRewardCouncilPrivilege implements ICLIHandler
{
	private static final Map<Integer, List<ResourceAmount>> RESOURCE_CHOICE = new HashMap<>();

	static {
		RESOURCE_CHOICE.put(1, Arrays.asList(new ResourceAmount(ResourceType.WOOD, 1), new ResourceAmount(ResourceType.STONE, 1)));
		RESOURCE_CHOICE.put(2, Collections.singletonList(new ResourceAmount(ResourceType.SERVANT, 2)));
		RESOURCE_CHOICE.put(3, Collections.singletonList(new ResourceAmount(ResourceType.COIN, 2)));
		RESOURCE_CHOICE.put(4, Collections.singletonList(new ResourceAmount(ResourceType.MILITARY_POINT, 2)));
		RESOURCE_CHOICE.put(5, Collections.singletonList(new ResourceAmount(ResourceType.FAITH_POINT, 1)));
	}

	@Override
	public void execute()
	{
		this.askResourceChoice();
	}

	@Override
	public CLIHandlerChooseRewardCouncilPrivilege newInstance()
	{
		return new CLIHandlerChooseRewardCouncilPrivilege();
	}

	private void askResourceChoice()
	{
		Client.getLogger().log(Level.INFO, "Enter Resource Trade Choice...");
		Client.getLogger().log(Level.INFO, "1 - WOOD: 1, STONE: 1");
		Client.getLogger().log(Level.INFO, "2 - SERVANT: 2");
		Client.getLogger().log(Level.INFO, "3 - COIN: 2");
		Client.getLogger().log(Level.INFO, "4 - MILITARY POINT: 2");
		Client.getLogger().log(Level.INFO, "5 - FAITH POINT: 1");
		if (GameStatus.getInstance().getCurrentPlayersData().size() == 5) {
			Client.getLogger().log(Level.INFO, "6 - PRESTIGE POINT: 1");
			RESOURCE_CHOICE.put(6, Collections.singletonList((new ResourceAmount(ResourceType.PRESTIGE_POINT, 1))));
		}
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerChooseRewardCouncilPrivilege.RESOURCE_CHOICE.containsKey(Integer.parseInt(input)));
		this.addResources(CLIHandlerChooseRewardCouncilPrivilege.RESOURCE_CHOICE.get(Integer.parseInt(input)));
	}

	private void addResources(List<ResourceAmount> resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().put(resourceAmount.getResourceType(), resourceAmount.getAmount());
		}
	}
}
