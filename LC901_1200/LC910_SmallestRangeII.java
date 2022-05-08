package LC901_1200;
import java.util.*;
public class LC910_SmallestRangeII {
    /**
     * You are given an integer array nums and an integer k.
     *
     * For each index i where 0 <= i < nums.length, change nums[i] to be either nums[i] + k or nums[i] - k.
     *
     * The score of nums is the difference between the maximum and minimum elements in nums.
     *
     * Return the minimum score of nums after changing the values at each index.
     *
     * Input: nums = [1], k = 0
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^4
     * 0 <= nums[i] <= 10^4
     * 0 <= k <= 10^4
     * @param nums
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int smallestRangeII(int[] nums, int k) {
        Arrays.sort(nums);

        int n = nums.length;
        int diff = nums[n - 1] - nums[0];
        for (int i = 0; i < n - 1; i++) {
            int max = Math.max(nums[i] + k, nums[n - 1] - k);
            int min = Math.min(nums[0] + k, nums[i + 1] - k);
            diff = Math.min(diff, max - min);
        }
        return diff;
    }
}
/**
 * 尽量均"贫富"
 * 1 2 3 | 4 5 6 7 8 9
 * + + +   - - - - - -
 * 有个分界线，左半部分都是+k，右半部分都是-k
 * a < b < c，如果我们决策是 a+K, b-K, c+K 的话，b和c之间的差距不仅没有减小反而被拉大了。
 * 唯一的方案就是：左边一部分加K，右边一部分减K。
 * 要确定分界点在哪里？
 */
