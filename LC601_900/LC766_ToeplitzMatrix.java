package LC601_900;
import java.util.*;
public class LC766_ToeplitzMatrix {
    /**
     * Given an m x n matrix, return true if the matrix is Toeplitz. Otherwise, return false.
     *
     * A matrix is Toeplitz if every diagonal from top-left to bottom-right has the same elements.
     *
     * Input: matrix = [[1,2,3,4],[5,1,2,3],[9,5,1,2]]
     * Output: true
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 20
     * 0 <= matrix[i][j] <= 99
     *
     *
     * Follow up:
     *
     * What if the matrix is stored on disk, and the memory is limited such that you can only load at most one row of
     * the matrix into the memory at once?
     * What if the matrix is so large that you can only load up a partial row into the memory at once?
     * @param matrix
     * @return
     */
    // S1
    // time = O(m * n), space = O(1)
    public boolean isToeplitzMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0 && j > 0 && matrix[i - 1][j - 1] != matrix[i][j]) return false;
            }
        }
        return true;
    }

    // S2
    // time = O(m * n), space = O(m + n)
    public boolean isToeplitzMatrix2(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!map.containsKey(i - j)) map.put(i - j, matrix[i][j]);
                else if (map.get(i - j) != matrix[i][j]) return false;
            }
        }
        return true;
    }

    // follow-up 1
    // time = O(m * n), space = O(n)
    public boolean isToeplitzMatrix3(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        List<Integer> buffer = new LinkedList<>();
        for (int j = 0; j < n; j++) buffer.add(matrix[0][j]);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (buffer.get(j - 1) != matrix[i][j]) return false;
            }
            buffer.remove(buffer.size() - 1);
            buffer.add(0, matrix[i][0]);
        }
        return true;
    }
}
/**
 * For the follow-up 1, we are only able to load one row at one time,
 * so we have to use a buffer (1D data structure) to store the row info.
 * When next row comes as a streaming flow, we can compare each value
 * (say, matrix[i][j], i>=1, j>=1) to the "upper-left" value in the buffer (buffer[j - 1]);
 * meanwhile, we update the buffer by inserting the 1st element of the current row (matrix[i][0]) to buffer
 * at position 0 (buffer[0]), and removing the last element in the buffer.
 *
 * For the follow-up 2, we can only load a partial row at one time.
 * We could split the whole matrix vertically into several sub-matrices,
 * while each sub-matrix should have one column overlapped.
 * We repeat the same method in follow-up 1 for each sub-matrix.
 *
 * For example:
 *
 * [1 2 3 4 5 6 7 8 9 0]              [1 2 3 4]              [4 5 6 7]              [7 8 9 0]
 * [0 1 2 3 4 5 6 7 8 9]              [0 1 2 3]              [3 4 5 6]              [6 7 8 9]
 * [1 0 1 2 3 4 5 6 7 8]     -->      [1 0 1 2]       +      [2 3 4 5]       +      [5 6 7 8]
 * [2 1 0 1 2 3 4 5 6 7]              [2 1 0 1]              [1 2 3 4]              [4 5 6 7]
 * [3 2 1 0 1 2 3 4 5 6]              [3 2 1 0]              [0 1 2 3]              [3 4 5 6]
 *
 * The reason why we need to have a column overlapped is because you need to check the top left cell
 * and if your matrices are not overlapped with 1 column, you don't know the leftmost column has the same elements
 * as its previous column.
 */
