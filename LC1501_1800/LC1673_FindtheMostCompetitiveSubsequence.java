package LC1501_1800;
import java.util.*;
public class LC1673_FindtheMostCompetitiveSubsequence {
    /**
     * Given an integer array nums and a positive integer k, return the most competitive subsequence of nums of size k.
     *
     * An array's subsequence is a resulting sequence obtained by erasing some (possibly zero) elements from the array.
     *
     * We define that a subsequence a is more competitive than a subsequence b (of the same length) if
     * in the first position where a and b differ, subsequence a has a number less than the corresponding number in b.
     * For example, [1,3,4] is more competitive than [1,3,5] because the first position they differ is
     * at the final number, and 4 is less than 5.
     *
     * Input: nums = [3,5,2,6], k = 2
     * Output: [2,6]
     *
     * Input: nums = [2,4,3,3,5,4,9,6], k = 4
     * Output: [2,3,3,4]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 105
     * 0 <= nums[i] <= 109
     * 1 <= k <= nums.length
     * @param nums
     * @param k
     * @return
     */
    // S1: 单调栈
    // time = O(n), space = O(n)
    public int[] mostCompetitive(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int count = nums.length - k; // 最多能够删除 len - k 个数组元素
        Stack<Integer> stack = new Stack<>();

        for (int n : nums) {
            if (stack.isEmpty() || n >= stack.peek()) { // 单调升序时可压入栈里
                stack.push(n);
            } else {
                while (count > 0 && !stack.isEmpty() && n < stack.peek()) { // 非单调升序且依然可以有配额删除元素时弹栈
                    stack.pop();
                    count--;
                }
                stack.push(n); // 弹栈完成后切记要将当前元素压栈！！！
            }
        }
        while (count-- > 0) stack.pop(); // 出了for loop之后很有可能栈中元素依然超过k个，那么此刻要把多余的也要弹出直至只留k个
        int[] res = new int[k];
        int i = k - 1;
        while (!stack.isEmpty()) {
            res[i--] = stack.pop(); // 从栈顶开始逆序倒入res array之中
        }
        return res;
    }
}

/**
 *   1 3 5 7 9 6        单调栈，maintain k-size
 *   1 3 5 (7 9) 6      递增序列 -> 最多扔 n - k 个元素，超过的话只能老老实实放在栈顶上
 */
