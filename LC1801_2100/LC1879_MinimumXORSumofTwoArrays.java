package LC1801_2100;
import java.util.*;
public class LC1879_MinimumXORSumofTwoArrays {
    /**
     * You are given two integer arrays nums1 and nums2 of length n.
     *
     * The XOR sum of the two integer arrays is (nums1[0] XOR nums2[0]) + (nums1[1] XOR nums2[1]) + ... + (nums1[n - 1]
     * XOR nums2[n - 1]) (0-indexed).
     *
     * For example, the XOR sum of [1,2,3] and [3,2,1] is equal to (1 XOR 3) + (2 XOR 2) + (3 XOR 1) = 2 + 0 + 2 = 4.
     * Rearrange the elements of nums2 such that the resulting XOR sum is minimized.
     *
     * Return the XOR sum after the rearrangement.
     *
     * Input: nums1 = [1,2], nums2 = [2,3]
     * Output: 2
     *
     * Constraints:
     *
     * n == nums1.length
     * n == nums2.length
     * 1 <= n <= 14
     * 0 <= nums1[i], nums2[i] <= 10^7
     * @param nums1
     * @param nums2
     * @return
     */
    // S1: dfs
    // time = O(n * 2^n), space = O(n)
    public int minimumXORSum(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        return dfs(dp, nums1, nums2, 0, 0);
    }

    private int dfs(int[] dp, int[] nums1, int[] nums2, int cur, int mask) {
        int n = nums1.length;
        // base case
        if (cur == n) return 0;
        if (dp[mask] != Integer.MAX_VALUE) return dp[mask];

        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) == 0) {
                dp[mask] = Math.min(dp[mask], (nums1[cur] ^ nums2[i]) + dfs(dp, nums1, nums2, cur + 1, mask + (1 << i)));
            }
        }
        return dp[mask];
    }

    // S2: DP
    // time = O(n^2 * 2^n), space = O(2^n)
    public int minimumXORSum2(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] dp = new int[1 << n];
        dp[0] = 0;

        for (int j = 0; j < n; j++) {
            int[] dp2 = dp.clone();
            for (int state = 0; state < (1 << n); state++) {
                dp[state] = Integer.MAX_VALUE / 2;
                for (int i = 0; i < n; i++) { // 先把bit 1挑出来，遍历nums1里所有的元素
                    if (((state >> i) & 1) == 1) {  // 这种情况在state里出现过
                        dp[state] = Math.min(dp[state], dp2[state - (1 << i)] + (nums1[i] ^ nums2[j]));
                    }
                }
            }
        }
        return dp[(1 << n) - 1];
    }

    // S2.2: DP + Gospher's hack
    // time = O(n^2 * 2^n), space = O(2^n)
    public int minimumXORSum3(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        int[] dp2 = new int[1 << n];
        dp[0] = 0;


        for (int j = 0; j < n; j++) {
            dp2 = dp.clone();
            int k = j + 1; // 优化：只要遍历m个state当中有k个bit 1的情况，并不需要遍历所有可能的state
            int state = (1 << k) - 1;
            while (state < (1 << n)) {
                dp[state] = Integer.MAX_VALUE / 2;
                for (int i = 0; i < n; i++) { // 先把bit 1挑出来，遍历nums1里所有的元素
                    if (((state >> i) & 1) == 1) {  // 这种情况在state里出现过
                        dp[state] = Math.min(dp[state], dp2[state - (1 << i)] + (nums1[i] ^ nums2[j]));
                    }
                }
                int c = state & -state;
                int r = state + c;
                state = (((r ^ state) >> 2) / c) | r;
            }
        }
        return dp[(1 << n) - 1];
    }

    // S3: Dijkstra
    // time = O(n * 2^n), space = O(2^n)
    public int minimumXORSum4(int[] nums1, int[] nums2) {
        int n = nums1.length;

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]); // {cost, state}
        pq.offer(new int[]{0, 0});
        boolean[] visited = new boolean[1 << n];

        while (!pq.isEmpty()) {
            int[] cur = pq.poll(); // state = cur[1]
            if (visited[cur[1]]) continue;
            visited[cur[1]] = true; // 最先跑出来的就是最小cost的最优解，所以标记下，后来的就直接跳过，不需要再访问

            int j = Integer.bitCount(cur[1]); // O(1)
            if (j == n) return cur[0]; // cost = cur[0]

            for (int i = 0; i < n; i++) {
                if (((cur[1] >> i) & 1) == 1) continue;
                int newState = cur[1] + (1 << i);
                if (visited[newState]) continue;
                pq.offer(new int[]{cur[0] + (nums1[i] ^ nums2[j]), newState});
            }
        }
        return -1;
    }
}
/**
 * ref: LC1820 -> 不带权的二分匹配，最多能连通多少对 => 匈牙利算法 (dfs 更简单, bfs 效率更高)
 * 如果有权重 -> 带权的二分匹配，用的是KM算法 -> 配对出来的值最小
 * 1 <= n <= 14  -> 用状态压缩
 * 我们这里用基于状态压缩的DP和Dijkstra来做，左右数量相等。
 * ref: LC1066
 *
 * state = 000101001011000 => nums2[0:5] 表示num1里的状态，哪些元素被匹配了，凡是1的位置对应nums1里哪些元素被匹配了
 * state = 000000000011000 => nums2[0:1]
 * dp[state]
 * assume there are k bit 1 in state
 * the minimum cost if match nums2[0:k-1] with nums1[state]
 * nums1有多种不同的选择，这些不同的状态就用state来表示
 * induction rule怎么写？
 *
 * Dijkstra: 求最短路径，有个图，求结点与结点之间的路径，指点原点和终点的最短路径，权重非负
 * state = 000000 => 000001 => 000011
 * state = 111111 => 000010 => 000101
 *                => 000100    ......
 *                nums1的匹配是随机的，但是nums2是按照顺序来与nums1来匹配的
 * state = 111111
 *
 * dp[state]只关心配对的元素，并不关心这些配对的元素究竟谁跟谁匹配，细节被模糊掉了，这些信息我们并不需要 => 极大节省时间和空间
 * 而dfs每一步都极为细致的去模拟实现整个过程
 * A1 - B1
 * A2 - B2
 *
 * A1 - B2
 * A2 - B1
 *
 * 这样工作量多了一倍，而按照状态压缩来说，这两个cost是一样的话，就可以把这两个当做同一个来看。基于状态压缩的dp要比dfs有效得多。
 */
