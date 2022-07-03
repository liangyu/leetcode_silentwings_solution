package LC601_900;
import java.util.*;
public class LC785_IsGraphBipartite {
    /**
     * Given an undirected graph, return true if and only if it is bipartite.
     *
     * Recall that a graph is bipartite if we can split its set of nodes into two independent subsets A and B, such
     * that every edge in the graph has one node in A and another node in B.
     *
     * The graph is given in the following form: graph[i] is a list of indexes j for which the edge between nodes i
     * and j exists. Each node is an integer between 0 and graph.length - 1. There are no self edges or parallel edges:
     * graph[i] does not contain i, and it doesn't contain any element twice.
     *
     * Input: graph = [[1,3],[0,2],[1,3],[0,2]]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= graph.length <= 100
     * 0 <= graph[i].length < 100
     * 0 <= graph[i][j] <= graph.length - 1
     * graph[i][j] != i
     * All the values of graph[i] are unique.
     * The graph is guaranteed to be undirected.
     *
     * @param graph
     * @return
     */
    // S1: BFS
    // time = O(n), space = O(n)
    public boolean isBipartite(int[][] graph) {
        // corner case
        // 注意：graph.length == 0 || graph[0].length == 0在这里不能作为corner case来直接return true / false
        // 参考：[[],[3],[],[1],[]]
        if (graph == null || graph[0] == null) return false;

        int n = graph.length;
        int[] visited = new int[n];

        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) {
                Queue<Integer> queue = new LinkedList<>();
                queue.offer(i);
                visited[i] = 1;
                while (!queue.isEmpty()) {
                    int cur = queue.poll();
                    int curColor = visited[cur];
                    int neiColor = curColor == 1 ? 2 : 1;
                    for (int nei : graph[cur]) {
                        if (visited[nei] == 0) {
                            visited[nei] = neiColor;
                            queue.offer(nei);
                        } else {
                            if (visited[nei] != neiColor) return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // S1.2: BFS
    // time = O(n), space = O(n)
    public boolean isBipartite2(int[][] graph) {
        int n = graph.length;
        int[] visited = new int[n];
        Arrays.fill(visited, -1); // -1: unvisited; 0: group0; 1: group1
        for (int i = 0; i < n; i++) {
            if (visited[i] != -1) continue;
            Queue<int[]> queue = new LinkedList<>(); // {node, group}
            queue.offer(new int[]{i, 0});

            while (!queue.isEmpty()) {
                int[] cur = queue.poll();
                int node = cur[0], group = cur[1];
                for (int next : graph[node]) {
                    if (visited[next] != -1) {
                        if (visited[next] == group) return false; // 矛盾
                    } else {
                        visited[next] = 1 - group;
                        queue.offer(new int[]{next, 1 - group});
                    }
                }
            }
        }
        return true;
    }

    // S2: Union Find
    // time = O(nlogn), space = O(n)
    private int[] parent;
    public boolean isBipartite3(int[][] graph) {
        int n = graph.length;
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        for (int i = 0; i < n; i++) {
            int k = 0;
            if (graph[i].length > 0) k = graph[i][0];
            for (int x : graph[i]) {
                if (findParent(x) == findParent(i)) return false;
                union(k, x);
            }
        }
        return true;
    }

    private int findParent(int x) {
        if (x != parent[x]) parent[x] = findParent(parent[x]);
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
 * 顺着点走，next结点肯定是B,如果推出矛盾来了，那肯定是false
 * 有可能不连通
 * 随便抓一个，做bfs，不停翻转看是否有矛盾出来
 * S2: union find
 *
 */
