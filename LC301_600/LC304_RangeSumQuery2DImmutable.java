package LC301_600;
import java.util.*;
public class LC304_RangeSumQuery2DImmutable {
    /**
     * Given a 2D matrix matrix, handle multiple queries of the following type:
     *
     * Calculate the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1)
     * and lower right corner (row2, col2).
     * Implement the NumMatrix class:
     *
     * NumMatrix(int[][] matrix) Initializes the object with the integer matrix matrix.
     * int sumRegion(int row1, int col1, int row2, int col2) Returns the sum of the elements of matrix inside the
     * rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
     *
     * Input
     * ["NumMatrix", "sumRegion", "sumRegion", "sumRegion"]
     * [[[[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]], [2, 1, 4, 3],
     * [1, 1, 2, 2], [1, 2, 2, 4]]
     * Output
     * [null, 8, 11, 12]
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 200
     * -10^5 <= matrix[i][j] <= 10^5
     * 0 <= row1 <= row2 < m
     * 0 <= col1 <= col2 < n
     * At most 10^4 calls will be made to sumRegion.
     * @param matrix
     */
    // time = O(m * n), space = O(m * n)
    int[][] presum;
    public LC304_RangeSumQuery2DImmutable(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return;
        }

        int m = matrix.length, n = matrix[0].length;
        presum = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                presum[i][j] = presum[i - 1][j] + presum[i][j - 1] - presum[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        row1++;
        col1++;
        row2++;
        col2++;
        return presum[row2][col2] - presum[row2][col1 - 1] - presum[row1 - 1][col2] + presum[row1 - 1][col1 - 1];
    }
}