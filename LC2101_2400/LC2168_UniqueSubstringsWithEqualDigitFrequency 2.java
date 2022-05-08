package LC2101_2400;
import java.util.*;
public class LC2168_UniqueSubstringsWithEqualDigitFrequency {
    /**
     * Given a digit string s, return the number of unique substrings of s where every digit appears the same number of
     * times.
     *
     * Input: s = "1212"
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s consists of digits.
     * @param s
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public int equalDigitFrequency(String s) {
        int n = s.length();
        int M = (int)(1e9 + 7);
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int hash = 0, count = 0, time = 0;
            int[] freq = new int[10];
            for (int j = i; j < n; j++) {
                int digit = s.charAt(j) - '0';
                freq[digit]++;
                hash = (11 * hash + digit + 1) % M;
                count += freq[digit] == 1 ? 1 : 0;
                time = Math.max(time, freq[digit]);
                if (count * time == j - i + 1) set.add(hash);
            }
        }
        return set.size();
    }
}
