package LC001_300;
import java.util.*;
public class LC261_GraphValidTree {
    /**
     * You have a graph of n nodes labeled from 0 to n - 1. You are given an integer n and a list of edges where
     * edges[i] = [ai, bi] indicates that there is an undirected edge between nodes ai and bi in the graph.
     *
     * Return true if the edges of the given graph make up a valid tree, and false otherwise.
     *
     * Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= 2000 <= n
     * 0 <= edges.length <= 5000
     * edges[i].length == 2
     * 0 <= ai, bi < n
     * ai != bi
     * There are no self-loops or repeated edges.
     * @param n
     * @param edges
     * @return
     */
    // S1: Topological Sort
    // time = O(V + E), space = O(V + E)
    public boolean validTree(int n, int[][] edges) {
        // corner case
        if (edges == null) return false;

        List<List<Integer>> graph = buildGraph(n, edges);
        int[] status = new int[n];

        if (containsCycle(graph, status, 0, -1)) return false;
        for (int s : status) {
            if (s == 0) return false;
        }
        return true;
    }

    private List<List<Integer>> buildGraph(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return graph;
    }

    private boolean containsCycle(List<List<Integer>> graph, int[] status, int cur, int prev) {
        if (status[cur] == 2) return false;
        if (status[cur] == 1) return true;

        status[cur] = 1;
        for (int next : graph.get(cur)) {
            if (next == prev) continue;
            if (containsCycle(graph, status, next, cur)) return true;
        }
        status[cur] = 2;
        return false;
    }

    // S2: Union Find
    // time = O(nlogn), space = O(n)  n: number of nodes
    private int[] parent;
    public boolean validTree2(int n, int[][] edges) {
        // corner case
        if (edges == null || edges.length != n - 1) return false;

        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        for (int[] edge : edges) {
            if (findParent(edge[0]) == findParent(edge[1])) return false;
            union(edge[0], edge[1]);
        }
        return true;
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
