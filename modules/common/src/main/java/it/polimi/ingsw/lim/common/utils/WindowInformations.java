package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.gui.CustomController;
import javafx.stage.Stage;

public class WindowInformations
{
	private final CustomController controller;
	private final Stage stage;

	public WindowInformations(CustomController controller, Stage stage)
	{
		this.controller = controller;
		this.stage = stage;
	}

	public CustomController getController()
	{
		return this.controller;
	}

	public Stage getStage()
	{
		return this.stage;
	}
}
