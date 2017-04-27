package it.polimi.ingsw.lim.common.utils;

import javafx.stage.Stage;

public class WindowInformations
{
	private final Object controller;
	private final Stage stage;

	public WindowInformations(Object controller, Stage stage)
	{
		this.controller = controller;
		this.stage = stage;
	}

	public Object getController()
	{
		return this.controller;
	}

	public Stage getStage()
	{
		return this.stage;
	}
}
