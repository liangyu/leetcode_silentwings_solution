package LC2101_2400;
import java.util.*;
public class LC2192_AllAncestorsofaNodeinaDirectedAcyclicGraph {
    /**
     * You are given a positive integer n representing the number of nodes of a Directed Acyclic Graph (DAG). The nodes
     * are numbered from 0 to n - 1 (inclusive).
     *
     * You are also given a 2D integer array edges, where edges[i] = [fromi, toi] denotes that there is a unidirectional
     * edge from fromi to toi in the graph.
     *
     * Return a list answer, where answer[i] is the list of ancestors of the ith node, sorted in ascending order.
     *
     * A node u is an ancestor of another node v if u can reach v via a set of edges.
     *
     * Input: n = 8, edgeList = [[0,3],[0,4],[1,3],[2,4],[2,7],[3,5],[3,6],[3,7],[4,6]]
     * Output: [[],[],[],[0,1],[0,2],[0,1,3],[0,1,2,3,4],[0,1,2,3]]
     *
     * Constraints:
     *
     * 1 <= n <= 1000
     * 0 <= edges.length <= min(2000, n * (n - 1) / 2)
     * edges[i].length == 2
     * 0 <= fromi, toi <= n - 1
     * fromi != toi
     * There are no duplicate edges.
     * The graph is directed and acyclic.
     * @param n
     * @param edges
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        int[] indegree = new int[n];

        for (int[] edge : edges) { // O(m)
            int a = edge[0], b = edge[1];
            graph[a].add(b);
            indegree[b]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {  // O(n)
            if (indegree[i] == 0) queue.offer(i);
        }

        HashSet<Integer>[] nodes = new HashSet[n];
        for (int i = 0; i < n; i++) nodes[i] = new HashSet<>();

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int next : graph[cur]) {
                for (int x : nodes[cur]) {
                    nodes[next].add(x);
                }
                nodes[next].add(cur);
                indegree[next]--;
                if (indegree[next] == 0) queue.offer(next);
            }
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> list = new ArrayList<>(nodes[i]);
            Collections.sort(list);
            res.add(list);
        }
        return res;
    }
}
