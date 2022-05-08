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
    // time = O(m * n + m * logm), space = O(m)
    public int[] kWeakestRows(int[][] mat, int k) {
        int m = mat.length, n = mat[0].length;
        int[][] arr = new int[m][2];

        for (int i = 0; i < m; i++) {
            int j = 0;
            while (j < n && mat[i][j] == 1) j++;
            arr[i] = new int[]{j, i};
        }

        Arrays.sort(arr, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = arr[i][1];
        }
        return res;
    }
}
