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
    BIT bit;
    int[][] matrix;
    int m, n;
    public LC308_RangeSumQuery2DMutable(int[][] matrix) {
        this.matrix = matrix;
        this.m = matrix.length;
        this.n = matrix[0].length;
        bit = new BIT(m, n);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                bit.update(i + 1, j + 1, matrix[i][j]);
            }
        }
    }

    public void update(int row, int col, int val) {
        bit.update(row + 1, col + 1, val - matrix[row][col]);
        matrix[row][col] = val;
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return bit.sumRange(row1 + 1, col1 + 1, row2 + 1, col2 + 1);
    }

    private class BIT {
        int m, n;
        int[][] bitree;
        public BIT(int m, int n) {
            this.m = m;
            this.n = n;
            this.bitree = new int[m + 1][n + 1];
        }

        private void update(int x, int y, int val) {
            for (int i = x; i <= m; i += i & (-i)) {
                for (int j = y; j <= n; j += j & (-j)) {
                    bitree[i][j] += val;
                }
            }
        }

        private int query(int x, int y) {
            int res = 0;
            for (int i = x; i > 0; i -= i & (-i)) {
                for (int j = y; j > 0; j -= j & (-j)) {
                    res += bitree[i][j];
                }
            }
            return res;
        }

        private int sumRange(int i, int j, int k, int l) {
            return query(k, l) + query(i - 1, j - 1) - query(i - 1, l) - query(k, j - 1);
        }
    }
}
