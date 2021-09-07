package LC1801_2100;
import java.util.*;
public class LC1975_MaximumMatrixSum {
    /**
     * You are given an n x n integer matrix. You can do the following operation any number of times:
     *
     * Choose any two adjacent elements of matrix and multiply each of them by -1.
     * Two elements are considered adjacent if and only if they share a border.
     *
     * Your goal is to maximize the summation of the matrix's elements. Return the maximum sum of the matrix's elements
     * using the operation mentioned above.
     *
     * Input: matrix = [[1,-1],[-1,1]]
     * Output: 4
     *
     * Constraints:
     *
     * n == matrix.length == matrix[i].length
     * 2 <= n <= 250
     * -10^5 <= matrix[i][j] <= 10^5
     * @param matrix
     * @return
     */
    // time = O(n^2), space = O(1)
    public long maxMatrixSum(int[][] matrix) {
        // corner case
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return 0;

        int n = matrix.length, count = 0, min = Integer.MAX_VALUE;
        long sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] < 0) {
                    count++;
                    sum -= matrix[i][j];
                } else sum += matrix[i][j];
                min = Math.min(min, Math.abs(matrix[i][j]));
            }
        }
        return count % 2 == 0 ? sum : sum - 2 * min;
    }
}
