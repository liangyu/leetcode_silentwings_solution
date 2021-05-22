package LC001_300;
import java.util.*;
public class LC169_MajorityElement {
    /**
     * Given an array nums of size n, return the majority element.
     *
     * The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority
     * element always exists in the array.
     *
     * Input: nums = [3,2,3]
     * Output: 3
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 5 * 10^4
     * -2^31 <= nums[i] <= 2^31 - 1
     *
     *
     * Follow-up: Could you solve the problem in linear time and in O(1) space?
     * @param nums
     * @return
     */
    // Moore voting algorithm
    // 每次都找出一对不同的元素，从数组中删掉，直到数组为空或只有一种元素。
    // 不难证明，如果存在元素e出现频率超过半数，那么数组中最后剩下的就只有e。
    // time = O(n), space = O(1)
    public int majorityElement(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException();
        }

        int count = 0, res = 0;
        for (int num : nums) {
            if (count == 0) res = num;
            if (res != num) count--;
            else count++;
        }
        return res;
    }
}
