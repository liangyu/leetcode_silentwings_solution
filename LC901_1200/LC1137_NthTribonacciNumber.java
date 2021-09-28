package LC901_1200;

public class LC1137_NthTribonacciNumber {
    /**
     * The Tribonacci sequence Tn is defined as follows:
     *
     * T0 = 0, T1 = 1, T2 = 1, and Tn+3 = Tn + Tn+1 + Tn+2 for n >= 0.
     *
     * Given n, return the value of Tn.
     *
     * Input: n = 4
     * Output: 4
     *
     * Constraints:
     *
     * 0 <= n <= 37
     * The answer is guaranteed to fit within a 32-bit integer, ie. answer <= 2^31 - 1.
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int tribonacci(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;

        int x = 0, y = 1, z = 1, res = 0;
        for (int i = 3; i <= n; i++) {
            res = x + y + z;
            x = y;
            y = z;
            z = res;
        }
        return res;
    }
}
