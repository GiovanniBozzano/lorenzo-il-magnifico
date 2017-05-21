package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Main extends Application
{
	public static void main(String[] args)
	{
		Client.setLogger(Logger.getLogger(Client.class.getSimpleName().toUpperCase()));
		Client.getLogger().setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		Client.getLogger().addHandler(consoleHandler);
		Instance.setInstance(new Client());
		Main.launch(args);
	}

	@Override
	public void start(Stage stage)
	{
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-Black.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-BlackItalic.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-Bold.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-BoldItalic.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-Italic.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-Light.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-LightItalic.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-Medium.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-MediumItalic.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-Regular.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-Thin.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-ThinItalic.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/RobotoCondensed-Bold.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/RobotoCondensed-BoldItalic.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/RobotoCondensed-Italic.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/RobotoCondensed-Light.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/RobotoCondensed-LightItalic.ttf"), 12);
		Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/RobotoCondensed-Regular.ttf"), 12);
		CommonUtils.setNewWindow(Utils.SCENE_CONNECTION, null);
	}

	@Override
	public void stop()
	{
		Client.getInstance().stop();
	}
}
