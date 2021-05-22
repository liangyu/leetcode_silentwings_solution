package LC1501_1800;
import java.util.*;
public class LC1727_LargestSubmatrixWithRearrangements {
    /**
     * You are given a binary matrix matrix of size m x n, and you are allowed to rearrange the columns of the matrix
     * in any order.
     *
     * Return the area of the largest submatrix within matrix where every element of the submatrix is 1 after
     * reordering the columns optimally.
     *
     * Input: matrix = [[0,0,1],[1,1,1],[1,0,1]]
     * Output: 4
     *
     * Input: matrix = [[1,0,1,0,1]]
     * Output: 3
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m * n <= 105
     * matrix[i][j] is 0 or 1.
     *
     * @param matrix
     * @return
     */
    // time = O(m * nlogn), space = O(1)
    public int largestSubmatrix(int[][] matrix) {
        // corner case
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return 0;

        int row = matrix.length, col = matrix[0].length;
        for (int i = 1; i < row; i++) { // O(m)
            for (int j = 0; j < col; j++) { // O(n)
                if (matrix[i][j] == 1) {
                    matrix[i][j] += matrix[i - 1][j];
                }
            }
        }

        int max = 0;
        for (int i = 0; i < row; i++) { // O(m)
            Arrays.sort(matrix[i]); // O(nlogn)
            int height = Integer.MAX_VALUE;
            for (int j = col - 1; j>= 0; j--) { // O(n)
                height = Math.min(height, matrix[i][j]);
                max = Math.max(max, height * (col - j));
            }
        }
        return max;
    }
}
