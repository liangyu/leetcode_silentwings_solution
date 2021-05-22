package LC601_900;
import java.util.*;
public class LC828_CountUniqueCharactersofAllSubstringsofaGivenString {
    /**
     * Let's define a function countUniqueChars(s) that returns the number of unique characters on s, for example if
     * s = "LEETCODE" then "L", "T","C","O","D" are the unique characters since they appear only once in s,
     * therefore countUniqueChars(s) = 5.
     *
     * On this problem given a string s we need to return the sum of countUniqueChars(t) where t is a substring of s.
     * Notice that some substrings can be repeated so on this case you have to count the repeated ones too.
     *
     * Since the answer can be very large, return the answer modulo 10 ^ 9 + 7.
     *
     * Input: s = "ABC"
     * Output: 10
     * Explanation: All possible substrings are: "A","B","C","AB","BC" and "ABC".
     * Evey substring is composed with only unique letters.
     * Sum of lengths of all substring is 1 + 1 + 1 + 2 + 2 + 3 = 10
     *
     * Constraints:
     *
     * 0 <= s.length <= 10^4
     * s contain upper-case English letters only.
     *
     * @param s
     * @return
     */
    // S1：两边扩散法
    // time = O(n^2), space = O(1)
    public int uniqueLetterString(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        long res = 0;

        for (int i = 0; i < s.length(); i++) {
            int j = i - 1, k = i + 1;
            while (j >= 0) {
                if (s.charAt(j) == s.charAt(i)) break;
                j--;
            }
            while (k < s.length()) {
                if (s.charAt(k) == s.charAt(i)) break;
                k++;
            }
            res += (i - (j + 1) + 1) * ((k - 1) - i + 1);
        }
        return (int)(res % 1000000007);
    }

    // S2: int[]  最优解！！！
    // time = O(n), space = O(1)
    public int uniqueLetterString2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int[] lastIndex = new int[26];
        int[] secondLastIndex = new int[26];
        Arrays.fill(lastIndex, -1); // O(1)
        Arrays.fill(secondLastIndex, -1); // O(1)

        int len = s.length();
        long res = 0;

        for (int i = 0; i < len; i++) { // O(n)
            int idx = s.charAt(i) - 'A';
            res += (i - secondLastIndex[idx]) * (secondLastIndex[idx] - lastIndex[idx]);
            lastIndex[idx] = secondLastIndex[idx];
            secondLastIndex[idx] = i;
        }

        // post-processing the final case
        for (int i = 0; i < 26; i++) { // O(1)
            res += (len - 1 - secondLastIndex[i] + 1) * (secondLastIndex[i] - lastIndex[i]);
        }
        return (int)(res % 1000000007);
    }
}
