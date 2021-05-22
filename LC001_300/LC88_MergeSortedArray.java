package LC001_300;
import java.util.*;
public class LC88_MergeSortedArray {
    /**
     * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
     *
     * The number of elements initialized in nums1 and nums2 are m and n respectively. You may assume that nums1 has a
     * size equal to m + n such that it has enough space to hold additional elements from nums2.
     *
     * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * Output: [1,2,2,3,5,6]
     *
     * Constraints:
     *
     * nums1.length == m + n
     * nums2.length == n
     * 0 <= m, n <= 200
     * 1 <= m + n <= 200
     * -10^9 <= nums1[i], nums2[i] <= 10^9
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    // time = O(m + n), space = O(1)
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // corner case
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) return;

        int i = m - 1, j = n - 1, idx = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] >= nums2[j]) nums1[idx--] = nums1[i--];
            else nums1[idx--] = nums2[j--];
        }
        while (j >= 0) nums1[idx--] = nums2[j--];
    }
}
