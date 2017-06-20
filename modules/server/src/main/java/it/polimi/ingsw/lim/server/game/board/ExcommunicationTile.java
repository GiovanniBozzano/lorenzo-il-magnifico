package it.polimi.ingsw.lim.server.game.board;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.server.game.events.*;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public enum ExcommunicationTile
{
	EXCOMMUNICATION_TILES_1_1(0, "/images/excommunication_tiles/excommunication_tile_1_1.png", Period.FIRST, new Modifier<EventGainResources>(EventGainResources.class, "Each time you gain Military Points (from action spaces or from your Cards), gain 1 fewer Military Point. (If you have more Cards that give you Military Points, consider each Card a single source, so you gain -1 Military Point for each card).")
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
	EXCOMMUNICATION_TILES_1_2(1, "/images/excommunication_tiles/excommunication_tile_1_2.png", Period.FIRST, new Modifier<EventGainResources>(EventGainResources.class, "Each time you receive coins (from\naction spaces or from your Cards),\nyou receive 1 fewer coin. (If you\nhave more Cards that give you coins,\nconsider each Card a single source, so\nyou receive -1 coin for each card.)")
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
	EXCOMMUNICATION_TILES_1_3(2, "/images/excommunication_tiles/excommunication_tile_1_3.png", Period.FIRST, new Modifier<EventGainResources>(EventGainResources.class, "Each time you receive servants\n(from action spaces or from your\nCards), you receive 1 fewer\nservant. (If you have more Cards\nthat give you servants, consider each\nCard a single source, so you receive -1\nservant for each card.)")
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
	EXCOMMUNICATION_TILES_1_4(3, "/images/excommunication_tiles/excommunication_tile_1_4.png", Period.FIRST, new Modifier<EventGainResources>(EventGainResources.class, "Each time you receive wood or\nstone (from action spaces or from your\nCards), you receive 1 fewer wood\nor stone. (If you have more Cards\nthat give you wood and stone, consider\neach Card a single source, so you receive\n-1 wood/stone for each card.)")
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
	EXCOMMUNICATION_TILES_1_5(4, "/images/excommunication_tiles/excommunication_tile_1_5.png", Period.FIRST, new Modifier<EventStartHarvest>(EventStartHarvest.class, "Each time you perform a Harvest\naction (through the action space or as\na Card effect), decrease its value by\n3. You may still spend servants to\nincrease the action value and you\nmust apply your Card’s effects. (If\nyou place in the second action space,\nyour action value is decreased by 6.)")
	{
		@Override
		public void apply(EventStartHarvest event)
		{
			event.setActionValue(event.getActionValue() - 3);
		}
	}),
	EXCOMMUNICATION_TILES_1_6(5, "/images/excommunication_tiles/excommunication_tile_1_6.png", Period.FIRST, new Modifier<EventStartProduction>(EventStartProduction.class, "Each time you perform a\nProduction action (through the\naction space or as a Card effect),\ndecrease its value by 3. You may\nstill spend servants to increase the\naction value and you must apply\nyour Card’s effects. (If you place\nin the large action space, your action\nvalue is decreased by 6.)")
	{
		@Override
		public void apply(EventStartProduction event)
		{
			event.setActionValue(event.getActionValue() - 3);
		}
	}),
	EXCOMMUNICATION_TILES_1_7(6, "/images/excommunication_tiles/excommunication_tile_1_7.png", Period.FIRST, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "All your colored Family Members\nreceive a -1 reduction of their\nvalue each time you place them.\n(For example, if you roll a 5 on the\nblack die, your Family Member with\nthe black die symbol has a value of\n4.) You may still spend servants to\nincrease their value and you must\napply your Card’s effects.")
	{
		@Override
		public void apply(EventPlaceFamilyMember event)
		{
			event.setFamilyMemberValue(event.getFamilyMemberValue() - 1);
		}
	}),
	EXCOMMUNICATION_TILES_2_1(7, "/images/excommunication_tiles/excommunication_tile_2_1.png", Period.SECOND, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class, "Each time you take a Territory\nCard (through the action space or as\na Card effect), your action receives\na -4 reduction of its value.\nYou may still spend servants to\nincrease the action value and you\nmust apply your Card’s effects.")
	{
		@Override
		public void apply(EventGetDevelopmentCard event)
		{
			if (event.getCardType() == CardType.TERRITORY) {
				event.setActionValue(event.getActionValue() - 4);
			}
		}
	}),
	EXCOMMUNICATION_TILES_2_2(8, "/images/excommunication_tiles/excommunication_tile_2_2.png", Period.SECOND, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class, "Each time you take a Building\nCard (through the action space or as\na Card effect), your action receives\na reduction of -4 of its value.\nYou may still spend servants to\nincrease the action value and you\nmust apply your Card’s effects.")
	{
		@Override
		public void apply(EventGetDevelopmentCard event)
		{
			if (event.getCardType() == CardType.BUILDING) {
				event.setActionValue(event.getActionValue() - 4);
			}
		}
	}),
	EXCOMMUNICATION_TILES_2_3(9, "/images/excommunication_tiles/excommunication_tile_2_3.png", Period.SECOND, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class, "Each time you take a Character\nCard (through the action space or as\na Card effect), your action receives\na -4 reduction of its value.\nYou may still spend servants to\nincrease the action value,and you\nmust apply your Card’s effects.")
	{
		@Override
		public void apply(EventGetDevelopmentCard event)
		{
			if (event.getCardType() == CardType.CHARACTER) {
				event.setActionValue(event.getActionValue() - 4);
			}
		}
	}),
	EXCOMMUNICATION_TILES_2_4(10, "/images/excommunication_tiles/excommunication_tile_2_4.png", Period.SECOND, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class, "Each time you take a Venture\nCard (through the action space or as\na Card effect), your action receives\na -4 reduction of its value.\nYou may still spend servants to\nincrease the action value and you\nmust apply your Card’s effects.")
	{
		@Override
		public void apply(EventGetDevelopmentCard event)
		{
			if (event.getCardType() == CardType.VENTURE) {
				event.setActionValue(event.getActionValue() - 4);
			}
		}
	}),
	EXCOMMUNICATION_TILES_2_5(11, "/images/excommunication_tiles/excommunication_tile_2_5.png", Period.SECOND, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "You can’t place your Family\nMembers in the Market action\nspaces.")
	{
		@Override
		public void apply(EventPlaceFamilyMember event)
		{
			if (event.getBoardPosition() == BoardPosition.MARKET_1 || event.getBoardPosition() == BoardPosition.MARKET_2 || event.getBoardPosition() == BoardPosition.MARKET_3 || event.getBoardPosition() == BoardPosition.MARKET_4) {
				event.setCancelled(true);
			}
		}
	}),
	EXCOMMUNICATION_TILES_2_6(12, "/images/excommunication_tiles/excommunication_tile_2_6.png", Period.SECOND, new Modifier<EventUseServants>(EventUseServants.class, "You have to spend 2 servants to\nincrease your action value by 1\n(and 4 servants to increase it by 2,\nand so on...).")
	{
		@Override
		public void apply(EventUseServants event)
		{
			event.setServants(event.getServants() / 2);
		}
	}),
	EXCOMMUNICATION_TILES_2_7(13, "/images/excommunication_tiles/excommunication_tile_2_7.png", Period.SECOND, new Modifier<EventFirstTurn>(EventFirstTurn.class, "Each round, you skip your first\nturn. (When you have to place your\nfirst Family Member, you have to\npass.) You start taking actions from\nthe second turn (in the appropriate\nturn order.) When all players have\ntaken all their turns, you may still\nplace your last Family Member.")
	{
		@Override
		public void apply(EventFirstTurn event)
		{
			event.setCancelled(true);
		}
	}),
	EXCOMMUNICATION_TILES_3_1(14, "/images/excommunication_tiles/excommunication_tile_3_1.png", Period.THIRD, new Modifier<EventVictoryPointsCalculation>(EventVictoryPointsCalculation.class, "At the end of the game, you\ndon’t score points for any of your\nInfluenced Characters.")
	{
		@Override
		public void apply(EventVictoryPointsCalculation event)
		{
			event.setCountingCharacters(false);
		}
	}),
	EXCOMMUNICATION_TILES_3_2(15, "/images/excommunication_tiles/excommunication_tile_3_2.png", Period.THIRD, new Modifier<EventVictoryPointsCalculation>(EventVictoryPointsCalculation.class, "At the end of the game, you\ndon’t score points for any of your\nEncouraged Ventures.")
	{
		@Override
		public void apply(EventVictoryPointsCalculation event)
		{
			event.setCountingVentures(false);
		}
	}),
	EXCOMMUNICATION_TILES_3_3(16, "/images/excommunication_tiles/excommunication_tile_3_3.png", Period.THIRD, new Modifier<EventVictoryPointsCalculation>(EventVictoryPointsCalculation.class, "At the end of the game, you\ndon’t score points for any of your\nConquered Territories.\n")
	{
		@Override
		public void apply(EventVictoryPointsCalculation event)
		{
			event.setCountingTerritories(false);
		}
	}),
	EXCOMMUNICATION_TILES_3_4(17, "/images/excommunication_tiles/excommunication_tile_3_4.png", Period.THIRD, new Modifier<EventPreVictoryPointsCalculation>(EventPreVictoryPointsCalculation.class, "At the end of the game, before\nthe Final Scoring, you lose\n1 Victory Point for every\n5 Victory Points you have. (For\nexample, if you have 26 Victory\nPoints before the Final Scoring, you\nlose 5 Victory Points.")
	{
		@Override
		public void apply(EventPreVictoryPointsCalculation event)
		{
			event.setVictoryPoints(event.getVictoryPoints() - event.getVictoryPoints() / 5);
		}
	}),
	EXCOMMUNICATION_TILES_3_5(18, "/images/excommunication_tiles/excommunication_tile_3_5.png", Period.THIRD, new Modifier<EventPostVictoryPointsCalculation>(EventPostVictoryPointsCalculation.class, "At the end of the game, you lose\n1 Victory Point for every Military\nPoint you have. (For example, if\nyou end the game with 12 Military\nPoints, you lose 12 Victory Points.)")
	{
		@Override
		public void apply(EventPostVictoryPointsCalculation event)
		{
			event.setVictoryPoints(event.getVictoryPoints() - event.getPlayer().getPlayerResourceHandler().getResources().get(ResourceType.MILITARY_POINT));
		}
	}),
	EXCOMMUNICATION_TILES_3_6(19, "/images/excommunication_tiles/excommunication_tile_3_6.png", Period.THIRD, new Modifier<EventPostVictoryPointsCalculation>(EventPostVictoryPointsCalculation.class, "At the end of the game, you lose\n1 Victory Point for every wood\nand stone on your Building\nCards’ costs. (For example, if all\nyour Building Cards cost 7 wood\nand 6 stone, you lose 13 Victory\nPoints.)")
	{
		@Override
		public void apply(EventPostVictoryPointsCalculation event)
		{
			for (DevelopmentCardBuilding developmentCardBuilding : event.getPlayer().getPlayerCardHandler().getDevelopmentCards(CardType.BUILDING, DevelopmentCardBuilding.class)) {
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
	EXCOMMUNICATION_TILES_3_7(20, "/images/excommunication_tiles/excommunication_tile_3_7.png", Period.THIRD, new Modifier<EventPostVictoryPointsCalculation>(EventPostVictoryPointsCalculation.class, "At the end of the game, you lose\n1 Victory Point for every resource\n(wood, stone, coin, servant) in your\nsupply on your Personal Board.\n(For example, if you end the game\nwith 3 wood, 1 stone, 4 coins, and\n2 servants, you lose 10 Victory\nPoints.)")
	{
		@Override
		public void apply(EventPostVictoryPointsCalculation event)
		{
			event.setVictoryPoints(event.getVictoryPoints() - event.getPlayer().getPlayerResourceHandler().getResources().get(ResourceType.COIN));
			event.setVictoryPoints(event.getVictoryPoints() - event.getPlayer().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT));
			event.setVictoryPoints(event.getVictoryPoints() - event.getPlayer().getPlayerResourceHandler().getResources().get(ResourceType.STONE));
			event.setVictoryPoints(event.getVictoryPoints() - event.getPlayer().getPlayerResourceHandler().getResources().get(ResourceType.WOOD));
		}
	});
	private final int index;
	private final String texturePath;
	private final Period period;
	private final Modifier modifier;

	ExcommunicationTile(int index, String texturePath, Period period, Modifier modifier)
	{
		this.index = index;
		this.texturePath = texturePath;
		this.period = period;
		this.modifier = modifier;
	}

	public int getIndex()
	{
		return this.index;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}

	public Period getPeriod()
	{
		return this.period;
	}

	public Modifier getModifier()
	{
		return this.modifier;
	}
}
