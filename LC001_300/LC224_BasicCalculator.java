package LC001_300;
import java.util.*;
public class LC224_BasicCalculator {
    /**
     * Given a string s representing an expression, implement a basic calculator to evaluate it.
     *
     * Input: s = "1 + 1"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s.length <= 3 * 10^5
     * s consists of digits, '+', '-', '(', ')', and ' '.
     * s represents a valid expression.
     * @param s
     * @return
     */
    // S1: stack
    // time = O(n), space = O(n)
    public int calculate(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int res = 0, sign = 1, n = s.length();
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '+' || c == '-') {
                sign = c == '+' ? 1 : -1;
            } else if (Character.isDigit(c)) {
                int num = 0;
                while (i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + s.charAt(i) - '0';
                    i++;
                }
                res += num * sign;
                i--; // 注意千万别忘了i要后退一格，因为for loop会i++!!!
            } else if (c == '(') {
                stack.push(res);
                stack.push(sign);
                res = 0; // reset
                sign = 1; // 注意符号也要reset回1
            } else if (c == ')') {
                res = stack.pop() * res + stack.pop();
            }
        }
        return res;
    }

    // S2: stack
    // time = O(n), space = O(n)
    public int calculate2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        StringBuilder sb = new StringBuilder();
        sb.append('+'); // 刚开始时也要加一个'+'，确定开始数的符号默认值，后面出现'-'的话，可以在for loop里重新赋值
        // reprocessing the string s
        for (char c : s.toCharArray()) {
            if (c == ' ') continue;
            sb.append(c);
            if (c == '(') sb.append('+'); // 遇到左括号，则在后面添加一个'+',也可以再下面遇到'('时，reset sign = 1
        }

        s = sb.toString();
        Stack<Integer> stack = new Stack<>();
        int n = s.length(), sign = 0, res = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '+' || c == '-') {
                sign = c == '+' ? 1 : -1;
            } else if (Character.isDigit(c)) {
                int j = i;
                while (i < n && Character.isDigit(s.charAt(i))) i++;
                int num = Integer.parseInt(s.substring(j, i));
                res += num * sign;
                i--; // 注意千万别忘了i要后退一格，因为for loop会i++!!!
            } else if (c == '(') {
                stack.push(res); // 先把迄今为止的res压入栈，再压入接下来括号里结果对应的符号sign
                stack.push(sign);
                res = 0;
            } else if (c == ')') {
                res = stack.pop() * res; // 先给括号里的结果定下sign，再和括号前所有累加的结果进行计算
                res += stack.pop();
            }
        }
        return res;
    }
}
/**
 * 压栈
 * -3 => +(-3)
 * 每个数都有正负号
 * 所有单项式都是做加法操作
 */
