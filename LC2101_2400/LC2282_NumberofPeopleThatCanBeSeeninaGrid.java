package LC2101_2400;
import java.util.*;
public class LC2282_NumberofPeopleThatCanBeSeeninaGrid {
    /**
     * You are given an m x n 0-indexed 2D array of positive integers heights where heights[i][j] is the height of the
     * person standing at position (i, j).
     *
     * A person standing at position (row1, col1) can see a person standing at position (row2, col2) if:
     *
     * The person at (row2, col2) is to the right or below the person at (row1, col1). More formally, this means that
     * either row1 == row2 and col1 < col2 or row1 < row2 and col1 == col2.
     * Everyone in between them is shorter than both of them.
     * Return an m x n 2D array of integers answer where answer[i][j] is the number of people that the person at
     * position (i, j) can see.
     *
     * Input: heights = [[3,1,4,2,5]]
     * Output: [[2,1,2,1,0]]
     *
     * Input: heights = [[5,1],[3,1],[4,1]]
     * Output: [[3,1],[2,1],[1,0]]
     *
     * Constraints:
     *
     * 1 <= heights.length <= 400
     * 1 <= heights[i].length <= 400
     * 1 <= heights[i][j] <= 10^5
     * @param heights
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int[][] seePeople(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        int[][] right = new int[m][n]; // nextGreaterOrEqual
        int[][] below = new int[m][n];

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < m; i++) {
            stack.clear();
            for (int j = n - 1; j >= 0; j--) {
                int count = 0;
                while (!stack.isEmpty() && heights[i][stack.peek()] < heights[i][j]) {
                    count++;
                    stack.pop();
                }
                if (!stack.isEmpty()) count++;
                right[i][j] = count;
                if (stack.isEmpty() || heights[i][j] != heights[i][stack.peek()]) stack.push(j);
            }
        }

        for (int j = 0; j < n; j++) {
            stack.clear();
            for (int i = m - 1; i >= 0; i--) {
                int count = 0;
                while (!stack.isEmpty() && heights[stack.peek()][j] < heights[i][j]) {
                    count++;
                    stack.pop();
                }
                if (!stack.isEmpty()) count++;
                below[i][j] = count;
                if (stack.isEmpty() || heights[i][j] != heights[stack.peek()][j]) stack.push(i);
            }
        }

        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = right[i][j] + below[i][j];
            }
        }
        return res;
    }
}
