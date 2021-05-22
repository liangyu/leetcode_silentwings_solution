package LC1501_1800;
import java.util.*;
public class LC1735_CountWaystoMakeArrayWithProduct {
    /**
     * You are given a 2D integer array, queries. For each queries[i], where queries[i] = [ni, ki], find the number of
     * different ways you can place positive integers into an array of size ni such that the product of the integers
     * is ki. As the number of ways may be too large, the answer to the ith query is the number of ways modulo 109 + 7.
     *
     * Return an integer array answer where answer.length == queries.length, and answer[i] is the answer to
     * the ith query.
     *
     * Input: queries = [[2,6],[5,1],[73,660]]
     * Output: [4,1,50734910]
     *
     * Input: queries = [[1,1],[2,2],[3,3],[4,4],[5,5]]
     * Output: [1,2,3,10,5]
     *
     * Constraints:
     *
     * 1 <= queries.length <= 10^4
     * 1 <= ni, ki <= 10^4
     *
     * @param queries
     * @return
     */
    // time = O(n), space = O(1)
    // 10^4，质因数最小为2，如果全是2，那么也就log2(10^4) = log(10^4) / log(2) = 4/log(2) = 13.29 = 14个
    private long[][] comb = new long[10015][15];
    private long M = (long)1e9+7;
    public int[] waysToFillArray(int[][] queries) {
        // corner case
        if (queries == null || queries.length == 0 || queries[0] == null || queries[0].length == 0) {
            return new int[0];
        }

        int[] res = new int[queries.length];
        int idx = 0;

        // step 1: calculate the combo beforehand
        for (int i = 0; i <= 10014; i++) {
            comb[i][0] = 1;
            for (int j = 1; j <= Math.min(i, 14); j++) {
                comb[i][j] = (comb[i - 1][j - 1] + comb[i - 1][j]) % M; // 所有组合数都算出来了
            }
        }

        // step 2: 处理每个query
        for (int[] q : queries) { // O(n)
            int k = q[1];
            int n = q[0];
            long ans = 1;
            // 分解质因数
            for (int x = 2; x <= k; x++) { // O(1)
                int count = 0;
                while (k % x == 0) { // 真正有意义的x在这里只是纯质数
                    count++;
                    k /= x;
                }
                // k个相同的质因数要分成n份 => k = count, n = n => C(k + n - 1, k) = C(count + n - 1, count)
                ans = ans * comb[count + n - 1][count] % M; // 可能会溢出
            }
            res[idx++] = (int)ans;
        }
        return res;
    }
}

/**
 * 如何divide ki by ni 个 positive integers？？？combo可以通过helper()来进行排列组合的枚举
 *
 * 分解？-> recursion??? 遍历k的所有因数
 * k = 6 => 1, 2, 3, 6
 * dfs(k, n)
 * x1 = 1 => dfs(6, n - 1) -> 递归深度非常长 10^4 -> 效率很低，时间非常长TLE！
 * x2 = 2 => dfs(3, n - 1)
 * x3 = 3 => dfs(2, n - 1)
 * x4 = 6 => dfs(6, n - 1)
 *
 * 分解质因数 => 6 = 2 * 3
 * k = x1 * x2 * x3 * x4 * x5 * x6 ...* xn => 插板法 (给你若干个数，你要把它分组，比如k个球分成n份 -> 插板)
 * k = 1 | x1 * x2 * x3 * x4 * x5 * x6  -> 比如6个球分成3份，每份一定要有球的话 => 5个间隔，插2块板 => C(5, 2)
 * 但这里可以每一份不一定有球，比如在开头插，可以有一份为1
 * k balls divided into n groups, allowing empty group
 * => C(k + n - 1, n - 1) => C(k + n - 1, k) 只适用于同质球，但这里却并不是
 *
 *  允许某一份是空的 => 给插出的每一份 + 1,这样就可以用1来代表原始该份 = 0的情况，比如这里是切出3份，所以就 + 3 => 6 + 3 = 9个球
 *  O O | O | O O O O O O    => (2 - 1, 1 - 1, 6 - 1) = （1， 0， 5）
 *  => C(6 + 3 - 1, 3 - 1)
 *
 * 比如： 2 * 2 * 3 * 3 * 5 * 7 => C(8, 2)
 *       2 * 3 * 2 * 5 * 7 * 3 => 上面C(8, 2)cover不到这里的情况，比如2和5要在一起 => 穷举出所有的unique的permutation (卡在这里!)
 *
 *  O O | O O O | O
 *
 * 如何解决这种问题呢？=> 不要试图直接在质因数分解上面分组!!! -> 并不是同质的!
 * 对每种质因数都单独考虑！-> 这样每一种质因数都单独做以上分析出的插板就不存在permutation的问题了，因为当前只有"一种"质因数。
 * how to divide two 2s into 3 parts? => C(2 + 3 - 1, 3 - 1) = C(4, 2)
 * how to divide two 3s into 3 parts? => C(2 + 3 - 1, 3 - 1) = C(4, 2)
 * how to divide one 5 into 3 parts? => C(1 + 3 - 1, 3 - 1) = C(3, 2)
 * how to divide one 7 into 3 parts? => C(1 + 3 - 1, 3 - 1) = C(3, 2)
 * => 最后把以上的4种情况再multiple起来。
 *
 * 如何求组合数？ C(m, n) -> 2种做法。由于本题这里有大量query，所以采用S1效率更高！！！
 *
 * S1: compute all C(n, m) saved in comb
 * private long comb[1000][1000];
 * for (int i = 0; i <= n; i++) {
 *     comb[i][i] = comb[i][0] = 1;
 *     for (int j = 0; j < i; j++) {
 *         comb[i][j] = comb[i - 1][j - 1] + comb[i - 1][j]; // 每个C(i, j)只要均摊O(1)就能求出来，所以提前算出来均摊下来效率很高
 *     }
 * }
 *
 * // S2: compute C(n, m) on demand
 * private long help(int n, int m) {
 *     long cnt = 1;
 *     for (int i = 0; i < m; i++) { // O(m)
 *         cnt *= n - i;
 *         cnt /= i + 1;
 *     }
 *     return cnt;
 * }
 *
 */

