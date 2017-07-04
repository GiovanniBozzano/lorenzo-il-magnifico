package it.polimi.ingsw.lim.server.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface interface IQueryArgumentFiller
{
	void execute(PreparedStatement preparedStatement, QueryArgument queryArgument, int index) throws SQLException;
}
