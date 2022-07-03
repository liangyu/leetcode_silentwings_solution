package LC1201_1500;
import java.util.*;
public class LC1480_RunningSumof1dArray {
    /**
     * Given an array nums. We define a running sum of an array as runningSum[i] = sum(nums[0]…nums[i]).
     *
     * Return the running sum of nums.
     *
     * Input: nums = [1,2,3,4]
     * Output: [1,3,6,10]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * -10^6 <= nums[i] <= 10^6
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int[] runningSum(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) nums[i] += i == 0 ? 0 : nums[i - 1];
        return nums;
    }
}