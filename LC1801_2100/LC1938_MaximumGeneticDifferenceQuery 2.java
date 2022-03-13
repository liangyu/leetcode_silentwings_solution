package LC1801_2100;
import java.util.*;
public class LC1938_MaximumGeneticDifferenceQuery {
    /**
     * There is a rooted tree consisting of n nodes numbered 0 to n - 1. Each node's number denotes its unique genetic
     * value (i.e. the genetic value of node x is x). The genetic difference between two genetic values is defined as
     * the bitwise-XOR of their values. You are given the integer array parents, where parents[i] is the parent for
     * node i. If node x is the root of the tree, then parents[x] == -1.
     *
     * You are also given the array queries where queries[i] = [nodei, vali]. For each query i, find the maximum genetic
     * difference between vali and pi, where pi is the genetic value of any node that is on the path between nodei and
     * the root (including nodei and the root). More formally, you want to maximize vali XOR pi.
     *
     * Return an array ans where ans[i] is the answer to the ith query.
     *
     * Input: parents = [-1,0,1,1], queries = [[0,2],[3,2],[2,5]]
     * Output: [2,3,7]
     *
     * Constraints:
     *
     * 2 <= parents.length <= 10^5
     * 0 <= parents[i] <= parents.length - 1 for every node i that is not the root.
     * parents[root] == -1
     * 1 <= queries.length <= 3 * 10^4
     * 0 <= nodei <= parents.length - 1
     * 0 <= vali <= 2 * 10^5
     * @param parents
     * @param queries
     * @return
     */
    // time = O(m + n), space = O(m + n)
    public int[] maxGeneticDifference(int[] parents, int[][] queries) {
        TrieNode root = new TrieNode();
        HashMap<Integer, List<int[]>> map = new HashMap<>(); // node -> {val, idx}

        for (int i = 0; i < queries.length; i++) {
            map.putIfAbsent(queries[i][0], new ArrayList<>());
            map.get(queries[i][0]).add(new int[]{queries[i][1], i});
        }

        int n = parents.length;
        int topNode = -1;
        HashMap<Integer, List<Integer>> children = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (parents[i] != -1) {
                children.putIfAbsent(parents[i], new ArrayList<>());
                children.get(parents[i]).add(i);
            } else topNode = i; // root
        }

        int[] res = new int[queries.length];
        dfs(topNode, map, children, root, res);
        return res;
    }

    private void dfs(int cur, HashMap<Integer, List<int[]>> map, HashMap<Integer, List<Integer>> children, TrieNode root, int[] res) {
        // globally use the same trie -> built trie
        TrieNode node = root;
        for (int i = 31; i >= 0; i--) {
            int d = ((cur >> i) & 1);
            if (node.next[d] == null) {
                node.next[d] = new TrieNode();
            }
            node = node.next[d];
            node.count++;
        }

        if (map.containsKey(cur)) {
            for (int[] q : map.get(cur)) {
                int val = q[0], idx = q[1], ans = 0;
                node = root;
                for (int i = 31; i >= 0; i--) {
                    int d = ((val >> i) & 1);
                    if (node.next[1 - d] == null || node.next[1 - d].count == 0) {
                        node = node.next[d];
                        ans = ans * 2 + d;
                    } else {
                        node = node.next[1 - d];
                        ans = ans * 2 + (1 - d);
                    }
                }
                res[idx] = ans ^ val;
            }
        }

        // dfs
        if (children.containsKey(cur)) {
            for (int child : children.get(cur)) {
                dfs(child, map, children, root, res);
            }
        }

        // backtracing
        node = root;
        for (int i = 31; i >= 0; i--) {
            int d = ((cur >> i) & 1);
            node = node.next[d];
            node.count--;
        }
    }

    private class TrieNode {
        private TrieNode[] next;
        private int count;
        public TrieNode() {
            this.next = new TrieNode[2];
            this.count = 0;
        }
    }
}
/**
 * ref: LC421
 * O(32n)来建trie,如何提高效率，避免对每个query都建树
 * 从上往下每多一个数就多一条支链
 * 可以用dfs，progressively built trie
 * 按照深度优先的顺序来建树 => 用hash来存query
 * dfs要记得回溯 => trie不仅要增加结点，还要能够减少结点 => ref: LC212
 * 每个结点增加一个属性，count，用来标记这个结点是否被删除了！！！
 * 总结下来要解决以下三个问题：
 * 1. how to pick a from an array so that maximize a^b
 * 2. DFS, solve queries when traversing the tree
 * 3. how to deal with backtracing when DFS for trie (i.e. delete nodes in Trie)
 */