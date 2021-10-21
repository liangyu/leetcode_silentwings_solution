package LC601_900;
import java.util.*;
public class LC772_BasicCalculatorIII {
    /**
     * Implement a basic calculator to evaluate a simple expression string.
     *
     * The expression string contains only non-negative integers, '+', '-', '*', '/' operators, and open '(' and closing
     * parentheses ')'. The integer division should truncate toward zero.
     *
     * You may assume that the given expression is always valid. All intermediate results will be in the range of
     * [-231, 231 - 1].
     *
     * Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions,
     * such as eval().
     *
     * Input: s = "1+1"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s <= 10^4
     * s consists of digits, '+', '-', '*', '/', '(', and ')'.
     * s is a valid expression.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public int calculate(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        Stack<String> stack = new Stack<>();
        String curStr = "";

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(curStr);
                curStr = "";
            } else if (s.charAt(i) == ')') {
                int curRes = eval(curStr); // calculate the result inside the ()
                curStr = stack.pop() + curRes; // 字符串拼接起来
            } else curStr += s.charAt(i);
        }
        return eval(curStr);
    }

    private int eval(String s) { // 不包含任何括号的str => LC227，唯一不同的就是会可能出现 +5 - +4这种情况
        StringBuilder sb = new StringBuilder();
        sb.append('+');
        for (char c : s.toCharArray()) {
            if (c != ' ') sb.append(c);
        }
        s = sb.toString();
        int n = s.length();
        List<Integer> nums = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                int j = i + 1;
                if (s.charAt(j) == '+' || s.charAt(j) == '-') j++;
                while (j < n && Character.isDigit(s.charAt(j))) j++;
                int num = Integer.parseInt(s.substring(i + 1, j));
                if (s.charAt(i) == '+') nums.add(num);
                else nums.add(-num);
                i = j - 1;
            } else if (s.charAt(i) == '*' || s.charAt(i) == '/') {
                int j = i + 1;
                if (s.charAt(j) == '+' || s.charAt(j) == '-') j++;
                while (j < n && Character.isDigit(s.charAt(j))) j++;
                int num = Integer.parseInt(s.substring(i + 1, j));
                int k = nums.size();
                if (s.charAt(i) == '*') nums.set(k - 1, nums.get(k - 1) * num);
                else nums.set(k - 1, nums.get(k - 1) / num);
                i = j - 1;
            }
        }
        int sum = 0;
        for (int x : nums) sum += x;
        return sum;
    }
}
/**
 * 手头上存的是一个string，每次碰到左括号的时候，就压入stack，碰到右括号，需要evaluate string，变成一个值
 * 再和栈顶元素结合，拼接起来
 * 3 + (5)
 * 左括号左边始终是有一个运算符的，这样可以直接eval括号里的东西
 * 不包含任何括号的str => LC227，唯一不同的就是会可能出现 +5 - +4这种情况
 * 为什么？
 * +5 - (-4) + 3 * (-2)
 * 拆分成单项式，放在数组里
 */
