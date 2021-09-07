package LC1501_1800;
import java.util.*;
public class LC1627_GraphConnectivityWithThreshold {
    /**
     * We have n cities labeled from 1 to n. Two different cities with labels x and y are directly connected by a
     * bidirectional road if and only if x and y share a common divisor strictly greater than some threshold.
     * More formally, cities with labels x and y have a road between them if there exists an integer z such that
     * all of the following are true:
     *
     * x % z == 0,
     * y % z == 0, and
     * z > threshold.
     * Given the two integers, n and threshold, and an array of queries, you must determine for each
     * queries[i] = [ai, bi] if cities ai and bi are connected directly or indirectly. (i.e. there is some path between them).
     *
     * Return an array answer, where answer.length == queries.length and answer[i] is true if for the ith query,
     * there is a path between ai and bi, or answer[i] is false if there is no path.
     *
     * Input: n = 6, threshold = 2, queries = [[1,4],[2,5],[3,6]]
     * Output: [false,false,true]
     *
     * Constraints:
     *
     * 2 <= n <= 10^4
     * 0 <= threshold <= n
     * 1 <= queries.length <= 10^5
     * queries[i].length == 2
     * 1 <= ai, bi <= cities
     * ai != bi
     * @param n
     * @param threshold
     * @param queries
     * @return
     */
    // time = O(nlogn), space = O(n)
    private int[] parent;
    public List<Boolean> areConnected(int n, int threshold, int[][] queries) {
        List<Boolean> res = new ArrayList<>();

        parent = new int[n + 1];
        for (int i = 0; i < parent.length; i++) parent[i] = i;

        boolean[] visited = new boolean[n + 1];
        for (int k = threshold + 1; k <= n; k++) {
            if (visited[k]) continue;
            for (int x = 2 * k; x <= n; x += k) {
                visited[x] = true;
                if (findParent(x) != findParent(k)) {
                    union(x, k);
                }
            }
        }

        for (int[] query : queries) {
            res.add(findParent(query[0]) == findParent(query[1]));
        }
        return res;
    }

    private int findParent(int x) {
        if (x != parent[x]) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }
}
/**
 * 连通性 => union find
 * 找出哪些城市要把它并起来
 * 根据条件，比较显然的办法，遍历2个城市，枚举它们，看它们是否有大于threshold的公约数 => O(n^2 * logM) => > 10^8 LTE
 * => 枚举公约数，然后推哪些城市可以连接
 * for (int k = threshold + 1; k <= n; k++) {  // O(n)
 *      for (int x = 2 * k; x <= n; x += k) {  // O(n/k)  n/1+n/2+n/3+...+n/n = n*(1+1/2+1/3+...1/n) => O(logn)
 *          union(x, k)
 *      }
 * }
 * => O(nlogn)
 * union => O(1) => O(c(n))
 * k = 4
 *     4 8 16 ...
 * 艾拉托斯特尼筛选法 Eratosthenes => O(NloglogN)
 */
