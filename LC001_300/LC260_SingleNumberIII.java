package LC001_300;
import java.util.*;
public class LC260_SingleNumberIII {
    /**
     * Given an integer array nums, in which exactly two elements appear only once and all the other elements appear
     * exactly twice. Find the two elements that appear only once. You can return the answer in any order.
     *
     * You must write an algorithm that runs in linear runtime complexity and uses only constant extra space.
     *
     * Input: nums = [1,2,1,3,2,5]
     * Output: [3,5]
     *
     * Constraints:
     *
     * 2 <= nums.length <= 3 * 10^4
     * -2^31 <= nums[i] <= 2^31 - 1
     * Each integer in nums will appear twice, only two integers will appear once.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int[] singleNumber(int[] nums) {
        // corner case
        if (nums == null || nums.length < 2) return new int[0];

        int xor = 0;
        for (int n : nums) xor ^= n;

        int mask = 1;
        while ((mask & xor) == 0) mask <<= 1; // 筛选出xor中从右往左第一个为1的那位，在那位置上两个数肯定一个位0， 一个位1 -> 分组
        int group1 = 0, group2 = 0;
        for (int n : nums) {
            if ((n & mask) == 0) group1 ^= n;
            else group2 ^= n;
        }
        return new int[]{group1, group2};
    }
}
