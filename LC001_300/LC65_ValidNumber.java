package LC001_300;
import java.util.*;
public class LC65_ValidNumber {
    /**
     * A valid number can be split up into these components (in order):
     *
     * A decimal number or an integer.
     * (Optional) An 'e' or 'E', followed by an integer.
     * A decimal number can be split up into these components (in order):
     *
     * (Optional) A sign character (either '+' or '-').
     * One of the following formats:
     * At least one digit, followed by a dot '.'.
     * At least one digit, followed by a dot '.', followed by at least one digit.
     * A dot '.', followed by at least one digit.
     * An integer can be split up into these components (in order):
     *
     * (Optional) A sign character (either '+' or '-').
     * At least one digit.
     *
     * @param s
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public boolean isNumber(String s) {
        // corner case
        if (s == null || s.length() == 0) return true;

        boolean numberSeen = false, eSeen = false, numberAfterE = false, pointSeen = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                numberSeen = true;
                numberAfterE = true;
            } else if (c == '.') {
                if (eSeen || pointSeen) return false;
                pointSeen = true;
            } else if (c == 'e' || c == 'E') {
                if (eSeen || !numberSeen) return false;
                eSeen = true;
                numberAfterE = false;
            } else if (c == '+' || c == '-') {
                if (i != 0 && s.charAt(i - 1) != 'e' && s.charAt(i - 1) != 'E') return false;
            } else return false;
        }
        return numberSeen && numberAfterE;
    }

    // S2
    // time = O(n), space = O(1)
    public boolean isNumber2(String s) {
        // corner case
        if (s == null || s.length() == 0) return false;

        // Step 1: trim the white space
        s = s.trim(); // delete white spaces at head and tail
        if (s.equals("")) return false;

        // Step 2: count e
        int countE = 0, posE = 0;
        for (int i = 0; i < s.length(); i++) {  // O(n)
            if (s.charAt(i) == 'e' || s.charAt(i) == 'E') {
                countE++;
                posE = i;
            }
        }

        // Step 3: split front and back halves by e
        if (countE > 1) return false; // e的数目不能超过1个
        if (countE == 0) return isOK(s, 0, s.length(), 1); // case 1: 如果没有e,那么小数点只能最多有1个
        // case 2: 有1个e的情况下，e之前最多只能有1个小数点；e之后不能有小数点
        return isOK(s, 0, posE, 1) && isOK(s, posE + 1, s.length(), 0);
    }

    private boolean isOK(String s, int start, int end, int k) {
        // corner case
        if (start == end) return false;

        // 可能有正负号，只可能出现在最前面
        for (int i = start; i < end; i++) { // O(n)
            if ((s.charAt(i) == '+' || s.charAt(i) == '-') && i != start) { // must appear at the 1st place and only have one
                return false;
            }
        }
        boolean hasSign = false;
        if (s.charAt(start) == '+' || s.charAt(start) == '-') {
            hasSign = true;
            start++; // 如果头部有正负号，那么start++
        }

        // 后面的纯数字 + 小数点
        if (start == end || start + 1 == end && s.charAt(start) == '.') { // 除去正负号，如果后面是""或者"."，那么肯定false
            return false;
        }
        int countDot = 0;
        for (int i = start; i < end; i++) { // O(n)
            char c = s.charAt(i);
            if (c == '.') countDot++;
            else if (!Character.isDigit(c)) return false; // 后面出现非数字非"."肯定是false
        }
        return countDot <= k; // 判断"."不能超过k个
    }
}

/**
 * aeb -> [+a] e [-b]  大方向：找e，然后分而治之
 * [+c]
 * 中间不能有空格，否则就是false
 */
