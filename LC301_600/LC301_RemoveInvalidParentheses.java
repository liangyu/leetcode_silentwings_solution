package LC301_600;
import java.util.*;
public class LC301_RemoveInvalidParentheses {
    /**
     * Remove the minimum number of invalid parentheses in order to make the input string valid.
     * Return all possible results.
     *
     * Note: The input string may contain letters other than the parentheses ( and ).
     *
     * Input: "()())()"
     * Output: ["()()()", "(())()"]
     *
     * Input: "(a)())()"
     * Output: ["(a)()()", "(a())()"]
     *
     * @param s
     * @return
     */
    // S1: DFS 最优解！！！
    // time = O(2^n), space = O(n)
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();
        // corner case
        if (s == null) return res;

        int[] rmlr = calrmlr(s);
        dfs(s, new StringBuilder(), 0, rmlr[0], rmlr[1], 0, res);
        return res;
    }

    private void dfs(String s, StringBuilder path, int idx, int rml, int rmr, int delta, List<String> res) {
        // base case - success
        if (idx == s.length() && delta == 0 && rml == 0 && rmr == 0) {
            res.add(path.toString());
            return;
        }

        // base case - fail
        if (rml < 0 || rmr < 0 || delta < 0 || idx == s.length()) {
            return;
        }

        char c = s.charAt(idx);
        int len = path.length();

        // case 1: '('
        if (c == '(') {
            // case 1.1: remove '('
            dfs(s, path, idx + 1, rml - 1, rmr, delta, res);

            // case 1.2: keep '('
            int i = idx, k = 0;
            while (i < s.length() && s.charAt(i) == '(') {
                path.append('(');
                i++;
                k++;
            }
            dfs(s, path, i, rml, rmr, delta + k, res);
            path.setLength(len);
        } else if (c == ')') { // case 2: ')'
            // case 2.1: remove ')'
            dfs(s, path, idx + 1, rml, rmr - 1, delta, res);

            // case 2.2: keep ')'
            int i = idx, k = 0;
            while (i < s.length() && s.charAt(i) == ')') {
                path.append(')');
                i++;
                k++;
            }
            dfs(s, path, i, rml, rmr, delta - k, res);
            path.setLength(len);
        } else { // case 3: other chars
            path.append(c);
            dfs(s, path, idx + 1, rml, rmr, delta, res);
            path.setLength(len);
        }
    }

    private int[] calrmlr(String s) {
        int rml = 0, rmr = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                rml++;
            } else if (c == ')') {
                if (rml > 0) rml--;
                else rmr++;
            }
        }
        return new int[]{rml, rmr};
    }

    // S2: DFS 精简版
    // time = O(2^n), space = O(n)
    private int maxLen = 0;
    public List<String> removeInvalidParentheses2(String s) {
        List<String> res = new ArrayList<>();

        int count = 0, remove = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '(') count++;
            else if (ch == ')') count--; // allow other chars to exist
            if (count < 0) {
                remove++;
                count = 0;
            }
        }
        remove += count;
        maxLen = s.length() - remove;

        String curStr = "";
        dfs(s, 0, curStr, 0, res);
        return res;
    }

    private void dfs(String s, int i, String curStr, int count, List<String> res) {
        // base case
        if (count < 0) return;
        if (curStr.length() > maxLen) return;
        if (i == s.length()) {
            if (count == 0 && curStr.length() == maxLen) {
                res.add(curStr);
            }
            return;
        }

        // case 0: 非括号字符 -> 直接加入，不影响括号之间的匹配
        if (s.charAt(i) != '(' && s.charAt(i) != ')') {
            dfs(s, i + 1, curStr + s.substring(i, i + 1), count, res);
        } else {
            // case 1: choose s[i]
            dfs(s, i + 1, curStr + s.substring(i, i + 1), count + (s.charAt(i) == '(' ? 1 : -1), res);

            // case 2: not choose s[i] -> 避免做重复的DFS的技巧，不同路径得到同一个结果，及时剪枝！！！
            if (curStr.length() == 0 || s.charAt(i) != curStr.charAt(curStr.length() - 1)) { // Important: pruning!!!
                // not take
                dfs(s, i + 1, curStr, count, res); // This path is not always taken!
            }
        }
    }
}

/**
 *  (((() -> ()  要去重 -> 保留1个，去掉3个，有很多不同路径得到同一个结果
 *  要得到互不相同的结果 -> 用hashmap去重，太慢！O(2^n) -> 能不能不用hash来得到一个独一无二的结果 -> 本题精华！！！
 *  DFS怎么去重？？？ 出现重复的根源在哪？
 *  (((( => (( => C(4, 2)种选择方式 = 4 * 3 / 2 = 6
 *  OOXX
 *  OXOX
 *  OXXO
 *  XOOX
 *  XOXO
 *  XXOO
 *  额外加一种规则 => 相同的话必须先取，比如如果取2个，就必须取前面遇见的2个 => DFS搜索过程中就不会有任何重复了
 *
 *  ())() => )) =>
 *  XOOXX 只有唯一的一条DFS路径得到它！比如XXOXO就不符合。
 *
 *  (((()
 *  XXX
 *  XXO
 *  XOO
 *  OX
 *  OO
 *
 *  作规定：
 *  if (s[i] == curStr.back())
 *      add(s[i])
*   else
 *      add(S[i])
 *      skip(s[i])
 *  这样就不会有重复 => 保证生成的dfs路径是唯一的
 *  ((((((((
 *  XXXXOOOO
 *      ((((
 *
 *   cur = (((
 *         OOX
 *         XOO (与上面重复)
 *
 *
 *  minimum number of removed parentheses
 *  print
 *  count: unmatched left parentheses
 *  maxLen => LC921的方法
 *  ((()))) ...
 *  减掉哪一个是任意都可以的。没法用贪心精准的抓准如何去删除右括号。
 *  比较笨的办法就是每个括号尝试一下
 *  O(2^n)
 *  pruning:
 *  1. count < 0 -> 当前走不下去了，这条路没救了
 *  2. 最多能保留多少也能知道， size > maxLen
 *  本题的考点不在于pruning，而在于去重！
 *  ((((  ))
 *  删哪2个？-> 任意2个都可以
 *  2^4 = 16种可能性，其中合法的为C(4,2) = 6，但最终答案只有1
 *  这个是本题最重要的地方，把这个解决了，复杂度会大幅降低！
 *  => dfs中经常用到的一个技巧：search in an array 如何正确设计dfs的方法能够去重！！！
 *  如何避免？
 *  经典方法：   A * A * A * A
 *         B   x   x   x   x => B
 *         B   x   x   x   A => BA
 *         B   x   x   A   A => BAA  所有取2个A的情况，我就已经枚举完了
 *         B   x   A   A   A => BAAA 强制你必须选，取3个A方案就得到了
 *         B   A   A   A   A => BAAAA 取4个A的方案也就得到了
 *
 *  策略：
 *  1. if s[i] != ret.back()
 *     ret+s[i] -> ...
 *     ret -> ...
 *  2. if s[i] == ret.back()
 *     ret+s[i] -> ...
 * 本质：永远只会取最后的要求个数的A,用这种机制可以去重，不需要用set。
 * 在很多dfs里都用到来去重。
 */
