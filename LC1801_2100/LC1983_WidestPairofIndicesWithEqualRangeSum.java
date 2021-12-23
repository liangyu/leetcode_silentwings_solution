package LC1801_2100;
import java.util.*;
public class LC1983_WidestPairofIndicesWithEqualRangeSum {
    /**
     * You are given two 0-indexed binary arrays nums1 and nums2. Find the widest pair of indices (i, j) such that
     * i <= j and nums1[i] + nums1[i+1] + ... + nums1[j] == nums2[i] + nums2[i+1] + ... + nums2[j].
     *
     * The widest pair of indices is the pair with the largest distance between i and j. The distance between a pair
     * of indices is defined as j - i + 1.
     *
     * Return the distance of the widest pair of indices. If no pair of indices meets the conditions, return 0.
     *
     * Input: nums1 = [1,1,0,1], nums2 = [0,1,1,0]
     * Output: 3
     *
     * Constraints:
     *
     * n == nums1.length == nums2.length
     * 1 <= n <= 10^5
     * nums1[i] is either 0 or 1.
     * nums2[i] is either 0 or 1.
     * @param nums1
     * @param nums2
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public int widestPairOfIndices(int[] nums1, int[] nums2) {
        int n = nums1.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // [0] [0]

        int diff = 0, res = 0;
        for (int i = 0; i < n; i++) {
            diff += nums1[i] - nums2[i];
            if (map.containsKey(diff)) {
                res = Math.max(res, i - map.get(diff)); // ( ]
            } else map.put(diff, i);
        }
        return res;
    }

    // S2
    // time = O(n), space = O(n)
    public int widestPairOfIndices2(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] pre1 = new int[n + 1], pre2 = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            pre1[i] = pre1[i - 1] + nums1[i - 1];
            pre2[i] = pre2[i - 1] + nums2[i - 1];
        }

        int[] diff = new int[n];
        for (int i = 0; i < n; i++) diff[i] = pre1[i + 1] - pre2[i + 1];

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (map.containsKey(diff[i])) {
                res = Math.max(res, i - map.get(diff[i]));
            }
            if (!map.containsKey(diff[i])) map.put(diff[i], i);
        }
        return res;
    }
}
/**
 * [1,1,0,1]    1  x x x  => 1 + X
 * [0,1,1,0]    0  x x x  => 0 + Y
 * X == Y (diff不变)  => 左开右闭区间 => len = i - map.get(diff)
 */