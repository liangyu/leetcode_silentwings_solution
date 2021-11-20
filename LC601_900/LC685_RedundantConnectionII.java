package LC601_900;
import java.util.*;
public class LC685_RedundantConnectionII {
    /**
     * In this problem, a rooted tree is a directed graph such that, there is exactly one node (the root) for which all
     * other nodes are descendants of this node, plus every node has exactly one parent, except for the root node which
     * has no parents.
     *
     * The given input is a directed graph that started as a rooted tree with n nodes (with distinct values from 1 to n),
     * with one additional directed edge added. The added edge has two different vertices chosen from 1 to n, and was
     * not an edge that already existed.
     *
     * The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [ui, vi] that represents a
     * directed edge connecting nodes ui and vi, where ui is a parent of child vi.
     *
     * Return an edge that can be removed so that the resulting graph is a rooted tree of n nodes. If there are multiple
     * answers, return the answer that occurs last in the given 2D-array.
     *
     * Input: edges = [[1,2],[1,3],[2,3]]
     * Output: [2,3]
     *
     * Constraints:
     *
     * n == edges.length
     * 3 <= n <= 1000
     * edges[i].length == 2
     * 1 <= ui, vi <= n
     * ui != vi
     * @param edges
     * @return
     */
    // time = O(n), space = O(n)
    int[] parent;
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        parent = new int[n + 1];
        int[] cand1 = null, cand2 = null;
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1];
            if (parent[b] != 0) {
                cand1 = edge;
                cand2 = new int[]{parent[b], b};
                break;
            } else parent[b] = a;
        }

        for (int i = 1; i <= n; i++) parent[i] = i;

        // 先任意删一个，然后判断剩下的是否能成环 -> union find / dfs 搜索
        // tree -> n edges => n + 1 nodes now we have n edges with one fake edge -> we have n nodes
        for (int[] edge : edges) {
            if (edge == cand1) continue; // 相当于当做没有这条边存在，不参与union
            int a = edge[0], b = edge[1];
            if (findParent(a) != findParent(b)) parent[b] = a;
            else { // a, b之间本身已经连通，现在再加一条连通边就成环了
                // cand1, cand2可能都是空 -> 就是这条边造成了环，并且肯定连接到了root上，所有结点都有一个入度，连root都有1个root =>
                // 整张图就是一个指向root的环，环里任何一条边切断都可以
                if (cand2 == null) return edge;
                return cand2;
            }
        }
        return cand1; // cand1切掉之后相安无事 => 罪魁祸首就是cand1
    }

    private int findParent(int x) {
        if (x != parent[x]) parent[x] = findParent(parent[x]);
        return parent[x];
    }
}
/**
 * 与上一题的区别：无向 vs 有向
 *       1
 *   2 3  4 5
 *         6 7
 * => 找到一个结点有2个parent，找additional direct edge
 * 如果这条directional edge正好指向root，所有结点都为1，包括root
 * 0  1 -> 2
 */
