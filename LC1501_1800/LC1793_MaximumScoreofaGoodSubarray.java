package LC1501_1800;

public class LC1793_MaximumScoreofaGoodSubarray {
    /**
     * You are given an array of integers nums (0-indexed) and an integer k.
     *
     * The score of a subarray (i, j) is defined as min(nums[i], nums[i+1], ..., nums[j]) * (j - i + 1).
     * A good subarray is a subarray where i <= k <= j.
     *
     * Return the maximum possible score of a good subarray.
     *
     * Input: nums = [1,4,3,7,4,5], k = 3
     * Output: 15
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 2 * 10^4
     * 0 <= k < nums.length
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public int maximumScore(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int l = k, r = k;
        int minVal = nums[k], res = 0;
        while (l >= 0 || r < n) {
            while (l >= 0 && nums[l] >= minVal) l--;
            while (r < n && nums[r] >= minVal) r++;
            res = Math.max(res, minVal * (r - l  - 1)); // l, r are both out of the range
            minVal = Math.max(l >= 0 ? nums[l] : Integer.MIN_VALUE, r < n ? nums[r] : Integer.MIN_VALUE);
        }
        return res;
    }
}
/**
 * 一定要包含k，左边界一定在k的左边，右边界则一定在k的右边
 * x x x x [x x k x x] x x x
 *         l         r
 * 以k为中心，向左右推移
 * 1个元素：nums[k] * 1
 * 往左右扩展下，保证k仍然是最小值
 * 1 <= nums[i] <= 2 * 10^4 => 所有元素都是正数
 * nums[k] * (r - l + 1)
 * 推不动了，因为无论往左还是往右，都可能遇到一个比nums[k]更小的元素
 * 在左右两边里选一个扩展下 => 选更大的一边 => 让minVal一点点往下降
 * 这时minVal = ...
 * 这样做的目的：
 * score = minVal * subarray_width
 * 可以从大到小遍历minVal * 宽度
 * 一上来k是minVal，随着扩展之后，不断被刷新下限，minVal只会越来越小
 * 单调递减的过程
 * => 按照从高到低来遍历minVal
 */
