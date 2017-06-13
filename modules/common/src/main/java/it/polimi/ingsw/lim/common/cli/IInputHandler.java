package it.polimi.ingsw.lim.common.cli;

@FunctionalInterface public interface IInputHandler
{
	boolean execute(ICLIHandler cliHandler);
}
