package LC901_1200;
import java.util.*;
public class LC917_ReverseOnlyLetters {
    /**
     * Given a string s, reverse the string according to the following rules:
     *
     * All the characters that are not English letters remain in the same position.
     * All the English letters (lowercase or uppercase) should be reversed.
     * Return s after reversing it.
     *
     * Input: s = "ab-cd"
     * Output: "dc-ba"
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s consists of characters with ASCII values in the range [33, 122].
     * s does not contain '\"' or '\\'.
     * @param s
     * @return
     */
    // S1: TreeMap
    // time = O(nlogn), space = O(n)
    public String reverseOnlyLetters(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        StringBuilder sb = new StringBuilder();
        TreeMap<Integer, Character> map = new TreeMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) sb.append(c);
            else map.put(i, c);
        }

        sb.reverse();
        for (int key : map.keySet()) {
            if (key < sb.length()) sb.insert(key, map.get(key));
            else sb.append(map.get(key));
        }
        return sb.toString();
    }

    // S2: stack
    // time = O(n), space = O(n)
    public String reverseOnlyLetters2(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) stack.push(c);
        }

        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) sb.append(stack.pop());
            else sb.append(c);
        }
        return sb.toString();
    }

    // S3: two pointers
    // time = O(n), space = O(n)
    public String reverseOnlyLetters3(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        StringBuilder sb = new StringBuilder();
        int n = s.length(), j = n - 1;

        for (int i = 0; i < n; i++) {
            if (Character.isLetter(s.charAt(i))) {
                while (!Character.isLetter(s.charAt(j))) j--;
                sb.append(s.charAt(j--));
            } else  sb.append(s.charAt(i));
        }
        return sb.toString();
    }
}
