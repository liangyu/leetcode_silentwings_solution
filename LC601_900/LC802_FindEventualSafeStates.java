package LC601_900;
import java.util.*;
public class LC802_FindEventualSafeStates {
    /**
     * We start at some node in a directed graph, and every turn, we walk along a directed edge of the graph. If we
     * reach a terminal node (that is, it has no outgoing directed edges), we stop.
     *
     * We define a starting node to be safe if we must eventually walk to a terminal node. More specifically, there is
     * a natural number k, so that we must have stopped at a terminal node in less than k steps for any choice of where
     * to walk.
     *
     * Return an array containing all the safe nodes of the graph. The answer should be sorted in ascending order.
     *
     * The directed graph has n nodes with labels from 0 to n - 1, where n is the length of graph. The graph is given
     * in the following form: graph[i] is a list of labels j such that (i, j) is a directed edge of the graph, going
     * from node i to node j.
     *
     * Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
     * Output: [2,4,5,6]
     *
     * Constraints:
     *
     * n == graph.length
     * 1 <= n <= 10^4
     * 0 <= graph[i].legnth <= n
     * graph[i] is sorted in a strictly increasing order.
     * The graph may contain self-loops.
     * The number of edges in the graph will be in the range [1, 4 * 104].
     * @param graph
     * @return
     */
    // S1: BFS
    // time = O(V + E), space = O(V)
    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer> res = new ArrayList<>();
        int n = graph.length;
        int[] outdegree = new int[n];
        List<Integer>[] prev = new List[n];
        for (int i = 0; i < n; i++) prev[i] = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j : graph[i]) {
                prev[j].add(i);
                outdegree[i]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (outdegree[i] == 0) {
                queue.offer(i);
                res.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int x : prev[cur]) {
                outdegree[x]--;
                if (outdegree[x] == 0) {
                    queue.offer(x);
                    res.add(x);
                }
            }
        }
        // order被打乱，需要重新sort
        Collections.sort(res);
        return res;
    }

    // S2: DFS
    // time = O(V + E), space = O(V)
    public List<Integer> eventualSafeNodes2(int[][] graph) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (graph == null || graph.length == 0) return res;

        int n = graph.length;
        int[] status = new int[n];

        for (int i = 0; i < n; i++) {
            if (!dfs(graph, status, i)) res.add(i);
        }
        // graph[i] is sorted in a strictly increasing order => 这里不需要再sort了
        return res;
    }

    private boolean dfs(int[][] graph, int[] status, int cur) {
        if (status[cur] == 2) return false;
        if (status[cur] == 1) return true;

        status[cur] = 1;
        for (int next : graph[cur]) {
            if (dfs(graph, status, next)) return true;
        }
        status[cur] = 2;
        return false;
    }
}
/**
 * dfs: visited[i]
 * 0: never visited
 * 1: first-time visit
 * 2: permanently visit
 * 对无向图的话，最好的做法是用union find来判断是否有环！
 * S2: bfs
 * 拓扑排序的应用。最容易判定safe的节点，是那些出度为0的节点。将这些点剪除之后，接下来出度为0的节点，肯定还是safe的节点。
 * 以此BFS不断推进，如果还有剩下的节点，那么他们肯定出度都不为0，即是互相成环的，可以终止程序。
 */