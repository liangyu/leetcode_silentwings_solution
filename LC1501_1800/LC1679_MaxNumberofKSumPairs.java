package LC1501_1800;
import java.util.*;
public class LC1679_MaxNumberofKSumPairs {
    /**
     * You are given an integer array nums and an integer k.
     *
     * In one operation, you can pick two numbers from the array whose sum equals k and remove them from the array.
     *
     * Return the maximum number of operations you can perform on the array.
     *
     * Input: nums = [1,2,3,4], k = 5
     * Output: 2
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= k <= 10^9
     *
     * @param nums
     * @param k
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public int maxOperations(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = nums.length, res = 0;
        for (int i = 0; i < n; i++) {
            int diff = k - nums[i];
            if (map.containsKey(diff) && map.get(diff) > 0) {
                res++;
                map.put(diff, map.get(diff) - 1);
            } else map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        return res;
    }

    // S2
    // time = O(nlogn), space = O(1)
    public int maxOperations2(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length, i = 0, j = n - 1, res = 0;

        while (i < j) {
            if (nums[i] + nums[j] == k) {
                i++;
                j--;
                res++;
            } else if (nums[i] + nums[j] < k) i++;
            else j--;
        }
        return res;
    }
}
