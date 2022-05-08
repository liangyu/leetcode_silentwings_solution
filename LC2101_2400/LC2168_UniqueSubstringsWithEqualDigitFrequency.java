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
    // S1: rolling hash
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

    // S1.2: rolling hash
    // time = O(n^2), space = O(n^2)
    public int equalDigitFrequency2(String s) {
        int n = s.length();
        long M = (long)(1e9 + 7);
        HashSet<Long> set = new HashSet<>();

        for (int i = 0; i < n; i++) {
            long hash = 0;
            int[] count = new int[10];
            for (int j = i; j < n; j++) {
                hash = hash * 11 + (s.charAt(j) - '0' + 1);
                hash %= M;
                count[s.charAt(j) - '0']++;

                boolean flag = true;
                int freq = -1;
                for (int k = 0; k < 10; k++) {
                    if (count[k] == 0) continue;
                    if (freq == -1) freq = count[k];
                    else if (freq != count[k]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) set.add(hash);
            }
        }
        return set.size();
    }
}
/**
 * 1 <= s.length <= 1000  => O(n^2) 可以枚举所有substring
 * 暴力解就可以了
 * unique
 * 放到集合里？最多检验n^2个substring => 数据量O(n^3) 空间复杂度太高
 * abcdef => 3480341
 * 1203849 => 12038......49 =>
 * 这里数字特别长
 * 1e9 + 7
 * 10 -> 11进制
 * "0129" => 1*11^3+2*11^2+3*11^1+10*11^0
 * "129" => 2*11^2+3*11^1+10*11^0
 * 统一提升1位
 */
