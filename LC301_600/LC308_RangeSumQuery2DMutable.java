package LC301_600;

public class LC308_RangeSumQuery2DMutable {
    /**
     * Given a 2D matrix matrix, handle multiple queries of the following types:
     *
     * Update the value of a cell in matrix.
     * Calculate the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1)
     * and lower right corner (row2, col2).
     * Implement the NumMatrix class:
     *
     * NumMatrix(int[][] matrix) Initializes the object with the integer matrix matrix.
     * void update(int row, int col, int val) Updates the value of matrix[row][col] to be val.
     * int sumRegion(int row1, int col1, int row2, int col2) Returns the sum of the elements of matrix inside the
     * rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
     *
     * Input
     * ["NumMatrix", "sumRegion", "update", "sumRegion"]
     * [[[[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]], [2, 1, 4, 3],
     * [3, 2, 2], [2, 1, 4, 3]]
     * Output
     * [null, 8, null, 10]
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 200
     * -10^5 <= matrix[i][j] <= 10^5
     * 0 <= row < m
     * 0 <= col < n
     * -10^5 <= val <= 10^5
     * 0 <= row1 <= row2 < m
     * 0 <= col1 <= col2 < n
     * At most 10^4 calls will be made to sumRegion and update.
     * @param matrix
     */
    // time = O(logm * logn), space = O(m * n)
    private int m, n;
    private int[][] bitree;
    private int[][] nums;
    public LC308_RangeSumQuery2DMutable(int[][] matrix) {
        m = matrix.length;
        n = matrix[0].length;
        bitree = new int[m + 1][n + 1];
        nums = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }

    public void update(int row, int col, int val) {
        if (m == 0 || n == 0) return;
        int diff = val - nums[row][col];
        nums[row][col] = val;
        for (int i = row + 1; i <= m; i += (i & -i)) {
            for (int j = col + 1; j <= n; j += (j & -j)) {
                bitree[i][j] += diff;
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (m == 0 || n == 0) return 0;
        return querySum(row2 + 1, col2 + 1) + querySum(row1, col1) - querySum(row1, col2 + 1) - querySum(row2 + 1, col1);
    }

    private int querySum(int m, int n) {
        int sum = 0;
        for (int i = m; i > 0; i -= (i & -i)) {
            for (int j = n; j > 0; j -= (j & -j)) {
                sum += bitree[i][j];
            }
        }
        return sum;
    }
}
