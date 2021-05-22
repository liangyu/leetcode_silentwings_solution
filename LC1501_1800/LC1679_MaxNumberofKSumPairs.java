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
    // time = O(n), space = O(n)
    public int maxOperations(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;

        for (int n : nums) {
            int diff = k - n;
            if (map.containsKey(diff)) {
                map.put(diff, map.get(diff) - 1);
                count++;
                if (map.get(diff) == 0) { // delete depleted element to eliminate the key
                    map.remove(diff);
                }
            } else { // if not able to remove, then add it to the hashmap
                map.put(n, map.getOrDefault(n, 0) + 1);
            }
        }
        return count;
    }
}
