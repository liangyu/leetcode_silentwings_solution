package LC2101_2400;
import java.util.*;
public class LC2172_MaximumANDSumofArray {
    /**
     * You are given an integer array nums of length n and an integer numSlots such that 2 * numSlots >= n. There are
     * numSlots slots numbered from 1 to numSlots.
     *
     * You have to place all n integers into the slots such that each slot contains at most two numbers. The AND sum of
     * a given placement is the sum of the bitwise AND of every number with its respective slot number.
     *
     * For example, the AND sum of placing the numbers [1, 3] into slot 1 and [4, 6] into slot 2 is equal to (1 AND 1)
     * + (3 AND 1) + (4 AND 2) + (6 AND 2) = 1 + 1 + 0 + 2 = 4.
     * Return the maximum possible AND sum of nums given numSlots slots.
     *
     * Input: nums = [1,2,3,4,5,6], numSlots = 3
     * Output: 9
     *
     * Input: nums = [1,3,10,4,7,1], numSlots = 9
     * Output: 24
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= numSlots <= 9
     * 1 <= n <= 2 * numSlots
     * 1 <= nums[i] <= 15
     * @param nums
     * @param numSlots
     * @return
     */
    // S1
    // time = O(n * m * 3^m), space = O(n * 3^m)
    public int maximumANDSum(int[] nums, int numSlots) {
        int n = nums.length, m = numSlots;
        int[][] dp = new int[n + 1][(int)Math.pow(3, m)];
        for (int i = 0; i <= n; i++) Arrays.fill(dp[i], Integer.MIN_VALUE / 2);
        dp[0][0] = 0;

        int res = 0;
        for (int i = 1; i <= n; i++) { // O(n)
            for (int state = 0; state < (int) Math.pow(3, m); state++) { // O(3^m)
                for (int j = 0; j < m; j++) { // O(m)
                    if (findBit(state, j) >= 1) {
                        // state第j位上扣掉1
                        dp[i][state] = Math.max(dp[i][state], dp[i - 1][state - (int) Math.pow(3, j)] + (nums[i - 1] & (j + 1)));
                    }
                }
                if (i == n) res = Math.max(res, dp[i][state]);
            }
        }
        return res;
    }

    private int findBit(int state, int j) {
        for (int i = 0; i < j; i++) state /= 3;
        return state % 3;
    }

    // S1.2: get rid of the 1st for loop above
    // time = O(m * 3^m), space = O(n * 3^m)
    public int maximumANDSum2(int[] nums, int numSlots) {
        int n = nums.length, m = numSlots;
        int[][] dp = new int[n + 1][(int)Math.pow(3, m)];
        for (int i = 0; i <= n; i++) Arrays.fill(dp[i], Integer.MIN_VALUE / 2);
        dp[0][0] = 0;

        int res = 0;
        for (int state = 0; state < (int) Math.pow(3, m); state++) {
            int i = 0;
            int temp = state;
            while (temp > 0) {
                i += temp % 3;
                temp /= 3;
            }
            if (i > n) continue; // 当i > n，也就是比实际数字量要多的时候，是没有实际意义的，直接continue
            for (int j = 0; j < m; j++) {
                if (findBit(state, j) >= 1) {
                    // state第j位上扣掉1
                    dp[i][state] = Math.max(dp[i][state], dp[i - 1][state - (int) Math.pow(3, j)] + (nums[i - 1] & (j + 1)));
                }
            }
            if (i == n) res = Math.max(res, dp[i][state]);
        }
        return res;
    }
}
/**
 * 带权二分图匹配，不用KM也能做 => 状压bitmask + dp
 * 没有任何长度为奇数的环就一定可以二分。
 * 并不是标准模板题，因为一个篮子里可以装2个数
 * dp[i][state]
 * 1. iterate over slots, using state to represent nums
 * for (int i = 0; i < numSlots; i++) { // 9
 *     for (int state = 0; state < (1 << n); state++) { // 2^18
 *         for (auto pickNums for i-th slot: avalable plans) { // 0 or 1 or 2   C(18,2) + C(18,1) + C(18,0) -> 18^2
 *             dp[i][state] = max{dp[i - 1][state - pickNums] + (pickNums & slot[i])}
 *         }
 *     }
 * }
 * time = 9 * 2^18 * [C(18,2) + C(18,1) + C(18,0)] = 7e8 -> TLE
 * 2. iterate over nums, using state to represent slots   012210221 -> 9位三进制数来表示篮子的状态
 * for (int i = 0; i < nums.size(); i++) { // 18
 *     for (int state = 0; state < (3 ^ m); state++) { // 3^9
 *          for (auto pickedSlot for i-th num: avalable plans) {  // 9
 *              dp[i][state] = max{dp[i-1][state - pickedSlot] + (nums[i] & pickedSlot)}
 *          }
 *     }
 * }
 * time = 18 * 3^9 * 9 = 3e6  ok
 * 第一个循环可以直接去掉，因为把state里每个bit位的数字加起来就知道现在的i有多少了！
 */
