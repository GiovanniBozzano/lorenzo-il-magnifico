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
		if (!this.canAddDevelopmentCardBuilding()) {
			return;
		}
		this.developmentCardBuildings.add(developmentCardBuilding);
	}

	public void addDevelopmentCard(DevelopmentCardCharacter developmentCardCharacter)
	{
		if (!this.canAddDevelopmentCardCharacter()) {
			return;
		}
		this.developmentCardCharacters.add(developmentCardCharacter);
	}

	public void addDevelopmentCard(DevelopmentCardTerritory developmentCardTerritory)
	{
		if (!this.canAddDevelopmentCardTerritory()) {
			return;
		}
		this.developmentCardTerritories.add(developmentCardTerritory);
	}

	public void addDevelopmentCard(DevelopmentCardVenture developmentCardVenture)
	{
		if (!this.canAddDevelopmentCardVenture()) {
			return;
		}
		this.developmentCardVentures.add(developmentCardVenture);
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
}
