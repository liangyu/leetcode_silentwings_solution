package LC2101_2400;

public class LC2116_CheckifaParenthesesStringCanBeValid {
    /**
     * A parentheses string is a non-empty string consisting only of '(' and ')'. It is valid if any of the following
     * conditions is true:
     *
     * It is ().
     * It can be written as AB (A concatenated with B), where A and B are valid parentheses strings.
     * It can be written as (A), where A is a valid parentheses string.
     * You are given a parentheses string s and a string locked, both of length n. locked is a binary string consisting
     * only of '0's and '1's. For each index i of locked,
     *
     * If locked[i] is '1', you cannot change s[i].
     * But if locked[i] is '0', you can change s[i] to either '(' or ')'.
     * Return true if you can make s a valid parentheses string. Otherwise, return false.
     *
     * Input: s = "))()))", locked = "010100"
     * Output: true
     *
     * Constraints:
     *
     * n == s.length == locked.length
     * 1 <= n <= 10^5
     * s[i] is either '(' or ')'.
     * locked[i] is either '0' or '1'.
     * @param s
     * @param locked
     * @return
     */
    // S1: one pass
    // time = O(n), space = O(1)
    public boolean canBeValid(String s, String locked) {
        int n = s.length();
        if (n % 2 != 0) return false;
        int upper = 0, lower = 0;
        for (int i = 0; i < n; i++) {
            if (locked.charAt(i) == '1') {
                if (s.charAt(i) == '(') {
                    lower++;
                    upper++;
                } else {
                    lower--;
                    upper--;
                }
            } else { // *
                upper++;
                lower--;
            }

            if (lower < 0) { // 右括号太多了，待匹配的左括号变负数了 -> 变成左括号
                // 为什么+2？反观上面*时，lower--，这个时候将1个右括号变为左括号，相当于当时lower--变为lower++,所以要补的差值是2！！！
                // ref: LC678,哪里lower < 0的时候，因为可以改成空""，所以只要lower++即可！
                lower += 2;
            }
            if (upper < 0) return false; // 在locked[i] = 1的时候，upper与lower同+同-，所以如果没有chance可以变，upper这里会<0！
        }
        return lower == 0;
    }

    // S2: two pass
    // time = O(n), space = O(1)
    public boolean canBeValid2(String s, String locked) {
        int n = s.length(), count = 0, chance = 0;
        if (n % 2 != 0) return false;

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') count++;
            else count--;
            if (locked.charAt(i) == '0' && s.charAt(i) == ')') chance++;
            if (count < 0) {
                if (chance == 0) return false;
                count += 2;
                chance--;
            }
        }
        // every right parenthesis must have a matching left parenthesis

        // 反过来走一遍，保证左括号都是有匹配的
        count = 0; // unmatched right parenthesis
        chance = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == ')') count++;
            else count--;
            if (locked.charAt(i) == '0' && s.charAt(i) == '(') chance++;
            if (count < 0) {
                if (chance == 0) return false;
                count += 2;
                chance--;
            }
        }
        return true;
    }
}
/**
 * ref: LC678
 * lock = 0 -> *
 * S1: one pass
 * count: currently # of unmatched left parenthesis
 * lower: currently at least # of unmatched left parenthesis
 * upper: currently at most # of unmatched left parenthesis
 * 对于有*的题目，我们不能用单一count，要用lower(无脑设置为右括号), upper(无脑设置为左括号)
 * 遇到不是*的字符，lower,upper都+1
 */