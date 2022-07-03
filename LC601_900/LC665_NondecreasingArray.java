package LC601_900;
import java.util.*;
public class LC665_NondecreasingArray {
    /**
     * Given an array nums with n integers, your task is to check if it could become non-decreasing by modifying at
     * most one element.
     *
     * We define an array is non-decreasing if nums[i] <= nums[i + 1] holds for every i (0-based) such that
     * (0 <= i <= n - 2).
     *
     * Input: nums = [4,2,3]
     * Output: true
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 10^4
     * -10^5 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public boolean checkPossibility(int[] nums) {
        // corner case
        if (nums == null || nums.length < 2) return true;

        int violation = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                if (violation == 1) return false;
                violation++;
                if (i < 2 || nums[i - 2] <= nums[i]) nums[i - 1] = nums[i];
                else nums[i] = nums[i - 1];
            }
        }
        return true;
    }

    // S2:
    // time = O(n), space = O(1)  最多只会遍历2次数组 => O(2 * n)
    public boolean checkPossibility2(int[] nums) {
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i - 1]) {
                int a = nums[i - 1], b = nums[i];
                nums[i - 1] = nums[i] = a;
                if (check(nums)) return true;
                nums[i - 1] = nums[i] = b;
                if (check(nums)) return true;
                return false;
            }
        }
        return true;
    }

    private boolean check(int[] nums) {
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i - 1]) return false;
        }
        return true;
    }
}
