package LC2101_2400;

public class LC2305_FairDistributionofCookies {
    /**
     * You are given an integer array cookies, where cookies[i] denotes the number of cookies in the ith bag. You are
     * also given an integer k that denotes the number of children to distribute all the bags of cookies to. All the
     * cookies in the same bag must go to the same child and cannot be split up.
     *
     * The unfairness of a distribution is defined as the maximum total cookies obtained by a single child in the
     * distribution.
     *
     * Return the minimum unfairness of all distributions.
     *
     * Input: cookies = [8,15,10,20,8], k = 2
     * Output: 31
     *
     * Input: cookies = [6,1,3,2,2,4,1,2], k = 3
     * Output: 7
     *
     *
     Constraints:

     2 <= cookies.length <= 8
     1 <= cookies[i] <= 10^5
     2 <= k <= cookies.length
     * @param cookies
     * @param k
     * @return
     */
    // time = O(n^n), space = O(n)
    int res = Integer.MAX_VALUE;
    public int distributeCookies(int[] cookies, int k) {
        dfs(cookies, 0, k, new int[k]);
        return res;
    }

    private void dfs(int[] nums, int idx, int k, int[] arr) {
        if (idx == nums.length) {
            int max = 0;
            for (int x : arr) max = Math.max(max, x);
            res = Math.min(res, max);
            return;
        }

        for (int i = 0; i < k; i++) {
            arr[i] += nums[idx];
            if (arr[i] <= res) dfs(nums, idx + 1, k, arr); // 剪枝，不会对答案更新有任何贡献，可以直接剪掉！
            arr[i] -= nums[idx];
        }
    }
}
/**
 * 暴力搜索，数据规模只有8
 * time = O(8^8) = O(2^24) = O(10^6 * 2^4) = 1.6 * 10^7
 * 模拟退火 -> if n = 20 超时
 * 用启发式搜索 AC2680
 * 模拟每个数据放到哪个组
 */