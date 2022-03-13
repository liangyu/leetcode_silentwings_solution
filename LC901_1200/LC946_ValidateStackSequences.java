package LC901_1200;
import java.util.*;
public class LC946_ValidateStackSequences {
    /**
     * Given two sequences pushed and popped with distinct values, return true if and only if this could have been the
     * result of a sequence of push and pop operations on an initially empty stack.
     *
     * Input: pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
     * Output: true
     *
     * Input: pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
     * Output: false
     *
     * Constraints:
     *
     * 0 <= pushed.length == popped.length <= 1000
     * 0 <= pushed[i], popped[i] < 1000
     * pushed is a permutation of popped.
     * pushed and popped have distinct values.
     *
     * @param pushed
     * @param popped
     * @return
     */
    // time = O(n), space = O(n)
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();

        int n = pushed.length, j = 0;
        for (int i = 0; i < n; i++) {
            stack.push(pushed[i]);
            while (j < n && !stack.isEmpty() && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
        }
        return j == popped.length;
    }
}
/**
 * push和pop相互交叠，检查有无矛盾
 * 你盯着pop的第一个元素看
 * 1 2 3 4x 5 6 4
 * 4 4 6 5 3 2 1
 * 如果有多个4，如何保证弹出的4是正确的那个4呢？
 * 看到能先消掉的就先相互消掉
 */