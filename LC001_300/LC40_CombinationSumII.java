package LC001_300;
import java.util.*;
public class LC40_CombinationSumII {
    /**
     * Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations
     * in candidates where the candidate numbers sum to target.
     *
     * Each number in candidates may only be used once in the combination.
     *
     * Note: The solution set must not contain duplicate combinations.
     *
     * Input: candidates = [10,1,2,7,6,1,5], target = 8
     * Output:
     * [
     * [1,1,6],
     * [1,2,5],
     * [1,7],
     * [2,6]
     * ]
     *
     * Constraints:
     *
     * 1 <= candidates.length <= 100
     * 1 <= candidates[i] <= 50
     * 1 <= target <= 30
     * @param candidates
     * @param target
     * @return
     */
    // time = O(2^n), space = O(n)   每个位置上的元素都分取或者不取2种情况，最大深度为n
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (candidates == null || candidates.length == 0) return res;

        Arrays.sort(candidates);
        dfs(candidates, target, 0, 0, new ArrayList<>(), res);
        return res;
    }

    private void dfs(int[] candidates, int target, int idx, int sum, List<Integer> path, List<List<Integer>> res) {
        // base case - success
        if (sum == target) {
            res.add(new ArrayList<>(path));
            return;
        }

        // base case - fail
        if (idx == candidates.length || sum > target) return;

        for (int i = idx; i < candidates.length; i++) {
            if (i != idx && candidates[i] == candidates[i - 1]) continue;
            else {
                path.add(candidates[i]);
                dfs(candidates, target, i + 1, sum + candidates[i], path, res);
                path.remove(path.size() - 1);
            }
        }
    }
}
