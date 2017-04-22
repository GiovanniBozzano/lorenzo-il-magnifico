package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.enums.FontType;
import it.polimi.ingsw.lim.common.utils.LogFormatter;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

class ConnectionListener extends Thread
{
	private int id = 0;
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
					Server.getInstance().displayToLog("Connessione accettata da: " + socket.getInetAddress() + " - " + ++this.id, FontType.BOLD);
					Server.getInstance().getClientConnections().add(new ClientConnection(socket, this.id));
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
