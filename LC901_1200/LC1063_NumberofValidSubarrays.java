package LC901_1200;
import java.util.*;
public class LC1063_NumberofValidSubarrays {
    /**
     * Given an integer array nums, return the number of non-empty subarrays with the leftmost element of the subarray
     * not larger than other elements in the subarray.
     *
     * A subarray is a contiguous part of an array.
     *
     * Input: nums = [1,4,2,5,3]
     * Output: 11
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5 * 10^4
     * 0 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int validSubarrays(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int count = 0, n = nums.length;
        for (int i = 0; i <= n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > (i == n ? Integer.MIN_VALUE : nums[i])) {
                count += i - stack.pop();
            }
            stack.push(i);
        }
        return count;
    }
}
/**
 * [5 6 7 8 9 5] 1
 *  ^
 *  i            j  => j-i个
 * 到任何一个比它小的元素就停 => next smaller element => 递增栈
 * 每个元素找一个对应的j即可。
 * -2 [1 2 3 4] -1
 * [1,2,3,4,5] INT_MIN -> 强制退栈
 * 特别注意的是，遍历结束的时候，栈里面可能还存有递增的元素，这些元素作为左端点的subarray都还未被计数。
 * 为了强制清空栈，我们可以nums的末尾添加一个INT_MIN，这样所有的nums的元素最终都会被退栈（退栈的时候会计算对应的subarray的数目）
 */