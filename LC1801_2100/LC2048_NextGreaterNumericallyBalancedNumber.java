package LC1801_2100;

public class LC2048_NextGreaterNumericallyBalancedNumber {
    /**
     * An integer x is numerically balanced if for every digit d in the number x, there are exactly d occurrences of
     * that digit in x.
     *
     * Given an integer n, return the smallest numerically balanced number strictly greater than n.
     *
     * Input: n = 1
     * Output: 22
     *
     * Constraints:
     *
     * 0 <= n <= 10^6
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int nextBeautifulNumber(int n) {
        for (int i = n + 1; i <= Integer.MAX_VALUE; i++) {
            if (helper(i)) return i;
        }
        return -1;
    }

    private boolean helper(int num) {
        int[] count = new int[10];
        while (num > 0) {
            count[num % 10]++;
            num /= 10;
        }

        for (int i = 0; i < 10; i++) {
            if (count[0] != 0) return false;
            if (count[i] == 0) continue;
            if (count[i] != i) return false;
        }
        return true;
    }
}
