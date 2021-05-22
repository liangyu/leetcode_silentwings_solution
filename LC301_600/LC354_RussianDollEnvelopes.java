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
        Arrays.fill(dp, 1);

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

        Arrays.sort(envelopes, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]); // 长度相等，宽度从大到小排，可以降维

        int n = envelopes.length;
        List<Integer> buffer = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int idx = getIdx(buffer, envelopes[i][1]);
            if (idx == buffer.size()) buffer.add(envelopes[i][1]);
            else buffer.set(idx, envelopes[i][1]);
        }
        return buffer.size();
    }

    private int getIdx(List<Integer> buffer, int target) {
        int start = 0, end = buffer.size() - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (buffer.get(mid) == target) return mid;
            else if (buffer.get(mid) < target) start = mid + 1;
            else end = mid - 1;
        }
        return start;
    }
}
/**
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
 */
