package LC601_900;
import java.util.*;
public class LC882_ReachableNodesInSubdividedGraph {
    /**
     * You are given an undirected graph (the "original graph") with n nodes labeled from 0 to n - 1. You decide to
     * subdivide each edge in the graph into a chain of nodes, with the number of new nodes varying between each edge.
     *
     * The graph is given as a 2D array of edges where edges[i] = [ui, vi, cnti] indicates that there is an edge
     * between nodes ui and vi in the original graph, and cnti is the total number of new nodes that you will subdivide
     * the edge into. Note that cnti == 0 means you will not subdivide the edge.
     *
     * To subdivide the edge [ui, vi], replace it with (cnti + 1) new edges and cnti new nodes.
     * The new nodes are x1, x2, ..., xcnti, and the new edges are [ui, x1], [x1, x2], [x2, x3], ...,
     * [xcnti+1, xcnti], [xcnti, vi].
     *
     * In this new graph, you want to know how many nodes are reachable from the node 0, where a node is reachable if
     * the distance is maxMoves or less.
     *
     * Given the original graph and maxMoves, return the number of nodes that are reachable from node 0 in the new graph.
     *
     * Input: edges = [[0,1,10],[0,2,1],[1,2,2]], maxMoves = 6, n = 3
     * Output: 13
     *
     * Constraints:
     *
     * 0 <= edges.length <= min(n * (n - 1) / 2, 10^4)
     * edges[i].length == 3
     * 0 <= ui < vi < n
     * There are no multiple edges in the graph.
     * 0 <= cnti <= 10^4
     * 0 <= maxMoves <= 10^9
     * 1 <= n <= 3000
     * @param edges
     * @param maxMoves
     * @param n
     * @return
     */
    // time = O(mlogn), space = O(n)  m: the length of edges
    public int reachableNodes(int[][] edges, int maxMoves, int n) {
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1], c = edge[2] + 1; // 注意：这里的weight要+1，因为走到大点的话还要中间小点个数+1
            graph[a].add(new int[]{b, c});
            graph[b].add(new int[]{a, c});
        }

        int[] dist = new int[n];
        Arrays.fill(dist, -1);

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]); // {dist, nodeIdx}
        pq.offer(new int[]{0, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int d = cur[0], node = cur[1];
            // 可能有很多种不同的方案走到cur，对应的d是不同的，而且可能同时存在于队列中，所以要在这里check是否第一次弹出cur
            // 第一次弹出即为最短方案
            if (dist[node] != -1) continue;
            dist[node] = d;

            for (int[] x : graph[node]) {
                int next = x[0], weight = x[1];
                if (dist[next] != -1) continue;
                if (d + weight <= maxMoves) {
                    pq.offer(new int[]{d + weight, next});
                }
            }
        }

        // check each edge
        int count = 0;
        for (int[] edge : edges) {  // count all smaller nodes
            int a = edge[0], b = edge[1], c = edge[2];
            int sum = 0; // 注意：sum要初始化在for loop里面！！！
            if (dist[a] != -1) sum += maxMoves - dist[a]; // 在已经可以到达a的基础上，看还有多少余力可以reach到ab间的其他点！
            if (dist[b] != -1) sum += maxMoves - dist[b];
            count += Math.min(sum, c);
        }

        for (int i = 0; i < n; i++) {  // count all larger nodes
            if (dist[i] != -1) count++;
        }
        return count;
    }
}
/**
 * Dijkstra = bfs + pq
 * 小点个数 == 边的权重
 * Dijkstra解决单源起点，每条边权重都是非负的，这样可以求得任意一个点到起点之间的最短距离。
 * dist[0] = 0
 * dist[1] = 5
 * dist[2] = 2
 * 小点如何处理？Dijkstra??? => 效率不行，cnt ~ 10^4
 * 比较直观的方法：0 ~ maxMoves
 * x + y <= cnt
 * 查看每条边，maxMoves - dist[0] ...  maxMoves - dist[1]... 再加起来就可以知道可以访问哪些点
 */
