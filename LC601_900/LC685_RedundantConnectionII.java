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
    HashMap<Integer, Integer> father = new HashMap<>();
    public int[] findRedundantDirectedConnection(int[][] edges) {
        // corner case
        if (edges == null || edges.length == 0 || edges[0] == null || edges[0].length == 0) return new int[0];

        int[] cand1 = null, cand2 = null;
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1];
            if (father.containsKey(b)) {
                cand1 = edge;
                cand2 = new int[]{father.get(b), b};
                break;
            } else {
                father.put(b, a);
            }
        }

        // 先任意删一个，然后判断剩下的是否能成环 -> union find / dfs 搜索
        // tree -> n edges => n + 1 nodes now we have n edges with one fake edge -> we have n nodes
        int n = edges.length;
        for (int i = 1; i <= n; i++) {
            father.put(i, i);
        }
        for (int[] edge : edges) {
            if (edge == cand1) continue; // 相当于当做没有这条边存在，不参与union
            int a = edge[0];
            int b = edge[1];
            if (findFather(a) != findFather(b)) {
                father.put(b, a);
            } else { // a, b之间本身已经连通，现在再加一条连通边就成环了
                // cand1, cand2可能都是空 -> 就是这条边造成了环，并且肯定连接到了root上，所有结点都有一个入度，连root都有1个root =>
                // 环里任何一条边切断都可以
                if (cand2 == null) return edge;
                else return cand2; // 跳过之后依然有环 —> cand2是罪魁祸首！
            }
        }
        return cand1; // cand1切掉之后相安无事 => 罪魁祸首就是cand1
    }

    private int findFather(int x) {
        if (father.get(x) != x) father.put(x, findFather(father.get(x)));
        return father.get(x);
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
