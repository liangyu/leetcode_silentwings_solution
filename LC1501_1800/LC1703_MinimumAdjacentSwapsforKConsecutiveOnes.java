package LC1501_1800;
import java.util.*;
public class LC1703_MinimumAdjacentSwapsforKConsecutiveOnes {
    /**
     * You are given an integer array, nums, and an integer k. nums comprises of only 0's and 1's. In one move, you can
     * choose two adjacent indices and swap their values.
     *
     * Return the minimum number of moves required so that nums has k consecutive 1's.
     *
     * Input: nums = [1,0,0,1,0,1], k = 2
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * nums[i] is 0 or 1.
     * 1 <= k <= sum(nums)
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int minMoves(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k < 0) return 0;

        int n = nums.length;
        List<Integer> p = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) p.add(i);
        }

        // sliding window -> sum1
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += Math.abs(p.get(i) - p.get(k / 2));
        }
        int res = sum;
        for (int i = 0; i + k < p.size(); i++) { // cost要均摊
            int mid = i + k / 2;
            sum -= Math.abs(p.get(mid) - p.get(i));
            sum += Math.abs(p.get(i + k) - p.get(mid + 1));
            sum += k / 2 * (p.get(mid + 1) - p.get(mid));
            sum -= (k - 1 - k / 2) * (p.get(mid + 1) - p.get(mid));
            res = Math.min(res, sum);
        }
        int offset = 0;
        for (int i = 0; i < k; i++) {
            offset += Math.abs(i - k / 2);
        }
        return res - offset;
    }
}
/**
 * 我们只关心1所在的位置
 * p = [0 4 7 8], k = 2
 * 若干个滑窗小问题
 * 赶到一块去需要多少次操作
 * [p1, p2, p3, p4, p5]
 * [q1, q2, x, q3, q4]
 * [        x        ]
 * min sum |pi - qi| for i = 0,1,2...,k-1
 * ref: LC296 - best meeting point
 * [p0,p1,p2,p3,...,p_mid,...,pk - 1],  find x
 * minimize sum |pi-x| for i = 0,1,2,...,k-1
 * 常见问题，解也是约定俗成
 * x显然是放在k个位置的中位数上
 * => x must be the medium of p[]
 * x x o o x x
 * 赶到一个位置，以x为中心的一个区间上
 * [x-2,x-1,x,x+1,x+2]最小
 * 灵感：先把所有数都赶到x上
 * sum: |pi-x| - (k/2+...+2+1+0+1+2+...+k/2) 吐掉这些东西，常熟
 * 不管最终解在哪里，都可以把它们都赶到中心点，在把每个数松弛下放到该在的位置上
 * 时间复杂度还是比较高，k特别大，滑窗特别长
 * O(n) 滑n次，没法过，太大。
 * 优化：消耗分摊下去
 * 求一个滑窗里面所有元素到达medium距离之和
 * 0 1 2 mid   k-1
 * x x x o x x x     => sum1
 *   x x x o x x x   => sum2
 *     x x x o x x x => sum3
 *   sum1 =>
 * 1. -abs(p[mid]-p[0])
 * 2. +abs(p[k]-p[mid+1])
 * 3. +k/2*(p[mid+1]-p[mid])
 * 4. -(k-1-k/2)*(p[mid+1]-p[mid])
 *   sum2
 * => O(1)就能算出来
 */
