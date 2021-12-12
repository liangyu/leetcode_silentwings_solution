package LC901_1200;
import java.util.*;
public class LC1135_ConnectingCitiesWithMinimumCost {
    /**
     * There are n cities numbered from 1 to n.
     *
     * You are given connections, where each connections[i] = [city1, city2, cost] represents the cost to connect city1
     * and city2 together.  (A connection is bidirectional: connecting city1 and city2 is the same as connecting city2
     * and city1.)
     *
     * Return the minimum cost so that for every pair of cities, there exists a path of connections (possibly of
     * length 1) that connects those two cities together.  The cost is the sum of the connection costs used. If the
     * task is impossible, return -1.
     *
     * Input: n = 3, connections = [[1,2,5],[1,3,6],[2,3,1]]
     * Output: 6
     *
     * Note:
     *
     * 1 <= n <= 10000
     * 1 <= connections.length <= 10000
     * 1 <= connections[i][0], connections[i][1] <= n
     * 0 <= connections[i][2] <= 10^5
     * connections[i][0] != connections[i][1]
     * @param n
     * @param connections
     * @return
     */
    // time = O(ElogE), space = O(n)   E:  total number of edges (connections)
    private int[] parent;
    public int minimumCost(int n, int[][] connections) {
        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) parent[i] = i;

        Arrays.sort(connections, (o1, o2) -> o1[2] - o2[2]);

        int res = 0;
        for (int[] edge : connections) {
            int a = edge[0];
            int b = edge[1];
            if (findParent(a) != findParent(b)) {
                union(a, b);
                res += edge[2];
            }
        }

        for (int i = 1; i <= n; i++) {
            if (findParent(i) != parent[1]) return -1;
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
 * 本题的本质是构造一个最小生成树．一个比较简单的算法就是Kruskal算法，本质就是贪心＋Union Find．
 * 按照边的权重顺序（从小到大）将边加入生成树中，但是若加入该边会与生成树形成环则不加入该边。
 * 直到树中含有V-1条边为止。
 * 这些边组成的就是该图的最小生成树。
 * Kruskal算法的时间复杂度为 ElogE。
 */
