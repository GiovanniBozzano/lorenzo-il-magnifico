package it.polimi.ingsw.lim.server.socket;

import it.polimi.ingsw.lim.common.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.packets.server.PacketLogMessage;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.IConnection;
import it.polimi.ingsw.lim.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

public class SocketConnection implements IConnection
{
	private final Socket socket;
	private final Integer id;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String name;

	SocketConnection(Socket socket, int id)
	{
		this.socket = socket;
		this.id = id;
		try {
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.out.flush();
			new PacketListener(this).start();
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			disconnect();
		}
	}

	@Override
	public void disconnect()
	{
		Server.getInstance().getConnections().remove(this);
		try {
			if (this.out != null) {
				this.out.close();
			}
			if (this.in != null) {
				this.in.close();
			}
			this.socket.close();
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendLogMessage(String text)
	{
		try {
			this.out.writeObject(new PacketLogMessage(text));
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendChatMessage(String text)
	{
		try {
			this.out.writeObject(new PacketChatMessage(text));
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void handleChatMessage(String text)
	{
		for (IConnection otherConnection : Server.getInstance().getConnections()) {
			if (otherConnection != this) {
				otherConnection.sendChatMessage(this.name + ": " + text);
			}
		}
	}

	public int getId()
	{
		return this.id;
	}

	public ObjectInputStream getIn()
	{
		return this.in;
	}

	public ObjectOutputStream getOut()
	{
		return this.out;
	}

	public String getName()
	{
		return this.name;
	}
}
