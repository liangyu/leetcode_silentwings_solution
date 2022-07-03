package LC901_1200;
import java.util.*;
public class LC1047_RemoveAllAdjacentDuplicatesInString {
    /**
     * Given a string S of lowercase letters, a duplicate removal consists of choosing two adjacent and equal letters,
     * and removing them.
     *
     * We repeatedly make duplicate removals on S until we no longer can.
     *
     * Return the final string after all such duplicate removals have been made.  It is guaranteed the answer is unique.
     *
     * Input: "abbaca"
     * Output: "ca"
     *
     * Note:
     *
     * 1 <= S.length <= 20000
     * S consists only of English lowercase letters.
     * @param s
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public String removeDuplicates(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peek() == c) {
                while (!stack.isEmpty() && stack.peek() == c) stack.pop();
            } else stack.push(c);
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) sb.append(stack.pop());
        return sb.reverse().toString();
    }

    // S2:
    // time = O(n), space = O(n)
    public String removeDuplicates2(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (sb.length() > 0 && sb.charAt(sb.length() - 1) == c) sb.setLength(sb.length() - 1);
            else sb.append(c);
        }
        return sb.toString();
    }
}
/**
 * 数学归纳法
 * n = 1 成立
 * 设 k < n 均成立
 * k = n 时也成立
 */