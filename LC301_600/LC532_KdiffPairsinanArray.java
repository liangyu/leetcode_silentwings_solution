package LC301_600;
import java.util.*;
public class LC532_KdiffPairsinanArray {
    /**
     * Given an array of integers nums and an integer k, return the number of unique k-diff pairs in the array.
     *
     * A k-diff pair is an integer pair (nums[i], nums[j]), where the following are true:
     *
     * 0 <= i < j < nums.length
     * |nums[i] - nums[j]| == k
     * Notice that |val| denotes the absolute value of val.
     *
     * Input: nums = [3,1,4,1,5], k = 2
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^4
     * -10^7 <= nums[i] <= 10^7
     * 0 <= k <= 107
     * @param nums
     * @param k
     * @return
     */
    // S1: Two Pointers
    // time = O(nlogn), space = O(n) -> from sort
    public int findPairs(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length, j = 0, count = 0;
        for (int i = 0; i < n; i++) {
            while (i + 1 < n && nums[i] == nums[i + 1]) i++;
            while (j < i && nums[i] - nums[j] > k) j++;
            if (j < i && nums[i] - nums[j] == k) count++;
        }
        return count;
    }

    // S2: HashMap
    // time = O(n), space = O(n)
    public int findPairs2(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) map.put(num, map.getOrDefault(num, 0) + 1);

        int count = 0;
        for (int key : map.keySet()) {
            if (k == 0) {
                if (map.get(key) >= 2) count++;
            } else {
                if (map.containsKey(key + k)) count++;
            }
        }
        return count;
    }
}
