package it.polimi.ingsw.lim.common;

import it.polimi.ingsw.lim.common.cli.ICLIHandler;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public abstract class Instance
{
	private static Logger debugger;
	private static Logger logger;
	private static Instance instance;
	private final ExecutorService cliListener = Executors.newSingleThreadExecutor();
	private final Scanner cliScanner = new Scanner(System.in);
	private ICLIHandler currentCliHandler;

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

	public ExecutorService getCliListener()
	{
		return this.cliListener;
	}

	public Scanner getCliScanner()
	{
		return this.cliScanner;
	}

	public ICLIHandler getCurrentCliHandler()
	{
		return this.currentCliHandler;
	}

	public void setCurrentCliHandler(ICLIHandler currentCliHandler)
	{
		this.currentCliHandler = currentCliHandler;
	}
}
