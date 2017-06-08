package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardHarvest;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardProduction;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardTemporaryModifier;
import it.polimi.ingsw.lim.server.game.cards.leaders.CardLeaderModifier;
import it.polimi.ingsw.lim.server.game.cards.leaders.CardLeaderReward;
import it.polimi.ingsw.lim.server.game.events.EventChurchSupport;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.CardAmount;
import it.polimi.ingsw.lim.server.game.utils.CardLeaderConditionsOption;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardsHandler
{
	public static final Map<CardType, Class<? extends DevelopmentCard>> DEVELOPMENT_CARDS_TYPES = new HashMap<>();

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
	public static final List<CardLeader> CARDS_LEADER = new ArrayList<>();

	static {
		List<CardLeaderConditionsOption> conditionsOptions = new ArrayList<>();
		List<CardAmount> conditionsCardsAmount = new ArrayList<>();
		conditionsCardsAmount.add(new CardAmount(CardType.BUILDING, 3));
		List<ResourceAmount> conditionsResourcesAmount = new ArrayList<>();
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.COIN, 12));
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.FAITH_POINT, 2));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Cesare Borgia", 0, conditionsOptions, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class)
		{
			@Override
			public void apply(EventGetDevelopmentCard event)
			{
				event.setIgnoreSlotLock(true);
			}
		}));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.BUILDING, 5));
		conditionsResourcesAmount.clear();
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Filippo Brunelleschi", 1, conditionsOptions, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				event.setIgnoreOccupiedTax(true);
			}
		}));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.BUILDING, 6));
		conditionsResourcesAmount.clear();
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.CHARACTER, 6));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.TERRITORY, 6));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.VENTURE, 6));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Lucrezia Borgia", 2, conditionsOptions, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
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
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsResourcesAmount.clear();
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.VICTORY_POINT, 35));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Lorenzo de' Medici", 3, conditionsOptions, null));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.CHARACTER, 5));
		conditionsResourcesAmount.clear();
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Ludovico Ariosto", 4, conditionsOptions, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				event.setIgnoreOccupied(true);
			}
		}));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.BUILDING, 2));
		conditionsCardsAmount.add(new CardAmount(CardType.CHARACTER, 2));
		conditionsCardsAmount.add(new CardAmount(CardType.TERRITORY, 2));
		conditionsCardsAmount.add(new CardAmount(CardType.VENTURE, 2));
		conditionsResourcesAmount.clear();
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Ludovico Il Moro", 5, conditionsOptions, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
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
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.BUILDING, 2));
		conditionsCardsAmount.add(new CardAmount(CardType.VENTURE, 4));
		conditionsResourcesAmount.clear();
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Pico Della Mirandola", 6, conditionsOptions, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class)
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
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsResourcesAmount.clear();
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.FAITH_POINT, 8));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Santa Rita", 7, conditionsOptions, new Modifier<EventGainResources>(EventGainResources.class)
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
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsResourcesAmount.clear();
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.FAITH_POINT, 3));
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.MILITARY_POINT, 7));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Sigismondo Malatesta", 8, conditionsOptions, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
					event.setFamilyMemberValue(event.getFamilyMemberValue() + 3);
				}
			}
		}));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsResourcesAmount.clear();
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.COIN, 6));
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.SERVANT, 6));
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.STONE, 6));
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.WOOD, 6));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Sisto IV", 9, conditionsOptions, new Modifier<EventChurchSupport>(EventChurchSupport.class)
		{
			@Override
			public void apply(EventChurchSupport event)
			{
				event.setVictoryPoints(event.getVictoryPoints() + 5);
			}
		}));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.TERRITORY, 4));
		conditionsCardsAmount.add(new CardAmount(CardType.VENTURE, 2));
		conditionsResourcesAmount.clear();
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		List<ResourceAmount> rewardResourcesAmount = new ArrayList<>();
		rewardResourcesAmount.add(new ResourceAmount(ResourceType.VICTORY_POINT, 4));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Bartolomeo Colleoni", 10, conditionsOptions, new Reward(null, rewardResourcesAmount)));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.BUILDING, 4));
		conditionsCardsAmount.add(new CardAmount(CardType.CHARACTER, 2));
		conditionsResourcesAmount.clear();
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		rewardResourcesAmount.clear();
		rewardResourcesAmount.add(new ResourceAmount(ResourceType.SERVANT, 3));
		rewardResourcesAmount.add(new ResourceAmount(ResourceType.VICTORY_POINT, 1));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Cosimo de' Medici", 11, conditionsOptions, new Reward(null, rewardResourcesAmount)));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.TERRITORY, 5));
		conditionsResourcesAmount.clear();
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		rewardResourcesAmount.clear();
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Federico da Montefeltro", 12, conditionsOptions, new Reward(new ActionRewardTemporaryModifier(), rewardResourcesAmount)));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.VENTURE, 5));
		conditionsResourcesAmount.clear();
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		rewardResourcesAmount.clear();
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Francesco Sforza", 13, conditionsOptions, new Reward(new ActionRewardHarvest(1), rewardResourcesAmount)));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsResourcesAmount.clear();
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.MILITARY_POINT, 12));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		rewardResourcesAmount.clear();
		rewardResourcesAmount.add(new ResourceAmount(ResourceType.COIN, 1));
		rewardResourcesAmount.add(new ResourceAmount(ResourceType.STONE, 1));
		rewardResourcesAmount.add(new ResourceAmount(ResourceType.WOOD, 1));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Giovanni dalle Bande Nere", 14, conditionsOptions, new Reward(null, rewardResourcesAmount)));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsResourcesAmount.clear();
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.COIN, 18));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		rewardResourcesAmount.clear();
		rewardResourcesAmount.add(new ResourceAmount(ResourceType.FAITH_POINT, 1));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Girolamo Savonarola", 15, conditionsOptions, new Reward(null, rewardResourcesAmount)));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsCardsAmount.add(new CardAmount(CardType.CHARACTER, 4));
		conditionsCardsAmount.add(new CardAmount(CardType.TERRITORY, 2));
		conditionsResourcesAmount.clear();
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		rewardResourcesAmount.clear();
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Leonardo da Vinci", 16, conditionsOptions, new Reward(new ActionRewardProduction(0), rewardResourcesAmount)));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsResourcesAmount.clear();
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.SERVANT, 15));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		rewardResourcesAmount.clear();
		rewardResourcesAmount.add(new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Ludovico III Gonzaga", 17, conditionsOptions, new Reward(null, rewardResourcesAmount)));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsResourcesAmount.clear();
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.STONE, 10));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		rewardResourcesAmount.clear();
		rewardResourcesAmount.add(new ResourceAmount(ResourceType.COIN, 3));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Michelangelo Buonarroti", 18, conditionsOptions, new Reward(null, rewardResourcesAmount)));
		conditionsOptions.clear();
		conditionsCardsAmount.clear();
		conditionsResourcesAmount.clear();
		conditionsResourcesAmount.add(new ResourceAmount(ResourceType.WOOD, 10));
		conditionsOptions.add(new CardLeaderConditionsOption(conditionsCardsAmount, conditionsResourcesAmount));
		rewardResourcesAmount.clear();
		rewardResourcesAmount.add(new ResourceAmount(ResourceType.MILITARY_POINT, 2));
		rewardResourcesAmount.add(new ResourceAmount(ResourceType.VICTORY_POINT, 1));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Sandro Botticelli", 19, conditionsOptions, new Reward(null, rewardResourcesAmount)));
	}

	public Map<Row, DevelopmentCardBuilding> getCurrentDevelopmentCardsBuilding()
	{
		return this.currentDevelopmentCardsBuilding;
	}

	public Map<Row, DevelopmentCardCharacter> getCurrentDevelopmentCardsCharacter()
	{
		return this.currentDevelopmentCardsCharacter;
	}

	public Map<Row, DevelopmentCardTerritory> getCurrentDevelopmentCardsTerritory()
	{
		return this.currentDevelopmentCardsTerritory;
	}

	public Map<Row, DevelopmentCardVenture> getCurrentDevelopmentCardsVenture()
	{
		return this.currentDevelopmentCardsVenture;
	}

	private final Map<Row, DevelopmentCardBuilding> currentDevelopmentCardsBuilding = new HashMap<>();
	private final Map<Row, DevelopmentCardCharacter> currentDevelopmentCardsCharacter = new HashMap<>();
	private final Map<Row, DevelopmentCardTerritory> currentDevelopmentCardsTerritory = new HashMap<>();
	private final Map<Row, DevelopmentCardVenture> currentDevelopmentCardsVenture = new HashMap<>();
	private final Map<CardType, Map<Row, ? extends DevelopmentCard>> currentDevelopmentCards = new HashMap<>();

	public CardsHandler()
	{
		this.currentDevelopmentCards.put(CardType.BUILDING, this.currentDevelopmentCardsBuilding);
		this.currentDevelopmentCards.put(CardType.CHARACTER, this.currentDevelopmentCardsCharacter);
		this.currentDevelopmentCards.put(CardType.TERRITORY, this.currentDevelopmentCardsTerritory);
		this.currentDevelopmentCards.put(CardType.VENTURE, this.currentDevelopmentCardsVenture);
	}

	public DevelopmentCard getDevelopmentCard(CardType cardType, Row row)
	{
		return this.currentDevelopmentCards.get(cardType).get(row);
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
}
