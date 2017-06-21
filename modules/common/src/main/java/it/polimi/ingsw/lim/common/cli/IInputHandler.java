package it.polimi.ingsw.lim.common.cli;

@FunctionalInterface public interface IInputHandler
{
	void execute(ICLIHandler cliHandler);
}
