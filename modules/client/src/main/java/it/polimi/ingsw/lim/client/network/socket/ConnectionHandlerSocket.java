package it.polimi.ingsw.lim.client.network.socket;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.client.utils.Utils;
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
		Client.getInstance().setConnected(true);
		this.packetListener = new PacketListener();
		this.packetListener.start();
		this.getHeartbeat().scheduleAtFixedRate(Connection::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
		CommonUtils.setNewWindow(Utils.SCENE_AUTHENTICATION, null, null, null);
	}

	public void disconnect()
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		this.getHeartbeat().shutdownNow();
		this.packetListener.end();
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException exception) {
			Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	ObjectInputStream getIn()
	{
		return this.in;
	}

	public ObjectOutputStream getOut()
	{
		return this.out;
	}
}
