package LC2101_2400;

public class LC2217_FindPalindromeWithFixedLength {
    /**
     * Given an integer array queries and a positive integer intLength, return an array answer where answer[i] is either
     * the queries[i]th smallest positive palindrome of length intLength or -1 if no such palindrome exists.
     *
     * A palindrome is a number that reads the same backwards and forwards. Palindromes cannot have leading zeros.
     *
     * Input: queries = [1,2,3,4,5,90], intLength = 3
     * Output: [101,111,121,131,141,999]
     *
     * Constraints:
     *
     * 1 <= queries.length <= 5 * 10^4
     * 1 <= queries[i] <= 10^9
     * 1 <= intLength <= 15
     *
     * @param queries
     * @param intLength
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public long[] kthPalindrome(int[] queries, int intLength) {
        int n = queries.length;
        long[] res = new long[n];

        int type = (intLength % 2 == 0 ? 0 : 1);
        int k = (intLength + 1) / 2;
        long x = (long) Math.pow(10, k - 1);
        long total = (long) Math.pow(10, k) - x;

        for (int i = 0; i < n; i++) {
            if (queries[i] > total) res[i] = -1;
            else {
                long num = x + queries[i] - 1;
                res[i] = constructPalin(num, type);
            }
        }
        return res;
    }

    private long constructPalin(long x, int type) {
        long y = x;
        if (type == 1) x /= 10;
        while (x > 0) {
            y = y * 10 + x % 10;
            x /= 10;
        }
        return y;
    }

    // S2
    // time = O(n), space = O(1)
    public long[] kthPalindrome2(int[] queries, int intLength) {
        int n = queries.length, idx = 0;
        long[] res = new long[n];
        for (int k : queries) {
            if (intLength == 1) {
                if (k > 9) res[idx++] = -1;
                else res[idx++] = k;
            } else if (intLength % 2 == 0) {
                int m = intLength / 2;
                long a = getKth(m, k);
                if (a == -1) {
                    res[idx++] = -1;
                    continue;
                }
                long b = flip(a);
                res[idx++] = a * (long) Math.pow(10, m) + b;
            } else {
                int m = intLength / 2;
                long a = getKth(m + 1, k);
                if (a == -1) {
                    res[idx++] = -1;
                    continue;
                }
                long c = a % 10;
                a = a / 10;
                long b = flip(a);
                res[idx++] = a * (long) Math.pow(10, m + 1) + c * (long) Math.pow(10, m) + b;
            }
        }
        return res;
    }

    private long getKth(int m, int k) {
        if (k > 9 * Math.pow(10, m - 1)) return -1;
        return (long) Math.pow(10, m - 1) + k - 1;
    }

    private long flip(long a) {
        long res = 0;
        while (a > 0) {
            res = res * 10 + (a % 10);
            a /= 10;
        }
        return res;
    }
}
/**
 * m = intLength / 2
 * 100+(k-1)
 * xxx | xxx
 * 100   001
 * 101   101
 * 102   201
 * a = 1e(m-1)+(k-1)   ab = a * 1e(m) + b
 * 100 ~ 999 => 900 = 9*1e(m-1)
 * 1000 ~ 9999 => 9999 - 1000 + 1 = 9000
 * (xxx y) xxx
 * 1000
 * 1001
 * ...
 * a = 1e(m)+(k-1)
 * c = a % 10
 * a = a / 10
 * acb = a * 1e(m+1) + c * 1e(m) + b
 */