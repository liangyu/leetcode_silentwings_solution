package LC1201_1500;

public class LC1201_UglyNumberIII {
    /**
     * An ugly number is a positive integer that is divisible by a, b, or c.
     *
     * Given four integers n, a, b, and c, return the nth ugly number.
     *
     * Input: n = 3, a = 2, b = 3, c = 5
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= n, a, b, c <= 10^9
     * 1 <= a * b * c <= 10^18
     * It is guaranteed that the result will be in range [1, 2 * 10^9].
     * @param n
     * @param a
     * @param b
     * @param c
     * @return
     */
    // time = O(log(INT_MAX)), space = O(1)
    public int nthUglyNumber(int n, int a, int b, int c) {
        long left = 1, right = Integer.MAX_VALUE;
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (count(mid, a, b, c) < n) left = mid + 1;
            else right = mid;
        }
        return (int) left;
    }

    private long count(long t, long a, long b, long c) {
        return t / a + t / b + t / c - t / lcm(a, b) - t / lcm(a, c) - t / lcm(b, c) + t / lcm(lcm(a, b), c);
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    private long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}
/**
 * 容斥原理
 * 1-100：2,3,5 => 100/2+100/3+100/5-100/6-100/15-100/10+100/30
 * 1-M: a,b,c => n
 * 最小M是多少，可以满足a,b,c整除为n个
 * 猜值search by value
 * 猜得到底是偏大还是偏小
 * => 找一个M => count(M) vs. n
 * 60 => k
 * 61 => k
 * 就算== n，也无法判断，要保守一点，right = mid。要找恰好第n个
 * lcm = a * b / gcd(a, b)
 * 6, 8 => 6 * 8 / 2 = 24
 */
