package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionCouncilPalace implements IAction
{
	private final Connection player;
	private final FamilyMemberType familyMemberType;
	private final int councilPalaceRewardIndex;

	public ActionCouncilPalace(Connection player, FamilyMemberType familyMemberType, int councilPalaceRewardIndex)
	{
		this.player = player;
		this.familyMemberType = familyMemberType;
		this.councilPalaceRewardIndex = councilPalaceRewardIndex;
	}

	@Override
	public boolean isLegal()
	{
		return false;
	}

	@Override
	public void apply()
	{
	}

	@Override
	public Connection getPlayer()
	{
		return this.player;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}

	public int getCouncilPalaceRewardIndex()
	{
		return this.councilPalaceRewardIndex;
	}
}
