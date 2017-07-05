package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationChooseLorenzoDeMediciLeader;
import it.polimi.ingsw.lim.server.game.cards.CardsHandler;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ActionChooseLorenzoDeMediciLeader extends ActionInformationChooseLorenzoDeMediciLeader implements IAction
{
	private final transient Player player;

	public ActionChooseLorenzoDeMediciLeader(int leaderCardIndex, Player player)
	{
		super(leaderCardIndex);
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if the chosen leader card is valid
		List<Integer> availableLeaderCards = new ArrayList<>();
		for (Player otherPlayer : this.player.getRoom().getGameHandler().getTurnOrder()) {
			if (otherPlayer != this.player) {
				for (LeaderCard leaderCard : otherPlayer.getPlayerCardHandler().getLeaderCards()) {
					if (leaderCard.isPlayed()) {
						availableLeaderCards.add(leaderCard.getIndex());
					}
				}
			}
		}
		if (!availableLeaderCards.contains(this.getLeaderCardIndex())) {
			throw new GameActionFailedException("Lorenzo Il Magnifico is not available for use");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		this.player.getRoom().getGameHandler().setCurrentPhase(Phase.LEADER);
		for (LeaderCard leaderCard : new ArrayList<>(this.player.getPlayerCardHandler().getLeaderCards())) {
			if (leaderCard instanceof LeaderCardReward && ((LeaderCardReward) leaderCard).getReward().getActionReward() != null && ((LeaderCardReward) leaderCard).getReward().getActionReward().getRequestedAction() == ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER) {
				this.player.getPlayerCardHandler().getLeaderCards().remove(leaderCard);
				break;
			}
		}
		LeaderCard leaderCard = CardsHandler.getleaderCardFromIndex(this.getLeaderCardIndex());
		if (leaderCard == null) {
			throw new GameActionFailedException("Cannot select Lorenzo Il Magnifico from your cards");
		}
		leaderCard.setPlayed(true);
		this.player.getPlayerCardHandler().getLeaderCards().add(leaderCard);
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " played Lorenzo De Medici and chose to copy " + leaderCard.getDisplayName() + "'s effect");
		if (Utils.activateLeaderCard(this.player, leaderCard)) {
			return;
		}
		this.player.getPlayerResourceHandler().convertTemporaryResources();
		this.player.getRoom().getGameHandler().setExpectedAction(null);
		this.player.getRoom().getGameHandler().sendGameUpdate(this.player);
	}
}
