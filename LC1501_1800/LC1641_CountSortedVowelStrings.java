package LC1501_1800;
import java.util.*;
public class LC1641_CountSortedVowelStrings {
    /**
     * Given an integer n, return the number of strings of length n that consist only of vowels (a, e, i, o, u)
     * and are lexicographically sorted.
     *
     * A string s is lexicographically sorted if for all valid i, s[i] is the same as or comes before s[i+1]
     * in the alphabet.
     *
     * Input: n = 1
     * Output: 5
     * Explanation: The 5 sorted strings that consist of vowels only are ["a","e","i","o","u"].
     *
     * Input: n = 2
     * Output: 15
     * Explanation: The 15 sorted strings that consist of vowels only are
     * ["aa","ae","ai","ao","au","ee","ei","eo","eu","ii","io","iu","oo","ou","uu"].
     * Note that "ea" is not a valid string since 'e' comes after 'a' in the alphabet.
     *
     * Input: n = 33
     * Output: 66045
     *
     * Constraints:
     * 1 <= n <= 50
     * @param n
     * @return
     */
    // S1: DP (prefer!!!)
    // time = O(n), space = O(n)
    public int countVowelStrings(int n) {
        int[][] dp = new int[n][5];

        // init: 构造第一个字符，无论是a e i o u 哪种情况，都是只有一种构造方法
        for (int k = 0; k < 5; k++) dp[0][k] = 1; // 对于第一个字符是'a' -> 1种构造方法，其他也都一样

        for (int i = 1; i < n; i++) {
            for (int k = 0; k < 5; k++) {
                for (int j = 0; j <= k; j++) {
                    dp[i][k] += dp[i - 1][j];
                }
            }
        }

        int res = 0;
        for (int k = 0; k < 5; k++) res += dp[n - 1][k];
        return res;
    }

    // S2: Math (最优解!)
    // time = O(1), space = O(1)
    public int countVowelStrings2(int n) {
        return combo(n + 4, 4);
    }

    private int combo(int n, int k) { // O(k)
        long res = 1;
        for (int i = 0; i < k; i++) {
            res *= n - i;
            res /= i + 1;
        }
        return (int) res;
    }
}

/**
 * S1: DP
 * X X X X i-1 i
 * dp[i][k]: the number of strings of which the i-th element is k
 * dp[i][k] = sum(dp[i - 1][j]) (j <= k)    i = 1 ~ n, k = 0 ~ 4
 *
 * S2: Math
 * 插板法：n balls; n + 1 spaces; 4 boards
 *        _o_o | o_o | o_o_o | o_o | o_
 *          2     2      3      2    1  ==> aaeeiiioou
 *        _o_o | o_o | o_o_o | o_o_o| _ |
 *          2     2      3       3    0   ==> aaeeiiiooo (u不用)
 *      C(n + 1, 4)
 *
 *        |o_o_o_o|o_o_o|o_o_o|
 *      0     4     3     3    0  ==> 问题：2，3，4永远不可能为0，但是事实上是可以为0的，所以是有问题的
 *       ==> 解决：n + 1 spaces, 3 special options：overlap 1 & 2，2 & 3， 3 & 4
 *       ==> n + 4 candidates ==> pick 4 ==> C(n + 4, 4)
 *     _o_o_o_o_o_o_o_o_o_o_    2 5 7 overlap 1 & 2
 *     _o_o || o_o_o | o_o | o_o_o_
 *       2  0    3      2      3     ==> aaiiioouuu
 *     _o_o_o_o_o_o_o_o_o_o_    0 overlap 1 & 2 overlap 2 & 3 overlap 3 & 4
 *     |||| o_o_o_o_o_o_o_o_o_o_
 *     0 0 0 0 10  ==> uuuuuuuuuu -> 符合条件的string
 *
 *     ==> res = C(n + 4, 4) = (n + 4) * (n + 3) * (n + 2) * (n + 1) / 24
 */
