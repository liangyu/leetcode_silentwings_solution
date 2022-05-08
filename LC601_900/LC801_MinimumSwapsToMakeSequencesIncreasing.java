package LC601_900;
import java.util.*;
public class LC801_MinimumSwapsToMakeSequencesIncreasing {
    /**
     * You are given two integer arrays of the same length nums1 and nums2. In one operation, you are allowed to swap
     * nums1[i] with nums2[i].
     *
     * For example, if nums1 = [1,2,3,8], and nums2 = [5,6,7,4], you can swap the element at i = 3 to obtain
     * nums1 = [1,2,3,4] and nums2 = [5,6,7,8].
     * Return the minimum number of needed operations to make nums1 and nums2 strictly increasing. The test cases are
     * generated so that the given input always makes it possible.
     *
     * An array arr is strictly increasing if and only if arr[0] < arr[1] < arr[2] < ... < arr[arr.length - 1].
     *
     * Input: nums1 = [1,3,5,4], nums2 = [1,2,3,7]
     * Output: 1
     *
     * Constraints:
     *
     * 2 <= nums1.length <= 10^5
     * nums2.length == nums1.length
     * 0 <= nums1[i], nums2[i] <= 2 * 10^5
     * @param nums1
     * @param nums2
     * @return
     */
    // S1: dp
    // time = O(n), space = O(1)
    public int minSwap(int[] nums1, int[] nums2) {
        int unchanged = 0, changed = 1;
        int n = nums1.length;

        for (int i = 1; i < n; i++) {
            int unchanged2 = unchanged, changed2 = changed;
            unchanged = Integer.MAX_VALUE; // 注意一开始一上来还是要先初始化为无穷大，因为之前的0,1已经是i=0时的有效解了！
            changed = Integer.MAX_VALUE;

            if (nums1[i - 1] < nums1[i] && nums2[i - 1] < nums2[i]) {
                unchanged = Math.min(unchanged, unchanged2);
                changed = Math.min(changed, changed2 + 1);
            }
            if (nums1[i - 1] < nums2[i] && nums2[i - 1] < nums1[i]) {
                unchanged = Math.min(unchanged, changed2);
                changed = Math.min(changed, unchanged2 + 1);
            }
        }
        return Math.min(unchanged, changed);
    }

    // S2: dp
    // time = O(n), space = O(n)
    public int minSwap2(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) Arrays.fill(dp[i], Integer.MAX_VALUE);
        dp[0][0] = 0;
        dp[0][1] = 1;

        for (int i = 1; i < n; i++) {
            if (nums1[i - 1] < nums1[i] && nums2[i - 1] < nums2[i]) {
                dp[i][0] = Math.min(dp[i][0], dp[i - 1][0]);
                dp[i][1] = Math.min(dp[i][1], dp[i - 1][1] + 1);
            }
            if (nums1[i - 1] < nums2[i] && nums2[i - 1] < nums1[i]) {
                dp[i][0] = Math.min(dp[i][0], dp[i - 1][1]);
                dp[i][1] = Math.min(dp[i][1], dp[i - 1][0] + 1);
            }
        }
        return Math.min(dp[n - 1][0], dp[n - 1][1]);
    }
}
/**
 * 第i个位置交不交换的话只取决于它前面i-1个位置 -> dp
 * f[i][0]: 第i个位置没有交换过，且前i个位置都已经严格递增了的条件下，最小交换次数是多少
 * f[i][1]: 在第i个位置交换之后，让前面递增的最小交换次数
 * f[i][0] = f[i-1][0]  (A[i-1] < A[i] && B[i - 1] < B[i])
 *           f[i-1][1]  (..)
 * f[i][1] = f[i-1][0] + 1 (会交换一下)
 *           f[i-1][1] + 1
 * res = Math.min(f[i-1][0], f[i-1][1])
 * time = O(n)
 * A[i]
 * B[i]
 *
 * B[i]
 * A[i]
 *
 * A[i-1]
 * B[i-1]
 *
 * B[i-1]
 * A[i-1]
 *
 * dp[i][0], dp[i][1] <= dp[i-1][0], dp[i-1][1]
 * dp[i][0]: minimum number of swaps to make both sequences A[0:i], B[0:i] strictly increasing by not swapping A[i] and B[i]
 * dp[i][1]: minimum number of swaps to make both sequences A[0:i], B[0:i] strictly increasing by swapping A[i] and B[i]
 * 今天状态只取决于昨天状态
 */