package LC1501_1800;
import java.util.*;
public class LC1554_StringsDifferbyOneCharacter {
    /**
     * Given a list of strings dict where all the strings are of the same length.
     *
     * Return True if there are 2 strings that only differ by 1 character in the same index, otherwise return False.
     *
     * Follow up: Could you solve this problem in O(n*m) where n is the length of dict and m is the length of each string.
     *
     * Input: dict = ["abcd","acbd", "aacd"]
     * Output: true
     *
     * Constraints:
     *
     * Number of characters in dict <= 10^5
     * dict[i].length == dict[j].length
     * dict[i] should be unique.
     * dict[i] contains only lowercase English letters.
     * @param dict
     * @return
     */
    // time = O(m * n), space = O(n)
    public boolean differByOne(String[] dict) {
        // corner case
        if (dict == null || dict.length == 0) return false;

        int n = dict.length, m = dict[0].length();
        long base = 26;
        long[] hash = new long[n];

        for (int i = 0; i < n; i++) {
            long h = 0;
            for (char ch : dict[i].toCharArray()) {
                h = h * base + ch - 'a';
            }
            hash[i] = h;
        }

        long power = 1;
        for (int j = m - 1; j >= 0; j--) {
            HashSet<Long> set = new HashSet<>();
            for (int i = 0; i < n; i++) {
                // hash[i]
                long hash_new = hash[i] - (dict[i].charAt(j) - 'a') * power;
                if (set.contains(hash_new)) return true;
                set.add(hash_new);
            }
            power *= base;
        }
        return false;
    }
}
/**
 * 两两配对来比较，一一对应起来判断 => time = O(n^2*m)
 * O(m*n) => O(n * 1e5)
 * {123,124,134,213}
 * {120,120,130,210}  把个位抹掉，前2个相同，说明这2个就是diff by 1 digit => 把string编码成数字
 * {103,104,104,203}
 * {023,024,034,013}
 * hash编码 => {0123,0214,0023}
 * O(m*n)
 * {0123,0214,0023} => {0120,0210,0020}  O(n)
 * {0103,0204,0003}  O(n)
 * (0023,0014,0023}  O(n)
 * => O(n*m)
 * 0123 -> 0*26^3+1*26^2+2*26^1+3*26^0
 * (0123)26 - 3 * 26^0
 * (0123)26 - 2 * 26^1
 */