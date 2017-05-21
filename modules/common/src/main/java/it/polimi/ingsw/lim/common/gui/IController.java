package it.polimi.ingsw.lim.common.gui;

import javax.annotation.PostConstruct;

public interface IController
{
	@PostConstruct
	void setupGui();
}
