package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.GameState;
import il.cshaifasweng.OCSFMediatorExample.entities.Move;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();

	public SimpleServer(int port) {
		super(port);
		
	}

	private ConnectionToClient playerX = null;
	private ConnectionToClient playerO = null;
	private GameState gameState = new GameState();

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			if (msg instanceof String && ((String) msg).equals("join")) {
				if (playerX == null) {
					playerX = client;
					client.sendToClient("symbol:X");
				} else if (playerO == null) {
					playerO = client;
					client.sendToClient("symbol:O");
					sendToAllClients(gameState);
				}
			} else if (msg instanceof Move) {
				Move move = (Move) msg;
				if ((move.getSymbol() == 'X' && client == playerX) || (move.getSymbol() == 'O' && client == playerO)) {
					gameState.applyMove(move);
					sendToAllClients(gameState);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendToAllClients(String message) {
		try {
			for (SubscribedClient subscribedClient : SubscribersList) {
				subscribedClient.getClient().sendToClient(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
