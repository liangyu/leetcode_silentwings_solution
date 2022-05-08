package LC2101_2400;
import java.util.*;
public class LC2203_MinimumWeightedSubgraphWiththeRequiredPaths {
    /**
     * You are given an integer n denoting the number of nodes of a weighted directed graph. The nodes are numbered from
     * 0 to n - 1.
     *
     * You are also given a 2D integer array edges where edges[i] = [fromi, toi, weighti] denotes that there exists a
     * directed edge from fromi to toi with weight weighti.
     *
     * Lastly, you are given three distinct integers src1, src2, and dest denoting three distinct nodes of the graph.
     *
     * Return the minimum weight of a subgraph of the graph such that it is possible to reach dest from both src1 and
     * src2 via a set of edges of this subgraph. In case such a subgraph does not exist, return -1.
     *
     * A subgraph is a graph whose vertices and edges are subsets of the original graph. The weight of a subgraph is the
     * sum of weights of its constituent edges.
     *
     * Input: n = 6, edges = [[0,2,2],[0,5,6],[1,0,3],[1,4,5],[2,1,1],[2,3,3],[2,3,4],[3,4,2],[4,5,1]], src1 = 0,
     * src2 = 1, dest = 5
     * Output: 9
     *
     * Constraints:
     *
     * 3 <= n <= 10^5
     * 0 <= edges.length <= 10^5
     * edges[i].length == 3
     * 0 <= fromi, toi, src1, src2, dest <= n - 1
     * fromi != toi
     * src1, src2, and dest are pairwise distinct.
     * 1 <= weight[i] <= 10^5
     * @param n
     * @param edges
     * @param src1
     * @param src2
     * @param dest
     * @return
     */
    // time = O(nlogn), space = O(n)
    public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest) {
        List<int[]>[] graph1 = new List[n];
        List<int[]>[] graph2 = new List[n];
        for (int i = 0; i < n; i++) {
            graph1[i] = new ArrayList<>();
            graph2[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int a = edge[0], b = edge[1], c = edge[2];
            graph1[a].add(new int[]{b, c});
            graph2[b].add(new int[]{a, c});
        }

        long[] val1 = new long[n];
        long[] val2 = new long[n];
        long[] val3 = new long[n];
        Arrays.fill(val1, -1);
        Arrays.fill(val2, -1);
        Arrays.fill(val3, -1);

        helper(graph1, src1, val1);
        helper(graph1, src2, val2);
        helper(graph2, dest, val3);

        long res = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (val1[i] == -1 || val2[i] == -1 || val3[i] == -1) continue;
            res = Math.min(res, val1[i] + val2[i] + val3[i]);
        }
        return res == Long.MAX_VALUE ? -1 : res;
    }

    private void helper(List<int[]>[] graph, int src, long[] res) {
        PriorityQueue<long[]> pq = new PriorityQueue<>((o1, o2) -> Long.compare(o1[1], o2[1]));
        pq.offer(new long[]{src, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int node = (int) cur[0];
            long cost = cur[1];
            if (res[node] != -1) continue;
            res[node] = cost;

            for (int[] x : graph[node]) {
                int next = x[0], weight = x[1];
                pq.offer(new long[]{next, cost + weight});
            }
        }
    }
}
/**
 * 2条路径如果没有任何重叠，直接加和即可
 * 路径万一有交叉怎么办？=> 交点
 * 最显著的问题：交点之后，2条路都可以走，肯定会选最短的那条路径。
 * 需要的路径模式：肯定是s1和s2在哪个地方交汇了，然后一起走到dest
 * 表面上求2端路径最小化，其实是三段路径和的最小化
 * 如果遍历M 0 - 10^5
 * min{s1[i] + s2[i] + dest[i]}
 * Dijkstra -> 适用single source, non-negative weight
 * O(ElogE) 非稠密图上是非常高效的算法
 * 构造一个反图，从dest作为source即可。
 */