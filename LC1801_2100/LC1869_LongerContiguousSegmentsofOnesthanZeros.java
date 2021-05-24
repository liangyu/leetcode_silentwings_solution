package LC1801_2100;
import java.util.*;
public class LC1869_LongerContiguousSegmentsofOnesthanZeros {
    /**
     * Given a binary string s, return true if the longest contiguous segment of 1s is strictly longer than the longest
     * contiguous segment of 0s in s. Return false otherwise.
     *
     * For example, in s = "110100010" the longest contiguous segment of 1s has length 2, and the longest contiguous
     * segment of 0s has length 3.
     * Note that if there are no 0s, then the longest contiguous segment of 0s is considered to have length 0. The same
     * applies if there are no 1s.
     *
     * Input: s = "1101"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s[i] is either '0' or '1'.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public boolean checkZeroOnes(String s) {
        // corner case
        if (s == null || s.length() == 0) return false;

        int n = s.length();
        int cnt0 = 0, cnt1 = 0, max0 = 0, max1 = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                cnt1 = 0;
                cnt0++;
            } else {
                cnt0 = 0;
                cnt1++;
            }
            max0 = Math.max(max0, cnt0);
            max1 = Math.max(max1, cnt1);
        }
        return max1 > max0;
    }
}
