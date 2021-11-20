package LC1501_1800;

public class LC1718_ConstructtheLexicographicallyLargestValidSequence {
    /**
     * Given an integer n, find a sequence that satisfies all of the following:
     *
     * The integer 1 occurs once in the sequence.
     * Each integer between 2 and n occurs twice in the sequence.
     * For every integer i between 2 and n, the distance between the two occurrences of i is exactly i.
     * The distance between two numbers on the sequence, a[i] and a[j], is the absolute difference of their indices,
     * |j - i|.
     *
     * Return the lexicographically largest sequence. It is guaranteed that under the given constraints, there is always
     * a solution.
     *
     * A sequence a is lexicographically larger than a sequence b (of the same length) if in the first position where a
     * and b differ, sequence a has a number greater than the corresponding number in b. For example, [0,1,9,0] is
     * lexicographically larger than [0,1,5,6] because the first position they differ is at the third number, and 9 is
     * greater than 5.
     *
     * Input: n = 3
     * Output: [3,1,2,3,2]
     *
     * Constraints:
     *
     * 1 <= n <= 20
     * @param n
     * @return
     */
    // time = O(n!), space = O(n)
    public int[] constructDistancedSequence(int n) {
        boolean[] used = new boolean[21];
        int[] res = new int[2 * n - 1];
        dfs(0, n, used, res);
        return res;
    }

    private boolean dfs(int pos, int n, boolean[] used, int[]res) {
        if (pos == 2 * n - 1) return true;
        if (res[pos] > 0) return dfs(pos + 1, n, used, res);

        for (int d = n; d >= 1; d--) {
            if (used[d]) continue;
            if (d > 1 && (pos + d >= 2 * n - 1 || res[pos + d] > 0)) continue;
            used[d] = true;
            res[pos] = d;
            if (d > 1) res[pos + d] = d;
            if (dfs(pos + 1, n, used, res)) return true;
            used[d] = false;
            res[pos] = 0;
            if (d > 1) res[pos + d] = 0;
        }
        return false;
    }
}
/**
 * 1 <= n <= 20 => 暴力，无巧解
 * 尽量在最高位上摆放最大的数字
 * 2n + 1
 * 2*2
 * 3**3
 * 4***4
 * NP hard, 暴力搜索
 *
 * 0  1        n
 * n n-2 x x   n x x x x
 *        n-2 n-1
 * 这种题并没有特殊的技巧，就是贪心暴搜。
 * 我们希望这个数最大，那么自然希望最高位最大，
 * 我们第一步贪心地在ret[0]放置n，根据题意要求在ret[n]也放置n；
 * 第二步，我们尝试在ret[1]放置第二大的元素，也就是n-1，那么根据题意ret[n]也必须放置n-1，但发现该位置已经放置了n，所以我们的第二步尝试失败；
 * 于是我们重新尝试第二步，在ret[1]放置第三大的元素n-2，发现可以继续推进下去...
 * 就是这样从高位到低位不断地尝试，每次位置优先尝试放置较大的元素。
 * 直至我们找到一种方法能把2*n-1个位置都填充满，自然就是我们所能构造的最大答案。
 */

