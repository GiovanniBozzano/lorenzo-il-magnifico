package it.polimi.ingsw.lim.common.cli;

public interface ICLIHandler
{
	void execute();

	ICLIHandler newInstance();
}
