package LC301_600;
import java.util.*;
public class LC583_DeleteOperationforTwoStrings {
    /**
     * Given two strings word1 and word2, return the minimum number of steps required to make word1 and word2 the same.
     *
     * In one step, you can delete exactly one character in either string.
     *
     * Input: word1 = "sea", word2 = "eat"
     * Output: 2
     *
     *
     Constraints:

     1 <= word1.length, word2.length <= 500
     word1 and word2 consist of only lowercase English letters.

     * @param word1
     * @param word2
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) dp[i][0] = i;
        for (int j = 1; j <= n; j++) dp[0][j] = j;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
                }
            }
        }
        return dp[m][n];
    }
}
/**
 * two string converging 解答方法一模一样，拿2个string搞事情
 * 给你2个字符串，xxxx => dp
 * dp[i][j]: 第一个字符串处理到第i个字符的时候，第二个字符串处理到j个字符的时候
 * the minimum number of steps required to make word1[0:i] and word2[0:j] the same
 * dp[m][n]
 *
 * dp[i-1][j-1]
 * dp[i][j-1]
 * dp[i-1][j]
 *
 * x x x x i
 * y y y y y y y j
 * 套路：考虑i和j的关系
 * 如果相等 => 啥都不用干，只要前面的字符串两者相等
 *
 * if (word1[i] == word2[j])
 *      dp[i][j] = min(dp[i][j], dp[i-1][j-1])
 *  dp[i][j] = min(dp[i][j], dp[i-1][j] + 1)
 *  dp[i][j] = min(dp[i][j], dp[i][j-1] + 1)
 *  => bottom-up
 *
 *  DP:
 *  状态表示：
 *  1. 集合：所有将s1(1~i) 与s2(1~j)变成相同字符中的所有方案
 *  2. 属性：min
 *  状态计算：4种情况中取最小值
 *  （1）i,j都存在：f(i-1,j-1)
 *  （2）i存在，j不存在: f(i,j-1) + 1
 *  （3）i不存在，j存在: f(i-1,j) + 1
 *  （4）i,j都不存在: f(i-1,j-1) + 2  -> 被包含在（2）和（3）之中，不用单独考虑
 */
