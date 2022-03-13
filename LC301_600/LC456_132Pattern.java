package LC301_600;
import java.util.*;
public class LC456_132Pattern {
    /**
     * Given an array of n integers nums, a 132 pattern is a subsequence of three integers nums[i], nums[j] and nums[k]
     * such that i < j < k and nums[i] < nums[k] < nums[j].
     *
     * Return true if there is a 132 pattern in nums, otherwise, return false.
     *
     * Input: nums = [1,2,3,4]
     * Output: false
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 2 * 10^5
     * -10^9 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public boolean find132pattern(int[] nums) {
        // corner case
        if (nums == null || nums.length < 3) return false;

        int n = nums.length;
        int[] leftMin = new int[n];
        leftMin[0] = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) leftMin[i] = Math.min(leftMin[i - 1], nums[i - 1]);

        Stack<Integer> stack = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            int second = Integer.MIN_VALUE; // focus on find nums[k], i.e., second
            // 从后往前维持一个单调递减栈，遇到转折点就check是否能做k
            while (!stack.isEmpty() && stack.peek() < nums[i]) second = stack.pop(); // 寻找当前能做nums[k]的最大的一个
            if (leftMin[i] < second) return true; // check是否存在一个i
            stack.push(nums[i]); // nums[i]无法做second,那就继续压入栈，继续维持一个从后往前的单调递减栈
        }
        return false;
    }
}
/**
 * 贪心想法
 * 以这中间这个数作为老大，左边这个就是老大之前所有元素里最小的一个
 * 挑选右边的，在这个数后面这些元素里面仅比它小的最大的一个
 * 从后往前开始，维持一个递减的序列
 * 总结：盯着中间的那个元素看，次大元素 -> 单调栈
 * 找一个仅此于该元素的，就从后往前单调递减的栈
 * 如果碰到一个比栈顶元素大的，就退栈
 */