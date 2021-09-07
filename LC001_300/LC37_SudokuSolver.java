package LC001_300;
import java.util.*;
public class LC37_SudokuSolver {
    /**
     * Write a program to solve a Sudoku puzzle by filling the empty cells.
     *
     * A sudoku solution must satisfy all of the following rules:
     *
     * Each of the digits 1-9 must occur exactly once in each row.
     * Each of the digits 1-9 must occur exactly once in each column.
     * Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
     * The '.' character indicates empty cells.
     *
     * Constraints:
     *
     * board.length == 9
     * board[i].length == 9
     * board[i][j] is a digit or '.'.
     * It is guaranteed that the input board has only one solution.
     * @param board
     */
    // S1: DFS
    // time = O(1), space = O(1)
    public void solveSudoku(char[][] board) {
        // corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return;

        solve(board);
    }

    private boolean solve(char[][] board) {
        int row = board.length, col = board[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) { // first check if it is valid, then check if solvable
                            board[i][j] = c;
                            if (solve(board)) return true;
                            board[i][j] = '.'; // setback
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(char[][] board, int i, int j, char c) {
        for (int k = 0; k < 9; k++) {
            if (board[k][j] == c) return false;
            if (board[i][k] == c) return false;
            if (board[(i / 3) * 3 + k / 3][(j / 3) * 3 + k % 3] == c) return false;
        }
        return true;
    }

    // S1.2: DFS
    // time = O(1), space = O(1)
    public void solveSudoku2(char[][] board) {
        // corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return;

        dfs(board, 0, 0);
    }

    private boolean dfs(char[][] board, int i, int j) {
        if (i == 9) return true;
        if (j == 9) return dfs(board, i + 1, 0); // 进入下一行，该行已填完
        if (board[i][j] != '.') return dfs(board, i, j + 1);

        for (char k = '1'; k <= '9'; k++) {
            if (!isOk(board, i, j, k)) continue;
            board[i][j] = k;
            if (dfs(board, i, j + 1)) return true;
            board[i][j] = '.'; // backtrack
        }
        return false;
    }

    private boolean isOk(char[][] board, int i, int j, char k) {
        for (int row = 0; row < 9; row++) {
            if (board[row][j] == k) return false;
        }

        for (int col = 0; col < 9; col++) {
            if (board[i][col] == k) return false;
        }

        // 以左上角来定义模块
        int x = i / 3 * 3, y = j / 3 * 3; // 小模块的左上角
        for (int p = 0; p < 3; p++) {
            for (int q = 0; q < 3; q++) {
                if (board[x + p][y + q] == k) return false;
            }
        }
        return true;
    }
}
/**
 * 暴力搜搜
 * 不违反规则，我们就继续填
 * 无论填什么不符合规则，那就退回去上一层重新填
 * 遇到死胡同就换，不行就回溯上一层
 * 递归的思想
 * 当前没矛盾的话就move on，有矛盾的话就换一格
 */