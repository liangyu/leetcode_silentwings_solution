package LC1801_2100;
import java.util.*;
public class LC1971_FindifPathExistsinGraph {
    /**
     * There is a bi-directional graph with n vertices, where each vertex is labeled from 0 to n - 1 (inclusive).
     * The edges in the graph are represented as a 2D integer array edges, where each edges[i] = [ui, vi] denotes a
     * bi-directional edge between vertex ui and vertex vi. Every vertex pair is connected by at most one edge, and no
     * vertex has an edge to itself.
     *
     * You want to determine if there is a valid path that exists from vertex start to vertex end.
     *
     * Given edges and the integers n, start, and end, return true if there is a valid path from start to end, or
     * false otherwise.
     *
     * Input: n = 3, edges = [[0,1],[1,2],[2,0]], start = 0, end = 2
     * Output: true
     *
     * Constraints:
     *
     * 1 <= n <= 2 * 10^5
     * 0 <= edges.length <= 2 * 10^5
     * edges[i].length == 2
     * 1 <= ui, vi <= n - 1
     * ui != vi
     * 1 <= start, end <= n - 1
     * There are no duplicate edges.
     * There are no self edges.
     * @param n
     * @param edges
     * @param start
     * @param end
     * @return
     */
    // S1: BFS
    // time = O(V + E), space = O(V + E)
    public boolean validPath(int n, int[][] edges, int start, int end) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            map.putIfAbsent(edge[0], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.putIfAbsent(edge[1], new ArrayList<>());
            map.get(edge[1]).add(edge[0]);
        }

        HashSet<Integer> set = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        set.add(start);

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            if (cur == end) return true;
            for (int next : map.get(cur)) {
                if (set.add(next)) {
                    queue.offer(next);
                }
            }
        }
        return false;
    }

    // S2: Union Find
    // time = O(nlogn), space = O(n)
    private int[] parent;
    public boolean validPath2(int n, int[][] edges, int start, int end) {
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        for (int[] edge : edges) {
            if (findParent(edge[0]) != findParent(edge[1])) {
                union(edge[0], edge[1]);
            }
        }
        return findParent(start) == findParent(end);
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
