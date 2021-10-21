package LC1201_1500;
import java.util.*;
public class LC1245_TreeDiameter {
    /**
     * Given an undirected tree, return its diameter: the number of edges in a longest path in that tree.
     *
     * The tree is given as an array of edges where edges[i] = [u, v] is a bidirectional edge between nodes u and v.
     * Each node has labels in the set {0, 1, ..., edges.length}.
     *
     * Input: edges = [[0,1],[0,2]]
     * Output: 2
     *
     * Constraints:
     *
     * 0 <= edges.length < 10^4
     * edges[i][0] != edges[i][1]
     * 0 <= edges[i][j] <= edges.length
     * The given edges form an undirected tree.
     * @param edges
     * @return
     */
    // time = O(n), space = O(n)   n: # of nodes in the graph
    public int treeDiameter(int[][] edges) {
        int n = edges.length + 1;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        int[] t1 = bfs(graph, 0, n);
        int[] t2 = bfs(graph, t1[0], n);
        return t2[1];
    }

    private int[] bfs(List<List<Integer>> graph, int start, int n) {
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        dist[start] = 0;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int next : graph.get(cur)) {
                if (dist[next] == -1) { // -1就能表示没访问过，相当于check visited
                    queue.offer(next);
                    dist[next] = dist[cur] + 1;
                }
            }
        }

        int maxDist = 0, nodeIdx = -1;
        for (int i = 0; i < n; i++) {
            if (dist[i] > maxDist) {
                maxDist = dist[i];
                nodeIdx = i;
            }
        }
        return new int[]{nodeIdx, maxDist};
    }
}
/**
 * 对于一张无向图的树，求最长的点到点距离，有一个非常成熟的做法。
 * 我们可以任意取一点A，用BFS找到距离A最远的节点B。那么B一定是就是“最长的点到点距离”的其中一个点。
 * 然后我们再以B为起点，用BFS找到距离B最远的节点C。那么BC就是答案。
 */
