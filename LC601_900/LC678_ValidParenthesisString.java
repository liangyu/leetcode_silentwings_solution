package LC601_900;

public class LC678_ValidParenthesisString {
    /**
     * Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.
     *
     * The following rules define a valid string:
     *
     * Any left parenthesis '(' must have a corresponding right parenthesis ')'.
     * Any right parenthesis ')' must have a corresponding left parenthesis '('.
     * Left parenthesis '(' must go before the corresponding right parenthesis ')'.
     * '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".
     *
     * Input: s = "()"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s[i] is '(', ')' or '*'.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public boolean checkValidString(String s) {
        // corner case
        if (s == null || s.length() == 0) return true;

        int countMax = 0; // max # of unmatched left parentheses, try to use * as ( if possible
        int countMin = 0; // min # of unmatched left parentheses, try to use * as ) if possible
        // [countMin, countMax]  中间是任意的
        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                countMax++;
                countMin++;
            }
            else if (ch == ')') {
                countMax--;
                countMin--;
            }
            else { // *
                countMax++;
                countMin--;
            }
            if (countMax < 0) return false;
            if (countMin < 0) { // 如果countMax > 0 but countMin < 0 => 前面一定出现过*
                countMin = 0; // 翻转一个*变成""空来挽救，但是countMax不用改变，因为*讨论的时候两种情况已经都各自count在内了。
            }
        }
        // [countMin, countMax] >= 0
        return countMin == 0; // 要让整个区间内出现等于0的可能，只能让countMin必须等于0
    }
}
/**
 * follow-up: what if * can only stand for ( or ), not ""
 * 要改动的地方就是：
 * if（countMin < 0) countMin += 2;
 * why?
 * countMin < 0 => it means countMin = -1, 我们错误地把一个*当成了右括号，造成目前右括号多出来一个
 * 同样做挽救：这个*就只能做左括号，也就回到了代码里的情况1，确定一定是左括号的情况下，countMax与countMin都++
 * 这样一来一去，把--变成++，差值 = 2，所以改动是countMin += 2
 * 而原题是我们可以把多出来的这一个*变成""来挽救，所以只需要恢复countMin = 0即可
 */