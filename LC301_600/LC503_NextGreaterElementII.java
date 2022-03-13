package LC301_600;
import java.util.*;
public class LC503_NextGreaterElementII {
    /**
     * Given a circular integer array nums (i.e., the next element of nums[nums.length - 1] is nums[0]), return the next
     * greater number for every element in nums.
     *
     * The next greater number of a number x is the first greater number to its traversing-order next in the array,
     * which means you could search circularly to find its next greater number. If it doesn't exist, return -1 for this
     * number.
     *
     * Input: nums = [1,2,1]
     * Output: [2,-1,2]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^4
     * -10^9 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[n];
        Arrays.fill(res, -1);

        for (int i = 0; i < 2 * n; i++) {
            int j = i % n;
            while (!stack.isEmpty() && nums[stack.peek()] < nums[j]) {
                res[stack.pop()] = nums[j];
            }
            stack.push(j);
        }
        return res;
    }
}
/**
 * 4 2 3 [4 2 3]
 * double一下，线性数组 -> 单调栈
 */
