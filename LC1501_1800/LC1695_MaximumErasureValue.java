package LC1501_1800;
import java.util.*;
public class LC1695_MaximumErasureValue {
    /**
     * You are given an array of positive integers nums and want to erase a subarray containing unique elements. The
     * score you get by erasing the subarray is equal to the sum of its elements.
     *
     * Return the maximum score you can get by erasing exactly one subarray.
     *
     * An array b is called to be a subarray of a if it forms a contiguous subsequence of a, that is, if it is equal to
     * a[l],a[l+1],...,a[r] for some (l,r).
     *
     * Input: nums = [4,2,4,5,6]
     * Output: 17
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^4
     * @param nums
     * @return
     */
    // S1: HashSet
    // time = O(n), space = O(n)
    public int maximumUniqueSubarray(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        HashSet<Integer> set = new HashSet<>();
        int sum = 0, max = 0, start = 0;
        for (int i = 0; i < nums.length; i++) {
            if (set.add(nums[i])) {
                sum += nums[i];
                max = Math.max(sum, max);
            } else {
                sum -= nums[start];
                set.remove(nums[start++]);
                i--; // 保持i不变
            }
        }
        return max;
    }

    // S2: HashMap
    // time = O(n), space = O(n)
    public int maximumUniqueSubarray2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        int start = 0, max = 0;

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                int sum = calSum(nums, start, i - 1);
                if (start <= map.get(nums[i])) start = map.get(nums[i]) + 1;
                max = Math.max(max, sum);
            }
            map.put(nums[i], i);
        }
        max = Math.max(max, calSum(nums, start, nums.length - 1));
        return max;
    }

    private int calSum(int[] nums, int start, int end) {
        int res = 0;
        for (int i = start; i <= end; i++) {
            res += nums[i];
        }
        return res;
    }
}
/**
 * ref: LC3
 */

