package it.polimi.ingsw.lim.common.network;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;

import java.util.*;

public class AuthenticationInformationsGame extends AuthenticationInformations
{
	private Map<Period, Integer> excommunicationTiles;
	private Map<Integer, PlayerIdentification> playersIdentifications;
	private int ownPlayerIndex;
	private boolean gameInitialized;
	private GameInformations gameInformations;
	private List<PlayerInformations> playersInformations;
	private Map<Integer, Boolean> ownLeaderCardsHand;
	private int turnPlayerIndex;
	private Map<ActionType, List<AvailableAction>> availableActions;

	public Map<Period, Integer> getExcommunicationTiles()
	{
		return this.excommunicationTiles;
	}

	public void setExcommunicationTiles(Map<Period, Integer> excommunicationTiles)
	{
		this.excommunicationTiles = new EnumMap<>(excommunicationTiles);
	}

	public Map<Integer, PlayerIdentification> getPlayersIdentifications()
	{
		return this.playersIdentifications;
	}

	public void setPlayersIdentifications(Map<Integer, PlayerIdentification> playersIdentifications)
	{
		this.playersIdentifications = new HashMap<>(playersIdentifications);
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
		this.playersInformations = new ArrayList<>(playersInformations);
	}

	public Map<Integer, Boolean> getOwnLeaderCardsHand()
	{
		return this.ownLeaderCardsHand;
	}

	public void setOwnLeaderCardsHand(Map<Integer, Boolean> ownLeaderCardsHand)
	{
		this.ownLeaderCardsHand = new HashMap<>(ownLeaderCardsHand);
	}

	public int getTurnPlayerIndex()
	{
		return this.turnPlayerIndex;
	}

	public void setTurnPlayerIndex(int turnPlayerIndex)
	{
		this.turnPlayerIndex = turnPlayerIndex;
	}

	public Map<ActionType, List<AvailableAction>> getAvailableActions()
	{
		return this.availableActions;
	}

	public void setAvailableActions(Map<ActionType, List<AvailableAction>> availableActions)
	{
		this.availableActions = new EnumMap<>(availableActions);
	}
}
