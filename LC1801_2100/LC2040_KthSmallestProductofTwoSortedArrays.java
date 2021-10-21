package LC1801_2100;

public class LC2040_KthSmallestProductofTwoSortedArrays {
    /**
     * Given two sorted 0-indexed integer arrays nums1 and nums2 as well as an integer k, return the kth (1-based)
     * smallest product of nums1[i] * nums2[j] where 0 <= i < nums1.length and 0 <= j < nums2.length.
     *
     * Input: nums1 = [2,5], nums2 = [3,4], k = 2
     * Output: 8
     *
     * Constraints:
     *
     * 1 <= nums1.length, nums2.length <= 5 * 10^4
     * -10^5 <= nums1[i], nums2[j] <= 10^5
     * 1 <= k <= nums1.length * nums2.length
     * nums1 and nums2 are sorted.
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    // S1: BS
    // time = O(mlogn * log(10^10)), space = O(1)
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        long left = (long)(-1e10), right = (long)1e10;
        while (left < right) { // O(log(10^10)
            long mid = left + (right - left) / 2;
            long count = countSmallerOrEqual(nums1, nums2, mid);
            if (count < k) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    private long countSmallerOrEqual(int[] nums1, int[] nums2, long t) {
        int m = nums1.length, n = nums2.length;
        long res = 0;
        for (int i = 0; i < m; i++) { // O(m)
            if (nums1[i] > 0) {
                int idx = upperBound(nums2, (int)Math.floor(t * 1.0 / nums1[i])); // O(logn)
                res += idx + 1;
            } else if (nums1[i] == 0) {
                if (t >= 0) res += n;
            } else {
                int idx = lowerBound(nums2, (int)Math.ceil(t * 1.0 / nums1[i]));
                res += n - idx;
            }
        }
        return res;
    }

    private int upperBound(int[] nums, int t) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (nums[mid] <= t) left = mid;
            else right = mid - 1;
        }
        return nums[left] <= t ? left : left - 1;
    }

    private int lowerBound(int[] nums, int t) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < t) left = mid + 1;
            else right = mid;
        }
        return nums[left] >= t ? left : left + 1;
    }

    // S2: BS + Two Pointers
    // time = O((m + n) * log(10^10), space = O(1)
    public long kthSmallestProduct2(int[] nums1, int[] nums2, long k) {
        long left = (long)(-1e10), right = (long)1e10;
        while (left < right) {
            long mid = left + (right - left) / 2;
            long count = countSmallerOrEqual(nums1, nums2, mid);
            if (count < k) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    private long countSmallerOrEqual2(int[] nums1, int[] nums2, long t) {
        int m = nums1.length, n = nums2.length;
        long res = 0;

        if (t >= 0) {
            int j0 = n - 1, j1 = n - 1;
            for (int i = 0; i < m; i++) {
                if (nums1[i] > 0) {
                    while (j0 >= 0 && (long)nums1[i] * nums2[j0] > t) j0--;
                    res += j0 + 1;
                } else if (nums1[i] == 0) res += n;
                else {
                    while (j1 >= 0 && (long)nums1[i] * nums2[j1] <= t) j1--;
                    res += n - 1 - j1;
                }
            }
        } else {
            int j0 = 0, j1 = 0;
            for (int i = 0; i < m; i++) {
                if (nums1[i] > 0) {
                    while (j0 < n && (long)nums1[i] * nums2[j0] <= t) j0++;
                    res += j0;
                } else if (nums1[i] == 0) res += 0;
                else {
                    while (j1 < n && (long)nums1[i] * nums2[j1] > t) j1++;
                    res += n - j1;
                }
            }
        }
        return res;
    }
}
/**
 * [lower，upper] = m
 * count = countSmallerOrEqual(m)
 * if (count < k) left = m + 1;
 * else right = m;
 * nums1[i] * num2[j] <= m => 找临界值即可
 * 1. nums1[i] > 0 => nums2[j] <= Math.floor(m * 1.0 / nums1[i])  => nums1[i]得是正数 => res += j + 1;
 * 2. if (nums1[i] == 0) => if (m >= 0) res += nums2.length; // 可以和nums2所有元素配对; else res += 0;
 * 3. if (nums1[i] < 0) => nums2[j] >= Math.ceil(m * 1.0 / num1[i]) => [j, n - 1] => res += n - 1 - j + 1 = n - j;
 * 只利用了nums2的单调性，没用到nums1? => 双指针 O(n)
 * nums1[i]* nums2[j] <= m
 * m>=0
 *
 */
