package LC901_1200;
import java.util.*;
public class LC906_SuperPalindromes {
    /**
     * Let's say a positive integer is a super-palindrome if it is a palindrome, and it is also the square of a
     * palindrome.
     *
     * Given two positive integers left and right represented as strings, return the number of super-palindromes
     * integers in the inclusive range [left, right].
     *
     * Input: left = "4", right = "1000"
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= left.length, right.length <= 18
     * left and right consist of only digits.
     * left and right cannot have leading zeros.
     * left and right represent integers in the range [1, 10^18].
     * left is less than or equal to right.
     * @param left
     * @param right
     * @return
     */
    // time = O(w^(1/4) * logw), space = O(logw)   w: 10^18
    // time complexity O(W^(1/4)∗logW). W^(1/4) is the magic number k. There are two loops from 1 to k. In each loop,
    // concatenating the k and k' takes O(len(k)) = O(log(k)) = O(log(W^(1/4)) = O(logW),
    // and checking if square value is palindrome takes O(len(W)) = O(logW). the base is 10;
    //Therefore, the time complexity is O(W^(1/4) * 2(logW)) = O(W^(1/4)∗logW).
    public int superpalindromesInRange(String left, String right) {
        long a = Long.valueOf(left), b = Long.valueOf(right);

        int start = (int)Math.pow(10, left.length() / 4 - 1);
        int end = (int)Math.pow(10, right.length() / 4 + 1);

        int count = 0;
        for (int i = start; i <= end; i++) {
            for (int j = 0; j <= 1; j++) {
                long palin = constructPalin(i, j); // j: type
                long superPalin = palin * palin;
                if (superPalin >= a && superPalin <= b && isPalin(superPalin)) {
                    count++;
                }
            }
        }
        return count;
    }

    private long constructPalin(int i, int j) {
        long y = i;
        // 构造奇数位的回文数
        if (j == 1) i /= 10;
        while (i > 0) {
            y = y * 10 + i % 10;
            i /= 10;
        }
        return y;
    }

    private boolean isPalin(long x) {
        String s = String.valueOf(x);
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i++) != s.charAt(j--)) return false;
        }
        return true;
    }
}
/**
 * refer: LC866 prime palindrome -> 先遍历比较稀疏的 -> 先遍历回文数,因为回文数比较好构造
 * 1-100 -> 1-10000 => 10^8
 *         palindrome superpalindrome => 转化成string，看是否左右对称即可
 * 1e5 -> 1e9 -> 1e18   => 1000 0 0001
 * 遍历他的平方根的前一半，撬动它的平方根，数字扩大一倍，再回文一下，再扩大一倍
 * 尽量用小杠杆撬动大杠杆
 */