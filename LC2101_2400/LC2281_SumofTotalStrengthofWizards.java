package LC2101_2400;
import java.util.*;
public class LC2281_SumofTotalStrengthofWizards {
    /**
     * As the ruler of a kingdom, you have an army of wizards at your command.
     *
     * You are given a 0-indexed integer array strength, where strength[i] denotes the strength of the ith wizard. For
     * a contiguous group of wizards (i.e. the wizards' strengths form a subarray of strength), the total strength is
     * defined as the product of the following two values:
     *
     * The strength of the weakest wizard in the group.
     * The total of all the individual strengths of the wizards in the group.
     * Return the sum of the total strengths of all contiguous groups of wizards. Since the answer may be very large,
     * return it modulo 109 + 7.
     *
     * A subarray is a contiguous non-empty sequence of elements within an array.
     *
     * Input: strength = [1,3,1,2]
     * Output: 44
     *
     * Input: strength = [5,4,6]
     * Output: 213
     *
     * Constraints:
     *
     * 1 <= strength.length <= 10^5
     * 1 <= strength[i] <= 10^9
     * @param strength
     * @return
     */
    // time = O(n), space = O(n)
    public int totalStrength(int[] strength) {
        int n = strength.length;
        long M = (long)(1e9 + 7);
        long[] nums = new long[n + 1];
        for (int i = 0; i < n; i++) nums[i + 1] = strength[i];

        long[] presum = new long[n + 2];
        long[] presum2 = new long[n + 2];
        for (int i = 1; i <= n; i++) presum[i] = (presum[i - 1] + nums[i]) % M;
        for (int i = 1; i <= n; i++) presum2[i] = (presum2[i - 1] + nums[i] * i) % M;

        Stack<Integer> stack = new Stack<>();
        int[] nextSmaller = new int[n + 2];
        int[] prevSmallerOrEqual = new int[n + 2];
        Arrays.fill(nextSmaller, n + 1);
        Arrays.fill(prevSmallerOrEqual, 0);

        for (int i = 1; i <= n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                nextSmaller[stack.pop()] = i;
            }
            if (!stack.isEmpty()) prevSmallerOrEqual[i] = stack.peek();
            stack.push(i);
        }

        long res = 0;
        for (int i = 1; i <= n; i++) {
            int a = prevSmallerOrEqual[i], b = nextSmaller[i];
            int x = i - a, y = b - i;
            long left = ((presum2[i - 1] - presum2[a]) - (presum[i - 1] - presum[a]) * a % M + M) % M;
            long right = ((presum[b - 1] - presum[i]) * b % M - (presum2[b - 1] - presum2[i]) + M) % M;
            long mid = nums[i] * x * y % M;
            res = (res + (left * y % M + right * x % M + mid) * nums[i]) % M;
        }
        return (int) res;
    }
}
/**
 * 遍历所有元素
 * nums[a]: prev smaller of nums[i]
 * nums[b]: next smaller of nums[i]
 * a [x x x x x i x x x] b
 * x = i - a
 * y = b - i;
 * x * y
 * 高效的办法，把x * y种的subarray sum都加起来
 * 当任意固定一个右边界：
 * (nums[a+1]*1+nums[a+2]*2+nums[a+3]*3+nums[a+4]*4) * y;
 * 不管左边界在那里，右边的3个数：
 * (nums[i+1]*3+nums[i+2]*2+nums[i+3]*1) * x;
 * nums[i] * x * y;
 * => res[i] = nums[i] * sum
 * 系数与index正相关
 * nums[i]*i
 * let presum2[i] = nums[1]*1 + nums[2]*2+ nums[3]*3 + ... + nums[i] * i
 * presum2[i-1] - presum2[a] = nums[a+1]*(a+1) + nums[a+2]*(a+2) + nums[a+3]*(a+3) + nums[a+4]*(a+4)
 * = what we want + sum[a+1:i-1] * a
 * presum2[b-1] - presum2[i] = nums[i+1]*(i+1) + nums[i+2]*（i+2) + nums[i+3]*(i+3)
 * = sum[i+1：b-1]*b - what we want
 * 约定有多个相同的元素都是最小值的话，最左边的是最小值 => prevSmallerOrEqual
 */