package LC301_600;

public class LC342_PowerofFour {
    /**
     * Given an integer n, return true if it is a power of four. Otherwise, return false.
     *
     * An integer n is a power of four, if there exists an integer x such that n == 4^x.
     *
     * Input: n = 16
     * Output: true
     *
     * Constraints:
     *
     * -2^31 <= n <= 2^31 - 1
     * Follow up: Could you solve it without loops/recursion?
     * @param n
     * @return
     */
    // time = O(1), space = O(1)
    public boolean isPowerOfFour(int n) {
        if (n == 1) return true;
        if (n > 0 && (n & (n - 1)) == 0 && (n - 1) % 3 == 0) return true;
        return false;
    }
}
/**
 * 满足power of 4的条件是：1.大于零；2.是power of 2；3.num-1之后是3的倍数。
 * 其中满足power of 2的条件是：除了最高bit位是1，其余位都是0，所以 num & (num-1) ==0
 * 为什么num-1一定会是3的倍数呢？(4^k-1)=(2^k+1)(2^k-1)，
 * 其中2^k一定不能被3整除，余数可能是1，也可能是2，无论哪一种，(2^k+1)和(2^k-1)里必然有一个能被3整除。
 */