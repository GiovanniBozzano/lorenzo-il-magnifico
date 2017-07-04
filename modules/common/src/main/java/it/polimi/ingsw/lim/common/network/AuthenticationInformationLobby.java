package it.polimi.ingsw.lim.common.network;

import it.polimi.ingsw.lim.common.game.RoomInformation;

public abstract class AuthenticationInformationLobby extends AuthenticationInformation
{
	private RoomInformation roomInformation;

	public AuthenticationInformationLobby(AuthenticationInformation authenticationInformation)
	{
		this.setDevelopmentCardsBuildingInformation(authenticationInformation.getDevelopmentCardsBuildingInformation());
		this.setDevelopmentCardsCharacterInformation(authenticationInformation.getDevelopmentCardsCharacterInformation());
		this.setDevelopmentCardsTerritoryInformation(authenticationInformation.getDevelopmentCardsTerritoryInformation());
		this.setDevelopmentCardsVentureInformation(authenticationInformation.getDevelopmentCardsVentureInformation());
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
