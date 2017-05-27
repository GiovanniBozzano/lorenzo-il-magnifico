package it.polimi.ingsw.lim.common.cards;

import it.polimi.ingsw.lim.common.game.ResourceAmount;

public class CardLeader extends Card
{
	/*
	FRANCESCO_SFORZA("Francesco Sforza", new ResourceAmount[] { new ResourceAmount(ResourceType.PURPLE_CARD, 5) }),
	LUDOVICO_ARIOSTO("Ludovico Ariosto", new ResourceAmount[] { new ResourceAmount(ResourceType.BLUE_CARD, 5) }),
	FILIPPO_BRUNELLESCHI("Filippo Brunelleschi", new ResourceAmount[] { new ResourceAmount(ResourceType.YELLOW_CARD, 5) }),
	FEDERICO_DA_MONTEFELTRO("Federico Da Montefeltro", new ResourceAmount[] { new ResourceAmount(ResourceType.GREEN_CARD, 5) }),
	GIROLAMO_SAVONAROLA("Girolamo Savonarola", new ResourceAmount[] { new ResourceAmount(ResourceType.COIN, 18) }),
	GIOVANNI_DALLE_BANDE_NERE("Giovanni Dalle Bande Nere", new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 12) }),
	SANDRO_BOTTICELLI("Sandro Botticelli", new ResourceAmount[] { new ResourceAmount(ResourceType.WOOD, 10) }),
	MICHELANGELO_BUONARROTI("Michelangelo Buonarroti", new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 10) }),
	LUDOVICO_III_GONZAGA("Ludovico III Gonzaga", new ResourceAmount[] { new ResourceAmount(ResourceType.SERVANT, 15) }),
	LEONARDO_DA_VINCI("Leonardo Da Vinci", new ResourceAmount[] { new ResourceAmount(ResourceType.BLUE_CARD, 4), new ResourceAmount(ResourceType.GREEN_CARD, 2) }),
	PICO_DELLA_MIRANDOLA("Pico Della Mirandola", new ResourceAmount[] { new ResourceAmount(ResourceType.PURPLE_CARD, 4), new ResourceAmount(ResourceType.YELLOW_CARD, 2) }),
	SISTO_IV("Sisto IV", new ResourceAmount[] { new ResourceAmount(ResourceType.STONE, 6), new ResourceAmount(ResourceType.SERVANT, 6), new ResourceAmount(ResourceType.WOOD, 6), new ResourceAmount(ResourceType.COIN, 6) }),
	/*LUCREZIA_BORGIA("Lucrezia Borgia", new Object[] {}),
	SIGISMONDO_MALATESTA("Sigismondo Malatesta", new ResourceAmount[] { new ResourceAmount(ResourceType.MILITARY_POINT, 7), new ResourceAmount(ResourceType.FAITH_POINT, 3) }),
	LORENZO_DE_MEDICI("Lorenzo De' Medici", new ResourceAmount[] { new ResourceAmount(ResourceType.VICTORY_POINT, 35) }),
	LUDOVICO_IL_MORO("Ludovico Il Moro", new ResourceAmount[] { new ResourceAmount(ResourceType.GREEN_CARD, 2), new ResourceAmount(ResourceType.PURPLE_CARD, 2), new ResourceAmount(ResourceType.YELLOW_CARD, 2), new ResourceAmount(ResourceType.BLUE_CARD, 2) }),
	CESARE_BORGIA("Cesare Borgia", new ResourceAmount[] { new ResourceAmount(ResourceType.YELLOW_CARD, 3), new ResourceAmount(ResourceType.COIN, 12), new ResourceAmount(ResourceType.FAITH_POINT, 2) }),
	SANTA_RITA("Santa Rita", new ResourceAmount[] { new ResourceAmount(ResourceType.FAITH_POINT, 8) }),
	COSIMO_DE_MEDICI("Cosimo De' Medici", new ResourceAmount[] { new ResourceAmount(ResourceType.BLUE_CARD, 2), new ResourceAmount(ResourceType.YELLOW_CARD, 4) }),
	BARTOLOMEO_COLLEONI("Bartolomeo Colleoni", new ResourceAmount[] { new ResourceAmount(ResourceType.PURPLE_CARD, 2), new ResourceAmount(ResourceType.GREEN_CARD, 4) });
	*/
	private final ResourceAmount[] activationResources;

	public CardLeader(String displayName, ResourceAmount[] activationResources)
	{
		super(displayName);
		this.activationResources = activationResources;
	}

	public ResourceAmount[] getactivationResources()
	{
		return this.activationResources;
	}
}
