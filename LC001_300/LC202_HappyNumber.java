package LC001_300;
import java.util.*;
public class LC202_HappyNumber {
    /**
     * Write an algorithm to determine if a number n is happy.
     *
     * A happy number is a number defined by the following process:
     *
     * Starting with any positive integer, replace the number by the sum of the squares of its digits.
     * Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does
     * not include 1.
     * Those numbers for which this process ends in 1 are happy.
     * Return true if n is a happy number, and false if not.
     *
     * Input: n = 19
     * Output: true
     *
     * Constraints:
     *
     * 1 <= n <= 2^31 - 1
     * @param n
     * @return
     */
    // time = O(logn), space = O(1)
    public boolean isHappy(int n) {
        HashSet<Integer> set = new HashSet<>();
        int sum = 0, remain = 0;

        while (set.add(n)) {
            sum = 0;
            while (n > 0) {
                remain = n % 10;
                sum += remain * remain;
                n /= 10;
            }
            if (sum == 1) return true;
            else n = sum;
        }
        return false;
    }
}
