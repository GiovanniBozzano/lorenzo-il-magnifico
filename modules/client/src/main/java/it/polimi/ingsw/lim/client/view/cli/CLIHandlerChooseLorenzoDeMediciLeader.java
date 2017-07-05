package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationChooseLorenzoDeMediciLeader;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseLorenzoDeMediciLeader;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerChooseLorenzoDeMediciLeader implements ICLIHandler
{
	private final Map<Integer, Integer> availableLeaderCards = new HashMap<>();

	@Override
	public void execute()
	{
		this.showLeaderCards();
		this.askLeaderCard();
	}

	@Override
	public CLIHandlerChooseLorenzoDeMediciLeader newInstance()
	{
		return new CLIHandlerChooseLorenzoDeMediciLeader();
	}

	private void showLeaderCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Leader Card:");
		int index = 1;
		for (List<Integer> totalAvailableLeaderCards : ((ExpectedActionChooseLorenzoDeMediciLeader) GameStatus.getInstance().getCurrentExpectedAction()).getAvailableLeaderCards().values()) {
			for (int availableLeaderCard : totalAvailableLeaderCards) {
				stringBuilder.append(Utils.createListElement(index, GameStatus.getInstance().getLeaderCards().get(availableLeaderCard).getInformation()));
				this.availableLeaderCards.put(index, availableLeaderCard);
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askLeaderCard()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableLeaderCards.containsKey(Integer.parseInt(input)));
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationChooseLorenzoDeMediciLeader(this.availableLeaderCards.get(Integer.parseInt(input))));
	}
}
