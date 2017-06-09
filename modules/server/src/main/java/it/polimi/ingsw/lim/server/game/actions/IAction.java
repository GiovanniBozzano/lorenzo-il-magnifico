package it.polimi.ingsw.lim.server.game.actions;

public interface IAction
{
	boolean isLegal();

	void apply();
}
