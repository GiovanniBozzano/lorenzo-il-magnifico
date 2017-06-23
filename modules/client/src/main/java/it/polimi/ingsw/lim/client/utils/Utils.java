package it.polimi.ingsw.lim.client.utils;

import it.polimi.ingsw.lim.client.gui.ControllerGame;
import it.polimi.ingsw.lim.common.enums.Color;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Utils
{
	public static final String SCENE_AUTHENTICATION = "/fxml/SceneAuthentication.fxml";
	public static final String SCENE_CONNECTION = "/fxml/SceneConnection.fxml";
	public static final String SCENE_ROOM = "/fxml/SceneRoom.fxml";
	public static final String SCENE_GAME = "/fxml/SceneGame.fxml";
	public static final Map<Integer, Map<FamilyMemberType, String>> DICES_FAMILY_MEMBER_TYPES = new HashMap<>();

	static {
		Map<FamilyMemberType, String> dice1FamilyMemberTypes = new EnumMap<>(FamilyMemberType.class);
		dice1FamilyMemberTypes.put(FamilyMemberType.BLACK, "/images/dices/dice_1_black.png");
		dice1FamilyMemberTypes.put(FamilyMemberType.ORANGE, "/images/dices/dice_1_orange.png");
		dice1FamilyMemberTypes.put(FamilyMemberType.WHITE, "/images/dices/dice_1_white.png");
		Map<FamilyMemberType, String> dice2FamilyMemberTypes = new EnumMap<>(FamilyMemberType.class);
		dice2FamilyMemberTypes.put(FamilyMemberType.BLACK, "/images/dices/dice_2_black.png");
		dice2FamilyMemberTypes.put(FamilyMemberType.ORANGE, "/images/dices/dice_2_orange.png");
		dice2FamilyMemberTypes.put(FamilyMemberType.WHITE, "/images/dices/dice_2_white.png");
		Map<FamilyMemberType, String> dice3FamilyMemberTypes = new EnumMap<>(FamilyMemberType.class);
		dice3FamilyMemberTypes.put(FamilyMemberType.BLACK, "/images/dices/dice_3_black.png");
		dice3FamilyMemberTypes.put(FamilyMemberType.ORANGE, "/images/dices/dice_3_orange.png");
		dice3FamilyMemberTypes.put(FamilyMemberType.WHITE, "/images/dices/dice_3_white.png");
		Map<FamilyMemberType, String> dice4FamilyMemberTypes = new EnumMap<>(FamilyMemberType.class);
		dice4FamilyMemberTypes.put(FamilyMemberType.BLACK, "/images/dices/dice_4_black.png");
		dice4FamilyMemberTypes.put(FamilyMemberType.ORANGE, "/images/dices/dice_4_orange.png");
		dice4FamilyMemberTypes.put(FamilyMemberType.WHITE, "/images/dices/dice_4_white.png");
		Map<FamilyMemberType, String> dice5FamilyMemberTypes = new EnumMap<>(FamilyMemberType.class);
		dice5FamilyMemberTypes.put(FamilyMemberType.BLACK, "/images/dices/dice_5_black.png");
		dice5FamilyMemberTypes.put(FamilyMemberType.ORANGE, "/images/dices/dice_5_orange.png");
		dice5FamilyMemberTypes.put(FamilyMemberType.WHITE, "/images/dices/dice_5_white.png");
		Map<FamilyMemberType, String> dice6FamilyMemberTypes = new EnumMap<>(FamilyMemberType.class);
		dice6FamilyMemberTypes.put(FamilyMemberType.BLACK, "/images/dices/dice_6_black.png");
		dice6FamilyMemberTypes.put(FamilyMemberType.ORANGE, "/images/dices/dice_6_orange.png");
		dice6FamilyMemberTypes.put(FamilyMemberType.WHITE, "/images/dices/dice_6_white.png");
		Utils.DICES_FAMILY_MEMBER_TYPES.put(1, dice1FamilyMemberTypes);
		Utils.DICES_FAMILY_MEMBER_TYPES.put(2, dice2FamilyMemberTypes);
		Utils.DICES_FAMILY_MEMBER_TYPES.put(3, dice3FamilyMemberTypes);
		Utils.DICES_FAMILY_MEMBER_TYPES.put(4, dice4FamilyMemberTypes);
		Utils.DICES_FAMILY_MEMBER_TYPES.put(5, dice5FamilyMemberTypes);
		Utils.DICES_FAMILY_MEMBER_TYPES.put(6, dice6FamilyMemberTypes);
	}

	public static final Map<Color, Map<FamilyMemberType, String>> PLAYERS_FAMILY_MEMBER_TYPES = new EnumMap<>(Color.class);

	static {
		Map<FamilyMemberType, String> bluePlayerFamilyMemberTypes = new EnumMap<>(FamilyMemberType.class);
		bluePlayerFamilyMemberTypes.put(FamilyMemberType.BLACK, "/images/players/player_blue_family_member_type_black.png");
		bluePlayerFamilyMemberTypes.put(FamilyMemberType.NEUTRAL, "/images/players/player_blue_family_member_type_neutral.png");
		bluePlayerFamilyMemberTypes.put(FamilyMemberType.ORANGE, "/images/players/player_blue_family_member_type_orange.png");
		bluePlayerFamilyMemberTypes.put(FamilyMemberType.WHITE, "/images/players/player_blue_family_member_type_white.png");
		Map<FamilyMemberType, String> greenPlayerFamilyMemberTypes = new EnumMap<>(FamilyMemberType.class);
		greenPlayerFamilyMemberTypes.put(FamilyMemberType.BLACK, "/images/players/player_green_family_member_type_black.png");
		greenPlayerFamilyMemberTypes.put(FamilyMemberType.NEUTRAL, "/images/players/player_green_family_member_type_neutral.png");
		greenPlayerFamilyMemberTypes.put(FamilyMemberType.ORANGE, "/images/players/player_green_family_member_type_orange.png");
		greenPlayerFamilyMemberTypes.put(FamilyMemberType.WHITE, "/images/players/player_green_family_member_type_white.png");
		Map<FamilyMemberType, String> purplePlayerFamilyMemberTypes = new EnumMap<>(FamilyMemberType.class);
		purplePlayerFamilyMemberTypes.put(FamilyMemberType.BLACK, "/images/players/player_purple_family_member_type_black.png");
		purplePlayerFamilyMemberTypes.put(FamilyMemberType.NEUTRAL, "/images/players/player_purple_family_member_type_neutral.png");
		purplePlayerFamilyMemberTypes.put(FamilyMemberType.ORANGE, "/images/players/player_purple_family_member_type_orange.png");
		purplePlayerFamilyMemberTypes.put(FamilyMemberType.WHITE, "/images/players/player_purple_family_member_type_white.png");
		Map<FamilyMemberType, String> redPlayerFamilyMemberTypes = new EnumMap<>(FamilyMemberType.class);
		redPlayerFamilyMemberTypes.put(FamilyMemberType.BLACK, "/images/players/player_red_family_member_type_black.png");
		redPlayerFamilyMemberTypes.put(FamilyMemberType.NEUTRAL, "/images/players/player_red_family_member_type_neutral.png");
		redPlayerFamilyMemberTypes.put(FamilyMemberType.ORANGE, "/images/players/player_red_family_member_type_orange.png");
		redPlayerFamilyMemberTypes.put(FamilyMemberType.WHITE, "/images/players/player_red_family_member_type_white.png");
		Map<FamilyMemberType, String> yellowPlayerFamilyMemberTypes = new EnumMap<>(FamilyMemberType.class);
		yellowPlayerFamilyMemberTypes.put(FamilyMemberType.BLACK, "/images/players/player_yellow_family_member_type_black.png");
		yellowPlayerFamilyMemberTypes.put(FamilyMemberType.NEUTRAL, "/images/players/player_yellow_family_member_type_neutral.png");
		yellowPlayerFamilyMemberTypes.put(FamilyMemberType.ORANGE, "/images/players/player_yellow_family_member_type_orange.png");
		yellowPlayerFamilyMemberTypes.put(FamilyMemberType.WHITE, "/images/players/player_yellow_family_member_type_white.png");
		Utils.PLAYERS_FAMILY_MEMBER_TYPES.put(Color.BLUE, bluePlayerFamilyMemberTypes);
		Utils.PLAYERS_FAMILY_MEMBER_TYPES.put(Color.GREEN, greenPlayerFamilyMemberTypes);
		Utils.PLAYERS_FAMILY_MEMBER_TYPES.put(Color.PURPLE, purplePlayerFamilyMemberTypes);
		Utils.PLAYERS_FAMILY_MEMBER_TYPES.put(Color.RED, redPlayerFamilyMemberTypes);
		Utils.PLAYERS_FAMILY_MEMBER_TYPES.put(Color.YELLOW, yellowPlayerFamilyMemberTypes);
	}

	public static final Map<Color, String> PLAYERS_PLACEHOLDERS = new EnumMap<>(Color.class);

	static {
		Utils.PLAYERS_PLACEHOLDERS.put(Color.BLUE, "/images/players/player_blue.png");
		Utils.PLAYERS_PLACEHOLDERS.put(Color.GREEN, "/images/players/player_green.png");
		Utils.PLAYERS_PLACEHOLDERS.put(Color.PURPLE, "/images/players/player_purple.png");
		Utils.PLAYERS_PLACEHOLDERS.put(Color.RED, "/images/players/player_red.png");
		Utils.PLAYERS_PLACEHOLDERS.put(Color.YELLOW, "/images/players/player_yellow.png");
	}

	public static final Map<ResourceType, String> RESOURCES_NAMES = new EnumMap<>(ResourceType.class);

	static {
		Utils.RESOURCES_NAMES.put(ResourceType.COIN, "Coins");
		Utils.RESOURCES_NAMES.put(ResourceType.COUNCIL_PRIVILEGE, "Council privileges");
		Utils.RESOURCES_NAMES.put(ResourceType.FAITH_POINT, "Faith points");
		Utils.RESOURCES_NAMES.put(ResourceType.MILITARY_POINT, "Military points");
		Utils.RESOURCES_NAMES.put(ResourceType.PRESTIGE_POINT, "Prestige points");
		Utils.RESOURCES_NAMES.put(ResourceType.SERVANT, "Servants");
		Utils.RESOURCES_NAMES.put(ResourceType.STONE, "Stone");
		Utils.RESOURCES_NAMES.put(ResourceType.VICTORY_POINT, "Victory points");
		Utils.RESOURCES_NAMES.put(ResourceType.WOOD, "Wood");
	}

	private Utils()
	{
	}

	public static void resizeChildrenNode(Pane pane, double widthRatio, double heightRatio)
	{
		for (Node child : pane.getChildren()) {
			if (child instanceof Pane) {
				((Pane) child).setSnapToPixel(false);
				((Pane) child).setPrefWidth(((Pane) child).getPrefWidth() * widthRatio);
				((Pane) child).setPrefHeight(((Pane) child).getPrefHeight() * heightRatio);
			} else if (child instanceof Label) {
				((Label) child).setSnapToPixel(false);
				((Label) child).setPrefWidth(((Label) child).getPrefWidth() * widthRatio);
				((Label) child).setPrefHeight(((Label) child).getPrefHeight() * heightRatio);
			}
			child.setLayoutX(child.getLayoutX() * widthRatio);
			child.setLayoutY(child.getLayoutY() * heightRatio);
		}
	}

	public static void setEffect(Pane pane, Effect effect)
	{
		pane.setOnMouseEntered(event -> pane.setEffect(effect));
		pane.setOnMouseExited(event -> pane.effectProperty().set(null));
	}
}
