package LC1501_1800;
import java.util.*;
public class LC1714_SumOfSpecialEvenlySpacedElementsInArray {
    /**
     * You are given a 0-indexed integer array nums consisting of n non-negative integers.
     *
     * You are also given an array queries, where queries[i] = [xi, yi]. The answer to the ith query is the sum of all
     * nums[j] where xi <= j < n and (j - xi) is divisible by yi.
     *
     * Return an array answer where answer.length == queries.length and answer[i] is the answer to the ith query modulo
     * 10^9 + 7.
     *
     * Input: nums = [0,1,2,3,4,5,6,7], queries = [[0,3],[5,1],[4,2]]
     * Output: [9,18,10]
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 5 * 10^4
     * 0 <= nums[i] <= 10^9
     * 1 <= queries.length <= 1.5 * 10^5
     * 0 <= xi < n
     * 1 <= yi <= 5 * 10^4
     * @param nums
     * @param queries
     * @return
     */
    // time = O(n^(3/2) * m), space = O(sqrt(n) * yi)
    public int[] solve(int[] nums, int[][] queries) {
        int n = nums.length;
        long[][] dp = new long[256][50005];

        for (int d = 1; d < Math.sqrt(n); d++) {
            for (int i = 0; i < n; i++) {
                dp[d][i] = -1;
            }
        }

        List<Integer> res = new ArrayList<>();
        long M = (long)(1e9 + 7);
        for (int[] q : queries) {
            int s = q[0], d = q[1];
            if (d >= Math.sqrt(n)) { // 注意这里是 >= instead of >
                long sum = 0;
                int i = s;
                while (i < n) {
                    sum = (sum + nums[i]) % M;
                    i += d;
                }
                res.add((int)sum);
            } else {
                if (dp[d][s] == -1) { // 没算过，那把所有dp[d][i]都算一遍，留着以后用
                    for (int i = n - 1; i >= 0; i--) { // 注意：i is from n-1 to 0,小的依赖大的，所以要倒着来
                        if (i + d < n) { // i + d 不能越界
                            dp[d][i] = (dp[d][i + d] + nums[i]) % M;
                        } else { // i 本身是idx里的最后一个了，i + d就越界了
                            dp[d][i] = nums[i]; // 相当于只有一个元素，就是nums[i]本身
                        }
                    }
                }
                res.add((int)dp[d][s]);
            }
        }
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) ans[i] = res.get(i);
        return ans;
    }
}
/**
 * query[s, d]
 * nums[s] + nums[s + d] + nums[s + 2d] = nums[s + 3d] + ...
 * 在index上是一个等差数列
 * 中间跨度有个d
 * 区间和是前缀和数组，这里根本用不上
 * 暴力蛮干 -> 10^9
 * d是个变量，如果d非常大，加起来也未尝不可
 * d要多大才能变得稀疏 -> 约定俗成的技巧
 * d = sqrt(n) => n/d = sqrt(n) 阈值
 * sqrt decomposition  切成若干段来处理 -> sqrt(n)
 * 很多时候把时间复杂度降低到根号的水平
 * [x x x x][x x x x] [x x x x][x x x x]
 *   sum0     sum1      sum2      sum3
 *   sum1 + sum2 + 两端的零头
 * 最多sqrt(n)个trunk，零头最多sqrt(n)个
 * sqrt(n) + sqrt(n)
 * 某个元素要改的话，只要更新那个trunk就好了
 * 最多暴力加sqrt(n)个，这样还能接受 10^7
 * 运气好，d都特别大，就可以直接用暴力解法
 * d > sqrt(n) => n/d = sqrt(n)  => time = O(sqrt(n) + Q)
 * d < sqrt(n) => dp[d][i] (i = 0,1,2,...n-1)  d是固定的，提前算好 => O(n) => time = O(n * sqrt(n) + Q)
 * query[d][i] = query[d][i+d] + nums[i]
 * d最多sqrt(n)个 => n*sqrt(n)  预处理
 * query[s,d]对应dp中的一个元素 => O(1)时间都算出来
 * 定义一个"后缀数组"
 * 对于相同的d而言，如果s不同，它的结果可能只相差若干项
 * query[s, d]
 * nums[s] + nums[s + d] + nums[s + 2d] = nums[s + 3d] + ...
 * quert[s+d, d] = nums[s + d] + nums[s + 2d] = nums[s + 3d] + ...
 * query之间可能只相差一项
 * 均摊一下，时间复杂度显著降下来，跨度为d，O(1)
 * query[s+kd,d] => O(1)  就是在原来基础上再加一项
 * 可以建立一个类似query[s,d]的东西
 * 总结：如果要把长序列拆分成若干个序列，首选的长度就是sqrt(n)，这样每段就是sqrt(n)个元素
 * d特别大就暴力，小的话就搞个dp，分界点就是sqrt(n)
 */