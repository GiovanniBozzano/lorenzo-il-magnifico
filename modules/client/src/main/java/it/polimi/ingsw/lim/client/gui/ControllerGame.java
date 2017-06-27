package it.polimi.ingsw.lim.client.gui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.actions.*;
import it.polimi.ingsw.lim.common.game.cards.*;
import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.common.gui.CustomController;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Duration;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

public class ControllerGame extends CustomController
{
	private static final Map<Integer, String> BOARD_IMAGES = new HashMap<>();

	static {
		ControllerGame.BOARD_IMAGES.put(2, "/images/game_board_2_players.png");
		ControllerGame.BOARD_IMAGES.put(3, "/images/game_board_3_players.png");
		ControllerGame.BOARD_IMAGES.put(4, "/images/game_board_4_players.png");
		ControllerGame.BOARD_IMAGES.put(5, "/images/game_board_5_players.png");
	}

	@FXML private Pane gameBoard;
	@FXML private Pane playerBoard1DevelopmentCardsVenture;
	@FXML private Pane playerBoard2DevelopmentCardsVenture;
	@FXML private Pane playerBoard3DevelopmentCardsVenture;
	@FXML private Pane playerBoard4DevelopmentCardsVenture;
	@FXML private Pane playerBoard5DevelopmentCardsVenture;
	@FXML private Pane playerBoard1DevelopmentCardsCharacter;
	@FXML private Pane playerBoard2DevelopmentCardsCharacter;
	@FXML private Pane playerBoard3DevelopmentCardsCharacter;
	@FXML private Pane playerBoard4DevelopmentCardsCharacter;
	@FXML private Pane playerBoard5DevelopmentCardsCharacter;
	@FXML private Pane playerBoard1;
	@FXML private Pane playerBoard2;
	@FXML private Pane playerBoard3;
	@FXML private Pane playerBoard4;
	@FXML private Pane playerBoard5;
	@FXML private Pane diceBlack;
	@FXML private Pane diceWhite;
	@FXML private Pane diceOrange;
	@FXML private Pane excommunicationTileFirst;
	@FXML private Pane excommunicationTileSecond;
	@FXML private Pane excommunicationTileThird;
	@FXML private Pane excommunicationTileFirstPlayer1;
	@FXML private Pane excommunicationTileFirstPlayer2;
	@FXML private Pane excommunicationTileFirstPlayer3;
	@FXML private Pane excommunicationTileFirstPlayer4;
	@FXML private Pane excommunicationTileFirstPlayer5;
	@FXML private Pane excommunicationTileSecondPlayer1;
	@FXML private Pane excommunicationTileSecondPlayer2;
	@FXML private Pane excommunicationTileSecondPlayer3;
	@FXML private Pane excommunicationTileSecondPlayer4;
	@FXML private Pane excommunicationTileSecondPlayer5;
	@FXML private Pane excommunicationTileThirdPlayer1;
	@FXML private Pane excommunicationTileThirdPlayer2;
	@FXML private Pane excommunicationTileThirdPlayer3;
	@FXML private Pane excommunicationTileThirdPlayer4;
	@FXML private Pane excommunicationTileThirdPlayer5;
	@FXML private Pane roundOrderPosition1;
	@FXML private Pane roundOrderPosition2;
	@FXML private Pane roundOrderPosition3;
	@FXML private Pane roundOrderPosition4;
	@FXML private Pane roundOrderPosition5;
	@FXML private Pane victoryPoint0;
	@FXML private Pane victoryPoint1;
	@FXML private Pane victoryPoint2;
	@FXML private Pane victoryPoint3;
	@FXML private Pane victoryPoint4;
	@FXML private Pane victoryPoint5;
	@FXML private Pane victoryPoint6;
	@FXML private Pane victoryPoint7;
	@FXML private Pane victoryPoint8;
	@FXML private Pane victoryPoint9;
	@FXML private Pane victoryPoint10;
	@FXML private Pane victoryPoint11;
	@FXML private Pane victoryPoint12;
	@FXML private Pane victoryPoint13;
	@FXML private Pane victoryPoint14;
	@FXML private Pane victoryPoint15;
	@FXML private Pane victoryPoint16;
	@FXML private Pane victoryPoint17;
	@FXML private Pane victoryPoint18;
	@FXML private Pane victoryPoint19;
	@FXML private Pane victoryPoint20;
	@FXML private Pane victoryPoint21;
	@FXML private Pane victoryPoint22;
	@FXML private Pane victoryPoint23;
	@FXML private Pane victoryPoint24;
	@FXML private Pane victoryPoint25;
	@FXML private Pane victoryPoint26;
	@FXML private Pane victoryPoint27;
	@FXML private Pane victoryPoint28;
	@FXML private Pane victoryPoint29;
	@FXML private Pane victoryPoint30;
	@FXML private Pane victoryPoint31;
	@FXML private Pane victoryPoint32;
	@FXML private Pane victoryPoint33;
	@FXML private Pane victoryPoint34;
	@FXML private Pane victoryPoint35;
	@FXML private Pane victoryPoint36;
	@FXML private Pane victoryPoint37;
	@FXML private Pane victoryPoint38;
	@FXML private Pane victoryPoint39;
	@FXML private Pane victoryPoint40;
	@FXML private Pane victoryPoint41;
	@FXML private Pane victoryPoint42;
	@FXML private Pane victoryPoint43;
	@FXML private Pane victoryPoint44;
	@FXML private Pane victoryPoint45;
	@FXML private Pane victoryPoint46;
	@FXML private Pane victoryPoint47;
	@FXML private Pane victoryPoint48;
	@FXML private Pane victoryPoint49;
	@FXML private Pane victoryPoint50;
	@FXML private Pane victoryPoint51;
	@FXML private Pane victoryPoint52;
	@FXML private Pane victoryPoint53;
	@FXML private Pane victoryPoint54;
	@FXML private Pane victoryPoint55;
	@FXML private Pane victoryPoint56;
	@FXML private Pane victoryPoint57;
	@FXML private Pane victoryPoint58;
	@FXML private Pane victoryPoint59;
	@FXML private Pane victoryPoint60;
	@FXML private Pane victoryPoint61;
	@FXML private Pane victoryPoint62;
	@FXML private Pane victoryPoint63;
	@FXML private Pane victoryPoint64;
	@FXML private Pane victoryPoint65;
	@FXML private Pane victoryPoint66;
	@FXML private Pane victoryPoint67;
	@FXML private Pane victoryPoint68;
	@FXML private Pane victoryPoint69;
	@FXML private Pane victoryPoint70;
	@FXML private Pane victoryPoint71;
	@FXML private Pane victoryPoint72;
	@FXML private Pane victoryPoint73;
	@FXML private Pane victoryPoint74;
	@FXML private Pane victoryPoint75;
	@FXML private Pane victoryPoint76;
	@FXML private Pane victoryPoint77;
	@FXML private Pane victoryPoint78;
	@FXML private Pane victoryPoint79;
	@FXML private Pane victoryPoint80;
	@FXML private Pane victoryPoint81;
	@FXML private Pane victoryPoint82;
	@FXML private Pane victoryPoint83;
	@FXML private Pane victoryPoint84;
	@FXML private Pane victoryPoint85;
	@FXML private Pane victoryPoint86;
	@FXML private Pane victoryPoint87;
	@FXML private Pane victoryPoint88;
	@FXML private Pane victoryPoint89;
	@FXML private Pane victoryPoint90;
	@FXML private Pane victoryPoint91;
	@FXML private Pane victoryPoint92;
	@FXML private Pane victoryPoint93;
	@FXML private Pane victoryPoint94;
	@FXML private Pane victoryPoint95;
	@FXML private Pane victoryPoint96;
	@FXML private Pane victoryPoint97;
	@FXML private Pane victoryPoint98;
	@FXML private Pane victoryPoint99;
	@FXML private Pane militaryPoint0;
	@FXML private Pane militaryPoint1;
	@FXML private Pane militaryPoint2;
	@FXML private Pane militaryPoint3;
	@FXML private Pane militaryPoint4;
	@FXML private Pane militaryPoint5;
	@FXML private Pane militaryPoint6;
	@FXML private Pane militaryPoint7;
	@FXML private Pane militaryPoint8;
	@FXML private Pane militaryPoint9;
	@FXML private Pane militaryPoint10;
	@FXML private Pane militaryPoint11;
	@FXML private Pane militaryPoint12;
	@FXML private Pane militaryPoint13;
	@FXML private Pane militaryPoint14;
	@FXML private Pane militaryPoint15;
	@FXML private Pane militaryPoint16;
	@FXML private Pane militaryPoint17;
	@FXML private Pane militaryPoint18;
	@FXML private Pane militaryPoint19;
	@FXML private Pane militaryPoint20;
	@FXML private Pane militaryPoint21;
	@FXML private Pane militaryPoint22;
	@FXML private Pane militaryPoint23;
	@FXML private Pane militaryPoint24;
	@FXML private Pane militaryPoint25;
	@FXML private Pane faithPoint0;
	@FXML private Pane faithPoint1;
	@FXML private Pane faithPoint2;
	@FXML private Pane faithPoint3;
	@FXML private Pane faithPoint4;
	@FXML private Pane faithPoint5;
	@FXML private Pane faithPoint6;
	@FXML private Pane faithPoint7;
	@FXML private Pane faithPoint8;
	@FXML private Pane faithPoint9;
	@FXML private Pane faithPoint10;
	@FXML private Pane faithPoint11;
	@FXML private Pane faithPoint12;
	@FXML private Pane faithPoint13;
	@FXML private Pane faithPoint14;
	@FXML private Pane faithPoint15;
	@FXML private Pane prestigePoint0;
	@FXML private Pane prestigePoint1;
	@FXML private Pane prestigePoint2;
	@FXML private Pane prestigePoint3;
	@FXML private Pane prestigePoint4;
	@FXML private Pane prestigePoint5;
	@FXML private Pane prestigePoint6;
	@FXML private Pane prestigePoint7;
	@FXML private Pane prestigePoint8;
	@FXML private Pane prestigePoint9;
	@FXML private Pane productionSmall;
	@FXML private Pane productionBig1;
	@FXML private Pane productionBig2;
	@FXML private Pane productionBig3;
	@FXML private Pane productionBig4;
	@FXML private Pane productionBig5;
	@FXML private Label productionBigLabel1;
	@FXML private Label productionBigLabel2;
	@FXML private Label productionBigLabel3;
	@FXML private Label productionBigLabel4;
	@FXML private Label productionBigLabel5;
	@FXML private Pane harvestSmall;
	@FXML private Pane harvestBig1;
	@FXML private Pane harvestBig2;
	@FXML private Pane harvestBig3;
	@FXML private Pane harvestBig4;
	@FXML private Pane harvestBig5;
	@FXML private Label harvestBigLabel1;
	@FXML private Label harvestBigLabel2;
	@FXML private Label harvestBigLabel3;
	@FXML private Label harvestBigLabel4;
	@FXML private Label harvestBigLabel5;
	@FXML private Pane market1;
	@FXML private Pane market2;
	@FXML private Pane market3;
	@FXML private Pane market4;
	@FXML private Pane market5;
	@FXML private Pane market6;
	@FXML private Pane councilPalace1;
	@FXML private Pane councilPalace2;
	@FXML private Pane councilPalace3;
	@FXML private Pane councilPalace4;
	@FXML private Pane councilPalace5;
	@FXML private Pane territory1;
	@FXML private Pane territory2;
	@FXML private Pane territory3;
	@FXML private Pane territory4;
	@FXML private Pane character1;
	@FXML private Pane character2;
	@FXML private Pane character3;
	@FXML private Pane character4;
	@FXML private Pane building1;
	@FXML private Pane building2;
	@FXML private Pane building3;
	@FXML private Pane building4;
	@FXML private Pane venture1;
	@FXML private Pane venture2;
	@FXML private Pane venture3;
	@FXML private Pane venture4;
	@FXML private Pane slotTerritory1;
	@FXML private Pane slotTerritory2;
	@FXML private Pane slotTerritory3;
	@FXML private Pane slotTerritory4;
	@FXML private Pane slotCharacter1;
	@FXML private Pane slotCharacter2;
	@FXML private Pane slotCharacter3;
	@FXML private Pane slotCharacter4;
	@FXML private Pane slotBuilding1;
	@FXML private Pane slotBuilding2;
	@FXML private Pane slotBuilding3;
	@FXML private Pane slotBuilding4;
	@FXML private Pane slotVenture1;
	@FXML private Pane slotVenture2;
	@FXML private Pane slotVenture3;
	@FXML private Pane slotVenture4;
	@FXML private Pane player1Venture1;
	@FXML private Pane player1Venture2;
	@FXML private Pane player1Venture3;
	@FXML private Pane player1Venture4;
	@FXML private Pane player1Venture5;
	@FXML private Pane player1Venture6;
	@FXML private Pane player1Character1;
	@FXML private Pane player1Character2;
	@FXML private Pane player1Character3;
	@FXML private Pane player1Character4;
	@FXML private Pane player1Character5;
	@FXML private Pane player1Character6;
	@FXML private Pane player1Building1;
	@FXML private Pane player1Building2;
	@FXML private Pane player1Building3;
	@FXML private Pane player1Building4;
	@FXML private Pane player1Building5;
	@FXML private Pane player1Building6;
	@FXML private Pane player1Territory1;
	@FXML private Pane player1Territory2;
	@FXML private Pane player1Territory3;
	@FXML private Pane player1Territory4;
	@FXML private Pane player1Territory5;
	@FXML private Pane player1Territory6;
	@FXML private Label player1Coin;
	@FXML private Label player1Wood;
	@FXML private Label player1Stone;
	@FXML private Label player1Servant;
	@FXML private Pane player2Venture1;
	@FXML private Pane player2Venture2;
	@FXML private Pane player2Venture3;
	@FXML private Pane player2Venture4;
	@FXML private Pane player2Venture5;
	@FXML private Pane player2Venture6;
	@FXML private Pane player2Character1;
	@FXML private Pane player2Character2;
	@FXML private Pane player2Character3;
	@FXML private Pane player2Character4;
	@FXML private Pane player2Character5;
	@FXML private Pane player2Character6;
	@FXML private Pane player2Building1;
	@FXML private Pane player2Building2;
	@FXML private Pane player2Building3;
	@FXML private Pane player2Building4;
	@FXML private Pane player2Building5;
	@FXML private Pane player2Building6;
	@FXML private Pane player2Territory1;
	@FXML private Pane player2Territory2;
	@FXML private Pane player2Territory3;
	@FXML private Pane player2Territory4;
	@FXML private Pane player2Territory5;
	@FXML private Pane player2Territory6;
	@FXML private Label player2Coin;
	@FXML private Label player2Wood;
	@FXML private Label player2Stone;
	@FXML private Label player2Servant;
	@FXML private Pane player3Venture1;
	@FXML private Pane player3Venture2;
	@FXML private Pane player3Venture3;
	@FXML private Pane player3Venture4;
	@FXML private Pane player3Venture5;
	@FXML private Pane player3Venture6;
	@FXML private Pane player3Character1;
	@FXML private Pane player3Character2;
	@FXML private Pane player3Character3;
	@FXML private Pane player3Character4;
	@FXML private Pane player3Character5;
	@FXML private Pane player3Character6;
	@FXML private Pane player3Building1;
	@FXML private Pane player3Building2;
	@FXML private Pane player3Building3;
	@FXML private Pane player3Building4;
	@FXML private Pane player3Building5;
	@FXML private Pane player3Building6;
	@FXML private Pane player3Territory1;
	@FXML private Pane player3Territory2;
	@FXML private Pane player3Territory3;
	@FXML private Pane player3Territory4;
	@FXML private Pane player3Territory5;
	@FXML private Pane player3Territory6;
	@FXML private Label player3Coin;
	@FXML private Label player3Wood;
	@FXML private Label player3Stone;
	@FXML private Label player3Servant;
	@FXML private Pane player4Venture1;
	@FXML private Pane player4Venture2;
	@FXML private Pane player4Venture3;
	@FXML private Pane player4Venture4;
	@FXML private Pane player4Venture5;
	@FXML private Pane player4Venture6;
	@FXML private Pane player4Character1;
	@FXML private Pane player4Character2;
	@FXML private Pane player4Character3;
	@FXML private Pane player4Character4;
	@FXML private Pane player4Character5;
	@FXML private Pane player4Character6;
	@FXML private Pane player4Building1;
	@FXML private Pane player4Building2;
	@FXML private Pane player4Building3;
	@FXML private Pane player4Building4;
	@FXML private Pane player4Building5;
	@FXML private Pane player4Building6;
	@FXML private Pane player4Territory1;
	@FXML private Pane player4Territory2;
	@FXML private Pane player4Territory3;
	@FXML private Pane player4Territory4;
	@FXML private Pane player4Territory5;
	@FXML private Pane player4Territory6;
	@FXML private Label player4Coin;
	@FXML private Label player4Wood;
	@FXML private Label player4Stone;
	@FXML private Label player4Servant;
	@FXML private Pane player5Venture1;
	@FXML private Pane player5Venture2;
	@FXML private Pane player5Venture3;
	@FXML private Pane player5Venture4;
	@FXML private Pane player5Venture5;
	@FXML private Pane player5Venture6;
	@FXML private Pane player5Character1;
	@FXML private Pane player5Character2;
	@FXML private Pane player5Character3;
	@FXML private Pane player5Character4;
	@FXML private Pane player5Character5;
	@FXML private Pane player5Character6;
	@FXML private Pane player5Building1;
	@FXML private Pane player5Building2;
	@FXML private Pane player5Building3;
	@FXML private Pane player5Building4;
	@FXML private Pane player5Building5;
	@FXML private Pane player5Building6;
	@FXML private Pane player5Territory1;
	@FXML private Pane player5Territory2;
	@FXML private Pane player5Territory3;
	@FXML private Pane player5Territory4;
	@FXML private Pane player5Territory5;
	@FXML private Pane player5Territory6;
	@FXML private Label player5Coin;
	@FXML private Label player5Wood;
	@FXML private Label player5Stone;
	@FXML private Label player5Servant;
	@FXML private Tab player1Tab;
	@FXML private Tab player2Tab;
	@FXML private Tab player3Tab;
	@FXML private Tab player4Tab;
	@FXML private Tab player5Tab;
	@FXML private Tab leaderCardsPlayer1Tab;
	@FXML private Tab leaderCardsPlayer2Tab;
	@FXML private Tab leaderCardsPlayer3Tab;
	@FXML private Tab leaderCardsPlayer4Tab;
	@FXML private Tab leaderCardsPlayer5Tab;
	@FXML private VBox rightVBox;
	@FXML private JFXTabPane playersTabPane;
	@FXML private JFXTabPane chatTabPane;
	@FXML private JFXTabPane leaderCardsTabPane;
	@FXML private TextArea chatTextArea;
	@FXML private TextArea gameLogTextArea;
	@FXML private VBox actionsVBox;
	@FXML private JFXButton leaderCardsButton;
	@FXML private Label timerLabel;
	@FXML private JFXDialog personalBonusTilesChoiceDialog;
	@FXML private JFXDialogLayout personalBonusTilesChoiceDialogLayout;
	@FXML private HBox personalBonusTilesChoiceDialogHBox;
	@FXML private JFXDialog leaderCardsChoiceDialog;
	@FXML private JFXDialogLayout leaderCardsChoiceDialogLayout;
	@FXML private HBox leaderCardsChoiceDialogHBox;
	@FXML private JFXDialog excommunicationChoiceDialog;
	@FXML private JFXDialogLayout excommunicationChoiceDialogLayout;
	@FXML private Text excommunicationChoiceDialogText;
	@FXML private JFXButton excommunicationChoiceDialogAcceptButton;
	@FXML private JFXButton excommunicationChoiceDialogRefuseButton;
	@FXML private JFXDialog servantsChoiceDialog;
	@FXML private JFXSlider servantsChoiceDialogSlider;
	@FXML private JFXButton servantsChoiceDialogAcceptButton;
	@FXML private JFXButton servantsChoiceDialogCancelButton;
	@FXML private JFXDialog pickDevelopmentCardChoiceDialog;
	@FXML private JFXSlider pickDevelopmentCardChoiceDialogSlider;
	@FXML private HBox pickDevelopmentCardChoiceDialogDiscountChoicesHBox;
	@FXML private HBox pickDevelopmentCardChoiceDialogResourceCostOptionsHBox;
	@FXML private JFXButton pickDevelopmentCardChoiceDialogAcceptButton;
	@FXML private JFXButton pickDevelopmentCardChoiceDialogCancelButton;
	@FXML private JFXDialog cardDialog;
	@FXML private JFXDialogLayout cardDialogLayout;
	@FXML private Pane cardDialogPane;
	@FXML private ScrollPane cardDialogScrollPane;
	@FXML private Text cardDialogText;
	@FXML private JFXDialog leaderCardsDialog;
	@FXML private Pane player1LeaderCard1Hand;
	@FXML private Pane player1LeaderCard2Hand;
	@FXML private Pane player1LeaderCard3Hand;
	@FXML private Pane player1LeaderCard4Hand;
	@FXML private Pane player2LeaderCard1Hand;
	@FXML private Pane player2LeaderCard2Hand;
	@FXML private Pane player2LeaderCard3Hand;
	@FXML private Pane player2LeaderCard4Hand;
	@FXML private Pane player3LeaderCard1Hand;
	@FXML private Pane player3LeaderCard2Hand;
	@FXML private Pane player3LeaderCard3Hand;
	@FXML private Pane player3LeaderCard4Hand;
	@FXML private Pane player4LeaderCard1Hand;
	@FXML private Pane player4LeaderCard2Hand;
	@FXML private Pane player4LeaderCard3Hand;
	@FXML private Pane player4LeaderCard4Hand;
	@FXML private Pane player5LeaderCard1Hand;
	@FXML private Pane player5LeaderCard2Hand;
	@FXML private Pane player5LeaderCard3Hand;
	@FXML private Pane player5LeaderCard4Hand;
	@FXML private Pane player1LeaderCard1Played;
	@FXML private Pane player1LeaderCard2Played;
	@FXML private Pane player1LeaderCard3Played;
	@FXML private Pane player1LeaderCard4Played;
	@FXML private Pane player2LeaderCard1Played;
	@FXML private Pane player2LeaderCard2Played;
	@FXML private Pane player2LeaderCard3Played;
	@FXML private Pane player2LeaderCard4Played;
	@FXML private Pane player3LeaderCard1Played;
	@FXML private Pane player3LeaderCard2Played;
	@FXML private Pane player3LeaderCard3Played;
	@FXML private Pane player3LeaderCard4Played;
	@FXML private Pane player4LeaderCard1Played;
	@FXML private Pane player4LeaderCard2Played;
	@FXML private Pane player4LeaderCard3Played;
	@FXML private Pane player4LeaderCard4Played;
	@FXML private Pane player5LeaderCard1Played;
	@FXML private Pane player5LeaderCard2Played;
	@FXML private Pane player5LeaderCard3Played;
	@FXML private Pane player5LeaderCard4Played;
	private static final DropShadow MOUSE_OVER_EFFECT = new DropShadow();

	static {
		ControllerGame.MOUSE_OVER_EFFECT.setOffsetY(0.0D);
		ControllerGame.MOUSE_OVER_EFFECT.setOffsetX(0.0D);
		ControllerGame.MOUSE_OVER_EFFECT.setColor(Color.BLACK);
		ControllerGame.MOUSE_OVER_EFFECT.setWidth(40.0D);
		ControllerGame.MOUSE_OVER_EFFECT.setHeight(40.0D);
	}

	private static final DropShadow DEVELOPMENT_CARD_BUILDING_SELECTED_EFFECT = new DropShadow();

	static {
		ControllerGame.DEVELOPMENT_CARD_BUILDING_SELECTED_EFFECT.setOffsetY(0.0D);
		ControllerGame.DEVELOPMENT_CARD_BUILDING_SELECTED_EFFECT.setOffsetX(0.0D);
		ControllerGame.DEVELOPMENT_CARD_BUILDING_SELECTED_EFFECT.setColor(Color.YELLOW);
		ControllerGame.DEVELOPMENT_CARD_BUILDING_SELECTED_EFFECT.setWidth(40.0D);
		ControllerGame.DEVELOPMENT_CARD_BUILDING_SELECTED_EFFECT.setHeight(40.0D);
	}

	private static final DropShadow DEVELOPMENT_CARD_CHARACTER_SELECTED_EFFECT = new DropShadow();

	static {
		ControllerGame.DEVELOPMENT_CARD_CHARACTER_SELECTED_EFFECT.setOffsetY(0.0D);
		ControllerGame.DEVELOPMENT_CARD_CHARACTER_SELECTED_EFFECT.setOffsetX(0.0D);
		ControllerGame.DEVELOPMENT_CARD_CHARACTER_SELECTED_EFFECT.setColor(Color.BLUE);
		ControllerGame.DEVELOPMENT_CARD_CHARACTER_SELECTED_EFFECT.setWidth(40.0D);
		ControllerGame.DEVELOPMENT_CARD_CHARACTER_SELECTED_EFFECT.setHeight(40.0D);
	}

	private static final DropShadow DEVELOPMENT_CARD_TERRITORY_SELECTED_EFFECT = new DropShadow();

	static {
		ControllerGame.DEVELOPMENT_CARD_TERRITORY_SELECTED_EFFECT.setOffsetY(0.0D);
		ControllerGame.DEVELOPMENT_CARD_TERRITORY_SELECTED_EFFECT.setOffsetX(0.0D);
		ControllerGame.DEVELOPMENT_CARD_TERRITORY_SELECTED_EFFECT.setColor(Color.GREEN);
		ControllerGame.DEVELOPMENT_CARD_TERRITORY_SELECTED_EFFECT.setWidth(40.0D);
		ControllerGame.DEVELOPMENT_CARD_TERRITORY_SELECTED_EFFECT.setHeight(40.0D);
	}

	private static final DropShadow DEVELOPMENT_CARD_VENTURE_SELECTED_EFFECT = new DropShadow();

	static {
		ControllerGame.DEVELOPMENT_CARD_VENTURE_SELECTED_EFFECT.setOffsetY(0.0D);
		ControllerGame.DEVELOPMENT_CARD_VENTURE_SELECTED_EFFECT.setOffsetX(0.0D);
		ControllerGame.DEVELOPMENT_CARD_VENTURE_SELECTED_EFFECT.setColor(Color.PURPLE);
		ControllerGame.DEVELOPMENT_CARD_VENTURE_SELECTED_EFFECT.setWidth(40.0D);
		ControllerGame.DEVELOPMENT_CARD_VENTURE_SELECTED_EFFECT.setHeight(40.0D);
	}

	private final Map<Integer, Tab> playersTabs = new HashMap<>();
	private final Map<Integer, Tab> leaderCardsTabs = new HashMap<>();
	private final Map<Integer, Pane> playersBoards = new HashMap<>();
	private final Map<FamilyMemberType, Pane> dices = new EnumMap<>(FamilyMemberType.class);
	private final Map<Period, Pane> excommunicationTilesPanes = new EnumMap<>(Period.class);
	private final Map<Integer, Pane> victoryPointsPanes = new HashMap<>();
	private final Map<Integer, Pane> militaryPointsPanes = new HashMap<>();
	private final Map<Integer, Pane> faithPointsPanes = new HashMap<>();
	private final Map<Integer, Pane> prestigePointsPanes = new HashMap<>();
	private final Map<Integer, Pane> councilPalacePositionsPanes = new HashMap<>();
	private final Map<Integer, Pane> roundOrderPositionsPanes = new HashMap<>();
	private final Map<Integer, Pane> harvestBigPositionsPanes = new HashMap<>();
	private final Map<Integer, Label> harvestBigPositionsLabels = new HashMap<>();
	private final Map<Integer, Pane> productionBigPositionsPanes = new HashMap<>();
	private final Map<Integer, Label> productionBigPositionsLabels = new HashMap<>();
	private final Map<Row, Pane> developmentCardsBuildingPanes = new EnumMap<>(Row.class);
	private final Map<Row, Pane> developmentCardsCharacterPanes = new EnumMap<>(Row.class);
	private final Map<Row, Pane> developmentCardsTerritoryPanes = new EnumMap<>(Row.class);
	private final Map<Row, Pane> developmentCardsVenturePanes = new EnumMap<>(Row.class);
	private final Map<CardType, Map<Row, Pane>> developmentCardsPanes = new EnumMap<>(CardType.class);
	private final Map<BoardPosition, Pane> boardPositionsPanes = new EnumMap<>(BoardPosition.class);
	private final Map<CardType, List<Pane>> player1DevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<CardType, List<Pane>> player2DevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<CardType, List<Pane>> player3DevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<CardType, List<Pane>> player4DevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<CardType, List<Pane>> player5DevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<Integer, Map<CardType, List<Pane>>> playersDevelopmentCards = new HashMap<>();
	private final Map<Integer, List<Pane>> playersLeaderCardsHand = new HashMap<>();
	private final Map<Integer, List<Pane>> playersLeaderCardsPlayed = new HashMap<>();
	private final Map<ResourceType, Label> player1Resources = new EnumMap<>(ResourceType.class);
	private final Map<ResourceType, Label> player2Resources = new EnumMap<>(ResourceType.class);
	private final Map<ResourceType, Label> player3Resources = new EnumMap<>(ResourceType.class);
	private final Map<ResourceType, Label> player4Resources = new EnumMap<>(ResourceType.class);
	private final Map<ResourceType, Label> player5Resources = new EnumMap<>(ResourceType.class);
	private final Map<Integer, Map<ResourceType, Label>> playersResources = new HashMap<>();
	private final Map<Pane, Integer> developmentCardsBuildingIndexes = new HashMap<>();
	private final Map<Pane, Integer> developmentCardsCharacterIndexes = new HashMap<>();
	private final Map<Pane, Integer> developmentCardsTerritoryIndexes = new HashMap<>();
	private final Map<Pane, Integer> developmentCardsVentureIndexes = new HashMap<>();
	private final Map<PlayerData, Integer> playersHarvestBigPositions = new HashMap<>();
	private final Map<PlayerData, Integer> playersProductionBigPositions = new HashMap<>();
	private double ratio;
	private boolean pickingDevelopmentCard = false;
	private JFXButton pickDevelopmentCardActionButton;
	private final List<Pane> selectableDevelopmentCards = new ArrayList<>();
	private CardType selectedDevelopmentCardType;
	private Integer selectedDevelopmentCardIndex;
	private final List<ResourceAmount> selectedDiscountChoice = new ArrayList<>();
	private ResourceCostOption selectedResourceCostOption;
	private LeaderCardConditionsOption selectedLeaderConditionsOption;

	@FXML
	private void boardDevelopmentCardPaneMouseClicked(MouseEvent event)
	{
		Pane pane = (Pane) event.getSource();
		if (event.getButton() == MouseButton.PRIMARY && this.pickingDevelopmentCard && this.selectableDevelopmentCards.contains(pane)) {
			if (this.developmentCardsBuildingPanes.containsValue(pane)) {
				if (this.selectedDevelopmentCardType == CardType.BUILDING && this.selectedDevelopmentCardIndex.equals(this.developmentCardsBuildingIndexes.get(pane))) {
					this.pickDevelopmentCardActionButton.setDisable(true);
					this.selectedDevelopmentCardType = null;
					this.selectedDevelopmentCardIndex = null;
					pane.setEffect(null);
					Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
				} else {
					this.pickDevelopmentCardActionButton.setDisable(false);
					this.selectedDevelopmentCardType = CardType.BUILDING;
					this.selectedDevelopmentCardIndex = this.developmentCardsBuildingIndexes.get(pane);
					for (CardType cardType : CardType.values()) {
						for (Pane otherPane : this.developmentCardsPanes.get(cardType).values()) {
							otherPane.setEffect(null);
							if (this.selectableDevelopmentCards.contains(otherPane)) {
								Utils.setEffect(otherPane, ControllerGame.MOUSE_OVER_EFFECT);
							}
						}
					}
					pane.setEffect(ControllerGame.DEVELOPMENT_CARD_BUILDING_SELECTED_EFFECT);
					Utils.unsetEffect(pane);
				}
			} else if (this.developmentCardsCharacterPanes.containsValue(pane)) {
				if (this.selectedDevelopmentCardType == CardType.CHARACTER && this.selectedDevelopmentCardIndex.equals(this.developmentCardsCharacterIndexes.get(pane))) {
					this.pickDevelopmentCardActionButton.setDisable(true);
					this.selectedDevelopmentCardType = null;
					this.selectedDevelopmentCardIndex = null;
					pane.setEffect(null);
					Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
				} else {
					this.pickDevelopmentCardActionButton.setDisable(false);
					this.selectedDevelopmentCardType = CardType.CHARACTER;
					this.selectedDevelopmentCardIndex = this.developmentCardsCharacterIndexes.get(pane);
					for (CardType cardType : CardType.values()) {
						for (Pane otherPane : this.developmentCardsPanes.get(cardType).values()) {
							otherPane.setEffect(null);
							if (this.selectableDevelopmentCards.contains(otherPane)) {
								Utils.setEffect(otherPane, ControllerGame.MOUSE_OVER_EFFECT);
							}
						}
					}
					pane.setEffect(ControllerGame.DEVELOPMENT_CARD_CHARACTER_SELECTED_EFFECT);
					Utils.unsetEffect(pane);
				}
			} else if (this.developmentCardsTerritoryPanes.containsValue(pane)) {
				if (this.selectedDevelopmentCardType == CardType.TERRITORY && this.selectedDevelopmentCardIndex.equals(this.developmentCardsTerritoryIndexes.get(pane))) {
					this.pickDevelopmentCardActionButton.setDisable(true);
					this.selectedDevelopmentCardType = null;
					this.selectedDevelopmentCardIndex = null;
					pane.setEffect(null);
					Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
				} else {
					this.pickDevelopmentCardActionButton.setDisable(false);
					this.selectedDevelopmentCardType = CardType.TERRITORY;
					this.selectedDevelopmentCardIndex = this.developmentCardsTerritoryIndexes.get(pane);
					for (CardType cardType : CardType.values()) {
						for (Pane otherPane : this.developmentCardsPanes.get(cardType).values()) {
							otherPane.setEffect(null);
							if (this.selectableDevelopmentCards.contains(otherPane)) {
								Utils.setEffect(otherPane, ControllerGame.MOUSE_OVER_EFFECT);
							}
						}
					}
					pane.setEffect(ControllerGame.DEVELOPMENT_CARD_TERRITORY_SELECTED_EFFECT);
					Utils.unsetEffect(pane);
				}
			} else {
				if (this.selectedDevelopmentCardType == CardType.VENTURE && this.selectedDevelopmentCardIndex.equals(this.developmentCardsVentureIndexes.get(pane))) {
					this.pickDevelopmentCardActionButton.setDisable(true);
					this.selectedDevelopmentCardType = null;
					this.selectedDevelopmentCardIndex = null;
					pane.setEffect(null);
					Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
				} else {
					this.pickDevelopmentCardActionButton.setDisable(false);
					this.selectedDevelopmentCardType = CardType.VENTURE;
					this.selectedDevelopmentCardIndex = this.developmentCardsVentureIndexes.get(pane);
					for (CardType cardType : CardType.values()) {
						for (Pane otherPane : this.developmentCardsPanes.get(cardType).values()) {
							otherPane.setEffect(null);
							if (this.selectableDevelopmentCards.contains(otherPane)) {
								Utils.setEffect(otherPane, ControllerGame.MOUSE_OVER_EFFECT);
							}
						}
					}
					pane.setEffect(ControllerGame.DEVELOPMENT_CARD_VENTURE_SELECTED_EFFECT);
					Utils.unsetEffect(pane);
				}
			}
		} else if (event.getButton() == MouseButton.SECONDARY && pane.getBackground() != null) {
			this.cardDialogPane.setBackground(pane.getBackground());
			this.cardDialogText.setText(this.getDevelopmentCardInformations(pane));
			this.cardDialog.show();
		}
	}

	@FXML
	private void playerDevelopmentCardPaneMouseClicked(MouseEvent event)
	{
		if (event.getButton() == MouseButton.SECONDARY && ((Pane) event.getSource()).getBackground() != null) {
			this.cardDialogText.setText(this.getDevelopmentCardInformations((Pane) event.getSource()));
			this.cardDialogPane.setBackground(((Pane) event.getSource()).getBackground());
			this.cardDialog.show();
		}
	}

	@FXML
	private void handleChatTextAreaAction(ActionEvent event)
	{
		String text = ((TextField) event.getSource()).getText().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		if (text.length() < 1) {
			return;
		}
		((TextField) event.getSource()).clear();
		Client.getInstance().getConnectionHandler().sendChatMessage(text);
		if (this.chatTextArea.getText().length() < 1) {
			this.chatTextArea.appendText("[ME]: " + text);
		} else {
			this.chatTextArea.appendText("\n[ME]: " + text);
		}
	}

	@FXML
	private void handleLeaderCardsButtonAction()
	{
		this.leaderCardsDialog.show();
	}

	@FXML
	private void handleExcommunicationChoiceDialogAcceptButtonAction()
	{
		this.excommunicationChoiceDialog.close();
		Client.getInstance().getConnectionHandler().sendGameExcommunicationPlayerChoice(true);
	}

	@FXML
	private void handleExcommunicationChoiceDialogRefuseButtonAction()
	{
		this.excommunicationChoiceDialog.close();
		Client.getInstance().getConnectionHandler().sendGameExcommunicationPlayerChoice(false);
	}

	@FXML
	private void handleServantsChoiceDialogCancelButtonAction()
	{
		this.servantsChoiceDialog.close();
	}

	@FXML
	private void handlePickDevelopmentCardChoiceDialogCancelButtonAction()
	{
		this.pickDevelopmentCardChoiceDialog.close();
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.playersTabs.put(0, this.player1Tab);
		this.playersTabs.put(1, this.player2Tab);
		this.playersTabs.put(2, this.player3Tab);
		this.playersTabs.put(3, this.player4Tab);
		this.playersTabs.put(4, this.player5Tab);
		this.leaderCardsTabs.put(0, this.leaderCardsPlayer1Tab);
		this.leaderCardsTabs.put(1, this.leaderCardsPlayer2Tab);
		this.leaderCardsTabs.put(2, this.leaderCardsPlayer3Tab);
		this.leaderCardsTabs.put(3, this.leaderCardsPlayer4Tab);
		this.leaderCardsTabs.put(4, this.leaderCardsPlayer5Tab);
		this.playersBoards.put(0, this.playerBoard1);
		this.playersBoards.put(1, this.playerBoard2);
		this.playersBoards.put(2, this.playerBoard3);
		this.playersBoards.put(3, this.playerBoard4);
		this.playersBoards.put(4, this.playerBoard5);
		this.dices.put(FamilyMemberType.BLACK, this.diceBlack);
		this.dices.put(FamilyMemberType.ORANGE, this.diceOrange);
		this.dices.put(FamilyMemberType.WHITE, this.diceWhite);
		this.excommunicationTilesPanes.put(Period.FIRST, this.excommunicationTileFirst);
		this.excommunicationTilesPanes.put(Period.SECOND, this.excommunicationTileSecond);
		this.excommunicationTilesPanes.put(Period.THIRD, this.excommunicationTileThird);
		this.victoryPointsPanes.put(0, this.victoryPoint0);
		this.victoryPointsPanes.put(1, this.victoryPoint1);
		this.victoryPointsPanes.put(2, this.victoryPoint2);
		this.victoryPointsPanes.put(3, this.victoryPoint3);
		this.victoryPointsPanes.put(4, this.victoryPoint4);
		this.victoryPointsPanes.put(5, this.victoryPoint5);
		this.victoryPointsPanes.put(6, this.victoryPoint6);
		this.victoryPointsPanes.put(7, this.victoryPoint7);
		this.victoryPointsPanes.put(8, this.victoryPoint8);
		this.victoryPointsPanes.put(9, this.victoryPoint9);
		this.victoryPointsPanes.put(10, this.victoryPoint10);
		this.victoryPointsPanes.put(11, this.victoryPoint11);
		this.victoryPointsPanes.put(12, this.victoryPoint12);
		this.victoryPointsPanes.put(13, this.victoryPoint13);
		this.victoryPointsPanes.put(14, this.victoryPoint14);
		this.victoryPointsPanes.put(15, this.victoryPoint15);
		this.victoryPointsPanes.put(16, this.victoryPoint16);
		this.victoryPointsPanes.put(17, this.victoryPoint17);
		this.victoryPointsPanes.put(18, this.victoryPoint18);
		this.victoryPointsPanes.put(19, this.victoryPoint19);
		this.victoryPointsPanes.put(20, this.victoryPoint20);
		this.victoryPointsPanes.put(21, this.victoryPoint21);
		this.victoryPointsPanes.put(22, this.victoryPoint22);
		this.victoryPointsPanes.put(23, this.victoryPoint23);
		this.victoryPointsPanes.put(24, this.victoryPoint24);
		this.victoryPointsPanes.put(25, this.victoryPoint25);
		this.victoryPointsPanes.put(26, this.victoryPoint26);
		this.victoryPointsPanes.put(27, this.victoryPoint27);
		this.victoryPointsPanes.put(28, this.victoryPoint28);
		this.victoryPointsPanes.put(29, this.victoryPoint29);
		this.victoryPointsPanes.put(30, this.victoryPoint30);
		this.victoryPointsPanes.put(31, this.victoryPoint31);
		this.victoryPointsPanes.put(32, this.victoryPoint32);
		this.victoryPointsPanes.put(33, this.victoryPoint33);
		this.victoryPointsPanes.put(34, this.victoryPoint34);
		this.victoryPointsPanes.put(35, this.victoryPoint35);
		this.victoryPointsPanes.put(36, this.victoryPoint36);
		this.victoryPointsPanes.put(37, this.victoryPoint37);
		this.victoryPointsPanes.put(38, this.victoryPoint38);
		this.victoryPointsPanes.put(39, this.victoryPoint39);
		this.victoryPointsPanes.put(40, this.victoryPoint40);
		this.victoryPointsPanes.put(41, this.victoryPoint41);
		this.victoryPointsPanes.put(42, this.victoryPoint42);
		this.victoryPointsPanes.put(43, this.victoryPoint43);
		this.victoryPointsPanes.put(44, this.victoryPoint44);
		this.victoryPointsPanes.put(45, this.victoryPoint45);
		this.victoryPointsPanes.put(46, this.victoryPoint46);
		this.victoryPointsPanes.put(47, this.victoryPoint47);
		this.victoryPointsPanes.put(48, this.victoryPoint48);
		this.victoryPointsPanes.put(49, this.victoryPoint49);
		this.victoryPointsPanes.put(50, this.victoryPoint50);
		this.victoryPointsPanes.put(51, this.victoryPoint51);
		this.victoryPointsPanes.put(52, this.victoryPoint52);
		this.victoryPointsPanes.put(53, this.victoryPoint53);
		this.victoryPointsPanes.put(54, this.victoryPoint54);
		this.victoryPointsPanes.put(55, this.victoryPoint55);
		this.victoryPointsPanes.put(56, this.victoryPoint56);
		this.victoryPointsPanes.put(57, this.victoryPoint57);
		this.victoryPointsPanes.put(58, this.victoryPoint58);
		this.victoryPointsPanes.put(59, this.victoryPoint59);
		this.victoryPointsPanes.put(60, this.victoryPoint60);
		this.victoryPointsPanes.put(61, this.victoryPoint61);
		this.victoryPointsPanes.put(62, this.victoryPoint62);
		this.victoryPointsPanes.put(63, this.victoryPoint63);
		this.victoryPointsPanes.put(64, this.victoryPoint64);
		this.victoryPointsPanes.put(65, this.victoryPoint65);
		this.victoryPointsPanes.put(66, this.victoryPoint66);
		this.victoryPointsPanes.put(67, this.victoryPoint67);
		this.victoryPointsPanes.put(68, this.victoryPoint68);
		this.victoryPointsPanes.put(69, this.victoryPoint69);
		this.victoryPointsPanes.put(70, this.victoryPoint70);
		this.victoryPointsPanes.put(71, this.victoryPoint71);
		this.victoryPointsPanes.put(72, this.victoryPoint72);
		this.victoryPointsPanes.put(73, this.victoryPoint73);
		this.victoryPointsPanes.put(74, this.victoryPoint74);
		this.victoryPointsPanes.put(75, this.victoryPoint75);
		this.victoryPointsPanes.put(76, this.victoryPoint76);
		this.victoryPointsPanes.put(77, this.victoryPoint77);
		this.victoryPointsPanes.put(78, this.victoryPoint78);
		this.victoryPointsPanes.put(79, this.victoryPoint79);
		this.victoryPointsPanes.put(80, this.victoryPoint80);
		this.victoryPointsPanes.put(81, this.victoryPoint81);
		this.victoryPointsPanes.put(82, this.victoryPoint82);
		this.victoryPointsPanes.put(83, this.victoryPoint83);
		this.victoryPointsPanes.put(84, this.victoryPoint84);
		this.victoryPointsPanes.put(85, this.victoryPoint85);
		this.victoryPointsPanes.put(86, this.victoryPoint86);
		this.victoryPointsPanes.put(87, this.victoryPoint87);
		this.victoryPointsPanes.put(88, this.victoryPoint88);
		this.victoryPointsPanes.put(89, this.victoryPoint89);
		this.victoryPointsPanes.put(90, this.victoryPoint90);
		this.victoryPointsPanes.put(91, this.victoryPoint91);
		this.victoryPointsPanes.put(92, this.victoryPoint92);
		this.victoryPointsPanes.put(93, this.victoryPoint93);
		this.victoryPointsPanes.put(94, this.victoryPoint94);
		this.victoryPointsPanes.put(95, this.victoryPoint95);
		this.victoryPointsPanes.put(96, this.victoryPoint96);
		this.victoryPointsPanes.put(97, this.victoryPoint97);
		this.victoryPointsPanes.put(98, this.victoryPoint98);
		this.victoryPointsPanes.put(99, this.victoryPoint99);
		this.militaryPointsPanes.put(0, this.militaryPoint0);
		this.militaryPointsPanes.put(1, this.militaryPoint1);
		this.militaryPointsPanes.put(2, this.militaryPoint2);
		this.militaryPointsPanes.put(3, this.militaryPoint3);
		this.militaryPointsPanes.put(4, this.militaryPoint4);
		this.militaryPointsPanes.put(5, this.militaryPoint5);
		this.militaryPointsPanes.put(6, this.militaryPoint6);
		this.militaryPointsPanes.put(7, this.militaryPoint7);
		this.militaryPointsPanes.put(8, this.militaryPoint8);
		this.militaryPointsPanes.put(9, this.militaryPoint9);
		this.militaryPointsPanes.put(10, this.militaryPoint10);
		this.militaryPointsPanes.put(11, this.militaryPoint11);
		this.militaryPointsPanes.put(12, this.militaryPoint12);
		this.militaryPointsPanes.put(13, this.militaryPoint13);
		this.militaryPointsPanes.put(14, this.militaryPoint14);
		this.militaryPointsPanes.put(15, this.militaryPoint15);
		this.militaryPointsPanes.put(16, this.militaryPoint16);
		this.militaryPointsPanes.put(17, this.militaryPoint17);
		this.militaryPointsPanes.put(18, this.militaryPoint18);
		this.militaryPointsPanes.put(19, this.militaryPoint19);
		this.militaryPointsPanes.put(20, this.militaryPoint20);
		this.militaryPointsPanes.put(21, this.militaryPoint21);
		this.militaryPointsPanes.put(22, this.militaryPoint22);
		this.militaryPointsPanes.put(23, this.militaryPoint23);
		this.militaryPointsPanes.put(24, this.militaryPoint24);
		this.militaryPointsPanes.put(25, this.militaryPoint25);
		this.faithPointsPanes.put(0, this.faithPoint0);
		this.faithPointsPanes.put(1, this.faithPoint1);
		this.faithPointsPanes.put(2, this.faithPoint2);
		this.faithPointsPanes.put(3, this.faithPoint3);
		this.faithPointsPanes.put(4, this.faithPoint4);
		this.faithPointsPanes.put(5, this.faithPoint5);
		this.faithPointsPanes.put(6, this.faithPoint6);
		this.faithPointsPanes.put(7, this.faithPoint7);
		this.faithPointsPanes.put(8, this.faithPoint8);
		this.faithPointsPanes.put(9, this.faithPoint9);
		this.faithPointsPanes.put(10, this.faithPoint10);
		this.faithPointsPanes.put(11, this.faithPoint11);
		this.faithPointsPanes.put(12, this.faithPoint12);
		this.faithPointsPanes.put(13, this.faithPoint13);
		this.faithPointsPanes.put(14, this.faithPoint14);
		this.faithPointsPanes.put(15, this.faithPoint15);
		this.prestigePointsPanes.put(0, this.prestigePoint0);
		this.prestigePointsPanes.put(1, this.prestigePoint1);
		this.prestigePointsPanes.put(2, this.prestigePoint2);
		this.prestigePointsPanes.put(3, this.prestigePoint3);
		this.prestigePointsPanes.put(4, this.prestigePoint4);
		this.prestigePointsPanes.put(5, this.prestigePoint5);
		this.prestigePointsPanes.put(6, this.prestigePoint6);
		this.prestigePointsPanes.put(7, this.prestigePoint7);
		this.prestigePointsPanes.put(8, this.prestigePoint8);
		this.prestigePointsPanes.put(9, this.prestigePoint9);
		this.councilPalacePositionsPanes.put(0, this.councilPalace1);
		this.councilPalacePositionsPanes.put(1, this.councilPalace2);
		this.councilPalacePositionsPanes.put(2, this.councilPalace3);
		this.councilPalacePositionsPanes.put(3, this.councilPalace4);
		this.councilPalacePositionsPanes.put(4, this.councilPalace5);
		this.roundOrderPositionsPanes.put(0, this.roundOrderPosition1);
		this.roundOrderPositionsPanes.put(1, this.roundOrderPosition2);
		this.roundOrderPositionsPanes.put(2, this.roundOrderPosition3);
		this.roundOrderPositionsPanes.put(3, this.roundOrderPosition4);
		this.roundOrderPositionsPanes.put(4, this.roundOrderPosition5);
		this.harvestBigPositionsPanes.put(0, this.harvestBig1);
		this.harvestBigPositionsPanes.put(1, this.harvestBig2);
		this.harvestBigPositionsPanes.put(2, this.harvestBig3);
		this.harvestBigPositionsPanes.put(3, this.harvestBig4);
		this.harvestBigPositionsPanes.put(4, this.harvestBig5);
		this.harvestBigPositionsLabels.put(0, this.harvestBigLabel1);
		this.harvestBigPositionsLabels.put(1, this.harvestBigLabel2);
		this.harvestBigPositionsLabels.put(2, this.harvestBigLabel3);
		this.harvestBigPositionsLabels.put(3, this.harvestBigLabel4);
		this.harvestBigPositionsLabels.put(4, this.harvestBigLabel5);
		this.productionBigPositionsPanes.put(0, this.productionBig1);
		this.productionBigPositionsPanes.put(1, this.productionBig2);
		this.productionBigPositionsPanes.put(2, this.productionBig3);
		this.productionBigPositionsPanes.put(3, this.productionBig4);
		this.productionBigPositionsPanes.put(4, this.productionBig5);
		this.productionBigPositionsLabels.put(0, this.productionBigLabel1);
		this.productionBigPositionsLabels.put(1, this.productionBigLabel2);
		this.productionBigPositionsLabels.put(2, this.productionBigLabel3);
		this.productionBigPositionsLabels.put(3, this.productionBigLabel4);
		this.productionBigPositionsLabels.put(4, this.productionBigLabel5);
		this.developmentCardsBuildingPanes.put(Row.FIRST, this.building1);
		this.developmentCardsBuildingPanes.put(Row.SECOND, this.building2);
		this.developmentCardsBuildingPanes.put(Row.THIRD, this.building3);
		this.developmentCardsBuildingPanes.put(Row.FOURTH, this.building4);
		this.developmentCardsCharacterPanes.put(Row.FIRST, this.character1);
		this.developmentCardsCharacterPanes.put(Row.SECOND, this.character2);
		this.developmentCardsCharacterPanes.put(Row.THIRD, this.character3);
		this.developmentCardsCharacterPanes.put(Row.FOURTH, this.character4);
		this.developmentCardsTerritoryPanes.put(Row.FIRST, this.territory1);
		this.developmentCardsTerritoryPanes.put(Row.SECOND, this.territory2);
		this.developmentCardsTerritoryPanes.put(Row.THIRD, this.territory3);
		this.developmentCardsTerritoryPanes.put(Row.FOURTH, this.territory4);
		this.developmentCardsVenturePanes.put(Row.FIRST, this.venture1);
		this.developmentCardsVenturePanes.put(Row.SECOND, this.venture2);
		this.developmentCardsVenturePanes.put(Row.THIRD, this.venture3);
		this.developmentCardsVenturePanes.put(Row.FOURTH, this.venture4);
		this.developmentCardsPanes.put(CardType.BUILDING, this.developmentCardsBuildingPanes);
		this.developmentCardsPanes.put(CardType.CHARACTER, this.developmentCardsCharacterPanes);
		this.developmentCardsPanes.put(CardType.TERRITORY, this.developmentCardsTerritoryPanes);
		this.developmentCardsPanes.put(CardType.VENTURE, this.developmentCardsVenturePanes);
		this.boardPositionsPanes.put(BoardPosition.BUILDING_1, this.slotBuilding1);
		this.boardPositionsPanes.put(BoardPosition.BUILDING_2, this.slotBuilding2);
		this.boardPositionsPanes.put(BoardPosition.BUILDING_3, this.slotBuilding3);
		this.boardPositionsPanes.put(BoardPosition.BUILDING_4, this.slotBuilding4);
		this.boardPositionsPanes.put(BoardPosition.CHARACTER_1, this.slotCharacter1);
		this.boardPositionsPanes.put(BoardPosition.CHARACTER_2, this.slotCharacter2);
		this.boardPositionsPanes.put(BoardPosition.CHARACTER_3, this.slotCharacter3);
		this.boardPositionsPanes.put(BoardPosition.CHARACTER_4, this.slotCharacter4);
		this.boardPositionsPanes.put(BoardPosition.TERRITORY_1, this.slotTerritory1);
		this.boardPositionsPanes.put(BoardPosition.TERRITORY_2, this.slotTerritory2);
		this.boardPositionsPanes.put(BoardPosition.TERRITORY_3, this.slotTerritory3);
		this.boardPositionsPanes.put(BoardPosition.TERRITORY_4, this.slotTerritory4);
		this.boardPositionsPanes.put(BoardPosition.VENTURE_1, this.slotVenture1);
		this.boardPositionsPanes.put(BoardPosition.VENTURE_2, this.slotVenture2);
		this.boardPositionsPanes.put(BoardPosition.VENTURE_3, this.slotVenture3);
		this.boardPositionsPanes.put(BoardPosition.VENTURE_4, this.slotVenture4);
		this.boardPositionsPanes.put(BoardPosition.MARKET_1, this.market1);
		this.boardPositionsPanes.put(BoardPosition.MARKET_2, this.market2);
		this.boardPositionsPanes.put(BoardPosition.MARKET_3, this.market3);
		this.boardPositionsPanes.put(BoardPosition.MARKET_4, this.market4);
		this.boardPositionsPanes.put(BoardPosition.MARKET_5, this.market5);
		this.boardPositionsPanes.put(BoardPosition.MARKET_6, this.market6);
		this.boardPositionsPanes.put(BoardPosition.HARVEST_SMALL, this.harvestSmall);
		this.boardPositionsPanes.put(BoardPosition.PRODUCTION_SMALL, this.productionSmall);
		this.player1DevelopmentCards.put(CardType.BUILDING, Arrays.asList(this.player1Building1, this.player1Building2, this.player1Building3, this.player1Building4, this.player1Building5, this.player1Building6));
		this.player1DevelopmentCards.put(CardType.CHARACTER, Arrays.asList(this.player1Character1, this.player1Character2, this.player1Character3, this.player1Character4, this.player1Character5, this.player1Character6));
		this.player1DevelopmentCards.put(CardType.TERRITORY, Arrays.asList(this.player1Territory1, this.player1Territory2, this.player1Territory3, this.player1Territory4, this.player1Territory5, this.player1Territory6));
		this.player1DevelopmentCards.put(CardType.VENTURE, Arrays.asList(this.player1Venture1, this.player1Venture2, this.player1Venture3, this.player1Venture4, this.player1Venture5, this.player1Venture6));
		this.player2DevelopmentCards.put(CardType.BUILDING, Arrays.asList(this.player2Building1, this.player2Building2, this.player2Building3, this.player2Building4, this.player2Building5, this.player2Building6));
		this.player2DevelopmentCards.put(CardType.CHARACTER, Arrays.asList(this.player2Character1, this.player2Character2, this.player2Character3, this.player2Character4, this.player2Character5, this.player2Character6));
		this.player2DevelopmentCards.put(CardType.TERRITORY, Arrays.asList(this.player2Territory1, this.player2Territory2, this.player2Territory3, this.player2Territory4, this.player2Territory5, this.player2Territory6));
		this.player2DevelopmentCards.put(CardType.VENTURE, Arrays.asList(this.player2Venture1, this.player2Venture2, this.player2Venture3, this.player2Venture4, this.player2Venture5, this.player2Venture6));
		this.player3DevelopmentCards.put(CardType.BUILDING, Arrays.asList(this.player3Building1, this.player3Building2, this.player3Building3, this.player3Building4, this.player3Building5, this.player3Building6));
		this.player3DevelopmentCards.put(CardType.CHARACTER, Arrays.asList(this.player3Character1, this.player3Character2, this.player3Character3, this.player3Character4, this.player3Character5, this.player3Character6));
		this.player3DevelopmentCards.put(CardType.TERRITORY, Arrays.asList(this.player3Territory1, this.player3Territory2, this.player3Territory3, this.player3Territory4, this.player3Territory5, this.player3Territory6));
		this.player3DevelopmentCards.put(CardType.VENTURE, Arrays.asList(this.player3Venture1, this.player3Venture2, this.player3Venture3, this.player3Venture4, this.player3Venture5, this.player3Venture6));
		this.player4DevelopmentCards.put(CardType.BUILDING, Arrays.asList(this.player4Building1, this.player4Building2, this.player4Building3, this.player4Building4, this.player4Building5, this.player4Building6));
		this.player4DevelopmentCards.put(CardType.CHARACTER, Arrays.asList(this.player4Character1, this.player4Character2, this.player4Character3, this.player4Character4, this.player4Character5, this.player4Character6));
		this.player4DevelopmentCards.put(CardType.TERRITORY, Arrays.asList(this.player4Territory1, this.player4Territory2, this.player4Territory3, this.player4Territory4, this.player4Territory5, this.player4Territory6));
		this.player4DevelopmentCards.put(CardType.VENTURE, Arrays.asList(this.player4Venture1, this.player4Venture2, this.player4Venture3, this.player4Venture4, this.player4Venture5, this.player4Venture6));
		this.player5DevelopmentCards.put(CardType.BUILDING, Arrays.asList(this.player5Building1, this.player5Building2, this.player5Building3, this.player5Building4, this.player5Building5, this.player5Building6));
		this.player5DevelopmentCards.put(CardType.CHARACTER, Arrays.asList(this.player5Character1, this.player5Character2, this.player5Character3, this.player5Character4, this.player5Character5, this.player5Character6));
		this.player5DevelopmentCards.put(CardType.TERRITORY, Arrays.asList(this.player5Territory1, this.player5Territory2, this.player5Territory3, this.player5Territory4, this.player5Territory5, this.player5Territory6));
		this.player5DevelopmentCards.put(CardType.VENTURE, Arrays.asList(this.player5Venture1, this.player5Venture2, this.player5Venture3, this.player5Venture4, this.player5Venture5, this.player5Venture6));
		this.playersDevelopmentCards.put(0, this.player1DevelopmentCards);
		this.playersDevelopmentCards.put(1, this.player2DevelopmentCards);
		this.playersDevelopmentCards.put(2, this.player3DevelopmentCards);
		this.playersDevelopmentCards.put(3, this.player4DevelopmentCards);
		this.playersDevelopmentCards.put(4, this.player5DevelopmentCards);
		this.playersLeaderCardsHand.put(0, Arrays.asList(this.player1LeaderCard1Hand, this.player1LeaderCard2Hand, this.player1LeaderCard3Hand, this.player1LeaderCard4Hand));
		this.playersLeaderCardsHand.put(1, Arrays.asList(this.player2LeaderCard1Hand, this.player2LeaderCard2Hand, this.player2LeaderCard3Hand, this.player2LeaderCard4Hand));
		this.playersLeaderCardsHand.put(2, Arrays.asList(this.player3LeaderCard1Hand, this.player3LeaderCard2Hand, this.player3LeaderCard3Hand, this.player3LeaderCard4Hand));
		this.playersLeaderCardsHand.put(3, Arrays.asList(this.player4LeaderCard1Hand, this.player4LeaderCard2Hand, this.player4LeaderCard3Hand, this.player4LeaderCard4Hand));
		this.playersLeaderCardsHand.put(4, Arrays.asList(this.player5LeaderCard1Hand, this.player5LeaderCard2Hand, this.player5LeaderCard3Hand, this.player5LeaderCard4Hand));
		this.playersLeaderCardsPlayed.put(0, Arrays.asList(this.player1LeaderCard1Played, this.player1LeaderCard2Played, this.player1LeaderCard3Played, this.player1LeaderCard4Played));
		this.playersLeaderCardsPlayed.put(1, Arrays.asList(this.player2LeaderCard1Played, this.player2LeaderCard2Played, this.player2LeaderCard3Played, this.player2LeaderCard4Played));
		this.playersLeaderCardsPlayed.put(2, Arrays.asList(this.player3LeaderCard1Played, this.player3LeaderCard2Played, this.player3LeaderCard3Played, this.player3LeaderCard4Played));
		this.playersLeaderCardsPlayed.put(3, Arrays.asList(this.player4LeaderCard1Played, this.player4LeaderCard2Played, this.player4LeaderCard3Played, this.player4LeaderCard4Played));
		this.playersLeaderCardsPlayed.put(4, Arrays.asList(this.player5LeaderCard1Played, this.player5LeaderCard2Played, this.player5LeaderCard3Played, this.player5LeaderCard4Played));
		this.player1Resources.put(ResourceType.COIN, this.player1Coin);
		this.player1Resources.put(ResourceType.SERVANT, this.player1Servant);
		this.player1Resources.put(ResourceType.STONE, this.player1Stone);
		this.player1Resources.put(ResourceType.WOOD, this.player1Wood);
		this.player2Resources.put(ResourceType.COIN, this.player2Coin);
		this.player2Resources.put(ResourceType.SERVANT, this.player2Servant);
		this.player2Resources.put(ResourceType.STONE, this.player2Stone);
		this.player2Resources.put(ResourceType.WOOD, this.player2Wood);
		this.player3Resources.put(ResourceType.COIN, this.player3Coin);
		this.player3Resources.put(ResourceType.SERVANT, this.player3Servant);
		this.player3Resources.put(ResourceType.STONE, this.player3Stone);
		this.player3Resources.put(ResourceType.WOOD, this.player3Wood);
		this.player4Resources.put(ResourceType.COIN, this.player4Coin);
		this.player4Resources.put(ResourceType.SERVANT, this.player4Servant);
		this.player4Resources.put(ResourceType.STONE, this.player4Stone);
		this.player4Resources.put(ResourceType.WOOD, this.player4Wood);
		this.player5Resources.put(ResourceType.COIN, this.player5Coin);
		this.player5Resources.put(ResourceType.SERVANT, this.player5Servant);
		this.player5Resources.put(ResourceType.STONE, this.player5Stone);
		this.player5Resources.put(ResourceType.WOOD, this.player5Wood);
		this.playersResources.put(0, this.player1Resources);
		this.playersResources.put(1, this.player2Resources);
		this.playersResources.put(2, this.player3Resources);
		this.playersResources.put(3, this.player4Resources);
		this.playersResources.put(4, this.player5Resources);
		this.getStackPane().getChildren().remove(this.personalBonusTilesChoiceDialog);
		this.personalBonusTilesChoiceDialog.setTransitionType(DialogTransition.CENTER);
		this.personalBonusTilesChoiceDialog.setDialogContainer(this.getStackPane());
		this.personalBonusTilesChoiceDialog.setOverlayClose(false);
		this.personalBonusTilesChoiceDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.leaderCardsChoiceDialog);
		this.leaderCardsChoiceDialog.setTransitionType(DialogTransition.CENTER);
		this.leaderCardsChoiceDialog.setDialogContainer(this.getStackPane());
		this.leaderCardsChoiceDialog.setOverlayClose(false);
		this.leaderCardsChoiceDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.excommunicationChoiceDialog);
		this.excommunicationChoiceDialog.setTransitionType(DialogTransition.CENTER);
		this.excommunicationChoiceDialog.setDialogContainer(this.getStackPane());
		this.excommunicationChoiceDialog.setOverlayClose(false);
		this.excommunicationChoiceDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.servantsChoiceDialog);
		this.servantsChoiceDialog.setTransitionType(DialogTransition.CENTER);
		this.servantsChoiceDialog.setDialogContainer(this.getStackPane());
		this.servantsChoiceDialog.setOverlayClose(false);
		this.servantsChoiceDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.pickDevelopmentCardChoiceDialog);
		this.pickDevelopmentCardChoiceDialog.setTransitionType(DialogTransition.CENTER);
		this.pickDevelopmentCardChoiceDialog.setDialogContainer(this.getStackPane());
		this.pickDevelopmentCardChoiceDialog.setOverlayClose(false);
		this.pickDevelopmentCardChoiceDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.cardDialog);
		this.cardDialog.setTransitionType(DialogTransition.CENTER);
		this.cardDialog.setDialogContainer(this.getStackPane());
		this.cardDialogLayout.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.leaderCardsDialog);
		this.leaderCardsDialog.setTransitionType(DialogTransition.CENTER);
		this.leaderCardsDialog.setDialogContainer(this.getStackPane());
		this.leaderCardsDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getStylesheets().add(Client.getInstance().getClass().getResource("/css/jfoenix-nodes-list-button.css").toExternalForm());
		for (CardType cardType : CardType.values()) {
			for (Pane pane : this.developmentCardsPanes.get(cardType).values()) {
				Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
			}
			for (int index = 0; index < 5; index++) {
				for (Pane pane : this.playersDevelopmentCards.get(index).get(cardType)) {
					Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
				}
			}
		}
		this.gameBoard.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(ControllerGame.BOARD_IMAGES.get(GameStatus.getInstance().getCurrentPlayersData().size())).toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
		for (Entry<Integer, Tab> tab : this.playersTabs.entrySet()) {
			if (!GameStatus.getInstance().getCurrentPlayersData().keySet().contains(tab.getKey())) {
				this.playersTabPane.getTabs().remove(tab.getValue());
			} else {
				tab.getValue().setText((GameStatus.getInstance().getOwnPlayerIndex() == tab.getKey() ? "[ME] " : "") + GameStatus.getInstance().getCurrentPlayersData().get(tab.getKey()).getUsername());
			}
		}
		for (Entry<Integer, Tab> tab : this.leaderCardsTabs.entrySet()) {
			if (!GameStatus.getInstance().getCurrentPlayersData().keySet().contains(tab.getKey())) {
				this.leaderCardsTabPane.getTabs().remove(tab.getValue());
			} else {
				tab.getValue().setText((GameStatus.getInstance().getOwnPlayerIndex() == tab.getKey() ? "[ME] " : "") + GameStatus.getInstance().getCurrentPlayersData().get(tab.getKey()).getUsername());
			}
		}
		for (Entry<Integer, PlayerData> playerData : GameStatus.getInstance().getCurrentPlayersData().entrySet()) {
			((AnchorPane) this.playersBoards.get(playerData.getKey()).getParent().getParent()).setBorder(new Border(new BorderStroke(Color.web(playerData.getValue().getColor().getHex()), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4.0D))));
		}
		for (Entry<Period, Integer> excommunicationTile : GameStatus.getInstance().getCurrentExcommunicationTiles().entrySet()) {
			this.excommunicationTilesPanes.get(excommunicationTile.getKey()).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getExcommunicationTiles().get(excommunicationTile.getValue()).getTexturePath()).toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			Tooltip tooltip = new Tooltip(GameStatus.getInstance().getExcommunicationTiles().get(excommunicationTile.getValue()).getModifier());
			WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
			WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
			Tooltip.install(this.excommunicationTilesPanes.get(excommunicationTile.getKey()), tooltip);
		}
		this.actionsVBox.getChildren().clear();
		JFXNodesList actionsNodesList = new JFXNodesList();
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action.png", true);
		this.actionsVBox.getChildren().add(actionsNodesList);
	}

	@Override
	@PostConstruct
	public void setupGui()
	{
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		this.ratio = (bounds.getHeight() - bounds.getHeight() / 15) / this.getStage().getHeight();
		this.gameBoard.setPrefWidth(this.gameBoard.getWidth() * this.ratio);
		this.gameBoard.setPrefHeight(this.gameBoard.getHeight() * this.ratio);
		this.playersTabPane.setPrefWidth(this.playersTabPane.getWidth() * this.ratio);
		this.playersTabPane.setPrefHeight(this.playersTabPane.getHeight() * this.ratio);
		this.playerBoard1.setPrefWidth(this.playerBoard1.getWidth() * this.ratio);
		this.playerBoard1.setPrefHeight(this.playerBoard1.getHeight() * this.ratio);
		this.playerBoard1DevelopmentCardsVenture.setPrefWidth(this.playerBoard1DevelopmentCardsVenture.getWidth() * this.ratio);
		this.playerBoard1DevelopmentCardsVenture.setPrefHeight(this.playerBoard1DevelopmentCardsVenture.getHeight() * this.ratio);
		this.playerBoard1DevelopmentCardsCharacter.setPrefWidth(this.playerBoard1DevelopmentCardsCharacter.getWidth() * this.ratio);
		this.playerBoard1DevelopmentCardsCharacter.setPrefHeight(this.playerBoard1DevelopmentCardsCharacter.getHeight() * this.ratio);
		this.playerBoard2.setPrefWidth(this.playerBoard2.getWidth() * this.ratio);
		this.playerBoard2.setPrefHeight(this.playerBoard2.getHeight() * this.ratio);
		this.playerBoard2DevelopmentCardsVenture.setPrefWidth(this.playerBoard2DevelopmentCardsVenture.getWidth() * this.ratio);
		this.playerBoard2DevelopmentCardsVenture.setPrefHeight(this.playerBoard2DevelopmentCardsVenture.getHeight() * this.ratio);
		this.playerBoard2DevelopmentCardsCharacter.setPrefWidth(this.playerBoard2DevelopmentCardsCharacter.getWidth() * this.ratio);
		this.playerBoard2DevelopmentCardsCharacter.setPrefHeight(this.playerBoard2DevelopmentCardsCharacter.getHeight() * this.ratio);
		this.playerBoard3.setPrefWidth(this.playerBoard3.getWidth() * this.ratio);
		this.playerBoard3.setPrefHeight(this.playerBoard3.getHeight() * this.ratio);
		this.playerBoard3DevelopmentCardsVenture.setPrefWidth(this.playerBoard3DevelopmentCardsVenture.getWidth() * this.ratio);
		this.playerBoard3DevelopmentCardsVenture.setPrefHeight(this.playerBoard3DevelopmentCardsVenture.getHeight() * this.ratio);
		this.playerBoard3DevelopmentCardsCharacter.setPrefWidth(this.playerBoard3DevelopmentCardsCharacter.getWidth() * this.ratio);
		this.playerBoard3DevelopmentCardsCharacter.setPrefHeight(this.playerBoard3DevelopmentCardsCharacter.getHeight() * this.ratio);
		this.playerBoard4.setPrefWidth(this.playerBoard4.getWidth() * this.ratio);
		this.playerBoard4.setPrefHeight(this.playerBoard4.getHeight() * this.ratio);
		this.playerBoard4DevelopmentCardsVenture.setPrefWidth(this.playerBoard4DevelopmentCardsVenture.getWidth() * this.ratio);
		this.playerBoard4DevelopmentCardsVenture.setPrefHeight(this.playerBoard4DevelopmentCardsVenture.getHeight() * this.ratio);
		this.playerBoard4DevelopmentCardsCharacter.setPrefWidth(this.playerBoard4DevelopmentCardsCharacter.getWidth() * this.ratio);
		this.playerBoard4DevelopmentCardsCharacter.setPrefHeight(this.playerBoard4DevelopmentCardsCharacter.getHeight() * this.ratio);
		this.playerBoard5.setPrefWidth(this.playerBoard5.getWidth() * this.ratio);
		this.playerBoard5.setPrefHeight(this.playerBoard5.getHeight() * this.ratio);
		this.playerBoard5DevelopmentCardsVenture.setPrefWidth(this.playerBoard5DevelopmentCardsVenture.getWidth() * this.ratio);
		this.playerBoard5DevelopmentCardsVenture.setPrefHeight(this.playerBoard5DevelopmentCardsVenture.getHeight() * this.ratio);
		this.playerBoard5DevelopmentCardsCharacter.setPrefWidth(this.playerBoard5DevelopmentCardsCharacter.getWidth() * this.ratio);
		this.playerBoard5DevelopmentCardsCharacter.setPrefHeight(this.playerBoard5DevelopmentCardsCharacter.getHeight() * this.ratio);
		this.rightVBox.setMaxWidth(this.rightVBox.getWidth() * this.ratio);
		this.chatTabPane.setMaxHeight(this.chatTabPane.getHeight() - (this.rightVBox.getHeight() - this.gameBoard.getHeight()));
		this.getStage().sizeToScene();
		Utils.resizeChildrenNode(this.gameBoard, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard1, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard1DevelopmentCardsVenture, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard1DevelopmentCardsCharacter, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard2, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard2DevelopmentCardsVenture, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard2DevelopmentCardsCharacter, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard3, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard3DevelopmentCardsVenture, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard3DevelopmentCardsCharacter, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard4, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard4DevelopmentCardsVenture, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard4DevelopmentCardsCharacter, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard5, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard5DevelopmentCardsVenture, this.ratio, this.ratio);
		Utils.resizeChildrenNode(this.playerBoard5DevelopmentCardsCharacter, this.ratio, this.ratio);
		this.cardDialogPane.setPrefWidth(this.building1.getWidth() * 4);
		this.cardDialogPane.setPrefHeight(this.building1.getHeight() * 4);
		this.cardDialogScrollPane.setPrefHeight(this.building1.getHeight() * 4);
		for (Integer playerIndex : GameStatus.getInstance().getCurrentPlayersData().keySet()) {
			for (Pane pane : this.playersLeaderCardsHand.get(playerIndex)) {
				pane.setPrefWidth(this.building1.getWidth() * 2.5);
				pane.setPrefHeight(this.building1.getHeight() * 2.5);
			}
			for (Pane pane : this.playersLeaderCardsPlayed.get(playerIndex)) {
				pane.setPrefWidth(this.building1.getWidth() * 2.5);
				pane.setPrefHeight(this.building1.getHeight() * 2.5);
			}
		}
		this.leaderCardsTabPane.requestLayout();
		this.leaderCardsButton.setPrefWidth(((VBox) this.leaderCardsButton.getParent()).getWidth());
		this.excommunicationChoiceDialogAcceptButton.setPrefWidth(((VBox) this.excommunicationChoiceDialogAcceptButton.getParent()).getWidth());
		this.excommunicationChoiceDialogRefuseButton.setPrefWidth(((VBox) this.excommunicationChoiceDialogRefuseButton.getParent()).getWidth());
		this.servantsChoiceDialogAcceptButton.setPrefWidth(((VBox) this.servantsChoiceDialogAcceptButton.getParent()).getWidth());
		this.servantsChoiceDialogCancelButton.setPrefWidth(((VBox) this.servantsChoiceDialogCancelButton.getParent()).getWidth());
		this.playersTabPane.getSelectionModel().select(this.playersTabs.get(GameStatus.getInstance().getOwnPlayerIndex()));
		this.getStage().setX(bounds.getWidth() / 2 - this.getStage().getWidth() / 2);
		this.getStage().setY(bounds.getHeight() / 2 - this.getStage().getHeight() / 2);
		Client.getInstance().setBackgroundMediaPlayer(new MediaPlayer(new Media(this.getClass().getResource("/sounds/background.mp3").toString())));
		Client.getInstance().getBackgroundMediaPlayer().setOnEndOfMedia(() -> Client.getInstance().getBackgroundMediaPlayer().seek(Duration.ZERO));
		Client.getInstance().getBackgroundMediaPlayer().play();
	}

	public void updatePlayerPersonalBonusTile(int choicePlayerIndex, int choicePersonalBonusTileIndex)
	{
		this.playersBoards.get(choicePlayerIndex).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getPersonalBonusTiles().get(choicePersonalBonusTileIndex).getPlayerBoardTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
	}

	public void setOwnTurn()
	{
		this.updateGame();
		this.generateAvailableActions();
		this.playersTabPane.getSelectionModel().select(this.playersTabs.get(GameStatus.getInstance().getOwnPlayerIndex()));
		this.gameLogTextArea.appendText((this.gameLogTextArea.getText().length() < 1 ? "" : '\n') + "Your turn");
	}

	public void setOtherTurn()
	{
		this.updateGame();
		this.playersTabPane.getSelectionModel().select(this.playersTabs.get(GameStatus.getInstance().getCurrentTurnPlayerIndex()));
		this.actionsVBox.getChildren().clear();
		JFXNodesList actionsNodesList = new JFXNodesList();
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action.png", true);
		this.actionsVBox.getChildren().add(actionsNodesList);
	}

	private void generateAvailableActions()
	{
		this.actionsVBox.getChildren().clear();
		JFXNodesList actionsNodesList = new JFXNodesList();
		JFXNodesList familyMemberActionNodesList = new JFXNodesList();
		JFXNodesList leaderCardsActionNodesList = new JFXNodesList();
		actionsNodesList.setSpacing(10.0D);
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action.png", false);
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.COUNCIL_PALACE).isEmpty() || !GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD).isEmpty() || !GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.HARVEST).isEmpty() || !GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET).isEmpty() || !GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PRODUCTION_START).isEmpty()) {
			ControllerGame.setActionButton(familyMemberActionNodesList, "/images/icons/action_family_member.png", false, () -> {
				if (WindowFactory.isNodesListExpanded(leaderCardsActionNodesList)) {
					leaderCardsActionNodesList.animateList();
				}
			});
			familyMemberActionNodesList.setSpacing(10.0D);
			JFXNodesList councilPalaceActionNodesList = new JFXNodesList();
			JFXNodesList harvestActionNodesList = new JFXNodesList();
			JFXNodesList marketActionNodesList = new JFXNodesList();
			JFXNodesList pickDevelopmentCardActionNodesList = new JFXNodesList();
			JFXNodesList productionStartActionNodesList = new JFXNodesList();
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.COUNCIL_PALACE).isEmpty()) {
				List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(councilPalaceActionNodesList, "/images/icons/action_council_palace.png", GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.COUNCIL_PALACE), new JFXNodesList[] { harvestActionNodesList, marketActionNodesList, pickDevelopmentCardActionNodesList, productionStartActionNodesList });
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
					ControllerGame.setActionButton(councilPalaceActionNodesList, "/images/icons/action_family_member_type_black.png", false, () -> this.showCouncilPalaceActionChoice(FamilyMemberType.BLACK));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
					ControllerGame.setActionButton(councilPalaceActionNodesList, "/images/icons/action_family_member_type_neutral.png", false, () -> this.showCouncilPalaceActionChoice(FamilyMemberType.NEUTRAL));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
					ControllerGame.setActionButton(councilPalaceActionNodesList, "/images/icons/action_family_member_type_orange.png", false, () -> this.showCouncilPalaceActionChoice(FamilyMemberType.ORANGE));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
					ControllerGame.setActionButton(councilPalaceActionNodesList, "/images/icons/action_family_member_type_white.png", false, () -> this.showCouncilPalaceActionChoice(FamilyMemberType.WHITE));
				}
				councilPalaceActionNodesList.setSpacing(10.0D);
				councilPalaceActionNodesList.setRotate(180.0D);
				familyMemberActionNodesList.addAnimatedNode(councilPalaceActionNodesList);
			}
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.HARVEST).isEmpty()) {
				List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(harvestActionNodesList, "/images/icons/action_harvest.png", GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.HARVEST), new JFXNodesList[] { councilPalaceActionNodesList, marketActionNodesList, pickDevelopmentCardActionNodesList, productionStartActionNodesList });
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
					ControllerGame.setActionButton(harvestActionNodesList, "/images/icons/action_family_member_type_black.png", false, () -> this.showHarvestActionChoice(FamilyMemberType.BLACK));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
					ControllerGame.setActionButton(harvestActionNodesList, "/images/icons/action_family_member_type_neutral.png", false, () -> this.showHarvestActionChoice(FamilyMemberType.NEUTRAL));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
					ControllerGame.setActionButton(harvestActionNodesList, "/images/icons/action_family_member_type_orange.png", false, () -> this.showHarvestActionChoice(FamilyMemberType.ORANGE));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
					ControllerGame.setActionButton(harvestActionNodesList, "/images/icons/action_family_member_type_white.png", false, () -> this.showHarvestActionChoice(FamilyMemberType.WHITE));
				}
				harvestActionNodesList.setSpacing(10.0D);
				harvestActionNodesList.setRotate(180.0D);
				familyMemberActionNodesList.addAnimatedNode(harvestActionNodesList);
			}
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET).isEmpty()) {
				Map<MarketSlot, List<AvailableAction>> mappedMarketSlots = new EnumMap<>(MarketSlot.class);
				mappedMarketSlots.put(MarketSlot.FIRST, new ArrayList<>());
				mappedMarketSlots.put(MarketSlot.SECOND, new ArrayList<>());
				mappedMarketSlots.put(MarketSlot.THIRD, new ArrayList<>());
				mappedMarketSlots.put(MarketSlot.FOURTH, new ArrayList<>());
				mappedMarketSlots.put(MarketSlot.FIFTH, new ArrayList<>());
				mappedMarketSlots.put(MarketSlot.SIXTH, new ArrayList<>());
				for (AvailableAction availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET)) {
					mappedMarketSlots.get(((AvailableActionMarket) availableAction).getMarketSlot()).add(availableAction);
				}
				ControllerGame.setActionButton(marketActionNodesList, "/images/icons/action_market.png", false, () -> {
					if (WindowFactory.isNodesListExpanded(councilPalaceActionNodesList)) {
						councilPalaceActionNodesList.animateList();
					} else if (WindowFactory.isNodesListExpanded(harvestActionNodesList)) {
						harvestActionNodesList.animateList();
					} else if (WindowFactory.isNodesListExpanded(pickDevelopmentCardActionNodesList)) {
						pickDevelopmentCardActionNodesList.animateList();
					} else if (WindowFactory.isNodesListExpanded(productionStartActionNodesList)) {
						productionStartActionNodesList.animateList();
					}
				});
				JFXNodesList market1NodesList = new JFXNodesList();
				JFXNodesList market2NodesList = new JFXNodesList();
				JFXNodesList market3NodesList = new JFXNodesList();
				JFXNodesList market4NodesList = new JFXNodesList();
				JFXNodesList market5NodesList = new JFXNodesList();
				JFXNodesList market6NodesList = new JFXNodesList();
				if (!mappedMarketSlots.get(MarketSlot.FIRST).isEmpty()) {
					List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(market1NodesList, "/images/icons/action_market_1.png", mappedMarketSlots.get(MarketSlot.FIRST), new JFXNodesList[] { market2NodesList, market3NodesList, market4NodesList, market5NodesList, market6NodesList });
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
						ControllerGame.setActionButton(market1NodesList, "/images/icons/action_family_member_type_black.png", false, () -> this.showMarketActionChoice(MarketSlot.FIRST, FamilyMemberType.BLACK));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
						ControllerGame.setActionButton(market1NodesList, "/images/icons/action_family_member_type_neutral.png", false, () -> this.showMarketActionChoice(MarketSlot.FIRST, FamilyMemberType.NEUTRAL));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
						ControllerGame.setActionButton(market1NodesList, "/images/icons/action_family_member_type_orange.png", false, () -> this.showMarketActionChoice(MarketSlot.FIRST, FamilyMemberType.ORANGE));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
						ControllerGame.setActionButton(market1NodesList, "/images/icons/action_family_member_type_white.png", false, () -> this.showMarketActionChoice(MarketSlot.FIRST, FamilyMemberType.WHITE));
					}
					market1NodesList.setSpacing(10.0D);
					market1NodesList.setRotate(270.0D);
					marketActionNodesList.addAnimatedNode(market1NodesList);
				}
				if (!mappedMarketSlots.get(MarketSlot.SECOND).isEmpty()) {
					List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(market2NodesList, "/images/icons/action_market_2.png", mappedMarketSlots.get(MarketSlot.SECOND), new JFXNodesList[] { market1NodesList, market3NodesList, market4NodesList, market5NodesList, market6NodesList });
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
						ControllerGame.setActionButton(market2NodesList, "/images/icons/action_family_member_type_black.png", false, () -> this.showMarketActionChoice(MarketSlot.SECOND, FamilyMemberType.BLACK));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
						ControllerGame.setActionButton(market2NodesList, "/images/icons/action_family_member_type_neutral.png", false, () -> this.showMarketActionChoice(MarketSlot.SECOND, FamilyMemberType.NEUTRAL));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
						ControllerGame.setActionButton(market2NodesList, "/images/icons/action_family_member_type_orange.png", false, () -> this.showMarketActionChoice(MarketSlot.SECOND, FamilyMemberType.ORANGE));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
						ControllerGame.setActionButton(market2NodesList, "/images/icons/action_family_member_type_white.png", false, () -> this.showMarketActionChoice(MarketSlot.SECOND, FamilyMemberType.WHITE));
					}
					market2NodesList.setSpacing(10.0D);
					market2NodesList.setRotate(270.0D);
					marketActionNodesList.addAnimatedNode(market2NodesList);
				}
				if (!mappedMarketSlots.get(MarketSlot.THIRD).isEmpty()) {
					List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(market3NodesList, "/images/icons/action_market_3.png", mappedMarketSlots.get(MarketSlot.THIRD), new JFXNodesList[] { market1NodesList, market2NodesList, market4NodesList, market5NodesList, market6NodesList });
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
						ControllerGame.setActionButton(market3NodesList, "/images/icons/action_family_member_type_black.png", false, () -> this.showMarketActionChoice(MarketSlot.THIRD, FamilyMemberType.BLACK));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
						ControllerGame.setActionButton(market3NodesList, "/images/icons/action_family_member_type_neutral.png", false, () -> this.showMarketActionChoice(MarketSlot.THIRD, FamilyMemberType.NEUTRAL));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
						ControllerGame.setActionButton(market3NodesList, "/images/icons/action_family_member_type_orange.png", false, () -> this.showMarketActionChoice(MarketSlot.THIRD, FamilyMemberType.ORANGE));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
						ControllerGame.setActionButton(market3NodesList, "/images/icons/action_family_member_type_white.png", false, () -> this.showMarketActionChoice(MarketSlot.THIRD, FamilyMemberType.WHITE));
					}
					market3NodesList.setSpacing(10.0D);
					market3NodesList.setRotate(270.0D);
					marketActionNodesList.addAnimatedNode(market3NodesList);
				}
				if (!mappedMarketSlots.get(MarketSlot.FOURTH).isEmpty()) {
					List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(market4NodesList, "/images/icons/action_market_4.png", mappedMarketSlots.get(MarketSlot.FOURTH), new JFXNodesList[] { market1NodesList, market2NodesList, market3NodesList, market5NodesList, market6NodesList });
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
						ControllerGame.setActionButton(market4NodesList, "/images/icons/action_family_member_type_black.png", false, () -> this.showMarketActionChoice(MarketSlot.FOURTH, FamilyMemberType.BLACK));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
						ControllerGame.setActionButton(market4NodesList, "/images/icons/action_family_member_type_neutral.png", false, () -> this.showMarketActionChoice(MarketSlot.FOURTH, FamilyMemberType.NEUTRAL));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
						ControllerGame.setActionButton(market4NodesList, "/images/icons/action_family_member_type_orange.png", false, () -> this.showMarketActionChoice(MarketSlot.FOURTH, FamilyMemberType.ORANGE));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
						ControllerGame.setActionButton(market4NodesList, "/images/icons/action_family_member_type_white.png", false, () -> this.showMarketActionChoice(MarketSlot.FOURTH, FamilyMemberType.WHITE));
					}
					market4NodesList.setSpacing(10.0D);
					market4NodesList.setRotate(270.0D);
					marketActionNodesList.addAnimatedNode(market4NodesList);
				}
				if (!mappedMarketSlots.get(MarketSlot.FIFTH).isEmpty()) {
					List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(market5NodesList, "/images/icons/action_market_5.png", mappedMarketSlots.get(MarketSlot.FIFTH), new JFXNodesList[] { market1NodesList, market2NodesList, market3NodesList, market4NodesList, market6NodesList });
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
						ControllerGame.setActionButton(market5NodesList, "/images/icons/action_family_member_type_black.png", false, () -> this.showMarketActionChoice(MarketSlot.FIFTH, FamilyMemberType.BLACK));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
						ControllerGame.setActionButton(market5NodesList, "/images/icons/action_family_member_type_neutral.png", false, () -> this.showMarketActionChoice(MarketSlot.FIFTH, FamilyMemberType.NEUTRAL));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
						ControllerGame.setActionButton(market5NodesList, "/images/icons/action_family_member_type_orange.png", false, () -> this.showMarketActionChoice(MarketSlot.FIFTH, FamilyMemberType.ORANGE));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
						ControllerGame.setActionButton(market5NodesList, "/images/icons/action_family_member_type_white.png", false, () -> this.showMarketActionChoice(MarketSlot.FIFTH, FamilyMemberType.WHITE));
					}
					market5NodesList.setSpacing(10.0D);
					market5NodesList.setRotate(270.0D);
					marketActionNodesList.addAnimatedNode(market5NodesList);
				}
				if (!mappedMarketSlots.get(MarketSlot.SIXTH).isEmpty()) {
					List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(market6NodesList, "/images/icons/action_market_6.png", mappedMarketSlots.get(MarketSlot.SIXTH), new JFXNodesList[] { market1NodesList, market2NodesList, market3NodesList, market4NodesList, market5NodesList });
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
						ControllerGame.setActionButton(market6NodesList, "/images/icons/action_family_member_type_black.png", false, () -> this.showMarketActionChoice(MarketSlot.SIXTH, FamilyMemberType.BLACK));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
						ControllerGame.setActionButton(market6NodesList, "/images/icons/action_family_member_type_neutral.png", false, () -> this.showMarketActionChoice(MarketSlot.SIXTH, FamilyMemberType.NEUTRAL));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
						ControllerGame.setActionButton(market6NodesList, "/images/icons/action_family_member_type_orange.png", false, () -> this.showMarketActionChoice(MarketSlot.SIXTH, FamilyMemberType.ORANGE));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
						ControllerGame.setActionButton(market6NodesList, "/images/icons/action_family_member_type_white.png", false, () -> this.showMarketActionChoice(MarketSlot.SIXTH, FamilyMemberType.WHITE));
					}
					market6NodesList.setSpacing(10.0D);
					market6NodesList.setRotate(270.0D);
					marketActionNodesList.addAnimatedNode(market6NodesList);
				}
				marketActionNodesList.setSpacing(10.0D);
				marketActionNodesList.setRotate(180.0D);
				familyMemberActionNodesList.addAnimatedNode(marketActionNodesList);
			}
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD).isEmpty()) {
				List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(pickDevelopmentCardActionNodesList, "/images/icons/action_pick_development_card.png", GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD), new JFXNodesList[] { councilPalaceActionNodesList, harvestActionNodesList, marketActionNodesList, productionStartActionNodesList });
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
					ControllerGame.setActionButton(pickDevelopmentCardActionNodesList, "/images/icons/action_family_member_type_black.png", false, () -> this.showPickDevelopmentCardActionChoice(FamilyMemberType.BLACK));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
					ControllerGame.setActionButton(pickDevelopmentCardActionNodesList, "/images/icons/action_family_member_type_neutral.png", false, () -> this.showPickDevelopmentCardActionChoice(FamilyMemberType.NEUTRAL));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
					ControllerGame.setActionButton(pickDevelopmentCardActionNodesList, "/images/icons/action_family_member_type_orange.png", false, () -> this.showPickDevelopmentCardActionChoice(FamilyMemberType.ORANGE));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
					ControllerGame.setActionButton(pickDevelopmentCardActionNodesList, "/images/icons/action_family_member_type_white.png", false, () -> this.showPickDevelopmentCardActionChoice(FamilyMemberType.WHITE));
				}
				pickDevelopmentCardActionNodesList.setSpacing(10.0D);
				pickDevelopmentCardActionNodesList.setRotate(180.0D);
				familyMemberActionNodesList.addAnimatedNode(pickDevelopmentCardActionNodesList);
			}
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PRODUCTION_START).isEmpty()) {
				List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(productionStartActionNodesList, "/images/icons/action_production_start.png", GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PRODUCTION_START), new JFXNodesList[] { councilPalaceActionNodesList, harvestActionNodesList, marketActionNodesList, pickDevelopmentCardActionNodesList });
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
					ControllerGame.setActionButton(productionStartActionNodesList, "/images/icons/action_family_member_type_black.png", false, () -> this.showProductionStartChoice(FamilyMemberType.BLACK));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
					ControllerGame.setActionButton(productionStartActionNodesList, "/images/icons/action_family_member_type_neutral.png", false, () -> this.showProductionStartChoice(FamilyMemberType.NEUTRAL));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
					ControllerGame.setActionButton(productionStartActionNodesList, "/images/icons/action_family_member_type_orange.png", false, () -> this.showProductionStartChoice(FamilyMemberType.ORANGE));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
					ControllerGame.setActionButton(productionStartActionNodesList, "/images/icons/action_family_member_type_white.png", false, () -> this.showProductionStartChoice(FamilyMemberType.WHITE));
				}
				productionStartActionNodesList.setSpacing(10.0D);
				productionStartActionNodesList.setRotate(180.0D);
				familyMemberActionNodesList.addAnimatedNode(productionStartActionNodesList);
			}
			familyMemberActionNodesList.setRotate(270.0D);
			actionsNodesList.addAnimatedNode(familyMemberActionNodesList);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_ACTIVATE).isEmpty() || !GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_DISCARD).isEmpty() || !GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_PLAY).isEmpty()) {
			leaderCardsActionNodesList.setSpacing(10.0D);
			ControllerGame.setActionButton(leaderCardsActionNodesList, "/images/icons/action_leader.png", false, () -> {
				if (WindowFactory.isNodesListExpanded(familyMemberActionNodesList)) {
					familyMemberActionNodesList.animateList();
				}
			});
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_ACTIVATE).isEmpty()) {
				ControllerGame.setActionButton(leaderCardsActionNodesList, "/images/icons/action_leader_activate.png", false, () -> this.showLeaderActivateChoice());
			}
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_DISCARD).isEmpty()) {
				ControllerGame.setActionButton(leaderCardsActionNodesList, "/images/icons/action_leader_discard.png", false, () -> this.showLeaderDiscardChoice());
			}
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_PLAY).isEmpty()) {
				ControllerGame.setActionButton(leaderCardsActionNodesList, "/images/icons/action_leader_play.png", false, () -> this.showLeaderPlayChoice());
			}
			leaderCardsActionNodesList.setRotate(90.0D);
			actionsNodesList.addAnimatedNode(leaderCardsActionNodesList);
		}
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action_pass_turn.png", false, true, () -> Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsPassTurn()));
		actionsNodesList.setRotate(180.0D);
		this.actionsVBox.getChildren().add(actionsNodesList);
	}

	private static void setActionButton(JFXNodesList nodesList, String imagePath, boolean disabled)
	{
		ControllerGame.setActionButton(nodesList, imagePath, disabled, false, null);
	}

	private static void setActionButton(JFXNodesList nodesList, String imagePath, boolean disabled, Runnable runnable)
	{
		ControllerGame.setActionButton(nodesList, imagePath, disabled, false, runnable);
	}

	private static void setActionButton(JFXNodesList nodesList, String imagePath, boolean disabled, boolean red, Runnable runnable)
	{
		JFXButton button = new JFXButton();
		if (disabled) {
			button.setDisable(true);
		}
		ImageView imageView = new ImageView(new Image(Client.class.getResource(imagePath).toString()));
		button.setGraphic(imageView);
		if (red) {
			button.getStyleClass().add("animated-option-button-red");
		} else {
			button.getStyleClass().add("animated-option-button-green");
		}
		if (runnable != null) {
			button.setOnMouseClicked((event) -> runnable.run());
		}
		nodesList.addAnimatedNode(button);
	}

	private static List<FamilyMemberType> mapFamilyMemberTypes(JFXNodesList nodesList, String imagePath, List<AvailableAction> availableActions, JFXNodesList[] nodesLists)
	{
		ControllerGame.setActionButton(nodesList, imagePath, false, () -> {
			for (JFXNodesList otherNodesList : nodesLists) {
				if (WindowFactory.isNodesListExpanded(otherNodesList)) {
					otherNodesList.animateList();
				}
			}
		});
		List<FamilyMemberType> mappedFamilyMemberTypes = new ArrayList<>();
		for (AvailableAction availableAction : availableActions) {
			if (!mappedFamilyMemberTypes.contains(((AvailableActionFamilyMember) availableAction).getFamilyMemberType())) {
				mappedFamilyMemberTypes.add(((AvailableActionFamilyMember) availableAction).getFamilyMemberType());
			}
		}
		return mappedFamilyMemberTypes;
	}

	private String getDevelopmentCardInformations(Pane pane)
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (this.developmentCardsBuildingPanes.containsValue(pane)) {
			DevelopmentCardBuildingInformations developmentCardBuildingInformations = GameStatus.getInstance().getDevelopmentCardsBuilding().get(this.developmentCardsBuildingIndexes.get(pane));
			boolean firstLine = true;
			if (!developmentCardBuildingInformations.getResourceCostOptions().isEmpty()) {
				firstLine = false;
				stringBuilder.append("RESOURCE COST OPTIONS:\n==============");
				for (ResourceCostOption resourceCostOption : developmentCardBuildingInformations.getResourceCostOptions()) {
					if (!resourceCostOption.getRequiredResources().isEmpty()) {
						stringBuilder.append("\nRequired resources:");
						for (ResourceAmount resourceAmount : resourceCostOption.getRequiredResources()) {
							stringBuilder.append("\n    - ");
							stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
							stringBuilder.append(": ");
							stringBuilder.append(resourceAmount.getAmount());
						}
					}
					if (!resourceCostOption.getSpentResources().isEmpty()) {
						stringBuilder.append("\nSpent resources:");
						for (ResourceAmount resourceAmount : resourceCostOption.getSpentResources()) {
							stringBuilder.append("\n    - ");
							stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
							stringBuilder.append(": ");
							stringBuilder.append(resourceAmount.getAmount());
						}
					}
					stringBuilder.append("\n==============");
				}
			}
			if (developmentCardBuildingInformations.getReward().getActionRewardInformations() != null || !developmentCardBuildingInformations.getReward().getResourceAmounts().isEmpty()) {
				if (!firstLine) {
					stringBuilder.append("\n\n");
				}
				stringBuilder.append("REWARD:");
			}
			if (!developmentCardBuildingInformations.getReward().getResourceAmounts().isEmpty()) {
				stringBuilder.append("\nInstant resources:");
				for (ResourceAmount resourceAmount : (developmentCardBuildingInformations.getReward().getResourceAmounts())) {
					stringBuilder.append("\n    - ");
					stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
			}
			if (developmentCardBuildingInformations.getReward().getActionRewardInformations() != null) {
				stringBuilder.append("\nAction reward:\n| ");
				stringBuilder.append(developmentCardBuildingInformations.getReward().getActionRewardInformations().replace("\n", "\n| "));
			}
			stringBuilder.append("\n\nPRODUCTION ACTIVATION COST: ");
			stringBuilder.append(developmentCardBuildingInformations.getActivationValue());
			if (!developmentCardBuildingInformations.getResourceTradeOptions().isEmpty()) {
				stringBuilder.append("\n\nRESOURCE TRADE OPTIONS:");
				for (ResourceTradeOption resourcetradeOption : developmentCardBuildingInformations.getResourceTradeOptions()) {
					stringBuilder.append("\n==============");
					if (!resourcetradeOption.getEmployedResources().isEmpty()) {
						stringBuilder.append("\nEmployed resources:");
						for (ResourceAmount resourceAmount : resourcetradeOption.getEmployedResources()) {
							stringBuilder.append("\n    - ");
							stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
							stringBuilder.append(": ");
							stringBuilder.append(resourceAmount.getAmount());
						}
					}
					if (!resourcetradeOption.getProducedResources().isEmpty()) {
						stringBuilder.append("\nProduced resources:");
						for (ResourceAmount resourceAmount : resourcetradeOption.getProducedResources()) {
							stringBuilder.append("\n    - ");
							stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
							stringBuilder.append(": ");
							stringBuilder.append(resourceAmount.getAmount());
						}
					}
					stringBuilder.append("\n==============");
				}
			}
		} else if (this.developmentCardsCharacterPanes.containsValue(pane)) {
			DevelopmentCardCharacterInformations developmentCardCharacterInformations = GameStatus.getInstance().getDevelopmentCardsCharacter().get(this.developmentCardsCharacterIndexes.get(pane));
			boolean firstLine = true;
			if (!developmentCardCharacterInformations.getResourceCostOptions().isEmpty()) {
				firstLine = false;
				stringBuilder.append("RESOURCE COST OPTIONS:\n==============");
				for (ResourceCostOption resourceCostOption : developmentCardCharacterInformations.getResourceCostOptions()) {
					if (!resourceCostOption.getRequiredResources().isEmpty()) {
						stringBuilder.append("\nRequired resources:");
						for (ResourceAmount resourceAmount : resourceCostOption.getRequiredResources()) {
							stringBuilder.append("\n    - ");
							stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
							stringBuilder.append(": ");
							stringBuilder.append(resourceAmount.getAmount());
						}
					}
					if (!resourceCostOption.getSpentResources().isEmpty()) {
						stringBuilder.append("\nSpent resources:");
						for (ResourceAmount resourceAmount : resourceCostOption.getSpentResources()) {
							stringBuilder.append("\n    - ");
							stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
							stringBuilder.append(": ");
							stringBuilder.append(resourceAmount.getAmount());
						}
					}
					stringBuilder.append("\n==============");
				}
			}
			if (developmentCardCharacterInformations.getReward().getActionRewardInformations() != null || !developmentCardCharacterInformations.getReward().getResourceAmounts().isEmpty()) {
				if (!firstLine) {
					stringBuilder.append("\n\n");
				}
				stringBuilder.append("REWARD:");
			}
			if (!developmentCardCharacterInformations.getReward().getResourceAmounts().isEmpty()) {
				stringBuilder.append("\nInstant resources:");
				for (ResourceAmount resourceAmount : (developmentCardCharacterInformations.getReward().getResourceAmounts())) {
					stringBuilder.append("\n    - ");
					stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
			}
			if (developmentCardCharacterInformations.getReward().getActionRewardInformations() != null) {
				stringBuilder.append("\nAction reward:\n| ");
				stringBuilder.append(developmentCardCharacterInformations.getReward().getActionRewardInformations().replace("\n", "\n| "));
			}
			if (developmentCardCharacterInformations.getModifierInformations() != null) {
				stringBuilder.append("\n\nMODIFIER:\n| ");
				stringBuilder.append(developmentCardCharacterInformations.getModifierInformations().replace("\n", "\n| "));
			}
		} else if (this.developmentCardsTerritoryPanes.containsValue(pane)) {
			DevelopmentCardTerritoryInformations developmentCardTerritoryInformations = GameStatus.getInstance().getDevelopmentCardsTerritory().get(this.developmentCardsTerritoryIndexes.get(pane));
			boolean firstLine = true;
			if (!developmentCardTerritoryInformations.getResourceCostOptions().isEmpty()) {
				firstLine = false;
				stringBuilder.append("RESOURCE COST OPTIONS:\n==============");
				for (ResourceCostOption resourceCostOption : developmentCardTerritoryInformations.getResourceCostOptions()) {
					if (!resourceCostOption.getRequiredResources().isEmpty()) {
						stringBuilder.append("\nRequired resources:");
						for (ResourceAmount resourceAmount : resourceCostOption.getRequiredResources()) {
							stringBuilder.append("\n    - ");
							stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
							stringBuilder.append(": ");
							stringBuilder.append(resourceAmount.getAmount());
						}
					}
					if (!resourceCostOption.getSpentResources().isEmpty()) {
						stringBuilder.append("\nSpent resources:");
						for (ResourceAmount resourceAmount : resourceCostOption.getSpentResources()) {
							stringBuilder.append("\n    - ");
							stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
							stringBuilder.append(": ");
							stringBuilder.append(resourceAmount.getAmount());
						}
					}
					stringBuilder.append("\n==============");
				}
			}
			if (developmentCardTerritoryInformations.getReward().getActionRewardInformations() != null || !developmentCardTerritoryInformations.getReward().getResourceAmounts().isEmpty()) {
				if (!firstLine) {
					stringBuilder.append("\n\n");
				}
				stringBuilder.append("REWARD:");
			}
			if (!developmentCardTerritoryInformations.getReward().getResourceAmounts().isEmpty()) {
				stringBuilder.append("\nInstant resources:");
				for (ResourceAmount resourceAmount : (developmentCardTerritoryInformations.getReward().getResourceAmounts())) {
					stringBuilder.append("\n    - ");
					stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
			}
			if (developmentCardTerritoryInformations.getReward().getActionRewardInformations() != null) {
				stringBuilder.append("\nAction reward:\n| ");
				stringBuilder.append(developmentCardTerritoryInformations.getReward().getActionRewardInformations().replace("\n", "\n| "));
			}
			stringBuilder.append("\n\nHARVEST ACTIVATION VALUE: ");
			stringBuilder.append(developmentCardTerritoryInformations.getActivationValue());
			if (!developmentCardTerritoryInformations.getHarvestResources().isEmpty()) {
				stringBuilder.append("\n\nHARVEST RESOURCES:");
				for (ResourceAmount resourceAmount : developmentCardTerritoryInformations.getHarvestResources()) {
					stringBuilder.append("\n    - ");
					stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
			}
		} else if (this.developmentCardsVenturePanes.containsValue(pane)) {
			DevelopmentCardVentureInformations developmentCardVentureInformations = GameStatus.getInstance().getDevelopmentCardsVenture().get(this.developmentCardsVentureIndexes.get(pane));
			boolean firstLine = true;
			if (!developmentCardVentureInformations.getResourceCostOptions().isEmpty()) {
				firstLine = false;
				stringBuilder.append("RESOURCE COST OPTIONS:\n==============");
				for (ResourceCostOption resourceCostOption : developmentCardVentureInformations.getResourceCostOptions()) {
					if (!resourceCostOption.getRequiredResources().isEmpty()) {
						stringBuilder.append("\nRequired resources:");
						for (ResourceAmount resourceAmount : resourceCostOption.getRequiredResources()) {
							stringBuilder.append("\n    - ");
							stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
							stringBuilder.append(": ");
							stringBuilder.append(resourceAmount.getAmount());
						}
					}
					if (!resourceCostOption.getSpentResources().isEmpty()) {
						stringBuilder.append("\nSpent resources:");
						for (ResourceAmount resourceAmount : resourceCostOption.getSpentResources()) {
							stringBuilder.append("\n    - ");
							stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
							stringBuilder.append(": ");
							stringBuilder.append(resourceAmount.getAmount());
						}
					}
					stringBuilder.append("\n==============");
				}
			}
			if (developmentCardVentureInformations.getReward().getActionRewardInformations() != null || !developmentCardVentureInformations.getReward().getResourceAmounts().isEmpty()) {
				if (!firstLine) {
					stringBuilder.append("\n\n");
				}
				stringBuilder.append("REWARD:");
			}
			if (!developmentCardVentureInformations.getReward().getResourceAmounts().isEmpty()) {
				stringBuilder.append("\nInstant resources:");
				for (ResourceAmount resourceAmount : (developmentCardVentureInformations.getReward().getResourceAmounts())) {
					stringBuilder.append("\n    - ");
					stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
			}
			if (developmentCardVentureInformations.getReward().getActionRewardInformations() != null) {
				stringBuilder.append("\nAction reward:\n| ");
				stringBuilder.append(developmentCardVentureInformations.getReward().getActionRewardInformations().replace("\n", "\n| "));
			}
			stringBuilder.append("\n\nVICTORY VALUE: ");
			stringBuilder.append(developmentCardVentureInformations.getVictoryValue());
		}
		return stringBuilder.toString();
	}

	private void updateGame()
	{
		this.leaderCardsChoiceDialog.close();
		this.cardDialog.close();
		this.leaderCardsDialog.close();
		this.servantsChoiceDialog.close();
		this.pickDevelopmentCardChoiceDialog.close();
		this.pickingDevelopmentCard = false;
		this.selectedDevelopmentCardType = null;
		this.selectedDevelopmentCardIndex = null;
		for (CardType cardType : CardType.values()) {
			for (Pane otherPane : this.developmentCardsPanes.get(cardType).values()) {
				otherPane.setEffect(null);
				Utils.setEffect(otherPane, ControllerGame.MOUSE_OVER_EFFECT);
			}
		}
		for (Row row : Row.values()) {
			this.developmentCardsPanes.get(CardType.BUILDING).get(row).setBackground(null);
			this.developmentCardsPanes.get(CardType.BUILDING).get(row).setBorder(null);
			Integer currentDevelopmentCardBuildingIndex = GameStatus.getInstance().getCurrentDevelopmentCardsBuilding().get(row);
			if (currentDevelopmentCardBuildingIndex != null) {
				this.developmentCardsPanes.get(CardType.BUILDING).get(row).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsBuilding().get(currentDevelopmentCardBuildingIndex).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				this.developmentCardsPanes.get(CardType.BUILDING).get(row).setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
				this.developmentCardsBuildingIndexes.put(this.developmentCardsPanes.get(CardType.BUILDING).get(row), currentDevelopmentCardBuildingIndex);
			}
			this.developmentCardsPanes.get(CardType.CHARACTER).get(row).setBackground(null);
			this.developmentCardsPanes.get(CardType.CHARACTER).get(row).setBorder(null);
			Integer currentDevelopmentCardCharacterIndex = GameStatus.getInstance().getCurrentDevelopmentCardsCharacter().get(row);
			if (currentDevelopmentCardCharacterIndex != null) {
				this.developmentCardsPanes.get(CardType.CHARACTER).get(row).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsCharacter().get(currentDevelopmentCardCharacterIndex).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				this.developmentCardsPanes.get(CardType.CHARACTER).get(row).setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
				this.developmentCardsCharacterIndexes.put(this.developmentCardsPanes.get(CardType.CHARACTER).get(row), currentDevelopmentCardCharacterIndex);
			}
			this.developmentCardsPanes.get(CardType.TERRITORY).get(row).setBackground(null);
			this.developmentCardsPanes.get(CardType.TERRITORY).get(row).setBorder(null);
			Integer currentDevelopmentCardTerritoryIndex = GameStatus.getInstance().getCurrentDevelopmentCardsTerritory().get(row);
			if (currentDevelopmentCardTerritoryIndex != null) {
				this.developmentCardsPanes.get(CardType.TERRITORY).get(row).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsTerritory().get(currentDevelopmentCardTerritoryIndex).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				this.developmentCardsPanes.get(CardType.TERRITORY).get(row).setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
				this.developmentCardsTerritoryIndexes.put(this.developmentCardsPanes.get(CardType.TERRITORY).get(row), currentDevelopmentCardTerritoryIndex);
			}
			this.developmentCardsPanes.get(CardType.VENTURE).get(row).setBackground(null);
			this.developmentCardsPanes.get(CardType.VENTURE).get(row).setBorder(null);
			Integer currentDevelopmentCardVentureIndex = GameStatus.getInstance().getCurrentDevelopmentCardsVenture().get(row);
			if (currentDevelopmentCardVentureIndex != null) {
				this.developmentCardsPanes.get(CardType.VENTURE).get(row).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsVenture().get(currentDevelopmentCardVentureIndex).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				this.developmentCardsPanes.get(CardType.VENTURE).get(row).setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
				this.developmentCardsVentureIndexes.put(this.developmentCardsPanes.get(CardType.VENTURE).get(row), currentDevelopmentCardVentureIndex);
			}
		}
		for (Pane pane : this.dices.values()) {
			pane.setBackground(null);
		}
		for (Entry<FamilyMemberType, Integer> dice : GameStatus.getInstance().getCurrentDices().entrySet()) {
			this.dices.get(dice.getKey()).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.DICES_FAMILY_MEMBER_TYPES.get(dice.getValue()).get(dice.getKey())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
		}
		for (Pane pane : this.victoryPointsPanes.values()) {
			pane.setBackground(null);
		}
		for (Pane pane : this.militaryPointsPanes.values()) {
			pane.setBackground(null);
		}
		for (Pane pane : this.faithPointsPanes.values()) {
			pane.setBackground(null);
		}
		if (GameStatus.getInstance().getCurrentPlayersData().size() == 5) {
			for (Pane pane : this.prestigePointsPanes.values()) {
				pane.setBackground(null);
			}
		}
		for (Pane pane : this.boardPositionsPanes.values()) {
			pane.setBackground(null);
		}
		for (Pane pane : this.harvestBigPositionsPanes.values()) {
			pane.setBackground(null);
		}
		for (Label label : this.harvestBigPositionsLabels.values()) {
			label.setText(null);
		}
		for (Pane pane : this.productionBigPositionsPanes.values()) {
			pane.setBackground(null);
		}
		for (Label label : this.productionBigPositionsLabels.values()) {
			label.setText(null);
		}
		for (Pane pane : this.councilPalacePositionsPanes.values()) {
			pane.setBackground(null);
		}
		for (Pane pane : this.roundOrderPositionsPanes.values()) {
			pane.setBackground(null);
		}
		for (Entry<Integer, PlayerData> playerData : GameStatus.getInstance().getCurrentPlayersData().entrySet()) {
			for (Pane pane : this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.BUILDING)) {
				pane.setBackground(null);
				pane.setBorder(null);
			}
			for (int index = 0; index < playerData.getValue().getDevelopmentCardsBuilding().size(); index++) {
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.BUILDING).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsBuilding().get(playerData.getValue().getDevelopmentCardsBuilding().get(index)).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.BUILDING).get(index).setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			}
			for (Pane pane : this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.CHARACTER)) {
				pane.setBackground(null);
				pane.setBorder(null);
			}
			for (int index = 0; index < playerData.getValue().getDevelopmentCardsCharacter().size(); index++) {
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.CHARACTER).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsCharacter().get(playerData.getValue().getDevelopmentCardsCharacter().get(index)).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.CHARACTER).get(index).setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			}
			for (Pane pane : this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.TERRITORY)) {
				pane.setBackground(null);
				pane.setBorder(null);
			}
			for (int index = 0; index < playerData.getValue().getDevelopmentCardsTerritory().size(); index++) {
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.TERRITORY).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsTerritory().get(playerData.getValue().getDevelopmentCardsTerritory().get(index)).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.TERRITORY).get(index).setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			}
			for (Pane pane : this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.VENTURE)) {
				pane.setBackground(null);
				pane.setBorder(null);
			}
			for (int index = 0; index < playerData.getValue().getDevelopmentCardsVenture().size(); index++) {
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.VENTURE).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsVenture().get(playerData.getValue().getDevelopmentCardsVenture().get(index)).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.VENTURE).get(index).setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			}
			for (Pane pane : this.playersLeaderCardsHand.get(playerData.getKey())) {
				pane.setBackground(null);
			}
			for (Pane pane : this.playersLeaderCardsPlayed.get(playerData.getKey())) {
				pane.setBackground(null);
			}
			int index = 0;
			for (Entry<Integer, Boolean> leaderCard : playerData.getValue().getLeaderCardsPlayed().entrySet()) {
				if (leaderCard.getValue()) {
					this.playersLeaderCardsPlayed.get(playerData.getKey()).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				} else {
					this.playersLeaderCardsPlayed.get(playerData.getKey()).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				}
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getDisplayName());
				stringBuilder.append("\n\n");
				stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getDescription());
				stringBuilder.append("\n\n");
				if (GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()) instanceof LeaderCardModifierInformations) {
					stringBuilder.append("PERMANENT ABILITY:\n");
					stringBuilder.append(((LeaderCardModifierInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey())).getModifier());
				} else {
					stringBuilder.append("ONCE PER ROUND ABILITY:");
					if (!((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey())).getReward().getResourceAmounts().isEmpty()) {
						stringBuilder.append("\n\nInstant resources:");
					}
					for (ResourceAmount resourceAmount : ((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey())).getReward().getResourceAmounts()) {
						stringBuilder.append('\n');
						stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
						stringBuilder.append(": ");
						stringBuilder.append(resourceAmount.getAmount());
					}
					if (((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey())).getReward().getActionRewardInformations() != null) {
						stringBuilder.append("\n\nAction reward:");
						stringBuilder.append('\n');
						stringBuilder.append(((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey())).getReward().getActionRewardInformations());
					}
				}
				Tooltip tooltip = new Tooltip(stringBuilder.toString());
				WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
				WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
				Tooltip.install(this.playersLeaderCardsPlayed.get(playerData.getKey()).get(index), tooltip);
				index++;
			}
			if (playerData.getKey() == GameStatus.getInstance().getOwnPlayerIndex()) {
				index = 0;
				for (int leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().keySet()) {
					this.playersLeaderCardsHand.get(playerData.getKey()).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard).getDisplayName());
					stringBuilder.append("\n\n");
					stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard).getDescription());
					stringBuilder.append("\n\n");
					if (GameStatus.getInstance().getLeaderCards().get(leaderCard) instanceof LeaderCardModifierInformations) {
						stringBuilder.append("PERMANENT ABILITY:\n");
						stringBuilder.append(((LeaderCardModifierInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getModifier());
					} else {
						stringBuilder.append("ONCE PER ROUND ABILITY:");
						if (!((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getResourceAmounts().isEmpty()) {
							stringBuilder.append("\n\nInstant resources:");
						}
						for (ResourceAmount resourceAmount : ((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getResourceAmounts()) {
							stringBuilder.append('\n');
							stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
							stringBuilder.append(": ");
							stringBuilder.append(resourceAmount.getAmount());
						}
						if (((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getActionRewardInformations() != null) {
							stringBuilder.append("\n\nAction reward:");
							stringBuilder.append('\n');
							stringBuilder.append(((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getActionRewardInformations());
						}
					}
					Tooltip tooltip = new Tooltip(stringBuilder.toString());
					WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
					WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
					Tooltip.install(this.playersLeaderCardsHand.get(playerData.getKey()).get(index), tooltip);
					index++;
				}
			} else {
				for (index = 0; index < playerData.getValue().getLeaderCardsInHandNumber(); index++) {
					this.playersLeaderCardsHand.get(playerData.getKey()).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource("/images/leader_cards/leader_card_back.png").toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				}
			}
			for (Entry<ResourceType, Integer> resourceAmount : playerData.getValue().getResourceAmounts().entrySet()) {
				if (this.playersResources.get(playerData.getKey()).containsKey(resourceAmount.getKey())) {
					this.playersResources.get(playerData.getKey()).get(resourceAmount.getKey()).setText(Integer.toString(resourceAmount.getValue()));
				}
			}
			this.victoryPointsPanes.get(playerData.getValue().getResourceAmounts().get(ResourceType.VICTORY_POINT) % 100).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.PLAYERS_PLACEHOLDERS.get(playerData.getValue().getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			this.militaryPointsPanes.get(playerData.getValue().getResourceAmounts().get(ResourceType.MILITARY_POINT)).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.PLAYERS_PLACEHOLDERS.get(playerData.getValue().getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			this.faithPointsPanes.get(playerData.getValue().getResourceAmounts().get(ResourceType.FAITH_POINT)).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.PLAYERS_PLACEHOLDERS.get(playerData.getValue().getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			if (GameStatus.getInstance().getCurrentPlayersData().size() == 5) {
				this.prestigePointsPanes.get(playerData.getValue().getResourceAmounts().get(ResourceType.PRESTIGE_POINT)).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.PLAYERS_PLACEHOLDERS.get(playerData.getValue().getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			}
			int harvestBigCounter = 0;
			int producionBigCounter = 0;
			for (Entry<FamilyMemberType, BoardPosition> familyMemberTypeBoardPositionEntry : playerData.getValue().getFamilyMembersPositions().entrySet()) {
				if (this.boardPositionsPanes.containsKey(familyMemberTypeBoardPositionEntry.getValue())) {
					this.boardPositionsPanes.get(familyMemberTypeBoardPositionEntry.getValue()).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.PLAYERS_FAMILY_MEMBER_TYPES.get(playerData.getValue().getColor()).get(familyMemberTypeBoardPositionEntry.getKey())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				} else if (familyMemberTypeBoardPositionEntry.getValue() == BoardPosition.HARVEST_BIG) {
					harvestBigCounter++;
					if (this.playersHarvestBigPositions.containsKey(playerData.getValue())) {
						this.harvestBigPositionsPanes.get(this.playersHarvestBigPositions.get(playerData.getValue())).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.PLAYERS_FAMILY_MEMBER_TYPES.get(playerData.getValue().getColor()).get(familyMemberTypeBoardPositionEntry.getKey())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
						this.harvestBigPositionsLabels.get(this.playersHarvestBigPositions.get(playerData.getValue())).setText(Integer.toString(harvestBigCounter));
					} else {
						for (Entry<Integer, Pane> pane : this.harvestBigPositionsPanes.entrySet()) {
							if (pane.getValue().getBackground() == null) {
								this.playersHarvestBigPositions.put(playerData.getValue(), pane.getKey());
								pane.getValue().setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.PLAYERS_FAMILY_MEMBER_TYPES.get(playerData.getValue().getColor()).get(familyMemberTypeBoardPositionEntry.getKey())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
								this.harvestBigPositionsLabels.get(pane.getKey()).setText(Integer.toString(harvestBigCounter));
								break;
							}
						}
					}
				} else if (familyMemberTypeBoardPositionEntry.getValue() == BoardPosition.PRODUCTION_BIG) {
					producionBigCounter++;
					if (this.playersProductionBigPositions.containsKey(playerData.getValue())) {
						this.productionBigPositionsPanes.get(this.playersProductionBigPositions.get(playerData.getValue())).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.PLAYERS_FAMILY_MEMBER_TYPES.get(playerData.getValue().getColor()).get(familyMemberTypeBoardPositionEntry.getKey())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
						this.productionBigPositionsLabels.get(this.playersProductionBigPositions.get(playerData.getValue())).setText(Integer.toString(producionBigCounter));
					} else {
						for (Entry<Integer, Pane> pane : this.productionBigPositionsPanes.entrySet()) {
							if (pane.getValue().getBackground() == null) {
								this.playersProductionBigPositions.put(playerData.getValue(), pane.getKey());
								pane.getValue().setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.PLAYERS_FAMILY_MEMBER_TYPES.get(playerData.getValue().getColor()).get(familyMemberTypeBoardPositionEntry.getKey())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
								this.productionBigPositionsLabels.get(pane.getKey()).setText(Integer.toString(producionBigCounter));
								break;
							}
						}
					}
				}
			}
		}
		for (Entry<Integer, Integer> orderPosition : GameStatus.getInstance().getCurrentCouncilPalaceOrder().entrySet()) {
			this.councilPalacePositionsPanes.get(orderPosition.getKey()).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.PLAYERS_PLACEHOLDERS.get(GameStatus.getInstance().getCurrentPlayersData().get(orderPosition.getValue()).getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
		}
		for (Entry<Integer, Integer> orderPosition : GameStatus.getInstance().getCurrentTurnOrder().entrySet()) {
			this.roundOrderPositionsPanes.get(orderPosition.getKey()).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.PLAYERS_PLACEHOLDERS.get(GameStatus.getInstance().getCurrentPlayersData().get(orderPosition.getValue()).getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
		}
		this.leaderCardsButton.setDisable(false);
	}

	public void showPersonalBonusTilesChoiceDialog()
	{
		this.personalBonusTilesChoiceDialog.show();
		this.personalBonusTilesChoiceDialogHBox.getChildren().clear();
		for (Integer personalBonusTileIndex : GameStatus.getInstance().getAvailablePersonalBonusTiles()) {
			Pane pane = new Pane();
			pane.setPrefWidth(76.0D * this.ratio);
			pane.setPrefHeight(650.0D * this.ratio);
			pane.setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTileIndex).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Production activation cost: ");
			stringBuilder.append(GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTileIndex).getProductionActivationCost());
			if (!GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTileIndex).getProductionInstantResources().isEmpty()) {
				stringBuilder.append("\n\nProduction bonus resources:");
			}
			for (ResourceAmount resourceAmount : GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTileIndex).getProductionInstantResources()) {
				stringBuilder.append('\n');
				stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
				stringBuilder.append(": ");
				stringBuilder.append(resourceAmount.getAmount());
			}
			stringBuilder.append("\n\nHarvest activation cost: ");
			stringBuilder.append(GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTileIndex).getProductionActivationCost());
			if (!GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTileIndex).getHarvestInstantResources().isEmpty()) {
				stringBuilder.append("\n\nHarvest bonus resources:");
			}
			for (ResourceAmount resourceAmount : GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTileIndex).getHarvestInstantResources()) {
				stringBuilder.append('\n');
				stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
				stringBuilder.append(": ");
				stringBuilder.append(resourceAmount.getAmount());
			}
			Tooltip tooltip = new Tooltip(stringBuilder.toString());
			WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
			WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
			Tooltip.install(pane, tooltip);
			Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
			pane.setOnMouseClicked(event -> {
				this.gameLogTextArea.appendText((this.gameLogTextArea.getText().length() < 1 ? "" : '\n') + "You have chosen a personal bonus tile");
				Client.getInstance().getConnectionHandler().sendGamePersonalBonusTilePlayerChoice(personalBonusTileIndex);
			});
			this.personalBonusTilesChoiceDialogHBox.getChildren().add(pane);
		}
		this.personalBonusTilesChoiceDialogLayout.setPrefWidth(76.0D * this.ratio * this.personalBonusTilesChoiceDialogHBox.getChildren().size() + this.personalBonusTilesChoiceDialogHBox.getSpacing() * (this.personalBonusTilesChoiceDialogHBox.getChildren().size() - 1) + this.personalBonusTilesChoiceDialogLayout.getInsets().getLeft() + this.personalBonusTilesChoiceDialogLayout.getInsets().getRight());
		this.personalBonusTilesChoiceDialogLayout.setPrefHeight(650.0D * this.ratio + this.personalBonusTilesChoiceDialogLayout.getInsets().getTop() + this.personalBonusTilesChoiceDialogLayout.getInsets().getTop() + 20.0D);
		this.personalBonusTilesChoiceDialog.show();
	}

	public void showLeaderCardsChoiceDialog()
	{
		this.leaderCardsChoiceDialogHBox.getChildren().clear();
		for (Integer leaderCard : GameStatus.getInstance().getAvailableLeaderCards()) {
			Pane pane = new Pane();
			pane.setPrefWidth(this.territory1.getWidth() * 3);
			pane.setPrefHeight(this.territory1.getHeight() * 3);
			pane.setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard).getDisplayName());
			stringBuilder.append("\n\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard).getDescription());
			stringBuilder.append("\n\n");
			if (GameStatus.getInstance().getLeaderCards().get(leaderCard) instanceof LeaderCardModifierInformations) {
				stringBuilder.append("PERMANENT ABILITY:\n");
				stringBuilder.append(((LeaderCardModifierInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getModifier());
			} else {
				stringBuilder.append("ONCE PER ROUND ABILITY:");
				if (!((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getResourceAmounts().isEmpty()) {
					stringBuilder.append("\n\nInstant resources:");
				}
				for (ResourceAmount resourceAmount : ((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getResourceAmounts()) {
					stringBuilder.append('\n');
					stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
				if (((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getActionRewardInformations() != null) {
					stringBuilder.append("\n\nAction reward:");
					stringBuilder.append('\n');
					stringBuilder.append(((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getActionRewardInformations());
				}
			}
			Tooltip tooltip = new Tooltip(stringBuilder.toString());
			WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
			WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
			Tooltip.install(pane, tooltip);
			Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
			pane.setOnMouseClicked(event -> Client.getInstance().getConnectionHandler().sendGameLeaderCardPlayerChoice(leaderCard));
			this.leaderCardsChoiceDialogHBox.getChildren().add(pane);
		}
		this.leaderCardsChoiceDialogLayout.setPrefWidth(this.territory1.getWidth() * 3 * this.leaderCardsChoiceDialogHBox.getChildren().size() + this.leaderCardsChoiceDialogHBox.getSpacing() * (this.leaderCardsChoiceDialogHBox.getChildren().size() - 1) + this.leaderCardsChoiceDialogLayout.getInsets().getLeft() + this.leaderCardsChoiceDialogLayout.getInsets().getRight());
		this.leaderCardsChoiceDialogLayout.setPrefHeight(this.territory1.getHeight() * 3 + this.leaderCardsChoiceDialogLayout.getInsets().getTop() + this.leaderCardsChoiceDialogLayout.getInsets().getTop() + 20.0D);
		this.leaderCardsChoiceDialog.show();
	}

	public void showExcommunicationChoiceDialog(Period period)
	{
		this.excommunicationChoiceDialogText.setText(GameStatus.getInstance().getExcommunicationTiles().get(GameStatus.getInstance().getCurrentExcommunicationTiles().get(period)).getModifier());
		this.excommunicationChoiceDialog.show();
		Client.getInstance().setSoundsMediaPlayer(new MediaPlayer(new Media(this.getClass().getResource("/sounds/excommunication.mp3").toString())));
		Client.getInstance().getSoundsMediaPlayer().setOnEndOfMedia(() -> Client.getInstance().getBackgroundMediaPlayer().dispose());
		Client.getInstance().getSoundsMediaPlayer().play();
	}

	private void showCouncilPalaceActionChoice(FamilyMemberType familyMemberType)
	{
		this.servantsChoiceDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		this.servantsChoiceDialogSlider.setValue(0);
		this.servantsChoiceDialogAcceptButton.setOnAction((event) -> {
			this.servantsChoiceDialog.close();
			Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsCouncilPalace(familyMemberType, (int) this.servantsChoiceDialogSlider.getValue()));
		});
		this.servantsChoiceDialog.show();
	}

	private void showHarvestActionChoice(FamilyMemberType familyMemberType)
	{
		this.servantsChoiceDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		this.servantsChoiceDialogSlider.setValue(0);
		this.servantsChoiceDialogAcceptButton.setOnAction((event) -> {
			this.servantsChoiceDialog.close();
			Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsHarvest(familyMemberType, (int) this.servantsChoiceDialogSlider.getValue()));
		});
		this.servantsChoiceDialog.show();
	}

	private void showMarketActionChoice(MarketSlot marketSlot, FamilyMemberType familyMemberType)
	{
		this.servantsChoiceDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		this.servantsChoiceDialogSlider.setValue(0);
		this.servantsChoiceDialogAcceptButton.setOnAction((event) -> {
			this.servantsChoiceDialog.close();
			Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsMarket(familyMemberType, (int) this.servantsChoiceDialogSlider.getValue(), marketSlot));
		});
		this.servantsChoiceDialog.show();
	}

	private void showPickDevelopmentCardActionChoice(FamilyMemberType familyMemberType)
	{
		this.pickingDevelopmentCard = true;
		this.actionsVBox.getChildren().clear();
		JFXNodesList actionsNodesList = new JFXNodesList();
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action_pick_development_card.png", false);
		this.pickDevelopmentCardActionButton = new JFXButton();
		this.pickDevelopmentCardActionButton.setDisable(true);
		ImageView imageView = new ImageView(new Image(Client.class.getResource("/images/icons/action.png").toString()));
		this.pickDevelopmentCardActionButton.setGraphic(imageView);
		this.pickDevelopmentCardActionButton.getStyleClass().add("animated-option-button-green");
		this.pickDevelopmentCardActionButton.setOnMouseClicked(event -> {
			this.pickDevelopmentCardChoiceDialogAcceptButton.setDisable(true);
			this.pickDevelopmentCardChoiceDialogDiscountChoicesHBox.getChildren().clear();
			this.pickDevelopmentCardChoiceDialogResourceCostOptionsHBox.getChildren().clear();
			this.pickDevelopmentCardChoiceDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
			this.pickDevelopmentCardChoiceDialogSlider.setValue(0);
			this.selectedDiscountChoice.clear();
			this.selectedResourceCostOption = null;
			for (AvailableAction availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD)) {
				if (((AvailableActionPickDevelopmentCard) availableAction).getCardType() == this.selectedDevelopmentCardType && ((AvailableActionPickDevelopmentCard) availableAction).getRow() == GameStatus.getInstance().getDevelopmentCardRow(this.selectedDevelopmentCardType, this.selectedDevelopmentCardIndex)) {
					List<Pane> discountChoicesPanes = new ArrayList<>();
					for (List<ResourceAmount> discountChoice : ((AvailableActionPickDevelopmentCard) availableAction).getDiscountChoices()) {
						Pane pane = new Pane();
						discountChoicesPanes.add(pane);
						Text text = new Text();
						StringBuilder stringBuilder = new StringBuilder();
						boolean first = true;
						for (ResourceAmount resourceAmount : discountChoice) {
							if (!first) {
								stringBuilder.append('\n');
							} else {
								first = false;
							}
							stringBuilder.append("- ");
							stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
							stringBuilder.append(": ");
							stringBuilder.append(Integer.toString(resourceAmount.getAmount()));
						}
						text.setText(stringBuilder.toString());
						pane.getChildren().add(text);
						pane.setOnMouseClicked(childEvent -> {
							this.selectedDiscountChoice.addAll(discountChoice);
							if (((AvailableActionPickDevelopmentCard) availableAction).getResourceCostOptions().isEmpty() || this.selectedResourceCostOption != null) {
								this.pickDevelopmentCardChoiceDialogAcceptButton.setDisable(false);
							}
							pane.setEffect(ControllerGame.MOUSE_OVER_EFFECT);
							for (Pane otherPane : discountChoicesPanes) {
								if (otherPane != pane) {
									otherPane.setEffect(null);
								}
							}
						});
						this.pickDevelopmentCardChoiceDialogDiscountChoicesHBox.getChildren().add(pane);
					}
					if (((AvailableActionPickDevelopmentCard) availableAction).getResourceCostOptions().isEmpty()) {
						this.pickDevelopmentCardChoiceDialogAcceptButton.setDisable(false);
					} else {
						List<AnchorPane> resourceCostOptionsAnchorPanes = new ArrayList<>();
						for (ResourceCostOption resourceCostOption : ((AvailableActionPickDevelopmentCard) availableAction).getResourceCostOptions()) {
							AnchorPane anchorPane = new AnchorPane();
							resourceCostOptionsAnchorPanes.add(anchorPane);
							Text text = new Text();
							StringBuilder stringBuilder = new StringBuilder();
							boolean first = true;
							for (ResourceAmount resourceAmount : resourceCostOption.getRequiredResources()) {
								if (first) {
									first = false;
									stringBuilder.append("REQUIRED RESOURCES:");
								}
								stringBuilder.append("\n- ");
								stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
								stringBuilder.append(": ");
								stringBuilder.append(Integer.toString(resourceAmount.getAmount()));
							}
							first = true;
							for (ResourceAmount resourceAmount : resourceCostOption.getSpentResources()) {
								if (first) {
									first = false;
									stringBuilder.append("SPENT RESOURCES:");
								}
								stringBuilder.append("\n- ");
								stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
								stringBuilder.append(": ");
								stringBuilder.append(Integer.toString(resourceAmount.getAmount()));
							}
							text.setText(stringBuilder.toString());
							anchorPane.getChildren().add(text);
							AnchorPane.setTopAnchor(text, 0.0);
							AnchorPane.setBottomAnchor(text, 0.0);
							AnchorPane.setLeftAnchor(text, 0.0);
							AnchorPane.setRightAnchor(text, 0.0);
							anchorPane.setOnMouseClicked(childEvent -> {
								this.selectedResourceCostOption = resourceCostOption;
								if (((AvailableActionPickDevelopmentCard) availableAction).getDiscountChoices().isEmpty() || !this.selectedDiscountChoice.isEmpty()) {
									this.pickDevelopmentCardChoiceDialogAcceptButton.setDisable(false);
								}
								anchorPane.setEffect(ControllerGame.MOUSE_OVER_EFFECT);
								for (AnchorPane otherAnchorPane : resourceCostOptionsAnchorPanes) {
									if (otherAnchorPane != anchorPane) {
										otherAnchorPane.setEffect(null);
									}
								}
							});
							this.pickDevelopmentCardChoiceDialogResourceCostOptionsHBox.getChildren().add(anchorPane);
						}
					}
					break;
				}
			}
			this.pickDevelopmentCardChoiceDialogAcceptButton.setOnAction(childEvent -> {
				this.pickDevelopmentCardChoiceDialog.close();
				Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsPickDevelopmentCard(familyMemberType, (int) this.pickDevelopmentCardChoiceDialogSlider.getValue(), this.selectedDevelopmentCardType, GameStatus.getInstance().getDevelopmentCardRow(this.selectedDevelopmentCardType, this.selectedDevelopmentCardIndex), this.selectedDiscountChoice, this.selectedResourceCostOption));
			});
			this.pickDevelopmentCardChoiceDialogAcceptButton.setPrefWidth(((VBox) this.pickDevelopmentCardChoiceDialogAcceptButton.getParent()).getWidth());
			this.pickDevelopmentCardChoiceDialogCancelButton.setPrefWidth(((VBox) this.pickDevelopmentCardChoiceDialogCancelButton.getParent()).getWidth());
			this.pickDevelopmentCardChoiceDialogAcceptButton.requestLayout();
			this.pickDevelopmentCardChoiceDialogCancelButton.requestLayout();
			this.pickDevelopmentCardChoiceDialog.show();
		});
		actionsNodesList.addAnimatedNode(this.pickDevelopmentCardActionButton);
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action_refuse_reward.png", false, true, () -> {
			this.pickingDevelopmentCard = false;
			this.selectedDevelopmentCardType = null;
			this.selectedDevelopmentCardIndex = null;
			for (CardType cardType : CardType.values()) {
				for (Pane pane : this.developmentCardsPanes.get(cardType).values()) {
					pane.setEffect(null);
					Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
				}
			}
			this.generateAvailableActions();
		});
		actionsNodesList.setSpacing(10.0D);
		actionsNodesList.setRotate(180.0D);
		this.actionsVBox.getChildren().add(actionsNodesList);
		this.selectableDevelopmentCards.clear();
		for (Entry<ActionType, List<AvailableAction>> availableActions : GameStatus.getInstance().getCurrentAvailableActions().entrySet()) {
			if (availableActions.getKey() == ActionType.PICK_DEVELOPMENT_CARD) {
				for (AvailableAction availableAction : availableActions.getValue()) {
					this.selectableDevelopmentCards.add(this.developmentCardsPanes.get(((AvailableActionPickDevelopmentCard) availableAction).getCardType()).get(((AvailableActionPickDevelopmentCard) availableAction).getRow()));
				}
			}
		}
		for (CardType cardType : CardType.values()) {
			for (Pane pane : this.developmentCardsPanes.get(cardType).values()) {
				if (!this.selectableDevelopmentCards.contains(pane)) {
					Utils.unsetEffect(pane);
				}
			}
		}
	}

	private void showProductionStartChoice(FamilyMemberType familyMemberType)
	{
		this.servantsChoiceDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		this.servantsChoiceDialogSlider.setValue(0);
		this.servantsChoiceDialogAcceptButton.setOnAction((event) -> {
			this.servantsChoiceDialog.close();
			Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsProductionStart(familyMemberType, (int) this.servantsChoiceDialogSlider.getValue()));
		});
		this.servantsChoiceDialog.show();
	}

	private void showLeaderActivateChoice()
	{
		this.leaderCardsChoiceDialog.setOverlayClose(true);
		this.leaderCardsChoiceDialogHBox.getChildren().clear();
		for (Entry<Integer, Boolean> leaderCard : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getLeaderCardsPlayed().entrySet()) {
			if (!leaderCard.getValue()) {
				continue;
			}
			Pane pane = new Pane();
			pane.setPrefWidth(this.territory1.getWidth() * 3);
			pane.setPrefHeight(this.territory1.getHeight() * 3);
			pane.setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getDisplayName());
			stringBuilder.append("\n\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getDescription());
			stringBuilder.append("\n\n");
			if (GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()) instanceof LeaderCardModifierInformations) {
				stringBuilder.append("PERMANENT ABILITY:\n");
				stringBuilder.append(((LeaderCardModifierInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey())).getModifier());
			} else {
				stringBuilder.append("ONCE PER ROUND ABILITY:");
				if (!((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey())).getReward().getResourceAmounts().isEmpty()) {
					stringBuilder.append("\n\nInstant resources:");
				}
				for (ResourceAmount resourceAmount : ((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey())).getReward().getResourceAmounts()) {
					stringBuilder.append('\n');
					stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
				if (((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey())).getReward().getActionRewardInformations() != null) {
					stringBuilder.append("\n\nAction reward:");
					stringBuilder.append('\n');
					stringBuilder.append(((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey())).getReward().getActionRewardInformations());
				}
			}
			Tooltip tooltip = new Tooltip(stringBuilder.toString());
			WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
			WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
			Tooltip.install(pane, tooltip);
			Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
			pane.setOnMouseClicked(event -> {
				this.leaderCardsChoiceDialog.close();
				Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsLeaderActivate(leaderCard.getKey()));
			});
			this.leaderCardsChoiceDialogHBox.getChildren().add(pane);
		}
		this.leaderCardsChoiceDialogLayout.setPrefWidth(this.territory1.getWidth() * 3 * this.leaderCardsChoiceDialogHBox.getChildren().size() + this.leaderCardsChoiceDialogHBox.getSpacing() * (this.leaderCardsChoiceDialogHBox.getChildren().size() - 1) + this.leaderCardsChoiceDialogLayout.getInsets().getLeft() + this.leaderCardsChoiceDialogLayout.getInsets().getRight());
		this.leaderCardsChoiceDialogLayout.setPrefHeight(this.territory1.getHeight() * 3 + this.leaderCardsChoiceDialogLayout.getInsets().getTop() + this.leaderCardsChoiceDialogLayout.getInsets().getTop() + 20.0D);
		this.leaderCardsChoiceDialog.show();
	}

	private void showLeaderDiscardChoice()
	{
		this.leaderCardsChoiceDialog.setOverlayClose(true);
		this.leaderCardsChoiceDialogHBox.getChildren().clear();
		for (Integer leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().keySet()) {
			Pane pane = new Pane();
			pane.setPrefWidth(this.territory1.getWidth() * 3);
			pane.setPrefHeight(this.territory1.getHeight() * 3);
			pane.setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard).getDisplayName());
			stringBuilder.append("\n\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard).getDescription());
			stringBuilder.append("\n\n");
			if (GameStatus.getInstance().getLeaderCards().get(leaderCard) instanceof LeaderCardModifierInformations) {
				stringBuilder.append("PERMANENT ABILITY:\n");
				stringBuilder.append(((LeaderCardModifierInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getModifier());
			} else {
				stringBuilder.append("ONCE PER ROUND ABILITY:");
				if (!((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getResourceAmounts().isEmpty()) {
					stringBuilder.append("\n\nInstant resources:");
				}
				for (ResourceAmount resourceAmount : ((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getResourceAmounts()) {
					stringBuilder.append('\n');
					stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
				if (((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getActionRewardInformations() != null) {
					stringBuilder.append("\n\nAction reward:");
					stringBuilder.append('\n');
					stringBuilder.append(((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getActionRewardInformations());
				}
			}
			Tooltip tooltip = new Tooltip(stringBuilder.toString());
			WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
			WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
			Tooltip.install(pane, tooltip);
			Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
			pane.setOnMouseClicked(event -> {
				this.leaderCardsChoiceDialog.close();
				Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsLeaderDiscard(leaderCard));
			});
			this.leaderCardsChoiceDialogHBox.getChildren().add(pane);
		}
		this.leaderCardsChoiceDialogLayout.setPrefWidth(this.territory1.getWidth() * 3 * this.leaderCardsChoiceDialogHBox.getChildren().size() + this.leaderCardsChoiceDialogHBox.getSpacing() * (this.leaderCardsChoiceDialogHBox.getChildren().size() - 1) + this.leaderCardsChoiceDialogLayout.getInsets().getLeft() + this.leaderCardsChoiceDialogLayout.getInsets().getRight());
		this.leaderCardsChoiceDialogLayout.setPrefHeight(this.territory1.getHeight() * 3 + this.leaderCardsChoiceDialogLayout.getInsets().getTop() + this.leaderCardsChoiceDialogLayout.getInsets().getTop() + 20.0D);
		this.leaderCardsChoiceDialog.show();
	}

	private void showLeaderPlayChoice()
	{
		this.leaderCardsChoiceDialog.setOverlayClose(true);
		this.leaderCardsChoiceDialogHBox.getChildren().clear();
		for (Integer leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().keySet()) {
			Pane pane = new Pane();
			pane.setPrefWidth(this.territory1.getWidth() * 3);
			pane.setPrefHeight(this.territory1.getHeight() * 3);
			pane.setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard).getDisplayName());
			stringBuilder.append("\n\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard).getDescription());
			stringBuilder.append("\n\n");
			if (GameStatus.getInstance().getLeaderCards().get(leaderCard) instanceof LeaderCardModifierInformations) {
				stringBuilder.append("PERMANENT ABILITY:\n");
				stringBuilder.append(((LeaderCardModifierInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getModifier());
			} else {
				stringBuilder.append("ONCE PER ROUND ABILITY:");
				if (!((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getResourceAmounts().isEmpty()) {
					stringBuilder.append("\n\nInstant resources:");
				}
				for (ResourceAmount resourceAmount : ((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getResourceAmounts()) {
					stringBuilder.append('\n');
					stringBuilder.append(Utils.RESOURCES_TYPES_NAMES.get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
				if (((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getActionRewardInformations() != null) {
					stringBuilder.append("\n\nAction reward:");
					stringBuilder.append('\n');
					stringBuilder.append(((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(leaderCard)).getReward().getActionRewardInformations());
				}
			}
			Tooltip tooltip = new Tooltip(stringBuilder.toString());
			WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
			WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
			Tooltip.install(pane, tooltip);
			Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
			pane.setOnMouseClicked(event -> {
				this.leaderCardsChoiceDialog.close();
				Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsLeaderPlay(leaderCard));
			});
			this.leaderCardsChoiceDialogHBox.getChildren().add(pane);
		}
		this.leaderCardsChoiceDialogLayout.setPrefWidth(this.territory1.getWidth() * 3 * this.leaderCardsChoiceDialogHBox.getChildren().size() + this.leaderCardsChoiceDialogHBox.getSpacing() * (this.leaderCardsChoiceDialogHBox.getChildren().size() - 1) + this.leaderCardsChoiceDialogLayout.getInsets().getLeft() + this.leaderCardsChoiceDialogLayout.getInsets().getRight());
		this.leaderCardsChoiceDialogLayout.setPrefHeight(this.territory1.getHeight() * 3 + this.leaderCardsChoiceDialogLayout.getInsets().getTop() + this.leaderCardsChoiceDialogLayout.getInsets().getTop() + 20.0D);
		this.leaderCardsChoiceDialog.show();
	}

	public TextArea getChatTextArea()
	{
		return this.chatTextArea;
	}

	public TextArea getGameLogTextArea()
	{
		return this.gameLogTextArea;
	}

	public Label getTimerLabel()
	{
		return this.timerLabel;
	}

	public JFXDialog getPersonalBonusTilesChoiceDialog()
	{
		return this.personalBonusTilesChoiceDialog;
	}

	public JFXDialog getLeaderCardsChoiceDialog()
	{
		return this.leaderCardsChoiceDialog;
	}
}
