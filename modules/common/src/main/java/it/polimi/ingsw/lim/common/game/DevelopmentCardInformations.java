package it.polimi.ingsw.lim.common.game;

import it.polimi.ingsw.lim.common.enums.CardType;

import java.io.Serializable;

public class DevelopmentCardInformations implements Serializable
{
	private final CardType cardType;
	private final int index;
	private final String texturePath;

	public DevelopmentCardInformations(CardType cardType, int index, String texturePath)
	{
		this.cardType = cardType;
		this.index = index;
		this.texturePath = texturePath;
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public int getIndex()
	{
		return this.index;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}
}
