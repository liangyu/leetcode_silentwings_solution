package LC001_300;
import java.util.*;
public class LC216_CombinationSumIII {
    /**
     * Find all valid combinations of k numbers that sum up to n such that the following conditions are true:
     *
     * Only numbers 1 through 9 are used.
     * Each number is used at most once.
     * Return a list of all possible valid combinations. The list must not contain the same combination twice, and the
     * combinations may be returned in any order.
     *
     * Input: k = 3, n = 7
     * Output: [[1,2,4]]
     *
     * Constraints:
     *
     * 2 <= k <= 9
     * 1 <= n <= 60
     * @param k
     * @param n
     * @return
     */
    // time = O(C(9,k)) = O(9! / (k! * (9 - k)!), space = O(k)
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(n, k, 1, new ArrayList<>(), res);
        return res;
    }

    private void dfs(int sum, int k, int idx, List<Integer> path, List<List<Integer>> res) {
        // base case
        if (path.size() == k) {
            if (sum == 0) res.add(new ArrayList<>(path));
            return;
        }

        for (int i = idx; i <= 9; i++) {
            path.add(i);
            sum -= i;
            dfs(sum, k, i + 1, path, res);
            sum +=i;
            path.remove(path.size() - 1);
        }
    }
}
