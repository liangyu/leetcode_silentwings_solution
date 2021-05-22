package LC901_1200;
import java.util.*;
public class LC1165_SingleRowKeyboard {
    /**
     * Given a string keyboard of length 26 indicating the layout of the keyboard (indexed from 0 to 25), initially
     * your finger is at index 0. To type a character, you have to move your finger to the index of the desired
     * character. The time taken to move your finger from index i to index j is |i - j|.
     *
     * You want to type a string word. Write a function to calculate how much time it takes to type it with one finger.
     *
     * Input: keyboard = "abcdefghijklmnopqrstuvwxyz", word = "cba"
     * Output: 4
     *
     * Constraints:
     *
     * keyboard.length == 26
     * keyboard contains each English lowercase letter exactly once in some order.
     * 1 <= word.length <= 10^4
     * word[i] is an English lowercase letter.
     *
     * @param keyboard
     * @param word
     * @return
     */
    // time = O(n), space = O(1)
    public int calculateTime(String keyboard, String word) {
        // corner case
        if (keyboard == null || keyboard.length() == 0 || word == null || word.length() == 0) return 0;

        int[] ch = new int[26];

        for (int i = 0; i < keyboard.length(); i++) { // time = O(26) = O(1)
            char c = keyboard.charAt(i);
            ch[c - 'a'] = i;
        }

        int start = 0, res = 0;
        for (int i = 0; i < word.length(); i++) { // O(n)
            char c = word.charAt(i);
            res += Math.abs(ch[c - 'a'] - start);
            start = ch[c - 'a'];
        }
        return res;
    }
}
