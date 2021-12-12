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
    // S1
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

    // S2: Eratosthenes
    // time = O(n(logn)(loglogn)), space = O(n)
    public int countPrimes2(int n) {
        int[] q = new int[n + 1];
        for (int i = 2; i * i <= n; i++) {
            if (q[i] == 0) {
                int j = i * 2;
                while (j < n) {
                    q[j] = 1;
                    j += i;
                }
            }
        }
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (q[i] == 0) count++;
        }
        return count;
    }
}
