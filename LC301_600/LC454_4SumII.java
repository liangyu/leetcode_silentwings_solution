package LC301_600;
import java.util.*;
public class LC454_4SumII {
    /**
     * Given four integer arrays nums1, nums2, nums3, and nums4 all of length n, return the number of tuples (i, j, k,
     * l) such that:
     *
     * 0 <= i, j, k, l < n
     * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
     *
     * Input: nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
     * Output: 2
     *
     * Constraints:
     *
     * n == nums1.length
     * n == nums2.length
     * n == nums3.length
     * n == nums4.length
     * 1 <= n <= 200
     * -228 <= nums1[i], nums2[i], nums3[i], nums4[i] <= 228
     * @param nums1
     * @param nums2
     * @param nums3
     * @param nums4
     * @return
     */
    // S1
    // time = O(n^2), space = O(n^2)
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int a : nums1) {
            for (int b : nums2) {
                map.put(a + b, map.getOrDefault(a + b, 0) + 1);
            }
        }

        int count = 0;
        for (int c : nums3) {
            for (int d : nums4) {
                 count += map.getOrDefault(-(c + d), 0);
            }
        }
        return count;
    }

    // S2: ksum
    // time = O(n^2), space = O(n2)
    public int fourSumCount2(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        return countKSum(new int[][]{nums1, nums2, nums3, nums4});
    }

    private int countKSum(int[][] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        addToHash(nums, map, 0, 0);
        return helper(nums, map, nums.length / 2, 0);
    }

    private void addToHash(int[][] nums, HashMap<Integer, Integer> map, int i, int sum) {
        int n = nums.length;
        if (i == n / 2) map.put(sum, map.getOrDefault(sum, 0) + 1);
        else {
            for (int x : nums[i]) addToHash(nums, map, i + 1, sum + x);
        }
    }

    private int helper(int[][] nums, HashMap<Integer, Integer> map, int i, int complement) {
        int n = nums.length;
        if (i == n) return map.getOrDefault(complement, 0);

        int count = 0;
        for (int x : nums[i]) count += helper(nums, map, i + 1, complement - x);
        return count;
    }
}
