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
	public static final DevelopmentCardsDeck<DevelopmentCardBuilding> DEVELOPMENT_CARDS_BUILDING = new DevelopmentCardsDeck.Builder<>(DevelopmentCardBuilding.class, "/json/development_cards_building.json").initialize();
	public static final DevelopmentCardsDeck<DevelopmentCardCharacter> DEVELOPMENT_CARDS_CHARACTER = new DevelopmentCardsDeck.Builder<>(DevelopmentCardCharacter.class, "/json/development_cards_character.json").initialize();
	public static final DevelopmentCardsDeck<DevelopmentCardTerritory> DEVELOPMENT_CARDS_TERRITORY = new DevelopmentCardsDeck.Builder<>(DevelopmentCardTerritory.class, "/json/development_cards_territory.json").initialize();
	public static final DevelopmentCardsDeck<DevelopmentCardVenture> DEVELOPMENT_CARDS_VENTURE = new DevelopmentCardsDeck.Builder<>(DevelopmentCardVenture.class, "/json/development_cards_venture.json").initialize();
	public static final List<CardLeader> CARDS_LEADER = new ArrayList<>();

	static {
		List<CardLeaderConditionsOption> cardLeaderConditionsOptions = new ArrayList<>();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.BUILDING, 3) }, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 12), new ResourceAmount(ResourceType.FAITH_POINT, 2) }));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Cesare Borgia", 0, cardLeaderConditionsOptions, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class)
		{
			@Override
			public void apply(EventGetDevelopmentCard event)
			{
				event.setIgnoreSlotLock(true);
			}
		}));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.BUILDING, 5) }, new ResourceAmount[] {}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Filippo Brunelleschi", 1, cardLeaderConditionsOptions, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				event.setIgnoreOccupiedTax(true);
			}
		}));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.BUILDING, 6) }, new ResourceAmount[] {}));
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.CHARACTER, 6) }, new ResourceAmount[] {}));
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.TERRITORY, 6) }, new ResourceAmount[] {}));
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.VENTURE, 6) }, new ResourceAmount[] {}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Lucrezia Borgia", 2, cardLeaderConditionsOptions, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
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
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 35) }));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Lorenzo de' Medici", 3, cardLeaderConditionsOptions, null));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.CHARACTER, 5) }, new ResourceAmount[] {}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Ludovico Ariosto", 4, cardLeaderConditionsOptions, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				event.setIgnoreOccupied(true);
			}
		}));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.BUILDING, 2), new CardAmount(CardType.CHARACTER, 2), new CardAmount(CardType.TERRITORY, 2), new CardAmount(CardType.VENTURE, 2) }, new ResourceAmount[] {}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Ludovico Il Moro", 5, cardLeaderConditionsOptions, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
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
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.BUILDING, 2), new CardAmount(CardType.VENTURE, 4) }, new ResourceAmount[] {}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Pico Della Mirandola", 6, cardLeaderConditionsOptions, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class)
		{
			@Override
			public void apply(EventGetDevelopmentCard event)
			{
				for (ResourceAmount resourceAmount : event.getCost()) {
					if (resourceAmount.getResourceType() == ResourceType.COIN) {
						resourceAmount.setAmount(resourceAmount.getAmount() - 3);
						return;
					}
				}
			}
		}));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 8) }));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Santa Rita", 7, cardLeaderConditionsOptions, new Modifier<EventGainResources>(EventGainResources.class)
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
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 3), new ResourceAmount(ResourceType.MILITARY_POINT, 7) }));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Sigismondo Malatesta", 8, cardLeaderConditionsOptions, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
					event.setFamilyMemberValue(event.getFamilyMemberValue() + 3);
				}
			}
		}));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 6), new ResourceAmount(ResourceType.SERVANT, 6), new ResourceAmount(ResourceType.STONE, 6), new ResourceAmount(ResourceType.WOOD, 6) }));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Sisto IV", 9, cardLeaderConditionsOptions, new Modifier<EventChurchSupport>(EventChurchSupport.class)
		{
			@Override
			public void apply(EventChurchSupport event)
			{
				event.setVictoryPoints(event.getVictoryPoints() + 5);
			}
		}));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.TERRITORY, 4), new CardAmount(CardType.VENTURE, 2) }, new ResourceAmount[] {}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Bartolomeo Colleoni", 10, cardLeaderConditionsOptions, new Reward(null, new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 4) })));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.BUILDING, 4), new CardAmount(CardType.CHARACTER, 2) }, new ResourceAmount[] {}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Cosimo de' Medici", 11, cardLeaderConditionsOptions, new Reward(null, new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 3), new ResourceAmount(ResourceType.VICTORY_POINT, 1) })));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.TERRITORY, 5) }, new ResourceAmount[] {}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Federico da Montefeltro", 12, cardLeaderConditionsOptions, new Reward(new ActionRewardTemporaryModifier(), new ResourceAmount[] {})));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.VENTURE, 5) }, new ResourceAmount[] {}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Francesco Sforza", 13, cardLeaderConditionsOptions, new Reward(new ActionRewardHarvest(1), new ResourceAmount[] {})));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 12) }));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Giovanni dalle Bande Nere", 14, cardLeaderConditionsOptions, new Reward(null, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 1), new ResourceAmount(ResourceType.STONE, 1), new ResourceAmount(ResourceType.WOOD, 1) })));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 18) }));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Girolamo Savonarola", 15, cardLeaderConditionsOptions, new Reward(null, new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 1) })));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] { new CardAmount(CardType.CHARACTER, 4), new CardAmount(CardType.TERRITORY, 2) }, new ResourceAmount[] {}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Leonardo da Vinci", 16, cardLeaderConditionsOptions, new Reward(new ActionRewardProduction(0), new ResourceAmount[] {})));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 15) }));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Ludovico III Gonzaga", 17, cardLeaderConditionsOptions, new Reward(null, new ResourceAmount[] { new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1) })));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 10) }));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Michelangelo Buonarroti", 18, cardLeaderConditionsOptions, new Reward(null, new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 3) })));
		cardLeaderConditionsOptions.clear();
		cardLeaderConditionsOptions.add(new CardLeaderConditionsOption(new CardAmount[] {}, new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 10) }));
		CardsHandler.CARDS_LEADER.add(new CardLeaderReward("Sandro Botticelli", 19, cardLeaderConditionsOptions, new Reward(null, new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 2), new ResourceAmount(ResourceType.VICTORY_POINT, 1) })));
		cardLeaderConditionsOptions.clear();
	}

	private final Map<Row, DevelopmentCardBuilding> current_development_cards_building = new HashMap<>();
	private final Map<Row, DevelopmentCardCharacter> current_development_cards_character = new HashMap<>();
	private final Map<Row, DevelopmentCardTerritory> current_development_cards_territory = new HashMap<>();
	private final Map<Row, DevelopmentCardVenture> current_development_cards_venture = new HashMap<>();
	private final Map<CardType, Map<Row, ? extends DevelopmentCard>> CURRENT_DEVELOPMENT_CARDS = new HashMap<>();

	public DevelopmentCard getDevelopmentCard(CardType cardType, Row row)
	{
		return this.CURRENT_DEVELOPMENT_CARDS.get(cardType).get(row);
	}

	private final DevelopmentCardCharacter[] currentDevelopmentCardsCharacters = new DevelopmentCardCharacter[4];
	private final DevelopmentCardTerritory[] currentDevelopmentCardsTerritory = new DevelopmentCardTerritory[4];
	private final DevelopmentCardVenture[] currentDevelopmentCardsVenture = new DevelopmentCardVenture[4];
	private final DevelopmentCardBuilding[] currentDevelopmentCardsBuilding = new DevelopmentCardBuilding[4];

	public DevelopmentCardCharacter[] getCurrentDevelopmentCardsCharacters()
	{
		return this.currentDevelopmentCardsCharacters;
	}

	public DevelopmentCardTerritory[] getCurrentDevelopmentCardsTerritory()
	{
		return this.currentDevelopmentCardsTerritory;
	}

	public DevelopmentCardVenture[] getCurrentDevelopmentCardsVenture()
	{
		return this.currentDevelopmentCardsVenture;
	}

	public DevelopmentCardBuilding[] getCurrentDevelopmentCardsBuilding()
	{
		return this.currentDevelopmentCardsBuilding;
	}
}
