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
        // corner case
        if (pushed == null || popped == null) return false;

        Stack<Integer> stack = new Stack<>();
        int j = 0, len = pushed.length;
        for (int p : pushed) {
            stack.push(p);
            while (!stack.isEmpty() && j < len && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
        }
        return j == len;
    }
}
