package LC2101_2400;
import java.util.*;
public class LC2233_MaximumProductAfterKIncrements {
    /**
     * You are given an array of non-negative integers nums and an integer k. In one operation, you may choose any
     * element from nums and increment it by 1.
     *
     * Return the maximum product of nums after at most k operations. Since the answer may be very large, return it
     * modulo 10^9 + 7.
     *
     * Input: nums = [0,4], k = 5
     * Output: 20
     *
     * Constraints:
     *
     * 1 <= nums.length, k <= 10^5
     * 0 <= nums[i] <= 10^6
     * @param nums
     * @param k
     * @return
     */
    // S1: minHeap
    // time = O(nlogn), sapce = O(n)
    public int maximumProduct(int[] nums, int k) {
        PriorityQueue<Long> pq = new PriorityQueue<>();
        for (int x : nums) pq.offer((long) x);

        while (k-- > 0) {
            long cur = pq.poll();
            pq.offer(cur + 1);
        }

        long M = (long)(1e9 + 7), res = 1;
        while (!pq.isEmpty()) res = res * pq.poll() % M;
        return (int) res;
    }

    // S2: Binary Search
    // time = O(nlogn), space = O(n)
    public int maximumProduct2(int[] nums, int k) {
        Arrays.sort(nums);

        int n = nums.length;
        long[] presum = new long[n];
        for (int i = 0; i < n; i++) {
            presum[i] = (i == 0 ? 0 : presum[i - 1]) + nums[i];
        }

        long[] diff = new long[n];
        for (int i = 0; i < n; i++){
            diff[i] = (long) nums[i] * (i + 1) - presum[i];
        }

        int p = lowerBound(diff, k);
        long total = presum[p] + k; // 注意：这里是presum[p]!!!
        long each = total / (p + 1);
        long extra = total % (p + 1);

        long res = 1;
        long M = (long)(1e9 + 7);
        for (int i = 0; i < extra; i++) res = res * (each + 1) % M;
        for (int i = 0; i < p + 1 - extra; i++) res = res * each % M;
        for (int i = p + 1; i < n; i++) res = res * nums[i] % M;
        return (int) res;
    }

    private int lowerBound(long[] diff, int k) {
        int left = 0, right = diff.length - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (diff[mid] <= k) left = mid;
            else right = mid - 1;
        }
        return diff[left] <= k ? left : left - 1;
    }
}
/**
 * 对于新的数组而言，它们的和是固定的，并且元素个数也是固定的
 * 乘积最大 => 尽量让这些元素都相等
 * (a1+a2+a3+...+an = S)
 * maximize a1*a2*a3*...*an => a1==a2==a3==S/n
 * 尽量把小的拉平
 * 尽量多的拉平
 * (presum[p] + k) / p < nums[p + 1]
 * diff[i]: 能够让前i个元素都拉平的投资
 * diff[i] = nums[i] * i - presum[i]
 * diff[p] <= k
 * diff 单调递增
 * Integer break: 区别是拆的元素个数不定
 * sum => 尽量拆成靠近自然对数e的数
 * sum => e + e + e ....
 * f(x) = x^(sum/x) => x -> e  => 尽量拆成3，否则尽量拆成2
 */
