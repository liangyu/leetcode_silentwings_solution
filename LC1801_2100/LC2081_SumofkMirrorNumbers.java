package LC1801_2100;
import java.util.*;
public class LC2081_SumofkMirrorNumbers {
    /**
     * A k-mirror number is a positive integer without leading zeros that reads the same both forward and backward in
     * base-10 as well as in base-k.
     *
     * For example, 9 is a 2-mirror number. The representation of 9 in base-10 and base-2 are 9 and 1001 respectively,
     * which read the same both forward and backward.
     * On the contrary, 4 is not a 2-mirror number. The representation of 4 in base-2 is 100, which does not read the
     * same both forward and backward.
     * Given the base k and the number n, return the sum of the n smallest k-mirror numbers.
     *
     * Input: k = 2, n = 5
     * Output: 25
     *
     * Input: k = 7, n = 17
     * Output: 20379000
     *
     * Constraints:
     *
     * 2 <= k <= 9
     * 1 <= n <= 30
     * @param k
     * @param n
     * @return
     */
    // time = O(10^5), space = O(1)
    public long kMirror(int k, int n) {
        int len = 1;
        List<Long> res = new ArrayList<>();
        while (true) {
            for (long i = (long)Math.pow(10, len - 1); i < (long)Math.pow(10, len); i++) {
                long a = getPalindrome(i, 0); // 0 - odd; 1 - even
                if (checkOK(a, k)) res.add(a);
                if (res.size() == n) return getSum(res);
            }
            for (long i = (long)Math.pow(10, len - 1); i < (long)Math.pow(10, len); i++) {
                long a = getPalindrome(i, 1); // 0 - odd; 1 - even
                if (checkOK(a, k)) res.add(a);
                if (res.size() == n) return getSum(res);
            }
            len++;
        }
    }

    private long getPalindrome(long x, int flag) {
        long y = x;
        if (flag == 0) x /= 10;
        while (x > 0) {
            y = y * 10 + x % 10;
            x /= 10;
        }
        return y;
    }

    private boolean checkOK(long num, int k) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(num % k);
            num /= k;
        }
        String s = sb.toString();
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }

    private long getSum(List<Long> res) {
        long sum = 0;
        for (long x : res) sum += x;
        return sum;
    }
}
/**
 * 直接构造回文数
 * 2      4     3
 * xy => xyyx, xyx
 *  3       6       5
 * xyz => xyzzyx, xyzyx
 * 不停对k取余 => k位数
 * 先构造十进制回文，再check是否是k进制回文
 * 我们是否可以反过来做，遍历所有从小到大的k进制回文数，再查验是否是十进制回文数呢？
 * 因为k小于10，遍历k进制回文数的效率不及遍历十进制回文数的效率高。
 * k越小，在相同范围内，k进制的数字就越长，回文数概率就越高。
 * 比如十进制的两位数，只有11-99这9种回文数。但是对应的二进制表示却是从1010到1100011：
 * 期间有（部分）四位数的回文、（任意）五位数的回文、（任意）六位数的回文、（部分）七位数的回文。
 * 在需要满足既是k进制回文、又是10进制回文的前提下，我们遍历10进制回文数需要尝试的次数更少。
 * [11,22,33,...99] => 9
 * [1011,...,110011] => 二进制的回文数密度比十进制更大，还不如同样的数字范围check 9次效率更高 => 构造十进制回文去check k 进制回文
 */
