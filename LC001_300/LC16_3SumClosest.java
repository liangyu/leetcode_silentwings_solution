package LC001_300;
import java.util.*;
public class LC16_3SumClosest {
    /**
     * Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest
     * to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.
     *
     * Input: nums = [-1,2,1,-4], target = 1
     * Output: 2
     *
     * Constraints:
     *
     * 3 <= nums.length <= 10^3
     * -10^3 <= nums[i] <= 10^3
     * -10^4 <= target <= 10^4
     * @param nums
     * @param target
     * @return
     */
    // time = O(n^2), space = O(1)
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int res = nums[0] + nums[1] + nums[2];

        int n = nums.length;
        for (int i = 0; i < n - 2; i++) {
            int start = i + 1, end = n - 1;
            while (start < end) {
                int sum = nums[i] + nums[start] + nums[end];
                if (sum > target) end--;
                else start++;
                if (Math.abs(sum - target) < Math.abs(res - target)) res = sum;
            }
        }
        return res;
    }
}
