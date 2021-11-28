package LC1501_1800;
import java.util.*;
public class LC1799_MaximizeScoreAfterNOperations {
    /**
     * You are given nums, an array of positive integers of size 2 * n. You must perform n operations on this array.
     *
     * In the ith operation (1-indexed), you will:
     *
     * Choose two elements, x and y.
     * Receive a score of i * gcd(x, y).
     * Remove x and y from nums.
     * Return the maximum score you can receive after performing n operations.
     *
     * The function gcd(x, y) is the greatest common divisor of x and y.
     *
     * Input: nums = [1,2]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= n <= 7
     * nums.length == 2 * n
     * 1 <= nums[i] <= 10^6
     * @param nums
     * @return
     */
    // time = O(2^n), space = O(2^n)
    public int maxScore(int[] nums) {
        int n = nums.length / 2;
        List<Integer>[] stateSet = new List[8]; // stateSet[i]: all the states whose 1-bit number is 2i
        for (int i = 0; i < 8; i++) stateSet[i] = new ArrayList<>(); // 将计算好的2i state保存下来，注意这里set size只取一半为n!
        int[] dp = new int[(1 << (2 * n)) + 5];
        int[][] g = new int[2 * n][2 * n];
        for (int i = 0; i < 2 * n; i++) { // pre-calculate gcd
            for (int j = 0; j < 2 * n; j++) {
                g[i][j] = gcd(nums[i], nums[j]);
            }
        }

        for (int i = 1; i <= n; i++) {
            // collect stateSet[i]
            int state = (1 << (2 * i)) - 1;
            while (state < (1 << (2 * n))) {
                stateSet[i].add(state);
                int c = state & -state;
                int r = state + c;
                state = (((r ^ state) >> 2) / c) | r;
            }
        }
        stateSet[0].add(0); // must have this for the stateSet[i - 1] below!

        for (int i = 1; i <= n; i++) { // 注意，i是从1开始的，前面多开了一维来应付stateSet[i - 1]
            for (int state : stateSet[i]) {
                for (int substate : stateSet[i - 1]) {
                    if ((state & substate) != substate) continue;
                    int[] cur = helper(state - substate);
                    int x = cur[0], y = cur[1];
                    dp[state] = Math.max(dp[state], dp[substate] + i * g[x][y]);
                }
            }
        }
        return dp[(1 << (2 * n)) - 1];
    }

    private int[] helper(int state) {
        int x = -1, y = -1;
        for (int i = 0; i < 14; i++) {
            if (((state >> i) & 1) == 1) {
                if (x == -1) x = i;
                else y = i;
            }
        }
        return new int[]{x, y};
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // S1.2
    public int maxScore2(int[] nums) {
        int n = nums.length / 2;
        List<Integer>[] stateSet = new List[8]; // stateSet[i]: all the states whose 1-bit number is 2i
        for (int i = 0; i < 8; i++) stateSet[i] = new ArrayList<>();
        int[] dp = new int[(1 << (2 * n)) + 5];
        int[][] g = new int[2 * n][2 * n];
        for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j < 2 * n; j++) {
                g[i][j] = gcd(nums[i], nums[j]);
            }
        }

        for (int i = 1; i <= n; i++) {
            // collect stateSet[i]
            int state = (1 << (2 * i)) - 1;
            while (state < (1 << (2 * n))) {
                stateSet[i].add(state);
                int c = state & -state;
                int r = state + c;
                state = (((r ^ state) >> 2) / c) | r;
            }
        }
        stateSet[0].add(0);

        for (int i = 1; i <= n; i++) {
            for (int state : stateSet[i]) {
                if (i == 1) { // initial state has to take out and deal separately!
                    int[] cur = helper(state);
                    int x = cur[0], y = cur[1];
                    dp[state] = Math.max(dp[state], 0 + i * g[x][y]);
                } else {
                    for (int subset = state; subset > 0; subset = (subset - 1) & state) { // 无法枚举到subset = 0，要另外单独处理
                        if (Integer.bitCount(subset) != 2 * (i - 1)) continue;
                        int[] cur = helper(state - subset);
                        int x = cur[0], y = cur[1];
                        dp[state] = Math.max(dp[state], dp[subset] + i * g[x][y]);
                    }
                }
            }
        }
        return dp[(1 << (2 * n)) - 1];
    }

//    private int[] helper(int state) {
//        int x = -1, y = -1;
//        for (int i = 0; i < 14; i++) {
//            if (((state >> i) & 1) == 1) {
//                if (x == -1) x = i;
//                else y = i;
//            }
//        }
//        return new int[]{x, y};
//    }
//
//    private int gcd(int a, int b) {
//        if (b == 0) return a;
//        return gcd(b, a % b);
//    }
}
/**
 * 数据量特别小 => 暴搜: dfs/state compression
 * 没有什么剪枝空间
 * 2^14 = 16384
 * for i = 1 : n
 *      dp[集合大小是2i的state] = max{dp[集合大小是2*(i-1)substate] + i*gcd(nums[x],nums[y])}
 * return dp[(1 << (2*n)-1]
 * substate must be a subset of state
 * 全部state跑一遍再过滤 => n*(2^2n)*(2^2n)
 * => use Gosper's hack
 * 对所有的C(14,x)而言，C(14,6) = C(14,8)是最大的 => 总共有C(14,6) = 3003种状态，都可以存下来
 * 所有的state = 2^14 = 16384,但我们只要存1-bit数是偶数个数的state => total = 2^14 / 2 = 2^13 = 8000 都可以存下来
 */