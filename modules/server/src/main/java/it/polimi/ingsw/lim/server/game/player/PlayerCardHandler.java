package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.server.game.cards.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerCardHandler
{
	private final PlayerInformations playerInformations;
	private final List<DevelopmentCardBuilding> developmentCardBuildings = new ArrayList<>();
	private final List<DevelopmentCardCharacter> developmentCardCharacters = new ArrayList<>();
	private final List<DevelopmentCardTerritory> developmentCardTerritories = new ArrayList<>();
	private final List<DevelopmentCardVenture> developmentCardVentures = new ArrayList<>();
	private final List<CardLeader> cardsLeader = new ArrayList<>();

	public PlayerCardHandler(PlayerInformations playerInformations)
	{
		this.playerInformations = playerInformations;
	}

	public void addDevelopmentCard(DevelopmentCardBuilding developmentCardBuilding)
	{
		this.developmentCardBuildings.add(developmentCardBuilding);
	}

	public void addDevelopmentCard(DevelopmentCardCharacter developmentCardCharacter)
	{
		this.developmentCardCharacters.add(developmentCardCharacter);
	}

	public void addDevelopmentCard(DevelopmentCardTerritory developmentCardTerritory)
	{
		this.developmentCardTerritories.add(developmentCardTerritory);
	}

	public void addDevelopmentCard(DevelopmentCardVenture developmentCardVenture)
	{
		this.developmentCardVentures.add(developmentCardVenture);
	}

	public void addCardLeader(CardLeader cardLeader)
	{
		this.cardsLeader.add(cardLeader);
	}

	public boolean canAddDevelopmentCardBuilding()
	{
		return this.developmentCardBuildings.size() < 6;
	}

	public boolean canAddDevelopmentCardCharacter()
	{
		return this.developmentCardCharacters.size() < 6;
	}

	public boolean canAddDevelopmentCardTerritory()
	{
		return this.developmentCardTerritories.size() < 6 && this.playerInformations.getPlayerResourceHandler().isTerritorySlotAvailable(this.developmentCardTerritories.size());
	}

	public boolean canAddDevelopmentCardVenture()
	{
		return this.developmentCardVentures.size() < 6;
	}

	public boolean canAddCardLeader()
	{
		return this.cardsLeader.size() < 4;
	}

	public List<CardLeader> getCardsLeader()
	{
		return cardsLeader;
	}
}
