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
        int[] res = new int[queries.length];

        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        int rootVal = -1;
        for (int i = 0; i < parents.length; i++) { // O(m)
            if (parents[i] != -1) {
                graph.putIfAbsent(parents[i], new ArrayList<>());
                graph.get(parents[i]).add(i);
            } else rootVal = i;
        }

        TrieNode root = new TrieNode();
        HashMap<Integer, List<int[]>> map = new HashMap<>();
        for (int i = 0; i < queries.length; i++) { // O(n)
            int[] q = queries[i];
            int node = q[0], val = q[1];
            map.putIfAbsent(node, new ArrayList<>());
            map.get(node).add(new int[]{val, i});
        }

        dfs(root, graph, map, rootVal, res);
        return res;
    }

    private void dfs(TrieNode root, HashMap<Integer, List<Integer>> graph, HashMap<Integer, List<int[]>> map, int cur, int[] res) {
        TrieNode node = root;
        for (int k = 31; k >= 0; k--) {
            int d = ((cur >> k) & 1);
            if (node.next[d] == null) {
                node.next[d] = new TrieNode();
            }
            node = node.next[d];
            node.count++;
        }

        if (map.containsKey(cur)) {
            for (int[] child : map.get(cur)) {
                node = root;
                int val = child[0], idx = child[1], ans = 0;
                for (int k = 31; k >= 0; k--) {
                    int d = ((val >> k) & 1);
                    if (node.next[1 - d] == null || node.next[1 - d].count == 0) {
                        node = node.next[d];
                        ans = ans * 2 + 0;
                    } else {
                        node = node.next[1 - d];
                        ans = ans * 2 + 1;
                    }
                }
                res[idx] = ans;
            }
        }

        if (graph.containsKey(cur)) {
            for (int c : graph.get(cur)) {
                dfs(root, graph, map, c, res);
            }
        }

        // setback the count to start fresh in the following round
        node = root;
        for (int k = 31; k >= 0; k--) {
            node = node.next[(cur >> k) & 1];
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
