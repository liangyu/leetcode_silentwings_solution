package LC601_900;

public class LC829_ConsecutiveNumbersSum {
    /**
     * Given an integer n, return the number of ways you can write n as the sum of consecutive positive integers.
     *
     * Input: n = 5
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 10^9
     * @param n
     * @return
     */
    // time = O(n^(1/2)), space = O(1)
    public int consecutiveNumbersSum(int n) {
        n *= 2;
        int res = 0;
        for (int b = 1; b * b <= n; b++) {
            if (n % b == 0) {
                if ((n / b - b + 1) % 2 == 0) res++;
            }
        }
        return res;
    }
}
/**
 * 首项为a，项数为b
 * a, a+1, a+2,...a+b-1
 * (a+a+b-1)*b / 2 = N
 * 2a + b - 1 = 2N / b
 * 2a = 2N/b - (b-1) > 0 => 2N/b > b - 1 => 2N/b >= b
 * b是2N较小的约数
 * 看一下2N/b - (b-1)是否能被2整除 => 能否解出a，是否偶数
 */