package it.polimi.ingsw.lim.common.network;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.RoomInformation;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformation;

import java.util.Map;
import java.util.Map.Entry;

public abstract class AuthenticationInformationLobby extends AuthenticationInformation
{
	private RoomInformation roomInformation;

	protected AuthenticationInformationLobby(AuthenticationInformation authenticationInformation)
	{
		for (Entry<CardType, Map<Integer, DevelopmentCardInformation>> developmentCardsInformatioType : authenticationInformation.getDevelopmentCardsInformation().entrySet()) {
			this.setDevelopmentCardsInformation(developmentCardsInformatioType.getKey(), developmentCardsInformatioType.getValue());
		}
		this.setLeaderCardsInformation(authenticationInformation.getLeaderCardsInformation());
		this.setExcommunicationTilesInformation(authenticationInformation.getExcommunicationTilesInformation());
		this.setPersonalBonusTilesInformation(authenticationInformation.getPersonalBonusTilesInformation());
	}

	public RoomInformation getRoomInformation()
	{
		return this.roomInformation;
	}

	public void setRoomInformation(RoomInformation roomInformation)
	{
		this.roomInformation = roomInformation;
	}
}
