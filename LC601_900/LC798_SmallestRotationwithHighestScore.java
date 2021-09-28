package LC601_900;
import java.util.*;
public class LC798_SmallestRotationwithHighestScore {
    /**
     * Given an array nums, we may rotate it by a non-negative integer k so that the array becomes nums[k], nums[k+1],
     * nums[k+2], ... nums[nums.length - 1], nums[0], nums[1], ..., nums[k-1].  Afterward, any entries that are less
     * than or equal to their index are worth 1 point.
     *
     * For example, if we have [2, 4, 1, 3, 0], and we rotate by k = 2, it becomes [1, 3, 0, 2, 4]. This is worth 3
     * points because 1 > 0 [no points], 3 > 1 [no points], 0 <= 2 [one point], 2 <= 3 [one point], 4 <= 4 [one point].
     *
     * Over all possible rotations, return the rotation index k that corresponds to the highest score we could receive.
     * If there are multiple answers, return the smallest such index k.
     *
     * Input: [2, 3, 1, 4, 0]
     * Output: 3
     *
     * Note:
     *
     * nums will have length at most 20000.
     * nums[i] will be in the range [0, nums.length].
     * @param nums
     * @return
     */
    // S1： diff array
    // time = O(n), space = O(n)
    public int bestRotation(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] diff = new int[n]; // [0, n - 1]

        for (int i = 0; i < n; i++) {
            if (nums[i] <= i) {
                diff[0] += 1;
                diff[(i - (nums[i] - 1)) % n] -= 1;
                diff[(i + 1) % n] += 1;
            } else {
                diff[0] += 0;
                diff[(i + 1) % n] += 1;
                diff[(i + 1 + n - nums[i]) % n] -= 1;
            }
        }

        int score = 0, maxScore = 0, res = 0;
        for (int k = 0; k < n; k++) {
            score += diff[k];
            if (score > maxScore) {
                maxScore = score;
                res = k;
            }
        }
        return res;
    }

    // S1.2: 将两种情况合并的写法
    // time = O(n), space = O(n)
    public int bestRotation2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] diff = new int[n]; // [0, n - 1]

        for (int i = 0; i < n; i++) {
            diff[0] += 1;
            diff[(i - (nums[i] - 1) + n) % n] -= 1; // 加上n将以上两种情况合并
            diff[(i + 1) % n] += 1;
        }

        int score = 0, maxScore = 0, res = 0;
        for (int k = 0; k < n; k++) {
            score += diff[k];
            if (score > maxScore) {
                maxScore = score;
                res = k;
            }
        }
        return res;
    }
}
/**
 *    A[i]
 * 0 1 2 3 4 5 6
 * x x x x 2 x x
 * A[i]-1  i
 * 1110011
 * A[i] <= i
 * 0: +1
 *    +1
 *    ...      111...
 * i-(A[i]-1): 000...
 * i+1:        111...
 * 1e4
 *
 * diff[k]: the score difference between moving k steps and moving k-1 steps
 * 0<=k<=n
 *
 * diff[0] += 1;
 * diff[i-(A[i]-1)] -= 1;
 * diff[i+1] +=1;
 *
 * idx 0123456
 *     xxx4xxx
 * A[i] > i
 * 0: 000...
 * i+1: 111...
 * i+1+N-1-(A[i]-1) = i+1+N-A[i]: 000...
 *
 * diff[0] += 0
 * diff[i+1] += 1
 * diff[i+1+N-A[i] -= 1
 *
 * ref: LC1674
 */
