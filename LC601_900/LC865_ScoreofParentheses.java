package LC601_900;
import java.util.*;
public class LC865_ScoreofParentheses {
    /**
     * Given a balanced parentheses string S, compute the score of the string based on the following rule:
     *
     * () has score 1
     * AB has score A + B, where A and B are balanced parentheses strings.
     * (A) has score 2 * A, where A is a balanced parentheses string.
     *
     * Input: "(()(()))"
     * Output: 6
     *
     * Note:
     *
     * S is a balanced parentheses string, containing only ( and ).
     * 2 <= S.length <= 50
     *
     * @param S
     * @return
     */
    // time = O(n), space = O(1)
    public int scoreOfParentheses(String S) {
        // corner case
        if (S == null || S.length() == 0) return 0;

        int bal = 0, res = 0;
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == '(') bal++;
            else {
                bal--;
                if (S.charAt(i - 1) == '(') res += 1 << bal;
            }
        }
        return res;
    }
}
