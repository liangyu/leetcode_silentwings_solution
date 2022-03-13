package LC901_1200;
import java.util.*;
public class LC1087_BraceExpansion {
    /**
     * You are given a string s representing a list of words. Each letter in the word has one or more options.
     *
     * If there is one option, the letter is represented as is.
     * If there is more than one option, then curly braces delimit the options. For example, "{a,b,c}" represents
     * options ["a", "b", "c"].
     * For example, if s = "a{b,c}", the first character is always 'a', but the second character can be 'b' or 'c'. The
     * original list is ["ab", "ac"].
     *
     * Return all words that can be formed in this manner, sorted in lexicographical order.
     *
     * Input: s = "{a,b}c{d,e}f"
     * Output: ["acdf","acef","bcdf","bcef"]
     *
     * Constraints:
     *
     * 1 <= s.length <= 50
     * s consists of curly brackets '{}', commas ',', and lowercase English letters.
     * s is guaranteed to be a valid input.
     * There are no nested curly brackets.
     * All characters inside a pair of consecutive opening and ending curly brackets are different.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public String[] expand(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) sb.append('{').append(c).append('}');
            else sb.append(c);
        }
        sb.append('}');
        s = sb.toString();

        Stack<List<String>> strStack = new Stack<>();
        Stack<Integer> opStack = new Stack<>();
        List<String> list = new ArrayList<>();

        for (char c : s.toCharArray()) {
            if (c == '{' || c == ',') {
                strStack.push(new ArrayList<>(list));
                opStack.push(c == '{' ? 0 : 1);
                list = new ArrayList<>();
            } else if (Character.isLowerCase(c)) {
                list.add(c + "");
            } else {
                while (opStack.peek() == 1) {
                    list = combine(strStack.pop(), list);
                    opStack.pop();
                }
                if (opStack.peek() == 0) {
                    list = crossProduct(strStack.pop(), list);
                    opStack.pop();
                }
            }
        }

        Collections.sort(list);
        String[] res = new String[list.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    private List<String> combine(List<String> s, List<String> t) {
        List<String> res = new ArrayList<>();
        for (String x : s) res.add(x);
        for (String x : t) res.add(x);
        return res;
    }

    private List<String> crossProduct(List<String> s, List<String> t) {
        List<String> res = new ArrayList<>();
        if (s.size() == 0) s.add("");
        if (t.size() == 0) t.add("");

        for (String a : s) {
            for (String b : t) {
                res.add(a + b);
            }
        }
        return res;
    }
}
/**
 * [a,b]
 * [c]
 * [d,e]
 * [f]
 * dfs -> 没办法拓展
 * use stack,也可以用在LC1092上面
 * 栈用来对付层级嵌套是首选
 * => 所有的字母都加上左右括号
 * {a,b}c{d,e}f => {{{a},{b}}{c}{{d},{e}}{f}} => 整体再加1个大括号，使得最终可以退栈
 * {W X Y Z}
 * 逗号怎么处理？
 * {{{a},{b}}
 * 逗号也要入栈
 * 碰到左括号也要入栈
 * 左括号退栈，和前面是x乘
 * 如果是逗号退栈，跟栈顶要合并起来
 *  ,  {   b   }   }
 * ""  ""      b   ab
 */
