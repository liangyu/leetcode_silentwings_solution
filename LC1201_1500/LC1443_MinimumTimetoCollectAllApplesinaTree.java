package LC1201_1500;
import java.util.*;
public class LC1443_MinimumTimetoCollectAllApplesinaTree {
    /**
     * Given an undirected tree consisting of n vertices numbered from 0 to n-1, which has some apples in their vertices.
     * You spend 1 second to walk over one edge of the tree. Return the minimum time in seconds you have to spend to
     * collect all apples in the tree, starting at vertex 0 and coming back to this vertex.
     *
     * The edges of the undirected tree are given in the array edges, where edges[i] = [ai, bi] means that exists an
     * edge connecting the vertices ai and bi. Additionally, there is a boolean array hasApple, where hasApple[i] = true
     * means that vertex i has an apple; otherwise, it does not have any apple.
     *
     * Input: n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,true,true,false]
     * Output: 8
     *
     * Input: n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,false,true,false]
     * Output: 6
     *
     * Input: n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,false,false,false,false,false]
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= ai < bi <= n - 1
     * fromi < toi
     * hasApple.length == n
     * @param n
     * @param edges
     * @param hasApple
     * @return
     */
    // time = O(n), space = O(n)
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] x : edges) {
            int a = x[0], b = x[1];
            graph[a].add(b);
            graph[b].add(a);
        }
        boolean[] visited = new boolean[n];
        visited[0] = true;
        return dfs(graph, 0, hasApple, visited);
    }

    private int dfs(List<Integer>[] graph, int cur, List<Boolean> hasApple, boolean[] visited) {
        int res = 0;
        for (int next : graph[cur]) {
            if (visited[next]) continue;
            visited[next] = true;
            res += dfs(graph, next, hasApple, visited);
        }
        if ((res > 0 || hasApple.get(cur)) && cur != 0) res += 2;
        return res;
    }
}
