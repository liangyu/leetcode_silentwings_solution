package LC2101_2400;
import java.util.*;
public class LC2322_MinimumScoreAfterRemovalsonaTree {
    /**
     * There is an undirected connected tree with n nodes labeled from 0 to n - 1 and n - 1 edges.
     *
     * You are given a 0-indexed integer array nums of length n where nums[i] represents the value of the ith node.
     * You are also given a 2D integer array edges of length n - 1 where edges[i] = [ai, bi] indicates that there is an
     * edge between nodes ai and bi in the tree.
     *
     * Remove two distinct edges of the tree to form three connected components. For a pair of removed edges, the
     * following steps are defined:
     *
     * Get the XOR of all the values of the nodes for each of the three components respectively.
     * The difference between the largest XOR value and the smallest XOR value is the score of the pair.
     * For example, say the three components have the node values: [4,5,7], [1,9], and [3,3,3]. The three XOR values
     * are 4 ^ 5 ^ 7 = 6, 1 ^ 9 = 8, and 3 ^ 3 ^ 3 = 3. The largest XOR value is 8 and the smallest XOR value is 3.
     * The score is then 8 - 3 = 5.
     * Return the minimum score of any possible pair of edge removals on the given tree.
     *
     * Input: nums = [1,5,5,4,11], edges = [[0,1],[1,2],[1,3],[3,4]]
     * Output: 9
     *
     * Input: nums = [5,5,2,4,4,2], edges = [[0,1],[1,2],[5,2],[4,3],[1,3]]
     * Output: 0
     *
     * Constraints:
     *
     * n == nums.length
     * 3 <= n <= 1000
     * 1 <= nums[i] <= 10^8
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= ai, bi < n
     * ai != bi
     * edges represents a valid tree.
     * @param nums
     * @param edges
     * @return
     */
    // time = O(n^2), space = O(n)
    List<Integer>[] graph;
    int[] nums;
    int n, res;
    public int minimumScore(int[] nums, int[][] edges) {
        this.nums = nums;
        n = nums.length;
        res = Integer.MAX_VALUE;
        graph = new List[n];

        for (int i = 0; i < n - 1; i++) {
            for (int k = 0; k < n; k++) graph[k] = new ArrayList<>();
            for (int j = 0; j < n - 1; j++) {
                if (i == j) continue;
                int a = edges[j][0], b = edges[j][1];
                graph[a].add(b);
                graph[b].add(a);
            }
            int x = edges[i][0], y = edges[i][1];
            int sumx = dfs(x, -1, -1, -1);
            int sumy = dfs(y, -1, -1, -1);
            dfs(x, -1, sumx, sumy);
            dfs(y, -1, sumy, sumx);
        }
        return res;
    }

    private int dfs(int u, int p, int sumx, int sumy) {
        int v = nums[u];
        for (int next : graph[u]) {
            if (next == p) continue;
            int t = dfs(next, u, sumx, sumy);
            v ^= t;
            if (sumx != -1) {
                int[] a = new int[]{sumy, t, sumx ^ t};
                Arrays.sort(a);
                res = Math.min(res, a[2] - a[0]);
            }
        }
        return v;
    }

    // S1.2
    class Solution {
        // time = O(n^2), space = O(n)
        final int N = 1010;
        int[] h, e, ne;
        int[] nums;
        int idx, res;
        public int minimumScore(int[] nums, int[][] edges) {
            this.nums = nums;
            h = new int[N];
            e = new int[N * 2];
            ne = new int[N * 2];
            res = Integer.MAX_VALUE;

            int m = edges.length;
            for (int i = 0; i < m; i++) {
                // init the graph
                Arrays.fill(h, -1);
                idx = 0;
                for (int j = 0; j < m; j++) {
                    if (i == j) continue;
                    int a = edges[j][0], b = edges[j][1];
                    add(a, b);
                    add(b, a);
                }
                int x = edges[i][0], y = edges[i][1];
                int sumx = dfs(x, -1, -1, -1);
                int sumy = dfs(y, -1, -1, -1);
                dfs(x, -1, sumx, sumy);
                dfs(y, -1, sumy, sumx);
            }
            return res;
        }

        private int dfs(int u, int p, int sumx, int sumy) {
            int v = nums[u];
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (j == p) continue;
                int t = dfs(j, u, sumx, sumy);
                v ^= t;
                if (sumx != -1) {
                    int[] a = new int[]{sumy, t, sumx ^ t};
                    Arrays.sort(a);
                    res = Math.min(res, a[2] - a[0]);
                }
            }
            return v;
        }

        private void add(int a, int b) {
            e[idx] = b;
            ne[idx] = h[a];
            h[a] = idx++;
        }
    }
}
