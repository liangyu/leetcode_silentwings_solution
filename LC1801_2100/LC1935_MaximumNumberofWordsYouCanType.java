package LC1801_2100;
import java.util.*;
public class LC1935_MaximumNumberofWordsYouCanType {
    /**
     * There is a malfunctioning keyboard where some letter keys do not work. All other keys on the keyboard work properly.
     *
     * Given a string text of words separated by a single space (no leading or trailing spaces) and a string
     * brokenLetters of all distinct letter keys that are broken, return the number of words in text you can fully type
     * using this keyboard.
     *
     * Input: text = "hello world", brokenLetters = "ad"
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= text.length <= 10^4
     * 0 <= brokenLetters.length <= 26
     * text consists of words separated by a single space without any leading or trailing spaces.
     * Each word only consists of lowercase English letters.
     * brokenLetters consists of distinct lowercase English letters.
     * @param text
     * @param brokenLetters
     * @return
     */
    // time = O(m + n * k), space = O(m)
    public int canBeTypedWords(String text, String brokenLetters) {
        HashSet<Character> set = new HashSet<>();
        for (char c : brokenLetters.toCharArray()) set.add(c); // O(m)

        int count = 0;
        String[] strs = text.split(" ");
        for (String s : strs) { // O(n)
            boolean flag = true;
            for (char c : s.toCharArray()) { // O(k)
                if (set.contains(c)) {
                    flag = false;
                    break;
                }
            }
            if (flag) count++;
        }
        return count;
    }

}
