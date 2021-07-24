package LC1801_2100;
import java.util.*;
public class LC1944_NumberofVisiblePeopleinaQueue {
    /**
     * There are n people standing in a queue, and they numbered from 0 to n - 1 in left to right order. You are given
     * an array heights of distinct integers where heights[i] represents the height of the ith person.
     *
     * A person can see another person to their right in the queue if everybody in between is shorter than both of them.
     * More formally, the ith person can see the jth person if i < j and min(heights[i], heights[j]) > max(heights[i+1],
     * heights[i+2], ..., heights[j-1]).
     *
     * Return an array answer of length n where answer[i] is the number of people the ith person can see to their right
     * in the queue.
     *
     * Input: heights = [10,6,8,5,11,9]
     * Output: [3,1,2,1,1,0]
     *
     * Constraints:
     *
     * n == heights.length
     * 1 <= n <= 10^5
     * 1 <= heights[i] <= 10^5
     * All the values of heights are unique.
     * @param heights
     * @return
     */
    // S1: stack
    // time = O(n), space = O(n)
    public int[] canSeePersonsCount(int[] heights) {
        // corner case
        if (heights == null || heights.length == 0) return new int[0];

        int n = heights.length;
        int[] res = new int[n];
        res[n - 1] = 0;
        Stack<Integer> stack = new Stack<>(); // maintain a monotonically increasing stack from left to right
        stack.push(heights[n - 1]);

        for (int i = n - 2; i >= 0; i--) {
            if (!stack.isEmpty() && stack.peek() < heights[i]) {
                int count = 0;
                while (!stack.isEmpty() && stack.peek() < heights[i]) {
                    stack.pop();
                    count++;
                }
                res[i] = stack.isEmpty() ? count : count + 1;
            } else res[i] = 1;
            stack.push(heights[i]);
        }
        return res;
    }

    // S2: stack
    // time = O(n), space = O(n)
    public int[] canSeePersonsCount2(int[] heights) {
        // corner case
        if (heights == null || heights.length == 0) return new int[0];

        int n = heights.length;
        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] <= heights[i]) res[stack.pop()]++;
            if (!stack.isEmpty()) res[stack.peek()]++;
            stack.push(i);
        }
        return res;
    }
}
