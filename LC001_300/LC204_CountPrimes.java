package LC001_300;
import java.util.*;
public class LC204_CountPrimes {
    /**
     * Count the number of prime numbers less than a non-negative number, n.
     *
     * Input: n = 10
     * Output: 4
     *
     * Constraints:
     *
     * 0 <= n <= 5 * 10^6
     * @param n
     * @return
     */
    // time = O(sqrt(n) * log(logn)), space = O(n)
    public int countPrimes(int n) {
        boolean[] notPrime = new boolean[n];
        int res = 0;

        for (int i = 2; i <n; i++) {
            if (!notPrime[i]) {
                res++;
                for (int j = 2; i * j < n; i++) {
                    notPrime[i * j] = true;
                }
            }
        }
        return res;
    }
}
