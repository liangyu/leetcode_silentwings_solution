package LC001_300;
import java.util.*;
public class LC96_UniqueBinarySearchTrees {
    /**
     * Given an integer n, return the number of structurally unique BST's (binary search trees) which has exactly n
     * nodes of unique values from 1 to n.
     *
     * Input: n = 3
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= n <= 19
     * @param n
     * @return
     */
    // time = O(n^2), space = O(n)
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }
        return dp[n];
    }
}
/**
 * 先确定root是哪个
 * 1，2，3，...,n
 *       k
 *     /   \
 *  1~k-1   k+1~n
 * 递归解法
 * => 从下往上，越往下能选择的点越来越少，最后有可能一个点，只能构成一个数 => dp
 * dp[n] = 0;
 */