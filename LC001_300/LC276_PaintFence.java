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
    public int numWays(int n, int k) {

    }
}
/**
 * 排列组合性质 -> dp
 * x x (k-1)
 * x y (k-1)
 * n^(k-1) ??? 错误答案。
 */
