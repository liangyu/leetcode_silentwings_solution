package LC1501_1800;
import java.util.*;
public class LC1548_TheMostSimilarPathinaGraph {
    /**
     * We have n cities and m bi-directional roads where roads[i] = [ai, bi] connects city ai with city bi. Each city
     * has a name consisting of exactly three upper-case English letters given in the string array names. Starting at
     * any city x, you can reach any city y where y != x (i.e., the cities and the roads are forming an undirected
     * connected graph).
     *
     * You will be given a string array targetPath. You should find a path in the graph of the same length and with the
     * minimum edit distance to targetPath.
     *
     * You need to return the order of the nodes in the path with the minimum edit distance. The path should be of the
     * same length of targetPath and should be valid (i.e., there should be a direct road between ans[i] and ans[i + 1]).
     * If there are multiple answers return any one of them.
     *
     * The edit distance is defined as follows:
     *
     * Input: n = 5, roads = [[0,2],[0,3],[1,2],[1,3],[1,4],[2,4]], names = ["ATL","PEK","LAX","DXB","HND"],
     * targetPath = ["ATL","DXB","HND","LAX"]
     * Output: [0,2,4,2]
     *
     * Constraints:
     *
     * 2 <= n <= 100
     * m == roads.length
     * n - 1 <= m <= (n * (n - 1) / 2)
     * 0 <= ai, bi <= n - 1
     * ai != bi
     * The graph is guaranteed to be connected and each pair of nodes may have at most one direct road.
     * names.length == n
     * names[i].length == 3
     * names[i] consists of upper-case English letters.
     * There can be two cities with the same name.
     * 1 <= targetPath.length <= 100
     * targetPath[i].length == 3
     * targetPath[i] consists of upper-case English letters.
     *
     *
     * Follow up: If each node can be visited only once in the path, What should you change in your solution?
     * @param n
     * @param roads
     * @param names
     * @param targetPath
     * @return
     */
    // S1: dp
    // time = O(m * n^2), space = O(m * n)
    public List<Integer> mostSimilar(int n, int[][] roads, String[] names, String[] targetPath) {
        int m = targetPath.length;
        int[] dp = new int[n];
        int[][] prev = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(prev[i], -1);

        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] r : roads) {
            int a = r[0], b = r[1];
            graph[a].add(b);
            graph[b].add(a);
        }

        for (int t = 0; t < m; t++) {
            int[] dp_temp = dp.clone();
            for (int i = 0; i < n; i++) {
                dp[i] = Integer.MAX_VALUE / 2;
                for (int j : graph[i]) {
                    if (dp[i] > dp_temp[j] + (names[i].equals(targetPath[t]) ? 0 : 1)) {
                        dp[i] = dp_temp[j] + (names[i].equals(targetPath[t]) ? 0 : 1);
                        prev[t][i] = j;
                    }
                }
            }
        }

        // find start point
        int dist = Integer.MAX_VALUE / 2;
        int start = -1;
        for (int i = 0; i < n; i++) {
            if (dist > dp[i]) {
                dist = dp[i];
                start = i;
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int t = m - 1; t >= 0; t--) {
            res.add(start);
            start = prev[t][start];
        }
        Collections.reverse(res); // 之前是从后往前存的，所以这里要逆序！
        return res;
    }

    // S2: dp (original)
    // time = O(m * n^2), space = O(m * n)
    public List<Integer> mostSimilar2(int n, int[][] roads, String[] names, String[] targetPath) {
        int m = targetPath.length;
        int[][] dp = new int[2][n];
        // for (int i = 0; i < m; i++) Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        int[][] prev = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(prev[i], -1);

        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] x : roads) {
            int a = x[0], b = x[1];
            graph[a].add(b);
            graph[b].add(a);
        }

        for (int t = 0; t < m; t++) {
            for (int i = 0; i < n; i++) {
                dp[t & 1][i] = Integer.MAX_VALUE / 2;
                for (int j : graph[i]) {
                    if (dp[t & 1][i] > (t == 0 ? 0 : dp[t - 1 & 1][j]) + (names[i].equals(targetPath[t]) ? 0 : 1)) {
                        dp[t & 1][i] = (t == 0 ? 0 : dp[t - 1 & 1][j]) + (names[i].equals(targetPath[t]) ? 0 : 1);
                        prev[t][i] = j;
                    }
                }
            }
        }

        // find starting point
        int dist = Integer.MAX_VALUE / 2, start = -1;
        for (int i = 0; i < n; i++) {
            if (dist > dp[m - 1 & 1][i]) {
                dist = dp[m - 1 & 1][i];
                start = i;
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int t = m - 1; t >= 0; t--) {
            res.add(start);
            start = prev[t][start];
        }
        Collections.reverse(res);
        return res;
    }
}
/**
 * dp[t][i]: 路径的第t个位置设置为第i个城市，所能得到的最小edit distance
 * dp[t][i] = min{dp[t-1][j] + (city[i] != targetPath[t])} for all j -> i
 * return min{dp[T-1][?]}
 * 打印出路径，因此需要额外记录prev[t][i]表示dp[t][i]的前驱状态对应的城市编号。
 * 然后从dp[T-1][?]最小值所对应的那个城市作为终点，根据Prev的信息往前倒推出整条path。
 */
