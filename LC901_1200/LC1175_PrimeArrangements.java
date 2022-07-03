package LC901_1200;

public class LC1175_PrimeArrangements {
    /**
     * Return the number of permutations of 1 to n so that prime numbers are at prime indices (1-indexed.)
     *
     * (Recall that an integer is prime if and only if it is greater than 1, and cannot be written as a product of two positive integers both smaller than it.)
     *
     * Since the answer may be large, return the answer modulo 10^9 + 7.
     *
     * Input: n = 5
     * Output: 12
     *
     * Input: n = 100
     * Output: 682289015
     *
     * Constraints:
     *
     * 1 <= n <= 100
     * @param n
     * @return
     */
    // time = O(n^(3/2), space = O(1)
    long M = (long)(1e9 + 7);
    public int numPrimeArrangements(int n) {
        int cnt = 0;
        for (int i = 2; i <= n; i++) {
            boolean is_prime = true;
            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    is_prime = false;
                    break;
                }
            }
            if (is_prime) cnt++;
        }
        return (int)(fact(n - cnt) * fact(cnt) % M);
    }

    private long fact(int n) {
        long res = 1;
        for (int i = 1; i <= n; i++) {
            res = res * i % M;
        }
        return res;
    }
}
