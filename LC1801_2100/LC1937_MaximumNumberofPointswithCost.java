package LC1801_2100;

public class LC1937_MaximumNumberofPointswithCost {
    /**
     * You are given an m x n integer matrix points (0-indexed). Starting with 0 points, you want to maximize the
     * number of points you can get from the matrix.
     *
     * To gain points, you must pick one cell in each row. Picking the cell at coordinates (r, c) will add points[r][c]
     * to your score.
     *
     * However, you will lose points if you pick a cell too far from the cell that you picked in the previous row. For
     * every two adjacent rows r and r + 1 (where 0 <= r < m - 1), picking cells at coordinates (r, c1) and (r + 1, c2)
     * will subtract abs(c1 - c2) from your score.
     *
     * Return the maximum number of points you can achieve.
     *
     * abs(x) is defined as:
     *
     * x for x >= 0.
     * -x for x < 0.
     *
     * Input: points = [[1,2,3],[1,5,1],[3,1,1]]
     * Output: 9
     *
     * Constraints:
     *
     * m == points.length
     * n == points[r].length
     * 1 <= m, n <= 10^5
     * 1 <= m * n <= 10^5
     * 0 <= points[r][c] <= 10^5
     * @param points
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public long maxPoints(int[][] points) {
        // corner case
        if (points == null || points.length == 0 || points[0] == null || points[0].length == 0) return 0;

        int m = points.length, n = points[0].length;
        long[][] f = new long[m][n];

        // init
        for (int j = 0; j < n; j++) f[0][j] = points[0][j];

        for (int i = 1; i < m; i++) {
            long[] left = new long[n];
            long[] right = new long[n];

            left[0] = f[i - 1][0] + 0;
            for (int k = 1; k < n; k++) {
                left[k] = Math.max(left[k - 1], f[i - 1][k] + k);
            }

            right[n - 1] = f[i - 1][n - 1] - (n - 1);
            for (int k = n - 2; k >= 0; k--) {
                right[k] = Math.max(right[k + 1], f[i - 1][k] - k);
            }

            for (int j = 0; j < n; j++) {
                f[i][j] = Math.max(left[j] - j, right[j] + j) + points[i][j];
            }
        }

        long res = 0;
        for (int j = 0; j < n; j++) {
            res = Math.max(res, f[m - 1][j]);
        }
        return res;
    }
}
