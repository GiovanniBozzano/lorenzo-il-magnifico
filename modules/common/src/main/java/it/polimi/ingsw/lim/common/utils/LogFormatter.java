package it.polimi.ingsw.lim.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter
{
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

	@Override
	public String format(LogRecord logRecord)
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(dateFormat.format(new Date(logRecord.getMillis())));
		stringBuilder.append(" - ");
		stringBuilder.append("[");
		stringBuilder.append(logRecord.getLoggerName());
		stringBuilder.append("/");
		stringBuilder.append(logRecord.getLevel());
		stringBuilder.append("] - ");
		stringBuilder.append(formatMessage(logRecord));
		stringBuilder.append("\n");
		return stringBuilder.toString();
	}
}
