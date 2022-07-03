package LC301_600;
import java.util.*;
public class LC473_MatchstickstoSquare {
    /**
     * You are given an integer array matchsticks where matchsticks[i] is the length of the ith matchstick. You want to
     * use all the matchsticks to make one square. You should not break any stick, but you can link them up, and each
     * matchstick must be used exactly one time.
     *
     * Return true if you can make this square and false otherwise.
     *
     * Input: matchsticks = [1,1,2,2,2]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= matchsticks.length <= 15
     * 0 <= matchsticks[i] <= 10^9
     * @param matchsticks
     * @return
     */
    // time = O(4^n), space = O(n)
    // we have a total of n sticks and for each one of those matchsticks, we have 4 different possibilities for the
    // subsets they might belong to or the side of the square they might be a part of.
    public boolean makesquare(int[] matchsticks) {
        // corner case
        if (matchsticks == null || matchsticks.length == 0) return false;

        Arrays.sort(matchsticks); // 相同元素都变得邻接
        int i = 0, j = matchsticks.length - 1;
        while (i < j) swap(matchsticks, i++, j--); // 从大到小排序

        boolean[] visited = new boolean[matchsticks.length];
        int sum = 0;
        for (int n : matchsticks) sum += n;
        if (sum % 4 != 0) return  false;
        return dfs(matchsticks, 0, 0, 0, sum / 4, visited);
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    private boolean dfs(int[] nums, int cur, int group, int sum, int side, boolean[] visited) {
        // base case
        if (group == 4) return true;
        if (sum > side) return false;
        if (sum == side) return dfs(nums, 0, group + 1, 0, side, visited);

        int last = -1;
        for (int i = cur; i < visited.length; i++) {
            if (visited[i]) continue;
            if (nums[i] == last) continue;
            visited[i] = true;
            last = nums[i];
            if (dfs(nums,i + 1, group, sum + nums[i], side, visited)) return true;
            visited[i] = false;
            if (sum == 0 || sum + nums[i] == side) return false;
        }
        return false;
    }
}
/**
 * ref: LC698 一模一样
 * dfs暴力搜索：状态压缩(方便枚举,不太容易剪枝),深度搜索(容易剪枝)
 * Ref: LC1681 状态压缩
 * 1. 从大到小枚举 -> 剩余分支更少，更容易剪枝
 * 2. 每条边内部，要求火柴编号递增 -> 避免搜搜重复
 * 3. 若当前放某根火柴失败，
 * 3.1 跳过长度相同的火柴
 * 3.2 是第一根火柴，则直接剪掉当前分支
 * 3.3 是最后一根火柴，也剪掉当前分支
 */
