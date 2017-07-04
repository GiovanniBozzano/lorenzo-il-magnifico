package it.polimi.ingsw.lim.client.view;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.network.AuthenticationInformation;
import it.polimi.ingsw.lim.common.network.AuthenticationInformationGame;
import javafx.stage.Stage;

public interface IInterfaceHandler
{
	void start();

	void start(Stage stage);

	void disconnect();

	void stop();

	void handleRoomEntryOther(String name);

	void handleRoomExitOther(String name);

	void handleRoomTimer(int timer);

	void handleDisconnectionLogMessage(String text);

	void handleChatMessage(String text);

	void handleGameStarted();

	void handleGameLogMessage(String text);

	void handleGameTimer(int timer);

	void handleGameDisconnectionOther(int playerIndex);

	void handleGamePersonalBonusTileChoiceRequest();

	void handleGamePersonalBonusTileChoiceOther(int choicePlayerIndex);

	void handleGamePersonalBonusTileChosen(int choicePlayerIndex);

	void handleGameLeaderCardChoiceRequest();

	void handleGameExcommunicationChoiceRequest(Period period);

	void handleGameExcommunicationChoiceOther();

	void handleGameUpdateLog();

	void handleGameUpdate();

	void handleGameUpdateExpectedAction();

	void handleGameUpdateOtherTurnLog(int turnPlayerIndex);

	void handleGameUpdateOther();

	void handleGameEnded();

	void handleConnectionError();

	void handleConnectionSuccess();

	void handleAuthenticationFailed(String text);

	void handleAuthenticationSuccess(AuthenticationInformation authenticationInformation);

	void handleAuthenticationSuccessGameStarted(AuthenticationInformationGame authenticationInformation);

	void handleGameActionFailed(String text);
}
