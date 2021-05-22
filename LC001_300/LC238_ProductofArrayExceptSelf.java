package LC001_300;
import java.util.*;
public class LC238_ProductofArrayExceptSelf {
    /**
     * Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to
     * the product of all the elements of nums except nums[i].
     *
     * Example:
     *
     * Input:  [1,2,3,4]
     * Output: [24,12,8,6]
     * Constraint: It's guaranteed that the product of the elements of any prefix or suffix of the array
     * (including the whole array) fits in a 32 bit integer.
     *
     * Note: Please solve it without division and in O(n).
     *
     * Follow up:
     * Could you solve it with constant space complexity? (The output array does not count as extra space for
     * the purpose of space complexity analysis.)
     *
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int[] productExceptSelf(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return new int[0];

        int len = nums.length;
        int[] res = new int[len];
        res[0] = 1; // 左端点初始化为1
        for (int i = 1; i < len; i++) { // 注意由于是从当前访问元素向前看，所以i要从1开始
            res[i] = nums[i - 1] * res[i - 1]; // res[i - 1]：不包括nums[i - 1]的前面其他数的累积
        }
        // res = [1, 1, 2, 6]

        // 逆向遍历，补上第一遍遍历时每个元素向后的所有元素的累积
        int right = 1; // 将右端点设为1
        for (int i = len - 1; i >= 0; i--) {
            res[i] = res[i] * right; // 最右点已经是除自己之外的前面所有元素的累积，所以right从1开始
            right *= nums[i]; // right代表当前元素之后的所有元素的累积，所以必须乘以当前元素的值，以便loop到前一个元素时它能表示之后所有元素的累积
        }
        return res;
    }
}

/**
 *        1  2  3  4
 *        |1 2 3 4      i = 0 => nums[i] = 1, nums[i - 1] = ?, res[i - 1] = ?
 *         i
 *       1 | 2  3 4     i = 1 => nums[i] = 2, nums[i - 1] = 1, res[i - 1] = ?  => res[i] = 1
 *      i-1  i
 *      1 2 | 3 4      i = 2 => nums[i] = 3, nums[i - 1] = 2, res[i - 1] = 1 => res[i] = 2
 *       i-1  i        i = 3 => nums[i] = 4, nums[i - 1] = 3, res[i - 1] = 2 => res[i] = 6 (correct)
 *       ==> [?, 1, 2, 6]
 *
 *       反向          i= 3 => nums[i] = 4, res[i] = 6, res[i - 1] = 2 => res[i - 1] = nums[i] * res[i - 1] = 8
 *                    i = 2 => nums[i] = 2, res[i] = 2, res[i - 1] = 1 => res[i - 1] = res[i - 1] * product = 12
 *                    product = nums[2] * nums[3] => use a pointer "right" to track
 *                    i = 1 => nums[i] = 2, res[i] = 1, res[i - 1] = ? => res[i - 1] = res[i - 1] * right = 24
 *                    ==> res[i - 1] * 24 = 24 => res[i - 1] = res[0] = 1
 *
 */
