package LC1801_2100;
import java.util.*;
public class LC1952_ThreeDivisors {
    /**
     * Given an integer n, return true if n has exactly three positive divisors. Otherwise, return false.
     *
     * An integer m is a divisor of n if there exists an integer k such that n = k * m.
     *
     * Input: n = 2
     * Output: false
     *
     * Constraints:
     *
     * 1 <= n <= 10^4
     * @param n
     * @return
     */
    // S1: HashSet
    // time = O(n), space = O(n)
    public boolean isThree(int n) {
        HashSet<Integer> set = new HashSet<>();

        for (int i = 1; i <= n; i++) {
            if (n % i == 0) set.add(i);
        }
        return set.size() == 3;
    }

    // S2: Math -> prime
    // time = O(n^(1/4)), space = O(1)
    public boolean isThree2(int n) {
        if (n < 4) return false;
        int root = (int)Math.sqrt(n);
        if (root * root < n) return false;
        for (int i = 2; i <= Math.sqrt(root); i++) {
            if (root % i == 0) return false;
        }
        return true;
    }
}
/**
 * the numbers having exact 3 divisors are those which are a perfect square of a prime number.
 * So the task is to find :
 * i. If n is a perfect square
 * ii. If the square root of n is a prime number
 * Also I am iterating to n.pow(1/4) times.
 */
