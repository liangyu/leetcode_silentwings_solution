package LC301_600;
import java.util.*;
public class LC394_DecodeString {
    /**
     * Given an encoded string, return its decoded string.
     *
     * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated
     * exactly k times. Note that k is guaranteed to be a positive integer.
     *
     * You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.
     *
     * Furthermore, you may assume that the original data does not contain any digits and that digits are only for
     * those repeat numbers, k. For example, there won't be input like 3a or 2[4].
     *
     * Input: s = "3[a]2[bc]"
     * Output: "aaabcbc"
     *
     * Constraints:
     *
     * 1 <= s.length <= 30
     * s consists of lowercase English letters, digits, and square brackets '[]'.
     * s is guaranteed to be a valid input.
     * All the integers in s are in the range [1, 300].
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public String decodeString(String s) {
        Stack<Integer> numStack = new Stack<>();
        Stack<StringBuilder> strStack = new Stack<>();
        strStack.push(new StringBuilder());

        int val = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) val = val * 10 + c - '0';
            else if (c == '[') {
                numStack.push(val);
                val = 0;
                strStack.push(new StringBuilder());
            }  else if (c == ']') {
                int k = numStack.pop();
                String str = strStack.pop().toString();
                StringBuilder sb = new StringBuilder();
                while (k-- > 0) sb.append(str);
                strStack.peek().append(sb);
            } else strStack.peek().append(c);
        }
        return strStack.peek().toString();
    }
}
