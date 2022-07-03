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
}
