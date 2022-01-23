package LC601_900;
import java.util.*;
public class LC828_CountUniqueCharactersofAllSubstringsofaGivenString {
    /**
     * Let's define a function countUniqueChars(s) that returns the number of unique characters on s.
     *
     * For example if s = "LEETCODE" then "L", "T", "C", "O", "D" are the unique characters since they appear only once
     * in s, therefore countUniqueChars(s) = 5.
     *
     * Given a string s, return the sum of countUniqueChars(t) where t is a substring of s.
     *
     * Notice that some substrings can be repeated so in this case you have to count the repeated ones too.
     *
     * Input: s = "ABC"
     * Output: 10
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists of uppercase English letters only.
     *
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int uniqueLetterString(String s) {
        int n = s.length(), count = 0, res = 0;
        int[] lastCount = new int[26];
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1); // 注意，一开始要初始化为-1， 这样从0开始的substring的长度才是正确的!

        for (int i = 0; i < n; i++) {
            int idx = s.charAt(i) - 'A';
            int currentCount = i - lastSeen[idx];
            count = count + currentCount - lastCount[idx];
            lastCount[idx] = currentCount;
            lastSeen[idx] = i;
            res += count;
        }
        return res;
    }
}
/**
 * "ABCBD"
 * cur[i]: the sum of Uniq() for all substrings whose last char is S.charAt(i).
 * The final result is the sum of all cur[i].
 * cur[1] = 3
 *   B
 * A B
 * cur[2] = 3 + 3
 *     | C
 *   B | C
 * A B | C
 * cur[3] = -2 + 6 + 2 = 6 => count - lastCount[idx] + currentCount = 6 - 2 + 2 = 6
 *           B
 *     | C | B
 *   B | C | B
 * A B | C | B
 */
