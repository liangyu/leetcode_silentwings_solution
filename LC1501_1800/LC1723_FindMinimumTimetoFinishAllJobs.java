package LC1501_1800;
import java.util.*;
public class LC1723_FindMinimumTimetoFinishAllJobs {
    /**
     * You are given an integer array jobs, where jobs[i] is the amount of time it takes to complete the ith job.
     *
     * There are k workers that you can assign jobs to. Each job should be assigned to exactly one worker. The working
     * time of a worker is the sum of the time it takes to complete all jobs assigned to them. Your goal is to devise an
     * optimal assignment such that the maximum working time of any worker is minimized.
     *
     * Return the minimum possible maximum working time of any assignment.
     *
     * Input: jobs = [3,2,3], k = 3
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= k <= jobs.length <= 12
     * 1 <= jobs[i] <= 10^7
     * @param jobs
     * @param k
     * @return
     */
    // S1: Bit DP
    // time = O(k * 3^n)，space = O(k * 2^n)
    public int minimumTimeRequired(int[] jobs, int k) {
        int n = jobs.length;
        int[][] dp = new int[k + 1][(int)Math.pow(2, n)];
        int[] time = new int[(int)Math.pow(2, n)];

        for (int state = 0; state < (1 << n); state++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if (((state >> i) & 1) == 1) sum += jobs[i];
            }
            time[state] = sum;
        }

        dp[0][0] = 0;
        for (int state = 1; state < (1 << n); state++) {
            dp[0][state] = Integer.MAX_VALUE; // 除了dp[0][0],其他情况不可能发生，所以预设为无穷大！
        }

        for (int i = 1; i <= k; i++) { // O(k)
            for (int state = 0; state < (1 << n); state++) { // O(3^n) 不会遍历到不属于它的subset
                dp[i][state] = Integer.MAX_VALUE;
                for (int subset = state; subset > 0; subset = (subset - 1) & state) {
                    dp[i][state] = Math.min(dp[i][state], Math.max(dp[i - 1][state - subset], time[subset]));
                }
            }
        }
        return dp[k][(1 << n) - 1];
    }

    // S2: BS + Bit DFS
    // time = O(2^n), space = O(2^n)
    public int minimumTimeRequired2(int[] jobs, int k) {
        int n = jobs.length;
        int[] time = new int[(int)Math.pow(2, n)];
        int[][] memo = new int[(int)Math.pow(2, n)][k];
        for (int state = 0; state < (1 << n); state++) { // O(2^n)
            int sum = 0;
            for (int i = 0; i < n; i++) { // O(n)
                if (((state >> i) & 1) == 1) sum += jobs[i];
            }
            time[state] = sum;
        }

        int sum = 0;
        for (int job : jobs) sum += job; // O(n)

        int left = 1, right = sum;
        while (left < right) { // O(logS)
            // clear memo
            for (int i = 0; i < memo.length; i++) Arrays.fill(memo[i], 0); // O(2^n)
            int mid = left + (right - left) / 2;
            if (!dfs((1 << n) - 1, mid, 0, k, time, memo)) left = mid + 1;
            else right = mid;
        }
        return left; // 肯定有解
    }

    private boolean dfs(int state, int th, int m, int k, int[] time, int[][] memo) {  // 递归k次
        if (state == 0) return true;
        if (m == k) return false; // 如果已经给k个人都指派完了，那么还没到达state = 0，那么任务完不成了。

        if (memo[state][m] == 1) return false; // memo里存的都是过往失败的案例，因为成功的话已经直接返回true了

        for (int subset = state; subset > 0; subset = (subset - 1) & state) { // iterate through all subsets of the state
            if (time[subset] > th) continue;
            if (dfs(state - subset, th, m + 1, k, time, memo)) return true; // 接着指派给第m + 1个工人
        }
        memo[state][m] = 1;
        return false;
    }

    // S3: BS + Tranditional DFS
    // time = O(nlogn + logS * k^n), space = O(k + n)
    public int minimumTimeRequired3(int[] jobs, int k) {
        int n = jobs.length, sum = 0;
        for (int job : jobs) sum += job;

        Arrays.sort(jobs); // O(nlogn)

        int left = 1, right = sum;
        while (left < right) { // O(logS)
            int[] workers = new int[k];
            int mid = left + (right - left) / 2;
            if (!dfs(workers, jobs, mid, n - 1, k)) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    private boolean dfs(int[] workers, int[] jobs, int th, int i, int k) {  // 处理第i件任务  O(k^n)
        // base case
        if (i < 0) return true;

        boolean flag = false;
        for (int j = 0; j < k; j++) { // O(k)
            if (workers[j] + jobs[i] > th) continue;
            if (workers[j] == 0) {
                if (flag) continue; // 非常高效的剪枝，优化了k倍。
                else flag = true;
            }
            workers[j] += jobs[i];
            if (dfs(workers, jobs, th, i - 1, k)) return true;
            workers[j] -= jobs[i]; // backtracking
        }
        return false;
    }
}
/**
 * NP问题 -> 暴搜
 * 状态压缩dp
 * dp[i][state]: the minimum possible maximum working time of any assignment if we use i workers and get jobs of state done
 * 00110101
 * 00100100 是上面的子集
 * 遍历第i个工人做了哪些活
 * dp[i][state] = min{max{dp[i - 1][state - subset], time(subset)}} over all possible subset of state
 * 3层循环
 * state = 10 => subset = 10, 00, 而不可能遍历到01
 * 效率比较高的做法是二分猜值！！！
 * 32次就能猜出答案，每次猜出来只要判断可不可行即可。
 * S3: 传统DFS是一个个任务来分配，递归n次，而bit dfs是一个个人来分配
 * workers是个数组，把数组存入memo没法压缩，记忆化很麻烦！
 * 需要剪枝！=> 优先分配比较大的job，就越容易触发threshold限制，及时剪枝，今早回头！
 * 而如果先分配小的，再分配大的发现超标，之前那些dfs层数就浪费了。
 * 继续优化：
 * 如果你当前处理第i个job,如果你分配给了一个空闲的工人，如果有多个空闲的工人，你分配给谁都一样，这时候无需继续分叉，只是轮换对称了一下
 */