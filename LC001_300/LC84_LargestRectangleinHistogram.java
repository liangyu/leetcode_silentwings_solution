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
     * Constraints:
     *
     * 1 <= heights.length <= 10^5
     * 0 <= heights[i] <= 10^4
     * @param heights
     * @return
     */
    // S1: stack
    // time = O(n), space = O(n) 每个index进栈一次，出栈一次，所以overall是O(n)
    public int largestRectangleArea(int[] heights) {
        // corner case
        if (heights == null || heights.length == 0) return 0;

        Stack<Integer> stack = new Stack<>(); // stack里存的是index，通过index也可以知道对应的高度，反之则不行
        int max = 0;

        for (int i = 0; i <= heights.length; i++) {
            // 最后所有剩下的栈内元素都要弹栈 --> 末尾加1个0，0比所有元素都要小 --> 栈内元素全部弹栈
            int h = i < heights.length ? heights[i] : 0;
            // 把栈顶元素pop直至能valid push当前的h，然后再把它放进去吧
            while (!stack.isEmpty() && heights[stack.peek()] > h) { // 等于的话需要pop()吗？一样要进栈，比如2，5，5，6，2，
                // 必须把2个5下面的长方形面积都哟啊算进去，也就是两个5都要进栈，只有严格 > 的时候才能弹栈从而得出正确结论
                int top = stack.pop();
                // 算以当前top作为高度的矩形所能扩张到的最大面积
                int area = heights[top] * (stack.isEmpty() ? i : (i - stack.peek() - 1));
                max = Math.max(max, area);
            }
            stack.push(i);
        }
        return max;
    }

    // S2: Monotonic Stack (维持一个单调"非递减"栈，在数组首尾各加1个0，同时stack里也加一个0防止stack为空)
    // time = O(n), space = O(n)
    public int largestRectangleArea2(int[] heights) {
        int[] nums = new int[heights.length + 2];
        for (int i = 0; i < heights.length; i++) nums[i + 1] = heights[i]; // 前后各自+1个0

        Stack<Integer> stack = new Stack<>();
        stack.push(0); // 加入的其实就是nums[0]里的index 0，这样下面就从i = 1开始

        int area = 0;
        for (int i = 1; i < nums.length; i++) { // nums里末尾的0会被遍历到，因为要让所有的stack元素统统pop出来！
            if (nums[i] >= nums[stack.peek()]) stack.push(i);
            else {
                while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
                    int h = nums[stack.pop()];
                    int w = i - stack.peek() - 1;
                    area = Math.max(area, h * w);
                }
                stack.push(i);
            }
        }
        return area;
    }
}
/**
 * 贪心法的原则是维护一个递增（严格的说是非递减）的栈序列s，s里面是所给数组元素的index（注意不是数组元素本身）。
 * 当下一个元素满足递增的要求时，入栈
 * 当下一个元素不满足递增的要求时，就退栈处理栈顶的一些元素，使得即将入列的元素依然满足递增关系。
 * 退栈处理的过程中可以方便地考察那些退栈元素所围成的最大面积。
 * 其高度易知是height[s.top()]，但宽度是什么呢？注意是和次顶元素的位置有关：
 * while (height[s.back()]>height[i])
 * {
 *   Height = height[s.top()];
 *   s.pop(); // 提取次顶元素的位置
 *   result = max(result, Height * (i-s.top()-1);
 * }
 * 注意如果写成以下就是错误的:
 * result = max(result, height[s.top()] * (i-s.top());
 * 原因是次顶元素和栈顶元素可能在index上并不是相邻的，中间可能隔着一些已经被处理掉的大数。
 * 因此在考虑当前的栈顶元素围成的面积，应该包括这些位置，所以其宽度不仅是i-s.top()，而要更大。
 * 在height数组末添加元素0，是为了保证最后强制回溯。
 * 在height数组首端添加元素0，是为了便于处理s.pop()之后栈为空的特殊情况；这样处理后永远不会栈空。
 * 永远只在退栈的时候算面积。
 */