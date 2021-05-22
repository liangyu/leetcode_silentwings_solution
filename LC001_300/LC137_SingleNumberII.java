package LC001_300;
import java.util.*;
public class LC137_SingleNumberII {
    /**
     * Given an integer array nums where every element appears three times except for one, which appears exactly once.
     * Find the single element and return it.
     *
     * Input: nums = [2,2,3,2]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 3 * 10^4
     * -2^31 <= nums[i] <= 2^31 - 1
     * Each element in nums appears exactly three times except for one element which appears once.
     *
     *
     * Follow up: Your algorithm should have a linear runtime complexity. Could you implement it without using extra
     * memory?
     * @param nums
     * @return
     */
    // S1:
    // time = O(n), space = O(1)
    public int singleNumber(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int[] bits = new int[32];
        for (int n : nums) {
            for (int i = 0; i < 32; i++) {
                bits[i] += (n >> i) & 1; // 将每一个数的每一位都分别加起来
            }
        }
        int res = 0;
        for (int i = 0; i < 32; i++) {
            res += (bits[i] % 3) << i; // 每一位对3取模，再左移i位回去
        }
        return res;
    }

    // S2: 01状态机
    // time = O(n), space = O(1)
    public int singleNumber2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int ones = 0, twos = 0;
        for (int n : nums) {
            ones = (ones ^ n) & ~twos;
            twos = (twos ^ n) & ~ones;
        }
        return ones;
    }
}

/**
 * 全部加起来，除以3，出现3次的肯定能被3整除
 * bits[10]
 * 可能为负数
 * -3214 % 10 = ？
 * 负号怎么存？？？
 * 符号位相加对3取模，也是正确的，就不用特别考虑了
 *
 * 用2个bit来实现对3取模的结果：00 01 10
 * int bits1, bits2
 * bits1(i), bits2(i) 共同来表示是00，01，10
 */
