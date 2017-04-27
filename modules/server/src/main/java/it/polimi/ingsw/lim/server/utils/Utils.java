package it.polimi.ingsw.lim.server.utils;

import it.polimi.ingsw.lim.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;

public class Utils
{
	private Utils()
	{
	}

	public static String getExternalIpAddress()
	{
		try {
			URL myIP = new URL("http://checkip.amazonaws.com");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myIP.openStream()));
			return bufferedReader.readLine();
		} catch (IOException exception) {
			Server.getLogger().log(Level.INFO, "Cannot retrieve IP address...", exception);
		}
		return null;
	}
}
