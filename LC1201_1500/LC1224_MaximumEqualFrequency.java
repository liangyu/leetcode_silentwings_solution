package LC1201_1500;
import java.util.*;
public class LC1224_MaximumEqualFrequency {
    /**
     * Given an array nums of positive integers, return the longest possible length of an array prefix of nums, such
     * that it is possible to remove exactly one element from this prefix so that every number that has appeared in it
     * will have the same number of occurrences.
     *
     * If after removing one element there are no remaining elements, it's still considered that every appeared number
     * has the same number of ocurrences (0).
     *
     * Input: nums = [2,2,1,1,5,3,3,5]
     * Output: 7
     *
     * Constraints:
     *
     * 2 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int maxEqualFreq(int[] nums) {
        int n = nums.length, res = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        HashMap<Integer, Integer> freq = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            int val = map.get(nums[i]);
            freq.put(val, freq.getOrDefault(val, 0) + 1);

            int count = freq.get(val) * val;
            if (count == i + 1 && i != n - 1) res = Math.max(res, i + 2); // case1: 需要后面继续补1位，长度变为i + 2
            else if (count == i) res = Math.max(res, i + 1); // 不需要后面补1位，已经满足条件，当前subarray长度为i + 1
        }
        return res;
    }
}
/**
 * Count frequency of the elements
 * We also need to count the number of elements with that frequency
 * There are 2 cases where we need to update the result:
 * Case 1:
 * frequency * (number of elements with that frequency) == length AND i != nums.length - 1
 * E.g. [1,1,2,2,3]
 * When the iteration is at index 3, the count will be equal to the length.
 * It should update the result with (length + 1) as it should take an extra element in order to fulfil the condition.
 *
 * Case 2:
 * frequency * (number of elements with that frequency) == length - 1
 * E.g. [1,1,1,2,2,3]
 * When the iteration is at index 4, the count will be equal to (length - 1).
 * It should update the result with length as it fulfil the condition.
 */
