package LC1201_1500;
import java.util.*;
public class LC1329_SorttheMatrixDiagonally {
    /**
     * Given an m x n matrix mat of integers, sort each matrix diagonal in ascending order and
     * return the resulting matrix.
     *
     * Input: mat = [[3,3,1,1],[2,2,1,2],[1,1,1,2]]
     * Output: [[1,1,1,1],[1,2,2,2],[1,2,3,3]]
     *
     * Constraints:
     *
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 100
     * 1 <= mat[i][j] <= 100
     *
     * @param mat
     * @return
     */
    // time = O((m + n) * min(m, n) * log(min(m, n))) => O(m * n * log(min(m, n)))
    // 从全局的整个过程来看，mat里所有元素都会被加入到maxHeap中一次，也会被poll()出来填回去一次，所以整体上是m * n个元素被heap操作2次
    // space = O(min(m, n))
    public int[][] diagonalSort(int[][] mat) {
        int m = mat.length, n = mat[0].length;

        for (int i = 0; i < m; i++) { // O(m)
            sortDiagonal(mat, i, 0);
        }

        for (int j = 0; j < n; j++) { // O(n)
            sortDiagonal(mat, 0, j);
        }

        return mat;
    }

    private void sortDiagonal(int[][] mat, int i, int j) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int m = mat.length, n = mat[0].length;

        // sort the whole diagonal start with (i, j)
        while (i < m && j < n) maxHeap.offer(mat[i++][j++]); // O(min(m, n) * log(min(m, n)))

        // refill the matrix in reverse direction as i, j lands on the end of the diagonal in last step
        while (i > 0 && j > 0) mat[--i][--j] = maxHeap.poll(); // O(min(m, n) * log(min(m, n)))
    }
}
