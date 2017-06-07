package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionProductionTrade implements IAction
{
	private final Connection player;
	private final int developmentCardBuildingIndex;

	public ActionProductionTrade(Connection player, int developmentCardBuildingIndex)
	{
		this.player = player;
		this.developmentCardBuildingIndex = developmentCardBuildingIndex;
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
		// check if the player has enough resources to activate production trades
		return true;
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

	public int getDevelopmentCardBuildingIndex()
	{
		return this.developmentCardBuildingIndex;
	}
}