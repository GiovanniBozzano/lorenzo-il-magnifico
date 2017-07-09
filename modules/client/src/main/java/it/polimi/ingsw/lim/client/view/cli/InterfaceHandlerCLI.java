package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.view.IInterfaceHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.network.AuthenticationInformation;
import it.polimi.ingsw.lim.common.network.AuthenticationInformationGame;
import javafx.stage.Stage;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class InterfaceHandlerCLI implements IInterfaceHandler
{
	private static final Map<ActionType, CLIStatus> EXPECTED_ACTION_HANDLERS = new EnumMap<>(ActionType.class);

	static {
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER, CLIStatus.CHOOSE_LORENZO_DE_MEDICI_LEADER);
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE, CLIStatus.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_HARVEST, CLIStatus.CHOOSE_REWARD_HARVEST);
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD, CLIStatus.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD);
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_PRODUCTION_START, CLIStatus.CHOOSE_REWARD_PRODUCTION_START);
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER, CLIStatus.CHOOSE_REWARD_TEMPORARY_MODIFIER);
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.PRODUCTION_TRADE, CLIStatus.PRODUCTION_TRADE);
	}

	@Override
	public void start()
	{
		Client.getInstance().getCliListener().execute(Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance()::execute);
	}

	@Override
	public void start(Stage stage)
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void disconnect()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.CONNECTION);
		Client.getInstance().getCliListener().execute(Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance()::execute);
	}

	@Override
	public void stop()
	{
		Client.getInstance().getCliScanner().close();
		Client.getInstance().getCliListener().shutdownNow();
	}

	@Override
	public void handleRoomEntryOther(String name)
	{
		Client.getLogger().log(Level.INFO, "\n\n{0} connected", new Object[] { name });
	}

	@Override
	public void handleRoomExitOther(String name)
	{
		Client.getLogger().log(Level.INFO, "\n\n{0} disconnected", new Object[] { name });
	}

	@Override
	public void handleRoomTimer(int timer)
	{
		Client.getLogger().log(Level.INFO, "\nGame starts in: {0}", new Object[] { Integer.toString(timer) });
	}

	@Override
	public void handleDisconnectionLogMessage(String text)
	{
		Client.getLogger().log(Level.INFO, text);
	}

	@Override
	public void handleChatMessage(String text)
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGameStarted()
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGameLogMessage(String text)
	{
		Client.getLogger().log(Level.INFO, text);
	}

	@Override
	public void handleGameTimer(int timer)
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGameDisconnectionOther(int playerIndex)
	{
		Client.getLogger().log(Level.INFO, "\n\n{0} disconnected", new Object[] { GameStatus.getInstance().getCurrentPlayersData().get(playerIndex).getUsername() });
	}

	@Override
	public void handleGamePersonalBonusTileChoiceRequest()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.PERSONAL_BONUS_TILE_CHOICE);
		Client.getInstance().getCliListener().execute(Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance()::execute);
	}

	@Override
	public void handleGamePersonalBonusTileChoiceOther(int choicePlayerIndex)
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGamePersonalBonusTileChosen(int choicePlayerIndex)
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGameLeaderCardChoiceRequest()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.LEADER_CARDS_CHOICE);
		Client.getInstance().getCliListener().execute(Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance()::execute);
	}

	@Override
	public void handleGameExcommunicationChoiceRequest(Period period)
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.EXCOMMUNICATION_CHOICE);
		Client.getInstance().getCliListener().execute(Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance()::execute);
	}

	@Override
	public void handleGameExcommunicationChoiceOther()
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGameUpdateLog()
	{
		Client.getLogger().log(Level.INFO, "\n\nYour turn...");
	}

	@Override
	public void handleGameUpdate()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getCliListener().execute(Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance()::execute);
	}

	@Override
	public void handleGameUpdateExpectedAction()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.get(GameStatus.getInstance().getCurrentExpectedAction().getActionType()));
		Client.getInstance().getCliListener().execute(Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance()::execute);
	}

	@Override
	public void handleGameUpdateOtherTurnLog(int turnPlayerIndex)
	{
		Client.getLogger().log(Level.INFO, "\n\n{0}'s turn...", new Object[] { GameStatus.getInstance().getCurrentPlayersData().get(turnPlayerIndex).getUsername() });
	}

	@Override
	public void handleGameUpdateOther()
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGameEnded()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.END_GAME);
		Client.getInstance().getCliListener().execute(Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance()::execute);
	}

	@Override
	public void handleConnectionError()
	{
		Client.getLogger().log(Level.INFO, "\n\nCould not connect to host");
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().getCliListener().execute(Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance()::execute);
	}

	@Override
	public void handleConnectionSuccess()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.AUTHENTICATION);
		Client.getInstance().getCliListener().execute(Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance()::execute);
	}

	@Override
	public void handleAuthenticationFailed(String text)
	{
		Client.getLogger().log(Level.INFO, text);
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().getCliListener().execute(Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance()::execute);
	}

	@Override
	public void handleAuthenticationSuccess(AuthenticationInformation authenticationInformation)
	{
		Client.getLogger().log(Level.INFO, "\n\nWaiting for other players...");
	}

	@Override
	public void handleAuthenticationSuccessGameStarted(AuthenticationInformationGame authenticationInformation)
	{
		if (authenticationInformation.isGameInitialized()) {
			GameStatus.getInstance().updateGameStatus(authenticationInformation.getGameInformation(), authenticationInformation.getPlayersInformation(), authenticationInformation.getOwnLeaderCardsHand());
			if (authenticationInformation.getTurnPlayerIndex() != authenticationInformation.getOwnPlayerIndex()) {
				GameStatus.getInstance().setCurrentTurnPlayerIndex(authenticationInformation.getTurnPlayerIndex());
				Client.getLogger().log(Level.INFO, "\n\n{0}'s turn...", new Object[] { GameStatus.getInstance().getCurrentPlayersData().get(authenticationInformation.getTurnPlayerIndex()).getUsername() });
			} else {
				GameStatus.getInstance().setCurrentAvailableActions(authenticationInformation.getAvailableActions());
				Client.getLogger().log(Level.INFO, "\n\nYour turn...");
			}
		}
	}

	@Override
	public void handleGameActionFailed(String text)
	{
		Client.getLogger().log(Level.INFO, "\n\nAction Failed: {0}", new Object[] { text });
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().getCliListener().execute(Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance()::execute);
	}
}
