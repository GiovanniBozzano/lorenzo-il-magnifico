package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardHarvest;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardProduction;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardTemporaryModifier;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardModifier;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.events.EventChurchSupport;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.CardAmount;
import it.polimi.ingsw.lim.server.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.common.game.ResourceAmount;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.*;

public class CardsHandler
{
	private static final Map<CardType, Class<? extends DevelopmentCard>> DEVELOPMENT_CARDS_TYPES = new EnumMap<>(CardType.class);

	static {
		CardsHandler.DEVELOPMENT_CARDS_TYPES.put(CardType.BUILDING, DevelopmentCardBuilding.class);
		CardsHandler.DEVELOPMENT_CARDS_TYPES.put(CardType.CHARACTER, DevelopmentCardCharacter.class);
		CardsHandler.DEVELOPMENT_CARDS_TYPES.put(CardType.TERRITORY, DevelopmentCardTerritory.class);
		CardsHandler.DEVELOPMENT_CARDS_TYPES.put(CardType.VENTURE, DevelopmentCardVenture.class);
	}

	public static final DevelopmentCardsDeck<DevelopmentCardBuilding> DEVELOPMENT_CARDS_BUILDING = new DevelopmentCardsDeck.Builder<>(DevelopmentCardBuilding.class, "/json/development_cards_building.json").initialize();
	public static final DevelopmentCardsDeck<DevelopmentCardCharacter> DEVELOPMENT_CARDS_CHARACTER = new DevelopmentCardsDeck.Builder<>(DevelopmentCardCharacter.class, "/json/development_cards_character.json").initialize();
	public static final DevelopmentCardsDeck<DevelopmentCardTerritory> DEVELOPMENT_CARDS_TERRITORY = new DevelopmentCardsDeck.Builder<>(DevelopmentCardTerritory.class, "/json/development_cards_territory.json").initialize();
	public static final DevelopmentCardsDeck<DevelopmentCardVenture> DEVELOPMENT_CARDS_VENTURE = new DevelopmentCardsDeck.Builder<>(DevelopmentCardVenture.class, "/json/development_cards_venture.json").initialize();
	private static final List<LeaderCard> CARDS_LEADER = new ArrayList<>();

	static {
		CardsHandler.CARDS_LEADER.add(new LeaderCardModifier("Cesare Borgia", 0, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.BUILDING, 3))), new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.COIN, 12), new ResourceAmount(ResourceType.FAITH_POINT, 2)))))), "You don’t need to satisfy the Military Points requirement when\nyou take Territory Cards.", new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class)
		{
			@Override
			public void apply(EventGetDevelopmentCard event)
			{
				event.setIgnoreTerritoriesSlotLock(true);
			}
		}));
		CardsHandler.CARDS_LEADER.add(new LeaderCardModifier("Filippo Brunelleschi", 1, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.BUILDING, 5))), new ArrayList<>()))), " You don’t have to spend 3 coins when you place your Family\nMembers in a Tower that is already occupied.", new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				event.setIgnoreOccupiedTax(true);
			}
		}));
		CardsHandler.CARDS_LEADER.add(new LeaderCardModifier("Lucrezia Borgia", 2, new ArrayList<>(Arrays.asList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.BUILDING, 6))), new ArrayList<>()), new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.CHARACTER, 6))), new ArrayList<>()), new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.TERRITORY, 6))), new ArrayList<>()), new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.VENTURE, 6))), new ArrayList<>()))), "Your colored Family Members have a bonus of +2 on their value. (You can increase their\nvalue by spending servants or if you have Character\nCards with this effect.)", new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
					return;
				}
				event.setFamilyMemberValue(event.getFamilyMemberValue() + 2);
			}
		}));
		CardsHandler.CARDS_LEADER.add(new LeaderCardModifier("Lorenzo de' Medici", 3, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.VICTORY_POINT, 35)))))), "Copy the ability of another Leader Card already played by\nanother player. Once you decide the ability to copy, it can’t be changed.", null));
		CardsHandler.CARDS_LEADER.add(new LeaderCardModifier("Ludovico Ariosto", 4, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.CHARACTER, 5))), new ArrayList<>()))), "You can place your Family Members in occupied action spaces.", new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				event.setIgnoreOccupied(true);
			}
		}));
		CardsHandler.CARDS_LEADER.add(new LeaderCardModifier("Ludovico Il Moro", 5, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new CardAmount(CardType.BUILDING, 2), new CardAmount(CardType.CHARACTER, 2), new CardAmount(CardType.TERRITORY, 2), new CardAmount(CardType.VENTURE, 2))), new ArrayList<>()))), "Your colored Family Members has a value of 5, regardless of\ntheir related dice. (You can increase their value by spending servants or if you have\nCharacter Cards with this effect.)", new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
					return;
				}
				event.setFamilyMemberValue(5);
			}
		}));
		CardsHandler.CARDS_LEADER.add(new LeaderCardModifier("Pico Della Mirandola", 6, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new CardAmount(CardType.BUILDING, 2), new CardAmount(CardType.VENTURE, 4))), new ArrayList<>()))), "When you take Development Cards, you get a discount of\n3 coins (if the card you are taking has coins in its cost.) This is not a discount on the\ncoins you must spend if you take a Development Card from a Tower that’s already\noccupied.", new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class)
		{
			@Override
			public void apply(EventGetDevelopmentCard event)
			{
				for (ResourceAmount resourceAmount : event.getResourceCost()) {
					if (resourceAmount.getResourceType() == ResourceType.COIN) {
						resourceAmount.setAmount(resourceAmount.getAmount() - 3);
						return;
					}
				}
			}
		}));
		CardsHandler.CARDS_LEADER.add(new LeaderCardModifier("Santa Rita", 7, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.FAITH_POINT, 8)))))), "Each time you receive wood, stone, coins, or servants as an\nimmediate effect from Development Cards (not from an action space), you receive the\nresources twice.", new Modifier<EventGainResources>(EventGainResources.class)
		{
			@Override
			public void apply(EventGainResources event)
			{
				if (event.getResourcesSource() != ResourcesSource.DEVELOPMENT_CARDS) {
					return;
				}
				for (ResourceAmount resourceAmount : event.getResourceAmounts()) {
					if (resourceAmount.getResourceType() != ResourceType.COIN && resourceAmount.getResourceType() != ResourceType.SERVANT && resourceAmount.getResourceType() != ResourceType.STONE && resourceAmount.getResourceType() != ResourceType.WOOD) {
						continue;
					}
					resourceAmount.setAmount(resourceAmount.getAmount() * 2);
				}
			}
		}));
		CardsHandler.CARDS_LEADER.add(new LeaderCardModifier("Sigismondo Malatesta", 8, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.FAITH_POINT, 3), new ResourceAmount(ResourceType.MILITARY_POINT, 7)))))), "Your uncolored Family Member has a bonus of +3 on its value.\n(You can increase its value by spending servants or if you have Character Cards with\nthis effect.)", new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
					event.setFamilyMemberValue(event.getFamilyMemberValue() + 3);
				}
			}
		}));
		CardsHandler.CARDS_LEADER.add(new LeaderCardModifier("Sisto IV", 9, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.COIN, 6), new ResourceAmount(ResourceType.SERVANT, 6), new ResourceAmount(ResourceType.STONE, 6), new ResourceAmount(ResourceType.WOOD, 6)))))), "You gain 5 additional Victory Points when you support the\nChurch in a Vatican Report phase.", new Modifier<EventChurchSupport>(EventChurchSupport.class)
		{
			@Override
			public void apply(EventChurchSupport event)
			{
				event.setVictoryPoints(event.getVictoryPoints() + 5);
			}
		}));
		CardsHandler.CARDS_LEADER.add(new LeaderCardReward("Bartolomeo Colleoni", 10, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new CardAmount(CardType.TERRITORY, 4), new CardAmount(CardType.VENTURE, 2))), new ArrayList<>()))), "Gain 4 Victory Points.", new Reward(null, new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.VICTORY_POINT, 4))))));
		CardsHandler.CARDS_LEADER.add(new LeaderCardReward("Cosimo de' Medici", 11, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new CardAmount(CardType.BUILDING, 4), new CardAmount(CardType.CHARACTER, 2))), new ArrayList<>()))), "Receive 3 servants and gain 1 Victory Point.", new Reward(null, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.SERVANT, 3), new ResourceAmount(ResourceType.VICTORY_POINT, 1))))));
		CardsHandler.CARDS_LEADER.add(new LeaderCardReward("Federico da Montefeltro", 12, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.TERRITORY, 5))), new ArrayList<>()))), "One of your colored Family Members has a value of 6,\nregardless of its related die.", new Reward(new ActionRewardTemporaryModifier(), new ArrayList<>())));
		CardsHandler.CARDS_LEADER.add(new LeaderCardReward("Francesco Sforza", 13, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.VENTURE, 5))), new ArrayList<>()))), "Perform a Harvest action at value 1. (You can increase\nthis action value only by spending servants; you can’t increase it with Farmer or Peasant\nDevelopment Cards.", new Reward(new ActionRewardHarvest(1), new ArrayList<>())));
		CardsHandler.CARDS_LEADER.add(new LeaderCardReward("Giovanni dalle Bande Nere", 14, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.MILITARY_POINT, 12)))))), "Receive 1 wood, 1 stone, and 1 coin.", new Reward(null, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.COIN, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.WOOD, 1))))));
		CardsHandler.CARDS_LEADER.add(new LeaderCardReward("Girolamo Savonarola", 15, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.COIN, 18)))))), "Gain 1 Faith Point.", new Reward(null, new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.FAITH_POINT, 1))))));
		CardsHandler.CARDS_LEADER.add(new LeaderCardReward("Leonardo da Vinci", 16, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new CardAmount(CardType.CHARACTER, 4), new CardAmount(CardType.TERRITORY, 2))), new ArrayList<>()))), "Perform a Production action at value 0. (You can increase\nthis action value only by spending servants; you can’t increase it with Artisan or Scholar\nDevelopment Cards.)", new Reward(new ActionRewardProduction(0), new ArrayList<>())));
		CardsHandler.CARDS_LEADER.add(new LeaderCardReward("Ludovico III Gonzaga", 17, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.SERVANT, 15)))))), "Receive 1 Council Privilege.", new Reward(null, new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1))))));
		CardsHandler.CARDS_LEADER.add(new LeaderCardReward("Michelangelo Buonarroti", 18, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.STONE, 10)))))), "Receive 3 coins.", new Reward(null, new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.COIN, 3))))));
		CardsHandler.CARDS_LEADER.add(new LeaderCardReward("Sandro Botticelli", 19, new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.WOOD, 10)))))), "Gain 2 Military Points and 1 Victory Point.", new Reward(null, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.MILITARY_POINT, 2), new ResourceAmount(ResourceType.VICTORY_POINT, 1))))));
	}

	private final Map<Row, DevelopmentCard> currentDevelopmentCardsBuilding = new EnumMap<>(Row.class);
	private final Map<Row, DevelopmentCard> currentDevelopmentCardsCharacter = new EnumMap<>(Row.class);
	private final Map<Row, DevelopmentCard> currentDevelopmentCardsTerritory = new EnumMap<>(Row.class);
	private final Map<Row, DevelopmentCard> currentDevelopmentCardsVenture = new EnumMap<>(Row.class);
	private final Map<CardType, Map<Row, DevelopmentCard>> currentDevelopmentCards = new EnumMap<>(CardType.class);

	public CardsHandler()
	{
		this.currentDevelopmentCards.put(CardType.BUILDING, this.currentDevelopmentCardsBuilding);
		this.currentDevelopmentCards.put(CardType.CHARACTER, this.currentDevelopmentCardsCharacter);
		this.currentDevelopmentCards.put(CardType.TERRITORY, this.currentDevelopmentCardsTerritory);
		this.currentDevelopmentCards.put(CardType.VENTURE, this.currentDevelopmentCardsVenture);
	}

	public void addDevelopmentCard(DevelopmentCardBuilding developmentCard, Row row)
	{
		this.currentDevelopmentCardsBuilding.put(row, developmentCard);
	}

	public void addDevelopmentCard(DevelopmentCardCharacter developmentCard, Row row)
	{
		this.currentDevelopmentCardsCharacter.put(row, developmentCard);
	}

	public void addDevelopmentCard(DevelopmentCardTerritory developmentCard, Row row)
	{
		this.currentDevelopmentCardsTerritory.put(row, developmentCard);
	}

	public void addDevelopmentCard(DevelopmentCardVenture developmentCard, Row row)
	{
		this.currentDevelopmentCardsVenture.put(row, developmentCard);
	}

	public static Map<CardType, Class<? extends DevelopmentCard>> getDevelopmentCardsTypes()
	{
		return CardsHandler.DEVELOPMENT_CARDS_TYPES;
	}

	public static List<LeaderCard> getCardsLeader()
	{
		return CardsHandler.CARDS_LEADER;
	}

	public Map<CardType, Map<Row, DevelopmentCard>> getCurrentDevelopmentCards()
	{
		return this.currentDevelopmentCards;
	}
}
