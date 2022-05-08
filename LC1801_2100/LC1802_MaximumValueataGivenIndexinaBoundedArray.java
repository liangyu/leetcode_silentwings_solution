package LC1801_2100;

public class LC1802_MaximumValueataGivenIndexinaBoundedArray {
    /**
     * You are given three positive integers: n, index, and maxSum. You want to construct an array nums (0-indexed)
     * that satisfies the following conditions:
     *
     * nums.length == n
     * nums[i] is a positive integer where 0 <= i < n.
     * abs(nums[i] - nums[i+1]) <= 1 where 0 <= i < n-1.
     * The sum of all the elements of nums does not exceed maxSum.
     * nums[index] is maximized.
     * Return nums[index] of the constructed array.
     *
     * Note that abs(x) equals x if x >= 0, and -x otherwise.
     *
     * Input: n = 4, index = 2,  maxSum = 6
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= maxSum <= 109
     * 0 <= index < n
     * @param n
     * @param index
     * @param maxSum
     * @return
     */
    // time = O(log(maxSum)), space = O(1)
    public int maxValue(int n, int index, int maxSum) {
        int left = 1, right = maxSum;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (count(mid, n, index) <= maxSum) left = mid; // mid is reasonable, maybe larger
            else right = mid - 1;
        }
        return left; // 一定有解
    }

    private long count(long h, long n, long index) {
        long sum = 0;

        // calculate left side
        if (h > index) sum += (h - index + h) * (index + 1) / 2;
        else {
            sum += index - h + 1;
            sum += (1 + h) * h / 2;
        }

        // calculate right side
        if (h > n - index) sum += (h - (n - index) + 1 + h) * (n - index) / 2;
        else {
            sum += n - (index + h);
            sum += (h + 1) * h / 2;
        }
        return sum - h;
    }
}
/**
 * 尽量往index这个元素垒的高一点
 * index最高，周围逐渐不得不小1，一直小到1为止
 * 等差数列，从h向两边递减，不足的部分都补1，不能补0，题目要求为正数
 * 所以这题的模式是固定的，最优解
 * h越大，sum越大 => 猜h，算sum，跟maxSum去比较 => 二分猜值
 */