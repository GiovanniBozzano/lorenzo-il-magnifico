package it.polimi.ingsw.lim.client.gui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.actions.*;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.gui.CustomController;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
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
	@FXML private Pane harvestSmall;
	@FXML private Pane harvestBig1;
	@FXML private Pane harvestBig2;
	@FXML private Pane harvestBig3;
	@FXML private Pane harvestBig4;
	@FXML private Pane harvestBig5;
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
	@FXML private VBox rightVBox;
	@FXML private JFXTabPane playersTabPane;
	@FXML private Tab player1Tab;
	@FXML private Tab player2Tab;
	@FXML private Tab player3Tab;
	@FXML private Tab player4Tab;
	@FXML private Tab player5Tab;
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
	@FXML private JFXTabPane chatTabPane;
	@FXML private TextArea chatTextArea;
	@FXML private TextArea gameLogTextArea;
	@FXML private VBox actionsVBox;
	@FXML private JFXButton leaderCardsButton;
	@FXML private Label timerLabel;
	@FXML private JFXDialog dialog;
	@FXML private JFXDialogLayout dialogLayout;
	@FXML private Label dialogLabel;
	@FXML private JFXButton dialogOkButton;
	@FXML private JFXDialog personalBonusTilesDialog;
	@FXML private JFXDialogLayout personalBonusTilesDialogLayout;
	@FXML private HBox personalBonusTilesDialogHBox;
	@FXML private JFXDialog leaderCardsDialog;
	@FXML private JFXDialogLayout leaderCardsDialogLayout;
	@FXML private HBox leaderCardsDialogHBox;
	@FXML private JFXDialog excommunicationDialog;
	@FXML private Text excommunicationDialogText;
	@FXML private JFXButton excommunicationDialogSupportButton;
	@FXML private JFXButton excommunicationDialogDoNotSupportButton;
	@FXML private JFXDialog servantsDialog;
	@FXML private JFXSlider servantsDialogSlider;
	@FXML private JFXButton servantsDialogAcceptButton;
	@FXML private JFXButton servantsDialogCancelButton;
	@FXML private JFXDialog pickDevelopmentCardDialog;
	@FXML private JFXSlider pickDevelopmentCardDialogSlider;
	@FXML private HBox pickDevelopmentCardDialogDiscountChoicesHBox;
	@FXML private HBox pickDevelopmentCardDialogResourceCostOptionsHBox;
	@FXML private JFXButton pickDevelopmentCardDialogAcceptButton;
	@FXML private JFXButton pickDevelopmentCardDialogCancelButton;
	@FXML private JFXDialog expectedChooseLorenzoDeMediciLeaderDialog;
	@FXML private JFXDialogLayout expectedChooseLorenzoDeMediciLeaderDialogLayout;
	@FXML private VBox expectedChooseLorenzoDeMediciLeaderDialogVBox;
	@FXML private JFXDialog expectedChooseRewardCouncilPrivilegeDialog;
	@FXML private Label expectedChooseRewardCouncilPrivilegeDialogLabel;
	@FXML private Pane expectedChooseRewardCouncilPrivilegeDialogPane;
	@FXML private JFXDialog expectedChooseRewardServantsDialog;
	@FXML private Label expectedChooseRewardServantsDialogLabel;
	@FXML private JFXSlider expectedChooseRewardServantsDialogSlider;
	@FXML private JFXButton expectedChooseRewardServantsDialogAcceptButton;
	@FXML private JFXDialog expectedChooseRewardPickDevelopmentCardDialog;
	@FXML private JFXSlider expectedChooseRewardPickDevelopmentCardDialogSlider;
	@FXML private JFXRadioButton expectedChooseRewardPickDevelopmentCardDialogThirdRow;
	@FXML private JFXRadioButton expectedChooseRewardPickDevelopmentCardDialogFourthRow;
	@FXML private HBox expectedChooseRewardPickDevelopmentCardDialogInstantDiscountChoicesHBox;
	@FXML private HBox expectedChooseRewardPickDevelopmentCardDialogDiscountChoicesHBox;
	@FXML private HBox expectedChooseRewardPickDevelopmentCardDialogResourceCostOptionsHBox;
	@FXML private JFXButton expectedChooseRewardPickDevelopmentCardDialogAcceptButton;
	@FXML private JFXButton expectedChooseRewardPickDevelopmentCardDialogCancelButton;
	@FXML private JFXDialog expectedChooseRewardTemporaryModifierDialog;
	@FXML private HBox expectedChooseRewardTemporaryModifierDialogHBox;
	@FXML private JFXButton expectedChooseRewardCouncilPrivilegeDialogAcceptButton;
	@FXML private JFXDialog developmentCardDialog;
	@FXML private JFXDialogLayout developmentCardDialogLayout;
	@FXML private Pane developmentCardDialogPane;
	@FXML private ScrollPane developmentCardDialogScrollPane;
	@FXML private Text developmentCardDialogText;
	@FXML private JFXDialog playersLeaderCardsDialog;
	@FXML private JFXTabPane playersLeaderCardsTabPane;
	@FXML private Tab leaderCardsPlayer1Tab;
	@FXML private Tab leaderCardsPlayer2Tab;
	@FXML private Tab leaderCardsPlayer3Tab;
	@FXML private Tab leaderCardsPlayer4Tab;
	@FXML private Tab leaderCardsPlayer5Tab;
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
	@FXML private JFXDialog endGameDialog;
	@FXML private Label endGameDialogTitle;
	@FXML private VBox endGameDialogPlayersVBox;
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

	private static final Map<ActionType, IExpectedActionHandler> EXPECTED_ACTION_HANDLERS = new EnumMap<>(ActionType.class);

	static {
		ControllerGame.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER, (controllerGame, expectedAction) -> controllerGame.showExpectedChooseLorenzoDeMediciLeader((ExpectedActionChooseLorenzoDeMediciLeader) expectedAction));
		ControllerGame.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE, (controllerGame, expectedAction) -> controllerGame.showExpectedChooseRewardCouncilPrivilege((ExpectedActionChooseRewardCouncilPrivilege) expectedAction));
		ControllerGame.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_HARVEST, (controllerGame, expectedAction) -> controllerGame.showExpectedChooseRewardHarvest((ExpectedActionChooseRewardHarvest) expectedAction));
		ControllerGame.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD, (controllerGame, expectedAction) -> controllerGame.showExpectedChooseRewardPickDevelopmentCard((ExpectedActionChooseRewardPickDevelopmentCard) expectedAction));
		ControllerGame.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_PRODUCTION_START, (controllerGame, expectedAction) -> controllerGame.showExpectedChooseRewardProductionStart((ExpectedActionChooseRewardProductionStart) expectedAction));
		ControllerGame.EXPECTED_ACTION_HANDLERS.put(ActionType.PRODUCTION_TRADE, (controllerGame, expectedAction) -> controllerGame.showExpectedProductionTrade((ExpectedActionProductionTrade) expectedAction));
		ControllerGame.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER, (controllerGame, expectedAction) -> controllerGame.showExpectedChooseRewardTemporaryModifier());
	}

	private final Map<Integer, Tab> playersTabs = new HashMap<>();
	private final Map<Integer, Tab> leaderCardsTabs = new HashMap<>();
	private final Map<Integer, Pane> playersBoards = new HashMap<>();
	private final Map<FamilyMemberType, Pane> dices = new EnumMap<>(FamilyMemberType.class);
	private final Map<Period, Pane> excommunicationTilesPanes = new EnumMap<>(Period.class);
	private final Map<Period, List<Pane>> excommunicationTilesPlayersPanes = new EnumMap<>(Period.class);
	private final Map<Integer, Pane> victoryPointsPanes = new HashMap<>();
	private final Map<Integer, Pane> militaryPointsPanes = new HashMap<>();
	private final Map<Integer, Pane> faithPointsPanes = new HashMap<>();
	private final Map<Integer, Pane> prestigePointsPanes = new HashMap<>();
	private final Map<Integer, Pane> councilPalacePositionsPanes = new HashMap<>();
	private final Map<Integer, Pane> roundOrderPositionsPanes = new HashMap<>();
	private final Map<Integer, Pane> harvestBigPositionsPanes = new HashMap<>();
	private final Map<Integer, Pane> productionBigPositionsPanes = new HashMap<>();
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
	private Row selectedInstantRewardRow;
	private final List<ResourceAmount> selectedInstantDiscountChoice = new ArrayList<>();
	private final List<ResourceAmount> selectedDiscountChoice = new ArrayList<>();
	private ResourceCostOption selectedResourceCostOption;

	@FXML
	private void boardDevelopmentCardPaneMouseClicked(MouseEvent event)
	{
		Pane pane = (Pane) event.getSource();
		if (event.getButton() == MouseButton.PRIMARY && this.pickingDevelopmentCard && this.selectableDevelopmentCards.contains(pane)) {
			if (this.developmentCardsBuildingPanes.containsValue(pane)) {
				if (this.selectedDevelopmentCardType == CardType.BUILDING && this.selectedDevelopmentCardIndex.equals(this.developmentCardsBuildingIndexes.get(pane))) {
					this.resetSelectedDevelopmentCard(pane);
				} else {
					this.pickDevelopmentCardActionButton.setDisable(false);
					this.selectedDevelopmentCardType = CardType.BUILDING;
					this.selectedDevelopmentCardIndex = this.developmentCardsBuildingIndexes.get(pane);
					this.resetDevelopmentCardsEffects();
					pane.setEffect(ControllerGame.DEVELOPMENT_CARD_BUILDING_SELECTED_EFFECT);
					Utils.unsetEffect(pane);
				}
			} else if (this.developmentCardsCharacterPanes.containsValue(pane)) {
				if (this.selectedDevelopmentCardType == CardType.CHARACTER && this.selectedDevelopmentCardIndex.equals(this.developmentCardsCharacterIndexes.get(pane))) {
					this.resetSelectedDevelopmentCard(pane);
				} else {
					this.pickDevelopmentCardActionButton.setDisable(false);
					this.selectedDevelopmentCardType = CardType.CHARACTER;
					this.selectedDevelopmentCardIndex = this.developmentCardsCharacterIndexes.get(pane);
					this.resetDevelopmentCardsEffects();
					pane.setEffect(ControllerGame.DEVELOPMENT_CARD_CHARACTER_SELECTED_EFFECT);
					Utils.unsetEffect(pane);
				}
			} else if (this.developmentCardsTerritoryPanes.containsValue(pane)) {
				if (this.selectedDevelopmentCardType == CardType.TERRITORY && this.selectedDevelopmentCardIndex.equals(this.developmentCardsTerritoryIndexes.get(pane))) {
					this.resetSelectedDevelopmentCard(pane);
				} else {
					this.pickDevelopmentCardActionButton.setDisable(false);
					this.selectedDevelopmentCardType = CardType.TERRITORY;
					this.selectedDevelopmentCardIndex = this.developmentCardsTerritoryIndexes.get(pane);
					this.resetDevelopmentCardsEffects();
					pane.setEffect(ControllerGame.DEVELOPMENT_CARD_TERRITORY_SELECTED_EFFECT);
					Utils.unsetEffect(pane);
				}
			} else {
				if (this.selectedDevelopmentCardType == CardType.VENTURE && this.selectedDevelopmentCardIndex.equals(this.developmentCardsVentureIndexes.get(pane))) {
					this.resetSelectedDevelopmentCard(pane);
				} else {
					this.pickDevelopmentCardActionButton.setDisable(false);
					this.selectedDevelopmentCardType = CardType.VENTURE;
					this.selectedDevelopmentCardIndex = this.developmentCardsVentureIndexes.get(pane);
					this.resetDevelopmentCardsEffects();
					pane.setEffect(ControllerGame.DEVELOPMENT_CARD_VENTURE_SELECTED_EFFECT);
					Utils.unsetEffect(pane);
				}
			}
		} else if (event.getButton() == MouseButton.SECONDARY && pane.getBackground() != null) {
			this.developmentCardDialogPane.setBackground(pane.getBackground());
			this.developmentCardDialogText.setText(this.getDevelopmentCardInformations(pane));
			this.developmentCardDialog.show();
		}
	}

	@FXML
	private void playerDevelopmentCardPaneMouseClicked(MouseEvent event)
	{
		if (event.getButton() == MouseButton.SECONDARY && ((Pane) event.getSource()).getBackground() != null) {
			this.developmentCardDialogPane.setBackground(((Pane) event.getSource()).getBackground());
			this.developmentCardDialogText.setText(this.getDevelopmentCardInformations((Pane) event.getSource()));
			this.developmentCardDialog.show();
		}
	}

	@FXML
	private void handleChatTextAreaAction(ActionEvent event)
	{
		Utils.sendChatMessage((TextField) event.getSource(), this.chatTextArea);
	}

	@FXML
	private void handlePlayersLeaderCardsButtonAction()
	{
		this.playersLeaderCardsDialog.show();
	}

	@FXML
	public void handleDialogOkButtonAction()
	{
		this.dialog.close();
		this.getStackPane().getScene().getRoot().requestFocus();
	}

	@FXML
	private void handleExcommunicationDialogSupportButtonAction()
	{
		this.excommunicationDialog.close();
		Client.getInstance().getConnectionHandler().sendGameExcommunicationPlayerChoice(false);
	}

	@FXML
	private void handleExcommunicationDialogDoNotSupportButtonAction()
	{
		this.excommunicationDialog.close();
		Client.getInstance().getConnectionHandler().sendGameExcommunicationPlayerChoice(true);
	}

	@FXML
	private void handleServantsDialogCancelButtonAction()
	{
		this.servantsDialog.close();
	}

	@FXML
	private void handlePickDevelopmentCardDialogCancelButtonAction()
	{
		this.pickDevelopmentCardDialog.close();
	}

	@FXML
	private void handleExpectedChooseRewardPickDevelopmentCardDialogThirdRowAction()
	{
		this.selectedInstantRewardRow = Row.THIRD;
	}

	@FXML
	private void handleExpectedChooseRewardPickDevelopmentCardDialogFourthRowAction()
	{
		this.selectedInstantRewardRow = Row.FOURTH;
	}

	@FXML
	void handleExpectedChooseRewardPickDevelopmentCardDialogCancelButtonAction()
	{
		this.expectedChooseRewardPickDevelopmentCardDialog.close();
	}

	@FXML
	private void handleEndGameDialogDisconnectButtonAction()
	{
		Client.getInstance().disconnect(false, true);
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
		this.excommunicationTilesPlayersPanes.put(Period.FIRST, Arrays.asList(this.excommunicationTileFirstPlayer1, this.excommunicationTileFirstPlayer2, this.excommunicationTileFirstPlayer3, this.excommunicationTileFirstPlayer4, this.excommunicationTileFirstPlayer5));
		this.excommunicationTilesPlayersPanes.put(Period.SECOND, Arrays.asList(this.excommunicationTileSecondPlayer1, this.excommunicationTileSecondPlayer2, this.excommunicationTileSecondPlayer3, this.excommunicationTileSecondPlayer4, this.excommunicationTileSecondPlayer5));
		this.excommunicationTilesPlayersPanes.put(Period.THIRD, Arrays.asList(this.excommunicationTileThirdPlayer1, this.excommunicationTileThirdPlayer2, this.excommunicationTileThirdPlayer3, this.excommunicationTileThirdPlayer4, this.excommunicationTileThirdPlayer5));
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
		this.productionBigPositionsPanes.put(0, this.productionBig1);
		this.productionBigPositionsPanes.put(1, this.productionBig2);
		this.productionBigPositionsPanes.put(2, this.productionBig3);
		this.productionBigPositionsPanes.put(3, this.productionBig4);
		this.productionBigPositionsPanes.put(4, this.productionBig5);
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
		this.getStackPane().getChildren().remove(this.dialog);
		this.dialog.setTransitionType(DialogTransition.CENTER);
		this.dialog.setDialogContainer(this.getStackPane());
		this.dialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.personalBonusTilesDialog);
		this.personalBonusTilesDialog.setTransitionType(DialogTransition.CENTER);
		this.personalBonusTilesDialog.setDialogContainer(this.getStackPane());
		this.personalBonusTilesDialog.setOverlayClose(false);
		this.personalBonusTilesDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.leaderCardsDialog);
		this.leaderCardsDialog.setTransitionType(DialogTransition.CENTER);
		this.leaderCardsDialog.setDialogContainer(this.getStackPane());
		this.leaderCardsDialog.setOverlayClose(false);
		this.leaderCardsDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.excommunicationDialog);
		this.excommunicationDialog.setTransitionType(DialogTransition.CENTER);
		this.excommunicationDialog.setDialogContainer(this.getStackPane());
		this.excommunicationDialog.setOverlayClose(false);
		this.excommunicationDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.servantsDialog);
		this.servantsDialog.setTransitionType(DialogTransition.CENTER);
		this.servantsDialog.setDialogContainer(this.getStackPane());
		this.servantsDialog.setOverlayClose(false);
		this.servantsDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.pickDevelopmentCardDialog);
		this.pickDevelopmentCardDialog.setTransitionType(DialogTransition.CENTER);
		this.pickDevelopmentCardDialog.setDialogContainer(this.getStackPane());
		this.pickDevelopmentCardDialog.setOverlayClose(false);
		this.pickDevelopmentCardDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.expectedChooseLorenzoDeMediciLeaderDialog);
		this.expectedChooseLorenzoDeMediciLeaderDialog.setTransitionType(DialogTransition.CENTER);
		this.expectedChooseLorenzoDeMediciLeaderDialog.setDialogContainer(this.getStackPane());
		this.expectedChooseLorenzoDeMediciLeaderDialog.setOverlayClose(false);
		this.expectedChooseLorenzoDeMediciLeaderDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.expectedChooseRewardCouncilPrivilegeDialog);
		this.expectedChooseRewardCouncilPrivilegeDialog.setTransitionType(DialogTransition.CENTER);
		this.expectedChooseRewardCouncilPrivilegeDialog.setDialogContainer(this.getStackPane());
		this.expectedChooseRewardCouncilPrivilegeDialog.setOverlayClose(false);
		this.expectedChooseRewardCouncilPrivilegeDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.expectedChooseRewardServantsDialog);
		this.expectedChooseRewardServantsDialog.setTransitionType(DialogTransition.CENTER);
		this.expectedChooseRewardServantsDialog.setDialogContainer(this.getStackPane());
		this.expectedChooseRewardServantsDialog.setOverlayClose(false);
		this.expectedChooseRewardServantsDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.expectedChooseRewardPickDevelopmentCardDialog);
		this.expectedChooseRewardPickDevelopmentCardDialog.setTransitionType(DialogTransition.CENTER);
		this.expectedChooseRewardPickDevelopmentCardDialog.setDialogContainer(this.getStackPane());
		this.expectedChooseRewardPickDevelopmentCardDialog.setOverlayClose(false);
		this.expectedChooseRewardPickDevelopmentCardDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.expectedChooseRewardTemporaryModifierDialog);
		this.expectedChooseRewardTemporaryModifierDialog.setTransitionType(DialogTransition.CENTER);
		this.expectedChooseRewardTemporaryModifierDialog.setDialogContainer(this.getStackPane());
		this.expectedChooseRewardTemporaryModifierDialog.setOverlayClose(false);
		this.expectedChooseRewardTemporaryModifierDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.developmentCardDialog);
		this.developmentCardDialog.setTransitionType(DialogTransition.CENTER);
		this.developmentCardDialog.setDialogContainer(this.getStackPane());
		this.developmentCardDialogLayout.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.playersLeaderCardsDialog);
		this.playersLeaderCardsDialog.setTransitionType(DialogTransition.CENTER);
		this.playersLeaderCardsDialog.setDialogContainer(this.getStackPane());
		this.playersLeaderCardsDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.endGameDialog);
		this.endGameDialog.setTransitionType(DialogTransition.CENTER);
		this.endGameDialog.setDialogContainer(this.getStackPane());
		this.endGameDialog.setOverlayClose(false);
		this.endGameDialog.setPadding(new Insets(24, 24, 24, 24));
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
		this.gameBoard.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource("/images/game_board_5_players.png").toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
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
				this.playersLeaderCardsTabPane.getTabs().remove(tab.getValue());
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
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action.png", "Actions", true);
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
		this.getStage().sizeToScene();
		this.chatTabPane.setMaxHeight(this.chatTabPane.getHeight() - (this.rightVBox.getHeight() - this.gameBoard.getPrefHeight()));
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
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
			if (familyMemberType != FamilyMemberType.NEUTRAL) {
				Pane pane = new Pane();
				pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getFamilyMemberTypesTextures().get(familyMemberType)).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				pane.setPrefSize(64.0D, 64.0D);
				Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
				pane.setOnMouseClicked(event -> Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsChooseRewardTemporaryModifier(familyMemberType)));
				this.expectedChooseRewardTemporaryModifierDialogHBox.getChildren().add(pane);
			}
		}
		this.developmentCardDialogPane.setPrefWidth(this.building1.getWidth() * 4);
		this.developmentCardDialogPane.setPrefHeight(this.building1.getHeight() * 4);
		this.developmentCardDialogScrollPane.setPrefHeight(this.building1.getHeight() * 4);
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
		this.playersLeaderCardsTabPane.requestLayout();
		((StackPane) ((JFXRippler) ((AnchorPane) this.expectedChooseRewardPickDevelopmentCardDialogThirdRow.getChildrenUnmodifiable().get(1)).getChildren().get(0)).getChildren().get(0)).setPadding(new Insets(0.0D));
		this.expectedChooseRewardPickDevelopmentCardDialogThirdRow.getChildrenUnmodifiable().get(0).setTranslateX(10.0D);
		((StackPane) ((JFXRippler) ((AnchorPane) this.expectedChooseRewardPickDevelopmentCardDialogFourthRow.getChildrenUnmodifiable().get(1)).getChildren().get(0)).getChildren().get(0)).setPadding(new Insets(0.0D));
		this.expectedChooseRewardPickDevelopmentCardDialogFourthRow.getChildrenUnmodifiable().get(0).setTranslateX(10.0D);
		this.leaderCardsButton.setPrefWidth(((VBox) this.leaderCardsButton.getParent()).getWidth());
		this.servantsDialogAcceptButton.setPrefWidth(((VBox) this.servantsDialogAcceptButton.getParent()).getWidth());
		this.servantsDialogCancelButton.setPrefWidth(((VBox) this.servantsDialogCancelButton.getParent()).getWidth());
		this.dialogLayout.setPrefWidth(300.0D);
		this.dialogOkButton.setPrefWidth(((VBox) this.dialogOkButton.getParent()).getWidth());
		this.playersTabPane.getSelectionModel().select(this.playersTabs.get(GameStatus.getInstance().getOwnPlayerIndex()));
		this.getStage().setX(bounds.getWidth() / 2 - this.getStage().getWidth() / 2);
		this.getStage().setY(bounds.getHeight() / 2 - this.getStage().getHeight() / 2);
		Client.getInstance().setBackgroundMediaPlayer(new MediaPlayer(new Media(this.getClass().getResource("/sounds/background.mp3").toString())));
		Client.getInstance().getBackgroundMediaPlayer().setOnEndOfMedia(() -> Client.getInstance().getBackgroundMediaPlayer().seek(Duration.ZERO));
		Client.getInstance().getBackgroundMediaPlayer().play();
	}

	private void generateAvailableActions()
	{
		this.actionsVBox.getChildren().clear();
		JFXNodesList actionsNodesList = new JFXNodesList();
		JFXNodesList familyMemberActionNodesList = new JFXNodesList();
		JFXNodesList leaderCardsActionNodesList = new JFXNodesList();
		actionsNodesList.setSpacing(10.0D);
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action.png", "Actions", false);
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.COUNCIL_PALACE).isEmpty() || !GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD).isEmpty() || !GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.HARVEST).isEmpty() || !GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET).isEmpty() || !GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PRODUCTION_START).isEmpty()) {
			ControllerGame.setActionButton(familyMemberActionNodesList, "/images/icons/action_family_member.png", "Family Member Actions", false, () -> {
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
				List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(councilPalaceActionNodesList, "/images/icons/action_council_palace.png", "Council Palace", GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.COUNCIL_PALACE), new JFXNodesList[] { harvestActionNodesList, marketActionNodesList, pickDevelopmentCardActionNodesList, productionStartActionNodesList });
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
					ControllerGame.setActionButton(councilPalaceActionNodesList, "/images/icons/action_family_member_type_black.png", "Black Family Member", false, () -> this.showCouncilPalace(FamilyMemberType.BLACK));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
					ControllerGame.setActionButton(councilPalaceActionNodesList, "/images/icons/action_family_member_type_neutral.png", "Neutral Family Member", false, () -> this.showCouncilPalace(FamilyMemberType.NEUTRAL));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
					ControllerGame.setActionButton(councilPalaceActionNodesList, "/images/icons/action_family_member_type_orange.png", "Orange Family Member", false, () -> this.showCouncilPalace(FamilyMemberType.ORANGE));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
					ControllerGame.setActionButton(councilPalaceActionNodesList, "/images/icons/action_family_member_type_white.png", "White Family Member", false, () -> this.showCouncilPalace(FamilyMemberType.WHITE));
				}
				councilPalaceActionNodesList.setSpacing(10.0D);
				councilPalaceActionNodesList.setRotate(180.0D);
				familyMemberActionNodesList.addAnimatedNode(councilPalaceActionNodesList);
			}
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.HARVEST).isEmpty()) {
				List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(harvestActionNodesList, "/images/icons/action_harvest.png", "Harvest", GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.HARVEST), new JFXNodesList[] { councilPalaceActionNodesList, marketActionNodesList, pickDevelopmentCardActionNodesList, productionStartActionNodesList });
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
					ControllerGame.setActionButton(harvestActionNodesList, "/images/icons/action_family_member_type_black.png", "Black Family Member", false, () -> this.showHarvest(FamilyMemberType.BLACK));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
					ControllerGame.setActionButton(harvestActionNodesList, "/images/icons/action_family_member_type_neutral.png", "Neutral Family Member", false, () -> this.showHarvest(FamilyMemberType.NEUTRAL));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
					ControllerGame.setActionButton(harvestActionNodesList, "/images/icons/action_family_member_type_orange.png", "Orange Family Member", false, () -> this.showHarvest(FamilyMemberType.ORANGE));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
					ControllerGame.setActionButton(harvestActionNodesList, "/images/icons/action_family_member_type_white.png", "White Family Member", false, () -> this.showHarvest(FamilyMemberType.WHITE));
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
				ControllerGame.setActionButton(marketActionNodesList, "/images/icons/action_market.png", "Market", false, () -> {
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
					List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(market1NodesList, "/images/icons/action_market_1.png", "Market 1", mappedMarketSlots.get(MarketSlot.FIRST), new JFXNodesList[] { market2NodesList, market3NodesList, market4NodesList, market5NodesList, market6NodesList });
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
						ControllerGame.setActionButton(market1NodesList, "/images/icons/action_family_member_type_black.png", "Black Family Member", false, () -> this.showMarket(MarketSlot.FIRST, FamilyMemberType.BLACK));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
						ControllerGame.setActionButton(market1NodesList, "/images/icons/action_family_member_type_neutral.png", "Neutral Family Member", false, () -> this.showMarket(MarketSlot.FIRST, FamilyMemberType.NEUTRAL));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
						ControllerGame.setActionButton(market1NodesList, "/images/icons/action_family_member_type_orange.png", "Orange Family Member", false, () -> this.showMarket(MarketSlot.FIRST, FamilyMemberType.ORANGE));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
						ControllerGame.setActionButton(market1NodesList, "/images/icons/action_family_member_type_white.png", "White Family Member", false, () -> this.showMarket(MarketSlot.FIRST, FamilyMemberType.WHITE));
					}
					market1NodesList.setSpacing(10.0D);
					market1NodesList.setRotate(270.0D);
					marketActionNodesList.addAnimatedNode(market1NodesList);
				}
				if (!mappedMarketSlots.get(MarketSlot.SECOND).isEmpty()) {
					List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(market2NodesList, "/images/icons/action_market_2.png", "Market 2", mappedMarketSlots.get(MarketSlot.SECOND), new JFXNodesList[] { market1NodesList, market3NodesList, market4NodesList, market5NodesList, market6NodesList });
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
						ControllerGame.setActionButton(market2NodesList, "/images/icons/action_family_member_type_black.png", "Black Family Member", false, () -> this.showMarket(MarketSlot.SECOND, FamilyMemberType.BLACK));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
						ControllerGame.setActionButton(market2NodesList, "/images/icons/action_family_member_type_neutral.png", "Neutral Family Member", false, () -> this.showMarket(MarketSlot.SECOND, FamilyMemberType.NEUTRAL));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
						ControllerGame.setActionButton(market2NodesList, "/images/icons/action_family_member_type_orange.png", "Orange Family Member", false, () -> this.showMarket(MarketSlot.SECOND, FamilyMemberType.ORANGE));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
						ControllerGame.setActionButton(market2NodesList, "/images/icons/action_family_member_type_white.png", "White Family Member", false, () -> this.showMarket(MarketSlot.SECOND, FamilyMemberType.WHITE));
					}
					market2NodesList.setSpacing(10.0D);
					market2NodesList.setRotate(270.0D);
					marketActionNodesList.addAnimatedNode(market2NodesList);
				}
				if (!mappedMarketSlots.get(MarketSlot.THIRD).isEmpty()) {
					List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(market3NodesList, "/images/icons/action_market_3.png", "Market 3", mappedMarketSlots.get(MarketSlot.THIRD), new JFXNodesList[] { market1NodesList, market2NodesList, market4NodesList, market5NodesList, market6NodesList });
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
						ControllerGame.setActionButton(market3NodesList, "/images/icons/action_family_member_type_black.png", "Black Family Member", false, () -> this.showMarket(MarketSlot.THIRD, FamilyMemberType.BLACK));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
						ControllerGame.setActionButton(market3NodesList, "/images/icons/action_family_member_type_neutral.png", "Neutral Family Member", false, () -> this.showMarket(MarketSlot.THIRD, FamilyMemberType.NEUTRAL));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
						ControllerGame.setActionButton(market3NodesList, "/images/icons/action_family_member_type_orange.png", "Orange Family Member", false, () -> this.showMarket(MarketSlot.THIRD, FamilyMemberType.ORANGE));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
						ControllerGame.setActionButton(market3NodesList, "/images/icons/action_family_member_type_white.png", "White Family Member", false, () -> this.showMarket(MarketSlot.THIRD, FamilyMemberType.WHITE));
					}
					market3NodesList.setSpacing(10.0D);
					market3NodesList.setRotate(270.0D);
					marketActionNodesList.addAnimatedNode(market3NodesList);
				}
				if (!mappedMarketSlots.get(MarketSlot.FOURTH).isEmpty()) {
					List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(market4NodesList, "/images/icons/action_market_4.png", "Market 4", mappedMarketSlots.get(MarketSlot.FOURTH), new JFXNodesList[] { market1NodesList, market2NodesList, market3NodesList, market5NodesList, market6NodesList });
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
						ControllerGame.setActionButton(market4NodesList, "/images/icons/action_family_member_type_black.png", "Black Family Member", false, () -> this.showMarket(MarketSlot.FOURTH, FamilyMemberType.BLACK));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
						ControllerGame.setActionButton(market4NodesList, "/images/icons/action_family_member_type_neutral.png", "Neutral Family Member", false, () -> this.showMarket(MarketSlot.FOURTH, FamilyMemberType.NEUTRAL));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
						ControllerGame.setActionButton(market4NodesList, "/images/icons/action_family_member_type_orange.png", "Orange Family Member", false, () -> this.showMarket(MarketSlot.FOURTH, FamilyMemberType.ORANGE));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
						ControllerGame.setActionButton(market4NodesList, "/images/icons/action_family_member_type_white.png", "White Family Member", false, () -> this.showMarket(MarketSlot.FOURTH, FamilyMemberType.WHITE));
					}
					market4NodesList.setSpacing(10.0D);
					market4NodesList.setRotate(270.0D);
					marketActionNodesList.addAnimatedNode(market4NodesList);
				}
				if (!mappedMarketSlots.get(MarketSlot.FIFTH).isEmpty()) {
					List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(market5NodesList, "/images/icons/action_market_5.png", "Market 5", mappedMarketSlots.get(MarketSlot.FIFTH), new JFXNodesList[] { market1NodesList, market2NodesList, market3NodesList, market4NodesList, market6NodesList });
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
						ControllerGame.setActionButton(market5NodesList, "/images/icons/action_family_member_type_black.png", "Black Family Member", false, () -> this.showMarket(MarketSlot.FIFTH, FamilyMemberType.BLACK));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
						ControllerGame.setActionButton(market5NodesList, "/images/icons/action_family_member_type_neutral.png", "Neutral Family Member", false, () -> this.showMarket(MarketSlot.FIFTH, FamilyMemberType.NEUTRAL));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
						ControllerGame.setActionButton(market5NodesList, "/images/icons/action_family_member_type_orange.png", "Orange Family Member", false, () -> this.showMarket(MarketSlot.FIFTH, FamilyMemberType.ORANGE));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
						ControllerGame.setActionButton(market5NodesList, "/images/icons/action_family_member_type_white.png", "White Family Member", false, () -> this.showMarket(MarketSlot.FIFTH, FamilyMemberType.WHITE));
					}
					market5NodesList.setSpacing(10.0D);
					market5NodesList.setRotate(270.0D);
					marketActionNodesList.addAnimatedNode(market5NodesList);
				}
				if (!mappedMarketSlots.get(MarketSlot.SIXTH).isEmpty()) {
					List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(market6NodesList, "/images/icons/action_market_6.png", "Market 6", mappedMarketSlots.get(MarketSlot.SIXTH), new JFXNodesList[] { market1NodesList, market2NodesList, market3NodesList, market4NodesList, market5NodesList });
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
						ControllerGame.setActionButton(market6NodesList, "/images/icons/action_family_member_type_black.png", "Black Family Member", false, () -> this.showMarket(MarketSlot.SIXTH, FamilyMemberType.BLACK));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
						ControllerGame.setActionButton(market6NodesList, "/images/icons/action_family_member_type_neutral.png", "Neutral Family Member", false, () -> this.showMarket(MarketSlot.SIXTH, FamilyMemberType.NEUTRAL));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
						ControllerGame.setActionButton(market6NodesList, "/images/icons/action_family_member_type_orange.png", "Orange Family Member", false, () -> this.showMarket(MarketSlot.SIXTH, FamilyMemberType.ORANGE));
					}
					if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
						ControllerGame.setActionButton(market6NodesList, "/images/icons/action_family_member_type_white.png", "White Family Member", false, () -> this.showMarket(MarketSlot.SIXTH, FamilyMemberType.WHITE));
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
				List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(pickDevelopmentCardActionNodesList, "/images/icons/action_pick_development_card.png", "Pick Development Card", GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD), new JFXNodesList[] { councilPalaceActionNodesList, harvestActionNodesList, marketActionNodesList, productionStartActionNodesList });
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
					ControllerGame.setActionButton(pickDevelopmentCardActionNodesList, "/images/icons/action_family_member_type_black.png", "Black Family Member", false, () -> this.showPickDevelopmentCard(FamilyMemberType.BLACK));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
					ControllerGame.setActionButton(pickDevelopmentCardActionNodesList, "/images/icons/action_family_member_type_neutral.png", "Neutral Family Member", false, () -> this.showPickDevelopmentCard(FamilyMemberType.NEUTRAL));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
					ControllerGame.setActionButton(pickDevelopmentCardActionNodesList, "/images/icons/action_family_member_type_orange.png", "Orange Family Member", false, () -> this.showPickDevelopmentCard(FamilyMemberType.ORANGE));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
					ControllerGame.setActionButton(pickDevelopmentCardActionNodesList, "/images/icons/action_family_member_type_white.png", "White Family Member", false, () -> this.showPickDevelopmentCard(FamilyMemberType.WHITE));
				}
				pickDevelopmentCardActionNodesList.setSpacing(10.0D);
				pickDevelopmentCardActionNodesList.setRotate(180.0D);
				familyMemberActionNodesList.addAnimatedNode(pickDevelopmentCardActionNodesList);
			}
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PRODUCTION_START).isEmpty()) {
				List<FamilyMemberType> mappedFamilyMemberTypes = ControllerGame.mapFamilyMemberTypes(productionStartActionNodesList, "/images/icons/action_production_start.png", "Start Production", GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PRODUCTION_START), new JFXNodesList[] { councilPalaceActionNodesList, harvestActionNodesList, marketActionNodesList, pickDevelopmentCardActionNodesList });
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.BLACK)) {
					ControllerGame.setActionButton(productionStartActionNodesList, "/images/icons/action_family_member_type_black.png", "Black Family Member", false, () -> this.showProductionStart(FamilyMemberType.BLACK));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.NEUTRAL)) {
					ControllerGame.setActionButton(productionStartActionNodesList, "/images/icons/action_family_member_type_neutral.png", "Neutral Family Member", false, () -> this.showProductionStart(FamilyMemberType.NEUTRAL));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.ORANGE)) {
					ControllerGame.setActionButton(productionStartActionNodesList, "/images/icons/action_family_member_type_orange.png", "Orange Family Member", false, () -> this.showProductionStart(FamilyMemberType.ORANGE));
				}
				if (mappedFamilyMemberTypes.contains(FamilyMemberType.WHITE)) {
					ControllerGame.setActionButton(productionStartActionNodesList, "/images/icons/action_family_member_type_white.png", "White Family Member", false, () -> this.showProductionStart(FamilyMemberType.WHITE));
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
			ControllerGame.setActionButton(leaderCardsActionNodesList, "/images/icons/action_leader.png", "Leader Actions", false, () -> {
				if (WindowFactory.isNodesListExpanded(familyMemberActionNodesList)) {
					familyMemberActionNodesList.animateList();
				}
			});
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_ACTIVATE).isEmpty()) {
				ControllerGame.setActionButton(leaderCardsActionNodesList, "/images/icons/action_leader_activate.png", "Activate Leader", false, this::showLeaderActivate);
			}
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_DISCARD).isEmpty()) {
				ControllerGame.setActionButton(leaderCardsActionNodesList, "/images/icons/action_leader_discard.png", "Discard Leader", false, this::showLeaderDiscard);
			}
			if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_PLAY).isEmpty()) {
				ControllerGame.setActionButton(leaderCardsActionNodesList, "/images/icons/action_leader_play.png", "Play Leader", false, this::showLeaderPlay);
			}
			leaderCardsActionNodesList.setRotate(90.0D);
			actionsNodesList.addAnimatedNode(leaderCardsActionNodesList);
		}
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action_pass_turn.png", "Pass Turn", false, () -> Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsPassTurn()), true);
		actionsNodesList.setRotate(180.0D);
		this.actionsVBox.getChildren().add(actionsNodesList);
	}

	private static void setActionButton(JFXNodesList nodesList, String imagePath, String tooltipText, boolean disabled)
	{
		ControllerGame.setActionButton(nodesList, imagePath, tooltipText, disabled, null, false);
	}

	private static void setActionButton(JFXNodesList nodesList, String imagePath, String tooltipText, boolean disabled, Runnable runnable)
	{
		ControllerGame.setActionButton(nodesList, imagePath, tooltipText, disabled, runnable, false);
	}

	private static void setActionButton(JFXNodesList nodesList, String imagePath, String tooltipText, boolean disabled, Runnable runnable, boolean red)
	{
		JFXButton button = new JFXButton();
		if (disabled) {
			button.setDisable(true);
		}
		Tooltip tooltip = new Tooltip(tooltipText);
		WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
		WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
		button.setTooltip(tooltip);
		ImageView imageView = new ImageView(new Image(ControllerGame.class.getResource(imagePath).toString()));
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

	private static List<FamilyMemberType> mapFamilyMemberTypes(JFXNodesList nodesList, String imagePath, String tooltipText, List<AvailableAction> availableActions, JFXNodesList[] nodesLists)
	{
		ControllerGame.setActionButton(nodesList, imagePath, tooltipText, false, () -> {
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

	private void resetSelectedDevelopmentCard(Pane pane)
	{
		this.pickDevelopmentCardActionButton.setDisable(true);
		this.selectedDevelopmentCardType = null;
		this.selectedDevelopmentCardIndex = null;
		pane.setEffect(null);
		Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
	}

	private void resetDevelopmentCardsEffects()
	{
		for (CardType cardType : CardType.values()) {
			for (Pane otherPane : this.developmentCardsPanes.get(cardType).values()) {
				otherPane.setEffect(null);
				if (this.selectableDevelopmentCards.contains(otherPane)) {
					Utils.setEffect(otherPane, ControllerGame.MOUSE_OVER_EFFECT);
				}
			}
		}
	}

	private String getDevelopmentCardInformations(Pane pane)
	{
		if (this.developmentCardsBuildingIndexes.containsKey(pane)) {
			return GameStatus.getInstance().getDevelopmentCardsBuilding().get(this.developmentCardsBuildingIndexes.get(pane)).getInformations();
		} else if (this.developmentCardsCharacterIndexes.containsKey(pane)) {
			return GameStatus.getInstance().getDevelopmentCardsCharacter().get(this.developmentCardsCharacterIndexes.get(pane)).getInformations();
		} else if (this.developmentCardsTerritoryIndexes.containsKey(pane)) {
			return GameStatus.getInstance().getDevelopmentCardsTerritory().get(this.developmentCardsTerritoryIndexes.get(pane)).getInformations();
		} else if (this.developmentCardsVentureIndexes.containsKey(pane)) {
			return GameStatus.getInstance().getDevelopmentCardsVenture().get(this.developmentCardsVentureIndexes.get(pane)).getInformations();
		}
		return null;
	}

	private String getResourcesInformations(List<ResourceAmount> resourceAmounts)
	{
		StringBuilder stringBuilder = new StringBuilder();
		boolean firstLine = true;
		for (ResourceAmount resourceAmount : resourceAmounts) {
			if (!firstLine) {
				stringBuilder.append('\n');
			} else {
				firstLine = false;
			}
			stringBuilder.append("- ");
			stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
			stringBuilder.append(": ");
			stringBuilder.append(Integer.toString(resourceAmount.getAmount()));
		}
		return stringBuilder.toString();
	}

	public void setOwnTurn()
	{
		this.updateGame();
		this.generateAvailableActions();
		this.playersTabPane.getSelectionModel().select(this.playersTabs.get(GameStatus.getInstance().getOwnPlayerIndex()));
	}

	public void setOwnTurnExpectedAction(ExpectedAction expectedAction)
	{
		this.updateGame();
		ControllerGame.EXPECTED_ACTION_HANDLERS.get(expectedAction.getActionType()).execute(this, expectedAction);
	}

	public void setOtherTurn()
	{
		this.updateGame();
		this.playersTabPane.getSelectionModel().select(this.playersTabs.get(GameStatus.getInstance().getCurrentTurnPlayerIndex()));
		this.actionsVBox.getChildren().clear();
		JFXNodesList actionsNodesList = new JFXNodesList();
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action.png", "Actions", true);
		this.actionsVBox.getChildren().add(actionsNodesList);
	}

	private void updateGame()
	{
		this.dialog.close();
		this.leaderCardsDialog.close();
		this.excommunicationDialog.close();
		this.developmentCardDialog.close();
		this.playersLeaderCardsDialog.close();
		this.servantsDialog.close();
		this.pickDevelopmentCardDialog.close();
		this.expectedChooseLorenzoDeMediciLeaderDialog.close();
		this.expectedChooseRewardCouncilPrivilegeDialog.close();
		this.expectedChooseRewardServantsDialog.close();
		this.expectedChooseRewardPickDevelopmentCardDialog.close();
		this.expectedChooseRewardTemporaryModifierDialog.close();
		this.pickingDevelopmentCard = false;
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
			this.dices.get(dice.getKey()).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getDicesFamilyMemberTypesTextures().get(dice.getValue()).get(dice.getKey())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
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
		for (Pane pane : this.productionBigPositionsPanes.values()) {
			pane.setBackground(null);
		}
		for (Pane pane : this.councilPalacePositionsPanes.values()) {
			pane.setBackground(null);
		}
		for (Entry<Period, List<Pane>> ecommunicationTilePlayersPanes : this.excommunicationTilesPlayersPanes.entrySet()) {
			for (Pane pane : ecommunicationTilePlayersPanes.getValue()) {
				pane.setBackground(null);
			}
		}
		for (Pane pane : this.roundOrderPositionsPanes.values()) {
			pane.setBackground(null);
		}
		ColorAdjust greyScaleEffect = new ColorAdjust();
		greyScaleEffect.setSaturation(-1);
		for (Entry<Integer, PlayerData> playerData : GameStatus.getInstance().getCurrentPlayersData().entrySet()) {
			this.playersBoards.get(playerData.getKey()).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getPersonalBonusTiles().get(playerData.getValue().getPersonalBonusTile()).getPlayerBoardTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			for (Pane pane : this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.BUILDING)) {
				pane.setBackground(null);
				pane.setBorder(null);
			}
			for (int index = 0; index < playerData.getValue().getDevelopmentCardsBuilding().size(); index++) {
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.BUILDING).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsBuilding().get(playerData.getValue().getDevelopmentCardsBuilding().get(index)).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.BUILDING).get(index).setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
				this.developmentCardsBuildingIndexes.put(this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.BUILDING).get(index), playerData.getValue().getDevelopmentCardsBuilding().get(index));
			}
			for (Pane pane : this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.CHARACTER)) {
				pane.setBackground(null);
				pane.setBorder(null);
			}
			for (int index = 0; index < playerData.getValue().getDevelopmentCardsCharacter().size(); index++) {
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.CHARACTER).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsCharacter().get(playerData.getValue().getDevelopmentCardsCharacter().get(index)).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.CHARACTER).get(index).setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
				this.developmentCardsCharacterIndexes.put(this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.CHARACTER).get(index), playerData.getValue().getDevelopmentCardsCharacter().get(index));
			}
			for (Pane pane : this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.TERRITORY)) {
				pane.setBackground(null);
				pane.setBorder(null);
			}
			for (int index = 0; index < playerData.getValue().getDevelopmentCardsTerritory().size(); index++) {
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.TERRITORY).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsTerritory().get(playerData.getValue().getDevelopmentCardsTerritory().get(index)).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.TERRITORY).get(index).setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
				this.developmentCardsTerritoryIndexes.put(this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.TERRITORY).get(index), playerData.getValue().getDevelopmentCardsTerritory().get(index));
			}
			for (Pane pane : this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.VENTURE)) {
				pane.setBackground(null);
				pane.setBorder(null);
			}
			for (int index = 0; index < playerData.getValue().getDevelopmentCardsVenture().size(); index++) {
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.VENTURE).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsVenture().get(playerData.getValue().getDevelopmentCardsVenture().get(index)).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.VENTURE).get(index).setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
				this.developmentCardsVentureIndexes.put(this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.VENTURE).get(index), playerData.getValue().getDevelopmentCardsVenture().get(index));
			}
			for (Pane pane : this.playersLeaderCardsHand.get(playerData.getKey())) {
				pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource("/images/leader_cards/leader_card_background.png").toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			}
			for (Pane pane : this.playersLeaderCardsPlayed.get(playerData.getKey())) {
				pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource("/images/leader_cards/leader_card_background.png").toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				pane.setEffect(null);
			}
			int index = 0;
			for (Entry<Integer, Boolean> leaderCard : playerData.getValue().getLeaderCardsPlayed().entrySet()) {
				if (leaderCard.getValue()) {
					this.playersLeaderCardsPlayed.get(playerData.getKey()).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				} else {
					this.playersLeaderCardsPlayed.get(playerData.getKey()).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
					this.playersLeaderCardsPlayed.get(playerData.getKey()).get(index).setEffect(greyScaleEffect);
				}
				this.playersLeaderCardsHand.get(playerData.getKey()).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				Tooltip tooltip = new Tooltip(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getInformations());
				WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
				WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
				Tooltip.install(this.playersLeaderCardsPlayed.get(playerData.getKey()).get(index), tooltip);
				index++;
			}
			if (playerData.getKey() == GameStatus.getInstance().getOwnPlayerIndex()) {
				index = 0;
				for (int leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().keySet()) {
					this.playersLeaderCardsHand.get(playerData.getKey()).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
					Tooltip tooltip = new Tooltip(GameStatus.getInstance().getLeaderCards().get(leaderCard).getInformations());
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
			this.victoryPointsPanes.get(playerData.getValue().getResourceAmounts().get(ResourceType.VICTORY_POINT) % 100).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getPlayersPlaceholdersTextures().get(playerData.getValue().getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			this.militaryPointsPanes.get(playerData.getValue().getResourceAmounts().get(ResourceType.MILITARY_POINT)).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getPlayersPlaceholdersTextures().get(playerData.getValue().getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			this.faithPointsPanes.get(playerData.getValue().getResourceAmounts().get(ResourceType.FAITH_POINT)).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getPlayersPlaceholdersTextures().get(playerData.getValue().getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			if (GameStatus.getInstance().getCurrentPlayersData().size() == 5) {
				this.prestigePointsPanes.get(playerData.getValue().getResourceAmounts().get(ResourceType.PRESTIGE_POINT)).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getPlayersPlaceholdersTextures().get(playerData.getValue().getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			}
			for (Entry<FamilyMemberType, BoardPosition> familyMemberTypeBoardPositionEntry : playerData.getValue().getFamilyMembersPositions().entrySet()) {
				if (this.boardPositionsPanes.containsKey(familyMemberTypeBoardPositionEntry.getValue())) {
					this.boardPositionsPanes.get(familyMemberTypeBoardPositionEntry.getValue()).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getPlayersFamilyMemberTypesTextures().get(playerData.getValue().getColor()).get(familyMemberTypeBoardPositionEntry.getKey())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				} else if (familyMemberTypeBoardPositionEntry.getValue() == BoardPosition.HARVEST_BIG) {
					if (this.playersHarvestBigPositions.containsKey(playerData.getValue())) {
						this.harvestBigPositionsPanes.get(this.playersHarvestBigPositions.get(playerData.getValue())).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getPlayersFamilyMemberTypesTextures().get(playerData.getValue().getColor()).get(familyMemberTypeBoardPositionEntry.getKey())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
					} else {
						for (Entry<Integer, Pane> pane : this.harvestBigPositionsPanes.entrySet()) {
							if (!this.playersHarvestBigPositions.containsValue(pane.getKey())) {
								this.playersHarvestBigPositions.put(playerData.getValue(), pane.getKey());
								pane.getValue().setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getPlayersFamilyMemberTypesTextures().get(playerData.getValue().getColor()).get(familyMemberTypeBoardPositionEntry.getKey())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
								break;
							}
						}
					}
				} else if (familyMemberTypeBoardPositionEntry.getValue() == BoardPosition.PRODUCTION_BIG) {
					if (this.playersProductionBigPositions.containsKey(playerData.getValue())) {
						this.productionBigPositionsPanes.get(this.playersProductionBigPositions.get(playerData.getValue())).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getPlayersFamilyMemberTypesTextures().get(playerData.getValue().getColor()).get(familyMemberTypeBoardPositionEntry.getKey())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
					} else {
						for (Entry<Integer, Pane> pane : this.productionBigPositionsPanes.entrySet()) {
							if (!this.playersProductionBigPositions.containsValue(pane.getKey())) {
								this.playersProductionBigPositions.put(playerData.getValue(), pane.getKey());
								pane.getValue().setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getPlayersFamilyMemberTypesTextures().get(playerData.getValue().getColor()).get(familyMemberTypeBoardPositionEntry.getKey())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
								break;
							}
						}
					}
				}
			}
		}
		for (Entry<Period, List<Integer>> excommunicationTilePlayersPanes : GameStatus.getInstance().getCurrentExcommunicatedPlayers().entrySet()) {
			for (int index = 0; index < excommunicationTilePlayersPanes.getValue().size(); index++) {
				this.excommunicationTilesPlayersPanes.get(excommunicationTilePlayersPanes.getKey()).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getExcommunicationPlayersPlaceholdersTextures().get(GameStatus.getInstance().getCurrentPlayersData().get(excommunicationTilePlayersPanes.getValue().get(index)).getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			}
		}
		for (Entry<Integer, Integer> orderPosition : GameStatus.getInstance().getCurrentCouncilPalaceOrder().entrySet()) {
			this.councilPalacePositionsPanes.get(orderPosition.getKey()).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getPlayersPlaceholdersTextures().get(GameStatus.getInstance().getCurrentPlayersData().get(orderPosition.getValue()).getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
		}
		for (Entry<Integer, Integer> orderPosition : GameStatus.getInstance().getCurrentTurnOrder().entrySet()) {
			this.roundOrderPositionsPanes.get(orderPosition.getKey()).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(Utils.getPlayersPlaceholdersTextures().get(GameStatus.getInstance().getCurrentPlayersData().get(orderPosition.getValue()).getColor())).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
		}
		this.leaderCardsButton.setDisable(false);
	}

	public void showDialog(String message)
	{
		this.dialogLabel.setText(message);
		this.dialog.show();
	}

	public void showPersonalBonusTiles()
	{
		this.personalBonusTilesDialog.show();
		this.personalBonusTilesDialogHBox.getChildren().clear();
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
				stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
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
				stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
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
			this.personalBonusTilesDialogHBox.getChildren().add(pane);
		}
		this.personalBonusTilesDialogLayout.setPrefWidth(76.0D * this.ratio * this.personalBonusTilesDialogHBox.getChildren().size() + this.personalBonusTilesDialogHBox.getSpacing() * (this.personalBonusTilesDialogHBox.getChildren().size() - 1) + this.personalBonusTilesDialogLayout.getInsets().getLeft() + this.personalBonusTilesDialogLayout.getInsets().getRight());
		this.personalBonusTilesDialogLayout.setPrefHeight(650.0D * this.ratio + this.personalBonusTilesDialogLayout.getInsets().getTop() + this.personalBonusTilesDialogLayout.getInsets().getTop() + 20.0D);
		this.personalBonusTilesDialog.show();
	}

	public void showLeaderCards()
	{
		this.leaderCardsDialogHBox.getChildren().clear();
		for (Integer leaderCard : GameStatus.getInstance().getAvailableLeaderCards()) {
			Pane pane = new Pane();
			pane.setPrefWidth(this.territory1.getWidth() * 3);
			pane.setPrefHeight(this.territory1.getHeight() * 3);
			pane.setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			Tooltip tooltip = new Tooltip(GameStatus.getInstance().getLeaderCards().get(leaderCard).getInformations());
			WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
			WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
			Tooltip.install(pane, tooltip);
			Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
			pane.setOnMouseClicked(event -> Client.getInstance().getConnectionHandler().sendGameLeaderCardPlayerChoice(leaderCard));
			this.leaderCardsDialogHBox.getChildren().add(pane);
		}
		this.leaderCardsDialogLayout.setPrefWidth(this.territory1.getWidth() * 3 * this.leaderCardsDialogHBox.getChildren().size() + this.leaderCardsDialogHBox.getSpacing() * (this.leaderCardsDialogHBox.getChildren().size() - 1) + this.leaderCardsDialogLayout.getInsets().getLeft() + this.leaderCardsDialogLayout.getInsets().getRight());
		this.leaderCardsDialogLayout.setPrefHeight(this.territory1.getHeight() * 3 + this.leaderCardsDialogLayout.getInsets().getTop() + this.leaderCardsDialogLayout.getInsets().getTop() + 20.0D);
		this.leaderCardsDialog.show();
	}

	public void showExcommunicationOther()
	{
		this.playersTabPane.getSelectionModel().select(this.playersTabs.get(GameStatus.getInstance().getCurrentTurnPlayerIndex()));
		this.actionsVBox.getChildren().clear();
		JFXNodesList actionsNodesList = new JFXNodesList();
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action.png", "Actions", true);
		this.actionsVBox.getChildren().add(actionsNodesList);
	}

	public void showExcommunication(Period period)
	{
		this.playersTabPane.getSelectionModel().select(this.playersTabs.get(GameStatus.getInstance().getCurrentTurnPlayerIndex()));
		this.actionsVBox.getChildren().clear();
		JFXNodesList actionsNodesList = new JFXNodesList();
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action.png", "Actions", true);
		this.actionsVBox.getChildren().add(actionsNodesList);
		this.excommunicationDialogText.setText(GameStatus.getInstance().getExcommunicationTiles().get(GameStatus.getInstance().getCurrentExcommunicationTiles().get(period)).getModifier().replace("\n", " "));
		this.excommunicationDialogSupportButton.setPrefWidth(((VBox) this.excommunicationDialogSupportButton.getParent()).getWidth());
		this.excommunicationDialogDoNotSupportButton.setPrefWidth(((VBox) this.excommunicationDialogDoNotSupportButton.getParent()).getWidth());
		this.excommunicationDialog.show();
		new AudioClip(this.getClass().getResource("/sounds/excommunication.mp3").toString()).play();
	}

	private void showCouncilPalace(FamilyMemberType familyMemberType)
	{
		this.servantsDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		this.servantsDialogSlider.setValue(0);
		this.servantsDialogAcceptButton.setOnAction((event) -> {
			this.servantsDialog.close();
			Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsCouncilPalace(familyMemberType, (int) this.servantsDialogSlider.getValue()));
		});
		this.servantsDialog.show();
	}

	private void showHarvest(FamilyMemberType familyMemberType)
	{
		this.servantsDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		this.servantsDialogSlider.setValue(0);
		this.servantsDialogAcceptButton.setOnAction((event) -> {
			this.servantsDialog.close();
			Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsHarvest(familyMemberType, (int) this.servantsDialogSlider.getValue()));
		});
		this.servantsDialog.show();
	}

	private void showMarket(MarketSlot marketSlot, FamilyMemberType familyMemberType)
	{
		this.servantsDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		this.servantsDialogSlider.setValue(0);
		this.servantsDialogAcceptButton.setOnAction((event) -> {
			this.servantsDialog.close();
			Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsMarket(familyMemberType, (int) this.servantsDialogSlider.getValue(), marketSlot));
		});
		this.servantsDialog.show();
	}

	private void showPickDevelopmentCard(FamilyMemberType familyMemberType)
	{
		this.selectedDevelopmentCardType = null;
		this.selectedDevelopmentCardIndex = null;
		this.pickingDevelopmentCard = true;
		this.actionsVBox.getChildren().clear();
		JFXNodesList actionsNodesList = new JFXNodesList();
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action_pick_development_card.png", "Pick Development Card", false);
		this.pickDevelopmentCardActionButton = new JFXButton();
		this.pickDevelopmentCardActionButton.setDisable(true);
		Tooltip tooltip = new Tooltip("Accept");
		WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
		WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
		this.pickDevelopmentCardActionButton.setTooltip(tooltip);
		ImageView imageView = new ImageView(new Image(Client.class.getResource("/images/icons/action.png").toString()));
		this.pickDevelopmentCardActionButton.setGraphic(imageView);
		this.pickDevelopmentCardActionButton.getStyleClass().add("animated-option-button-green");
		this.pickDevelopmentCardActionButton.setOnMouseClicked(event -> {
			this.pickDevelopmentCardDialogAcceptButton.setDisable(true);
			this.pickDevelopmentCardDialogDiscountChoicesHBox.getChildren().clear();
			this.pickDevelopmentCardDialogResourceCostOptionsHBox.getChildren().clear();
			this.pickDevelopmentCardDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
			this.pickDevelopmentCardDialogSlider.setValue(0);
			this.selectedDiscountChoice.clear();
			this.selectedResourceCostOption = null;
			for (AvailableAction availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD)) {
				if (((AvailableActionPickDevelopmentCard) availableAction).getCardType() == this.selectedDevelopmentCardType && ((AvailableActionPickDevelopmentCard) availableAction).getRow() == GameStatus.getInstance().getDevelopmentCardRow(this.selectedDevelopmentCardType, this.selectedDevelopmentCardIndex)) {
					List<AnchorPane> discountChoicesAnchorPanes = new ArrayList<>();
					for (List<ResourceAmount> discountChoice : ((AvailableActionPickDevelopmentCard) availableAction).getDiscountChoices()) {
						AnchorPane anchorPane = new AnchorPane();
						discountChoicesAnchorPanes.add(anchorPane);
						Text text = new Text();
						text.setText(this.getResourcesInformations(discountChoice));
						anchorPane.getChildren().add(text);
						AnchorPane.setTopAnchor(text, 0.0);
						AnchorPane.setBottomAnchor(text, 0.0);
						AnchorPane.setLeftAnchor(text, 0.0);
						AnchorPane.setRightAnchor(text, 0.0);
						anchorPane.setOnMouseClicked(childEvent -> {
							this.selectedDiscountChoice.clear();
							this.selectedDiscountChoice.addAll(discountChoice);
							if (((AvailableActionPickDevelopmentCard) availableAction).getResourceCostOptions().isEmpty() || this.selectedResourceCostOption != null) {
								this.pickDevelopmentCardDialogAcceptButton.setDisable(false);
							}
							anchorPane.setEffect(ControllerGame.MOUSE_OVER_EFFECT);
							for (AnchorPane otherAnchorPane : discountChoicesAnchorPanes) {
								if (otherAnchorPane != anchorPane) {
									otherAnchorPane.setEffect(null);
								}
							}
						});
						this.pickDevelopmentCardDialogDiscountChoicesHBox.getChildren().add(anchorPane);
					}
					if (((AvailableActionPickDevelopmentCard) availableAction).getResourceCostOptions().isEmpty()) {
						this.pickDevelopmentCardDialogAcceptButton.setDisable(false);
					} else {
						List<AnchorPane> resourceCostOptionsAnchorPanes = new ArrayList<>();
						for (ResourceCostOption resourceCostOption : ((AvailableActionPickDevelopmentCard) availableAction).getResourceCostOptions()) {
							AnchorPane anchorPane = new AnchorPane();
							resourceCostOptionsAnchorPanes.add(anchorPane);
							Text text = new Text();
							StringBuilder stringBuilder = new StringBuilder();
							if (!resourceCostOption.getRequiredResources().isEmpty()) {
								stringBuilder.append("REQUIRED RESOURCES:\n");
							}
							stringBuilder.append(this.getResourcesInformations(resourceCostOption.getRequiredResources()));
							if (!resourceCostOption.getSpentResources().isEmpty()) {
								stringBuilder.append("SPENT RESOURCES:\n");
							}
							stringBuilder.append(this.getResourcesInformations(resourceCostOption.getSpentResources()));
							text.setText(stringBuilder.toString());
							anchorPane.getChildren().add(text);
							AnchorPane.setTopAnchor(text, 0.0);
							AnchorPane.setBottomAnchor(text, 0.0);
							AnchorPane.setLeftAnchor(text, 0.0);
							AnchorPane.setRightAnchor(text, 0.0);
							anchorPane.setOnMouseClicked(childEvent -> {
								this.selectedResourceCostOption = resourceCostOption;
								if (((AvailableActionPickDevelopmentCard) availableAction).getDiscountChoices().isEmpty() || !this.selectedDiscountChoice.isEmpty()) {
									this.pickDevelopmentCardDialogAcceptButton.setDisable(false);
								}
								anchorPane.setEffect(ControllerGame.MOUSE_OVER_EFFECT);
								for (AnchorPane otherAnchorPane : resourceCostOptionsAnchorPanes) {
									if (otherAnchorPane != anchorPane) {
										otherAnchorPane.setEffect(null);
									}
								}
							});
							this.pickDevelopmentCardDialogResourceCostOptionsHBox.getChildren().add(anchorPane);
						}
					}
					break;
				}
			}
			this.pickDevelopmentCardDialogAcceptButton.setOnAction(childEvent -> {
				this.pickDevelopmentCardDialog.close();
				Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsPickDevelopmentCard(familyMemberType, (int) this.pickDevelopmentCardDialogSlider.getValue(), this.selectedDevelopmentCardType, GameStatus.getInstance().getDevelopmentCardRow(this.selectedDevelopmentCardType, this.selectedDevelopmentCardIndex), this.selectedDiscountChoice, this.selectedResourceCostOption));
			});
			this.pickDevelopmentCardDialogAcceptButton.setPrefWidth(((VBox) this.pickDevelopmentCardDialogAcceptButton.getParent()).getWidth());
			this.pickDevelopmentCardDialogCancelButton.setPrefWidth(((VBox) this.pickDevelopmentCardDialogCancelButton.getParent()).getWidth());
			this.pickDevelopmentCardDialogAcceptButton.requestLayout();
			this.pickDevelopmentCardDialogCancelButton.requestLayout();
			this.pickDevelopmentCardDialog.show();
		});
		actionsNodesList.addAnimatedNode(this.pickDevelopmentCardActionButton);
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action_refuse_reward.png", "Cancel", false, () -> {
			this.pickingDevelopmentCard = false;
			for (CardType cardType : CardType.values()) {
				for (Pane pane : this.developmentCardsPanes.get(cardType).values()) {
					pane.setEffect(null);
					Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
				}
			}
			this.generateAvailableActions();
		}, true);
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

	private void showProductionStart(FamilyMemberType familyMemberType)
	{
		this.servantsDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		this.servantsDialogSlider.setValue(0);
		this.servantsDialogAcceptButton.setOnAction((event) -> {
			this.servantsDialog.close();
			Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsProductionStart(familyMemberType, (int) this.servantsDialogSlider.getValue()));
		});
		this.servantsDialog.show();
	}

	private void showLeaderActivate()
	{
		this.leaderCardsDialog.setOverlayClose(true);
		this.leaderCardsDialogHBox.getChildren().clear();
		for (Entry<Integer, Boolean> leaderCard : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getLeaderCardsPlayed().entrySet()) {
			if (!leaderCard.getValue()) {
				continue;
			}
			Pane pane = new Pane();
			pane.setPrefWidth(this.territory1.getWidth() * 3);
			pane.setPrefHeight(this.territory1.getHeight() * 3);
			pane.setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			Tooltip tooltip = new Tooltip(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getInformations());
			WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
			WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
			Tooltip.install(pane, tooltip);
			Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
			pane.setOnMouseClicked(event -> {
				this.leaderCardsDialog.close();
				Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsLeaderActivate(leaderCard.getKey()));
			});
			this.leaderCardsDialogHBox.getChildren().add(pane);
		}
		this.leaderCardsDialogLayout.setPrefWidth(this.territory1.getWidth() * 3 * this.leaderCardsDialogHBox.getChildren().size() + this.leaderCardsDialogHBox.getSpacing() * (this.leaderCardsDialogHBox.getChildren().size() - 1) + this.leaderCardsDialogLayout.getInsets().getLeft() + this.leaderCardsDialogLayout.getInsets().getRight());
		this.leaderCardsDialogLayout.setPrefHeight(this.territory1.getHeight() * 3 + this.leaderCardsDialogLayout.getInsets().getTop() + this.leaderCardsDialogLayout.getInsets().getTop() + 20.0D);
		this.leaderCardsDialog.show();
	}

	private void showLeaderDiscard()
	{
		this.leaderCardsDialog.setOverlayClose(true);
		this.leaderCardsDialogHBox.getChildren().clear();
		for (Integer leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().keySet()) {
			Pane pane = new Pane();
			pane.setPrefWidth(this.territory1.getWidth() * 3);
			pane.setPrefHeight(this.territory1.getHeight() * 3);
			pane.setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			Tooltip tooltip = new Tooltip(GameStatus.getInstance().getLeaderCards().get(leaderCard).getInformations());
			WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
			WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
			Tooltip.install(pane, tooltip);
			Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
			pane.setOnMouseClicked(event -> {
				this.leaderCardsDialog.close();
				Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsLeaderDiscard(leaderCard));
			});
			this.leaderCardsDialogHBox.getChildren().add(pane);
		}
		this.leaderCardsDialogLayout.setPrefWidth(this.territory1.getWidth() * 3 * this.leaderCardsDialogHBox.getChildren().size() + this.leaderCardsDialogHBox.getSpacing() * (this.leaderCardsDialogHBox.getChildren().size() - 1) + this.leaderCardsDialogLayout.getInsets().getLeft() + this.leaderCardsDialogLayout.getInsets().getRight());
		this.leaderCardsDialogLayout.setPrefHeight(this.territory1.getHeight() * 3 + this.leaderCardsDialogLayout.getInsets().getTop() + this.leaderCardsDialogLayout.getInsets().getTop() + 20.0D);
		this.leaderCardsDialog.show();
	}

	private void showLeaderPlay()
	{
		this.leaderCardsDialog.setOverlayClose(true);
		this.leaderCardsDialogHBox.getChildren().clear();
		for (Integer leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().keySet()) {
			Pane pane = new Pane();
			pane.setPrefWidth(this.territory1.getWidth() * 3);
			pane.setPrefHeight(this.territory1.getHeight() * 3);
			pane.setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
			pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			Tooltip tooltip = new Tooltip(GameStatus.getInstance().getLeaderCards().get(leaderCard).getInformations());
			WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
			WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
			Tooltip.install(pane, tooltip);
			Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
			pane.setOnMouseClicked(event -> {
				this.leaderCardsDialog.close();
				Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsLeaderPlay(leaderCard));
			});
			this.leaderCardsDialogHBox.getChildren().add(pane);
		}
		this.leaderCardsDialogLayout.setPrefWidth(this.territory1.getWidth() * 3 * this.leaderCardsDialogHBox.getChildren().size() + this.leaderCardsDialogHBox.getSpacing() * (this.leaderCardsDialogHBox.getChildren().size() - 1) + this.leaderCardsDialogLayout.getInsets().getLeft() + this.leaderCardsDialogLayout.getInsets().getRight());
		this.leaderCardsDialogLayout.setPrefHeight(this.territory1.getHeight() * 3 + this.leaderCardsDialogLayout.getInsets().getTop() + this.leaderCardsDialogLayout.getInsets().getTop() + 20.0D);
		this.leaderCardsDialog.show();
	}

	private void showExpectedChooseLorenzoDeMediciLeader(ExpectedActionChooseLorenzoDeMediciLeader expectedAction)
	{
		this.expectedChooseLorenzoDeMediciLeaderDialogVBox.getChildren().clear();
		int maximumCards = 1;
		for (Entry<Integer, List<Integer>> availableLeaderCards : expectedAction.getAvailableLeaderCards().entrySet()) {
			HBox hBox = new HBox();
			hBox.setAlignment(Pos.CENTER);
			hBox.setSpacing(20.0D);
			int leaderCardsCount = 0;
			for (Integer leaderCard : availableLeaderCards.getValue()) {
				Pane pane = new Pane();
				pane.setPrefWidth(this.territory1.getWidth() * 2);
				pane.setPrefHeight(this.territory1.getHeight() * 2);
				pane.setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0D))));
				pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCard).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
				Tooltip tooltip = new Tooltip(GameStatus.getInstance().getLeaderCards().get(leaderCard).getInformations());
				WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
				WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
				Tooltip.install(pane, tooltip);
				Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
				pane.setOnMouseClicked(event -> Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsChooseLorenzoDeMediciLeader(leaderCard)));
				hBox.getChildren().add(pane);
				leaderCardsCount++;
			}
			if (leaderCardsCount > maximumCards) {
				maximumCards = leaderCardsCount;
			}
			this.expectedChooseLorenzoDeMediciLeaderDialogVBox.getChildren().add(hBox);
		}
		this.expectedChooseLorenzoDeMediciLeaderDialogLayout.setPrefWidth(this.territory1.getWidth() * 2 * maximumCards + 20.0D * (maximumCards - 1) + this.expectedChooseLorenzoDeMediciLeaderDialogLayout.getInsets().getLeft() + this.expectedChooseLorenzoDeMediciLeaderDialogLayout.getInsets().getRight());
		this.expectedChooseLorenzoDeMediciLeaderDialogLayout.setPrefHeight(this.territory1.getHeight() * 2 + this.expectedChooseLorenzoDeMediciLeaderDialogLayout.getInsets().getTop() + this.expectedChooseLorenzoDeMediciLeaderDialogLayout.getInsets().getTop() + 20.0D);
		this.expectedChooseLorenzoDeMediciLeaderDialog.show();
	}

	private void showExpectedChooseRewardCouncilPrivilege(ExpectedActionChooseRewardCouncilPrivilege expectedAction)
	{
		this.expectedChooseRewardCouncilPrivilegeDialogLabel.setText("Choose " + expectedAction.getCouncilPrivilegesNumber() + " different Council Privilege rewards.");
		Map<Integer, Integer> selectedCouncilPrivilegesRewards = new HashMap<>();
		for (int councilPrivilegeIndex = 0; councilPrivilegeIndex < expectedAction.getCouncilPrivilegesNumber(); councilPrivilegeIndex++) {
			HBox hBox = new HBox();
			List<AnchorPane> councilPalaceRewardsAnchorPanes = new ArrayList<>();
			for (Entry<Integer, List<ResourceAmount>> councilPalaceReward : GameStatus.getInstance().getCurrentCouncilPrivilegeRewards().entrySet()) {
				AnchorPane anchorPane = new AnchorPane();
				councilPalaceRewardsAnchorPanes.add(anchorPane);
				Text text = new Text();
				text.setText(this.getResourcesInformations(councilPalaceReward.getValue()));
				anchorPane.getChildren().add(text);
				int currentCouncilPrivilegeIndex = councilPrivilegeIndex;
				AnchorPane.setTopAnchor(text, 0.0);
				AnchorPane.setBottomAnchor(text, 0.0);
				AnchorPane.setLeftAnchor(text, 0.0);
				AnchorPane.setRightAnchor(text, 0.0);
				anchorPane.setOnMouseClicked(childEvent -> {
					selectedCouncilPrivilegesRewards.put(currentCouncilPrivilegeIndex, councilPalaceReward.getKey());
					anchorPane.setEffect(ControllerGame.MOUSE_OVER_EFFECT);
					for (AnchorPane otherAnchorPane : councilPalaceRewardsAnchorPanes) {
						if (otherAnchorPane != anchorPane) {
							otherAnchorPane.setEffect(null);
						}
					}
				});
				hBox.getChildren().add(anchorPane);
				anchorPane.getChildren().add(text);
				hBox.setAlignment(Pos.CENTER);
				hBox.setSpacing(20.0D);
				hBox.setPadding(new Insets(20.0D, 20.0D, 20.0D, 20.0D));
			}
			this.expectedChooseRewardCouncilPrivilegeDialogPane.getChildren().add(hBox);
		}
		this.expectedChooseRewardCouncilPrivilegeDialogAcceptButton.setOnAction(childEvent -> Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsChooseRewardCouncilPrivilege(new ArrayList<>(selectedCouncilPrivilegesRewards.values()))));
		this.expectedChooseRewardCouncilPrivilegeDialogAcceptButton.setPrefWidth(((VBox) this.expectedChooseRewardCouncilPrivilegeDialogAcceptButton.getParent()).getWidth());
		this.expectedChooseRewardCouncilPrivilegeDialogAcceptButton.requestLayout();
		this.expectedChooseRewardCouncilPrivilegeDialog.show();
	}

	private void showExpectedChooseRewardHarvest(ExpectedActionChooseRewardHarvest expectedAction)
	{
		this.expectedChooseRewardServantsDialogLabel.setText("Choose the servants to spend for a bonus Harvest action of value " + expectedAction.getValue() + ".");
		this.expectedChooseRewardServantsDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		this.expectedChooseRewardServantsDialogSlider.setValue(0);
		this.expectedChooseRewardServantsDialogAcceptButton.setOnAction((event) -> Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsChooseRewardHarvest((int) this.servantsDialogSlider.getValue())));
		this.expectedChooseRewardServantsDialogAcceptButton.setPrefWidth(((VBox) this.expectedChooseRewardServantsDialogAcceptButton.getParent()).getWidth());
		this.expectedChooseRewardServantsDialog.show();
	}

	private void showExpectedChooseRewardPickDevelopmentCard(ExpectedActionChooseRewardPickDevelopmentCard expectedAction)
	{
		this.selectedDevelopmentCardType = null;
		this.selectedDevelopmentCardIndex = null;
		this.pickingDevelopmentCard = true;
		this.actionsVBox.getChildren().clear();
		JFXNodesList actionsNodesList = new JFXNodesList();
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action_pick_development_card.png", "Pick Development Card", false);
		this.pickDevelopmentCardActionButton = new JFXButton();
		this.pickDevelopmentCardActionButton.setDisable(true);
		Tooltip tooltip = new Tooltip("Accept");
		WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
		WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
		this.pickDevelopmentCardActionButton.setTooltip(tooltip);
		ImageView imageView = new ImageView(new Image(Client.class.getResource("/images/icons/action.png").toString()));
		this.pickDevelopmentCardActionButton.setGraphic(imageView);
		this.pickDevelopmentCardActionButton.getStyleClass().add("animated-option-button-green");
		this.pickDevelopmentCardActionButton.setOnMouseClicked(event -> {
			this.expectedChooseRewardPickDevelopmentCardDialogAcceptButton.setDisable(true);
			this.expectedChooseRewardPickDevelopmentCardDialogInstantDiscountChoicesHBox.getChildren().clear();
			this.expectedChooseRewardPickDevelopmentCardDialogDiscountChoicesHBox.getChildren().clear();
			this.expectedChooseRewardPickDevelopmentCardDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
			this.expectedChooseRewardPickDevelopmentCardDialogSlider.setValue(0);
			this.expectedChooseRewardPickDevelopmentCardDialogFourthRow.setSelected(true);
			this.selectedInstantRewardRow = Row.FOURTH;
			this.selectedInstantDiscountChoice.clear();
			this.selectedDiscountChoice.clear();
			this.selectedResourceCostOption = null;
			for (AvailableAction availableAction : expectedAction.getAvailableActions()) {
				if (((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType() == this.selectedDevelopmentCardType && ((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getRow() == GameStatus.getInstance().getDevelopmentCardRow(this.selectedDevelopmentCardType, this.selectedDevelopmentCardIndex)) {
					List<AnchorPane> instantDiscountChoicesAnchorPanes = new ArrayList<>();
					for (List<ResourceAmount> instantDiscountChoice : ((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getInstantDiscountChoices()) {
						AnchorPane anchorPane = new AnchorPane();
						instantDiscountChoicesAnchorPanes.add(anchorPane);
						Text text = new Text();
						text.setText(this.getResourcesInformations(instantDiscountChoice));
						anchorPane.getChildren().add(text);
						AnchorPane.setTopAnchor(text, 0.0);
						AnchorPane.setBottomAnchor(text, 0.0);
						AnchorPane.setLeftAnchor(text, 0.0);
						AnchorPane.setRightAnchor(text, 0.0);
						anchorPane.setOnMouseClicked(childEvent -> {
							this.selectedInstantDiscountChoice.clear();
							this.selectedInstantDiscountChoice.addAll(instantDiscountChoice);
							if ((((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getResourceCostOptions().isEmpty() || this.selectedResourceCostOption != null) && (((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getDiscountChoices().isEmpty() || !this.selectedDiscountChoice.isEmpty())) {
								this.expectedChooseRewardPickDevelopmentCardDialogAcceptButton.setDisable(false);
							}
							anchorPane.setEffect(ControllerGame.MOUSE_OVER_EFFECT);
							for (AnchorPane otherAnchorPane : instantDiscountChoicesAnchorPanes) {
								if (otherAnchorPane != anchorPane) {
									otherAnchorPane.setEffect(null);
								}
							}
						});
						this.expectedChooseRewardPickDevelopmentCardDialogInstantDiscountChoicesHBox.getChildren().add(anchorPane);
					}
					List<AnchorPane> discountChoicesAnchorPanes = new ArrayList<>();
					for (List<ResourceAmount> discountChoice : ((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getDiscountChoices()) {
						AnchorPane anchorPane = new AnchorPane();
						discountChoicesAnchorPanes.add(anchorPane);
						Text text = new Text();
						text.setText(this.getResourcesInformations(discountChoice));
						anchorPane.getChildren().add(text);
						AnchorPane.setTopAnchor(text, 0.0);
						AnchorPane.setBottomAnchor(text, 0.0);
						AnchorPane.setLeftAnchor(text, 0.0);
						AnchorPane.setRightAnchor(text, 0.0);
						anchorPane.setOnMouseClicked(childEvent -> {
							this.selectedDiscountChoice.clear();
							this.selectedDiscountChoice.addAll(discountChoice);
							if ((((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getResourceCostOptions().isEmpty() || this.selectedResourceCostOption != null) && (((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getInstantDiscountChoices().isEmpty() || !this.selectedInstantDiscountChoice.isEmpty())) {
								this.expectedChooseRewardPickDevelopmentCardDialogAcceptButton.setDisable(false);
							}
							anchorPane.setEffect(ControllerGame.MOUSE_OVER_EFFECT);
							for (AnchorPane otherAnchorPane : discountChoicesAnchorPanes) {
								if (otherAnchorPane != anchorPane) {
									otherAnchorPane.setEffect(null);
								}
							}
						});
						this.expectedChooseRewardPickDevelopmentCardDialogDiscountChoicesHBox.getChildren().add(anchorPane);
					}
					if (((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getResourceCostOptions().isEmpty()) {
						this.expectedChooseRewardPickDevelopmentCardDialogAcceptButton.setDisable(false);
					} else {
						List<AnchorPane> resourceCostOptionsAnchorPanes = new ArrayList<>();
						for (ResourceCostOption resourceCostOption : ((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getResourceCostOptions()) {
							AnchorPane anchorPane = new AnchorPane();
							resourceCostOptionsAnchorPanes.add(anchorPane);
							Text text = new Text();
							StringBuilder stringBuilder = new StringBuilder();
							if (!resourceCostOption.getRequiredResources().isEmpty()) {
								stringBuilder.append("REQUIRED RESOURCES:\n");
							}
							stringBuilder.append(this.getResourcesInformations(resourceCostOption.getRequiredResources()));
							if (!resourceCostOption.getSpentResources().isEmpty()) {
								stringBuilder.append("SPENT RESOURCES:\n");
							}
							stringBuilder.append(this.getResourcesInformations(resourceCostOption.getSpentResources()));
							text.setText(stringBuilder.toString());
							anchorPane.getChildren().add(text);
							AnchorPane.setTopAnchor(text, 0.0);
							AnchorPane.setBottomAnchor(text, 0.0);
							AnchorPane.setLeftAnchor(text, 0.0);
							AnchorPane.setRightAnchor(text, 0.0);
							anchorPane.setOnMouseClicked(childEvent -> {
								this.selectedResourceCostOption = resourceCostOption;
								if ((((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getInstantDiscountChoices().isEmpty() || !this.selectedInstantDiscountChoice.isEmpty()) && (((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getDiscountChoices().isEmpty())) {
									this.expectedChooseRewardPickDevelopmentCardDialogAcceptButton.setDisable(false);
								}
								anchorPane.setEffect(ControllerGame.MOUSE_OVER_EFFECT);
								for (AnchorPane otherAnchorPane : resourceCostOptionsAnchorPanes) {
									if (otherAnchorPane != anchorPane) {
										otherAnchorPane.setEffect(null);
									}
								}
							});
							this.expectedChooseRewardPickDevelopmentCardDialogResourceCostOptionsHBox.getChildren().add(anchorPane);
						}
					}
					break;
				}
			}
			this.expectedChooseRewardPickDevelopmentCardDialogAcceptButton.setOnAction(childEvent -> Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsChooseRewardPickDevelopmentCard((int) this.expectedChooseRewardPickDevelopmentCardDialogSlider.getValue(), this.selectedDevelopmentCardType, GameStatus.getInstance().getDevelopmentCardRow(this.selectedDevelopmentCardType, this.selectedDevelopmentCardIndex), this.selectedInstantRewardRow, this.selectedInstantDiscountChoice, this.selectedDiscountChoice, this.selectedResourceCostOption)));
			this.expectedChooseRewardPickDevelopmentCardDialogAcceptButton.setPrefWidth(((VBox) this.expectedChooseRewardPickDevelopmentCardDialogAcceptButton.getParent()).getWidth());
			this.expectedChooseRewardPickDevelopmentCardDialogCancelButton.setPrefWidth(((VBox) this.expectedChooseRewardPickDevelopmentCardDialogCancelButton.getParent()).getWidth());
			this.expectedChooseRewardPickDevelopmentCardDialogAcceptButton.requestLayout();
			this.expectedChooseRewardPickDevelopmentCardDialogCancelButton.requestLayout();
			this.expectedChooseRewardPickDevelopmentCardDialog.show();
		});
		actionsNodesList.addAnimatedNode(this.pickDevelopmentCardActionButton);
		ControllerGame.setActionButton(actionsNodesList, "/images/icons/action_refuse_reward.png", "Refuse Reward", false, () -> {
			this.pickingDevelopmentCard = false;
			for (CardType cardType : CardType.values()) {
				for (Pane pane : this.developmentCardsPanes.get(cardType).values()) {
					pane.setEffect(null);
					Utils.setEffect(pane, ControllerGame.MOUSE_OVER_EFFECT);
				}
			}
			Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsRefuseReward());
		}, true);
		actionsNodesList.setSpacing(10.0D);
		actionsNodesList.setRotate(180.0D);
		this.actionsVBox.getChildren().add(actionsNodesList);
		this.selectableDevelopmentCards.clear();
		for (AvailableActionChooseRewardPickDevelopmentCard availableActionChooseRewardPickDevelopmentCard : expectedAction.getAvailableActions()) {
			this.selectableDevelopmentCards.add(this.developmentCardsPanes.get(availableActionChooseRewardPickDevelopmentCard.getCardType()).get(availableActionChooseRewardPickDevelopmentCard.getRow()));
		}
		for (CardType cardType : CardType.values()) {
			for (Pane pane : this.developmentCardsPanes.get(cardType).values()) {
				if (!this.selectableDevelopmentCards.contains(pane)) {
					Utils.unsetEffect(pane);
				}
			}
		}
	}

	private void showExpectedChooseRewardProductionStart(ExpectedActionChooseRewardProductionStart expectedAction)
	{
		this.expectedChooseRewardServantsDialogLabel.setText("Choose the servants to spend for a bonus Production action of value " + expectedAction.getValue() + ".");
		this.expectedChooseRewardServantsDialogSlider.setMax(GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		this.expectedChooseRewardServantsDialogSlider.setValue(0);
		this.expectedChooseRewardServantsDialogAcceptButton.setOnAction((event) -> Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsChooseRewardProductionStart((int) this.servantsDialogSlider.getValue())));
		this.expectedChooseRewardServantsDialogAcceptButton.setPrefWidth(((VBox) this.expectedChooseRewardServantsDialogAcceptButton.getParent()).getWidth());
		this.expectedChooseRewardServantsDialog.show();
	}

	private void showExpectedProductionTrade(ExpectedActionProductionTrade expectedAction)
	{
	}

	private void showExpectedChooseRewardTemporaryModifier()
	{
		this.expectedChooseRewardTemporaryModifierDialog.show();
	}

	public void showEndGame(Map<Integer, Integer> playersScores, Map<Integer, Integer> playerIndexesVictoryPointsRecord)
	{
		if (new ArrayList<>(playersScores.keySet()).get(0) == GameStatus.getInstance().getOwnPlayerIndex()) {
			this.endGameDialogTitle.setText("VICTORY");
			this.endGameDialogTitle.setTextFill(Color.web("#2E7D32"));
		} else {
			this.endGameDialogTitle.setText("DEFEAT");
			this.endGameDialogTitle.setTextFill(Color.web("#B71C1C"));
		}
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10.0D);
		gridPane.setVgap(10.0D);
		ColumnConstraints columnConstraints1 = new ColumnConstraints();
		columnConstraints1.setPercentWidth(65.0D);
		gridPane.getColumnConstraints().add(columnConstraints1);
		ColumnConstraints columnConstraints2 = new ColumnConstraints();
		columnConstraints2.setPercentWidth(35.0D);
		columnConstraints2.setHalignment(HPos.RIGHT);
		gridPane.getColumnConstraints().add(columnConstraints2);
		int index = 1;
		for (Entry<Integer, Integer> playerScore : playersScores.entrySet()) {
			RowConstraints rowConstraints = new RowConstraints();
			gridPane.getRowConstraints().add(rowConstraints);
			Label label = new Label(index + " - " + GameStatus.getInstance().getCurrentPlayersData().get(playerScore.getKey()).getUsername() + " - " + playerScore.getValue() + " Points " + " (Record: " + playerIndexesVictoryPointsRecord.get(playerScore.getKey()) + " Points)");
			label.setFont(CommonUtils.ROBOTO_BOLD);
			if (playerScore.getKey() != GameStatus.getInstance().getOwnPlayerIndex()) {
				VBox vBox = new VBox();
				JFXButton button = new JFXButton("Send Good Game");
				button.setTextFill(Color.WHITE);
				button.setFont(CommonUtils.ROBOTO_BOLD);
				button.setStyle("-fx-background-color: #66BB6A;");
				button.setOnAction((event) -> {
					button.setDisable(true);
					Client.getInstance().getConnectionHandler().sendGoodGame(playerScore.getKey());
				});
				vBox.getChildren().add(button);
				gridPane.add(vBox, 1, index - 1);
			}
			rowConstraints.setPrefHeight(25.0D);
			gridPane.add(label, 0, index - 1);
			index++;
		}
		this.endGameDialogPlayersVBox.getChildren().add(gridPane);
		gridPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		this.endGameDialog.show();
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

	public JFXDialog getPersonalBonusTilesDialog()
	{
		return this.personalBonusTilesDialog;
	}

	public JFXDialog getLeaderCardsDialog()
	{
		return this.leaderCardsDialog;
	}
}
