package LC001_300;
import java.util.*;
public class LC36_ValidSudoku {
    /**
     * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be validated according to the following
     * rules:
     *
     * Each row must contain the digits 1-9 without repetition.
     * Each column must contain the digits 1-9 without repetition.
     * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition.
     *
     * Note:
     *
     * A Sudoku board (partially filled) could be valid but is not necessarily solvable.
     * Only the filled cells need to be validated according to the mentioned rules.
     *
     * Input: board =
     * [["5","3",".",".","7",".",".",".","."]
     * ,["6",".",".","1","9","5",".",".","."]
     * ,[".","9","8",".",".",".",".","6","."]
     * ,["8",".",".",".","6",".",".",".","3"]
     * ,["4",".",".","8",".","3",".",".","1"]
     * ,["7",".",".",".","2",".",".",".","6"]
     * ,[".","6",".",".",".",".","2","8","."]
     * ,[".",".",".","4","1","9",".",".","5"]
     * ,[".",".",".",".","8",".",".","7","9"]]
     * Output: true
     *
     * Constraints:
     *
     * board.length == 9
     * board[i].length == 9
     * board[i][j] is a digit or '.'.
     * @param board
     * @return
     */
    // time = O(1), space = O(1)   // 9 * 9 -> O(1)
    public boolean isValidSudoku(char[][] board) {
        // corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return false;

        int row = board.length, col = board[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == '.') continue;
                if (!isValid(board, i, j)) return false;
            }
        }
        return true;
    }

    private boolean isValid(char[][] board, int r, int c) {
        int row = board.length, col = board[0].length;
        // check row
        for (int i = 0; i < row; i++) {
            if (i == r) continue;
            if (board[i][c] == board[r][c]) return false;
        }

        // check column
        for (int j = 0; j < col; j++) {
            if (j == c) continue;
            if (board[r][j] == board[r][c]) return false;
        }

        // check grid
        for (int i = (r / 3) * 3; i < (r / 3 + 1) * 3; i++) {
            for (int j = (c / 3) * 3; j < (c / 3 + 1) * 3; j++) {
                if (i == r && j == c) continue;;
                if (board[i][j] == board[r][c]) return false;
            }
        }
        return true;
    }
}
