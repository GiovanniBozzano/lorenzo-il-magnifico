package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.packets.server.PacketLogMessage;
import it.polimi.ingsw.lim.common.utils.LogFormatter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

public class ClientConnection
{
	private final Socket socket;
	private final Integer id;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean isConnected = false;
	private String name;

	ClientConnection(Socket socket, int id)
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

	public void disconnect()
	{
		Server.getInstance().getClientConnections().remove(this);
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

	public void sendLogMessage(String text)
	{
		try {
			this.out.writeObject(new PacketLogMessage(text));
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public void sendChatMessage(String text)
	{
		try {
			this.out.writeObject(new PacketChatMessage(text));
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
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

	public boolean getIsConnected()
	{
		return this.isConnected;
	}

	public void setIsConnected(boolean isConnected)
	{
		this.isConnected = isConnected;
	}

	public String getName()
	{
		return this.name;
	}
}
