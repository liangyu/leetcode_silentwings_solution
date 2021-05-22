package LC001_300;
import java.util.*;
public class LC74_Searcha2DMatrix {
    /**
     * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has
     * the following properties:
     *
     * Integers in each row are sorted from left to right.
     * The first integer of each row is greater than the last integer of the previous row.
     *
     * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
     * Output: true
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 100
     * -104 <= matrix[i][j], target <= 104
     *
     * @param matrix
     * @param target
     * @return
     */
    // time = O(log(m * n)), space = O(1)
    public boolean searchMatrix(int[][] matrix, int target) {
        // corner case
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return false;
        }

        int m = matrix.length, n = matrix[0].length;
        int start = 0, end = m * n - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            int i = mid / n, j = mid % n;
            if (matrix[i][j] == target) return true;
            if (matrix[i][j] < target) start = mid + 1;
            else end = mid - 1;
        }
        return false;
    }
}
