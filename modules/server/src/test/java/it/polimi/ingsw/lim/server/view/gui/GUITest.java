package it.polimi.ingsw.lim.server.view.gui;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.view.gui.CustomController;
import it.polimi.ingsw.lim.common.view.gui.JavaFXThreadingRule;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.enums.CLIStatus;
import it.polimi.ingsw.lim.server.utils.Utils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class GUITest
{
	@Rule public JavaFXThreadingRule javaFxRule = new JavaFXThreadingRule();

	@Before
	public void setUp()
	{
		Server.setDebugger(Logger.getLogger(Server.class.getSimpleName().toUpperCase()));
		Server.getDebugger().setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new DebuggerFormatter());
		Server.getDebugger().addHandler(consoleHandler);
		Instance.setInstance(new Server());
		Server.getInstance().setCliStatus(CLIStatus.NONE);
		Server.getInstance().setInterfaceHandler(new InterfaceHandlerGUI());
	}

	@Test
	public void testGui() throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(Instance.getInstance().getClass().getResource(Utils.SCENE_START));
		Parent parent = fxmlLoader.load();
		Stage newStage = new Stage();
		((CustomController) fxmlLoader.getController()).setStage(newStage);
		newStage.setScene(new Scene(parent));
		newStage.show();
		((ControllerStart) fxmlLoader.getController()).setupGui();
		newStage.close();
		fxmlLoader = new FXMLLoader(Instance.getInstance().getClass().getResource(Utils.SCENE_MAIN));
		parent = fxmlLoader.load();
		newStage = new Stage();
		((CustomController) fxmlLoader.getController()).setStage(newStage);
		newStage.setScene(new Scene(parent));
		newStage.show();
		((ControllerMain) fxmlLoader.getController()).setupGui();
		newStage.close();
	}

	@After
	public void tearDown()
	{
		Server.getInstance().stop();
	}
}
