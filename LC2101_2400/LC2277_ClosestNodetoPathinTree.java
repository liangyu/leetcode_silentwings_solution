package LC2101_2400;
import java.util.*;
public class LC2277_ClosestNodetoPathinTree {
    /**
     * You are given a positive integer n representing the number of nodes in a tree, numbered from 0 to n - 1
     * (inclusive). You are also given a 2D integer array edges of length n - 1, where edges[i] = [node1i, node2i]
     * denotes that there is a bidirectional edge connecting node1i and node2i in the tree.
     *
     * You are given a 0-indexed integer array query of length m where query[i] = [starti, endi, nodei] means that for
     * the ith query, you are tasked with finding the node on the path from starti to endi that is closest to nodei.
     *
     * Return an integer array answer of length m, where answer[i] is the answer to the ith query.
     *
     * Input: n = 7, edges = [[0,1],[0,2],[0,3],[1,4],[2,5],[2,6]], query = [[5,3,4],[5,3,6]]
     * Output: [0,2]
     *
     * Constraints:
     *
     * 1 <= n <= 1000
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= node1i, node2i <= n - 1
     * node1i != node2i
     * 1 <= query.length <= 1000
     * query[i].length == 3
     * 0 <= starti, endi, nodei <= n - 1
     * The graph is a tree.
     * @param n
     * @param edges
     * @param query
     * @return
     */
    // S1: bfs
    // time = O(m * n + n^2), space = O(n^2)
    public int[] closestNode(int n, int[][] edges, int[][] query) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] x : edges) {
            int a = x[0], b = x[1];
            graph[a].add(b);
            graph[b].add(a);
        }

        int[][] dist = bfs(graph);
        int m = query.length;
        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            int a = query[i][0], b = query[i][1], c = query[i][2];
            int minDist = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                int d1 = dist[j][a];
                int d2 = dist[j][b];
                int d3 = dist[j][c];
                if (d1 + d2 + d3 < minDist) {
                    minDist = d1 + d2 + d3;
                    res[i] = j;
                }
            }
        }
        return res;
    }

    private int[][] bfs(List<Integer>[] graph) {
        int n = graph.length;
        int[][] dist = new int[n][n];
        Queue<Integer> queue = new LinkedList<>();
        boolean[] st;
        for (int i = 0; i < n; i++) {
            queue.offer(i);
            st = new boolean[n];
            st[i] = true;

            int step = 0;
            while (!queue.isEmpty()) {
                int size = queue.size();
                while (size-- > 0) {
                    int cur = queue.poll();
                    dist[i][cur] = step;
                    for (int next : graph[cur]) {
                        if (st[next]) continue;
                        st[next] = true;
                        queue.offer(next);
                    }
                }
                step++;
            }
        }
        return dist;
    }

    // S2: dfs
    // time = O(n^2 + m * n), space = O(n^2)
    boolean[] visited;
    public int[] closestNode2(int n, int[][] edges, int[][] query) {
        HashMap<String, Integer> map = new HashMap<>();
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] x : edges) {
            int a = x[0], b = x[1];
            graph[a].add(b);
            graph[b].add(a);
        }

        for (int i = 0; i < n; i++) {
            visited = new boolean[n];
            visited[i] = true;
            dfs(graph, map, i, i, 0);
        }

        int m = query.length;
        int[] res = new int[m];
        Arrays.fill(res, Integer.MAX_VALUE);
        for (int i = 0; i < m; i++) {
            int a = query[i][0], b = query[i][1], c = query[i][2];
            List<Integer> path = new ArrayList<>();
            visited = new boolean[n];
            visited[a] = true;
            path.add(a);
            helper(graph, a, b, path);
            int minDist = Integer.MAX_VALUE;
            for (int x : path) {
                String key = c + "#" + x;
                int dist = map.getOrDefault(key, 0);
                if (dist < minDist) {
                    minDist = dist;
                    res[i] = x;
                }
            }
        }
        return res;
    }

    private void dfs(List<Integer>[] graph, HashMap<String, Integer> map, int root, int cur, int step) {
        for (int next : graph[cur]) {
            if (!visited[next]) {
                String key = root + "#" + next;
                map.put(key, step + 1);
                visited[next] = true;
                dfs(graph, map, root, next, step + 1);
            }
        }
    }

    private boolean helper(List<Integer>[] graph, int a, int b, List<Integer> path) {
        if (a == b) return true;
        for (int next : graph[a]) {
            if (!visited[next]) {
                visited[next] = true;
                path.add(next);
                if (helper(graph, next, b, path)) return true;
                path.remove(path.size() - 1);
                visited[next] = false;
            }
        }
        return false;
    }
}
