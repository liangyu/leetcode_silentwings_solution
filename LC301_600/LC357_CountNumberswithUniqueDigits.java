package LC301_600;
public class LC357_CountNumberswithUniqueDigits {
    /**
     * Given an integer n, return the count of all numbers with unique digits, x, where 0 <= x < 10^n.
     *
     * Input: n = 2
     * Output: 91
     *
     * Input: n = 0
     * Output: 1
     *
     * Constraints:
     *
     * 0 <= n <= 8
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int countNumbersWithUniqueDigits(int n) {
        if (n == 0) return 1;
        if (n == 1) return 10;

        int res = 10, cur = 9;
        for (int i = 0; i < n - 1; i++) {
            cur *= 9 - i;
            res += cur;
        }
        return res;
    }
}
