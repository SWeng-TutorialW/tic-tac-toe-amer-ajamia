package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.GameState;
import il.cshaifasweng.OCSFMediatorExample.entities.Move;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class PrimaryController {

	@FXML
	private Label statusLabel;

	@FXML
	private GridPane boardGrid;

	private Button[][] cells = new Button[3][3];
	private char mySymbol = ' ';
	private boolean myTurn = false;

	@FXML
	public void initialize() {
		EventBus.getDefault().register(this);
		createBoard();

		try {
			SimpleClient.getClient().sendToServer("join");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createBoard() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Button button = new Button(" ");
				button.setMinSize(100, 100);
				button.setStyle("-fx-font-size: 24px;");
				final int r = row, c = col;

				button.setOnAction(event -> {
					if (myTurn && button.getText().equals(" ")) {
						try {
							SimpleClient.getClient().sendToServer(new Move(r, c, mySymbol));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});

				cells[row][col] = button;
				boardGrid.add(button, col, row);
			}
		}
	}

	@Subscribe
	public void onGameStateUpdate(GameState gameState) {
		Platform.runLater(() -> {
			updateBoard(gameState.getBoard());
			statusLabel.setText(gameState.getStatus());
			myTurn = !gameState.isGameOver() && gameState.getCurrentTurn() == mySymbol;
		});
	}

	@Subscribe
	public void onSymbolAssigned(StringEvent event) {
		Platform.runLater(() -> {
			String msg = event.getMessage();
			if (msg.startsWith("symbol:")) {
				mySymbol = msg.charAt(7);
				statusLabel.setText("You are Player " + mySymbol + ". Waiting for opponent...");
			}
		});
	}

	private void updateBoard(char[][] board) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				cells[i][j].setText(String.valueOf(board[i][j]));
			}
		}
	}
}
