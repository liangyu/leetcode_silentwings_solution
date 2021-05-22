package LC001_300;
import java.util.*;
public class LC77_Combinations {
    /**
     * Given two integers n and k, return all possible combinations of k numbers out of the range [1, n].
     *
     * You may return the answer in any order.
     *
     * Input: n = 4, k = 2
     * Output:
     * [
     *   [2,4],
     *   [3,4],
     *   [2,3],
     *   [1,2],
     *   [1,3],
     *   [1,4],
     * ]
     *
     * Constraints:
     *
     * 1 <= n <= 20
     * 1 <= k <= n
     * @param n
     * @param k
     * @return
     */
    // time = O(k * C(n,k)), space = O(C(n,k))
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(n, k, 1, new ArrayList<>(), res);
        return res;
    }

    private void dfs(int n, int k, int idx, List<Integer> path, List<List<Integer>> res) {
        // base case - success
        if (k == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = idx; i <= n; i++) {
            path.add(i);
            dfs(n, k - 1, i + 1, path, res);
            path.remove(path.size() - 1);
        }
    }
}
