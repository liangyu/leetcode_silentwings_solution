package LC301_600;

public class LC479_LargestPalindromeProduct {
    /**
     * Given an integer n, return the largest palindromic integer that can be represented as the product of two n-digits
     * integers. Since the answer can be very large, return it modulo 1337.
     *
     * Input: n = 2
     * Output: 987
     *
     * Constraints:
     *
     * 1 <= n <= 8
     * @param n
     * @return
     */
    // time = O(1), space = O(1)
    public int largestPalindrome(int n) {
        if (n == 1) return 9;

        long low = (long)Math.pow(10, n - 1);
        long high = (long)Math.pow(10, n) - 1;

        for (long i = high; i >= low; i--) {
            long p = createPalindrome(i);
            for (int d = (int)high; d >= Math.sqrt(p); d--) {  // 这里注意d >= Math.sqrt(p)中，d默认转化为double去比较才精确！
                if (p % d == 0 && p / d >= low) return (int)(p % 1337);
            }
        }
        return -1;
    }

    private long createPalindrome(long i) {
        String s = String.valueOf(i);
        StringBuilder sb = new StringBuilder(s);
        return Long.valueOf(s + sb.reverse().toString());
    }
}
/**
 * n = 1e8
 * 暴力枚举2n回文整数 2n -> n  sqrt(n) 从大到小枚举
 * n * sqrt(n)
 * n * n
 * 碰到回文数可以考虑暴力一下
 * 枚举一半就可以了
 * 100000
 * 999999
 */
