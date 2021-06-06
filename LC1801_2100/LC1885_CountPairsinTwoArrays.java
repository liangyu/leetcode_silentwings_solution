package LC1801_2100;
import java.util.*;
public class LC1885_CountPairsinTwoArrays {
    /**
     * Given two integer arrays nums1 and nums2 of length n, count the pairs of indices (i, j) such that i < j and
     * nums1[i] + nums1[j] > nums2[i] + nums2[j].
     *
     * Return the number of pairs satisfying the condition.
     *
     * Input: nums1 = [2,1,2,1], nums2 = [1,2,1,2]
     * Output: 1
     *
     * Constraints:
     *
     * n == nums1.length == nums2.length
     * 1 <= n <= 10^5
     * 1 <= nums1[i], nums2[i] <= 10^5
     * @param nums1
     * @param nums2
     * @return
     */
    // time = O(nlogn), space = O(n)
    public long countPairs(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] diff = new int[n];
        for (int i = 0; i < n; i++) diff[i] = nums1[i] - nums2[i];
        Arrays.sort(diff);

        long res = 0;
        int left = 0, right = n - 1;
        while (left < right) {
            int dl = diff[left], dr = diff[right];
            if (Math.abs(dl) < Math.abs(dr) || dl > 0) { // all pairs of {left++, right} will be the answer
                res += right - left;
                right--;
            } else left++; // dl < 0 -> diff to small; abs(dl) > abs(dr) -> dl < 0
        }
        return res;
    }
}
/**
 * Compute the difference of nums1 and nums2,
 * sort the diff and use 2 pointer approach to find out how many pairs have sum greater than 0.
 */