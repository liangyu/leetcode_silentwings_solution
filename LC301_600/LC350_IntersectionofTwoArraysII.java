package LC301_600;
import java.util.*;
public class LC350_IntersectionofTwoArraysII {
    /**
     * Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must
     * appear as many times as it shows in both arrays and you may return the result in any order.
     *
     * Input: nums1 = [1,2,2,1], nums2 = [2,2]
     * Output: [2,2]
     *
     * Constraints:
     *
     * 1 <= nums1.length, nums2.length <= 1000
     * 0 <= nums1[i], nums2[i] <= 1000
     *
     *
     * Follow up:
     *
     * What if the given array is already sorted? How would you optimize your algorithm?
     * What if nums1's size is small compared to nums2's size? Which algorithm is better?
     * What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements
     * into the memory at once?
     * @param nums1
     * @param nums2
     * @return
     */
    // S1: HashMap
    // time = O(m + n), space = O(m + n)
    public int[] intersect(int[] nums1, int[] nums2) {
        // corner case
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) return new int[0];

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int n : nums1) map.put(n, map.getOrDefault(n, 0) + 1);

        int k = 0;
        for (int n : nums2) {
            int count = map.getOrDefault(n, 0);
            if (count > 0) {
                nums1[k++] = n;
                map.put(n, count - 1);
            }
        }
        return Arrays.copyOf(nums1, k);
    }

    // S2: Sort
    // time = O(mlogm + nlogn), space = O(1)
    public int[] intersect2(int[] nums1, int[] nums2) {
        // corner case
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) return new int[0];

        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int i = 0, j = 0, k = 0;
        int m = nums1.length, n = nums2.length;
        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) i++;
            else if (nums1[i] > nums2[j]) j++;
            else {
                nums1[k++] = nums1[i++];
                j++;
            }
        }
        return Arrays.copyOf(nums1, k);
    }
}
