package LC2101_2400;
import java.util.*;
public class LC2242_MaximumScoreofaNodeSequence {
    /**
     * There is an undirected graph with n nodes, numbered from 0 to n - 1.
     *
     * You are given a 0-indexed integer array scores of length n where scores[i] denotes the score of node i. You are
     * also given a 2D integer array edges where edges[i] = [ai, bi] denotes that there exists an undirected edge
     * connecting nodes ai and bi.
     *
     * A node sequence is valid if it meets the following conditions:
     *
     * There is an edge connecting every pair of adjacent nodes in the sequence.
     * No node appears more than once in the sequence.
     * The score of a node sequence is defined as the sum of the scores of the nodes in the sequence.
     *
     * Return the maximum score of a valid node sequence with a length of 4. If no such sequence exists, return -1.
     *
     * Input: scores = [5,2,9,8,4], edges = [[0,1],[1,2],[2,3],[0,2],[1,3],[2,4]]
     * Output: 24
     *
     * Input: scores = [9,20,6,4,11,12], edges = [[0,3],[5,3],[2,4],[1,3]]
     * Output: -1
     *
     * Constraints:
     *
     * n == scores.length
     * 4 <= n <= 5 * 10^4
     * 1 <= scores[i] <= 10^8
     * 0 <= edges.length <= 5 * 10^4
     * edges[i].length == 2
     * 0 <= ai, bi <= n - 1
     * ai != bi
     * There are no duplicate edges.
     * @param scores
     * @param edges
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int maximumScore(int[] scores, int[][] edges) {
        int n = scores.length;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1];
            graph[a].add(b);
            graph[b].add(a);
        }

        for (int i = 0; i < n; i++) {
            Collections.sort(graph[i], (o1, o2) -> scores[o2] - scores[o1]);
        }

        int res = -1;
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1];
            int val1 = helper(graph[a], graph[b], a, b, scores);
            int val2 = helper(graph[b], graph[a], b, a, scores);
            res = Math.max(res, Math.max(val1, val2));
        }
        return res;
    }

    private int helper(List<Integer> nodes1, List<Integer> nodes2, int a, int b, int[] scores) {
        int m = nodes1.size(), n = nodes2.size();
        if (m < 2 || n < 2) return -1; // only contains itself, no children nodes are available!

        int i = 0, j = 0;
        // 找与 a 相邻的最大节点
        while (i < m && nodes1.get(i) == b) i++;
        if (i == m) return -1;

        // 找与 b 相邻的最大节点
        // 注意：这里要用equals，因为涉及常量池，超过-128~127的范围时，只能用equals!!!
        // You can't compare two Integer with a simple == they're objects so most of the time references won't be the same.
        while (j < n && (nodes2.get(j) == a || nodes2.get(j).equals(nodes1.get(i)))) j++; // can't be the same node as above
        if (j == n) return -1;
        int c = nodes1.get(i), d = nodes2.get(j);
        return scores[a] + scores[b] + scores[c] + scores[d];
    }
}
