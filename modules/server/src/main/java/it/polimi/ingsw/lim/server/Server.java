package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.Side;
import it.polimi.ingsw.lim.common.utils.CardHelper;
import it.polimi.ingsw.lim.common.utils.LogFormatter;

import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Instance
{
	private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName().toUpperCase());
	private static Server instance;

	Server()
	{
		Server.instance = this;
		Server.LOGGER.setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		Server.LOGGER.addHandler(consoleHandler);
		this.side = Side.SERVER;
		this.cardHelper = new CardHelper();
		Server.LOGGER.log(Level.INFO, "FIRST PERIOD:");
		Server.LOGGER.log(Level.INFO, "BLUE:   {0}", Arrays.toString(Period.FIRST.getBlueCards()));
		Server.LOGGER.log(Level.INFO, "GREEN:  {0}", Arrays.toString(Period.FIRST.getGreenCards()));
		Server.LOGGER.log(Level.INFO, "PURPLE: {0}", Arrays.toString(Period.FIRST.getPurpleCards()));
		Server.LOGGER.log(Level.INFO, "YELLOW: {0}", Arrays.toString(Period.FIRST.getYellowCards()));
		Server.LOGGER.log(Level.INFO, "----------");
		Server.LOGGER.log(Level.INFO, "SECOND PERIOD:");
		Server.LOGGER.log(Level.INFO, "BLUE:   {0}", Arrays.toString(Period.SECOND.getBlueCards()));
		Server.LOGGER.log(Level.INFO, "GREEN:  {0}", Arrays.toString(Period.SECOND.getGreenCards()));
		Server.LOGGER.log(Level.INFO, "PURPLE: {0}", Arrays.toString(Period.SECOND.getPurpleCards()));
		Server.LOGGER.log(Level.INFO, "YELLOW: {0}", Arrays.toString(Period.SECOND.getYellowCards()));
		Server.LOGGER.log(Level.INFO, "----------");
		Server.LOGGER.log(Level.INFO, "THIRD PERIOD:");
		Server.LOGGER.log(Level.INFO, "BLUE:   {0}", Arrays.toString(Period.THIRD.getBlueCards()));
		Server.LOGGER.log(Level.INFO, "GREEN:  {0}", Arrays.toString(Period.THIRD.getGreenCards()));
		Server.LOGGER.log(Level.INFO, "PURPLE: {0}", Arrays.toString(Period.THIRD.getPurpleCards()));
		Server.LOGGER.log(Level.INFO, "YELLOW: {0}", Arrays.toString(Period.THIRD.getYellowCards()));
	}

	public static Logger getLogger()
	{
		return LOGGER;
	}

	public static Server getInstance()
	{
		return instance;
	}
}
