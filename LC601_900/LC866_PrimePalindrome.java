package LC601_900;

public class LC866_PrimePalindrome {
    /**
     * Find the smallest prime palindrome greater than or equal to n.
     *
     * Recall that a number is prime if it's only divisors are 1 and itself, and it is greater than 1.
     *
     * For example, 2,3,5,7,11 and 13 are primes.
     *
     * Recall that a number is a palindrome if it reads the same from left to right as it does from right to left.
     *
     * For example, 12321 is a palindrome.
     *
     * Input: n = 6
     * Output: 7
     *
     * Note:
     *
     * 1 <= n <= 10^8
     * The answer is guaranteed to exist and be less than 2 * 10^8.
     * @param n
     * @return
     */
    public int primePalindrome(int n) {
        if (n <= 2) return 2;
        if (n <= 3) return 3;
        if (n <= 5) return 5;
        if (n <= 7) return 7;
        if (n <= 11) return 11;

        String M = String.valueOf(n);
        int N = M.length() / 2;

        int a = (int)Math.pow(10, N);
        for (int i = a; i <= 20000; i++) {
            String s = String.valueOf(i);
            String s1 = s.substring(0, s.length() - 1);
            s1 = reverse(s1);
            s = s  + s1;

            int k = Integer.valueOf(s);
            if (k >= n && isPrime(k)) return k;
        }
        return -1;
    }

    private String reverse(String s) {
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }

    private boolean isPrime(int k) {
        if (k < 2) return false;
        if (k % 2 == 0) return k == 2;
        for (int i = 3; i * i <= k; i += 2) {
            if (k % i == 0) return false;
        }
        return true;
    }
}
/**
 * 又要质数，又要回文数，并没有什么共通的地方，没有什么巧妙的办法给求出来
 * x x x x x x
 * y y y z z z
 *
 * x x x x x
 * y y y z z
 *
 * 真正要枚举的只要10^4 => 可以接受
 * O(N) * (sqrt(N)) => O(10^6) 一般极限就是10^6
 * 找出比N大的数
 * 找有4位数的回文数
 * n = 10000
 * 10 => 1001
 * xyz -> xyzzyx 有偶数位的肯定不会是一个质数 => 肯定是11的倍数
 * x*1e5+x*1e0
 * y*1e4+y*1e1
 * z*1e3+z*1e2
 *
 * 10 => 101
 * 9999 => 99999999
 *
 * 100 => 10001
 * 20000 => 200000002
 */
