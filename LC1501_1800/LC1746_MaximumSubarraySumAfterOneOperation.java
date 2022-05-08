package LC1501_1800;

public class LC1746_MaximumSubarraySumAfterOneOperation {
    /**
     * You are given an integer array nums. You must perform exactly one operation where you can replace one element
     * nums[i] with nums[i] * nums[i].
     *
     * Return the maximum possible subarray sum after exactly one operation. The subarray must be non-empty.
     *
     * Input: nums = [2,-1,-4,-3]
     * Output: 17
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -104 <= nums[i] <= 10^4
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int maxSumAfterOperation(int[] nums) {
        int a = 0, b = 0, res = 0;
        for (int x : nums) {
            int a0 = a, b0 = b;
            a = Math.max(a0 + x, x); // 继续不使用 vs 当前不使用且独立
            b = Math.max(b0 + x, x); // 前面用过，这边只能不使用 vs 当前不使用且独立

            b = Math.max(a0 + x * x, b); // 现在使用 vs 前面使用过
            b = Math.max(b, x * x); // 现在使用并独立 vs 前面使用过

            res = Math.max(res, Math.max(a, b));
        }
        return res;
    }
}
/**
 * 这就是在常规的max subarray sum的基础上，再套一个“是否使用自乘权力”的选择。
 * 令a表示截止到目前为止“没有使用该权力”得到的最大子区间sum，b表示截止到目前为止“已经使用过该权力”得到的最大子区间sum，
 * 状态转移如下：
 *
 * a = max(a+x, x)
 * b = max(a+x*x, x*x, b+x, x)
 * ret = max(ret, a, b)
 * 注意，需要在每回合的时候都更新ret，因为最大区间和可能截止在任何位置。
 */