package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.utils.LoggerFormatter;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import it.polimi.ingsw.lim.server.utils.Utils;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Main extends Application
{
	private static String[] args;

	public static void main(String[] args)
	{
		Main.args = args;
		Server.setDebugger(Logger.getLogger(Server.class.getSimpleName().toUpperCase()));
		Server.getDebugger().setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new DebuggerFormatter());
		Server.getDebugger().addHandler(consoleHandler);
		Server.setLogger(Logger.getLogger("LOGGER"));
		Server.getLogger().setUseParentHandlers(false);
		consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LoggerFormatter());
		Server.getLogger().addHandler(consoleHandler);
		Instance.setInstance(new Server());
		Server.getInstance().getCliListener().execute(() -> {
			ICLIHandler cliHandler = Server.getCliHandlers().get(Server.getInstance().getCliStatus());
			Server.getInstance().setCurrentCliHandler(cliHandler);
			cliHandler.execute();
		});
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
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_START, stage);
	}

	@Override
	public void stop()
	{
		Server.getInstance().stop();
	}

	public static String[] getArgs()
	{
		return Main.args;
	}
}
