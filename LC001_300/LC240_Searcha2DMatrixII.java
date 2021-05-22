package LC001_300;
import java.util.*;
public class LC240_Searcha2DMatrixII {
    /**
     * Write an efficient algorithm that searches for a target value in an m x n integer matrix.
     * The matrix has the following properties:
     *
     * Integers in each row are sorted in ascending from left to right.
     * Integers in each column are sorted in ascending from top to bottom.
     *
     * Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
     * Output: true
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= n, m <= 300
     * -10^9 <= matix[i][j] <= 10^9
     * All the integers in each row are sorted in ascending order.
     * All the integers in each column are sorted in ascending order.
     * -10^9 <= target <= 10^9
     *
     * @param matrix
     * @param target
     * @return
     */
    // time = O(m + n), space = O(1)
    public boolean searchMatrix(int[][] matrix, int target) {
        // corner case
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return false;
        }

        int row = matrix.length, col = matrix[0].length;
        int i = row - 1, j = 0; // 从左下角出发去寻找

        while (i >= 0 && i < row && j >= 0 && j < col) {
            if (matrix[i][j] == target) return true;
            if (matrix[i][j] < target) j++;
            else i--;
        }
        return false;
    }
}
