package LC901_1200;
import java.util.*;
public class LC921_MinimumAddtoMakeParenthesesValid {
    /**
     * Given a string s of '(' and ')' parentheses, we add the minimum number of parentheses ( '(' or ')', and in any
     * positions ) so that the resulting parentheses string is valid.
     *
     * Formally, a parentheses string is valid if and only if:
     *
     * It is the empty string, or
     * It can be written as AB (A concatenated with B), where A and B are valid strings, or
     * It can be written as (A), where A is a valid string.
     * Given a parentheses string, return the minimum number of parentheses we must add to make the resulting string
     * valid.
     *
     * Input: s = "())"
     * Output: 1
     *
     * Note:
     *
     * s.length <= 1000
     * s only consists of '(' and ')' characters.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int minAddToMakeValid(String s) {
        int count = 0, res = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') count++;
            else count--;
            if (count < 0) {
                res++;
                count = 0;
            }
        }
        res += count;
        return res;
    }
}
/**
 * 对于括号，无非2种做法：
 * 1. stack: ( -> enter stack, ) -> pop out of stack
 * 好处：针对每一个右括号，你可以找到与之匹配的最近的左括号 ((**))
 * 2. Greedy: 维护一个计数器， count -> the number of unmatched left parentheses (one pass) 当前未被匹配的左括号
 * 非常经典，要掌握！！！
 * (()))((
 * ((...    count = 2
 * (() ...  count = 1
 * (()) ... count = 0
 * (())) .. count = -1 -> 无论后面怎么操作，无法挽救右括号，必须出手做一次操作使之变得合法 => count = 0   + left
 * (()))( . count = 1
 * (()))((  count = 2 -> 不得不出手，最小出手次数 = 2 -> count = 0    + 2 right
 * follow-up: minimum deletion -> 答案跟这个一模一样！
 *
 */
