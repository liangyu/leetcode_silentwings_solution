package LC601_900;
import java.util.*;
public class LC684_RedundantConnection {
    /**
     * In this problem, a tree is an undirected graph that is connected and has no cycles.
     *
     * You are given a graph that started as a tree with n nodes labeled from 1 to n, with one additional edge added.
     * The added edge has two different vertices chosen from 1 to n, and was not an edge that already existed. The graph
     * is represented as an array edges of length n where edges[i] = [ai, bi] indicates that there is an edge between
     * nodes ai and bi in the graph.
     *
     * Return an edge that can be removed so that the resulting graph is a tree of n nodes. If there are multiple
     * answers, return the answer that occurs last in the input.
     *
     * Input: edges = [[1,2],[1,3],[2,3]]
     * Output: [2,3]
     *
     * Constraints:
     *
     * n == edges.length
     * 3 <= n <= 1000
     * edges[i].length == 2
     * 1 <= ai < bi <= edges.length
     * ai != bi
     * There are no repeated edges.
     * The given graph is connected.
     * @param edges
     * @return
     */
    // time = O(n), space = O(n)  n: number of vertices
    HashMap<Integer, Integer> parent = new HashMap<>();
    public int[] findRedundantConnection(int[][] edges) {
        // corner case
        if (edges == null || edges.length == 0 || edges[0] == null || edges[0].length == 0) return new int[0];


        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            if (!parent.containsKey(a)) parent.put(a, a);
            if (!parent.containsKey(b)) parent.put(b, b);

            // 在做union(a,b)之前一定要先做这个，保证a的parent是最早的老祖宗，把a, b各自路径压缩一下！！！
            if (findParent(a) == findParent(b)) return edge;
            else union(a, b);
        }
        return new int[0];
    }

    private int findParent(int x) {
        if (parent.get(x) != x) parent.put(x, findParent(parent.get(x)));
        return parent.get(x);
    }

    private void union(int x, int y) {
        x = parent.get(x);
        y = parent.get(y);
        if (x < y) parent.put(y, x);
        else parent.put(x, y);
    }
}
/**
 * Union Find模板题
 * 考察每条边，怎么判断放入后这条边变成环？放入前2端点已经是连通的了。
 * 如何快速判断两点是否连通？=> Union Find
 * 1-2
 *   |
 * 3-4
 */
