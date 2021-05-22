package LC1501_1800;
import java.util.*;
public class LC1786_NumberofRestrictedPathsFromFirsttoLastNode {
    /**
     * There is an undirected weighted connected graph. You are given a positive integer n which denotes that the graph
     * has n nodes labeled from 1 to n, and an array edges where each edges[i] = [ui, vi, weighti] denotes that there is
     * an edge between nodes ui and vi with weight equal to weighti.
     *
     * A path from node start to node end is a sequence of nodes [z0, z1, z2, ..., zk] such that z0 = start and zk = end
     * and there is an edge between zi and zi+1 where 0 <= i <= k-1.
     *
     * The distance of a path is the sum of the weights on the edges of the path. Let distanceToLastNode(x) denote the
     * shortest distance of a path between node n and node x. A restricted path is a path that also satisfies that
     * distanceToLastNode(zi) > distanceToLastNode(zi+1) where 0 <= i <= k-1.
     *
     * Return the number of restricted paths from node 1 to node n. Since that number may be too large, return it
     * modulo 10^9 + 7.
     *
     * Input: n = 5, edges = [[1,2,3],[1,3,3],[2,3,1],[1,4,2],[5,2,2],[3,5,1],[5,4,10]]
     * Output: 3
     *
     * Input: n = 7, edges = [[1,3,1],[4,1,2],[7,3,4],[2,5,3],[5,6,1],[6,7,2],[7,5,3],[2,6,4]]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= n <= 2 * 10^4
     * n - 1 <= edges.length <= 4 * 10^4
     * edges[i].length == 3
     * 1 <= ui, vi <= n
     * ui != vi
     * 1 <= weighti <= 10^5
     * There is at most one edge between any two nodes.
     * There is at least one path between any two nodes.
     *
     * @param n
     * @param edges
     * @return
     */

    public int countRestrictedPaths(int n, int[][] edges) {
        int[] dist = new int[20001];
        HashMap<Integer, List<Pair>> map = new HashMap<>();
        boolean[] visited = new boolean[20001];

        for (int[] e : edges) { // 构建邻接表
            int a = e[0] - 1;
            int b = e[1] - 1; // 0-based index
            map.putIfAbsent(a, new ArrayList<>());
            map.putIfAbsent(b, new ArrayList<>());
            map.get(a).add(new Pair(b, e[2]));
            map.get(b).add(new Pair(a, e[2]));
        }

        // Dijkstra: BFS + PQ
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
        pq.offer(new Pair(0, n - 1));

        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            int cur = p.y;
            if (visited[cur]) continue;
            dist[cur] = p.x; // shortest dist from any point to the starting point
            visited[cur] = true;

            for (Pair pair : map.get(cur)) {
                if (visited[pair.x]) continue;
                pq.offer(new Pair(p.x + len, pair.x));
            }
        }
    }

    private class Pair {
        private int x;
        private int y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

/**
 * Dijkstra: single source, non-negative weight -> 单源，到起点的最短距离
 * Elog(E)
 * node n是海平面，其他点到n的距离就是到海平面的海拔差，我经过的每个点，其海拔是依次递减
 * 实质：topdown: DFS + memo
 * 只选海拔更低的地方去，一路去更低的地方 -> 一旦走到海平面就成功了
 * memo: pathNum(x1) = pathNum(x2) + pathNum(x3) + ...
 * pathNum(x2) = pathNum(x3) + ...
 * => Dijkstra + DFS
 * 不需要用visited去重 -> 因为递归的过程决定永远只会往海拔更低的地方走，所以不可能会访问到之前访问过的路径，不会走回头路
 */
