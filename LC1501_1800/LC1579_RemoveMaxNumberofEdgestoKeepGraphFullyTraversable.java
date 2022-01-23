package LC1501_1800;
import java.util.*;
public class LC1579_RemoveMaxNumberofEdgestoKeepGraphFullyTraversable {
    /**
     * Alice and Bob have an undirected graph of n nodes and 3 types of edges:
     *
     * Type 1: Can be traversed by Alice only.
     * Type 2: Can be traversed by Bob only.
     * Type 3: Can by traversed by both Alice and Bob.
     * Given an array edges where edges[i] = [typei, ui, vi] represents a bidirectional edge of type typei between
     * nodes ui and vi, find the maximum number of edges you can remove so that after removing the edges, the graph can
     * still be fully traversed by both Alice and Bob. The graph is fully traversed by Alice and Bob if starting from
     * any node, they can reach all other nodes.
     *
     * Return the maximum number of edges you can remove, or return -1 if it's impossible for the graph to be fully
     * traversed by Alice and Bob.
     *
     * Input: n = 4, edges = [[3,1,2],[3,2,3],[1,1,3],[1,2,4],[1,1,2],[2,3,4]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * 1 <= edges.length <= min(10^5, 3 * n * (n-1) / 2)
     * edges[i].length == 3
     * 1 <= edges[i][0] <= 3
     * 1 <= edges[i][1] < edges[i][2] <= n
     * All tuples (typei, ui, vi) are distinct.
     * @param n
     * @param edges
     * @return
     */
    // time = O(m * a(n)), space = O(n)  其中 mm 是数组{edges}edges 的长度，a 是阿克曼函数的反函数。
    private int[] parent, parent0;
    public int maxNumEdgesToRemove(int n, int[][] edges) {
        List<int[]> edges0 = new ArrayList<>();
        List<int[]> edges1 = new ArrayList<>();
        List<int[]> edges2 = new ArrayList<>();
        for (int[] edge : edges) {
            if (edge[0] == 3) edges0.add(new int[]{edge[1], edge[2]});
            if (edge[0] == 1) edges1.add(new int[]{edge[1], edge[2]});
            if (edge[0] == 2) edges2.add(new int[]{edge[1], edge[2]});
        }
        parent = new int[n + 1];
        for (int i = 0; i < n + 1; i++) parent[i] = i;

        int count0 = 0, count1 = 0, count2 = 0;
        for (int[] edge : edges0) {
            int a = edge[0], b = edge[1];
            if (findParent(a) != findParent(b)) {
                union(a, b);
                count0++;
            }
        }

        parent0 = parent.clone();
        for (int[] edge : edges1) {
            int a = edge[0], b = edge[1];
            if (findParent(a) != findParent(b)) {
                union(a, b);
                count1++;
            }
        }
        if (count0 + count1 != n - 1) return -1;

        parent = parent0;
        for (int[] edge : edges2) {
            int a = edge[0], b = edge[1];
            if (findParent(a) != findParent(b)) {
                union(a, b);
                count2++;
            }
        }
        if (count0 + count2 != n - 1) return -1;

        return edges.length - count0 - count1 - count2;
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
 * MST: minimum spanning tree
 * 每条边有权重，希望把这些点连通起来形成一棵树，这样的话所需用到的权重的和最小
 * N connected nodes, N edges (M <= N * (N - 1) / 2)
 * what is the minimum number of edges to connect N nodes?
 * N - 1
 * 如何在M个点里挑出N - 1个点？
 * 1 2 3 4 5 6 ....
 * 0 x 0 x 0 x
 * 全排列，能用就用
 * N - 1: you're sure all the N nodes are connected.
 * 优先用第三类的边 => type 3 edges are priority
 * 尽量少的边把每一个连通块都连通起来
 * 处理完alice后要处理bob专属的边
 * 对于生成树而言，你不用关心它的排列是什么
 * 优先用权重较小的边，不是任意一个排列，是要从小到大排列
 */