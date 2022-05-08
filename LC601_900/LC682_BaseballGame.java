package LC601_900;
import java.util.*;
public class LC682_BaseballGame {
    /**
     * You are keeping score for a baseball game with strange rules. The game consists of several rounds, where the
     * scores of past rounds may affect future rounds' scores.
     *
     * At the beginning of the game, you start with an empty record. You are given a list of strings ops, where ops[i]
     * is the ith operation you must apply to the record and is one of the following:
     *
     * An integer x - Record a new score of x.
     * "+" - Record a new score that is the sum of the previous two scores. It is guaranteed there will always be two
     * previous scores.
     * "D" - Record a new score that is double the previous score. It is guaranteed there will always be a previous score.
     * "C" - Invalidate the previous score, removing it from the record. It is guaranteed there will always be a
     * previous score.
     * Return the sum of all the scores on the record.
     *
     * Input: ops = ["5","2","C","D","+"]
     * Output: 30
     *
     * Constraints:
     *
     * 1 <= ops.length <= 1000
     * ops[i] is "C", "D", "+", or a string representing an integer in the range [-3 * 104, 3 * 104].
     * For operation "+", there will always be at least two previous scores on the record.
     * For operations "C" and "D", there will always be at least one previous score on the record.
     * @param ops
     * @return
     */
    // S1: stack
    // time = O(n), space = O(n)
    public int calPoints(String[] ops) {
        int n = ops.length;
        Stack<Integer> stack = new Stack<>();
        for (String s : ops) {
            if (s.equals("+")) {
                int temp = stack.pop();
                int cur = temp + stack.peek();
                stack.push(temp);
                stack.push(cur);
            } else if (s.equals("D")) stack.push(stack.peek() * 2);
            else if (s.equals("C")) stack.pop();
            else stack.push(Integer.parseInt(s));
        }

        int res = 0;
        while (!stack.isEmpty()) res += stack.pop();
        return res;
    }
}
