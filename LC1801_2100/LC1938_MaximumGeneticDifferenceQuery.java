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
    TrieNode root;
    HashMap<Integer, List<int[]>> map; // node -> {val, idx}
    List<Integer>[] children;
    int[] res;
    public int[] maxGeneticDifference(int[] parents, int[][] queries) {
        root = new TrieNode();
        map = new HashMap<>();

        int n = parents.length;
        int topNode = -1;
        children = new List[n];
        for (int i = 0; i < n; i++) children[i] = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (parents[i] == -1) topNode = i;
            else children[parents[i]].add(i);
        }

        for (int i = 0; i < queries.length; i++) {
            int node = queries[i][0], val = queries[i][1];
            map.putIfAbsent(node, new ArrayList<>());
            map.get(node).add(new int[]{val, i}); // each node may have multiple queries
        }

        res = new int[queries.length];
        dfs(topNode);
        return res;
    }

    private void dfs(int cur) {
        TrieNode node = root;
        // add cur into the trie
        for (int i = 31; i >= 0; i--) { // 从高到低
            int d = (cur >> i) & 1;
            if (node.next[d] == null) {
                node.next[d] = new TrieNode();
            }
            node = node.next[d];
            node.count++;
        }

        // deal with the queries related to cur node
        // dfs到这一步，很可能没有关于cur这个node的query，直接下去dfs它的children
        for (int[] x : map.getOrDefault(cur, new ArrayList<>())) {
            int val = x[0], idx = x[1];
            node = root; // 这里注意：node与ans都是在for loop里初始化的，针对每一个Pair x做初始化！！！
            int ans = 0;
            for (int i = 31; i >= 0; i--) {
                int d = (val >> i) & 1; // ideally go to branch 1-d
                // not existing or chopped in backtracking，注意千万不要漏掉count == 0的情况，也代表这条支路目前被回溯砍掉了！！！
                if (node.next[1 - d] == null || node.next[1 - d].count == 0) {
                    node = node.next[d];
                    ans = ans * 2 + d;
                } else {
                    node = node.next[1 - d];
                    ans = ans * 2 + 1 - d;
                }
            }
            res[idx] = ans ^ val;
        }

        for (int child : children[cur]) {
            dfs(child);
        }

        // backtracking -> traverse again
        node = root;
        for (int i = 31; i >= 0; i--) { // 从高到低
            int d = (cur >> i) & 1;
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
 * reorder the query!!! 0 -> 1 -> 3 -> ...
 * 按照深度优先的顺序来建树 => 用hash来存query => time = O(n) 有多少条支链
 * dfs要记得回溯 => trie不仅要增加结点，还要能够减少结点 => ref: LC212 word search II
 * 每个结点增加一个属性，count，用来标记这个结点是否被删除了！！！
 * 总结下来要解决以下三个问题：
 * 1. how to pick a from an array so that maximize a^b
 * 2. DFS, solve queries when traversing the tree
 * 3. how to deal with backtracing when DFS for trie (i.e. delete nodes in Trie)
 */