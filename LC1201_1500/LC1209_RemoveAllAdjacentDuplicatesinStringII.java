package LC1201_1500;
import java.util.*;
public class LC1209_RemoveAllAdjacentDuplicatesinStringII {
    /**
     * You are given a string s and an integer k, a k duplicate removal consists of choosing k adjacent and equal
     * letters from s and removing them, causing the left and the right side of the deleted substring to concatenate
     * together.
     *
     * We repeatedly make k duplicate removals on s until we no longer can.
     *
     * Return the final string after all such duplicate removals have been made. It is guaranteed that the answer is
     * unique.
     *
     * Input: s = "abcd", k = 2
     * Output: "abcd"
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * 2 <= k <= 10^4
     * s only contains lower case English letters.
     * @param s
     * @param k
     * @return
     */
    // S1: stack
    // time = O(n), space O(n)
    public String removeDuplicates(String s, int k) {
        Stack<int[]> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (stack.isEmpty() || c - 'a' != stack.peek()[0]) stack.push(new int[]{c - 'a', 1});
            else stack.push(new int[]{c - 'a', stack.peek()[1] + 1});

            if (stack.peek()[1] == k) {
                for (int i = 0; i < k; i++) stack.pop();
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append((char)(stack.pop()[0] + 'a'));
        }
        return sb.reverse().toString();
    }
}
/**
 * d[eee]dbbcccbdaa
 * 一次性消掉 -> one pass搞掉 能凑满3个就消掉，中间无任何阻隔，逢3必消
 * 如何快速知道栈顶有几个d呢？=> 每次push的时候，不仅Push本身，还包括其个数count
 */
