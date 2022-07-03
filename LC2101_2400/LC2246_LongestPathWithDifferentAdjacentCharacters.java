package LC2101_2400;
import java.util.*;
public class LC2246_LongestPathWithDifferentAdjacentCharacters {
    /**
     * You are given a tree (i.e. a connected, undirected graph that has no cycles) rooted at node 0 consisting of n
     * nodes numbered from 0 to n - 1. The tree is represented by a 0-indexed array parent of size n, where parent[i] is
     * the parent of node i. Since node 0 is the root, parent[0] == -1.
     *
     * You are also given a string s of length n, where s[i] is the character assigned to node i.
     *
     * Return the length of the longest path in the tree such that no pair of adjacent nodes on the path have the same
     * character assigned to them.
     *
     * Input: parent = [-1,0,0,1,1,2], s = "abacbe"
     * Output: 3
     *
     * Input: parent = [-1,0,0,0], s = "aabc"
     * Output: 3
     *
     * Constraints:
     *
     * n == parent.length == s.length
     * 1 <= n <= 10^5
     * 0 <= parent[i] <= n - 1 for all i >= 1
     * parent[0] == -1
     * parent represents a valid tree.
     * s consists of only lowercase English letters.
     * @param parent
     * @param s
     * @return
     */
    // time = O(n),s pace = O(n)
    int res = 0;
    public int longestPath(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int i = 1; i < n; i++) graph[parent[i]].add(i);

        dfs(graph, 0, s);
        return res + 1; // 求的是结点个数，edge + 1
    }


    private int dfs(List<Integer>[] graph, int cur, String s) {
       int max = 0;
       for (int next : graph[cur]) {
           int len = dfs(graph, next, s) + 1; // x -> y => + 1
           if (s.charAt(next) != s.charAt(cur)) {
               res = Math.max(res, max + len);
               max = Math.max(max, len);
           }
       }
       return max;
    }

    // S2: dfs
    // time = O(n), space = O(n)
    class Solution {
        List<Integer>[] children;
        int[] len;
        int res = 1; // at least 1
        String s;
        public int longestPath(int[] parent, String s) {
            this.s = s;
            int n = parent.length;
            children = new List[n];
            len = new int[n];
            for (int i = 0; i < n; i++) children[i] = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (parent[i] != -1) children[parent[i]].add(i);
            }

            dfs(0);
            return res;
        }

        private void dfs(int node) {
            if (children[node].size() == 0) {
                len[node] = 1;
                return;
            }

            List<Integer> pool = new ArrayList<>();
            len[node] = 1;
            for (int child : children[node]) {
                dfs(child); // len[child] 一定就有了！！！
                if (s.charAt(child) != s.charAt(node)) {
                    pool.add(len[child]);
                    len[node] = Math.max(len[node], len[child] + 1);
                }
            }
            Collections.sort(pool, (o1, o2) -> o2 - o1);
            if (pool.size() >= 2) {
                res = Math.max(res, pool.get(0) + pool.get(1) + 1);
            } else if (pool.size() == 1) {
                res = Math.max(res, pool.get(0) + 1);
            } else res = Math.max(res, 1);
        }
    }
}
/**
 * 有根树
 * 任何路径都必然有一个拐点，我们遍历所有的节点，考虑对每个节点作为拐点时的最优路径，这样我们就可以做到不遗漏地求出全局的最优路径。
 * 以Node为拐点的最长路径，必然由两条以它孩子节点为起点的最长单链路径（即一直向下没有拐弯）拼接组成。
 * 本题的核心就转化为，求对于每个节点，从它往下走能够找到的最长单链路径h
 * 遍历所有的拐点，找到每个拐点的最优路径。在全局中找最优解。
 * len(node): the longest path starting from node toward leaves
 * len 有递归性质
 * len(node) = max{len(node -> child) + 1}  if (node is different from child)
 *           = 1 otherwise
 * for node:
 * global = max(global, len(node->child)_max1 + len(node->child)_max2 + 1)
 */