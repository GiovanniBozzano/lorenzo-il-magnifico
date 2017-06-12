package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;

public enum ExcommunicationTiles
{
	EXCOMMUNICATION_TILES_1_1(0, new Modifier<EventGainResources>(EventGainResources.class)
	{
		@Override
		public void apply(EventGainResources event)
		{
			for (ResourceAmount resourceAmount : event.getResourceAmounts()) {
				if (resourceAmount.getResourceType() == ResourceType.MILITARY_POINT) {
					resourceAmount.setAmount(resourceAmount.getAmount() - 1);
				}
			}
		}
	}),
	EXCOMMUNICATION_TILES_1_2(1, new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_1_3(2,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_1_4(3,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_1_5(4,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_1_6(5,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_1_7(6,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_2_1(7,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_2_2(8,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_2_3(9,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_2_4(10,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_2_5(11,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_2_6(12,new Modifier<>(.class)
	{
		@Override public void apply ( event) {}
	}),

	EXCOMMUNICATION_TILES_2_7(13,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_3_1(14,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_3_2(15,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_3_3(16,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_3_4(17,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}),

	EXCOMMUNICATION_TILES_3_5(18,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	});
EXCOMMUNICATION_TILES_3_6(19,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}
EXCOMMUNICATION_TILES_3_7(20,new Modifier<>(.class)
	{
		@Override public void apply (event) {}
	}

	ExcommunicationTiles(int index, Modifier modifier)
	{
		this.index = index;
		this.modifier = modifier;
	}

	public int getIndex()
	{
		return this.index;
	}

	public Modifier getModifier()
	{
		return this.modifier;
	}
	}
