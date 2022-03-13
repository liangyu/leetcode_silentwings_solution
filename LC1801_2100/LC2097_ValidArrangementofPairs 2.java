package LC1801_2100;
import java.util.*;
public class LC2097_ValidArrangementofPairs {
    /**
     * You are given a 0-indexed 2D integer array pairs where pairs[i] = [starti, endi]. An arrangement of pairs is
     * valid if for every index i where 1 <= i < pairs.length, we have endi-1 == starti.
     *
     * Return any valid arrangement of pairs.
     *
     * Note: The inputs will be generated such that there exists a valid arrangement of pairs.
     *
     * Input: pairs = [[5,1],[4,5],[11,9],[9,4]]
     * Output: [[11,9],[9,4],[4,5],[5,1]]
     *
     * Constraints:
     *
     * 1 <= pairs.length <= 10^5
     * pairs[i].length == 2
     * 0 <= starti, endi <= 10^9
     * starti != endi
     * No two pairs are exactly the same.
     * There exists a valid arrangement of pairs.
     * @param pairs
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int[][] validArrangement(int[][] pairs) {
        int n = pairs.length;
        List<int[]> res = new ArrayList<>();

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        HashMap<Integer, Integer> count = new HashMap<>();
        for (int[] p : pairs) {
            int a = p[0], b = p[1];
            map.putIfAbsent(a, new ArrayList<>());
            map.get(a).add(b);
            count.put(a, count.getOrDefault(a, 0) + 1);
            count.put(b, count.getOrDefault(b, 0) - 1);
        }

        int start = -1;
        for (int key : count.keySet()) {
            if (count.get(key) > 0) start = key;
        }

        if (start == -1) start = pairs[0][0];
        dfs(map, start, res);

        int[][] ans = new int[n][2];
        for (int i = 0; i < n; i++) ans[i] = res.get(n - 1 - i);
        return ans;
    }

    private void dfs(HashMap<Integer, List<Integer>> map, int cur, List<int[]> res) {
        while (map.getOrDefault(cur, new ArrayList<>()).size() > 0) {
            int next = map.get(cur).get(map.get(cur).size() - 1);
            map.get(cur).remove(map.get(cur).size() - 1);
            dfs(map, next, res);
            res.add(new int[]{cur, next}); // 不断绕圈，最后才会到达最终出口
        }
    }
}
/**
 * ref: LC332 Eulerian Path
 * 对欧拉路径，最多只会有一个deadend
 * 如果有2个，不可能有一笔画
 * 从起点开始随便走，不允许走重复的边
 * 如果有deadend，最终一定是能走到deadend
 * 稍微严格一点的证明：除了起点和终点是个open end,其他所有点肯定是入度 == 出度
 * 假设你走不到dead end，你肯定也是能走回来的，因为这个入度和出度数目是相等的
 * 你占用了一个出度，一定会有个入度让你走回来
 * S: path1    4
 *    path2a   3
 *    path2b   2
 *    path2c   1
 * 如果欧拉路径里没有open end怎么办？
 * 这种情况下没有path1，都是环
 * 只要path2c, 2b, 2a倒序即可
 */
