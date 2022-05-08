package LC1201_1500;
import java.util.*;
public class LC1337_TheKWeakestRowsinaMatrix {
    /**
     * Given a m * n matrix mat of ones (representing soldiers) and zeros (representing civilians), return the indexes
     * of the k weakest rows in the matrix ordered from the weakest to the strongest.
     *
     * A row i is weaker than row j, if the number of soldiers in row i is less than the number of soldiers in row j,
     * or they have the same number of soldiers but i is less than j. Soldiers are always stand in the frontier of a
     * row, that is, always ones may appear first and then zeros.
     *
     * Input: mat =
     * [[1,1,0,0,0],
     *  [1,1,1,1,0],
     *  [1,0,0,0,0],
     *  [1,1,0,0,0],
     *  [1,1,1,1,1]],
     * k = 3
     * Output: [2,0,3]
     *
     * Constraints:
     *
     * m == mat.length
     * n == mat[i].length
     * 2 <= n, m <= 100
     * 1 <= k <= m
     * matrix[i][j] is either 0 or 1.
     *
     * @param mat
     * @param k
     * @return
     */
    // time = O(m * n + mlogm) = O(m * (n + logm), space = O(m + k) = O(m)
    public int[] kWeakestRows(int[][] mat, int k) {
        int[] res = new int[k];
        // corner case
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0 || k <= 0) return res;

        int row = mat.length, col = mat[0].length;
        List<Cell > list = new ArrayList<>();
        for (int i = 0; i < row; i++) { // O(m)
            int cnt = 0;
            for (int j = 0; j < col; j++) {
                if (mat[i][j] == 1) cnt++;
                else break;
            }
            list.add(new Cell(cnt, i));
        }

        Collections.sort(list, (o1, o2) -> o1.val != o2.val ? o1.val - o2.val : o1.idx - o2.idx);
        for (int i = 0; i < k; i++) res[i] = list.get(i).idx;
        return res;
    }

    private class Cell {
        private int val;
        private int idx;
        public Cell (int val, int idx) {
            this.val = val;
            this.idx = idx;
        }
    }
}
