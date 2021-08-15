package LC1501_1800;

public class LC1625_LexicographicallySmallestStringAfterApplyingOperations {
    /**
     * You are given a string s of even length consisting of digits from 0 to 9, and two integers a and b.
     *
     * You can apply either of the following two operations any number of times and in any order on s:
     *
     * Add a to all odd indices of s (0-indexed). Digits post 9 are cycled back to 0. For example, if s = "3456" and
     * a = 5, s becomes "3951".
     * Rotate s to the right by b positions. For example, if s = "3456" and b = 1, s becomes "6345".
     * Return the lexicographically smallest string you can obtain by applying the above operations any number of times
     * on s.
     *
     * A string a is lexicographically smaller than a string b (of the same length) if in the first position where a
     * and b differ, string a has a letter that appears earlier in the alphabet than the corresponding letter in b.
     * For example, "0158" is lexicographically smaller than "0190" because the first position they differ is at the
     * third letter, and '5' comes before '9'.
     *
     * Input: s = "5525", a = 9, b = 2
     * Output: "2050"
     *
     * Constraints:
     *
     * 2 <= s.length <= 100
     * s.length is even.
     * s consists of digits from 0 to 9 only.
     * 1 <= a <= 9
     * 1 <= b <= s.length - 1
     * @param s
     * @param a
     * @param b
     * @return
     */
    // S1: triple ennumeration
    // time = (n * 100), space = O(n)
    public String findLexSmallestString(String s, int a, int b) {
        int n = s.length();
        String res = s;

        int evenLimit = 1;
        if (b % 2 == 1) evenLimit = 10;

        for (int i = 0; i < evenLimit; i++) { // even pos
            for (int j = 0; j < 10; j++) { // odd pos
                char[] t = s.toCharArray();
                for (int k = 0; k < n; k += 2) { // for even pos when b is odd
                    t[k] = (char)((t[k] - '0' + i * a) % 10 + '0');
                }

                for (int k = 1; k < n; k += 2) { // for odd pos any time
                    t[k] = (char)((t[k] - '0' + j * a) % 10 + '0');
                }

                String p = String.valueOf(t);
                for (int k = 0; k <= n / gcd(n, b); k++) {
                    p = p.substring(n - b) + p.substring(0, n - b); // rotate
                    if (res.compareTo(p) > 0) res = p;
                }
            }
        }
        return res;
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // S2: first rotate, then add
    // time = O(n^2/gcd(n,b)), space = O(n)
    public String findLexSmallestString2(String s, int a, int b) {
        int n = s.length();
        String res = s;

        for (int k = 0; k < n / gcd(n, b); k++) {
            s = s.substring(n - b) + s.substring(0, n - b); // s[0] 尽量小，但只能b是奇数的时候才能改s[0]!
            // minimize s[0] if (b % 2 == 1)
            // minimize s[1] -> 永远可以修改s[1]
            char[] t = s.toCharArray();
            if (b % 2 == 1) { // even pos
                int minHead = t[0] - '0', count = 0;
                for (int i = 0; i < 10; i++) {
                    if ((t[0] - '0' + i * a) % 10 < minHead) {
                        minHead = (t[0] - '0' + i * a) % 10;
                        count = i;
                    }
                }
                for (int i = 0; i < n; i += 2) {
                    t[i] = (char)((t[i] - '0' + count * a) % 10 + '0');
                }
            }

            // odd pos
            int minHead = t[1] - '0', count = 0;
            for (int i = 0; i < 10; i++) {
                if ((t[1] - '0' + i * a) % 10 < minHead) {
                    minHead = (t[1] - '0' + i * a) % 10;
                    count = i;
                }
            }
            for (int i = 1; i < n; i += 2) {
                t[i] = (char)((t[i] - '0' + count * a) % 10 + '0');
            }
            String p = String.valueOf(t);
            if (res.compareTo(p) > 0) res = p;
        }
        return res;
    }
}
/**
 * O X O X O X O X
 * if b is even number, you always can only change X, O won't be changed
 * add 与 rotate互不影响，可分别穷举，2层循环，把操作下来的数值记录下来，找最小值
 * 最多枚举10次add
 * a
 * 2a
 * ...
 * 10a -> a 穷尽所有结果
 * 最多rotate n 次即可
 * rotate by b
 *          2b
 *          3b
 *          ...
 *          nb -> 又回来了
 * 12345
 * 两层暴力的枚举是可行的，时间复杂度 time = O(10n) = O(1000) 可行
 * what if b is odd?
 * X O 奇偶性会变化 -> 操作会在O上
 * 奇数位上和偶数位上加多少数值也是互相独立的，与rotate也互不影响
 * => add odd/even and rotate are all independent!!!
 * 这三者之间是互不影响的 => 都暴力枚举一遍, time = O(10 * 10 * n) = O(10000) 暴力的思路
 * 周赛第二题暴力也没问题
 * eg. b = 3 for 123456, 不需要rotate n = 6 次，只要rotate 2 次 => n / gcd(n, b) = 6 / 3 = 2
 * 3rd ennumeration only takes place when b is odd
 */