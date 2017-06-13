package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.ResourceAmount;
import it.polimi.ingsw.lim.server.game.events.*;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.ResourceCostOption;

public enum ExcommunicationTiles
{
	EXCOMMUNICATION_TILES_1_1(0, new Modifier<EventGainResources>(EventGainResources.class)
	{
		@Override
		public void apply(EventGainResources event)
		{
			for (ResourceAmount resourceAmount : event.getResourceAmounts()) {
				if (resourceAmount.getResourceType() == ResourceType.MILITARY_POINT) {
					resourceAmount.setAmount(resourceAmount.getAmount() - 1);
				}
			}
		}
	}),
	EXCOMMUNICATION_TILES_1_2(1, new Modifier<EventGainResources>(EventGainResources.class)
	{
		@Override
		public void apply(EventGainResources event)
		{
			for (ResourceAmount resourceAmount : event.getResourceAmounts()) {
				if (resourceAmount.getResourceType() == ResourceType.SERVANT) {
					resourceAmount.setAmount(resourceAmount.getAmount() - 1);
				}
			}
		}
	}),
	EXCOMMUNICATION_TILES_1_3(2, new Modifier<EventGainResources>(EventGainResources.class)
	{
		@Override
		public void apply(EventGainResources event)
		{
			{
				for (ResourceAmount resourceAmount : event.getResourceAmounts()) {
					if (resourceAmount.getResourceType() == ResourceType.COIN) {
						resourceAmount.setAmount(resourceAmount.getAmount() - 1);
					}
				}
			}
		}
	}),
	EXCOMMUNICATION_TILES_1_4(3, new Modifier<EventGainResources>(EventGainResources.class)
	{
		@Override
		public void apply(EventGainResources event)
		{
			{
				for (ResourceAmount resourceAmount : event.getResourceAmounts()) {
					if (resourceAmount.getResourceType() == ResourceType.WOOD || resourceAmount.getResourceType() == ResourceType.STONE) {
						resourceAmount.setAmount(resourceAmount.getAmount() - 1);
					}
				}
			}
		}
	}),
	EXCOMMUNICATION_TILES_1_5(4, new Modifier<EventStartHarvest>(EventStartHarvest.class)
	{
		@Override
		public void apply(EventStartHarvest event)
		{
			event.setActionValue(event.getActionValue() - 3);
		}
	}),
	EXCOMMUNICATION_TILES_1_6(5, new Modifier<EventStartProduction>(EventStartProduction.class)
	{
		@Override
		public void apply(EventStartProduction event)
		{
			event.setActionValue(event.getActionValue() - 3);
		}
	}),
	EXCOMMUNICATION_TILES_1_7(6, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
	{
		@Override
		public void apply(EventPlaceFamilyMember event)
		{
			event.setFamilyMemberValue(event.getFamilyMemberValue() - 1);
		}
	}),
	EXCOMMUNICATION_TILES_2_1(7, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class)
	{
		@Override
		public void apply(EventGetDevelopmentCard event)
		{
			if (event.getCardType() == CardType.TERRITORY) {
				event.setActionValue(event.getActionValue() - 4);
			}
		}
	}),
	EXCOMMUNICATION_TILES_2_2(8, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class)
	{
		@Override
		public void apply(EventGetDevelopmentCard event)
		{
			if (event.getCardType() == CardType.BUILDING) {
				event.setActionValue(event.getActionValue() - 4);
			}
		}
	}),
	EXCOMMUNICATION_TILES_2_3(9, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class)
	{
		@Override
		public void apply(EventGetDevelopmentCard event)
		{
			if (event.getCardType() == CardType.CHARACTER) {
				event.setActionValue(event.getActionValue() - 4);
			}
		}
	}),
	EXCOMMUNICATION_TILES_2_4(10, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class)
	{
		@Override
		public void apply(EventGetDevelopmentCard event)
		{
			if (event.getCardType() == CardType.VENTURE) {
				event.setActionValue(event.getActionValue() - 4);
			}
		}
	}),
	EXCOMMUNICATION_TILES_2_5(11, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
	{
		@Override
		public void apply(EventPlaceFamilyMember event)
		{
			if (event.getBoardPosition() == BoardPosition.MARKET_1 || event.getBoardPosition() == BoardPosition.MARKET_2 || event.getBoardPosition() == BoardPosition.MARKET_3 || event.getBoardPosition() == BoardPosition.MARKET_4) {
				event.setCancelled(true);
			}
		}
	}),
	EXCOMMUNICATION_TILES_2_6(12, new Modifier<EventUseServants>(EventUseServants.class)
	{
		@Override
		public void apply(EventUseServants event)
		{
			event.setServants(event.getServants() / 2);
		}
	}),
	EXCOMMUNICATION_TILES_2_7(13, new Modifier<EventFirstTurn>(EventFirstTurn.class)
	{
		@Override
		public void apply(EventFirstTurn event)
		{
			event.setCancelled(true);
		}
	}),
	EXCOMMUNICATION_TILES_3_1(14, new Modifier<EventVictoryPointsCalculation>(EventVictoryPointsCalculation.class)
	{
		@Override
		public void apply(EventVictoryPointsCalculation event)
		{
			event.setCountingCharacters(false);
		}
	}),
	EXCOMMUNICATION_TILES_3_2(15, new Modifier<EventVictoryPointsCalculation>(EventVictoryPointsCalculation.class)
	{
		@Override
		public void apply(EventVictoryPointsCalculation event)
		{
			event.setCountingVentures(false);
		}
	}),
	EXCOMMUNICATION_TILES_3_3(16, new Modifier<EventVictoryPointsCalculation>(EventVictoryPointsCalculation.class)
	{
		@Override
		public void apply(EventVictoryPointsCalculation event)
		{
			event.setCountingTerritories(false);
		}
	}),
	EXCOMMUNICATION_TILES_3_4(17, new Modifier<EventPreVictoryPointsCalculation>(EventPreVictoryPointsCalculation.class)
	{
		@Override
		public void apply(EventPreVictoryPointsCalculation event)
		{
			event.setVictoryPoints(event.getVictoryPoints() - event.getVictoryPoints() / 5);
		}
	}),
	EXCOMMUNICATION_TILES_3_5(18, new Modifier<EventPostVictoryPointsCalculation>(EventPostVictoryPointsCalculation.class)
	{
		@Override
		public void apply(EventPostVictoryPointsCalculation event)
		{
			event.setVictoryPoints(event.getVictoryPoints() - event.getPlayer().getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.MILITARY_POINT));
		}
	}),
	EXCOMMUNICATION_TILES_3_6(19, new Modifier<EventPostVictoryPointsCalculation>(EventPostVictoryPointsCalculation.class)
	{
		@Override
		public void apply(EventPostVictoryPointsCalculation event)
		{
			for (DevelopmentCardBuilding developmentCardBuilding : event.getPlayer().getPlayerHandler().getPlayerCardHandler().getDevelopmentCards(CardType.BUILDING, DevelopmentCardBuilding.class)) {
				for (ResourceCostOption resourceCostOption : developmentCardBuilding.getResourceCostOptions()) {
					for (ResourceAmount resourceAmount : resourceCostOption.getSpentResources()) {
						if (resourceAmount.getResourceType() == ResourceType.STONE || resourceAmount.getResourceType() == ResourceType.WOOD) {
							event.setVictoryPoints(event.getVictoryPoints() - resourceAmount.getAmount());
						}
					}
				}
			}
		}
	}),
	EXCOMMUNICATION_TILES_3_7(20, new Modifier<EventPostVictoryPointsCalculation>(EventPostVictoryPointsCalculation.class)
	{
		@Override
		public void apply(EventPostVictoryPointsCalculation event)
		{
			event.setVictoryPoints(event.getVictoryPoints() - event.getPlayer().getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.COIN));
			event.setVictoryPoints(event.getVictoryPoints() - event.getPlayer().getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT));
			event.setVictoryPoints(event.getVictoryPoints() - event.getPlayer().getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.STONE));
			event.setVictoryPoints(event.getVictoryPoints() - event.getPlayer().getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.WOOD));
		}
	});
	private final int index;
	private final Modifier modifier;

	ExcommunicationTiles(int index, Modifier modifier)
	{
		this.index = index;
		this.modifier = modifier;
	}

	public int getIndex()
	{
		return this.index;
	}

	public Modifier getModifier()
	{
		return this.modifier;
	}
}
