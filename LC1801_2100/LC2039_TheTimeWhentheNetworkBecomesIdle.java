package LC1801_2100;
import java.util.*;
public class LC2039_TheTimeWhentheNetworkBecomesIdle {
    /**
     * There is a network of n servers, labeled from 0 to n - 1. You are given a 2D integer array edges, where
     * edges[i] = [ui, vi] indicates there is a message channel between servers ui and vi, and they can pass any number
     * of messages to each other directly in one second. You are also given a 0-indexed integer array patience of
     * length n.
     *
     * All servers are connected, i.e., a message can be passed from one server to any other server(s) directly or
     * indirectly through the message channels.
     *
     * The server labeled 0 is the master server. The rest are data servers. Each data server needs to send its message
     * to the master server for processing and wait for a reply. Messages move between servers optimally, so every
     * message takes the least amount of time to arrive at the master server. The master server will process all newly
     * arrived messages instantly and send a reply to the originating server via the reversed path the message had gone
     * through.
     *
     * At the beginning of second 0, each data server sends its message to be processed. Starting from second 1, at the
     * beginning of every second, each data server will check if it has received a reply to the message it sent
     * (including any newly arrived replies) from the master server:
     *
     * If it has not, it will resend the message periodically. The data server i will resend the message every
     * patience[i] second(s), i.e., the data server i will resend the message if patience[i] second(s) have elapsed
     * since the last time the message was sent from this server.
     * Otherwise, no more resending will occur from this server.
     * The network becomes idle when there are no messages passing between servers or arriving at servers.
     *
     * Return the earliest second starting from which the network becomes idle.
     *
     * Input: edges = [[0,1],[1,2]], patience = [0,2,1]
     * Output: 8
     *
     * Constraints:
     *
     * n == patience.length
     * 2 <= n <= 10^5
     * patience[0] == 0
     * 1 <= patience[i] <= 105 for 1 <= i < n
     * 1 <= edges.length <= min(105, n * (n - 1) / 2)
     * edges[i].length == 2
     * 0 <= ui, vi < n
     * ui != vi
     * There are no duplicate edges.
     * Each server can directly or indirectly reach another server.
     * @param edges
     * @param patience
     * @return
     */
    // time = O(n), space = O(n)
    public int networkBecomesIdle(int[][] edges, int[] patience) {
        int n = patience.length;
        List<List<Integer>> graph = buildGraph(edges, n);
        int[] steps = bfs(graph, 0, n);

        int res = 0;
        for (int i = 1; i < n; i++) {
            int time = steps[i] * 2;
            int sent = time / patience[i] == 0 ? 0 : (time % patience[i] == 0 ? time / patience[i] - 1 : time / patience[i]);
            int totalTime = time + patience[i] * sent;
            res = Math.max(res, totalTime);
        }
        return res + 1;
    }

    private List<List<Integer>> buildGraph(int[][] edges, int n) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return graph;
    }

    private int[] bfs(List<List<Integer>> graph, int start, int n) {
        int[] steps = new int[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        boolean[] visited = new boolean[n];
        visited[start] = true;

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                steps[cur] = step;
                for (int next : graph.get(cur)) {
                    if (visited[next]) continue;
                    queue.offer(next);
                    visited[next] = true;
                }
            }
            step++;
        }
        return steps;
    }
}
