package it.polimi.ingsw.lim.server.game.cards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmountMultiplierCard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmountMultiplierResource;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionReward;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardHarvest;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardProduction;
import it.polimi.ingsw.lim.server.game.modifiers.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;

public class DevelopmentCardsDeck<T extends DevelopmentCard>
{
	private final List<T> firstPeriod;
	private final List<T> secondPeriod;
	private final List<T> thirdPeriod;
	private final Map<Period, List<T>> periods = new EnumMap<>(Period.class);

	private DevelopmentCardsDeck(List<T> firstPeriod, List<T> secondPeriod, List<T> thirdPeriod)
	{
		this.firstPeriod = new ArrayList<>(firstPeriod);
		this.secondPeriod = new ArrayList<>(secondPeriod);
		this.thirdPeriod = new ArrayList<>(thirdPeriod);
		this.periods.put(Period.FIRST, this.firstPeriod);
		this.periods.put(Period.SECOND, this.secondPeriod);
		this.periods.put(Period.THIRD, this.thirdPeriod);
	}

	public DevelopmentCardsDeck(DevelopmentCardsDeck<T> developmentCardsDeck)
	{
		this(developmentCardsDeck.periods.get(Period.FIRST), developmentCardsDeck.periods.get(Period.SECOND), developmentCardsDeck.periods.get(Period.THIRD));
	}

	public Map<Period, List<T>> getPeriods()
	{
		return this.periods;
	}

	public void shuffle()
	{
		Collections.shuffle(this.firstPeriod);
		Collections.shuffle(this.secondPeriod);
		Collections.shuffle(this.thirdPeriod);
	}

	@Override
	public String toString()
	{
		return Builder.GSON.toJson(this);
	}

	static class Builder<T extends DevelopmentCard>
	{
		private static final Map<Class<? extends DevelopmentCard>, Type> TYPE_TOKENS = new HashMap<>();

		static {
			Builder.TYPE_TOKENS.put(DevelopmentCardBuilding.class, new TypeToken<DevelopmentCardsDeck<DevelopmentCardBuilding>>()
			{
			}.getType());
			Builder.TYPE_TOKENS.put(DevelopmentCardCharacter.class, new TypeToken<DevelopmentCardsDeck<DevelopmentCardCharacter>>()
			{
			}.getType());
			Builder.TYPE_TOKENS.put(DevelopmentCardTerritory.class, new TypeToken<DevelopmentCardsDeck<DevelopmentCardTerritory>>()
			{
			}.getType());
			Builder.TYPE_TOKENS.put(DevelopmentCardVenture.class, new TypeToken<DevelopmentCardsDeck<DevelopmentCardVenture>>()
			{
			}.getType());
		}

		private static final RuntimeTypeAdapterFactory<ResourceAmount> RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT = RuntimeTypeAdapterFactory.of(ResourceAmount.class).registerSubtype(ResourceAmount.class, "STANDARD").registerSubtype(ResourceAmountMultiplierCard.class, "MULTIPLIER_CARD").registerSubtype(ResourceAmountMultiplierResource.class, "MULTIPLIER_RESOURCE");
		private static final RuntimeTypeAdapterFactory<Modifier> RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER = RuntimeTypeAdapterFactory.of(Modifier.class).registerSubtype(ModifierGetDevelopmentCard.class, "CARD").registerSubtype(ModifierStartHarvest.class, "HARVEST").registerSubtype(ModifierStartProduction.class, "PRODUCTION").registerSubtype(ModifierGetDevelopmentCardReward.class, "MALUS");
		private static final RuntimeTypeAdapterFactory<ActionReward> RUNTIME_TYPE_ADAPTER_FACTORY_EVENT = RuntimeTypeAdapterFactory.of(ActionReward.class).registerSubtype(ActionRewardGetDevelopmentCard.class, "CARD").registerSubtype(ActionRewardHarvest.class, "HARVEST").registerSubtype(ActionRewardProduction.class, "PRODUCTION");
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapterFactory(Builder.RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT).registerTypeAdapterFactory(Builder.RUNTIME_TYPE_ADAPTER_FACTORY_MODIFIER).registerTypeAdapterFactory(Builder.RUNTIME_TYPE_ADAPTER_FACTORY_EVENT);
		private static final Gson GSON = Builder.GSON_BUILDER.create();
		private final Class<T> clazz;
		private final String jsonFile;

		Builder(Class<T> clazz, String jsonFile)
		{
			this.clazz = clazz;
			this.jsonFile = jsonFile;
		}

		DevelopmentCardsDeck<T> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return Builder.GSON.fromJson(reader, Builder.TYPE_TOKENS.get(this.clazz));
			} catch (IOException exception) {
				Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return null;
		}
	}
}
