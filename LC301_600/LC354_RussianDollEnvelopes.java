package LC301_600;
import java.util.*;
public class LC354_RussianDollEnvelopes {
    /**
     * You are given a 2D array of integers envelopes where envelopes[i] = [wi, hi] represents the width and the height
     * of an envelope.
     *
     * One envelope can fit into another if and only if both the width and height of one envelope are greater than the
     * other envelope's width and height.
     *
     * Return the maximum number of envelopes you can Russian doll (i.e., put one inside the other).
     *
     * Note: You cannot rotate an envelope.
     *
     * Input: envelopes = [[5,4],[6,4],[6,7],[2,3]]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= envelopes.length <= 5000
     * envelopes[i].length == 2
     * 1 <= wi, hi <= 10^4
     * @param envelopes
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n)
    public int maxEnvelopes(int[][] envelopes) {
        // corner case
        if (envelopes == null || envelopes.length == 0 || envelopes[0] == null || envelopes[0].length == 0) return 0;

        Arrays.sort(envelopes, (o1, o2) -> o1[0] - o2[0]);

        int n = envelopes.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // 一个信封也是一个序列，自立门户

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (envelopes[j][0] < envelopes[i][0] && envelopes[j][1] < envelopes[i][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) res = Math.max(dp[i], res);
        return res;
    }

    // S2: Greedy + B.S. (最优解！)
    // time = O(nlogn), space = O(n)
    public int maxEnvelopes2(int[][] envelopes) {
        // corner case
        if (envelopes == null || envelopes.length == 0 || envelopes[0] == null || envelopes[0].length == 0) return 0;

        // 排除长度上的干扰，只要看宽度即可！降维！
        Arrays.sort(envelopes, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]); // 宽度按降序排列！！！
        List<Integer> buffer = new ArrayList<>();
        for (int[] x : envelopes) {
            int idx = upperBound(buffer, x[1]);
            if (idx == buffer.size()) buffer.add(x[1]);
            else buffer.set(idx, x[1]);
        }
        return buffer.size();
    }

    private int upperBound(List<Integer> buffer, int target) {
        if (buffer.size() == 0) return 0;
        int left = 0, right = buffer.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (buffer.get(mid) < target) left = mid + 1;
            else right = mid;
        }
        return buffer.get(left) >= target ? left : left + 1;
    }
}
/**
 * length: 1 3 4 6 8 9
 * 任何的一个合法序列一定是它的一个subsequence；反之则不一定，因为我们还要考虑width
 * length: (1, 7) (3, 6) (4, 2) (6, 8) (8, 9) (9, 10)
 * 找一个第二维度递增的最长子序列 => LCS
 * dp[i]: the maximum length of Russian doll sequence if ith envelop is the largest one
 * dp[i] = 1
 * for (j = 0; j < i; j++) {
 *     if (width[j] < width[i] && length[j] < length[i])  // 长度相同不能选入！！！
 *      dp[i] = max(dp[i], dp[j] + 1)
 * }
 * return max{dp[i]} for i = 0, 1, 2, ...
 *
 * Greedy:
 * 1 3 (5) 7     4 => 1 3 (4) 7   找第一个>=4的换掉， 把原来的元素变小一点，更有利于以后接更多的元素
 * 用二分法来找第一个比4大的元素
 * 宽度按降序排列的好处是LIS序列里肯定就不会包括长度相同的(因为长度相等元素宽度在降维之后变成降序，二者最多取其一)，
 * 因此可以放心求LIS，这个想法很妙，记住即可。
 */
