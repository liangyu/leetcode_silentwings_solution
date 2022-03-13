package LC301_600;

public class LC338_CountingBits {
    /**
     * Given an integer n, return an array ans of length n + 1 such that for each i (0 <= i <= n), ans[i] is the number
     * of 1's in the binary representation of i.
     *
     * Input: n = 2
     * Output: [0,1,1]
     *
     * Constraints:
     *
     * 0 <= n <= 10^5
     *
     *
     * Follow up:
     *
     * It is very easy to come up with a solution with a runtime of O(n log n). Can you do it in linear time O(n) and
     * possibly in a single pass?
     * Can you do it without using any built-in function (i.e., like __builtin_popcount in C++)?
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int[] countBits(int n) {
        int[] res = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            res[i] = i % 2 == 0 ? res[i / 2] : res[i / 2] + 1;
        }
        return res;
    }
}
