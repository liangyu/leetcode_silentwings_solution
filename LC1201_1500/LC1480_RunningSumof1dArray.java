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
        // corner case
        if (nums == null || nums.length == 0) return new int[0];

        int[] res = new int[nums.length];
        res[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] + nums[i];
        }
        return res;
    }
}
