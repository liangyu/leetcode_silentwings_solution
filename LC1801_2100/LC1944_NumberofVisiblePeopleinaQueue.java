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
        int n = heights.length;
        Stack<Integer> stack = new Stack<>();

        int[] res = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            int count = 0;
            while (!stack.isEmpty() && heights[i] > heights[stack.peek()]) { // 不遵循递减规律
                count++;
                stack.pop();
            }
            if (!stack.isEmpty()) count++;
            res[i] = count;
            stack.push(i);
        }
        return res;
    }
}
/**
 * next greater element => 单调栈
 * 最多会增加到多少呢？一直到大于A的next greater element就到头了，对A来说能看到的就是BCDE
 * A的左边是A',中间的BCD是A'看不到的 => 不管A'比A大还是比A小，BCD是看不到，可以扔掉 => 从右往左是一个递减的序列
 * 逆序，从右往左维护一个单调递减栈
 * 退栈是因为用不到了，同时需要计数
 */
