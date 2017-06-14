package it.polimi.ingsw.lim.common.cli;

import java.util.Scanner;

public abstract class CLIListener extends Thread
{
	private final Scanner scanner = new Scanner(System.in);
	private boolean keepGoing = true;

	@Override
	public abstract void run();

	public synchronized void end()
	{
		this.keepGoing = false;
		this.scanner.close();
	}

	public Scanner getScanner()
	{
		return this.scanner;
	}

	public boolean isKeepGoing()
	{
		return this.keepGoing;
	}
}
