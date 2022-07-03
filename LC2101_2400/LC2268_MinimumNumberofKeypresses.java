package LC2101_2400;
import java.util.*;
public class LC2268_MinimumNumberofKeypresses {
    /**
     * You have a keypad with 9 buttons, numbered from 1 to 9, each mapped to lowercase English letters. You can choose
     * which characters each button is matched to as long as:
     *
     * All 26 lowercase English letters are mapped to.
     * Each character is mapped to by exactly 1 button.
     * Each button maps to at most 3 characters.
     * To type the first character matched to a button, you press the button once. To type the second character, you
     * press the button twice, and so on.
     *
     * Given a string s, return the minimum number of keypresses needed to type s using your keypad.
     *
     * Note that the characters mapped to by e
     *
     * Input: s = "apple"
     * Output: 5
     *
     * Input: s = "abcdefghijkl"
     * Output: 15
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists of lowercase English letters.
     * @param s
     * @return
     */
    // S1: sort
    // time = O(nlogn), space = O(n)
    public int minimumKeypresses(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) count[c - 'a']++;

        List<Integer> list = new ArrayList<>();
        for (int x : count) {
            if (x > 0) list.add(x);
        }

        Collections.sort(list, (o1, o2) -> o2 - o1);

        int res = 0;
        for (int i = 0; i < list.size(); i++) res += (i / 9 + 1) * list.get(i);
        return res;
    }

    // S2: sort
    public int minimumKeypresses2(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) count[c - 'a']++;

        Arrays.sort(count);
        int res = 0, one = 9, two = 9, three = 8;
        for (int i = 25; i >= 0; i--) {
            if (count[i] == 0) break;
            if (one > 0) {
                res += count[i];
                one--;
            } else if (two > 0) {
                res += 2 * count[i];
                two--;
            } else {
                res += 3 * count[i];
            }
        }
        return res;
    }
}
