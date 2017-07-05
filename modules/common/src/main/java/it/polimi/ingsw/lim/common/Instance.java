package it.polimi.ingsw.lim.common;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public abstract class Instance
{
	private static Logger debugger;
	private static Logger logger;
	private static Instance instance;
	private final Scanner cliScanner = new Scanner(System.in);
	private ExecutorService cliListener = Executors.newSingleThreadExecutor();

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

	public void setCliListener(ExecutorService cliListener)
	{
		this.cliListener = cliListener;
	}

	public Scanner getCliScanner()
	{
		return this.cliScanner;
	}
}
