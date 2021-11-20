package LC001_300;
import java.util.*;
public class LC87_ScrambleString {
    /**
     * We can scramble a string s to get a string t using the following algorithm:
     *
     * If the length of the string is 1, stop.
     * If the length of the string is > 1, do the following:
     * Split the string into two non-empty substrings at a random index, i.e., if the string is s, divide it to x and y
     * where s = x + y.
     * Randomly decide to swap the two substrings or to keep them in the same order. i.e., after this step, s may become
     * s = x + y or s = y + x.
     * Apply step 1 recursively on each of the two substrings x and y.
     * Given two strings s1 and s2 of the same length, return true if s2 is a scrambled string of s1, otherwise, return
     * false.
     *
     * Input: s1 = "great", s2 = "rgeat"
     * Output: true
     *
     * Constraints:
     *
     * s1.length == s2.length
     * 1 <= s1.length <= 30
     * s1 and s2 consist of lower-case English letters.
     * @param s1
     * @param s2
     * @return
     */
    // time = O(n!), space = O(n)
    HashMap<String, Boolean> map = new HashMap<>();
    public boolean isScramble(String s1, String s2) {
        // corner case
        if (s1 == null || s2 == null) return false;

        StringBuilder sb = new StringBuilder();
        sb.append(s1);
        sb.append(s2);
        String key = sb.toString();

        if (map.containsKey(key)) return map.get(key);

        if (s1.equals(s2)) {
            map.put(key, true);
            return true;
        }

        int[] letters = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            letters[s1.charAt(i) - 'a']++;
            letters[s2.charAt(i) - 'a']--;
        }

        for (int i = 0; i < 26; i++) {
            if (letters[i] != 0) {
                map.put(key, false);
                return false;
            }
        }
        int len = s1.length();
        for (int i = 1; i < s1.length(); i++) {
            if (isScramble(s1.substring(0, i), s2.substring(0, i)) && isScramble(s1.substring(i), s2.substring(i))) {
                map.put(key, true);
                return true;
            }
            if (isScramble(s1.substring(0, i), s2.substring(len - i)) && isScramble(s1.substring(i), s2.substring(0, len - i))) {
                map.put(key, true);
                return true;
            }
        }
        map.put(key, false);
        return false;
    }

    // S2: DP
    // time = O(n^4), space = O(n^3)
    public boolean isScramble2(String s1, String s2) {
        char[] t1 = s1.toCharArray();
        char[] t2 = s2.toCharArray();
        int m = t1.length, n = t2.length;
        if (m != n) return false;

        boolean[][][] dp = new boolean[n][n][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][1] = t1[i] == t2[j];
            }
        }

        for (int k = 2; k <= n; k++) {
            for (int i = 0; i <= n - k; i++) { // start position of s1 [i...i+k-1]
                for (int j = 0; j <= n - k; j++) { // start position of s2 [j...j+k-1]
                    dp[i][j][k] = false;
                    for (int w = 1; w <= k - 1; w++) { // length, where to split
                        if (dp[i][j][w] && dp[i + w][j + w][k - w] || dp[i][j + k - w][w] && dp[i + w][j][k - w]) {
                            dp[i][j][k] = true;
                            break;
                        }
                    }
                }
            }
        }
        return dp[0][0][n];
    }
}
/**
 * 基本的思想就是：在S1上找到一个切割点，左边长度为i, 右边长为len - i。 有2种情况表明它们是IsScramble
 * (1). S1的左边和S2的左边是IsScramble， S1的右边和S2的右边是IsScramble
 * (2). S1的左边和S2的右边是IsScramble， S1的右边和S2的左边是IsScramble （实际上是交换了S1的左右子树）
 * 我们可以在递归中加适当的剪枝：在进入递归前，先把2个字符串排序，再比较，如果不相同，则直接退出掉。
 */
