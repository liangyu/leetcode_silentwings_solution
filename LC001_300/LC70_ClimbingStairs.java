package LC001_300;
import java.util.*;
public class LC70_ClimbingStairs {
    /**
     * You are climbing a staircase. It takes n steps to reach the top.
     *
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
     *
     * Input: n = 2
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 45
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int climbStairs(int n) {
        // corner case
        if (n == 1 || n == 2) return n;
        int lo = 1, hi = 2;
        int res = 0;
        for (int i = 3; i <= n; i++) {
            res = lo + hi;
            lo = hi;
            hi = res;
        }
        return res;
    }
}
