package LC001_300;
import java.util.*;
public class LC39_CombinationSum {
    /**
     * Given an array of distinct integers candidates and a target integer target, return a list of all unique
     * combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.
     *
     * The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the
     * frequency of at least one of the chosen numbers is different.
     *
     * It is guaranteed that the number of unique combinations that sum up to target is less than 150 combinations for
     * the given input.
     *
     * Input: candidates = [2,3,6,7], target = 7
     * Output: [[2,2,3],[7]]
     *
     * Constraints:
     *
     * 1 <= candidates.length <= 30
     * 1 <= candidates[i] <= 200
     * All elements of candidates are distinct.
     * 1 <= target <= 500
     * @param candidates
     * @param target
     * @return
     */
    // time = O(n^(t/m + 1)), space = O(n^(t/m))  t: target, m: minimal val in candidates
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (candidates == null || candidates.length == 0) return res;

        dfs(candidates, target, 0, 0, new ArrayList<>(), res);
        return res;
    }

    private void dfs(int[] candidates, int target, int idx, int sum, List<Integer> path, List<List<Integer>> res) {
        // base case - success
        if (sum == target) {
            res.add(new ArrayList<>(path));
            return;
        }
        // base case - fail;
        if (idx == candidates.length || sum > target) return;

        for (int i = idx; i < candidates.length; i++) {
            path.add(candidates[i]);
            dfs(candidates, target, i, sum + candidates[i], path, res);
            path.remove(path.size() - 1);
        }
    }
}
