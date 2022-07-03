package LC2101_2400;

public class LC2316_CountUnreachablePairsofNodesinanUndirectedGraph {
    /**
     * You are given an integer n. There is an undirected graph with n nodes, numbered from 0 to n - 1. You are given a
     * 2D integer array edges where edges[i] = [ai, bi] denotes that there exists an undirected edge connecting nodes
     * ai and bi.
     *
     * Return the number of pairs of different nodes that are unreachable from each other.
     *
     * Input: n = 3, edges = [[0,1],[0,2],[1,2]]
     * Output: 0
     *
     * Input: n = 7, edges = [[0,2],[0,5],[2,4],[1,6],[5,4]]
     * Output: 14
     *
     * Constraints:
     *
     * 1 <= n <= 105
     * 0 <= edges.length <= 2 * 10^5
     * edges[i].length == 2
     * 0 <= ai, bi < n
     * ai != bi
     * There are no repeated edges.
     * @param n
     * @param edges
     * @return
     */
    // time = O(nlogn), space = O(n)
    int[] p, s;
    public long countPairs(int n, int[][] edges) {
        p = new int[n];
        s = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = i;
            s[i] = 1;
        }

        for (int[] x : edges) {
            int a = x[0], b = x[1];
            if (find(a) != find(b)) union(a, b);
        }

        long res = (long) n * (n - 1) / 2;
        for (int i = 0; i < n; i++) {
            if (i == p[i]) res -= (long) s[i] * (s[i] - 1) / 2;
        }
        return res;
    }

    private int find(int x) {
        if (x != p[x]) p[x] = find(p[x]);
        return p[x];
    }

    private void union(int x, int y) {
        x = p[x];
        y = p[y];
        p[y] = x;
        s[x] += s[y];
    }
}
/**
 * n = 100 -> Floyd
 * 不能暴力枚举
 * => 做补集
 * 求多少点对可以到达
 * C(n,2) - 可以到达的点对数 -> 求连通块的大小 组合数
 * 1.图的遍历
 * 2.并查集
 */
