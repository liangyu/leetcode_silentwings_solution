package LC901_1200;
import java.util.*;
public class LC1056_ConfusingNumber {
    /**
     * A confusing number is a number that when rotated 180 degrees becomes a different number with each digit valid.
     *
     * We can rotate digits of a number by 180 degrees to form new digits.
     *
     * When 0, 1, 6, 8, and 9 are rotated 180 degrees, they become 0, 1, 9, 8, and 6 respectively.
     * When 2, 3, 4, 5, and 7 are rotated 180 degrees, they become invalid.
     * Note that after rotating a number, we can ignore leading zeros.
     *
     * For example, after rotating 8000, we have 0008 which is considered as just 8.
     * Given an integer n, return true if it is a confusing number, or false otherwise.
     *
     * Input: n = 6
     * Output: true
     *
     * Constraints:
     *
     * 0 <= n <= 10^9
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public boolean confusingNumber(int n) {
        // corner case
        if (n == 0) return false;

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        map.put(1, 1);
        map.put(6, 9);
        map.put(8, 8);
        map.put(9, 6);

        int cur = 0, temp = n;
        while (n > 0) {
            int r = n % 10;
            if (!map.containsKey(r)) return false;
            cur = cur * 10 + map.get(r);
            n /= 10;
        }
        return cur != temp;
    }
}
/**
 * Reverse each digit with their corresponding new digit if an invalid digit is found the return -1.
 * After reversing the digits just compare the reversed number with the original number.
 */
