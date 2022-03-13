package LC1801_2100;
import java.util.*;
public class LC1856_MaximumSubarrayMinProduct {
    /**
     * The min-product of an array is equal to the minimum value in the array multiplied by the array's sum.
     *
     * For example, the array [3,2,5] (minimum value is 2) has a min-product of 2 * (3+2+5) = 2 * 10 = 20.
     * Given an array of integers nums, return the maximum min-product of any non-empty subarray of nums. Since the
     * answer may be large, return it modulo 10^9 + 7.
     *
     * Note that the min-product should be maximized before performing the modulo operation. Testcases are generated
     * such that the maximum min-product without modulo will fit in a 64-bit signed integer.
     *
     * A subarray is a contiguous part of an array.
     *
     * Input: nums = [1,2,3,2]
     * Output: 14
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^7
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int maxSumMinProduct(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] prevSmaller = new int[n];
        int[] nextSmaller = new int[n];
        Arrays.fill(prevSmaller, -1);
        Arrays.fill(nextSmaller, n);

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                nextSmaller[stack.peek()] = i;
                stack.pop();
            }
            stack.push(i);
        }

        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                prevSmaller[stack.peek()] = i;
                stack.pop();
            }
            stack.push(i);
        }

        long[] presum = new long[n];
        presum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            presum[i] = presum[i - 1] + nums[i];
        }

        long res = 0;
        long M = (long)1e9 + 7;
        for (int i = 0; i < n; i++) {
            int a = prevSmaller[i];
            int b = nextSmaller[i];
            long sum = presum[b - 1] - (a == -1 ? 0 : presum[a]);
            res = Math.max(res, sum *  nums[i]);
        }
        return (int)(res % M);
    }
}
/**
 * subArray sum * subArray minVal => O(n^2)
 * 关键是找minVal
 * 倒过来想
 * 1 [5 3 2 4 6] 0 x
 * a      ^      b
 *        9 subArrays => 求一个最大的subArray sum * minVal, sum越大越好，val > 0 => subArray越大越好 => 范围越大越好
 * 找prev smaller 和 next smaller
 * nums[i]: prev smaller element / next smaller element => 单调栈 LC496 / 503 -> O(n) one pass
 * 做一个预处理，再扫一遍
 * sum = nums[a+1:b-1] => presum[b-1]-presum[a]
 * min = nums[i]
 * time = O(n) 比LC907更简单，比如遇到重复元素怎么办，这里有重复的2没关系，照样可以继续推动边界，不影响最小值依然是2
 * 遍历subArray找最小值 -> O(n^2)，遍历最小值找subArray -> O(n) => 选择后者，与LC907非常类似，一起做！
 */
