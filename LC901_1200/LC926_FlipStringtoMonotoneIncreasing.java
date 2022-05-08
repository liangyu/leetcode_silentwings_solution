package LC901_1200;

public class LC926_FlipStringtoMonotoneIncreasing {
    /**
     * A binary string is monotone increasing if it consists of some number of 0's (possibly none), followed by some
     * number of 1's (also possibly none).
     *
     * You are given a binary string s. You can flip s[i] changing it from 0 to 1 or from 1 to 0.
     *
     * Return the minimum number of flips to make s monotone increasing.
     *
     * Input: s = "00110"
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s[i] is either '0' or '1'.
     * @param s
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public int minFlipsMonoIncr(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        int flip = 0, one = 0;

        for (char c : s.toCharArray()) {
            if (c == '0') {
                if (one == 0) continue; // all previous ones = 0
                else flip++; // 0 -> 1
            } else { // c == '1'
                one++;
            }
            // keep 1 or 1 -> 0
            flip = Math.min(one, flip); // keep 1: no flip, flip -> flip不变； 1 -> 0: flip, total time = number of one
        }
        return flip;
    }

    // S2: DP
    // time = O(n), space = O(n)
    public int minFlipsMonoIncr2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length(), one = 0;
        int[] f = new int[n];

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                f[i] = i == 0 ? 0 : Math.min(f[i - 1] + 1, one);
            } else {
                f[i] = i == 0 ? 0 : Math.min(f[i - 1], one + 1);
                one++; // 注意, i == 0 时，虽然f[0] = 0, 但是由于s.charAt(i) == 1, one依然要+1，千万不能忘！！！！
            }
        }
        return f[n - 1];
    }
}
/**
 * dp[i]表示前i个字符所需最小翻转次数，则dp[i+1]有如下四种情况，取其最小值即可
 * 1、第i+1个字符为0，且不翻转，d[i+1]=前i个字符中1的个数
 * 2、第i+1个字符为0，且翻转，d[i+1]=d[i]+1
 * 3、第i+1个字符为1，且不翻转，d[i+1]=d[i]
 * 4、第i+1个字符为1，且翻转，d[i+1]=前i个字符中1的个数+1
 *
 * Skip 0's until we encounter the first 1.
 * Keep track of number of 1's in onesCount (Prefix).
 * Any 0 that comes after we encounter 1 can be a potential candidate for flip. Keep track of it in flipCount.
 * If flipCount exceeds oneCount - (Prefix 1's flipped to 0's)
 * a. Then we are trying to flip more 0's (suffix) than number of 1's (prefix) we have.
 * b. Its better to flip the 1's instead.
 */
