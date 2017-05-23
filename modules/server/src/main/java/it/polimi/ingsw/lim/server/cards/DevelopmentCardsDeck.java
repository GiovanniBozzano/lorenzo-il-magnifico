package it.polimi.ingsw.lim.server.cards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.bonus.BonusAdditionCard;
import it.polimi.ingsw.lim.common.bonus.BonusAdditionWork;
import it.polimi.ingsw.lim.common.bonus.Malus;
import it.polimi.ingsw.lim.common.cards.*;
import it.polimi.ingsw.lim.common.events.Event;
import it.polimi.ingsw.lim.common.events.EventCard;
import it.polimi.ingsw.lim.common.events.EventWork;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.ResourceAmountMultiplierCard;
import it.polimi.ingsw.lim.common.utils.ResourceAmountMultiplierResource;
import it.polimi.ingsw.lim.server.Server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class DevelopmentCardsDeck<T extends Card>
{
	private final T[] firstPeriod;
	private final T[] secondPeriod;
	private final T[] thirdPeriod;

	public DevelopmentCardsDeck(T[] firstPeriod, T[] secondPeriod, T[] thirdPeriod)
	{
		this.firstPeriod = firstPeriod;
		this.secondPeriod = secondPeriod;
		this.thirdPeriod = thirdPeriod;
	}

	public T[] getFirstPeriod()
	{
		return this.firstPeriod;
	}

	public T[] getSecondPeriod()
	{
		return this.secondPeriod;
	}

	public T[] getThirdPeriod()
	{
		return this.thirdPeriod;
	}

	@Override
	public String toString()
	{
		return Builder.GSON.toJson(this);
	}

	public static class Builder<T extends Card>
	{
		private static final Map<Class<? extends Card>, Type> TYPE_TOKENS = new HashMap<>();

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
		private static final RuntimeTypeAdapterFactory<Object> RUNTIME_TYPE_ADAPTER_FACTORY_BONUS = RuntimeTypeAdapterFactory.of(Object.class).registerSubtype(BonusAdditionCard.class, "ADDITION_CARD").registerSubtype(BonusAdditionWork.class, "ADDITION_WORK").registerSubtype(Malus.class, "MALUS");
		private static final RuntimeTypeAdapterFactory<Event> RUNTIME_TYPE_ADAPTER_FACTORY_EVENT = RuntimeTypeAdapterFactory.of(Event.class).registerSubtype(EventCard.class, "CARD").registerSubtype(EventWork.class, "WORK");
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder().registerTypeAdapterFactory(Builder.RUNTIME_TYPE_ADAPTER_FACTORY_RESOURCE_AMOUNT).registerTypeAdapterFactory(Builder.RUNTIME_TYPE_ADAPTER_FACTORY_BONUS).registerTypeAdapterFactory(Builder.RUNTIME_TYPE_ADAPTER_FACTORY_EVENT);
		private static final Gson GSON = Builder.GSON_BUILDER.create();
		private final Class<T> clazz;
		private final String jsonFile;

		public Builder(Class<T> clazz, String jsonFile)
		{
			this.clazz = clazz;
			this.jsonFile = jsonFile;
		}

		public DevelopmentCardsDeck<T> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return Builder.GSON.fromJson(reader, Builder.TYPE_TOKENS.get(this.clazz));
			} catch (IOException exception) {
				Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
			return null;
		}
	}
}
