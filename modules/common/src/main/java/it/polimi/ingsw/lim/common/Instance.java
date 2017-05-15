package it.polimi.ingsw.lim.common;

import it.polimi.ingsw.lim.common.utils.WindowInformations;

import java.util.logging.Logger;

public abstract class Instance
{
	private static Logger logger;
	private static Instance instance;
	private WindowInformations windowInformations;

	public abstract void stop();

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

	public WindowInformations getWindowInformations()
	{
		return this.windowInformations;
	}

	public void setWindowInformations(WindowInformations windowInformations)
	{
		this.windowInformations = windowInformations;
	}
}
