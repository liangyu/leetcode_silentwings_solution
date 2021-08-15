package LC1501_1800;

public class LC1541_MinimumInsertionstoBalanceaParenthesesString {
    /**
     * Given a parentheses string s containing only the characters '(' and ')'. A parentheses string is balanced if:
     *
     * Any left parenthesis '(' must have a corresponding two consecutive right parenthesis '))'.
     * Left parenthesis '(' must go before the corresponding two consecutive right parenthesis '))'.
     * In other words, we treat '(' as openning parenthesis and '))' as closing parenthesis.
     *
     * For example, "())", "())(())))" and "(())())))" are balanced, ")()", "()))" and "(()))" are not balanced.
     *
     * You can insert the characters '(' and ')' at any position of the string to balance it if needed.
     *
     * Return the minimum number of insertions needed to make s balanced.
     *
     * Input: s = "(()))"
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists of '(' and ')' only.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int minInsertions(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length(), count = 0, res = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') count++;
            else {
                if (i + 1 < n && s.charAt(i + 1) == ')') {
                    count--;
                    i++; // 记得再移动一格
                } else { // 只有一个右括号，人工+1个右括号，强制凑成2个右括号
                    res++;
                    count--;
                }
            }
            if (count < 0) { // 右括号太多了，通过补充左括号来平衡
                res++;
                count = 0; // count记录的仅仅是左括号的数量，上面只会每次-1，所以这里必定只要补1个左括号就能回到0
            }
        }
        res += count * 2;
        return res;
    }
}
/**
 * ref: LC921，代码改动非常小
 * balance的定义不同，一个左括号要对应2个右括号
 * stack: 存放左括号，有右括号就和栈顶元素相结合 (..()().. )
 * greedy: count -> unmatched left parenthesis
 *         count > 0
 *         count < 0 没有匹配的右括号，没有办法了
 * 一定要找到2个连续的右括号 -> count--
 * 只有一个右括号，永远都是非法的
 * 1. must two consecutive ) cancel one (
 * 2. at the end, ret += count * 2
 */
