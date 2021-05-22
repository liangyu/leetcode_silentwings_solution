package LC301_600;
import java.util.*;
public class LC376_WiggleSubsequence {
    /**
     * Given an integer array nums, return the length of the longest wiggle sequence.
     *
     * A wiggle sequence is a sequence where the differences between successive numbers strictly alternate between
     * positive and negative. The first difference (if one exists) may be either positive or negative. A sequence with
     * fewer than two elements is trivially a wiggle sequence.
     *
     * For example, [1, 7, 4, 9, 2, 5] is a wiggle sequence because the differences (6, -3, 5, -7, 3) are alternately
     * positive and negative.
     * In contrast, [1, 4, 7, 2, 5] and [1, 7, 4, 5, 5] are not wiggle sequences, the first because its first two
     * differences are positive and the second because its last difference is zero.
     * A subsequence is obtained by deleting some elements (eventually, also zero) from the original sequence, leaving
     * the remaining elements in their original order.
     *
     * Input: nums = [1,7,4,9,2,5]
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] <= 1000
     *
     * @param nums
     * @return
     */
    // S1: Greedy
    // time = O(n), space = O(1)
    public int wiggleMaxLength(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int ret = 1, dir = -2; // dir 预设为一个特殊值，不干扰之后判断是否为拐点

        for (int i = 1; i < nums.length; i++) {
            int dir_prev = dir;

            if (nums[i] > nums[i - 1]) {
                dir = 1;
            } else if (nums[i] < nums[i - 1]) {
                dir = -1;
            } else { // dir = 0
                dir = dir_prev;
            }
            if (dir != dir_prev) { // nums[i] 是拐点
                ret += 1;
            }
        }
        return ret;
    }

    // S2: DP
    // time = O(n), space = O(1)
    public int wiggleMaxLength2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        // initialization
        int p = 1, q = 1; // 只有一个元素的话，自身就是一个wiggle sequence，所以初始值为1

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                q = p + 1;
            } else if (nums[i] < nums[i - 1]) {
                p = q + 1;
            } // == 的时候完全没帮助 => 直接跳过
        }
        return Math.max(p, q);
    }
}

/**
 * 找出原数组所有的拐点
 * 拐点定义 + -> -, - -> +
 * 如果是0的话，就继承上一个斜率的正负，不会出现0
 * 只有出现正负交替的时候，才算拐点
 * 没有任何拐点的话，初始值应为1
 *
 * S2: DP
 * 如果之前是向上 => 后面向下才有帮助，如果依然向上则没有帮助，长度 q = p + 1
 * 同样，如果之前是向下 => 新增向上，就得到一个更长的wiggle sequence，p = q + 1
 */
