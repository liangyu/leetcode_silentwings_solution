package LC901_1200;
import java.util.*;
public class LC1199_MinimumTimetoBuildBlocks {
    /**
     * You are given a list of blocks, where blocks[i] = t means that the i-th block needs t units of time to be built.
     * A block can only be built by exactly one worker.
     *
     * A worker can either split into two workers (number of workers increases by one) or build a block then go home.
     * Both decisions cost some time.
     *
     * The time cost of spliting one worker into two workers is given as an integer split. Note that if two workers
     * split at the same time, they split in parallel so the cost would be split.
     *
     * Output the minimum time needed to build all blocks.
     *
     * Initially, there is only one worker.
     *
     * Input: blocks = [1], split = 1
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= blocks.length <= 1000
     * 1 <= blocks[i] <= 10^5
     * 1 <= split <= 100
     * @param blocks
     * @param split
     * @return
     */
    // S1: dfs + dp
    // time = O(n^2), space = O(n^2)
    public int minBuildTime(int[] blocks, int split) {
        int n = blocks.length;
        int[][] dp = new int[n][n + 1];
        Arrays.sort(blocks);

        for (int i = 0; i < n; i++) Arrays.fill(dp[i], -1);
        int i = 0, j = n - 1;
        while (i <= j) {
            int temp = blocks[i];
            blocks[i++] = blocks[j];
            blocks[j--] = temp;
        }
        return dfs(blocks, split, dp, 0, 1);
    }

    private int dfs(int[] blocks, int split, int[][] dp, int i, int j) {
        int n = blocks.length;
        if (i == n) return 0;
        if (j == 0) return Integer.MAX_VALUE / 2;
        if (j > n - i) return blocks[i];
        if (dp[i][j] != -1) return dp[i][j];

        dp[i][j] = Math.min(split + dfs(blocks, split, dp, i, j * 2), Math.max(blocks[i], dfs(blocks, split, dp, i + 1, j - 1)));
        return dp[i][j];
    }

    // S2: Huffman's Algorithm
    // time = O(nlogn), space = O(n)
    public int minBuildTime2(int[] blocks, int split) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int b : blocks) pq.offer(b);
        while (pq.size() > 1) {
            int x = pq.poll(), y = pq.poll();
            pq.offer(y + split);
        }
        return pq.poll();
    }
}
/**
 * 感觉本题用top-down的递归要比bottom up的动态规划更好写。
 * 我们将所有任务按照从大到小的顺序排序。令dp[i][j]表示用j个工人完成blocks[i:end]的最少时间。我们有两种决策：
 * 当前我们不派工人干活，只派工人分裂，这样需要花费固定split的时间，索性贪心些，将人数double一下。所以dfs(i,j) = split + dfs(i, j*2)
 * 当前我们至少派一个工人干活，干什么活呢？肯定是干耗时最长的活（也就是blocks[i]），因为我们可以把耗时短的工作放在稍后去做，起到尽量并行完成的目的。
 * 因此dfs(i,j) = Math.max(blocks[i], dfs(i+1, j-1)).
 * 但是注意到，这个决策其实是包含在上面的第二个决策里的，我们不需要重复去列举。否则我们如果枚举当前指派干活的工人人数，会TLE。
 * 边界条件有这么几种：
 * 1. 工作干完了，即i=blocks.size().
 * 2. 工作没干完，但没有工人了，即j==0，这时候无法做任何操作了（包括分裂）。
 * 3. 工人比工作的数量多，那么就直接让所有工人都并行开工，这样答案就是blocks[i].
 */
