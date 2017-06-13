package it.polimi.ingsw.lim.common;

import it.polimi.ingsw.lim.common.cli.CLIListener;

import java.util.logging.Logger;

public abstract class Instance
{
	private static Logger debugger;
	private static Logger logger;
	private static Instance instance;
	private static CLIListener cliListener;

	public abstract void stop();

	public static Logger getDebugger()
	{
		return Instance.debugger;
	}

	public static void setDebugger(Logger debugger)
	{
		Instance.debugger = debugger;
	}

	public static Logger getLogger()
	{
		return Instance.logger;
	}

	public static void setLogger(Logger logger)
	{
		Instance.logger = logger;
	}

	public static Instance getInstance()
	{
		return Instance.instance;
	}

	public static void setInstance(Instance instance)
	{
		Instance.instance = instance;
	}

	public static CLIListener getCliListener()
	{
		return Instance.cliListener;
	}

	public static void setCliListener(CLIListener cliListener)
	{
		Instance.cliListener = cliListener;
	}
}
