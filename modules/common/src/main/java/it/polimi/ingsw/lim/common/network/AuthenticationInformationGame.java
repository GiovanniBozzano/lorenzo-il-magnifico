package it.polimi.ingsw.lim.common.network;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformation;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformation;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.io.Serializable;
import java.util.*;

public class AuthenticationInformationGame extends AuthenticationInformation
{
	private Map<Period, Integer> excommunicationTiles;
	private Map<Integer, List<ResourceAmount>> councilPrivilegeRewards;
	private Map<Integer, PlayerIdentification> playersIdentifications;
	private int ownPlayerIndex;
	private boolean gameInitialized;
	private GameInformation gameInformation;
	private List<PlayerInformation> playersInformation;
	private Map<Integer, Boolean> ownLeaderCardsHand;
	private int turnPlayerIndex;
	private Map<ActionType, List<Serializable>> availableActions;

	public AuthenticationInformationGame(AuthenticationInformation authenticationInformation)
	{
		this.setDevelopmentCardsBuildingInformation(authenticationInformation.getDevelopmentCardsBuildingInformation());
		this.setDevelopmentCardsCharacterInformation(authenticationInformation.getDevelopmentCardsCharacterInformation());
		this.setDevelopmentCardsTerritoryInformation(authenticationInformation.getDevelopmentCardsTerritoryInformation());
		this.setDevelopmentCardsVentureInformation(authenticationInformation.getDevelopmentCardsVentureInformation());
		this.setLeaderCardsInformation(authenticationInformation.getLeaderCardsInformation());
		this.setExcommunicationTilesInformation(authenticationInformation.getExcommunicationTilesInformation());
		this.setPersonalBonusTilesInformation(authenticationInformation.getPersonalBonusTilesInformation());
	}

	public Map<Period, Integer> getExcommunicationTiles()
	{
		return this.excommunicationTiles;
	}

	public void setExcommunicationTiles(Map<Period, Integer> excommunicationTiles)
	{
		this.excommunicationTiles = new EnumMap<>(excommunicationTiles);
	}

	public Map<Integer, List<ResourceAmount>> getCouncilPrivilegeRewards()
	{
		return this.councilPrivilegeRewards;
	}

	public void setCouncilPrivilegeRewards(Map<Integer, List<ResourceAmount>> councilPrivilegeRewards)
	{
		this.councilPrivilegeRewards = councilPrivilegeRewards;
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

	public GameInformation getGameInformation()
	{
		return this.gameInformation;
	}

	public void setGameInformation(GameInformation gameInformation)
	{
		this.gameInformation = gameInformation;
	}

	public List<PlayerInformation> getPlayersInformation()
	{
		return this.playersInformation;
	}

	public void setPlayersInformation(List<PlayerInformation> playersInformation)
	{
		this.playersInformation = new ArrayList<>(playersInformation);
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

	public Map<ActionType, List<Serializable>> getAvailableActions()
	{
		return this.availableActions;
	}

	public void setAvailableActions(Map<ActionType, List<Serializable>> availableActions)
	{
		this.availableActions = new EnumMap<>(availableActions);
	}
}
