package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class Move implements Serializable {
    private int row;
    private int col;
    private char symbol;

    public Move(int row, int col, char symbol) {
        this.row = row;
        this.col = col;
        this.symbol = symbol;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public char getSymbol() { return symbol; }
}
