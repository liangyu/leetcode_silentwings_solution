package LC901_1200;
import java.util.*;
public class LC1130_MinimumCostTreeFromLeafValues {
    /**
     * Given an array arr of positive integers, consider all binary trees such that:
     *
     * Each node has either 0 or 2 children;
     * The values of arr correspond to the values of each leaf in an in-order traversal of the tree.
     * The value of each non-leaf node is equal to the product of the largest leaf value in its left and right subtree,
     * respectively.
     * Among all possible binary trees considered, return the smallest possible sum of the values of each non-leaf node.
     * It is guaranteed this sum fits into a 32-bit integer.
     *
     * A node is a leaf if and only if it has zero children.
     *
     * Input: arr = [6,2,4]
     * Output: 32
     *
     * Constraints:
     *
     * 2 <= arr.length <= 40
     * 1 <= arr[i] <= 15
     * It is guaranteed that the answer fits into a 32-bit signed integer (i.e., it is less than 2^31).
     * @param arr
     * @return
     */
    // S1: stack
    // time = O(n), space = O(n)
    public int mctFromLeafValues(int[] arr) {
        int n = arr.length;
        int[] prevGreater = new int[n];
        int[] nextGreater = new int[n];
        Arrays.fill(prevGreater, Integer.MAX_VALUE);
        Arrays.fill(nextGreater, Integer.MAX_VALUE);

        Stack<Integer> stack = new Stack<>();
        // find nextGreater elements
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] <= arr[i]) {
                nextGreater[stack.pop()] = arr[i];
            }
            stack.push(i);
        }

        stack.clear();
        // find prevGreater elements -> 根据约定相等时只能规定右边的更大，所以无法直接倒着遍历来处理这种情况！
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] <= arr[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) prevGreater[i] = arr[stack.peek()];
            stack.push(i);
        }

        int cost = 0;
        for (int i = 0; i < n; i++) {
            int x = Math.min(prevGreater[i], nextGreater[i]);
            if (x != Integer.MAX_VALUE) cost += arr[i] * x;
        }
        return cost;
    }

    // S2: dp
    // time = O(n^3), space = O(n^2)
    public int mctFromLeafValues2(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(dp[i], Integer.MAX_VALUE);
        int[][] large = new int[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(large[i], Integer.MIN_VALUE);
        int sum = 0;
        for (int i = 0; i < n; i++) {
            dp[i][i] = arr[i];
            large[i][i] = arr[i];
            sum += arr[i];
        }

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                for (int k = i; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + large[i][k] * large[k + 1][j]);
                    large[i][j] = Math.max(large[i][k], large[k + 1][j]);
                }
            }
        }
        // sum is the total sum of all leaf node number, so we must deduct them to get non-leaf sum!
        return dp[0][n - 1] - sum;
    }
}
/**
 * 区间型dp
 * 最后一步merge
 * [i x x k] [x x x j]
 *    y1        y2
 *        res
 * dp[i][j] = min{max[i][k] * max[k+1][j] + dp[i][k] + dp[k+1][j]} over k: i ~ j-1
 * for int len - ...
 *  for int i = ...
 *      int j = i + len - 1;
 *      for k = i : j - 1;
 *      dp[i][j] = ...
 * S2
 * x y2 x x y3 x x x x
 * 任意2个neighbor相消，最小的数消失了，剩下的数是2个数之间较大的。
 * x a x x x x x
 * 最后剩下的肯定是这里面最大的数
 * x1 x2 x3 x4 x5 (x6) .. xn
 * 1 0 3 4
 * 2 [1] 3 4
 * 1和2相消，1消失，这个操作不会影响其他元素的相消，因为2依然被保留下来了，消失的只有1，即较小的那个数
 * 除了最大的数，其他元素都会被它左边或者右边greater number消掉，根据最优策略我们选择一个较小的
 * left: previous greater number or right: next greater number => monotonic stack
 * 如果有相同元素怎么办？
 * 2 1 3 3 => 消去哪个呢？
 * 约定：哪个才是greater number? 2个3在一起，永远认为靠右边的更大。
 */