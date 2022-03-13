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

        for (int i = 1; i <= n; i++) { // i: 结点的个数
            for (int j = 0; j < i; j++) { // j: 左子树有多少个
                dp[i] += dp[j] * dp[i - j - 1]; // 注意：这里是+=
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
 * Catalan
 * h(0) = 1, h(1) = 1, h(2) = 2, h(3) = 5 ...
 * Follow up:
 * 1. n nodes, how many structures of binary trees => h(n)
 * 2. n pairs of parenthesis, how many valid sequences => h(n)
 *   (k) (n - 1 - k)  可以放在现有的一对括号里，或者放在括号外
 * 3. full binary tree: for all nodes, they either have no children or have two children
 * 2n + 1 nodes, how many structures of full binary trees? => h(n)
 * 2n + 1 nodes => 2n edges => n pairs
 * visit root => go through left edge => ... => go through right edge
 *                  (                                       )
 * a valid parenthesis sequence
 * preorder sequence for full binary tree
 * h(n) = h(n - 1) * (4n - 2) / (n + 1)
 * h(n) = C(2n, n) - C(2n, n - 1), C 是组合数
 */