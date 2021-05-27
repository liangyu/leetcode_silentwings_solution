package LC1801_2100;
import java.util.*;
public class LC1874_MinimizeProductSumofTwoArrays {
    /**
     * The product sum of two equal-length arrays a and b is equal to the sum of a[i] * b[i] for all 0 <= i < a.length
     * (0-indexed).
     *
     * For example, if a = [1,2,3,4] and b = [5,2,3,1], the product sum would be 1*5 + 2*2 + 3*3 + 4*1 = 22.
     * Given two arrays nums1 and nums2 of length n, return the minimum product sum if you are allowed to rearrange the
     * order of the elements in nums1.
     *
     * Input: nums1 = [5,3,4,2], nums2 = [4,2,2,5]
     * Output: 40
     *
     * Input: nums1 = [2,1,4,5,7], nums2 = [3,2,4,8,6]
     * Output: 65
     *
     * Constraints:
     *
     * n == nums1.length == nums2.length
     * 1 <= n <= 10^5
     * 1 <= nums1[i], nums2[i] <= 100
     * @param nums1
     * @param nums2
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int minProductSum(int[] nums1, int[] nums2) {
        // corner case
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) return 0;

        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int n = nums1.length, res = 0;
        for (int i = 0; i < n; i++) res += nums1[i] * nums2[n - 1 - i];
        return res;
    }
}
/**
 *             4 2 2 5
 * 5 3 4 2 ->  3 5 4 2
 *
 *              3 2 4 8 6
 * 2 1 4 5 7 -> 5 7 4 1 2
 */
