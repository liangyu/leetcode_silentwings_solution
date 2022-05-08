package LC001_300;
import java.util.*;
public class LC227_BasicCalculatorII {
    /**
     * Given a string s which represents an expression, evaluate this expression and return its value.
     *
     * The integer division should truncate toward zero.
     *
     * Input: s = "3+2*2"
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= s.length <= 3 * 10^5
     * s consists of integers and operators ('+', '-', '*', '/') separated by some number of spaces.
     * s represents a valid expression.
     * All the integers in the expression are non-negative integers in the range [0, 2^31 - 1].
     * The answer is guaranteed to fit in a 32-bit integer.
     * @param s
     * @return
     */
    // S1: stack
    // time = O(n), space = O(n)
    public int calculate(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        Stack<Integer> stack = new Stack<>();
        int res = 0, num = 0;
        char sign = '+';

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    num = num * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
            }
            if (!Character.isDigit((c)) && c != ' ' || i == s.length() - 1) {
                if (sign == '+') stack.push(num);
                if (sign == '-') stack.push(-num);
                if (sign == '*') stack.push(stack.pop() * num);
                if (sign == '/') stack.push(stack.pop() / num);
                sign = s.charAt(i);
                num = 0;
            }
        }
        while (!stack.isEmpty()) res += stack.pop();
        return res;
    }

    // S2: rolling matrix
    // time = O(n), space = O(1)
    public int calculate2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        s = s.trim().replaceAll(" +", "");
        int res = 0, prev = 0, i = 0;
        char sign = '+';


        while (i < s.length()) {
            int cur = 0;
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                cur = cur * 10 + s.charAt(i) - '0';
                i++;
            }

            if (sign == '+') {
                res += prev;
                prev = cur;
            } else if (sign == '-') {
                res += prev;
                prev = -cur;
            } else if (sign == '*') {
                prev = prev * cur;
            } else if (sign == '/') {
                prev = prev / cur;
            }

            if (i < s.length()) {
                sign = s.charAt(i);
                i++;
            }
        }

        res += prev;
        return res;
    }

    // S3: List
    // time = O(n), space = O(n)
    public int calculate3(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        StringBuilder sb = new StringBuilder();
        sb.append('+'); // 补上一个+号，这样可以保证字符串都是从运算符开始的！
        for (char c : s.toCharArray()) {
            if (c != ' ') sb.append(c);
        }
        s = sb.toString();
        int n = s.length();
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                int j = i + 1;
                while (j < n && Character.isDigit(s.charAt(j))) j++;
                int num = Integer.parseInt(s.substring(i + 1, j));
                if (s.charAt(i) == '+') res.add(num);
                else res.add(-num);
                i = j - 1;
            } else if (s.charAt(i) == '*' || s.charAt(i) == '/') {
                int j = i + 1;
                while (j < n && Character.isDigit(s.charAt(j))) j++;
                int num = Integer.parseInt(s.substring(i + 1, j));
                int k = res.size();
                if (s.charAt(i) == '*') res.set(k - 1, res.get(k - 1) * num);
                else res.set(k - 1, res.get(k - 1) / num);
                i = j - 1;
            }
        }
        int sum = 0;
        for (int x : res) sum += x;
        return sum;
    }

    // S3: stack
    // time = O(n), space = O(n)
    public int calculate4(String s) {
        Stack<Integer> num = new Stack<>();
        Stack<Character> op = new Stack<>();

        int n = s.length();
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('+', 1);
        map.put('-', 1);
        map.put('*', 2);
        map.put('/', 2);

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == ' ') continue;
            if (Character.isDigit(c)) {
                int j = i;
                while (j < n && Character.isDigit(s.charAt(j))) j++;
                int val = Integer.parseInt(s.substring(i, j));
                num.push(val);
                i = j - 1;
            } else {
                while (!op.isEmpty() && map.get(op.peek()) >= map.get(c)) eval(num, op);
                op.push(c);
            }
        }

        while (!op.isEmpty()) eval(num, op);
        return num.peek();
    }

    private void eval(Stack<Integer> num, Stack<Character> op) {
        int b = num.pop(), a = num.pop();
        char c = op.pop();
        int r = 0;

        if (c == '+') r = a + b;
        else if (c == '-') r = a - b;
        else if (c == '*') r = a * b;
        else r = a / b;
        num.push(r);
    }
}
/**
 * stack
 * +3, -2, +4, *5, -6, -1, +2/2
 * 字符串第一个前面可以人工加一个+号
 * 有括号就递归
 *
 * 运算表达式模板：
 * 1. 数：压入栈中
 * 2. (: 压入栈中
 * 3. ): 则 ()中间部分算完
 * 4. 运算符：while 栈顶op的优先级 >= 当前op的优先级，则操作栈顶
 */