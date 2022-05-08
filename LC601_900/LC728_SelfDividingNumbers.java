package LC601_900;
import java.util.*;
public class LC728_SelfDividingNumbers {
    /**
     * A self-dividing number is a number that is divisible by every digit it contains.
     *
     * For example, 128 is a self-dividing number because 128 % 1 == 0, 128 % 2 == 0, and 128 % 8 == 0.
     * A self-dividing number is not allowed to contain the digit zero.
     *
     * Given two integers left and right, return a list of all the self-dividing numbers in the range [left, right].
     *
     * Input: left = 1, right = 22
     * Output: [1,2,3,4,5,6,7,8,9,11,12,15,22]
     *
     * Constraints:
     *
     * 1 <= left <= right <= 10^4
     * @param left
     * @param right
     * @return
     */
    // time = O(n), space = O(1)
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> res = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (helper(i)) res.add(i);
        }
        return res;
    }

    private boolean helper(int num) {
        int t = num;
        while (t > 0) {
            int digit = t % 10;
            if (digit == 0 || num % digit != 0) return false;
            t /= 10;
        }
        return true;
    }
}
