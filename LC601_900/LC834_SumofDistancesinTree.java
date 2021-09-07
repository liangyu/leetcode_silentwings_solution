package LC601_900;
import java.util.*;
public class LC834_SumofDistancesinTree {
    /**
     * There is an undirected connected tree with n nodes labeled from 0 to n - 1 and n - 1 edges.
     *
     * You are given the integer n and the array edges where edges[i] = [ai, bi] indicates that there is an edge
     * between nodes ai and bi in the tree.
     *
     * Return an array answer of length n where answer[i] is the sum of the distances between the ith node in the tree
     * and all other nodes.
     *
     * Input: n = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
     * Output: [8,12,6,10,10,10]
     *
     * Constraints:
     *
     * 1 <= n <= 3 * 10^4
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= ai, bi < n
     * ai != bi
     * The given input represents a valid tree.
     * @param n
     * @param edges
     * @return
     */
    // S1: ArrayList + dp
    // time = O(n), space = O(n)
    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        int[] dp = new int[n];
        int[] sz = new int[n];
        int[] res = new int[n];

        // build graph
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        dfs(dp, sz, graph, 0, -1); // 撸一遍统计获得了在 u 为根的树中，每个节点为根的子树的答案 dp
        dfs2(dp, sz, graph, 0, -1, res); // 换根，在以u为根节点得到的值的基础上作更改即可获得新的根所产生的值
        return res;
    }

    private void dfs(int[] dp, int[] sz, List<List<Integer>> graph, int cur, int parent) {
        sz[cur] = 1;
        dp[cur] = 0;

        for (int next : graph.get(cur)) {
            if (next == parent) continue;
            dfs(dp, sz, graph, next, cur);
            dp[cur] += dp[next] + sz[next];
            sz[cur] += sz[next];
        }
    }

    private void dfs2(int[] dp, int[] sz, List<List<Integer>> graph, int cur, int parent, int[] res) {
        res[cur] = dp[cur];

        for (int next : graph.get(cur)) {
            if (next == parent) continue;
            int pu = dp[cur], pv = dp[next];
            int su = sz[cur], sv = sz[next];

            dp[cur] -= dp[next] + sz[next];
            sz[cur] -= sz[next];
            dp[next] += dp[cur] + sz[cur];
            sz[next] += sz[cur];

            dfs2(dp, sz, graph, next, cur, res);

            // setback
            dp[cur] = pu;
            dp[next] = pv;
            sz[cur] = su;
            sz[next] = sv;
        }
    }

    // S1.2: HashMap + dp
    // time = O(n), space = O(n)
    public int[] sumOfDistancesInTree2(int n, int[][] edges) {
        int[] dp = new int[n];
        int[] sz = new int[n];
        int[] res = new int[n];

        // build graph
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            map.putIfAbsent(edge[0], new ArrayList<>());
            map.putIfAbsent(edge[1], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        dfs(dp, sz, map, 0, -1);
        dfs2(dp, sz, map, 0, -1, res);
        return res;
    }

    private void dfs(int[] dp, int[] sz, HashMap<Integer, List<Integer>> map, int cur, int parent) {
        dp[cur] = 0;
        sz[cur] = 1;

        if (map.containsKey(cur)) {
            for (int next : map.get(cur)) {
                if (next == parent) continue;
                dfs(dp, sz, map, next, cur);
                dp[cur] += dp[next] + sz[next];
                sz[cur] += sz[next];
            }
        }
    }

    private void dfs2(int[] dp, int[] sz, HashMap<Integer, List<Integer>> map, int cur, int parent, int[] res) {
        res[cur] = dp[cur];

        if (map.containsKey(cur)) {
            for (int next : map.get(cur)) {
                if (next == parent) continue;

                int pu = dp[cur], su = sz[cur];
                int pv = dp[next], sv = sz[next];

                dp[cur] -= dp[next] + sz[next];
                sz[cur] -= sz[next];
                dp[next] += dp[cur] + sz[cur];
                sz[next] += sz[cur];

                dfs2(dp, sz, map, next, cur, res);

                // setback
                dp[cur] = pu;
                sz[cur] = su;
                dp[next] = pv;
                sz[next] = sv;
            }
        }
    }
}
/**
 * 树型dp
 * dp[u] 表示以 u 为根的子树，它的所有子节点到它的距离之和
 * sz[u] 表示以 u 为根的子树的节点数量
 */
