package LC301_600;
import java.util.*;
public class LC349_IntersectionofTwoArrays {
    /**
     * Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must
     * be unique and you may return the result in any order.
     *
     * Input: nums1 = [1,2,2,1], nums2 = [2,2]
     * Output: [2]
     *
     * Constraints:
     *
     * 1 <= nums1.length, nums2.length <= 1000
     * 0 <= nums1[i], nums2[i] <= 1000
     * @param nums1
     * @param nums2
     * @return
     */
    // S1: Two Sets
    // time = O(m + n), space = O(m + n)
    public int[] intersection(int[] nums1, int[] nums2) {
        // corner case
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) return new int[0];

        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();

        for (int n : nums1) set1.add(n);
        for (int n : nums2) {
            if (set1.contains(n)) set2.add(n);
        }

        int[] res = new int[set2.size()];
        int k = 0;
        for (int n : set2) res[k++] = n;
        return res;
    }

    // S2:
    // time = O(mlogm + nlogn), space = O(1)
    public int[] intersection2(int[] nums1, int[] nums2) {
        // corner case
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) return new int[0];

        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int m = nums1.length, n = nums2.length;
        int[] arr = new int[m];
        int idx = 0;

        int i = 0, j = 0;
        while (i < m && j < n) {
            int val1 = nums1[i], val2 = nums2[j];
            if (val1 == val2) {
                arr[idx++] = val1;
                while (i < m && val1 == nums1[i]) i++;
                while (j < n && val2 == nums2[j]) j++;
            } else if (val1 < val2) {
                while (i < m && val1 == nums1[i]) i++;
            } else {
                while (j < n && val2 == nums2[j]) j++;
            }
        }
        return Arrays.copyOf(arr, idx);
    }
}
