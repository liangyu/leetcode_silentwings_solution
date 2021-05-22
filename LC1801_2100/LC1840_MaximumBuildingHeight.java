package LC1801_2100;
import java.util.*;
public class LC1840_MaximumBuildingHeight {
    /**
     * You want to build n new buildings in a city. The new buildings will be built in a line and are labeled from 1 to n.
     *
     * However, there are city restrictions on the heights of the new buildings:
     *
     * The height of each building must be a non-negative integer.
     * The height of the first building must be 0.
     * The height difference between any two adjacent buildings cannot exceed 1.
     * Additionally, there are city restrictions on the maximum height of specific buildings. These restrictions are
     * given as a 2D integer array restrictions where restrictions[i] = [idi, maxHeighti] indicates that building idi
     * must have a height less than or equal to maxHeighti.
     *
     * It is guaranteed that each building will appear at most once in restrictions, and building 1 will not be in
     * restrictions.
     *
     * Return the maximum possible height of the tallest building.
     *
     * Input: n = 5, restrictions = [[2,1],[4,1]]
     * Output: 2
     *
     * Input: n = 6, restrictions = []
     * Output: 5
     *
     * Constraints:
     *
     * 2 <= n <= 10^9
     * 0 <= restrictions.length <= min(n - 1, 10^5)
     * 2 <= idi <= n
     * idi is unique.
     * 0 <= maxHeighti <= 10^9
     * @param n
     * @param restrictions
     * @return
     */
    // time = O(nlogn), space = O(n);
    public int maxBuilding(int n, int[][] restrictions) {
        // corner case
        if (restrictions == null || restrictions.length == 0) return n - 1;

        // step 1: init + sort
        int len = restrictions.length;
        int[][] r = new int[len + 2][2];
        r[0][0] = 1;
        r[0][1] = 0;

        int k = 1;
        for (int[] res : restrictions) {
            r[k][0] = res[0];
            r[k++][1] = res[1];
        }
        r[k][0] = n;
        r[k][1] = n - 1;

        Arrays.sort(r, (o1, o2) -> o1[0] - o2[0]);

        // step 2: right - > left
        for (int i = r.length - 2; i >= 0; i--) {
            r[i][1] = Math.min(r[i][1], r[i + 1][1] + r[i + 1][0] - r[i][0]);
        }

        // step 3: left - > right
        int res = 0;
        for (int i = 1; i < r.length; i++) {
            r[i][1] = Math.min(r[i][1], r[i - 1][1] + r[i][0] - r[i - 1][0]);
            int val = r[i][0] - r[i - 1][0] - Math.abs(r[i][1] - r[i - 1][1]);
            res = Math.max(res, val / 2 + Math.max(r[i][1], r[i - 1][1]));
        }
        return res;
    }
}
/**
 * h[i] - h[i - 1] <= p[i] - p[i -1] 高度差不能大于它们之间的水平间隔
 * h[1] = 0
 * h[i] = Math.min(lim[i], h[i - 1] + p[i] - p[i - 1])
 * h[i] = Math.min(h[i], h[i + 1] + p[i + 1] - p[i])
 * 2个关系式：
 * h[i - 1] + x = h[i] + y
 * p[i - 1] + x = p[i] - y
 */
