package LC301_600;

public class LC487_MaxConsecutiveOnesII {
    /**
     * Given a binary array nums, return the maximum number of consecutive 1's in the array if you can flip at most
     * one 0.
     *
     * Input: nums = [1,0,1,1,0]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * nums[i] is either 0 or 1.
     *
     * Follow up: What if the input numbers come in one by one as an infinite stream? In other words, you can't store
     * all numbers coming from the stream as it's too large to hold in memory. Could you solve it efficiently?
     * @param nums
     * @return
     */
    // S1: DP
    // time = O(n), space = O(n)
    public int findMaxConsecutiveOnes(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[][] f = new int[n + 1][2];

        int res = 0;
        for (int i = 1; i <= n; i++) {
            if (nums[i - 1] == 1) {
                f[i][0] = f[i - 1][0] + 1;
                f[i][1] = f[i - 1][1] + 1;
            } else {
                f[i][0] = 0;
                f[i][1] = f[i - 1][0] + 1;
            }
            res = Math.max(res, f[i][1]); // f[i][1]用过一次反转权利，一定比f[i][0]更优
        }
        return res;
    }

    // S1.2: DP
    // time = O(n), space = O(1)
    public int findMaxConsecutiveOnes12(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        // count1: the longest consecutive 1s ending here with no flip
        // count2: the longest consecutive 1s ending here with one flip
        int count1 = 0, count2 = 0, res = 0;
        for (int x : nums) {
            if (x == 0) {
                count2 = count1 + 1; // 注意：这里用的是上一轮的count1
                count1 = 0;
            } else {
                count2++;
                count1++;
            }
            res = Math.max(res, count2);
        }
        return res;
    }

    // S2: Two Pointers
    // time = O(n), space = O(1)
    public int findMaxConsecutiveOnes2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int i = 0, j = 0, zeroIdx = -1, res = 0;
        while (j < nums.length) {
            if (nums[j] == 0) {
                i = zeroIdx + 1;
                zeroIdx = j;
            }
            res = Math.max(res, j - i + 1);
            j++;
        }
        return res;
    }
}
/**
 * 双状态dp
 * 反转权利什么时候用 -> 前面一堆1
 * 2个计数器，没有使用反转权利最多有多少个；另一个是使用了反转最多有多少个
 * if (nums[k] == 0)
 * count1[k] = 0
 * count2[k] = count1[k-1] + 1
 *
 * if (nums[k] == 1)
 * count1[k] = count1[k-1] + 1
 * count2[k] = count2[k-1] + 1
 *
 * Follow up infinite stream - Maintain the 'left', 'right' and 'last seen zero'. As soon as one encounters another 0
 * in stream, record the maxLength; make leftIndex = zeroIndex + 1, and set zeroIndex = right = curIndex.
 * Again continue processing the stream.
 */