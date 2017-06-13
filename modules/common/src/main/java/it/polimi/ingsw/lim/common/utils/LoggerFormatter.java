package it.polimi.ingsw.lim.common.utils;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LoggerFormatter extends Formatter
{
	@Override
	public String format(LogRecord logRecord)
	{
		return this.formatMessage(logRecord) + '\n';
	}
}
