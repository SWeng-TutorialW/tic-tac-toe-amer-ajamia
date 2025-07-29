package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class GameState implements Serializable {
    private char[][] board = new char[3][3];
    private char currentTurn;
    private String status;
    private boolean gameOver;

    public GameState() {
        currentTurn = 'X';
        gameOver = false;
        status = "Game started. X's turn";
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
    }

    public char[][] getBoard() { return board; }
    public char getCurrentTurn() { return currentTurn; }
    public String getStatus() { return status; }
    public boolean isGameOver() { return gameOver; }

    public void applyMove(Move move) {
        if (board[move.getRow()][move.getCol()] == ' ' && !gameOver) {
            board[move.getRow()][move.getCol()] = move.getSymbol();
            if (checkWin(move.getSymbol())) {
                status = "Player " + move.getSymbol() + " wins!";
                gameOver = true;
            } else if (checkDraw()) {
                status = "Draw!";
                gameOver = true;
            } else {
                currentTurn = (move.getSymbol() == 'X') ? 'O' : 'X';
                status = "Player " + currentTurn + "'s turn";
            }
        }
    }

    private boolean checkWin(char symbol) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol))
                return true;
        }
        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    private boolean checkDraw() {
        for (char[] row : board)
            for (char cell : row)
                if (cell == ' ')
                    return false;
        return true;
    }
}
