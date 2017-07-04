package it.polimi.ingsw.lim.server.utils;

@FunctionalInterface interface ICommandHandler
{
	void execute(String arguments);
}
