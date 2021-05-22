package LC1801_2100;
import java.util.*;
public class LC1822_SignoftheProductofanArray {
    /**
     * There is a function signFunc(x) that returns:
     *
     * 1 if x is positive.
     * -1 if x is negative.
     * 0 if x is equal to 0.
     * You are given an integer array nums. Let product be the product of all values in the array nums.
     *
     * Return signFunc(product).
     *
     * Input: nums = [-1,-2,-3,-4,3,2,1]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * -100 <= nums[i] <= 100
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int arraySign(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int sign = 1;
        for (int n : nums) {
            if (n == 0) return 0;
            else if (n < 0) sign = -sign;
        }
        return sign;
    }
}
