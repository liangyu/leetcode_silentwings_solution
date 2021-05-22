package LC001_300;
import java.util.*;
public class LC163_MissingRanges {
    /**
     * You are given an inclusive range [lower, upper] and a sorted unique integer array nums, where all elements are
     * in the inclusive range.
     *
     * A number x is considered missing if x is in the range [lower, upper] and x is not in nums.
     *
     * Return the smallest sorted list of ranges that cover every missing number exactly. That is, no element of nums
     * is in any of the ranges, and each missing number is in one of the ranges.
     *
     * Each range [a,b] in the list should be output as:
     *
     * "a->b" if a != b
     * "a" if a == b
     *
     * Input: nums = [0,1,3,50,75], lower = 0, upper = 99
     * Output: ["2","4->49","51->74","76->99"]
     *
     * Constraints:
     *
     * -10^9 <= lower <= upper <= 10^9
     * 0 <= nums.length <= 100
     * lower <= nums[i] <= upper
     * All the values of nums are unique.
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    // time = O(n), space = O(1)
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> res = new ArrayList<>();
        // corner case
        if (nums == null || nums.length == 0) {
            res.add(helper(lower, upper)); // 空的range也要返回[lower, higher]这一段！！！
            return res;
        }

        // case 1: treat the start
        if (nums[0] > lower) res.add(helper(lower, nums[0] - 1));

        // case 2: treat the middle: not equal or next to each other
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] != nums[i] && nums[i + 1] > nums[i] + 1) {
                res.add(helper(nums[i] + 1, nums[i + 1] - 1));
            }
        }

        // case 3: treat the end
        if (nums[nums.length - 1] < upper) res.add(helper(nums[nums.length - 1] + 1, upper));

        return res;
    }

    private String helper(int low, int high) {
        return low == high? String.valueOf(low) : low + "->" + high; // 注意考虑low == high的情况
    }
}
