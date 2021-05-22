package LC001_300;
import java.util.*;
public class LC52_NQueensII {
    /**
     * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each
     * other.
     *
     * Given an integer n, return the number of distinct solutions to the n-queens puzzle.
     *
     * Input: n = 4
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 9
     * @param n
     * @return
     */
    // time = O(n!), space = O(n)
    private int res = 0;
    public int totalNQueens(int n) {
        dfs(new int[n], 0);
        return res;
    }

    private void dfs(int[] queens, int pos) {
        // base case
        if (pos == queens.length) {
            res++;
            return;
        }

        for (int i = 0; i < queens.length; i++) {
            queens[pos] = i;
            if (isValid(queens, pos)) dfs(queens, pos + 1);
        }
    }

    private boolean isValid(int[] queens, int pos) {
        for (int i = 0; i < pos; i++) {
            if (queens[i] == queens[pos]) return false;
            if (Math.abs(queens[pos] - queens[i]) == Math.abs(pos - i)) return false;
        }
        return true;
    }
}
