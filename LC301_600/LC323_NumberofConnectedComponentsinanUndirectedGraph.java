package LC301_600;
import java.util.*;
public class LC323_NumberofConnectedComponentsinanUndirectedGraph {
    /**
     * You have a graph of n nodes. You are given an integer n and an array edges where edges[i] = [ai, bi]
     * indicates that there is an edge between ai and bi in the graph.
     *
     * Return the number of connected components in the graph.
     *
     * Input: n = 5, edges = [[0,1],[1,2],[3,4]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 2000
     * 1 <= edges.length <= 5000
     * edges[i].length == 2
     * 0 <= ai <= bi < n
     * ai != bi
     * There are no repeated edges.
     * @param n
     * @param edges
     * @return
     */
    // time = O(V + E) = O(n + m), space = O(v + E) = O(n + m)
    public int countComponents(int n, int[][] edges) {
        // corner case
        if (edges == null || n <= 0) return -1; // 注意：edges.length == 0时不能return 0， 比如1，[] => 1 而不是0！

        // step 1: build graph
        List<List<Integer>> graph = buildGraph(n, edges);

        // step 2: topological sort
        int[] status = new int[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (status[i] == 0) {
                count++;
                dfs(graph, status, i, i - 1);
            }
        }
        return count;
    }

    private List<List<Integer>> buildGraph(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return graph;
    }

    private void dfs(List<List<Integer>> graph, int[] status, int cur, int prev) {
        if (status[cur] != 0) return;

        status[cur] = 1;
        for (int next : graph.get(cur)) {
            if (next == prev) continue;
            dfs(graph, status, next, cur);
        }
        status[cur] = 2;
    }
}
