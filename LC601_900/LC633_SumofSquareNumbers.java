package LC601_900;

public class LC633_SumofSquareNumbers {
    /**
     * Given a non-negative integer c, decide whether there're two integers a and b such that a^2 + b^2 = c.
     *
     * Constraints:
     *
     * 0 <= c <= 2^31 - 1
     * @param c
     * @return
     */
    // S1: BS
    // time = O(c^(1/2) * logc), space = O(logc)
    public boolean judgeSquareSum(int c) {
        for (long i = 0; i * i <= c; i++) { // 注意：i * i <= c而不是 < c，可以出现=的情况，比如i == c, j == 0
            long j = (long)c - i * i;
            if (helper(0, j, j)) return true;
        }
        return false;
    }

    private boolean helper(long left, long right, long target) {
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (mid * mid == target) return true;
            if (mid * mid < target) left = mid + 1;
            else right = mid;
        }
        return left * left == target;
    }
}
