package it.polimi.ingsw.lim.common.network;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;

import java.util.List;
import java.util.Map;

public class AuthenticationInformationsGame extends AuthenticationInformations
{
	private Map<Period, Integer> excommunicationTiles;
	private Map<Integer, PlayerIdentification> playersIdentifications;
	private int ownPlayerIndex;
	private boolean gameInitialized;
	private GameInformations gameInformations;
	private List<PlayerInformations> playersInformations;
	private int turnPlayerIndex;
	private List<AvailableAction> availableActions;

	public Map<Period, Integer> getExcommunicationTiles()
	{
		return this.excommunicationTiles;
	}

	public void setExcommunicationTiles(Map<Period, Integer> excommunicationTiles)
	{
		this.excommunicationTiles = excommunicationTiles;
	}

	public Map<Integer, PlayerIdentification> getPlayersIdentifications()
	{
		return this.playersIdentifications;
	}

	public void setPlayersIdentifications(Map<Integer, PlayerIdentification> playersIdentifications)
	{
		this.playersIdentifications = playersIdentifications;
	}

	public int getOwnPlayerIndex()
	{
		return this.ownPlayerIndex;
	}

	public void setOwnPlayerIndex(int ownPlayerIndex)
	{
		this.ownPlayerIndex = ownPlayerIndex;
	}

	public boolean isGameInitialized()
	{
		return this.gameInitialized;
	}

	public void setGameInitialized(boolean gameInitialized)
	{
		this.gameInitialized = gameInitialized;
	}

	public GameInformations getGameInformations()
	{
		return this.gameInformations;
	}

	public void setGameInformations(GameInformations gameInformations)
	{
		this.gameInformations = gameInformations;
	}

	public List<PlayerInformations> getPlayersInformations()
	{
		return this.playersInformations;
	}

	public void setPlayersInformations(List<PlayerInformations> playersInformations)
	{
		this.playersInformations = playersInformations;
	}

	public int getTurnPlayerIndex()
	{
		return this.turnPlayerIndex;
	}

	public void setTurnPlayerIndex(int turnPlayerIndex)
	{
		this.turnPlayerIndex = turnPlayerIndex;
	}

	public List<AvailableAction> getAvailableActions()
	{
		return this.availableActions;
	}

	public void setAvailableActions(List<AvailableAction> availableActions)
	{
		this.availableActions = availableActions;
	}
}
