package LC1501_1800;
import java.util.*;
public class LC1782_CountPairsOfNodes {
    /**
     * You are given an undirected graph defined by an integer n, the number of nodes, and a 2D integer array edges, the
     * edges in the graph, where edges[i] = [ui, vi] indicates that there is an undirected edge between ui and vi. You
     * are also given an integer array queries.
     *
     * Let incident(a, b) be defined as the number of edges that are connected to either node a or b.
     *
     * The answer to the jth query is the number of pairs of nodes (a, b) that satisfy both of the following conditions:
     *
     * a < b
     * incident(a, b) > queries[j]
     * Return an array answers such that answers.length == queries.length and answers[j] is the answer of the jth query.
     *
     * Note that there can be multiple edges between the same two nodes.
     *
     * Input: n = 4, edges = [[1,2],[2,4],[1,3],[2,3],[2,1]], queries = [2,3]
     * Output: [6,5]
     *
     * Constraints:
     *
     * 2 <= n <= 2 * 10^4
     * 1 <= edges.length <= 10^5
     * 1 <= ui, vi <= n
     * ui != vi
     * 1 <= queries.length <= 20
     * 0 <= queries[j] < edges.length
     * @param n
     * @param edges
     * @param queries
     * @return
     */
    // time = O(nlogn + m * n), space = O(n)
    public int[] countPairs(int n, int[][] edges, int[] queries) {
        int[] count = new int[n];
        HashMap<Long, Integer> map = new HashMap<>();
        long m = 20005;
        for (int[] edge : edges) {
            int a = Math.min(edge[0] - 1, edge[1] - 1);
            int b = Math.max(edge[0] - 1, edge[1] - 1);
            count[a]++;
            count[b]++;
            long key = a * m + b;
            map.put(key, map.getOrDefault(key, 0) + 1);
        }
        int[] count2 = count.clone();
        Arrays.sort(count2);

        int[] res = new int[queries.length];
        int idx = 0;
        for (int x : queries) {
            int j = n - 1, sum = 0;
            for (int i = 0; i < n; i++) {
                if (i >= j) sum += n - i - 1;
                else {
                    while (i < j && count2[i] + count2[j] > x) j--;
                    sum += n - j - 1;
                }
            }

            // deal with offset -> check edge
            for (long key : map.keySet()) {
                int a = (int)(key / m);
                int b = (int)(key % m);
                int cnt = map.get(key);
                if (count[a] + count[b] > x && count[a] + count[b] - cnt <= x) sum--;
            }
            res[idx++] = sum;
        }
        return res;
    }
}
/**
 * {a,b}
 * query = x
 * count[a] + count[b] - num[a][b] > x
 * a,b之间的边数可能有若干条
 * 目前只考虑单向边 a -> b
 * 开不了二维数组num，开不了邻接矩阵
 * 真正边的数目只有10^5，没有那么多 -> 不需要存任意两点之间的边 -> use HashMap，做编码
 * count[a] + count[b] - num[a -> b] > x
 * 正向思考，暴力枚举 O(10^8)
 * 这道题的难点，num[a->b]
 * count[a] + count[b] > x  -> O(nlogn) 双指针，排序
 * offset => {a, b} s.t. count[a] + count[b] > x && count[a] + count[b] - num[a->b] <= x
 * 有多少个pair，它们的和大于x
 * count: x x x x x x x x
 *        i  [j         ]
 *        += n - i - 1
 * count[a] + count[b] - num[a->b] <= x
 * count0 - offset  => a 和 b一定是相连的，num[a->b]才是正值
 * 相连的点集 10^5 -> 看边
 * Q * (VlogV + E)
 * 20 * (1e5 + 1e5) => 1e6
 * idx = a * m + b  (m = 10^4)
 * a = idx / m, b = idx % m
 */
