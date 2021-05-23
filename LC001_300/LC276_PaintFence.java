package LC001_300;
import java.util.*;
public class LC276_PaintFence {
    /**
     * You are painting a fence of n posts with k different colors. You must paint the posts following these rules:
     *
     * Every post must be painted exactly one color.
     * At most one pair of adjacent fence posts can have the same color.
     * Given the two integers n and k, return the number of ways you can paint the fence.
     *
     * Input: n = 3, k = 2
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= n <= 50
     * 1 <= k <= 10^5
     * The answer is guaranteed to be in the range [0, 2^31 - 1] for the given n and k.
     * @param n
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public int numWays(int n, int k) {
        // corner case
        if (n == 0) return 0;
        if (n == 1) return k;

        int same = k, diff = k * (k - 1);
        for (int i = 3; i <= n; i++) {
            int same_tmp = same, diff_tmp = diff;
            same = diff_tmp;
            diff = (same_tmp + diff_tmp) * (k - 1);
        }
        return same + diff;
    }
}
/**
 * 排列组合性质 -> dp
 * x x (k-1)
 * x y (k-1)
 * n^(k-1) ??? 错误答案。
 *
 * same[k]: # of methods that satisfy nums[k-1] == nums[k]
 * diff[k]: # of methods that satisfy nums[k-1] != nums[k]
 * same[k] = diff[k-1]
 * diff[k] = same[k-1]*(k-1) + diff[k-1]*(k-1)
 * ref: house robber 与 k-1有关
 * adjacent fence -> 如果2个是相同的，那下面一定取不同
 * 双状态：dual status，该轮状态只取决于上一轮状态，而每一轮状态在这里只有2种：相等或者不等，不停更新这两种状态
 * 双状态上下会交错
 * 双状态型的题：198，213，276, 376，487，123，188，309，714，740，552，1186
 */
