package LC2101_2400;

public class LC2272_SubstringWithLargestVariance {
    /**
     * The variance of a string is defined as the largest difference between the number of occurrences of any 2
     * characters present in the string. Note the two characters may or may not be the same.
     *
     * Given a string s consisting of lowercase English letters only, return the largest variance possible among all
     * substrings of s.
     *
     * A substring is a contiguous sequence of characters within a string.
     *
     * Input: s = "aababbb"
     * Output: 3
     *
     * Input: s = "abcde"
     * Output: 0
     *
     *
     Constraints:

     1 <= s.length <= 10^4
     s consists of lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public int largestVariance(String s) {
        int n = s.length(), res = 0;
        int[] count = new int[26];
        for (char c : s.toCharArray()) count[c - 'a']++;
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                if (count[i] == 0 || count[j] == 0) continue;
                if (i == j) continue;
                int[] nums = new int[n];
                for (int k = 0; k < n; k++) {
                    if (s.charAt(k) - 'a' == i) nums[k] = 1;
                    else if (s.charAt(k) - 'a' == j) nums[k] = -1;
                }
                res = Math.max(res, helper(nums));
            }
        }
        return res;
    }

    private int helper(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
        }
        int curSum = 0, res = 0;
        for (int i = n - 1; i >= 0; i--) {
            curSum = Math.max(curSum + nums[i], nums[i]);
            if (nums[i] == -1) res = Math.max(res, dp[i] + curSum - nums[i]); // 一定要有一个-1存在才能去求最大值res
        }
        return res;
    }
}
/**
 * O(kn) k = 100 -> 26, 26^2
 * 穷举本题中的最大频次字符x和最小频次字母y。
 * 我们需要在原字符串里面找一个滑窗，使得x的频次与y的频次之差最大，而其他字母都没有任何作用。
 * 如果将x的字符都替换为1，y的字符都替换为-1，其他字符都替换为0，那么不就是变成了寻找max subarray sum的题目
 * 注意，这里有一点不同，根据题意，我们想要找的subarray必须至少包含一个-1.
 * 传统的kadane算法，我们很有可能找出只有+1的subarray。
 * dp[i]: the largest sum subarray ending at i
 * dp2[i] = max{dp[i - 1] + nums[i], nums[i]}   kadane算法
 * dp1[i]
 * dp1[i] + dp2[i] - nums[i]
 * 3 pass
 * 立足每个-1，往左看看，往右看看
 */
