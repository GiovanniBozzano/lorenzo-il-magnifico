package it.polimi.ingsw.lim.server.socket;

import it.polimi.ingsw.lim.common.enums.FontType;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class ConnectionListener extends Thread
{
	private boolean keepGoing = true;

	@Override
	public void run()
	{
		while (this.keepGoing) {
			try {
				Socket socket = Server.getInstance().getServerSocket().accept();
				if (!this.keepGoing) {
					Server.getInstance().disconnectAll();
					Server.getInstance().getServerSocket().close();
				} else {
					int connectionId = Server.getInstance().getConnectionId();
					Server.getInstance().displayToLog("Socket Connection accepted from: " + socket.getInetAddress().getHostAddress() + " - " + connectionId, FontType.NORMAL);
					Server.getInstance().getConnections().add(new SocketConnection(socket, connectionId));
				}
			} catch (IOException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				Server.getInstance().stop();
			}
		}
	}

	public synchronized void close()
	{
		this.keepGoing = false;
	}
}
