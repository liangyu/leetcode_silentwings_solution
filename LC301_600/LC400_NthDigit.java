package LC301_600;

public class LC400_NthDigit {
    /**
     * Given an integer n, return the nth digit of the infinite integer sequence [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...].
     *
     * Input: n = 3
     * Output: 3
     *
     * Input: n = 11
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= n <= 2^31 - 1
     * @param n
     * @return
     */
    // S1: Math
    // time = O(log10(n)), space = O(1)
    public int findNthDigit(int n) {
        int k = 1;
        while (true) {
            long num = countK(k);
            if (n > num) {
                n -= num;
                k++;
            } else if (n <= num) break;
        }

        // 第i个数第j个digit
        int i = n / k;
        int j = n % k;

        if (j != 0) {
            int base = (int) Math.pow(10, k - 1);
            String num = String.valueOf(base + i);
            return num.charAt(j - 1) - '0';
        } else {
            int base = (int) Math.pow(10, k - 1);
            String num = String.valueOf(base + i - 1);
            return num.charAt(num.length() - 1) - '0';
        }
    }

    private long countK(int k) {
        return (long)(Math.pow(10, k) - Math.pow(10, k - 1)) * k;
    }

    // S2: Math
    // time = O(log10(n)), space = O(1)
    public int findNthDigit2(int n) {
        long k = 1, t = 9, s = 1;
        while (n > k * t) {
            n -= k * t;
            k++;
            t *= 10;
            s *= 10;
        }
        // s += (int) Math.ceil(n * 1.0 / k) - 1;
        if (n % k == 0) { // k = 2, n = 4 -> 10 11 12 取的是11中的1，这时 n/k = 2 取的是idx = n / k - 1 = 1里的最后一位1，所以是k-1
            s += n / k - 1;
            n = (int) k;
        } else { // k = 2, n = 5 -> 10 11 12 取的是12中的1，这时 n/k = 2 取的是idx = n / k = 2 里的 n % k = 1 里的第0位 1-1
            s += n / k;
            n = (int)(n % k);
        }
        return String.valueOf(s).charAt(n - 1) - '0';
    }
}
/**
 * 1 ~ 9    9个数     9 x 1
 * 10 ~ 99  90个数    90 x 2
 * 100~999  900个数   900 x 3
 * 1. n位数
 * n = 200 -> n-9=191 > 180 -> n-180 = 11 < 2700 -> 3位数
 * 最多只会计算10次
 * 2. k位数的第几个数
 * 1~k
 * k+1~2k
 * 2k+1~3k
 * ...
 * [n/k]
 * 3. n是当前这个数的第几位 n % k == 0 ? k : n % k
 */