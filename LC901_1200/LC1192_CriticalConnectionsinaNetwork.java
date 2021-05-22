package LC901_1200;
import java.util.*;
public class LC1192_CriticalConnectionsinaNetwork {
    /**
     * There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections forming a
     * network where connections[i] = [a, b] represents a connection between servers a and b. Any server can reach any
     * other server directly or indirectly through the network.
     *
     * A critical connection is a connection that, if removed, will make some server unable to reach some other server.
     *
     * Return all critical connections in the network in any order.
     *
     * Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
     * Output: [[1,3]]
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * n-1 <= connections.length <= 10^5
     * connections[i][0] != connections[i][1]
     * There are no repeated connections.
     * @param n
     * @param connections
     * @return
     */
    // time = O(V + E), space = O(E)
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (connections == null || connections.size() == 0 || connections.get(0) == null || connections.get(0).size() == 0) {
            return res;
        }

        // step 1: build graph
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (List<Integer> c : connections) {
            graph.get(c.get(0)).add(c.get(1));
            graph.get(c.get(1)).add(c.get(0));
        }

        // step 2: check cycles
        int[] ts = new int[n];
        dfs(graph, 0, -1, ts, res);
        return res;
    }

    // step 3: topological sort
    private int t = 1;
    private int dfs(List<List<Integer>> graph, int cur, int prev, int[] ts, List<List<Integer>> res) {
        if (ts[cur] > 0) return ts[cur];

        // ts[cur] == 0 -> untouched ==> t must start from 1 instead of 0
        ts[cur] = t++;
        int min = Integer.MAX_VALUE;

        for (int next : graph.get(cur)) {
            if (next == prev) continue;
            int curMints = dfs(graph, next, cur, ts, res);
            min = Math.min(curMints, min);
        }

        if (ts[cur] <= min && prev != -1) res.add(Arrays.asList(prev, cur));
        return Math.min(min, ts[cur]);
    }
}
