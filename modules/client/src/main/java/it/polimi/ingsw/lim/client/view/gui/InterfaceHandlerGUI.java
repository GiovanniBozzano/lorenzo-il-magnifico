package it.polimi.ingsw.lim.client.view.gui;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.client.view.IInterfaceHandler;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.network.AuthenticationInformations;
import it.polimi.ingsw.lim.common.network.AuthenticationInformationsGame;
import it.polimi.ingsw.lim.common.network.AuthenticationInformationsLobby;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.logging.Level;

public class InterfaceHandlerGUI implements IInterfaceHandler
{
	@Override
	public void start()
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void start(Stage stage)
	{
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_CONNECTION, () -> {
			Client.getInstance().getCliScanner().close();
			Client.getInstance().getCliListener().shutdownNow();
		}, stage);
	}

	@Override
	public void disconnect()
	{
		if (WindowFactory.getInstance().isWindowOpen(ControllerConnection.class)) {
			WindowFactory.getInstance().enableWindow();
		} else {
			WindowFactory.getInstance().setNewWindow(Utils.SCENE_CONNECTION);
		}
	}

	@Override
	public void stop()
	{
		WindowFactory.getInstance().closeWindow();
	}

	@Override
	public void handleRoomEntryOther(String name)
	{
		Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getPlayersListView().getItems().add(name));
	}

	@Override
	public void handleRoomExitOther(String name)
	{
		Platform.runLater(() -> {
			((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getPlayersListView().getItems().remove(name);
			if (((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getPlayersListView().getItems().size() < 2) {
				((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getTimerLabel().setText("Waiting for other players...");
			}
		});
	}

	@Override
	public void handleRoomTimer(int timer)
	{
		Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getTimerLabel().setText("Game starts in: " + timer));
	}

	@Override
	public void handleDisconnectionLogMessage(String text)
	{
		Client.getDebugger().log(Level.INFO, text);
	}

	@Override
	public void handleChatMessage(String text)
	{
		if (WindowFactory.getInstance().isWindowOpen(ControllerRoom.class)) {
			Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getChatTextArea().appendText((((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getChatTextArea().getText().length() < 1 ? "" : '\n') + text));
		} else {
			Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getChatTextArea().appendText((((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getChatTextArea().getText().length() < 1 ? "" : '\n') + text));
		}
	}

	@Override
	public void handleGameStarted()
	{
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_GAME);
	}

	@Override
	public void handleGameLogMessage(String text)
	{
		Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().appendText((((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().getText().length() < 1 ? "" : '\n') + text));
	}

	@Override
	public void handleGameTimer(int timer)
	{
		Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getTimerLabel().setText(Integer.toString(timer)));
	}

	@Override
	public void handleGameDisconnectionOther(int playerIndex)
	{
		Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().appendText((((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().getText().length() < 1 ? "" : '\n') + GameStatus.getInstance().getCurrentPlayersData().get(playerIndex).getUsername() + " disconnected"));
	}

	@Override
	public void handleGamePersonalBonusTileChoiceRequest()
	{
		try {
			WindowFactory.WINDOW_OPENING_SEMAPHORE.acquire();
		} catch (InterruptedException exception) {
			Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		WindowFactory.WINDOW_OPENING_SEMAPHORE.release();
		Platform.runLater(() -> {
			((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().appendText((((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().getText().length() < 1 ? "" : '\n') + "You are choosing a personal bonus tile");
			((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).showPersonalBonusTiles();
		});
	}

	@Override
	public void handleGamePersonalBonusTileChoiceOther(int choicePlayerIndex)
	{
		try {
			WindowFactory.WINDOW_OPENING_SEMAPHORE.acquire();
		} catch (InterruptedException exception) {
			Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		WindowFactory.WINDOW_OPENING_SEMAPHORE.release();
		Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().appendText((((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().getText().length() < 1 ? "" : '\n') + GameStatus.getInstance().getCurrentPlayersData().get(choicePlayerIndex).getUsername() + " is choosing a personal bonus tile"));
	}

	@Override
	public void handleGamePersonalBonusTileChosen(int choicePlayerIndex)
	{
		if (choicePlayerIndex == GameStatus.getInstance().getOwnPlayerIndex()) {
			Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getPersonalBonusTilesDialog().close());
		} else {
			Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().appendText((((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().getText().length() < 1 ? "" : '\n') + GameStatus.getInstance().getCurrentPlayersData().get(choicePlayerIndex).getUsername() + " has chosen a personal bonus tile"));
		}
	}

	@Override
	public void handleGameLeaderCardChoiceRequest()
	{
		Platform.runLater(((ControllerGame) WindowFactory.getInstance().getCurrentWindow())::showLeaderCards);
	}

	@Override
	public void handleGameExcommunicationChoiceRequest(Period period)
	{
		Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).showExcommunication(period));
	}

	@Override
	public void handleGameExcommunicationChoiceOther()
	{
		Platform.runLater(((ControllerGame) WindowFactory.getInstance().getCurrentWindow())::showExcommunicationOther);
	}

	@Override
	public void handleGameUpdateLog()
	{
		Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().appendText((((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().getText().length() < 1 ? "" : '\n') + "Your turn"));
	}

	@Override
	public void handleGameUpdate()
	{
		Platform.runLater(((ControllerGame) WindowFactory.getInstance().getCurrentWindow())::setOwnTurn);
	}

	@Override
	public void handleGameUpdateExpectedAction()
	{
		Platform.runLater(((ControllerGame) WindowFactory.getInstance().getCurrentWindow())::setOwnTurnExpectedAction);
	}

	@Override
	public void handleGameUpdateOtherTurnLog(int turnPlayerIndex)
	{
		Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().appendText((((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().getText().length() < 1 ? "" : '\n') + GameStatus.getInstance().getCurrentPlayersData().get(turnPlayerIndex).getUsername() + "'s turn"));
	}

	@Override
	public void handleGameUpdateOther()
	{
		Platform.runLater(((ControllerGame) WindowFactory.getInstance().getCurrentWindow())::setOtherTurn);
	}

	@Override
	public void handleGameEnded()
	{
		Platform.runLater(((ControllerGame) WindowFactory.getInstance().getCurrentWindow())::showEndGame);
	}

	@Override
	public void handleConnectionError()
	{
		WindowFactory.getInstance().enableWindow();
		Platform.runLater(() -> ((ControllerConnection) WindowFactory.getInstance().getCurrentWindow()).showDialog("Could not connect to host"));
	}

	@Override
	public void handleConnectionSuccess()
	{
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_AUTHENTICATION);
	}

	@Override
	public void handleAuthenticationFailed(String text)
	{
		WindowFactory.getInstance().enableWindow();
		Platform.runLater(() -> ((ControllerAuthentication) WindowFactory.getInstance().getCurrentWindow()).showDialog(text));
	}

	@Override
	public void handleAuthenticationSuccess(AuthenticationInformations authenticationInformations)
	{
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_ROOM, () -> Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).setRoomInformations(((AuthenticationInformationsLobby) authenticationInformations).getRoomInformations().getRoomType(), ((AuthenticationInformationsLobby) authenticationInformations).getRoomInformations().getPlayerNames())));
	}

	@Override
	public void handleAuthenticationSuccessGameStarted(AuthenticationInformationsGame authenticationInformations)
	{
		if (authenticationInformations.isGameInitialized()) {
			GameStatus.getInstance().updateGameStatus(authenticationInformations.getGameInformations(), authenticationInformations.getPlayersInformations(), authenticationInformations.getOwnLeaderCardsHand());
			WindowFactory.getInstance().setNewWindow(Utils.SCENE_GAME, () -> {
				if (authenticationInformations.getTurnPlayerIndex() != authenticationInformations.getOwnPlayerIndex()) {
					GameStatus.getInstance().setCurrentTurnPlayerIndex(authenticationInformations.getTurnPlayerIndex());
					Platform.runLater(((ControllerGame) WindowFactory.getInstance().getCurrentWindow())::setOtherTurn);
				} else {
					GameStatus.getInstance().setCurrentAvailableActions(authenticationInformations.getAvailableActions());
					Platform.runLater(((ControllerGame) WindowFactory.getInstance().getCurrentWindow())::setOwnTurn);
				}
			});
		} else {
			WindowFactory.getInstance().setNewWindow(Utils.SCENE_GAME);
		}
	}

	@Override
	public void handleGameActionFailed(String text)
	{
		Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).showDialog(text));
	}
}
