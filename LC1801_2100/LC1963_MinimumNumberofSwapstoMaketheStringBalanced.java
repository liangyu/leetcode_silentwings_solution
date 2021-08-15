package LC1801_2100;
import java.util.*;
public class LC1963_MinimumNumberofSwapstoMaketheStringBalanced {
    /**
     * You are given a 0-indexed string s of even length n. The string consists of exactly n / 2 opening brackets '['
     * and n / 2 closing brackets ']'.
     *
     * A string is called balanced if and only if:
     *
     * It is the empty string, or
     * It can be written as AB, where both A and B are balanced strings, or
     * It can be written as [C], where C is a balanced string.
     * You may swap the brackets at any two indices any number of times.
     *
     * Return the minimum number of swaps to make s balanced.
     *
     * Input: s = "][]["
     * Output: 1
     *
     * Constraints:
     *
     * n == s.length
     * 2 <= n <= 10^6
     * n is even.
     * s[i] is either '[' or ']'.
     * The number of opening brackets '[' equals n / 2, and the number of closing brackets ']' equals n / 2.
     * @param s
     * @return
     */
    // S1: Stack
    // time = O(n), space = O(n)
    public int minSwaps(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (c == '[') stack.push('[');
            else {
                if (!stack.isEmpty() && stack.peek() == '[') stack.pop();
            }
        }
        return stack.size() % 2 == 0 ? stack.size() / 2 : (stack.size() + 1) / 2;
    }

    // S2: Greedy (最优解!)
    // time = O(n), space = O(1)
    public int minSwaps2(String s) {
        int count = 0; // temporarily unmatched left bracket
        int unmatch = 0; // permanently unmatched right bracket

        for (char ch : s.toCharArray()) {
            if (ch == '[') count++;
            else count--;
            if (count < 0) {
                unmatch++; // 注定无法匹配，count从0开始
                count = 0;
            }
        }
        return (unmatch + 1) / 2;
    }
}
/**
 * 尽量减少swap次数，只要有match就消掉
 * 剩下消不掉的就做swap
 * 把所有能match的都消掉
 * ]]]]]]]]]]][[[[[[[[[[[[
 * 最终消掉之后的模式只能是上面这种，最终得到的最少swap数目就在上面产生
 * stack 空了，再来一个]，就没办法了，再有左括号就继续累加
 * 最少需要多少对swap
 * ][ => 1
 * ]][[ => [][] => 1
 * ]]][[[ => []][][ => [[][]] => 2
 * ]]]][[[[ => []]][[[] => [][][][] => 2
 * 5 pairs => 3
 * 6 pairs => 3
 * (n + 1)/2  肯定有什么pattern
 *
 * 括号有关，贪心算法为主，结合stack
 */