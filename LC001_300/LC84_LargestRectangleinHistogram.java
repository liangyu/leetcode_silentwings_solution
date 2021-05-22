package LC001_300;
import java.util.*;
public class LC84_LargestRectangleinHistogram {
    /**
     * Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the
     * area of largest rectangle in the histogram.
     *
     * Input: [2,1,5,6,2,3]
     * Output: 10
     *
     * @param heights
     * @return
     */
    // time = O(n), space = O(n)
    public int largestRectangleArea(int[] heights) {
        // corner case
        if (heights == null || heights.length == 0) return 0;

        Stack<Integer> stack = new Stack<>();
        int max = 0;

        for (int i = 0; i <= heights.length; i++) {
            int h = i < heights.length ? heights[i] : 0;
            while (!stack.isEmpty() && heights[stack.peek()] > h) {
                int top = stack.pop();
                int area = heights[top] * (stack.isEmpty() ? i : i - stack.peek() - 1);
                max = Math.max(max, area);
            }
            stack.push(i);
        }
        return max;
    }
}
