package it.polimi.ingsw.lim.common.network;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformation;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformation;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformation;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

public class AuthenticationInformationGame extends AuthenticationInformation
{
	private final Map<Period, Integer> excommunicationTiles = new EnumMap<>(Period.class);
	private final Map<Integer, List<ResourceAmount>> councilPrivilegeRewards = new HashMap<>();
	private final Map<Integer, PlayerIdentification> playersIdentifications = new HashMap<>();
	private final List<PlayerInformation> playersInformation = new ArrayList<>();
	private final Map<Integer, Boolean> ownLeaderCardsHand = new HashMap<>();
	private final Map<ActionType, List<Serializable>> availableActions = new EnumMap<>(ActionType.class);
	private int ownPlayerIndex;
	private boolean gameInitialized;
	private GameInformation gameInformation;
	private int turnPlayerIndex;

	public AuthenticationInformationGame(AuthenticationInformation authenticationInformation)
	{
		for (Entry<CardType, Map<Integer, DevelopmentCardInformation>> developmentCardsInformatioType : authenticationInformation.getDevelopmentCardsInformation().entrySet()) {
			this.setDevelopmentCardsInformation(developmentCardsInformatioType.getKey(), developmentCardsInformatioType.getValue());
		}
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
		this.excommunicationTiles.clear();
		this.excommunicationTiles.putAll(excommunicationTiles);
	}

	public Map<Integer, List<ResourceAmount>> getCouncilPrivilegeRewards()
	{
		return this.councilPrivilegeRewards;
	}

	public void setCouncilPrivilegeRewards(Map<Integer, List<ResourceAmount>> councilPrivilegeRewards)
	{
		this.councilPrivilegeRewards.clear();
		this.councilPrivilegeRewards.putAll(councilPrivilegeRewards);
	}

	public Map<Integer, PlayerIdentification> getPlayersIdentifications()
	{
		return this.playersIdentifications;
	}

	public void setPlayersIdentifications(Map<Integer, PlayerIdentification> playersIdentifications)
	{
		this.playersIdentifications.clear();
		this.playersIdentifications.putAll(playersIdentifications);
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
		this.playersInformation.clear();
		this.playersInformation.addAll(playersInformation);
	}

	public Map<Integer, Boolean> getOwnLeaderCardsHand()
	{
		return this.ownLeaderCardsHand;
	}

	public void setOwnLeaderCardsHand(Map<Integer, Boolean> ownLeaderCardsHand)
	{
		this.ownLeaderCardsHand.clear();
		this.ownLeaderCardsHand.putAll(ownLeaderCardsHand);
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
		this.availableActions.clear();
		this.availableActions.putAll(availableActions);
	}
}
