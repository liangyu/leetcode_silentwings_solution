import java.util.*;
public class LC1857_LargestColorValueinaDirectedGraph {
    /**
     * There is a directed graph of n colored nodes and m edges. The nodes are numbered from 0 to n - 1.
     *
     * You are given a string colors where colors[i] is a lowercase English letter representing the color of the ith
     * node in this graph (0-indexed). You are also given a 2D array edges where edges[j] = [aj, bj] indicates that
     * there is a directed edge from node aj to node bj.
     *
     * A valid path in the graph is a sequence of nodes x1 -> x2 -> x3 -> ... -> xk such that there is a directed edge
     * from xi to xi+1 for every 1 <= i < k. The color value of the path is the number of nodes that are colored the
     * most frequently occurring color along that path.
     *
     * Return the largest color value of any valid path in the given graph, or -1 if the graph contains a cycle.
     *
     * Input: colors = "abaca", edges = [[0,1],[0,2],[2,3],[3,4]]
     * Output: 3
     *
     * Constraints:
     *
     * n == colors.length
     * m == edges.length
     * 1 <= n <= 10^5
     * 0 <= m <= 10^5
     * colors consists of lowercase English letters.
     * 0 <= aj, bj < n
     * @param colors
     * @param edges
     * @return
     */
    // S1: BFS
    // time = O(V + E), space = O(V + E)
    public int largestPathValue(String colors, int[][] edges) {
        int n = colors.length();
        List<List<Integer>> graph = buildGraph(n, edges);

        int[] indegree = new int[n];
        for (int[] edge : edges) {
            indegree[edge[1]]++;
        }

        HashSet<Character> set = new HashSet<>();
        for (char c : colors.toCharArray()) set.add(c);

        int res = 1;
        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (!set.contains(ch)) continue; // optimization -> not necessarily run 26 times all the time
            int ans = helper(graph, colors, indegree, n, ch - 'a'); // find the longest path that has char ch
            if (ans == -1) return -1;
            res = Math.max(res, ans);
        }
        return res;
    }

    private List<List<Integer>> buildGraph(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
        }
        return graph;
    }

    private int helper(List<List<Integer>> graph, String colors, int[] indegree, int n, int k) {
        int ans = 0;
        // count[i]: how many color k at most are there along the path from a start to i-th node
        // count[i] 表示的是针对当前的一种color "k"， 到达目前node i为止路径上存在的color "k"的最大值！
        int[] count = new int[n];
        int[] in = Arrays.copyOf(indegree, n);
        Queue<Integer> queue = new LinkedList<>();
        int nodes = 0;
        for (int i = 0; i < n; i++) {
            if (in[i] == 0) {
                queue.offer(i);
                nodes++;
                count[i] += (colors.charAt(i) - 'a' == k ? 1 : 0);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int p : graph.get(cur)) {
                count[p] = Math.max(count[p], count[cur] + (colors.charAt(p) - 'a' == k ? 1 : 0));
                ans = Math.max(ans, count[p]);
                in[p]--;
                if (in[p] == 0) {
                    queue.offer(p);
                    nodes++;
                }
            }
        }
        return nodes == n ? ans : -1;
    }
}
/**
 * 多线汇聚处的频次表是没法决定的，只能分情况讨论
 * 将原来的问题拆成"26个子问题" -> BFS拓扑遍历，O(26n)，n ~ 10^5 还是可以接受的
 * 如果只考虑color 2，希望找到一条路径，这路径上的color 2频次数最多 -> 可以确定继承那个频次表，比如继承于A
 * => 不管图多么复杂，整张图都可以这么处理，每次只看一个color，只要记录一个关于颜色2的频次,重复26次就可以了
 * 每次找起点是入度为0的点 -> bfs的起点
 */
