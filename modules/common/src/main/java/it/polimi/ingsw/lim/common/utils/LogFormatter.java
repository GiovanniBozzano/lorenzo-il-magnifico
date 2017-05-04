package it.polimi.ingsw.lim.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter
{
	public static final String EXCEPTION_MESSAGE = "an exception was thrown";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

	@Override
	public String format(LogRecord logRecord)
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(DATE_FORMAT.format(new Date(logRecord.getMillis())));
		stringBuilder.append(" - ");
		stringBuilder.append("[");
		stringBuilder.append(logRecord.getLoggerName());
		stringBuilder.append("/");
		stringBuilder.append(logRecord.getLevel());
		stringBuilder.append("] - ");
		stringBuilder.append(this.formatMessage(logRecord));
		if (logRecord.getLevel().intValue() >= Level.WARNING.intValue() && logRecord.getThrown() != null) {
			stringBuilder.append("\n");
			stringBuilder.append(logRecord.getThrown());
			stringBuilder.append("\n");
			stringBuilder.append(LogFormatter.stackTraceToString(logRecord.getThrown()));
		}
		stringBuilder.append("\n");
		return stringBuilder.toString();
	}

	private static String stackTraceToString(Throwable throwable)
	{
		StringBuilder stringBuilder = new StringBuilder();
		boolean isFirstLine = true;
		for (StackTraceElement element : throwable.getStackTrace()) {
			if (!isFirstLine) {
				stringBuilder.append("\n");
			} else {
				isFirstLine = false;
			}
			stringBuilder.append(element.toString());
		}
		return stringBuilder.toString();
	}
}
