package LC001_300;
import java.util.*;
public class LC58_LengthofLastWord {
    /**
     * Given a string s consists of some words separated by spaces, return the length of the last word in the string.
     * If the last word does not exist, return 0.
     *
     * A word is a maximal substring consisting of non-space characters only.
     *
     * Input: s = "Hello World"
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^4
     * s consists of only English letters and spaces ' '.
     * @param s
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public int lengthOfLastWord(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        String[] strs = s.split(" ");
        return strs.length == 0 ? 0 : strs[strs.length - 1].length();
    }

    // S2: 从后往前遍历直到看见第一个单词后的空格为止！(最优解！！！)
    // time = O(n), space = O(1)
    public int lengthOfLastWord2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int i = s.length() - 1, res = 0;
        while (i >= 0) {
            if (s.charAt(i--) != ' ') res++;
            else break;
        }
        return res;
    }
}
