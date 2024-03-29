package LC901_1200;
import java.util.*;
public class LC1168_OptimizeWaterDistributioninaVillage {
    /**
     * There are n houses in a village. We want to supply water for all the houses by building wells and laying pipes.
     *
     * For each house i, we can either build a well inside it directly with cost wells[i - 1]
     * (note the -1 due to 0-indexing), or pipe in water from another well to it. The costs to lay pipes between houses
     * are given by the array pipes, where each pipes[j] = [house1j, house2j, costj] represents the cost to connect
     * house1j and house2j together using a pipe. Connections are bidirectional.
     *
     * Return the minimum total cost to supply water to all houses.
     *
     * Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= n <= 10^4
     * wells.length == n
     * 0 <= wells[i] <= 10^5
     * 1 <= pipes.length <= 10^4
     * pipes[j].length == 3
     * 1 <= house1j, house2j <= n
     * 0 <= costj <= 10^5
     * house1j != house2j
     * @param n
     * @param wells
     * @param pipes
     * @return
     */
    // time = O((m + n)log(m + n)), space = O(m + n)
    int[] parent;
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        parent = new int[n + 1];
        for (int i = 0; i <= n; i++) parent[i] = i;

        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < wells.length; i++) edges.add(new int[]{i + 1, 0, wells[i]}); // add a dummy 0 node to connect!
        for (int[] x : pipes) edges.add(x);

        Collections.sort(edges, (o1, o2) -> o1[2] - o2[2]); // don't forget to sort the arrays!!!

        int res = 0;
        for (int[] x : edges) {
            int a = x[0], b = x[1], cost = x[2];
            if (findParent(a) != findParent(b)) {
                union(a, b);
                res += cost;
            }
        }
        return res;
    }

    private int findParent(int x) {
        if (parent[x] != x) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }
}
/**
 * 此题的解法非常巧妙。你增加一个隐藏的0号节点，把每个节点自建井的费用wells[i]想象成连接[0,i]的边的费用。
 * 这道题就变成了求最少的费用将所有的节点（包括隐藏的0号）连接起来。
 * 这就是最基本的最小生成树问题（MST），和1135.Connecting-Cities-With-Minimum-Cost一模一样。
 */
