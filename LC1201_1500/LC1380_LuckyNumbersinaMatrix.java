package LC1201_1500;
import java.util.*;
public class LC1380_LuckyNumbersinaMatrix {
    /**
     * Given an m x n matrix of distinct numbers, return all lucky numbers in the matrix in any order.
     *
     * A lucky number is an element of the matrix such that it is the minimum element in its row and maximum in its
     * column.
     *
     * Input: matrix = [[3,7,8],[9,11,13],[15,16,17]]
     * Output: [15]
     *
     * Constraints:
     *
     * m == mat.length
     * n == mat[i].length
     * 1 <= n, m <= 50
     * 1 <= matrix[i][j] <= 10^5.
     * All elements in the matrix are distinct.
     * @param matrix
     * @return
     */
    public List<Integer> luckyNumbers (int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int m = matrix.length, n = matrix[0].length;
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < m; i++) {
            int min = matrix[i][0], x = i, y = 0;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                    x = i;
                    y = j;
                }
            }
            set.add(x * n + y);
        }

        for (int j = 0; j < n; j++) {
            int max = matrix[0][j], x = 0, y = j;
            for (int i = 0; i < m; i++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                    x = i;
                    y = j;
                }
            }
            if (set.contains(x * n + y)) res.add(matrix[x][y]);
        }
        return res;
    }
}
