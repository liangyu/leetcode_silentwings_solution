package LC301_600;

public class LC397_IntegerReplacement {
    /**
     * Given a positive integer n, you can apply one of the following operations:
     *
     * If n is even, replace n with n / 2.
     * If n is odd, replace n with either n + 1 or n - 1.
     * Return the minimum number of operations needed for n to become 1.
     *
     * Input: n = 8
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= n <= 2^31 - 1
     * @param n
     * @return
     */
    // time = O(logn), space = O(logn)
    public int integerReplacement(int n) {
        return dfs(n);
    }

    private int dfs(long n) { // 注意：这里得用long!!!否则会有越界溢出问题。
        // base case
        if (n == 1) return 0;

        if (n % 2 == 0) return dfs(n / 2) + 1;
        else return Math.min(dfs(n + 1), dfs(n - 1)) + 1;
    }
}
