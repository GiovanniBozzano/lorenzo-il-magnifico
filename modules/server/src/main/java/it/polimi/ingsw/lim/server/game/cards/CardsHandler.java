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
import it.polimi.ingsw.lim.server.game.events.EventPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.*;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.logging.Level;

/**
 * <p>This class handles a match cards information. It is used to store game
 * cards data.
 */
public class CardsHandler
{
	private static final Map<Period, List<DevelopmentCardBuilding>> DEVELOPMENT_CARDS_BUILDING = new DevelopmentCardsBuildingBuilder("/json/development_cards_building.json").initialize();
	private static final Map<Period, List<DevelopmentCardCharacter>> DEVELOPMENT_CARDS_CHARACTER = new DevelopmentCardsCharacterBuilder("/json/development_cards_character.json").initialize();
	private static final Map<Period, List<DevelopmentCardTerritory>> DEVELOPMENT_CARDS_TERRITORY = new DevelopmentCardsTerritoryBuilder("/json/development_cards_territory.json").initialize();
	private static final Map<Period, List<DevelopmentCardVenture>> DEVELOPMENT_CARDS_VENTURE = new DevelopmentCardsVentureBuilder("/json/development_cards_venture.json").initialize();
	private static final List<LeaderCard> LEADER_CARDS = new ArrayList<>();

	static {
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(0, "/images/leader_cards/leader_card_0.png", "Francesco Sforza", "E per dirlo ad un tratto non ci fu guerra famosa nell’Italia, che Francesco Sforza non\nvi si trovasse, e le Repubbliche, Prencipi, Re e Papi andavano a gara per haverlo al suo\nsevigio.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new DevelopmentCardAmount(CardType.VENTURE, 5))), new ArrayList<>()))), new Reward(new ActionRewardHarvest("Perform a Harvest action at value 1. (You can increase\nthis action value only by spending servants; you can’t increase it with Farmer or Peasant\nDevelopment Cards.", 1, false), new ArrayList<>())));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(1, "/images/leader_cards/leader_card_1.png", "Ludovico Ariosto", "Io desidero intendere da voi Alessandro fratel, compar mio Bagno, S’in la Cort’è memoria\npiù di noi; Se più il Signor m’accusa; se compagno Per me si lieva.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new DevelopmentCardAmount(CardType.CHARACTER, 5))), new ArrayList<>()))), new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "You can place your Family Members in occupied action spaces.")
		{
			@Override
			public void setEventClass()
			{
				super.setEventClass(EventPlaceFamilyMember.class);
			}

			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				event.setIgnoreOccupied(true);
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(2, "/images/leader_cards/leader_card_2.png", "Filippo Brunelleschi", "[…] sparuto de la persona […], ma di ingegno tanto elevato che ben si può dire che e’\nci fu donato dal cielo per dar nuova forma alla architettura.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new DevelopmentCardAmount(CardType.BUILDING, 5))), new ArrayList<>()))), new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "You don't have to spend 3 coins when you place your Family\nMembers in a Tower that is already occupied.")
		{
			@Override
			public void setEventClass()
			{
				super.setEventClass(EventPlaceFamilyMember.class);
			}

			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				event.setIgnoreOccupiedTax(true);
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(3, "/images/leader_cards/leader_card_3.png", "Federico da Montefeltro", " […] la gloriosa memoria del Duca Federico, il quale a dì suoi fu lume della Italia. Né\nquivi [Urbino] cosa alcuna volse, se non rarissima et eccellente. ", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new DevelopmentCardAmount(CardType.TERRITORY, 5))), new ArrayList<>()))), new Reward(new ActionRewardTemporaryModifier("One of your colored Family Members has a value of 6,\nregardless of its related die."), new ArrayList<>())));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(4, "/images/leader_cards/leader_card_4.png", "Girolamo Savonarola", "Che se possibile sempre ruminate qualche cosa divota, et quando mangiate, et quando\nlavorate, et quando camminate; [..] et sentirete nel core uno continuo ardore di fiamma\ndi charità.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.COIN, 18)))))), new Reward(null, new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.FAITH_POINT, 1))))));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(5, "/images/leader_cards/leader_card_5.png", "Giovanni dalle Bande Nere", "Egli apprezzava più gli huomini prodi che le ricchezze le quai desiderava per donar a\nloro. ", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.MILITARY_POINT, 12)))))), new Reward(null, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.COIN, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.WOOD, 1))))));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(6, "/images/leader_cards/leader_card_6.png", "Sandro Botticelli", "[…] ancora che agevolmente apprendesse tutto quello che e’ voleva, era nientedimanco\ninquieto sempre, né si contentava di scuola alcuna […].", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.WOOD, 10)))))), new Reward(null, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.MILITARY_POINT, 2), new ResourceAmount(ResourceType.VICTORY_POINT, 1))))));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(7, "/images/leader_cards/leader_card_7.png", "Michelangelo Buonarroti", "Dai quali tutti Michelagnolo molto era accarezzato, et acceso al honorato suo studio, ma\nsopra tutti dal Magnifico, il quale spesse volte il giorno lo faceva chiamare monstrandogli\nsue gioie […].", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.STONE, 10)))))), new Reward(null, new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.COIN, 3))))));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(8, "/images/leader_cards/leader_card_8.png", "Ludovico III Gonzaga", "[…] la qual cosa sopportava con sdegno Lodovico, parendogli che nota infame gli fosse\nl’essergli preposto dal padre il fratello, il quale veramente odiava.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.SERVANT, 15)))))), new Reward(null, new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1))))));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(9, "/images/leader_cards/leader_card_9.png", "Leonardo da Vinci", "Ogniomo senpre si trova nel mezo del mondo en essotto il mezo del suo emisperio e sopra\nil cientro desso mondo.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new DevelopmentCardAmount(CardType.CHARACTER, 4), new DevelopmentCardAmount(CardType.TERRITORY, 2))), new ArrayList<>()))), new Reward(new ActionRewardProductionStart("Perform a Production action at value 0. (You can increase\nthis action value only by spending servants; you can’t increase it with Artisan or Scholar\nDevelopment Cards.)", 0, false), new ArrayList<>())));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(10, "/images/leader_cards/leader_card_10.png", "Pico Della Mirandola", "Ioannes Picus Mirandula merito cognomine phoenix appellatus est, quod in eum,\nDii superi, supra familiae claritatem, omnis corporis, ac animi vel rarissima dona\ncontulerint.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new DevelopmentCardAmount(CardType.BUILDING, 2), new DevelopmentCardAmount(CardType.VENTURE, 4))), new ArrayList<>()))), new Modifier<EventPickDevelopmentCard>(EventPickDevelopmentCard.class, "When you take Development Cards, you get a discount of\n3 coins (if the card you are taking has coins in its cost.) This is not a discount on the\ncoins you must spend if you take a Development Card from a Tower that’s already\noccupied.")
		{
			@Override
			public void setEventClass()
			{
				super.setEventClass(EventPickDevelopmentCard.class);
			}

			@Override
			public void apply(EventPickDevelopmentCard event)
			{
				for (ResourceAmount resourceAmount : event.getResourceCost()) {
					if (resourceAmount.getResourceType() == ResourceType.COIN) {
						resourceAmount.setAmount(resourceAmount.getAmount() - 3);
						return;
					}
				}
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(11, "/images/leader_cards/leader_card_11.png", "Sisto IV", "[…] secretamente trattò, che per mezzo di una congiura fussero ammazzati Lorenzo\ne Giuliano de’ Medici fratelli, e si riordinasse poi quella Repubblica a sua volontà.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.COIN, 6), new ResourceAmount(ResourceType.SERVANT, 6), new ResourceAmount(ResourceType.STONE, 6), new ResourceAmount(ResourceType.WOOD, 6)))))), new Modifier<EventChurchSupport>(EventChurchSupport.class, "You gain 5 additional Victory Points when you support the\nChurch in a Vatican Report phase.")
		{
			@Override
			public void setEventClass()
			{
				super.setEventClass(EventChurchSupport.class);
			}

			@Override
			public void apply(EventChurchSupport event)
			{
				event.setVictoryPoints(event.getVictoryPoints() + 5);
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(12, "/images/leader_cards/leader_card_12.png", "Lucrezia Borgia", "Donna Lucretia, benché avvezza homai a mutar mariti secondo il capriccio et interesse\ndei suoi, […] si trattenne fin che il tempo unico medico di queste passioni le fece volger\nl’animo a più soavi pensieri.", new ArrayList<>(Arrays.asList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new DevelopmentCardAmount(CardType.BUILDING, 6))), new ArrayList<>()), new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new DevelopmentCardAmount(CardType.CHARACTER, 6))), new ArrayList<>()), new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new DevelopmentCardAmount(CardType.TERRITORY, 6))), new ArrayList<>()), new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new DevelopmentCardAmount(CardType.VENTURE, 6))), new ArrayList<>()))), new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "Your colored Family Members have a bonus of +2 on their value. (You can increase their\nvalue by spending servants or if you have Character\nCards with this effect).")
		{
			@Override
			public void setEventClass()
			{
				super.setEventClass(EventPlaceFamilyMember.class);
			}

			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
					return;
				}
				event.setFamilyMemberValue(event.getFamilyMemberValue() + 2);
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(13, "/images/leader_cards/leader_card_13.png", "Sigismondo Malatesta", "Era a campo la maistà del re de Ragona. […] el fé levare de campo cum la soe gente e\ncum lo altre di fiorentini, cum gram danno e poco onore del re.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.FAITH_POINT, 3), new ResourceAmount(ResourceType.MILITARY_POINT, 7)))))), new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "Your uncolored Family Member has a bonus of +3 on its value.\n(You can increase its value by spending servants or if you have Character Cards with\nthis effect).")
		{
			@Override
			public void setEventClass()
			{
				super.setEventClass(EventPlaceFamilyMember.class);
			}

			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
					event.setFamilyMemberValue(event.getFamilyMemberValue() + 3);
				}
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(14, "/images/leader_cards/leader_card_14.png", "Lorenzo de' Medici", "Vir ad omnia summa natus, et qui flantem reflantemque totiens fortunam usque adeo\nsit alterna velificatione moderatus.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.VICTORY_POINT, 35)))))), new Reward(new ActionRewardLorenzoDeMediciLeader("Copy the ability of another Leader Card already played by\nanother player. Once you decide the ability to copy, it can’t be changed."), new ArrayList<>())));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(15, "/images/leader_cards/leader_card_15.png", "Ludovico Il Moro", "Ludovicum Sfortiam Mediolanensium principem, cui Moro cognomen fuit, nequaquam\na suscedine oris, quod esset aequo pallidior ita vocatum ferunt, quod pro insigni gestabat\nMori arboris.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new DevelopmentCardAmount(CardType.BUILDING, 2), new DevelopmentCardAmount(CardType.CHARACTER, 2), new DevelopmentCardAmount(CardType.TERRITORY, 2), new DevelopmentCardAmount(CardType.VENTURE, 2))), new ArrayList<>()))), new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "Your colored Family Members has a value of 5, regardless of\ntheir related dice. (You can increase their value by spending servants or if you have\nCharacter Cards with this effect).")
		{
			@Override
			public void setEventClass()
			{
				super.setEventClass(EventPlaceFamilyMember.class);
			}

			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
					return;
				}
				event.setFamilyMemberValue(5);
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(16, "/images/leader_cards/leader_card_16.png", "Cesare Borgia", "Cesarem Borgiam, qui sanguinario ingenio, immanique saevitia veteres tyrannos\naequasse censeri potest, viroso sanguine, execrabile semine progenitum ferunt.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Collections.singletonList(new DevelopmentCardAmount(CardType.BUILDING, 3))), new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.COIN, 12), new ResourceAmount(ResourceType.FAITH_POINT, 2)))))), new Modifier<EventPickDevelopmentCard>(EventPickDevelopmentCard.class, "You don’t need to satisfy the Military Points requirement when\nyou take Territory Cards.")
		{
			@Override
			public void setEventClass()
			{
				super.setEventClass(EventPickDevelopmentCard.class);
			}

			@Override
			public void apply(EventPickDevelopmentCard event)
			{
				event.setIgnoreTerritoriesSlotLock(true);
			}
		}));
		CardsHandler.LEADER_CARDS.add(new LeaderCardModifier(17, "/images/leader_cards/leader_card_17.png", "Santa Rita", "Fu talmente abbracciata la santa astinenza, e l’aspro vestire dalla nostra Beata Rita,\nche chi la mirava, restava meravigliato, e quasi fuor di se stesso rimaneva.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(), new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.FAITH_POINT, 8)))))), new Modifier<EventGainResources>(EventGainResources.class, "Each time you receive wood, stone, coins, or servants as an\nimmediate effect from Development Cards (not from an action space), you receive the\nresources twice.")
		{
			@Override
			public void setEventClass()
			{
				super.setEventClass(EventGainResources.class);
			}

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
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(18, "/images/leader_cards/leader_card_18.png", "Cosimo de' Medici", "Debebunt igitur Medici magno Cosmo omnis Medicea, et Florentina posteritas.", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new DevelopmentCardAmount(CardType.BUILDING, 4), new DevelopmentCardAmount(CardType.CHARACTER, 2))), new ArrayList<>()))), new Reward(null, new ArrayList<>(Arrays.asList(new ResourceAmount(ResourceType.SERVANT, 3), new ResourceAmount(ResourceType.VICTORY_POINT, 1))))));
		CardsHandler.LEADER_CARDS.add(new LeaderCardReward(19, "/images/leader_cards/leader_card_19.png", "Bartolomeo Colleoni", "Et era allhor frequente per le bocche del volgo un sì fatto motto: «Havere il Coglione\nallo Sforza, il gioco di maniera in man concio, che non facendo ei torto alle carte più\nnon potea perdere».", new ArrayList<>(Collections.singletonList(new LeaderCardConditionsOption(new ArrayList<>(Arrays.asList(new DevelopmentCardAmount(CardType.TERRITORY, 4), new DevelopmentCardAmount(CardType.VENTURE, 2))), new ArrayList<>()))), new Reward(null, new ArrayList<>(Collections.singletonList(new ResourceAmount(ResourceType.VICTORY_POINT, 4))))));
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

	public static LeaderCard getleaderCardFromIndex(int index)
	{
		for (LeaderCard leaderCard : CardsHandler.LEADER_CARDS) {
			if (leaderCard.getIndex() == index) {
				return leaderCard;
			}
		}
		throw new NoSuchElementException();
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

	public static Map<Period, List<DevelopmentCardBuilding>> getDevelopmentCardsBuilding()
	{
		return CardsHandler.DEVELOPMENT_CARDS_BUILDING;
	}

	public static Map<Period, List<DevelopmentCardCharacter>> getDevelopmentCardsCharacter()
	{
		return CardsHandler.DEVELOPMENT_CARDS_CHARACTER;
	}

	public static Map<Period, List<DevelopmentCardTerritory>> getDevelopmentCardsTerritory()
	{
		return CardsHandler.DEVELOPMENT_CARDS_TERRITORY;
	}

	public static Map<Period, List<DevelopmentCardVenture>> getDevelopmentCardsVenture()
	{
		return CardsHandler.DEVELOPMENT_CARDS_VENTURE;
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
		private static final RuntimeTypeAdapterFactory<Modifier> RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER = RuntimeTypeAdapterFactory.of(Modifier.class).registerSubtype(ModifierPickDevelopmentCard.class, "CARD").registerSubtype(ModifierHarvest.class, "HARVEST").registerSubtype(ModifierProductionStart.class, "PRODUCTION").registerSubtype(ModifierPickDevelopmentCardReward.class, "MALUS");
		private static final RuntimeTypeAdapterFactory<ActionReward> RUNTIME_TYPE_ADAPTER_FACTORY_ACTION_REWARD = RuntimeTypeAdapterFactory.of(ActionReward.class).registerSubtype(ActionRewardPickDevelopmentCard.class, "CARD").registerSubtype(ActionRewardHarvest.class, "HARVEST").registerSubtype(ActionRewardProductionStart.class, "PRODUCTION");
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapterFactory(DevelopmentCardsBuildingBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT).registerTypeAdapterFactory(DevelopmentCardsBuildingBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER).registerTypeAdapterFactory(DevelopmentCardsBuildingBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_ACTION_REWARD);
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
			return new EnumMap<>(Period.class);
		}
	}

	private static class DevelopmentCardsCharacterBuilder
	{
		private static final RuntimeTypeAdapterFactory<ResourceAmount> RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT = RuntimeTypeAdapterFactory.of(ResourceAmount.class).registerSubtype(ResourceAmount.class, "STANDARD").registerSubtype(ResourceAmountMultiplierCard.class, "MULTIPLIER_CARD").registerSubtype(ResourceAmountMultiplierResource.class, "MULTIPLIER_RESOURCE");
		private static final RuntimeTypeAdapterFactory<Modifier> RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER = RuntimeTypeAdapterFactory.of(Modifier.class).registerSubtype(ModifierPickDevelopmentCard.class, "CARD").registerSubtype(ModifierHarvest.class, "HARVEST").registerSubtype(ModifierProductionStart.class, "PRODUCTION").registerSubtype(ModifierPickDevelopmentCardReward.class, "MALUS");
		private static final RuntimeTypeAdapterFactory<ActionReward> RUNTIME_TYPE_ADAPTER_FACTORY_ACTION_REWARD = RuntimeTypeAdapterFactory.of(ActionReward.class).registerSubtype(ActionRewardPickDevelopmentCard.class, "CARD").registerSubtype(ActionRewardHarvest.class, "HARVEST").registerSubtype(ActionRewardProductionStart.class, "PRODUCTION");
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapterFactory(DevelopmentCardsCharacterBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT).registerTypeAdapterFactory(DevelopmentCardsCharacterBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER).registerTypeAdapterFactory(DevelopmentCardsCharacterBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_ACTION_REWARD);
		private static final Gson GSON = DevelopmentCardsCharacterBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		DevelopmentCardsCharacterBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		Map<Period, List<DevelopmentCardCharacter>> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				Map<Period, List<DevelopmentCardCharacter>> developmentCardsCharacter = DevelopmentCardsCharacterBuilder.GSON.fromJson(reader, new TypeToken<Map<Period, List<DevelopmentCardCharacter>>>()
				{
				}.getType());
				for (List<DevelopmentCardCharacter> periodDevelopmentCardsCharacter : developmentCardsCharacter.values()) {
					for (DevelopmentCardCharacter developmentCardCharacter : periodDevelopmentCardsCharacter) {
						if (developmentCardCharacter.getModifier() != null) {
							developmentCardCharacter.getModifier().setEventClass();
						}
					}
				}
				return developmentCardsCharacter;
			} catch (IOException exception) {
				Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new EnumMap<>(Period.class);
		}
	}

	private static class DevelopmentCardsTerritoryBuilder
	{
		private static final RuntimeTypeAdapterFactory<ResourceAmount> RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT = RuntimeTypeAdapterFactory.of(ResourceAmount.class).registerSubtype(ResourceAmount.class, "STANDARD").registerSubtype(ResourceAmountMultiplierCard.class, "MULTIPLIER_CARD").registerSubtype(ResourceAmountMultiplierResource.class, "MULTIPLIER_RESOURCE");
		private static final RuntimeTypeAdapterFactory<Modifier> RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER = RuntimeTypeAdapterFactory.of(Modifier.class).registerSubtype(ModifierPickDevelopmentCard.class, "CARD").registerSubtype(ModifierHarvest.class, "HARVEST").registerSubtype(ModifierProductionStart.class, "PRODUCTION").registerSubtype(ModifierPickDevelopmentCardReward.class, "MALUS");
		private static final RuntimeTypeAdapterFactory<ActionReward> RUNTIME_TYPE_ADAPTER_FACTORY_ACTION_REWARD = RuntimeTypeAdapterFactory.of(ActionReward.class).registerSubtype(ActionRewardPickDevelopmentCard.class, "CARD").registerSubtype(ActionRewardHarvest.class, "HARVEST").registerSubtype(ActionRewardProductionStart.class, "PRODUCTION");
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapterFactory(DevelopmentCardsTerritoryBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT).registerTypeAdapterFactory(DevelopmentCardsTerritoryBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER).registerTypeAdapterFactory(DevelopmentCardsTerritoryBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_ACTION_REWARD);
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
			return new EnumMap<>(Period.class);
		}
	}

	private static class DevelopmentCardsVentureBuilder
	{
		private static final RuntimeTypeAdapterFactory<ResourceAmount> RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT = RuntimeTypeAdapterFactory.of(ResourceAmount.class).registerSubtype(ResourceAmount.class, "STANDARD").registerSubtype(ResourceAmountMultiplierCard.class, "MULTIPLIER_CARD").registerSubtype(ResourceAmountMultiplierResource.class, "MULTIPLIER_RESOURCE");
		private static final RuntimeTypeAdapterFactory<Modifier> RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER = RuntimeTypeAdapterFactory.of(Modifier.class).registerSubtype(ModifierPickDevelopmentCard.class, "CARD").registerSubtype(ModifierHarvest.class, "HARVEST").registerSubtype(ModifierProductionStart.class, "PRODUCTION").registerSubtype(ModifierPickDevelopmentCardReward.class, "MALUS");
		private static final RuntimeTypeAdapterFactory<ActionReward> RUNTIME_TYPE_ADAPTER_FACTORY_ACTION_REWARD = RuntimeTypeAdapterFactory.of(ActionReward.class).registerSubtype(ActionRewardPickDevelopmentCard.class, "CARD").registerSubtype(ActionRewardHarvest.class, "HARVEST").registerSubtype(ActionRewardProductionStart.class, "PRODUCTION");
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapterFactory(DevelopmentCardsVentureBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT).registerTypeAdapterFactory(DevelopmentCardsVentureBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER).registerTypeAdapterFactory(DevelopmentCardsVentureBuilder.RUNTIME_TYPE_ADAPTER_FACTORY_ACTION_REWARD);
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
			return new EnumMap<>(Period.class);
		}
	}
}
