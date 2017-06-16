package it.polimi.ingsw.lim.server.game.cards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.utils.*;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.actionrewards.*;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardModifier;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.events.EventChurchSupport;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.*;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.logging.Level;

public class CardsHandler
{
	private static final Map<CardType, Class<? extends DevelopmentCard>> DEVELOPMENT_CARDS_TYPES = new EnumMap<>(CardType.class);

	static {
		CardsHandler.DEVELOPMENT_CARDS_TYPES.put(CardType.BUILDING, DevelopmentCardBuilding.class);
		CardsHandler.DEVELOPMENT_CARDS_TYPES.put(CardType.CHARACTER, DevelopmentCardCharacter.class);
		CardsHandler.DEVELOPMENT_CARDS_TYPES.put(CardType.TERRITORY, DevelopmentCardTerritory.class);
		CardsHandler.DEVELOPMENT_CARDS_TYPES.put(CardType.VENTURE, DevelopmentCardVenture.class);
	}

	public static final Map<Period, List<DevelopmentCardBuilding>> DEVELOPMENT_CARDS_BUILDING = new DevelopmentCardsBuildingBuilder("/json/development_cards_building.json").initialize();
	public static final Map<Period, List<DevelopmentCardCharacter>> DEVELOPMENT_CARDS_CHARACTER = new DevelopmentCardsCharacterBuilder("/json/development_cards_character.json").initialize();
	public static final Map<Period, List<DevelopmentCardTerritory>> DEVELOPMENT_CARDS_TERRITORY = new DevelopmentCardsTerritoryBuilder("/json/development_cards_territory.json").initialize();
	public static final Map<Period, List<DevelopmentCardVenture>> DEVELOPMENT_CARDS_VENTURE = new DevelopmentCardsVentureBuilder("/json/development_cards_venture.json").initialize();
	private static final List<LeaderCard> LEADER_CARDS = new ArrayList<>();

	static {
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(0, "", "Cesare Borgia", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.BUILDING, 3))), new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.COIN, 12), new ResourceAmount(ResourceType.FAITH_POINT, 2)))))), new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class, "You don’t need to satisfy the Military Points requirement when\nyou take Territory Cards.")
		{
			@Override
			public void apply(EventGetDevelopmentCard event)
			{
				event.setIgnoreTerritoriesSlotLock(true);
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(1, "", "Filippo Brunelleschi", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.BUILDING, 5))), new ArrayList<>()))), new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "You don't have to spend 3 coins when you place your Family\nMembers in a Tower that is already occupied.")
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				event.setIgnoreOccupiedTax(true);
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(2, "", "Lucrezia Borgia", "", new ArrayList<>(Arrays.asList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.BUILDING, 6))), new ArrayList<>()), new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.CHARACTER, 6))), new ArrayList<>()), new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.TERRITORY, 6))), new ArrayList<>()), new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.VENTURE, 6))), new ArrayList<>()))), new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "Your colored Family Members have a bonus of +2 on their value. (You can increase their\nvalue by spending servants or if you have Character\nCards with this effect).")
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
		//CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(3, "", "Lorenzo de' Medici", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.VICTORY_POINT, 35)))))), null));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(4, "", "Ludovico Ariosto", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.CHARACTER, 5))), new ArrayList<>()))), new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "You can place your Family Members in occupied action spaces.")
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				event.setIgnoreOccupied(true);
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(5, "", "Ludovico Il Moro", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new CardAmount(CardType.BUILDING, 2), new CardAmount(CardType.CHARACTER, 2), new CardAmount(CardType.TERRITORY, 2), new CardAmount(CardType.VENTURE, 2))), new ArrayList<>()))), new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "Your colored Family Members has a value of 5, regardless of\ntheir related dice. (You can increase their value by spending servants or if you have\nCharacter Cards with this effect).")
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
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(6, "", "Pico Della Mirandola", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new CardAmount(CardType.BUILDING, 2), new CardAmount(CardType.VENTURE, 4))), new ArrayList<>()))), new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class, "When you take Development Cards, you get a discount of\n3 coins (if the card you are taking has coins in its cost.) This is not a discount on the\ncoins you must spend if you take a Development Card from a Tower that’s already\noccupied.")
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
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(7, "", "Santa Rita", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.FAITH_POINT, 8)))))), new Modifier<EventGainResources>(EventGainResources.class, "Each time you receive wood, stone, coins, or servants as an\nimmediate effect from Development Cards (not from an action space), you receive the\nresources twice.")
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
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(8, "", "Sigismondo Malatesta", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.FAITH_POINT, 3), new ResourceAmount(ResourceType.MILITARY_POINT, 7)))))), new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "Your uncolored Family Member has a bonus of +3 on its value.\n(You can increase its value by spending servants or if you have Character Cards with\nthis effect).")
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
					event.setFamilyMemberValue(event.getFamilyMemberValue() + 3);
				}
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(9, "", "Sisto IV", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.COIN, 6), new ResourceAmount(ResourceType.SERVANT, 6), new ResourceAmount(ResourceType.STONE, 6), new ResourceAmount(ResourceType.WOOD, 6)))))), new Modifier<EventChurchSupport>(EventChurchSupport.class, "You gain 5 additional Victory Points when you support the\nChurch in a Vatican Report phase.")
		{
			@Override
			public void apply(EventChurchSupport event)
			{
				event.setVictoryPoints(event.getVictoryPoints() + 5);
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(10, "", "Bartolomeo Colleoni", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new CardAmount(CardType.TERRITORY, 4), new CardAmount(CardType.VENTURE, 2))), new ArrayList<>()))), new Reward(null, new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.VICTORY_POINT, 4))))));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(11, "", "Cosimo de' Medici", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new CardAmount(CardType.BUILDING, 4), new CardAmount(CardType.CHARACTER, 2))), new ArrayList<>()))), new Reward(null, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.SERVANT, 3), new ResourceAmount(ResourceType.VICTORY_POINT, 1))))));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(12, "", "Federico da Montefeltro", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.TERRITORY, 5))), new ArrayList<>()))), new Reward(new ActionRewardTemporaryModifier("One of your colored Family Members has a value of 6,\nregardless of its related die."), new ArrayList<>())));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(13, "", "Francesco Sforza", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new CardAmount(CardType.VENTURE, 5))), new ArrayList<>()))), new Reward(new ActionRewardHarvest("Perform a Harvest action at value 1. (You can increase\nthis action value only by spending servants; you can’t increase it with Farmer or Peasant\nDevelopment Cards.", 1), new ArrayList<>())));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(14, "", "Giovanni dalle Bande Nere", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.MILITARY_POINT, 12)))))), new Reward(null, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.COIN, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.WOOD, 1))))));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(15, "", "Girolamo Savonarola", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.COIN, 18)))))), new Reward(null, new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.FAITH_POINT, 1))))));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(16, "", "Leonardo da Vinci", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new CardAmount(CardType.CHARACTER, 4), new CardAmount(CardType.TERRITORY, 2))), new ArrayList<>()))), new Reward(new ActionRewardProduction("Perform a Production action at value 0. (You can increase\nthis action value only by spending servants; you can’t increase it with Artisan or Scholar\nDevelopment Cards.)", 0), new ArrayList<>())));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(17, "", "Ludovico III Gonzaga", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.SERVANT, 15)))))), new Reward(null, new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1))))));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(18, "", "Michelangelo Buonarroti", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.STONE, 10)))))), new Reward(null, new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.COIN, 3))))));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(19, "", "Sandro Botticelli", "", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.WOOD, 10)))))), new Reward(null, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.MILITARY_POINT, 2), new ResourceAmount(ResourceType.VICTORY_POINT, 1))))));
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

	public void addDevelopmentCard(Row row, DevelopmentCardBuilding developmentCard)
	{
		this.currentDevelopmentCardsBuilding.put(row, developmentCard);
	}

	public void addDevelopmentCard(Row row, DevelopmentCardCharacter developmentCard)
	{
		this.currentDevelopmentCardsCharacter.put(row, developmentCard);
	}

	public void addDevelopmentCard(Row row, DevelopmentCardTerritory developmentCard)
	{
		this.currentDevelopmentCardsTerritory.put(row, developmentCard);
	}

	public void addDevelopmentCard(Row row, DevelopmentCardVenture developmentCard)
	{
		this.currentDevelopmentCardsVenture.put(row, developmentCard);
	}

	public static Map<CardType, Class<? extends DevelopmentCard>> getDevelopmentCardsTypes()
	{
		return CardsHandler.DEVELOPMENT_CARDS_TYPES;
	}

	public static List<LeaderCard> getLeaderCards()
	{
		return CardsHandler.LEADER_CARDS;
	}

	public Map<CardType, Map<Row, DevelopmentCard>> getCurrentDevelopmentCards()
	{
		return this.currentDevelopmentCards;
	}

	private static class DevelopmentCardsBuildingBuilder
	{
		private static final RuntimeTypeAdapterFactory<ResourceAmount> RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT = RuntimeTypeAdapterFactory.of(ResourceAmount.class).registerSubtype(ResourceAmount.class, "STANDARD").registerSubtype(ResourceAmountMultiplierCard.class, "MULTIPLIER_CARD").registerSubtype(ResourceAmountMultiplierResource.class, "MULTIPLIER_RESOURCE");
		private static final RuntimeTypeAdapterFactory<Modifier> RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER = RuntimeTypeAdapterFactory.of(Modifier.class).registerSubtype(ModifierGetDevelopmentCard.class, "CARD").registerSubtype(ModifierStartHarvest.class, "HARVEST").registerSubtype(ModifierStartProduction.class, "PRODUCTION").registerSubtype(ModifierGetDevelopmentCardReward.class, "MALUS");
		private static final RuntimeTypeAdapterFactory<ActionReward> RUNTIME_TYPE_ADAPTER_FACTORY_EVENT = RuntimeTypeAdapterFactory.of(ActionReward.class).registerSubtype(ActionRewardGetDevelopmentCard.class, "CARD").registerSubtype(ActionRewardHarvest.class, "HARVEST").registerSubtype(ActionRewardProduction.class, "PRODUCTION");
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapterFactory(DevelopmentCardsBuildingBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT).registerTypeAdapterFactory(DevelopmentCardsBuildingBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER).registerTypeAdapterFactory(DevelopmentCardsBuildingBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_EVENT);
		private static final Gson GSON = DevelopmentCardsBuildingBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		DevelopmentCardsBuildingBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		Map<Period, List<DevelopmentCardBuilding>> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return DevelopmentCardsBuildingBuilder.GSON.fromJson(reader, new TypeToken<Map<Period, List<DevelopmentCardBuilding>>>()
				{
				}.getType());
			} catch (IOException exception) {
				Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new HashMap<>();
		}
	}

	private static class DevelopmentCardsCharacterBuilder
	{
		private static final RuntimeTypeAdapterFactory<ResourceAmount> RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT = RuntimeTypeAdapterFactory.of(ResourceAmount.class).registerSubtype(ResourceAmount.class, "STANDARD").registerSubtype(ResourceAmountMultiplierCard.class, "MULTIPLIER_CARD").registerSubtype(ResourceAmountMultiplierResource.class, "MULTIPLIER_RESOURCE");
		private static final RuntimeTypeAdapterFactory<Modifier> RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER = RuntimeTypeAdapterFactory.of(Modifier.class).registerSubtype(ModifierGetDevelopmentCard.class, "CARD").registerSubtype(ModifierStartHarvest.class, "HARVEST").registerSubtype(ModifierStartProduction.class, "PRODUCTION").registerSubtype(ModifierGetDevelopmentCardReward.class, "MALUS");
		private static final RuntimeTypeAdapterFactory<ActionReward> RUNTIME_TYPE_ADAPTER_FACTORY_EVENT = RuntimeTypeAdapterFactory.of(ActionReward.class).registerSubtype(ActionRewardGetDevelopmentCard.class, "CARD").registerSubtype(ActionRewardHarvest.class, "HARVEST").registerSubtype(ActionRewardProduction.class, "PRODUCTION");
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapterFactory(DevelopmentCardsCharacterBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT).registerTypeAdapterFactory(DevelopmentCardsCharacterBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER).registerTypeAdapterFactory(DevelopmentCardsCharacterBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_EVENT);
		private static final Gson GSON = DevelopmentCardsCharacterBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		DevelopmentCardsCharacterBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		Map<Period, List<DevelopmentCardCharacter>> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return DevelopmentCardsCharacterBuilder.GSON.fromJson(reader, new TypeToken<Map<Period, List<DevelopmentCardCharacter>>>()
				{
				}.getType());
			} catch (IOException exception) {
				Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new HashMap<>();
		}
	}

	private static class DevelopmentCardsTerritoryBuilder
	{
		private static final RuntimeTypeAdapterFactory<ResourceAmount> RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT = RuntimeTypeAdapterFactory.of(ResourceAmount.class).registerSubtype(ResourceAmount.class, "STANDARD").registerSubtype(ResourceAmountMultiplierCard.class, "MULTIPLIER_CARD").registerSubtype(ResourceAmountMultiplierResource.class, "MULTIPLIER_RESOURCE");
		private static final RuntimeTypeAdapterFactory<Modifier> RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER = RuntimeTypeAdapterFactory.of(Modifier.class).registerSubtype(ModifierGetDevelopmentCard.class, "CARD").registerSubtype(ModifierStartHarvest.class, "HARVEST").registerSubtype(ModifierStartProduction.class, "PRODUCTION").registerSubtype(ModifierGetDevelopmentCardReward.class, "MALUS");
		private static final RuntimeTypeAdapterFactory<ActionReward> RUNTIME_TYPE_ADAPTER_FACTORY_EVENT = RuntimeTypeAdapterFactory.of(ActionReward.class).registerSubtype(ActionRewardGetDevelopmentCard.class, "CARD").registerSubtype(ActionRewardHarvest.class, "HARVEST").registerSubtype(ActionRewardProduction.class, "PRODUCTION");
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapterFactory(DevelopmentCardsTerritoryBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT).registerTypeAdapterFactory(DevelopmentCardsTerritoryBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER).registerTypeAdapterFactory(DevelopmentCardsTerritoryBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_EVENT);
		private static final Gson GSON = DevelopmentCardsTerritoryBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		DevelopmentCardsTerritoryBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		Map<Period, List<DevelopmentCardTerritory>> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return DevelopmentCardsTerritoryBuilder.GSON.fromJson(reader, new TypeToken<Map<Period, List<DevelopmentCardTerritory>>>()
				{
				}.getType());
			} catch (IOException exception) {
				Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new HashMap<>();
		}
	}

	private static class DevelopmentCardsVentureBuilder
	{
		private static final RuntimeTypeAdapterFactory<ResourceAmount> RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT = RuntimeTypeAdapterFactory.of(ResourceAmount.class).registerSubtype(ResourceAmount.class, "STANDARD").registerSubtype(ResourceAmountMultiplierCard.class, "MULTIPLIER_CARD").registerSubtype(ResourceAmountMultiplierResource.class, "MULTIPLIER_RESOURCE");
		private static final RuntimeTypeAdapterFactory<Modifier> RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER = RuntimeTypeAdapterFactory.of(Modifier.class).registerSubtype(ModifierGetDevelopmentCard.class, "CARD").registerSubtype(ModifierStartHarvest.class, "HARVEST").registerSubtype(ModifierStartProduction.class, "PRODUCTION").registerSubtype(ModifierGetDevelopmentCardReward.class, "MALUS");
		private static final RuntimeTypeAdapterFactory<ActionReward> RUNTIME_TYPE_ADAPTER_FACTORY_EVENT = RuntimeTypeAdapterFactory.of(ActionReward.class).registerSubtype(ActionRewardGetDevelopmentCard.class, "CARD").registerSubtype(ActionRewardHarvest.class, "HARVEST").registerSubtype(ActionRewardProduction.class, "PRODUCTION");
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapterFactory(DevelopmentCardsVentureBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT).registerTypeAdapterFactory(DevelopmentCardsVentureBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER).registerTypeAdapterFactory(DevelopmentCardsVentureBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_EVENT);
		private static final Gson GSON = DevelopmentCardsVentureBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		DevelopmentCardsVentureBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		Map<Period, List<DevelopmentCardVenture>> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return DevelopmentCardsVentureBuilder.GSON.fromJson(reader, new TypeToken<Map<Period, List<DevelopmentCardVenture>>>()
				{
				}.getType());
			} catch (IOException exception) {
				Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new HashMap<>();
		}
	}
}
