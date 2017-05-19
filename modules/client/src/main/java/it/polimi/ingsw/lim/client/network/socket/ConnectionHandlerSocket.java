package it.polimi.ingsw.lim.client.network.socket;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketLogin;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRegistration;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRoomCreation;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRoomEntry;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ConnectionHandlerSocket extends ConnectionHandler
{
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private PacketListener packetListener;

	@Override
	public void run()
	{
		try {
			this.socket = new Socket(Client.getInstance().getIp(), Client.getInstance().getPort());
			this.socket.setSoTimeout(12000);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.in = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException exception) {
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
			Client.getLogger().log(Level.INFO, "Could not connect to host.", exception);
			return;
		}
		this.packetListener = new PacketListener();
		this.packetListener.start();
		this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
		CommonUtils.setNewWindow(Utils.SCENE_AUTHENTICATION, null, null, null);
	}

	@Override
	public void disconnect(boolean notifyServer)
	{
		super.disconnect(notifyServer);
		this.packetListener.end();
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException exception) {
			Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public synchronized void sendLogin(String username, String password)
	{
		super.sendLogin(username, password);
		new PacketLogin(username, CommonUtils.encrypt(password)).send(this.out);
	}

	@Override
	public synchronized void sendRegistration(String username, String password)
	{
		super.sendRegistration(username, password);
		new PacketRegistration(username, CommonUtils.encrypt(password)).send(this.out);
	}

	private synchronized void sendDisconnectionAcknowledgement()
	{
		new Packet(PacketType.DISCONNECTION_ACKNOWLEDGEMENT).send(this.out);
	}

	@Override
	public synchronized void sendHeartbeat()
	{
		new Packet(PacketType.HEARTBEAT).send(this.out);
	}

	@Override
	public synchronized void sendRequestRoomList()
	{
		new Packet(PacketType.ROOM_LIST_REQUEST).send(this.out);
	}

	@Override
	public synchronized void sendRoomCreation(String name)
	{
		super.sendRoomCreation(name);
		new PacketRoomCreation(name).send(this.out);
	}

	@Override
	public synchronized void sendRoomEntry(int id)
	{
		super.sendRoomEntry(id);
		new PacketRoomEntry(id).send(this.out);
	}

	@Override
	public synchronized void sendRoomExit()
	{
		new Packet(PacketType.ROOM_EXIT).send(this.out);
		super.sendRoomExit();
	}

	@Override
	public synchronized void sendChatMessage(String text)
	{
		new PacketChatMessage(text).send(this.out);
	}

	void handleDisconnectionLogMessage(String text)
	{
		Client.getLogger().log(Level.INFO, text);
		this.sendDisconnectionAcknowledgement();
	}

	ObjectInputStream getIn()
	{
		return this.in;
	}
}
