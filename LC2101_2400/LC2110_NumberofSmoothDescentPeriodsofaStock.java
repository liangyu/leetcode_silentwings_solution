package LC2101_2400;
import java.util.*;
public class LC2110_NumberofSmoothDescentPeriodsofaStock {
    /**
     * You are given an integer array prices representing the daily price history of a stock, where prices[i] is the
     * stock price on the ith day.
     *
     * A smooth descent period of a stock consists of one or more contiguous days such that the price on each day is
     * lower than the price on the preceding day by exactly 1. The first day of the period is exempted from this rule.
     *
     * Return the number of smooth descent periods.
     *
     * Input: prices = [3,2,1,4]
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= prices.length <= 10^5
     * 1 <= prices[i] <= 10^5
     * @param prices
     * @return
     */
    // S1: 最优解!
    // time = O(n), space = O(1)
    public long getDescentPeriods(int[] prices) {
        long res = 0;

        int prev = 10001, count = 0;
        for (int x : prices) {
            if (x == prev - 1) count++;
            else count = 1;
            prev = x;
            res += count;
        }
        return res;
    }

    // S2: monotonic stack
    // time = O(n), space = O(n)
    public long getDescentPeriods2(int[] prices) {
        Stack<Integer> stack = new Stack<>();

        long res = 0;
        for (int x : prices) {
            if (stack.isEmpty() || stack.peek() - 1 == x) stack.push(x);
            else {
                res += helper(stack.size());
                stack.clear();
                stack.push(x);
            }
        }
        return res + helper(stack.size());
    }

    private long helper(int n) {
        return (long)(1 + n) * n / 2;
    }
}
