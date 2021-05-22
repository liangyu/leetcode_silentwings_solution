package LC901_1200;
import java.util.*;
public class LC1198_FindSmallestCommonElementinAllRows {
    /**
     * Given a matrix mat where every row is sorted in strictly increasing order, return the smallest common element in
     * all rows.
     *
     * If there is no common element, return -1.
     *
     * Input: mat = [[1,2,3,4,5],[2,4,5,8,10],[3,5,7,9,11],[1,3,5,7,9]]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= mat.length, mat[i].length <= 500
     * 1 <= mat[i][j] <= 10^4
     * mat[i] is sorted in strictly increasing order.
     * @param mat
     * @return
     */
    // time = O(m * nlogn), space = O(n)
    public int smallestCommonElement(int[][] mat) {
        // corner case
        if (mat.length == 1) return mat[0][0];

        TreeSet<Integer> set1 = new TreeSet<>();


        int m = mat.length, n = mat[0].length;
        for (int i = 0; i < m; i++) {
            TreeSet<Integer> set2 = new TreeSet<>();
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    set1.add(mat[0][j]); // O(logn)
                    continue;
                } else {
                    if (set1.contains(mat[i][j])) set2.add(mat[i][j]); // O(logn)
                }
            }
            if (i > 0) set1 = set2;
        }
        return set1.size() == 0 ? -1 : set1.first();
    }
}
