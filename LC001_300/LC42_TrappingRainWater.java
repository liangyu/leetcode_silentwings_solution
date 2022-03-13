package LC001_300;
import java.util.*;
public class LC42_TrappingRainWater {
    /**
     * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much
     * water it can trap after raining.
     *
     *      0,1,0,2,1,0,1,3,2,1,2,1
     *
     *                    *
     *            *       * *   *
     *        *   * *   * * * * * *
     *      0 1 2 3 4 5 6 7 8 9 0 1
     *      l                     r
     *
     * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * Output: 6
     *
     * Input: height = [4,2,0,3,2,5]
     * Output: 9
     *
     * Constraints:
     *
     * n == height.length
     * 0 <= n <= 3 * 10^4
     * 0 <= height[i] <= 10^5
     *
     * @param height
     * @return
     */
    // S1: Two Pointers
    // time = O(n), space = O(1)
    public int trap(int[] height) {
        // corner case
        if (height == null || height.length == 0) return 0;

        int len = height.length;
        int left = 0, right = len - 1;
        int leftMax = 0, rightMax = 0;
        int res = 0;

        while (left < right) { // 当左右相遇则表示已经遍历完整个数组，跳出loop
            // 这里写成 <= 也行，放在left或者right都没有问题，效果是一样的
            if (height[left] < height[right]) { // 左矮右高，计算并移动左边
                leftMax = Math.max(leftMax, height[left]); // 实时更新leftMax
                res += leftMax - height[left];
                left++;
            } else { // 相等的情况在此也一起归入右边，移动右指针
                rightMax = Math.max(rightMax, height[right]); // 实时更新rightMax
                res += rightMax - height[right];
                right--;
            }
        }
        return res;
    }

    // S2: three pass
    // time = O(n), space = O(n)
    public int trap2(int[] height) {
        int n = height.length;
        int[] leftMost = new int[n]; // leftMost[i]: the highest bar to the left of i
        int[] rightMost = new int[n];

        for (int i = 1; i < n; i++) {
            leftMost[i] = Math.max(leftMost[i - 1], height[i - 1]);
        }

        for (int i = n - 2; i >= 0; i--) {
            rightMost[i] = Math.max(rightMost[i + 1], height[i + 1]);
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            int h = Math.min(leftMost[i], rightMost[i]) - height[i];
            res += Math.max(h * 1, 0);
        }
        return res;
    }

    // S3: monotonic stack
    // time = O(n), space = O(n)
    public int trap3(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int n = height.length, res = 0;
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
                int base = height[stack.pop()];
                if (stack.isEmpty()) continue;
                int h = Math.min(height[stack.peek()], height[i]) - base;
                int w = i - stack.peek() - 1;
                res += h * w;
            }
            stack.push(i);
        }
        return res;
    }
}
