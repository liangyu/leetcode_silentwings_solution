package LC1801_2100;
import java.util.*;
public class LC1901_FindaPeakElementII {
    /**
     * A peak element in a 2D grid is an element that is strictly greater than all of its adjacent neighbors to the left,
     * right, top, and bottom.
     *
     * Given a 0-indexed m x n matrix mat where no two adjacent cells are equal, find any peak element mat[i][j] and
     * return the length 2 array [i,j].
     *
     * You may assume that the entire matrix is surrounded by an outer perimeter with the value -1 in each cell.
     *
     * You must write an algorithm that runs in O(m log(n)) or O(n log(m)) time.
     *
     * Input: mat = [[1,4],[3,2]]
     * Output: [0,1]
     *
     * Constraints:
     *
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 500
     * 1 <= mat[i][j] <= 10^5
     * No two adjacent cells are equal.
     * @param mat
     * @return
     */
    // time = O(nlogm), space = O(1)
    public int[] findPeakGrid(int[][] mat) {
        // corner case
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0) return new int[0];

        int m = mat.length, n = mat[0].length;
        int left = 0, right = m - 1, idx = 0;
        while (left < right) {
            int mid = left + (right - left) / 2;
            idx = findPeak(mat[mid]);
            if (mat[mid][idx] < mat[mid + 1][idx]) left = mid + 1;
            else right = mid;
        }
        return new int[]{left, idx};
    }

    private int findPeak(int[] arr) {
        int idx = 0, max = 0;
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
                idx = i;
            }
        }
        return idx;
    }
}
