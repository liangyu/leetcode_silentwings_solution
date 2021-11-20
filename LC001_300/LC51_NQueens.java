package LC001_300;
import java.util.*;
public class LC51_NQueens {
    /**
     * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack
     * each other.
     *
     * Given an integer n, return all distinct solutions to the n-queens puzzle.
     *
     * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate
     * a queen and an empty space, respectively.
     *
     * Input: n = 4
     * Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
     *
     * Constraints:
     *
     * 1 <= n <= 9
     * @param n
     * @return
     */
    // S1: DFS
    // time = O(n!), space = O(n)
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        dfs(res, new int[n], 0);
        return res;
    }

    private void dfs(List<List<String>> res, int[] queens, int pos) {
        // base case
        if (pos == queens.length) {
            addSol(res, queens);
            return;
        }

        for (int i = 0; i < queens.length; i++) {
            queens[pos] = i;
            if (isValid(queens, pos)) {
                dfs(res, queens, pos + 1);
            }
        }
    }

    private boolean isValid(int[] queens, int pos) {
        for (int i = 0; i < pos; i++) {
            if (queens[i] == queens[pos]) return false;
            else if (Math.abs(queens[pos] - queens[i]) == Math.abs(pos - i)) return false;
        }
        return true;
    }

    private void addSol(List<List<String>> res, int[] queens) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < queens.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < queens.length; j++) {
                if (queens[i] == j) sb.append('Q');
                else sb.append('.');
            }
            list.add(sb.toString());
        }
        res.add(list);
    }

    // S2: DFS
    // time = O(n!), space = O(n)
    public List<List<String>> solveNQueens2(int n) {
        List<List<String>> res = new ArrayList<>();
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(board[i], '.');
        dfs(board, 0, res);
        return res;
    }

    private void dfs(char[][] board, int i, List<List<String>> res) {
        int n = board.length;
        if (i == n) {
            addSol(board, res);
            return;
        }

        for (int j = 0; j < n; j++) {
            if (isValid(board, i, j)) {
                board[i][j] = 'Q';
                dfs(board, i + 1, res);
                board[i][j] = '.';
            }
        }
    }

    private boolean isValid(char[][] board, int row, int col) {
        int n = board.length;
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }

        for (int j = 0; j < col; j++) {
            if (board[row][j] == 'Q') return false;
        }

        int k = 1;
        while (row - k >= 0 && col - k >= 0) {
            if (board[row - k][col - k] == 'Q') return false;
            k++;
        }

        k = 1;
        while (row - k >= 0 && col + k < n) {
            if (board[row - k][col + k] == 'Q') return false;
            k++;
        }
        return true;
    }

    private void addSol(char[][] board, List<List<String>> res) {
        int n = board.length;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(String.valueOf(board[i]));
        }
        res.add(list);
    }
}
/**
 * N个皇后，每行只能选一个
 * 选哪一个，搜索列，递归深度在行
 * 一旦固定了第一行皇后的位置，会对后面的行产生约束
 * 继续递归
 * 典型的深度优先搜索
 * 理论上时间复杂度是O(n^n)
 * 可以剪枝的可能性非常大，效率还是很高的
 */
