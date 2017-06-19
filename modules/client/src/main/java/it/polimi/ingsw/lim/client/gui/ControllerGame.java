package it.polimi.ingsw.lim.client.gui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.gui.CustomController;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;

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

	private static final Map<ResourceType, String> RESOURCES_NAMES = new EnumMap<>(ResourceType.class);

	static {
		ControllerGame.RESOURCES_NAMES.put(ResourceType.COIN, "Coins");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.COUNCIL_PRIVILEGE, "Council privileges");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.FAITH_POINT, "Faith points");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.MILITARY_POINT, "Military points");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.PRESTIGE_POINT, "Prestige points");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.SERVANT, "Servants");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.STONE, "Stone");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.VICTORY_POINT, "Victory points");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.WOOD, "Wood");
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
	@FXML private Pane roundPosition1;
	@FXML private Pane roundPosition2;
	@FXML private Pane roundPosition3;
	@FXML private Pane roundPosition4;
	@FXML private Pane roundPosition5;
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
	@FXML private JFXNodesList actionsNodesList;
	@FXML private JFXButton actionsButton;
	@FXML private JFXButton leaderCardsButton;
	@FXML private JFXDialog personalBonusTilesChoiceDialog;
	@FXML private JFXDialogLayout personalBonusTilesChoiceDialogLayout;
	@FXML private HBox personalBonusTilesChoiceDialogHBox;
	@FXML private JFXDialog leaderCardsChoiceDialog;
	@FXML private JFXDialogLayout leaderCardsChoiceDialogLayout;
	@FXML private HBox leaderCardsChoiceDialogHBox;
	@FXML private JFXDialog cardDialog;
	@FXML private JFXDialogLayout cardDialogLayout;
	@FXML private Pane cardDialogPane;
	@FXML private Text cardDialogText;
	@FXML private JFXDialog leaderCardsDialog;
	@FXML private JFXDialogLayout leaderCardsDialogLayout;
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
	private final Map<Integer, Tab> playersTabs = new HashMap<>();
	private final Map<Integer, Tab> leaderCardsTabs = new HashMap<>();
	private final Map<Period, Pane> excommunicationTilesPanes = new EnumMap<>(Period.class);
	private final Map<Integer, Pane> victoryPointsPanes = new HashMap<>();
	private final Map<Integer, Pane> militaryPointsPanes = new HashMap<>();
	private final Map<Integer, Pane> faithPointsPanes = new HashMap<>();
	private final Map<Integer, Pane> privilegePointsPanes = new HashMap<>();
	private final Map<Integer, Pane> councilPalacePositionsPanes = new HashMap<>();
	private final Map<Pane, Label> councilPalacePositionsLabels = new HashMap<>();
	private final Map<Integer, Pane> turnOrderPositionsPanes = new HashMap<>();
	private final List<Pane> harvestBigPositionsPanes = new LinkedList<>();
	private final Map<Pane, Label> harvestBigPositionsLabels = new HashMap<>();
	private final List<Pane> productionBigPositionsPanes = new LinkedList<>();
	private final Map<Pane, Label> productionBigPositionsLabels = new HashMap<>();
	private final Map<Row, Pane> developmentCardsBuildingPanes = new EnumMap<>(Row.class);
	private final Map<Row, Pane> developmentCardsCharacterPanes = new EnumMap<>(Row.class);
	private final Map<Row, Pane> developmentCardsTerritoryPanes = new EnumMap<>(Row.class);
	private final Map<Row, Pane> developmentCardsVenturePanes = new EnumMap<>(Row.class);
	private final Map<CardType, Map<Row, Pane>> developmentCardsPanes = new EnumMap<>(CardType.class);
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
	private final DropShadow borderGlow = new DropShadow();
	@FXML private HBox cardDialogHBox;

	@FXML
	private void boardDevelopmentCardPaneMouseClicked(MouseEvent event)
	{
		if (event.getButton() == MouseButton.SECONDARY) {
			if (((Pane) event.getSource()).getBackground() != null) {
				this.cardDialogPane.setBackground(((Pane) event.getSource()).getBackground());
				this.cardDialogLayout.setPrefWidth(this.territory1.getWidth() * 4 + this.cardDialogHBox.getSpacing() + this.cardDialogText.getLayoutBounds().getWidth() + this.cardDialogLayout.getInsets().getLeft() + this.cardDialogLayout.getInsets().getRight());
				this.cardDialogLayout.setPrefHeight(this.territory1.getHeight() * 4 + this.cardDialogLayout.getInsets().getTop() + this.cardDialogLayout.getInsets().getBottom() + 20.0D);
				this.cardDialog.show();
			}
		}
	}

	@FXML
	private void playerDevelopmentCardPaneMouseClicked(MouseEvent event)
	{
		if (event.getButton() == MouseButton.SECONDARY) {
			if (((Pane) event.getSource()).getBackground() != null) {
				this.cardDialogPane.setBackground(((Pane) event.getSource()).getBackground());
				this.cardDialog.show();
			}
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
		this.player1DevelopmentCards.put(CardType.BUILDING, Arrays.asList(this.player1Building1, this.player1Building2, this.player1Building3, this.player1Building4, this.player1Building5, this.player1Building6));
		this.player1DevelopmentCards.put(CardType.CHARACTER, Arrays.asList(this.player1Character1, this.player1Character2, this.player1Character3, this.player1Character4, this.player1Character5, this.player1Character6));
		this.player1DevelopmentCards.put(CardType.TERRITORY, Arrays.asList(this.player1Territory1, this.player1Territory2, this.player1Territory3, this.player1Territory4, this.player1Territory5, this.player1Territory6));
		this.player1DevelopmentCards.put(CardType.VENTURE, Arrays.asList(this.player1Venture1, this.player1Venture2, this.player1Venture3, this.player1Venture4, this.player1Venture5, this.player1Venture6));
		this.player2DevelopmentCards.put(CardType.BUILDING, Arrays.asList(this.player2Building1, this.player2Building2, this.player2Building3, this.player2Building4, this.player2Building5, this.player2Building6));
		this.player2DevelopmentCards.put(CardType.CHARACTER, Arrays.asList(this.player2Character1, this.player2Character2, this.player2Character3, this.player2Character4, this.player2Character5, this.player2Character6));
		this.player2DevelopmentCards.put(CardType.TERRITORY, Arrays.asList(this.player2Territory1, this.player2Territory2, this.player2Territory3, this.player2Territory4, this.player2Territory5, this.player2Territory6));
		this.player2DevelopmentCards.put(CardType.VENTURE, Arrays.asList(this.player2Venture1, this.player2Venture2, this.player2Venture3, this.player2Venture4, this.player2Venture5, this.player2Venture6));
		this.player2DevelopmentCards.put(CardType.BUILDING, Arrays.asList(this.player2Building1, this.player2Building2, this.player2Building3, this.player2Building4, this.player2Building5, this.player2Building6));
		this.player3DevelopmentCards.put(CardType.CHARACTER, Arrays.asList(this.player3Character1, this.player3Character2, this.player3Character3, this.player3Character4, this.player3Character5, this.player3Character6));
		this.player3DevelopmentCards.put(CardType.TERRITORY, Arrays.asList(this.player3Territory1, this.player3Territory2, this.player3Territory3, this.player3Territory4, this.player3Territory5, this.player3Territory6));
		this.player3DevelopmentCards.put(CardType.VENTURE, Arrays.asList(this.player3Venture1, this.player3Venture2, this.player3Venture3, this.player3Venture4, this.player3Venture5, this.player3Venture6));
		this.player3DevelopmentCards.put(CardType.BUILDING, Arrays.asList(this.player3Building1, this.player3Building2, this.player3Building3, this.player3Building4, this.player3Building5, this.player3Building6));
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
		this.getStackPane().getChildren().remove(this.cardDialog);
		this.cardDialog.setTransitionType(DialogTransition.CENTER);
		this.cardDialog.setDialogContainer(this.getStackPane());
		this.cardDialogLayout.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getChildren().remove(this.leaderCardsDialog);
		this.leaderCardsDialog.setTransitionType(DialogTransition.CENTER);
		this.leaderCardsDialog.setDialogContainer(this.getStackPane());
		this.leaderCardsDialog.setPadding(new Insets(24, 24, 24, 24));
		this.getStackPane().getStylesheets().add(Client.getInstance().getClass().getResource("/css/jfoenix-nodes-list-button.css").toExternalForm());
		this.borderGlow.setOffsetY(0.0D);
		this.borderGlow.setOffsetX(0.0D);
		this.borderGlow.setColor(Color.BLACK);
		this.borderGlow.setWidth(40.0D);
		this.borderGlow.setHeight(40.0D);
		Utils.setEffect(this.building1, this.borderGlow);
		Utils.setEffect(this.building2, this.borderGlow);
		Utils.setEffect(this.building3, this.borderGlow);
		Utils.setEffect(this.building4, this.borderGlow);
		Utils.setEffect(this.character1, this.borderGlow);
		Utils.setEffect(this.character2, this.borderGlow);
		Utils.setEffect(this.character3, this.borderGlow);
		Utils.setEffect(this.character4, this.borderGlow);
		Utils.setEffect(this.territory1, this.borderGlow);
		Utils.setEffect(this.territory2, this.borderGlow);
		Utils.setEffect(this.territory3, this.borderGlow);
		Utils.setEffect(this.territory4, this.borderGlow);
		Utils.setEffect(this.venture1, this.borderGlow);
		Utils.setEffect(this.venture2, this.borderGlow);
		Utils.setEffect(this.venture3, this.borderGlow);
		Utils.setEffect(this.venture4, this.borderGlow);
		Utils.setEffect(this.player1Building1, this.borderGlow);
		Utils.setEffect(this.player1Building2, this.borderGlow);
		Utils.setEffect(this.player1Building3, this.borderGlow);
		Utils.setEffect(this.player1Building4, this.borderGlow);
		Utils.setEffect(this.player1Building5, this.borderGlow);
		Utils.setEffect(this.player1Building6, this.borderGlow);
		Utils.setEffect(this.player1Character1, this.borderGlow);
		Utils.setEffect(this.player1Character2, this.borderGlow);
		Utils.setEffect(this.player1Character3, this.borderGlow);
		Utils.setEffect(this.player1Character4, this.borderGlow);
		Utils.setEffect(this.player1Character5, this.borderGlow);
		Utils.setEffect(this.player1Character6, this.borderGlow);
		Utils.setEffect(this.player1Territory1, this.borderGlow);
		Utils.setEffect(this.player1Territory2, this.borderGlow);
		Utils.setEffect(this.player1Territory3, this.borderGlow);
		Utils.setEffect(this.player1Territory4, this.borderGlow);
		Utils.setEffect(this.player1Territory5, this.borderGlow);
		Utils.setEffect(this.player1Territory6, this.borderGlow);
		Utils.setEffect(this.player1Venture1, this.borderGlow);
		Utils.setEffect(this.player1Venture2, this.borderGlow);
		Utils.setEffect(this.player1Venture3, this.borderGlow);
		Utils.setEffect(this.player1Venture4, this.borderGlow);
		Utils.setEffect(this.player1Venture5, this.borderGlow);
		Utils.setEffect(this.player1Venture6, this.borderGlow);
		Utils.setEffect(this.player2Building1, this.borderGlow);
		Utils.setEffect(this.player2Building2, this.borderGlow);
		Utils.setEffect(this.player2Building3, this.borderGlow);
		Utils.setEffect(this.player2Building4, this.borderGlow);
		Utils.setEffect(this.player2Building5, this.borderGlow);
		Utils.setEffect(this.player2Building6, this.borderGlow);
		Utils.setEffect(this.player2Character1, this.borderGlow);
		Utils.setEffect(this.player2Character2, this.borderGlow);
		Utils.setEffect(this.player2Character3, this.borderGlow);
		Utils.setEffect(this.player2Character4, this.borderGlow);
		Utils.setEffect(this.player2Character5, this.borderGlow);
		Utils.setEffect(this.player2Character6, this.borderGlow);
		Utils.setEffect(this.player2Territory1, this.borderGlow);
		Utils.setEffect(this.player2Territory2, this.borderGlow);
		Utils.setEffect(this.player2Territory3, this.borderGlow);
		Utils.setEffect(this.player2Territory4, this.borderGlow);
		Utils.setEffect(this.player2Territory5, this.borderGlow);
		Utils.setEffect(this.player2Territory6, this.borderGlow);
		Utils.setEffect(this.player2Venture1, this.borderGlow);
		Utils.setEffect(this.player2Venture2, this.borderGlow);
		Utils.setEffect(this.player2Venture3, this.borderGlow);
		Utils.setEffect(this.player2Venture4, this.borderGlow);
		Utils.setEffect(this.player2Venture5, this.borderGlow);
		Utils.setEffect(this.player2Venture6, this.borderGlow);
		Utils.setEffect(this.player3Building1, this.borderGlow);
		Utils.setEffect(this.player3Building2, this.borderGlow);
		Utils.setEffect(this.player3Building3, this.borderGlow);
		Utils.setEffect(this.player3Building4, this.borderGlow);
		Utils.setEffect(this.player3Building5, this.borderGlow);
		Utils.setEffect(this.player3Building6, this.borderGlow);
		Utils.setEffect(this.player3Character1, this.borderGlow);
		Utils.setEffect(this.player3Character2, this.borderGlow);
		Utils.setEffect(this.player3Character3, this.borderGlow);
		Utils.setEffect(this.player3Character4, this.borderGlow);
		Utils.setEffect(this.player3Character5, this.borderGlow);
		Utils.setEffect(this.player3Character6, this.borderGlow);
		Utils.setEffect(this.player3Territory1, this.borderGlow);
		Utils.setEffect(this.player3Territory2, this.borderGlow);
		Utils.setEffect(this.player3Territory3, this.borderGlow);
		Utils.setEffect(this.player3Territory4, this.borderGlow);
		Utils.setEffect(this.player3Territory5, this.borderGlow);
		Utils.setEffect(this.player3Territory6, this.borderGlow);
		Utils.setEffect(this.player3Venture1, this.borderGlow);
		Utils.setEffect(this.player3Venture2, this.borderGlow);
		Utils.setEffect(this.player3Venture3, this.borderGlow);
		Utils.setEffect(this.player3Venture4, this.borderGlow);
		Utils.setEffect(this.player3Venture5, this.borderGlow);
		Utils.setEffect(this.player3Venture6, this.borderGlow);
		Utils.setEffect(this.player4Building1, this.borderGlow);
		Utils.setEffect(this.player4Building2, this.borderGlow);
		Utils.setEffect(this.player4Building3, this.borderGlow);
		Utils.setEffect(this.player4Building4, this.borderGlow);
		Utils.setEffect(this.player4Building5, this.borderGlow);
		Utils.setEffect(this.player4Building6, this.borderGlow);
		Utils.setEffect(this.player4Character1, this.borderGlow);
		Utils.setEffect(this.player4Character2, this.borderGlow);
		Utils.setEffect(this.player4Character3, this.borderGlow);
		Utils.setEffect(this.player4Character4, this.borderGlow);
		Utils.setEffect(this.player4Character5, this.borderGlow);
		Utils.setEffect(this.player4Character6, this.borderGlow);
		Utils.setEffect(this.player4Territory1, this.borderGlow);
		Utils.setEffect(this.player4Territory2, this.borderGlow);
		Utils.setEffect(this.player4Territory3, this.borderGlow);
		Utils.setEffect(this.player4Territory4, this.borderGlow);
		Utils.setEffect(this.player4Territory5, this.borderGlow);
		Utils.setEffect(this.player4Territory6, this.borderGlow);
		Utils.setEffect(this.player4Venture1, this.borderGlow);
		Utils.setEffect(this.player4Venture2, this.borderGlow);
		Utils.setEffect(this.player4Venture3, this.borderGlow);
		Utils.setEffect(this.player4Venture4, this.borderGlow);
		Utils.setEffect(this.player4Venture5, this.borderGlow);
		Utils.setEffect(this.player4Venture6, this.borderGlow);
		Utils.setEffect(this.player5Building1, this.borderGlow);
		Utils.setEffect(this.player5Building2, this.borderGlow);
		Utils.setEffect(this.player5Building3, this.borderGlow);
		Utils.setEffect(this.player5Building4, this.borderGlow);
		Utils.setEffect(this.player5Building5, this.borderGlow);
		Utils.setEffect(this.player5Building6, this.borderGlow);
		Utils.setEffect(this.player5Character1, this.borderGlow);
		Utils.setEffect(this.player5Character2, this.borderGlow);
		Utils.setEffect(this.player5Character3, this.borderGlow);
		Utils.setEffect(this.player5Character4, this.borderGlow);
		Utils.setEffect(this.player5Character5, this.borderGlow);
		Utils.setEffect(this.player5Character6, this.borderGlow);
		Utils.setEffect(this.player5Territory1, this.borderGlow);
		Utils.setEffect(this.player5Territory2, this.borderGlow);
		Utils.setEffect(this.player5Territory3, this.borderGlow);
		Utils.setEffect(this.player5Territory4, this.borderGlow);
		Utils.setEffect(this.player5Territory5, this.borderGlow);
		Utils.setEffect(this.player5Territory6, this.borderGlow);
		Utils.setEffect(this.player5Venture1, this.borderGlow);
		Utils.setEffect(this.player5Venture2, this.borderGlow);
		Utils.setEffect(this.player5Venture3, this.borderGlow);
		Utils.setEffect(this.player5Venture4, this.borderGlow);
		Utils.setEffect(this.player5Venture5, this.borderGlow);
		Utils.setEffect(this.player5Venture6, this.borderGlow);
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
		this.actionsNodesList.addAnimatedNode(this.actionsButton);
	}

	@Override
	@PostConstruct
	public void setupGui()
	{
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double ratio = (bounds.getHeight() - bounds.getHeight() / 15) / this.getStage().getHeight();
		this.gameBoard.setPrefWidth(this.gameBoard.getWidth() * ratio);
		this.gameBoard.setPrefHeight(this.gameBoard.getHeight() * ratio);
		this.playersTabPane.setPrefWidth(this.playersTabPane.getWidth() * ratio);
		this.playersTabPane.setPrefHeight(this.playersTabPane.getHeight() * ratio);
		this.playerBoard1.setPrefWidth(this.playerBoard1.getWidth() * ratio);
		this.playerBoard1.setPrefHeight(this.playerBoard1.getHeight() * ratio);
		this.playerBoard1DevelopmentCardsVenture.setPrefWidth(this.playerBoard1DevelopmentCardsVenture.getWidth() * ratio);
		this.playerBoard1DevelopmentCardsVenture.setPrefHeight(this.playerBoard1DevelopmentCardsVenture.getHeight() * ratio);
		this.playerBoard1DevelopmentCardsCharacter.setPrefWidth(this.playerBoard1DevelopmentCardsCharacter.getWidth() * ratio);
		this.playerBoard1DevelopmentCardsCharacter.setPrefHeight(this.playerBoard1DevelopmentCardsCharacter.getHeight() * ratio);
		this.playerBoard2.setPrefWidth(this.playerBoard2.getWidth() * ratio);
		this.playerBoard2.setPrefHeight(this.playerBoard2.getHeight() * ratio);
		this.playerBoard2DevelopmentCardsVenture.setPrefWidth(this.playerBoard2DevelopmentCardsVenture.getWidth() * ratio);
		this.playerBoard2DevelopmentCardsVenture.setPrefHeight(this.playerBoard2DevelopmentCardsVenture.getHeight() * ratio);
		this.playerBoard2DevelopmentCardsCharacter.setPrefWidth(this.playerBoard2DevelopmentCardsCharacter.getWidth() * ratio);
		this.playerBoard2DevelopmentCardsCharacter.setPrefHeight(this.playerBoard2DevelopmentCardsCharacter.getHeight() * ratio);
		this.playerBoard3.setPrefWidth(this.playerBoard3.getWidth() * ratio);
		this.playerBoard3.setPrefHeight(this.playerBoard3.getHeight() * ratio);
		this.playerBoard3DevelopmentCardsVenture.setPrefWidth(this.playerBoard3DevelopmentCardsVenture.getWidth() * ratio);
		this.playerBoard3DevelopmentCardsVenture.setPrefHeight(this.playerBoard3DevelopmentCardsVenture.getHeight() * ratio);
		this.playerBoard3DevelopmentCardsCharacter.setPrefWidth(this.playerBoard3DevelopmentCardsCharacter.getWidth() * ratio);
		this.playerBoard3DevelopmentCardsCharacter.setPrefHeight(this.playerBoard3DevelopmentCardsCharacter.getHeight() * ratio);
		this.playerBoard4.setPrefWidth(this.playerBoard4.getWidth() * ratio);
		this.playerBoard4.setPrefHeight(this.playerBoard4.getHeight() * ratio);
		this.playerBoard4DevelopmentCardsVenture.setPrefWidth(this.playerBoard4DevelopmentCardsVenture.getWidth() * ratio);
		this.playerBoard4DevelopmentCardsVenture.setPrefHeight(this.playerBoard4DevelopmentCardsVenture.getHeight() * ratio);
		this.playerBoard4DevelopmentCardsCharacter.setPrefWidth(this.playerBoard4DevelopmentCardsCharacter.getWidth() * ratio);
		this.playerBoard4DevelopmentCardsCharacter.setPrefHeight(this.playerBoard4DevelopmentCardsCharacter.getHeight() * ratio);
		this.playerBoard5.setPrefWidth(this.playerBoard5.getWidth() * ratio);
		this.playerBoard5.setPrefHeight(this.playerBoard5.getHeight() * ratio);
		this.playerBoard5DevelopmentCardsVenture.setPrefWidth(this.playerBoard5DevelopmentCardsVenture.getWidth() * ratio);
		this.playerBoard5DevelopmentCardsVenture.setPrefHeight(this.playerBoard5DevelopmentCardsVenture.getHeight() * ratio);
		this.playerBoard5DevelopmentCardsCharacter.setPrefWidth(this.playerBoard5DevelopmentCardsCharacter.getWidth() * ratio);
		this.playerBoard5DevelopmentCardsCharacter.setPrefHeight(this.playerBoard5DevelopmentCardsCharacter.getHeight() * ratio);
		this.rightVBox.setMaxWidth(this.rightVBox.getWidth() * ratio);
		this.chatTabPane.setMaxHeight(this.chatTabPane.getHeight() - (this.rightVBox.getHeight() - this.gameBoard.getHeight()));
		this.getStage().sizeToScene();
		Utils.resizeChildrenNode(this.gameBoard, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard1, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard1DevelopmentCardsVenture, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard1DevelopmentCardsCharacter, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard2, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard2DevelopmentCardsVenture, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard2DevelopmentCardsCharacter, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard3, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard3DevelopmentCardsVenture, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard3DevelopmentCardsCharacter, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard4, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard4DevelopmentCardsVenture, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard4DevelopmentCardsCharacter, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard5, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard5DevelopmentCardsVenture, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard5DevelopmentCardsCharacter, ratio, ratio);
		this.cardDialogPane.setPrefWidth(this.territory1.getWidth() * 4);
		this.cardDialogPane.setPrefHeight(this.territory1.getHeight() * 4);
		for (Integer playerIndex : GameStatus.getInstance().getCurrentPlayersData().keySet()) {
			for (Pane pane : this.playersLeaderCardsHand.get(playerIndex)) {
				pane.setPrefWidth(this.territory1.getWidth() * 2.5);
				pane.setPrefHeight(this.territory1.getHeight() * 2.5);
			}
			for (Pane pane : this.playersLeaderCardsPlayed.get(playerIndex)) {
				pane.setPrefWidth(this.territory1.getWidth() * 2.5);
				pane.setPrefHeight(this.territory1.getHeight() * 2.5);
			}
		}
		this.leaderCardsTabPane.requestLayout();
		this.leaderCardsButton.setPrefWidth(((VBox) this.leaderCardsButton.getParent()).getWidth());
		this.getStage().setX(bounds.getWidth() / 2 - this.getStage().getWidth() / 2);
		this.getStage().setY(bounds.getHeight() / 2 - this.getStage().getHeight() / 2);
	}

	public void setOwnTurn()
	{
		this.updateGame();
		this.actionsButton.setDisable(false);
		this.gameLogTextArea.appendText((this.gameLogTextArea.getText().length() < 1 ? "" : '\n') + "Your turn");
	}

	public void setOtherTurn()
	{
		this.updateGame();
		this.actionsButton.setDisable(true);
		this.gameLogTextArea.appendText((this.gameLogTextArea.getText().length() < 1 ? "" : '\n') + GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getCurrentTurnPlayerIndex()).getUsername() + "'s turn");
	}

	private void updateGame()
	{
		for (Row row : Row.values()) {
			this.developmentCardsPanes.get(CardType.BUILDING).get(row).setBackground(null);
			Integer currentDevelopmentCardBuildingIndex = GameStatus.getInstance().getCurrentDevelopmentCardsBuilding().get(row);
			if (currentDevelopmentCardBuildingIndex != null) {
				this.developmentCardsPanes.get(CardType.BUILDING).get(row).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsBuilding().get(currentDevelopmentCardBuildingIndex).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			}
			this.developmentCardsPanes.get(CardType.CHARACTER).get(row).setBackground(null);
			Integer currentDevelopmentCardCharacterIndex = GameStatus.getInstance().getCurrentDevelopmentCardsCharacter().get(row);
			if (currentDevelopmentCardCharacterIndex != null) {
				this.developmentCardsPanes.get(CardType.CHARACTER).get(row).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsCharacter().get(currentDevelopmentCardCharacterIndex).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			}
			this.developmentCardsPanes.get(CardType.TERRITORY).get(row).setBackground(null);
			Integer currentDevelopmentCardTerritoryIndex = GameStatus.getInstance().getCurrentDevelopmentCardsTerritory().get(row);
			if (currentDevelopmentCardTerritoryIndex != null) {
				this.developmentCardsPanes.get(CardType.TERRITORY).get(row).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsTerritory().get(currentDevelopmentCardTerritoryIndex).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			}
			this.developmentCardsPanes.get(CardType.VENTURE).get(row).setBackground(null);
			Integer currentDevelopmentCardVentureIndex = GameStatus.getInstance().getCurrentDevelopmentCardsVenture().get(row);
			if (currentDevelopmentCardVentureIndex != null) {
				this.developmentCardsPanes.get(CardType.VENTURE).get(row).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsVenture().get(currentDevelopmentCardVentureIndex).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			}
		}
		for (Entry<Integer, PlayerData> playerData : GameStatus.getInstance().getCurrentPlayersData().entrySet()) {
			for (Pane pane : this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.BUILDING)) {
				pane.setBackground(null);
			}
			for (int index = 0; index < playerData.getValue().getDevelopmentCardsBuilding().size(); index++) {
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.BUILDING).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsBuilding().get(playerData.getValue().getDevelopmentCardsBuilding().get(index)).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			}
			for (Pane pane : this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.CHARACTER)) {
				pane.setBackground(null);
			}
			for (int index = 0; index < playerData.getValue().getDevelopmentCardsCharacter().size(); index++) {
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.CHARACTER).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsCharacter().get(playerData.getValue().getDevelopmentCardsCharacter().get(index)).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			}
			for (Pane pane : this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.TERRITORY)) {
				pane.setBackground(null);
			}
			for (int index = 0; index < playerData.getValue().getDevelopmentCardsTerritory().size(); index++) {
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.TERRITORY).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsTerritory().get(playerData.getValue().getDevelopmentCardsTerritory().get(index)).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			}
			for (Pane pane : this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.VENTURE)) {
				pane.setBackground(null);
			}
			for (int index = 0; index < playerData.getValue().getDevelopmentCardsVenture().size(); index++) {
				this.playersDevelopmentCards.get(playerData.getKey()).get(CardType.VENTURE).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getDevelopmentCardsVenture().get(playerData.getValue().getDevelopmentCardsVenture().get(index)).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
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
				index++;
			}
			if (playerData.getKey() == GameStatus.getInstance().getOwnPlayerIndex()) {
				index = 0;
				for (int leaderCardIndex : GameStatus.getInstance().getCurrentOwnLeaderCardsHand()) {
					this.playersLeaderCardsHand.get(playerData.getKey()).get(index).setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCardIndex).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
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
		}
		this.leaderCardsButton.setDisable(false);
	}

	public void showPersonalBonusTilesChoiceDialog()
	{
		for (Integer personalBonusTileIndex : GameStatus.getInstance().getAvailablePersonalBonusTiles()) {
			VBox vBox = new VBox();
			Text text = new Text();
			text.setFont(CommonUtils.ROBOTO_REGULAR);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Production activation cost: ");
			stringBuilder.append(GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTileIndex).getProductionActivationCost());
			if (!GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTileIndex).getProductionInstantResources().isEmpty()) {
				stringBuilder.append("\n\nProduction bonus resources:");
			}
			for (ResourceAmount resourceAmount : GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTileIndex).getProductionInstantResources()) {
				stringBuilder.append('\n');
				stringBuilder.append(ControllerGame.RESOURCES_NAMES.get(resourceAmount.getResourceType()));
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
				stringBuilder.append(ControllerGame.RESOURCES_NAMES.get(resourceAmount.getResourceType()));
				stringBuilder.append(": ");
				stringBuilder.append(resourceAmount.getAmount());
			}
			text.setText(stringBuilder.toString());
			vBox.getChildren().add(text);
			Utils.setEffect(vBox, this.borderGlow);
			vBox.setOnMouseClicked(event -> Client.getInstance().getConnectionHandler().sendGamePersonalBonusTilePlayerChoice(personalBonusTileIndex));
			this.personalBonusTilesChoiceDialogHBox.getChildren().add(vBox);
		}
		this.personalBonusTilesChoiceDialog.show();
	}

	public void closePersonalBonusTilesChoiceDialog()
	{
		this.personalBonusTilesChoiceDialog.close();
	}

	public void showLeaderCardsChoiceDialog()
	{
		this.leaderCardsChoiceDialogHBox.getChildren().clear();
		for (Integer leaderCardIndex : GameStatus.getInstance().getAvailableLeaderCards()) {
			Pane pane = new Pane();
			pane.setPrefWidth(this.territory1.getWidth() * 3);
			pane.setPrefHeight(this.territory1.getHeight() * 3);
			pane.setBackground(new Background(new BackgroundImage(new Image(this.getClass().getResource(GameStatus.getInstance().getLeaderCards().get(leaderCardIndex).getTexturePath()).toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
			Utils.setEffect(pane, this.borderGlow);
			pane.setOnMouseClicked(event -> Client.getInstance().getConnectionHandler().sendGameLeaderCardPlayerChoice(leaderCardIndex));
			this.leaderCardsChoiceDialogHBox.getChildren().add(pane);
		}
		this.leaderCardsChoiceDialogLayout.setPrefWidth(this.territory1.getWidth() * 3 * this.leaderCardsChoiceDialogHBox.getChildren().size() + this.leaderCardsChoiceDialogHBox.getSpacing() * (this.leaderCardsChoiceDialogHBox.getChildren().size() - 1) + this.leaderCardsChoiceDialogLayout.getInsets().getLeft() + this.leaderCardsChoiceDialogLayout.getInsets().getRight());
		this.leaderCardsChoiceDialogLayout.setPrefHeight(this.territory1.getHeight() * 3 + this.leaderCardsChoiceDialogLayout.getInsets().getTop() + this.leaderCardsChoiceDialogLayout.getInsets().getTop() + 20.0D);
		this.leaderCardsChoiceDialog.show();
	}

	public void closeLeaderCardsChoiceDialog()
	{
		this.leaderCardsChoiceDialog.close();
	}

	public TextArea getChatTextArea()
	{
		return this.chatTextArea;
	}

	public TextArea getGameLogTextArea()
	{
		return this.gameLogTextArea;
	}
}
