package it.polimi.ingsw.lim.common.network;

import it.polimi.ingsw.lim.common.game.RoomInformations;

public abstract class AuthenticationInformationsLobby extends AuthenticationInformations
{
	private RoomInformations roomInformations;

	public AuthenticationInformationsLobby(AuthenticationInformations authenticationInformations)
	{
		this.setDevelopmentCardsBuildingInformations(authenticationInformations.getDevelopmentCardsBuildingInformations());
		this.setDevelopmentCardsCharacterInformations(authenticationInformations.getDevelopmentCardsCharacterInformations());
		this.setDevelopmentCardsTerritoryInformations(authenticationInformations.getDevelopmentCardsTerritoryInformations());
		this.setDevelopmentCardsVentureInformations(authenticationInformations.getDevelopmentCardsVentureInformations());
		this.setLeaderCardsInformations(authenticationInformations.getLeaderCardsInformations());
		this.setExcommunicationTilesInformations(authenticationInformations.getExcommunicationTilesInformations());
		this.setPersonalBonusTilesInformations(authenticationInformations.getPersonalBonusTilesInformations());
	}

	public RoomInformations getRoomInformations()
	{
		return this.roomInformations;
	}

	public void setRoomInformations(RoomInformations roomInformations)
	{
		this.roomInformations = roomInformations;
	}
}
