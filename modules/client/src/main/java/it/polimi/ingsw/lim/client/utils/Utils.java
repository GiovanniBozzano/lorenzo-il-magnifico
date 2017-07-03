package it.polimi.ingsw.lim.client.utils;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.Color;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private static final Map<Integer, String> BOARD_IMAGES = new HashMap<>();

	static {
		Utils.BOARD_IMAGES.put(2, "/images/game_board_2_players.png");
		Utils.BOARD_IMAGES.put(3, "/images/game_board_3_players.png");
		Utils.BOARD_IMAGES.put(4, "/images/game_board_4_players.png");
		Utils.BOARD_IMAGES.put(5, "/images/game_board_5_players.png");
	}

	private static final Map<Integer, Map<FamilyMemberType, String>> DICES_FAMILY_MEMBER_TYPES_TEXTURES = new HashMap<>();

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
		Utils.DICES_FAMILY_MEMBER_TYPES_TEXTURES.put(1, dice1FamilyMemberTypes);
		Utils.DICES_FAMILY_MEMBER_TYPES_TEXTURES.put(2, dice2FamilyMemberTypes);
		Utils.DICES_FAMILY_MEMBER_TYPES_TEXTURES.put(3, dice3FamilyMemberTypes);
		Utils.DICES_FAMILY_MEMBER_TYPES_TEXTURES.put(4, dice4FamilyMemberTypes);
		Utils.DICES_FAMILY_MEMBER_TYPES_TEXTURES.put(5, dice5FamilyMemberTypes);
		Utils.DICES_FAMILY_MEMBER_TYPES_TEXTURES.put(6, dice6FamilyMemberTypes);
	}

	private static final Map<FamilyMemberType, String> FAMILY_MEMBER_TYPES_TEXTURES = new EnumMap<>(FamilyMemberType.class);

	static {
		Utils.FAMILY_MEMBER_TYPES_TEXTURES.put(FamilyMemberType.BLACK, "/images/icons/family_member_type_black.png");
		Utils.FAMILY_MEMBER_TYPES_TEXTURES.put(FamilyMemberType.ORANGE, "/images/icons/family_member_type_orange.png");
		Utils.FAMILY_MEMBER_TYPES_TEXTURES.put(FamilyMemberType.WHITE, "/images/icons/family_member_type_white.png");
	}

	private static final Map<Color, Map<FamilyMemberType, String>> PLAYERS_FAMILY_MEMBER_TYPES_TEXTURES = new EnumMap<>(Color.class);

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
		Utils.PLAYERS_FAMILY_MEMBER_TYPES_TEXTURES.put(Color.BLUE, bluePlayerFamilyMemberTypes);
		Utils.PLAYERS_FAMILY_MEMBER_TYPES_TEXTURES.put(Color.GREEN, greenPlayerFamilyMemberTypes);
		Utils.PLAYERS_FAMILY_MEMBER_TYPES_TEXTURES.put(Color.PURPLE, purplePlayerFamilyMemberTypes);
		Utils.PLAYERS_FAMILY_MEMBER_TYPES_TEXTURES.put(Color.RED, redPlayerFamilyMemberTypes);
		Utils.PLAYERS_FAMILY_MEMBER_TYPES_TEXTURES.put(Color.YELLOW, yellowPlayerFamilyMemberTypes);
	}

	private static final Map<Color, String> PLAYERS_PLACEHOLDERS_TEXTURES = new EnumMap<>(Color.class);

	static {
		Utils.PLAYERS_PLACEHOLDERS_TEXTURES.put(Color.BLUE, "/images/players/player_blue.png");
		Utils.PLAYERS_PLACEHOLDERS_TEXTURES.put(Color.GREEN, "/images/players/player_green.png");
		Utils.PLAYERS_PLACEHOLDERS_TEXTURES.put(Color.PURPLE, "/images/players/player_purple.png");
		Utils.PLAYERS_PLACEHOLDERS_TEXTURES.put(Color.RED, "/images/players/player_red.png");
		Utils.PLAYERS_PLACEHOLDERS_TEXTURES.put(Color.YELLOW, "/images/players/player_yellow.png");
	}

	private static final Map<Color, String> EXCOMMUNICATION_PLAYERS_PLACEHOLDERS_TEXTURES = new EnumMap<>(Color.class);

	static {
		Utils.EXCOMMUNICATION_PLAYERS_PLACEHOLDERS_TEXTURES.put(Color.BLUE, "/images/players/player_blue_excommunication.png");
		Utils.EXCOMMUNICATION_PLAYERS_PLACEHOLDERS_TEXTURES.put(Color.GREEN, "/images/players/player_green_excommunication.png");
		Utils.EXCOMMUNICATION_PLAYERS_PLACEHOLDERS_TEXTURES.put(Color.PURPLE, "/images/players/player_purple_excommunication.png");
		Utils.EXCOMMUNICATION_PLAYERS_PLACEHOLDERS_TEXTURES.put(Color.RED, "/images/players/player_red_excommunication.png");
		Utils.EXCOMMUNICATION_PLAYERS_PLACEHOLDERS_TEXTURES.put(Color.YELLOW, "/images/players/player_yellow_excommunication.png");
	}

	private Utils()
	{
	}

	public static String createListElement(int index, String text)
	{
		return "\n=== " + index + " ========\n" + text;
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

	public static void unsetEffect(Pane pane)
	{
		pane.setOnMouseEntered(event -> {
			// Remove the effect.
		});
		pane.setOnMouseExited(event -> {
			// Remove the effect.
		});
	}

	public static void sendChatMessage(TextField textField, TextArea textArea)
	{
		String trimmedText = textField.getText().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		if (trimmedText.length() < 1) {
			return;
		}
		textField.clear();
		Client.getInstance().getConnectionHandler().sendChatMessage(trimmedText);
		if (textArea.getText().length() < 1) {
			textArea.appendText("[ME]: " + trimmedText);
		} else {
			textArea.appendText("\n[ME]: " + trimmedText);
		}
	}

	public static Map<Integer, String> getBoardImages()
	{
		return Utils.BOARD_IMAGES;
	}

	public static Map<Integer, Map<FamilyMemberType, String>> getDicesFamilyMemberTypesTextures()
	{
		return Utils.DICES_FAMILY_MEMBER_TYPES_TEXTURES;
	}

	public static Map<FamilyMemberType, String> getFamilyMemberTypesTextures()
	{
		return Utils.FAMILY_MEMBER_TYPES_TEXTURES;
	}

	public static Map<Color, Map<FamilyMemberType, String>> getPlayersFamilyMemberTypesTextures()
	{
		return Utils.PLAYERS_FAMILY_MEMBER_TYPES_TEXTURES;
	}

	public static Map<Color, String> getPlayersPlaceholdersTextures()
	{
		return Utils.PLAYERS_PLACEHOLDERS_TEXTURES;
	}

	public static Map<Color, String> getExcommunicationPlayersPlaceholdersTextures()
	{
		return Utils.EXCOMMUNICATION_PLAYERS_PLACEHOLDERS_TEXTURES;
	}
}
