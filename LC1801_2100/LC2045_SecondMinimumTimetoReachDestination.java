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
    // S1: BFS
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

    // S2: BFS
    // time = O(n), space = O(n)
    public int secondMinimum2(int n, int[][] edges, int time, int change) {
        List<Integer>[] next = new List[n + 1];
        for (int i = 0; i <= n; i++) next[i] = new ArrayList<>();
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1];
            next[a].add(b);
            next[b].add(a);
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{1, 0});
        int[] visited = new int[n + 1];
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        dist[1] = 0;

        while (!queue.isEmpty()) {
            int[] top = queue.poll();
            int cur = top[0], t = top[1];
            int tt;
            int round = t / change;
            if (round % 2 == 0) tt = t + time;
            else tt = (round + 1) * change + time;

            for (int nxt : next[cur]) {
                if (visited[nxt] < 2 && dist[nxt] < tt) {
                    dist[nxt] = tt;
                    visited[nxt]++;
                    queue.offer(new int[]{nxt, tt});

                    if (nxt == n && visited[nxt] == 2) return tt;
                }
            }
        }
        return -1;
    }
}
/**
 * 0 1 1 1 2 2 2 2 3 3 3 3 3 4 4 4 4 5 5 5
 * 边权不一样 => Dijkstra
 * 0 2 3 1 4 5 4
 * 0 [1 2 3 4] (3 6 6) -> 有点贪心的思想
 * 最先到达的结点优先处理
 * [a] [b c d] [e f g h] -> 时刻永远都是递增的，红绿灯是全局作用的
 * 在本题中，虽然有了红绿灯的干扰，但是我们仔细想一想，无论什么情况下，后加入的节点时间一定会比先加入的节点时间晚。
 * 不可能出现这种情况：A先弹出可到达C，B后弹出可到达D，结果D比C的到达时间反而早。
 * 这是因为即使A被红灯耽搁了出发，那么B必然不会赶在那个红灯前出发。无论如何D不会早C。
 * 基于以上分析，传统的BFS就可以解这道题了。
 * 本题tricky的地方在于求到达终点的第二最短路径（时间）。
 * 传统的BFS求最短路径，每个节点我们只需要访问一次，任何路径如果访问了二次相同的节点必然不会是最短距离。
 * 结点最多重复走几次？ => 最多走2次就行。为什么呢？
 * 假设从起点到终点的第二最短路径经过了A->...->B1->...->B2->...->B3->...->C，
 * 那么我们只要把多绕的圈子去掉，必然有两条更短的路径A->...->B1->...->B2->...-->C和A->...->B1->...->C。
 * BFS种允许每个结点被访问2次，2次以上就没用了。
 * 在这里，我们需要查看下是否之前访问过，允许放入第二次，第二次放入时间比第一次进入要长
 */