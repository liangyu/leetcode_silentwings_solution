package LC1801_2100;

public class LC1933_CheckifStringIsDecomposableIntoValueEqualSubstrings {
    /**
     * A value-equal string is a string where all characters are the same.
     *
     * For example, "1111" and "33" are value-equal strings.
     * In contrast, "123" is not a value-equal string.
     * Given a digit string s, decompose the string into some number of consecutive value-equal substrings where
     * exactly one substring has a length of 2 and the remaining substrings have a length of 3.
     *
     * Return true if you can decompose s according to the above rules. Otherwise, return false.
     *
     * A substring is a contiguous sequence of characters in a string.
     *
     * Input: s = "000111000"
     * Output: false
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s consists of only digits '0' through '9'.
     * @param s
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public boolean isDecomposable(String s) {
        // corner case
        if (s == null || s.length() == 0) return false;

        int n = s.length();
        if (n % 3 != 2) return false; // 这里保证了合法的string必须要有一个长度为2的substring,否则下面最后必须return flag

        int i = 0, j = 0;
        boolean flag = false;
        while (j < n) {
            while (j < n && s.charAt(j) == s.charAt(i)) j++;
            int len = j - i;
            if (len % 3 == 2 && !flag) {
                flag = true;
                i = j;
            } else if (len % 3 == 0) i = j;
            else return false;
        }
        return true; // return flag can make sure we have a length of 2 substring
    }

    // S2
    // time = O(n), space = O(1)
    public boolean isDecomposable2(String s) {
        // corner case
        if (s == null || s.length() < 2) return false;

        int n = s.length(), count = 1;
        boolean flag = false;

        for (int i = 1; i <= n; i++) {
            if (i < n && s.charAt(i) == s.charAt(i - 1)) count++;
            else {
                if (count % 3 == 1) return false;
                if (count % 3 == 2) {
                    if (flag) return false;
                    flag = true;
                }
                count = 1;
            }
        }
        return flag;
    }
}
