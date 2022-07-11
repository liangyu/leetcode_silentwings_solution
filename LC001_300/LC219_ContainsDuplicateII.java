package LC001_300;
import java.util.*;
public class LC219_ContainsDuplicateII {
    /**
     * Given an integer array nums and an integer k, return true if there are two distinct indices i and j in the array
     * such that nums[i] == nums[j] and abs(i - j) <= k.
     *
     * Input: nums = [1,2,3,1], k = 3
     * Output: true
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -10^9 <= nums[i] <= 10^9
     * 0 <= k <= 10^5
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return false;

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                if (Math.abs(i - map.get(nums[i])) <= k) return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }
}
