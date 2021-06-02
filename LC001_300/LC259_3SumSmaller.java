package LC001_300;
import java.util.*;
public class LC259_3SumSmaller {
    /**
     * Given an array of n integers nums and an integer target, find the number of index triplets i, j, k
     * with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.
     *
     * Input: nums = [-2,0,1,3], target = 2
     * Output: 2
     *
     * Constraints:
     *
     * n == nums.length
     * 0 <= n <= 3500
     * -100 <= nums[i] <= 100
     * -100 <= target <= 100
     * @param nums
     * @param target
     * @return
     */
    // time = O(n^2), space = O(logn)
    public int threeSumSmaller(int[] nums, int target) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        Arrays.sort(nums);

        int n = nums.length, res = 0;
        for (int i = 0; i < n; i++) {
            int left = i + 1, right = n - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum < target) {
                    res += right - left;
                    left++;
                } else right--;
            }
        }
        return res;
    }
}
