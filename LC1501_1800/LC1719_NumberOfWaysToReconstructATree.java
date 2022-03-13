package LC1501_1800;
import java.util.*;
public class LC1719_NumberOfWaysToReconstructATree {
    /**
     * You are given an array pairs, where pairs[i] = [xi, yi], and:
     *
     * There are no duplicates.
     * xi < yi
     * Let ways be the number of rooted trees that satisfy the following conditions:
     *
     * The tree consists of nodes whose values appeared in pairs.
     * A pair [xi, yi] exists in pairs if and only if xi is an ancestor of yi or yi is an ancestor of xi.
     * Note: the tree does not have to be a binary tree.
     * Two ways are considered to be different if there is at least one node that has different parents in both ways.
     *
     * Return:
     *
     * 0 if ways == 0
     * 1 if ways == 1
     * 2 if ways > 1
     * A rooted tree is a tree that has a single root node, and all edges are oriented to be outgoing from the root.
     *
     * An ancestor of a node is any node on the path from the root to that node (excluding the node itself). The root
     * has no ancestors.
     *
     * Input: pairs = [[1,2],[2,3]]
     * Output: 1
     *
     * Input: pairs = [[1,2],[2,3],[1,3]]
     * Output: 2
     *
     * Input: pairs = [[1,2],[2,3],[2,4],[1,5]]
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= pairs.length <= 10^5
     * 1 <= xi < yi <= 500
     * The elements in pairs are unique.
     * @param pairs
     * @return
     */
    // time = O(m + n^2), space = O(m)  n 为树中节点的数目，m 表示数组 pairs 的长度
    List<Integer>[] relative;
    boolean[][] isRelative;
    HashSet<Integer> nodeSet;
    List<Integer>[] children;
    HashSet<Integer> visited;
    int flag = 1;
    public int checkWays(int[][] pairs) {
        relative = new List[501];
        isRelative = new boolean[501][501];
        children = new List[501];
        nodeSet = new HashSet<>();
        visited = new HashSet<>();
        for (int i = 0; i < 501; i++) relative[i] = new ArrayList<>();
        for (int i = 0; i < 501; i++) children[i] = new ArrayList<>();
        for (int[] pair : pairs) {
            nodeSet.add(pair[0]);
            nodeSet.add(pair[1]);
            relative[pair[0]].add(pair[1]);
            relative[pair[1]].add(pair[0]);
            isRelative[pair[0]][pair[1]] = true;
            isRelative[pair[1]][pair[0]] = true;
        }

        List<Integer> nodes = new ArrayList<>(nodeSet);
        Collections.sort(nodes, (o1, o2) -> relative[o1].size() - relative[o2].size());

        // find parent node for each node
        int root = -1;
        for (int i = 0; i < nodes.size(); i++) {
            // find the father of nodes[i]
            int j = i + 1;
            while (j < nodes.size() && !isRelative[nodes.get(i)][nodes.get(j)]) j++;
            if (j < nodes.size()) {
                children[nodes.get(j)].add(nodes.get(i));
                if (relative[nodes.get(i)].size() == relative[nodes.get(j)].size()) flag = 2;
            } else {
                if (root == -1) root = nodes.get(i);
                else return 0;
            }
        }

        dfs(root, 0);
        return flag;
    }

    private int dfs(int cur, int depth) {
        if (flag == 0) return -1;
        if (visited.contains(cur)) {  // find cycle
            flag = 0;
            return -1;
        }

        visited.add(cur);
        int sum = 0;
        for (int child : children[cur]) {
            sum += dfs(child, depth + 1);
        }
        if (sum + depth != relative[cur].size()) {
            flag = 0;
            return -1;
        }
        return sum + 1;
    }

    // S2:
    class Solution {
        List<Integer>[] relatives;
        boolean[][] isRelative;
        HashSet<Integer> set;
        int flag = 1;
        public int checkWays(int[][] pairs) {
            relatives = new List[501];
            isRelative = new boolean[501][501];
            set = new HashSet<>();

            for (int i = 0; i < 501; i++) relatives[i] = new ArrayList<>();

            for (int[] x : pairs) {
                int a = x[0], b = x[1];
                relatives[a].add(b);
                relatives[b].add(a);
                isRelative[a][b] = true;
                isRelative[b][a] = true;
                set.add(a);
                set.add(b);
            }

            List<Integer> nodes = new ArrayList<>(set);
            Collections.sort(nodes, (o1, o2) -> relatives[o1].size() - relatives[o2].size());

            int n = nodes.size(), root = -1;
            for (int i = 0; i < n; i++) {
                int j = i + 1;
                while (j < n && !isRelative[nodes.get(i)][nodes.get(j)]) j++;
                if (j < n) {
                    for (int r : relatives[nodes.get(i)]) {
                        if (r != nodes.get(j) && !isRelative[r][nodes.get(j)]) return 0;
                    }
                    if (relatives[nodes.get(i)].size() == relatives[nodes.get(j)].size()) flag = 2;
                } else {
                    if (root == -1) root = nodes.get(i);
                    else return 0;
                }
            }
            return flag;
        }
    }
}
/**
 * 任意两点如果是一对pair（即是在一条直系链上），它们必须出现在pairs里面。
 * relative[node]: {....}  node的直系亲属
 * degree[node]: int   直系亲属有几个
 * 单链 -> 所有结点的degree都相同
 * 如果有分叉，多了2个直系亲属的结点degree会更大
 * relative[node]
 * x x x x x   O x x x x
 *        k-1  k
 * 从前往后找第一个>=k的结点一定是它的父节点
 * 在只有一条单链的时候，是可以任意指定的，但其他情况，不同node的直系亲属链，会有冲突
 *     a
 *     b
 *    c  e
 *   d    f
 * r[c]: dgba
 * r[e]: fba
 * r = fedgcba  -> 在c后面找第一个>= c的degree并和c是直系亲属关系的node
 * 没有父节点的node多于1个的话，无法构成一棵树
 */