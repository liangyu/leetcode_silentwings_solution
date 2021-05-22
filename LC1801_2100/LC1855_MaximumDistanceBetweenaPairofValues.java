package LC1801_2100;
import java.util.*;
public class LC1855_MaximumDistanceBetweenaPairofValues {
    /**
     * You are given two non-increasing 0-indexed integer arrays nums1 and nums2.
     *
     * A pair of indices (i, j), where 0 <= i < nums1.length and 0 <= j < nums2.length, is valid if both i <= j and
     * nums1[i] <= nums2[j]. The distance of the pair is j - i.
     *
     * Return the maximum distance of any valid pair (i, j). If there are no valid pairs, return 0.
     *
     * An array arr is non-increasing if arr[i-1] >= arr[i] for every 1 <= i < arr.length.
     *
     * Input: nums1 = [55,30,5,4,2], nums2 = [100,20,10,10,5]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums1.length <= 10^5
     * 1 <= nums2.length <= 10^5
     * 1 <= nums1[i], nums2[j] <= 10^5
     * Both nums1 and nums2 are non-increasing.
     * @param nums1
     * @param nums2
     * @return
     */
    // time = O(m + n), space = O(1)
    public int maxDistance(int[] nums1, int[] nums2) {
        int res = 0, i = 0;
        int m = nums1.length, n = nums2.length;
        for (int j = 0; j < n; j++) { // O(n)
            while (i < m && nums1[i] > nums2[j]) i++; // sliding window收缩，要找到比res更大的，只能从原来的i出发继续向后找
            if (i == m) break;
            res = Math.max(res, j - i); // even if j - i < 0, it can be ignored by Math.max(0, j - i)
        }
        return res;
    }
}
