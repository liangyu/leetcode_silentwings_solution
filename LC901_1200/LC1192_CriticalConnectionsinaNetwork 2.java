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
    // S1: DFS
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

    // S2: Tarjan
    // time = O(V + E), space = O(E)
    public List<List<Integer>> criticalConnections2(int n, List<List<Integer>> connections) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (List<Integer> c : connections) {
            int a = c.get(0), b = c.get(1);
            graph[a].add(b);
            graph[b].add(a);
        }

        // init Tarjan
        boolean[] visited = new boolean[n];
        int[] dfn = new int[n];
        int[] low = new int[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        // dfs
        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            dfs(graph, i, visited, dfn, low, parent, res);
        }
        return res;
    }

    private int time = 0;
    private void dfs(List<Integer>[] graph, int cur, boolean[] visited, int[] dfn, int[] low, int[] parent, List<List<Integer>> res) {
        visited[cur] = true;
        dfn[cur] = low[cur] = time++;

        for (int next : graph[cur]) {
            if (next == parent[cur]) continue;
            if (!visited[next]) {
                parent[next] = cur;
                dfs(graph, next, visited, dfn, low, parent, res);
                if (low[next] > dfn[cur]) {
                    res.add(Arrays.asList(cur, next));
                }
            }
            low[cur] = Math.min(low[cur], low[next]);
        }
    }
}
/**
 * 本题本质就是图论中的经典问题，如何求一个无向图中的割边（critical edge）或者叫做桥（bridge）。
 * 网上的教程中，我觉得这一篇是最容易理解的：https://www.cnblogs.com/nullzx/p/7968110.html
 * 简单地说，我们可以以任意一个未访问过的节点作为根节点，用DFS的顺序来进行搜索，即永远深度优先，然后回溯再搜索其他分支。
 * 如果碰到访问过的节点，就停止，保证不行成环。
 * 我们在dfs的过程中维护两个数组，一个是dfs[u]，表示节点u被第一次访问时的顺序（可以理解为时间戳），这个是唯一且不变的量。
 * 另一个数组low[u]比较关键，初始的时候low[u]=dfn[u]。
 * 我们以u为节点的开始dfs（注意抵达u之前可能还有u的父节点，但我们dfs的时候不走回头路），想象它最终形成一棵搜索树，
 * 那么u的所有子节点中止的条件不外乎有两个：一个是走进了死胡同；
 * 另一个就是遇到了已经访问过的节点，特别的，这个已经访问过的节点有可能是u的祖先节点！
 * 所以，有了这样的搜索树之后，low[u]可以有机会更新为它所有的子节点v可以接触到的最小时间戳low[v]。
 * 令v是u的一个子节点，且有low[v]>dfn[u]，这说明什么呢？说明从v出发最终无法绕道u的前面去。因此(v,u)就是割边。
 * 如果消除了这条边，v及其子树就是一个孤岛，无法与u或u的祖先相通。
 * 同理，如果low[v]>=dfn[u]，说明u是一个割点，如果消除了这个点，那么v及其子树也是一个孤岛。
 * 本题中我们还设置了一个parent，其实是为了标记dfs过程中的搜索顺序。
 * 因为无向图for auto v: next[u]的遍历过程中，v可能是u的父节点，
 * 这种情况下v其实不能作为从u开始dfs的下一个目的地（否则就是走回头路了），所以得排除。
 */
