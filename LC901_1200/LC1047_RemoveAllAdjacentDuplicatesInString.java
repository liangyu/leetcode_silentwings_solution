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
     * @param S
     * @return
     */
    // time = O(n), space = O(n)
    public String removeDuplicates(String S) {
        // corner case
        if (S == null || S.length() == 0) return "";

        Stack<Character> stack = new Stack<>();
        for (char c : S.toCharArray()) {
            if (stack.isEmpty() || stack.peek() != c) stack.push(c);
            else stack.pop();
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) sb.append(stack.pop());
        return sb.reverse().toString();
    }
}
