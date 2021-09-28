package LC901_1200;
import java.util.*;
public class LC995_MinimumNumberofKConsecutiveBitFlips {
    /**
     * In an array nums containing only 0s and 1s, a k-bit flip consists of choosing a (contiguous) subarray of length
     * k and simultaneously changing every 0 in the subarray to 1, and every 1 in the subarray to 0.
     *
     * Return the minimum number of k-bit flips required so that there is no 0 in the array.  If it is not possible,
     * return -1.
     *
     * Input: nums = [0,1,0], k = 1
     * Output: 2
     *
     * Note:
     *
     * 1 <= nums.length <= 30000
     * 1 <= k <= nums.length
     * @param nums
     * @param k
     * @return
     */
    // S1: diff array
    // time = O(n), space = O(n)
    public int minKBitFlips(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] diff = new int[n + 1]; // 扩展一位，使diff[i + k]--不会越界！

        int flips = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            flips += diff[i];
            if (nums[i] + flips % 2 == 1) continue; // 判断奇偶性
            if (i + k - 1 >= n) return -1;
            flips++;
            diff[i + k]--;
            count++;
        }
        return count;
    }

    // S2: sliding window
    // time = O(n), space = O(1)
    public int minKBitFlips2(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length, flips = 0, count = 0;
        for (int i = 0; i < n; i++) {
            if (i >= k && nums[i - k] > 1) {
                flips++;
                nums[i - k] -= 2;
            }
            if (nums[i] + flips % 2 == 1) continue;
            if (i + k > n) return -1;
            flips++;
            nums[i] += 2; // 用nums[i]范围之外的数，比如2来表示已经翻转过
            count++;
        }
        return count;
    }
}
/**
 * 1 1 [0 x x 1] x x x x  -> 前面搞定了就不要去动了，百害而无一利
 * 1 1 1 [0 x x x] x x x x
 * 1 [1 0 x x] 1 x x x x  -> 并没有用，不会更优，最多持平
 * [1 1 0 x] x x x x x x
 * 突破口：最优策略就是个贪心法
 * 1 1 1 1 1 1 1 1 [0 0 ] -> return -1 每次都要4个做变换
 *
 * => O(nk) 遍历每个元素，如果是0，那么从左往右数k个都作flip，滑窗再往右走一下
 * 不需要每个元素都flip，只要首尾flip
 * 1 1 [0 x x 1] x x x x x   i ~ i+k-1
 *      s     e e + 1
 * [s,e]
 * A[i] + flips[i] % 2
 * flips[i] = flips[i-1] + diff[i]
 * diff[i]: 相邻2个元素A[i]与A[i-1]的翻转次数之差 =>
 * diff[s] += 1
 * diff[e+1] -= 1
 * 通过累加差分数组可以得到当前位置需要翻转的次数,flip来表示累加值
 * 遍历到nums[i]时，if (nums[i] + flip % 2 == 0) -> 当前实际元素为0，需要翻转区间[i, i+k-1]，可以flip++,diff[i+k]--
 * 注意如果 i + k > n 则越界无法翻转 -> return -1;
 *
 * S2: 若要翻转从位置 i 开始的子数组，可以将 nums[i] 加 2，这样当遍历到位置 i'时，如果nums[i'-k] > 1,说明在i'-k上发生了翻转
 */
