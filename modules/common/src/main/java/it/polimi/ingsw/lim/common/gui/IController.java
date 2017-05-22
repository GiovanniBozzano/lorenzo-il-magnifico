package it.polimi.ingsw.lim.common.gui;

import javax.annotation.PostConstruct;

@FunctionalInterface public interface IController
{
	@PostConstruct
	void setupGui();
}
