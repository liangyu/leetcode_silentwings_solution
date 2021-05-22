package LC001_300;
import java.util.*;
public class LC258_AddDigits {
    /**
     * Given an integer num, repeatedly add all its digits until the result has only one digit, and return it.
     *
     * Input: num = 38
     * Output: 2
     * Explanation: The process is
     * 38 --> 3 + 8 --> 11
     * 11 --> 1 + 1 --> 2
     * Since 2 has only one digit, return it.
     *
     * Constraints:
     *
     * 0 <= num <= 2^31 - 1
     *
     *
     * Follow up: Could you do it without any loop/recursion in O(1) runtime?
     * @param num
     * @return
     */
    // S1: recursion
    // time = O(1), space = O(1)
    public int addDigits(int num) {
        while (true) {
            num = helper(num);
            if (num / 10 == 0) break;
        }
        return num;
    }

    private int helper(int num) {
        int res = 0;
        while (num > 0) {
            res += num % 10;
            num /= 10;
        }
        return res;
    }

    // S2: Math
    // time = O(1), space = O(1)
    public int addDigits2(int num) {
        return (num - 1) % 9 + 1;
    }
}
