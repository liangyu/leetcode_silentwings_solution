package LC301_600;

public class LC598_RangeAdditionII {
    /**
     * You are given an m x n matrix M initialized with all 0's and an array of operations ops, where ops[i] = [ai, bi]
     * means M[x][y] should be incremented by one for all 0 <= x < ai and 0 <= y < bi.
     *
     * Count and return the number of maximum integers in the matrix after performing all the operations.
     *
     * Input: m = 3, n = 3, ops = [[2,2],[3,3]]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= m, n <= 4 * 10^4
     * 1 <= ops.length <= 10^4
     * ops[i].length == 2
     * 1 <= ai <= m
     * 1 <= bi <= n
     * @param m
     * @param n
     * @param ops
     * @return
     */
    // time = O(k), space = O(1)
    public int maxCount(int m, int n, int[][] ops) {
        // corner case
        if (ops == null || ops.length == 0 || ops[0] == null || ops[0].length == 0) return m * n;

        int x = m, y = n;
        for (int[] op : ops) {
            x = Math.min(op[0], x);
            y = Math.min(op[1], y);
        }
        return x * y;
    }
}
