package LC301_600;
import java.util.*;
public class LC507_PerfectNumber {
    /**
     * A perfect number is a positive integer that is equal to the sum of its positive divisors, excluding the number
     * itself. A divisor of an integer x is an integer that can divide x evenly.
     *
     * Given an integer n, return true if n is a perfect number, otherwise return false.
     *
     * Input: num = 28
     * Output: true
     *
     * Constraints:
     *
     * 1 <= num <= 10^8
     * @param num
     * @return
     */
    // time = O(n^(1/2)), space = O(1)
    public boolean checkPerfectNumber(int num) {
        int sum = 1;
        for (int i = 2; i * i < num; i++) {
            if (num % i == 0) sum += i + num / i;
        }
        return sum == num && num != 1;
    }
}
