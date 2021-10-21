package LC1801_2100;
import java.util.*;
public class LC2045_SecondMinimumTimetoReachDestination {
    /**
     * A city is represented as a bi-directional connected graph with n vertices where each vertex is labeled from 1 to
     * n (inclusive). The edges in the graph are represented as a 2D integer array edges, where each edges[i] = [ui, vi]
     * denotes a bi-directional edge between vertex ui and vertex vi. Every vertex pair is connected by at most one
     * edge, and no vertex has an edge to itself. The time taken to traverse any edge is time minutes.
     *
     * Each vertex has a traffic signal which changes its color from green to red and vice versa every change minutes.
     * All signals change at the same time. You can enter a vertex at any time, but can leave a vertex only when the
     * signal is green. You cannot wait at a vertex if the signal is green.
     *
     * The second minimum value is defined as the smallest value strictly larger than the minimum value.
     *
     * For example the second minimum value of [2, 3, 4] is 3, and the second minimum value of [2, 2, 4] is 4.
     * Given n, edges, time, and change, return the second minimum time it will take to go from vertex 1 to vertex n.
     *
     * Notes:
     *
     * You can go through any vertex any number of times, including 1 and n.
     * You can assume that when the journey starts, all signals have just turned green.
     *
     * Input: n = 5, edges = [[1,2],[1,3],[1,4],[3,4],[4,5]], time = 3, change = 5
     * Output: 13
     *
     * Constraints:
     *
     * 2 <= n <= 10^4
     * n - 1 <= edges.length <= min(2 * 10^4, n * (n - 1) / 2)
     * edges[i].length == 2
     * 1 <= ui, vi <= n
     * ui != vi
     * There are no duplicate edges.
     * Each vertex can be reached directly or indirectly from every other vertex.
     * 1 <= time, change <= 10^3
     * @param n
     * @param edges
     * @param time
     * @param change
     * @return
     */
    // time = O(n), space = O(n)
    public int secondMinimum(int n, int[][] edges, int time, int change) {
        List<List<Integer>> graph = buildGraph(edges, n);

        return bfs(graph, 1, n, time, change);
    }

    private List<List<Integer>> buildGraph(int[][] edges, int n) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return graph;
    }

    private int bfs(List<List<Integer>> graph, int start, int n, int time, int change) {
        int res = -1;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        int total = 0, diff = 0;
        int[][] visited = new int[n + 1][2];
        for (int[] v : visited) Arrays.fill(v, Integer.MAX_VALUE);
        visited[start][0] = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                if (cur == n) {
                    if (res == -1) res = total;
                    else if (res != total) return total - diff;
                }
                for (int next : graph.get(cur)) {
                    if (visited[next][0] > total) {
                        visited[next][0] = total;
                        queue.offer(next);
                    } else if (total > visited[next][0] && visited[next][1] > total) {
                        visited[next][1] = total;
                        queue.offer(next);
                    }
                }
            }
            total += time;
            if ((total / change) % 2 == 1) {
                diff = change - total % change;
                total += diff;
            } else diff = 0;
        }
        return -1;
    }
}
