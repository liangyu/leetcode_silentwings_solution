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
            if (i > idx && candidates[i] == candidates[i - 1]) continue;
            path.add(candidates[i]);
            dfs(candidates, target, i + 1, sum + candidates[i], path, res);
            path.remove(path.size() - 1);
        }
    }
}
/**
 * target = 4
 * 3  [1 1]  3 + 1
 *           3 + 1
 * target = 5
 * 3 [1 1 1] 2    3 + 1 + 1 = 5
 * 先排个序，把1挪到一起去，只取最前面的
 * 如果取2个的话，约定只取前2个
 * 如果取多个，我们永远只取靠前的那些，不会跳着取
 * 肯定不会有重复
 * target = 8
 * 4 [3 3 2 1 1 1]
 * for (int i = idx; i < candidates.size(); i++) {
 *     if (i > idx && candidates[i] == candidates[i - 1]) continue;
 * }
 *
 */
