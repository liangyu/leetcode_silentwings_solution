package LC1201_1500;
import java.util.*;
public class LC1461_CheckIfaStringContainsAllBinaryCodesofSizeK {
    /**
     * Given a binary string s and an integer k.
     *
     * Return True if every binary code of length k is a substring of s. Otherwise, return False.
     *
     * Input: s = "00110110", k = 2
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 5 * 10^5
     * s consists of 0's and 1's only.
     * 1 <= k <= 20
     *
     * @param s
     * @param k
     * @return
     */
    // time = O(n * k), space = O(n * k)
    public boolean hasAllCodes(String s, int k) {
        // corner case
        if (s == null || s.length() == 0 || k <= 0) return false;

        HashSet<String> set = new HashSet<>();

        int left = 0, right = k;
        while (right <= s.length()) {
            set.add(s.substring(left++, right++));
        }
        return set.size() == Math.pow(2, k);
    }
}
