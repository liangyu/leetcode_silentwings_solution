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
}
