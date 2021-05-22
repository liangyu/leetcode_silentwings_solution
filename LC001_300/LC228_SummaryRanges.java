package LC001_300;
import java.util.*;
public class LC228_SummaryRanges {
    /**
     * You are given a sorted unique integer array nums.
     *
     * Return the smallest sorted list of ranges that cover all the numbers in the array exactly. That is, each element
     * of nums is covered by exactly one of the ranges, and there is no integer x such that x is in one of the ranges
     * but not in nums.
     *
     * Each range [a,b] in the list should be output as:
     *
     * "a->b" if a != b
     * "a" if a == b
     *
     * Input: nums = [0,1,2,4,5,7]
     * Output: ["0->2","4->5","7"]
     *
     * Constraints:
     *
     * 0 <= nums.length <= 20
     * -2^31 <= nums[i] <= 2^31 - 1
     * All the values of nums are unique.
     * nums is sorted in ascending order.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        // corner case
        if (nums == null || nums.length == 0) return res;

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            while (i < nums.length - 1 && nums[i] + 1 == nums[i + 1]) i++;
            if (num != nums[i]) res.add(num + "->" + nums[i]);
            else res.add(num + "");
        }
        return res;
    }
}
