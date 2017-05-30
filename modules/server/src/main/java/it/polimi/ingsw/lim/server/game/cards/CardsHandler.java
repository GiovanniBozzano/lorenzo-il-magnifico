package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.cards.leaders.CardLeaderModifier;
import it.polimi.ingsw.lim.server.game.events.EventChurchSupport;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.CardLeaderConditions;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;

import java.util.ArrayList;
import java.util.List;

public class CardsHandler
{
	public static final DevelopmentCardsDeck<DevelopmentCardBuilding> DEVELOPMENT_CARDS_BUILDING = new DevelopmentCardsDeck.Builder<>(DevelopmentCardBuilding.class, "/json/development_cards_building.json").initialize();
	public static final DevelopmentCardsDeck<DevelopmentCardCharacter> DEVELOPMENT_CARDS_CHARACTER = new DevelopmentCardsDeck.Builder<>(DevelopmentCardCharacter.class, "/json/development_cards_character.json").initialize();
	public static final DevelopmentCardsDeck<DevelopmentCardTerritory> DEVELOPMENT_CARDS_TERRITORY = new DevelopmentCardsDeck.Builder<>(DevelopmentCardTerritory.class, "/json/development_cards_territory.json").initialize();
	public static final DevelopmentCardsDeck<DevelopmentCardVenture> DEVELOPMENT_CARDS_VENTURE = new DevelopmentCardsDeck.Builder<>(DevelopmentCardVenture.class, "/json/development_cards_venture.json").initialize();
	public static final List<CardLeader> CARDS_LEADER = new ArrayList<>();

	static {
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Cesare Borgia", 0, new CardLeaderConditions[] {}, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class)
		{
			@Override
			public void apply(EventGetDevelopmentCard event)
			{
				event.setIgnoreSlotLock(true);
			}
		}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Filippo Brunelleschi", 1, new CardLeaderConditions[] {}, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				event.setIgnoreOccupiedTax(true);
			}
		}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Lucrezia Borgia", 2, new CardLeaderConditions[] {}, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
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
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Ludovico Ariosto", 3, new CardLeaderConditions[] {}, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				event.setIgnoreOccupied(true);
			}
		}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Ludovico Il Moro", 4, new CardLeaderConditions[] {}, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
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
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Pico Della Mirandola", 5, new CardLeaderConditions[] {}, new Modifier<EventGetDevelopmentCard>(EventGetDevelopmentCard.class)
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
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Santa Rita", 6, new CardLeaderConditions[] {}, new Modifier<EventGainResources>(EventGainResources.class)
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
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Sigismondo Malatesta", 7, new CardLeaderConditions[] {}, new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class)
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				if (event.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
					event.setFamilyMemberValue(event.getFamilyMemberValue() + 3);
				}
			}
		}));
		CardsHandler.CARDS_LEADER.add(new CardLeaderModifier("Sisto IV", 8, new CardLeaderConditions[] {}, new Modifier<EventChurchSupport>(EventChurchSupport.class)
		{
			@Override
			public void apply(EventChurchSupport event)
			{
				event.setVictoryPoints(event.getVictoryPoints() + 5);
			}
		}));
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
