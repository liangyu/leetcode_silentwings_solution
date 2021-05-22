package LC601_900;
import java.util.*;
public class LC869_ReorderedPowerof2 {
    /**
     * Starting with a positive integer N, we reorder the digits in any order (including the original order) such
     * that the leading digit is not zero.
     *
     * Return true if and only if we can do this in a way such that the resulting number is a power of 2.
     *
     * Input: 46
     * Output: true
     *
     * Note:
     *
     * 1 <= N <= 10^9
     * @param N
     * @return
     */
    // time = O(1), space = O(1)
    public boolean reorderedPowerOf2(int N) {
        String n = String.valueOf(N);
        char[] chars = n.toCharArray();
        Arrays.sort(chars);
        for (int i = 0; i < 32; i++) {
            int X = (1 << i);
            String x = String.valueOf(X);
            char[] xs = x.toCharArray();
            Arrays.sort(xs);
            if (String.valueOf(xs).equals(String.valueOf(chars))) return true;
        }
        return false;
    }
}

/**
 * 2^N 总共就32个数，找出所有的，然后sort，看是否match
 * 找数目比较少的来进行遍历
 */
