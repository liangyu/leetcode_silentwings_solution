package LC601_900;

public class LC794_ValidTicTacToeState {
    /**
     * Given a Tic-Tac-Toe board as a string array board, return true if and only if it is possible to reach this board
     * position during the course of a valid tic-tac-toe game.
     *
     * The board is a 3 x 3 array that consists of characters ' ', 'X', and 'O'. The ' ' character represents an empty
     * square.
     *
     * Here are the rules of Tic-Tac-Toe:
     *
     * Players take turns placing characters into empty squares ' '.
     * The first player always places 'X' characters, while the second player always places 'O' characters.
     * 'X' and 'O' characters are always placed into empty squares, never filled ones.
     * The game ends when there are three of the same (non-empty) character filling any row, column, or diagonal.
     * The game also ends if all squares are non-empty.
     * No more moves can be played if the game is over.
     *
     * Input: board = ["O  ","   ","   "]
     * Output: false
     *
     * Constraints:
     *
     * board.length == 3
     * board[i].length == 3
     * board[i][j] is either 'X', 'O', or ' '.
     * @param board
     * @return
     */
    // time = O(1), space = O(1)
    public boolean validTicTacToe(String[] board) {
        int x = 0, o = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i].charAt(j) == 'X') x++;
                if (board[i].charAt(j) == 'O') o++;
            }
        }
        if (!(x == o || x == o + 1)) return false;
        if (x == o && win(board, 'X')) return false;
        if (x == o + 1 && win(board, 'O')) return false;
        return true;
    }

    private boolean win(String[] board, char ch) {
        for (int i = 0; i < 3; i++) {
            if (board[i].charAt(0) == board[i].charAt(1) && board[i].charAt(1) == board[i].charAt(2) && board[i].charAt(0) == ch) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (board[0].charAt(j) == board[1].charAt(j) && board[1].charAt(j) == board[2].charAt(j) && board[0].charAt(j) == ch) {
                return true;
            }
        }

        if (board[0].charAt(0) == board[1].charAt(1) && board[1].charAt(1) == board[2].charAt(2) && board[0].charAt(0) == ch) {
            return true;
        }

        if (board[0].charAt(2) == board[1].charAt(1) && board[1].charAt(1) == board[2].charAt(0) && board[0].charAt(2) == ch) {
            return true;
        }
        return false;
    }
}
