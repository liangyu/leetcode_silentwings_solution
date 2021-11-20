package LC1801_2100;
import java.util.*;
public class LC2065_MaximumPathQualityofaGraph {
    /**
     * There is an undirected graph with n nodes numbered from 0 to n - 1 (inclusive). You are given a 0-indexed integer
     * array values where values[i] is the value of the ith node. You are also given a 0-indexed 2D integer array edges,
     * where each edges[j] = [uj, vj, timej] indicates that there is an undirected edge between the nodes uj and vj, and
     * it takes timej seconds to travel between the two nodes. Finally, you are given an integer maxTime.
     *
     * A valid path in the graph is any path that starts at node 0, ends at node 0, and takes at most maxTime seconds to
     * complete. You may visit the same node multiple times. The quality of a valid path is the sum of the values of the
     * unique nodes visited in the path (each node's value is added at most once to the sum).
     *
     * Return the maximum quality of a valid path.
     *
     * Note: There are at most four edges connected to each node.
     *
     * Input: values = [0,32,10,43], edges = [[0,1,10],[1,2,15],[0,3,10]], maxTime = 49
     * Output: 75
     *
     * Constraints:
     *
     * n == values.length
     * 1 <= n <= 1000
     * 0 <= values[i] <= 10^8
     * 0 <= edges.length <= 2000
     * edges[j].length == 3
     * 0 <= uj < vj <= n - 1
     * 10 <= timej, maxTime <= 100
     * All the pairs [uj, vj] are unique.
     * There are at most four edges connected to each node.
     * The graph may not be connected.
     * @param values
     * @param edges
     * @param maxTime
     * @return
     */
    // time = O(4^10), space = O(n)
    private int res = 0;
    public int maximalPathQuality(int[] values, int[][] edges, int maxTime) {
        int n = values.length;
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1], c = edge[2];
            graph[a].add(new int[]{b, c}); // {node, time}
            graph[b].add(new int[]{a, c});
        }

        int[] visited = new int[n];
        visited[0]++;
        dfs(graph, 0, values, maxTime, values[0], visited);
        return res;
    }

    private void dfs(List<int[]>[] graph, int cur, int[] values, int curTime, int total, int[] visited) {
        // base case
        if (cur == 0) res = Math.max(res, total);

        for (int[] x : graph[cur]) {
            int next = x[0], time = x[1];
            if (curTime - time < 0) continue;
            visited[next]++;
            dfs(graph, next, values, curTime - time, total + (visited[next] == 1 ? values[next] : 0), visited);
            visited[next]--;
        }
    }
}
/**
 * 题意的约束中给出了10 <= timej, maxTime <= 100，这说明最多只能走10步。
 * 又因为There are at most four edges connected to each node，每一步出发最多只有四种选择，所以无脑搜索的话最多也只有4^10=1e6种可能。
 * 全部本题只要穷举所有的路径选择即可。
 */
