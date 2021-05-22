package LC301_600;
import java.util.*;
public class LC348_DesignTicTacToe {
    /**
     * Assume the following rules are for the tic-tac-toe game on an n x n board between two players:
     *
     * A move is guaranteed to be valid and is placed on an empty block.
     * Once a winning condition is reached, no more moves are allowed.
     * A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
     * Implement the TicTacToe class:
     *
     * TicTacToe(int n) Initializes the object the size of the board n.
     * int move(int row, int col, int player) Indicates that player with id player plays at the cell (row, col) of the
     * board. The move is guaranteed to be a valid move.
     * Follow up:
     * Could you do better than O(n2) per move() operation?
     *
     * Input
     * ["TicTacToe", "move", "move", "move", "move", "move", "move", "move"]
     * [[3], [0, 0, 1], [0, 2, 2], [2, 2, 1], [1, 1, 2], [2, 0, 1], [1, 0, 2], [2, 1, 1]]
     * Output
     * [null, 0, 0, 0, 0, 0, 0, 1]
     *
     * Constraints:
     *
     * 2 <= n <= 100
     * player is 1 or 2.
     * 1 <= row, col <= n
     * (row, col) are unique for each different call to move.
     * At most n2 calls will be made to move.
     */
    /** Initialize your data structure here. */
    // time = O(1), space = O(1)
    private int[] rows, cols;
    private int diagonal, antidiagonal, size;
    public LC348_DesignTicTacToe(int n) {
        rows = new int[n];
        cols = new int[n];
        size = n;
    }

    /** Player {player} makes a move at ({row}, {col}).
     @param row The row of the board.
     @param col The column of the board.
     @param player The player, can be either 1 or 2.
     @return The current winning condition, can be either:
     0: No one wins.
     1: Player 1 wins.
     2: Player 2 wins. */
    public int move(int row, int col, int player) {
        int toAdd = player == 1 ? 1 : -1;

        rows[row] += toAdd;
        cols[col] += toAdd;
        if (row == col) diagonal += toAdd;
        if (row == cols.length - 1 - col) antidiagonal += toAdd;

        if (Math.abs(rows[row]) == size || Math.abs(cols[col]) == size || Math.abs(diagonal) == size || Math.abs(antidiagonal) == size) {
            return player;
        }
        return 0;
    }
}
