package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationChooseRewardTemporaryModifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerChooseRewardTemporaryModifier implements ICLIHandler
{
	private static final Map<Integer, FamilyMemberType> FAMILY_MEMBER_TYPES = new HashMap<>();

	static {
		CLIHandlerChooseRewardTemporaryModifier.FAMILY_MEMBER_TYPES.put(1, FamilyMemberType.BLACK);
		CLIHandlerChooseRewardTemporaryModifier.FAMILY_MEMBER_TYPES.put(2, FamilyMemberType.ORANGE);
		CLIHandlerChooseRewardTemporaryModifier.FAMILY_MEMBER_TYPES.put(3, FamilyMemberType.WHITE);
	}

	/**
	 * <p>Uses the {@link Integer} {@link FamilyMemberType} {@link Map} to get
	 * the available family members to perform an {@link
	 * ActionInformationChooseRewardTemporaryModifier} and prints them and the
	 * corresponding choosing indexes on screen.
	 */
	private static void showFamilyMemberTypes()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Family Member...");
		for (Entry<Integer, FamilyMemberType> familyMemberType : CLIHandlerChooseRewardTemporaryModifier.FAMILY_MEMBER_TYPES.entrySet()) {
			stringBuilder.append("\n\n");
			stringBuilder.append(familyMemberType.getKey());
			stringBuilder.append(' ');
			stringBuilder.append(familyMemberType.getValue().name());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	/**
	 * <p>Asks which {@link FamilyMemberType} the player wants to use to perform
	 * the action and sends the new {@link ActionInformationChooseRewardTemporaryModifier}
	 * with the chosen value.
	 */
	private static void askFamilyMemberType()
	{
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationChooseRewardTemporaryModifier(Utils.cliAskFamilyMemberType(CLIHandlerChooseRewardTemporaryModifier.FAMILY_MEMBER_TYPES)));
	}

	@Override
	public void execute()
	{
		CLIHandlerChooseRewardTemporaryModifier.showFamilyMemberTypes();
		CLIHandlerChooseRewardTemporaryModifier.askFamilyMemberType();
	}

	@Override
	public CLIHandlerChooseRewardTemporaryModifier newInstance()
	{
		return new CLIHandlerChooseRewardTemporaryModifier();
	}
}

