package LC901_1200;
import java.util.*;
public class LC1096_BraceExpansionII {
    /**
     * Under the grammar given below, strings can represent a set of lowercase words. Let R(expr) denote the set of
     * words the expression represents.
     *
     * The grammar can best be understood through simple examples:
     *
     * Single letters represent a singleton set containing that word.
     * R("a") = {"a"}
     * R("w") = {"w"}
     * When we take a comma-delimited list of two or more expressions, we take the union of possibilities.
     * R("{a,b,c}") = {"a","b","c"}
     * R("{{a,b},{b,c}}") = {"a","b","c"} (notice the final set only contains each word at most once)
     * When we concatenate two expressions, we take the set of possible concatenations between two words where the first
     * word comes from the first expression and the second word comes from the second expression.
     * R("{a,b}{c,d}") = {"ac","ad","bc","bd"}
     * R("a{b,c}{d,e}f{g,h}") = {"abdfg", "abdfh", "abefg", "abefh", "acdfg", "acdfh", "acefg", "acefh"}
     * Formally, the three rules for our grammar:
     *
     * For every lowercase letter x, we have R(x) = {x}.
     * For expressions e1, e2, ... , ek with k >= 2, we have R({e1, e2, ...}) = R(e1) ∪ R(e2) ∪ ...
     * For expressions e1 and e2, we have R(e1 + e2) = {a + b for (a, b) in R(e1) × R(e2)}, where + denotes
     * concatenation, and × denotes the cartesian product.
     * Given an expression representing a set of words under the given grammar, return the sorted list of words that the
     * expression represents.
     *
     * Input: expression = "{a,b}{c,{d,e}}"
     * Output: ["ac","ad","ae","bc","bd","be"]
     *
     * Constraints:
     *
     * 1 <= expression.length <= 60
     * expression[i] consists of '{', '}', ','or lowercase English letters.
     * The given expression represents a set of words based on the grammar given in the description.
     * @param expression
     * @return
     */
    // S1: stack
    // time = O(n), space = O(n)
    public List<String> braceExpansionII(String expression) {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (char c : expression.toCharArray()) {
            if (Character.isLowerCase(c)) sb.append('{').append(c).append('}');
            else sb.append(c);
        }
        sb.append('}');
        expression = sb.toString();

        Stack<HashSet<String>> stackStr = new Stack<>();
        Stack<Integer> stackOp = new Stack<>();
        HashSet<String> curStr = new HashSet<>();

        for (char c : expression.toCharArray()) {
            if (c == '{' || c == ',') {
                stackStr.push(new HashSet<>(curStr));
                stackOp.push(c == '{' ? 0 : 1);
                curStr.clear();
            } else if (c == '}') {
                while (stackOp.peek() == 1) { // 注意：这里是while, eg.{a,b,c,d} 平级！
                    curStr = combine(stackStr.pop(), curStr);
                    stackOp.pop();
                }
                if (stackOp.peek() == 0) { // 在栈顶只可能连续出现1个0，因为前面x乘都已经处理完了！
                    curStr = crossProduct(stackStr.pop(), curStr);
                    stackOp.pop();
                }
            } else {
                curStr.add(c + "");
            }
        }
        List<String> res = new ArrayList<>(curStr);
        Collections.sort(res);
        return res;
    }

    private HashSet<String> combine(HashSet<String> s, HashSet<String> t) {
        HashSet<String> res = new HashSet<>();
        for (String x : s) res.add(x);
        for (String x : t) res.add(x);
        return res;
    }

    private HashSet<String> crossProduct(HashSet<String> s, HashSet<String> t) {
        HashSet<String> res = new HashSet<>();
        if (s.size() == 0) s.add("");
        if (t.size() == 0) t.add("");

        for (String x : s) {
            for (String y : t) {
                res.add(x + y);
            }
        }
        return res;
    }

    // S2: dfs
    // time = O(n), space = O(n)
    public List<String> braceExpansionII2(String expression) {
        HashSet<String> cur = helper(expression, 0, expression.length() - 1);
        List<String> res = new ArrayList<>(cur);
        Collections.sort(res);
        return res;
    }

    private HashSet<String> helper(String s, int a, int b) {
        HashSet<String> cur = new HashSet<>();
        Stack<HashSet<String>> stack = new Stack<>();
        for (int i = a; i <= b; i++) {
            if (s.charAt(i) == '{') {
                int level = 1;
                int j = i + 1;
                while (j <= b && level > 0) {
                    level += (s.charAt(j) == '{' ? 1 : 0);
                    level -= (s.charAt(j) == '}' ? 1 : 0);
                    if (level == 0) break;
                    else j++;
                }
                HashSet<String> next = helper(s, i + 1, j - 1);
                cur = crossProduct(cur, next);
                i = j;
            } else if (s.charAt(i) == ',') {
                stack.push(new HashSet<>(cur));
                cur.clear();
            } else {
                int j = i + 1;
                while (j <= b && Character.isLowerCase(s.charAt(j))) j++;
                HashSet<String> next = new HashSet<>();
                next.add(s.substring(i, j));
                cur = crossProduct(cur, next);
                i = j - 1;
            }
        }

        while (!stack.isEmpty()) {
            for (String x : stack.peek()) cur.add(x);
            stack.pop();
        }
        return cur;
    }
}
/**
 * ref: LC1087   List -> HashSet
 * {a,b}{d,e} x乘
 * {c},{c,d} => {c,d} 并起来
 * 逗号之间是一个去重的并
 * 把所有的字母都加上{} -> 层级结构一致
 * 遇到左括号与逗号就入栈
 * 建立2个栈， stackStr: 0, stackOp: 1
 * 压入一个字符串数组
 * S2: dfs
 * 同一级里只有逗号，平级相加，
 * 碰到逗号就压栈，最后把所有元素加起来
 */