package LC301_600;

public class LC441_ArrangingCoins {
    /**
     * You have n coins and you want to build a staircase with these coins. The staircase consists of k rows where the
     * ith row has exactly i coins. The last row of the staircase may be incomplete.
     *
     * Given the integer n, return the number of complete rows of the staircase you will build.
     *
     * Input: n = 5
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 2^31 - 1
     * @param n
     * @return
     */
    // time = O(logn), space = O(1)
    public int arrangeCoins(int n) {
        int left = 1, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (helper(mid) < n) left = mid + 1;
            else right = mid;
        }
        return helper(left) == n ? left : left - 1;
    }

    private long helper(int t) {
        return (1 + (long) t) * t / 2;
    }
}
