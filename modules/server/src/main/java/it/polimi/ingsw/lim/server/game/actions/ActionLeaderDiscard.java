package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.cards.CardLeader;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionLeaderDiscard implements IAction
{
	private final Connection player;
	private final int cardLeaderIndex;

	public ActionLeaderDiscard(Connection player, int cardLeaderIndex)
	{
		this.player = player;
		this.cardLeaderIndex = cardLeaderIndex;
	}

	@Override
	public boolean isLegal()
	{
		// check if the player is inside a room
		Room room = Room.getPlayerRoom(this.player);
		if (room == null) {
			return false;
		}
		// check if the game has started
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return false;
		}
		// check if it is the player's turn
		if (this.player != gameHandler.getTurnPlayer()) {
			return false;
		}
		// check if the player has the leader card
		boolean owned = false;
		CardLeader cardLeader = null;
		for (CardLeader currentCardLeader : this.player.getPlayerInformations().getPlayerCardHandler().getCardsLeader()) {
			if (this.cardLeaderIndex != currentCardLeader.getIndex()) {
				continue;
			}
			cardLeader = currentCardLeader;
			owned = true;
			break;
		}
		if (!owned) {
			return false;
		}
		// check if the leader card hasn't been played
		return cardLeader.isPlayed();
	}

	@Override
	public void apply()
	{
	}

	@Override
	public Connection getPlayer()
	{
		return this.player;
	}
}
