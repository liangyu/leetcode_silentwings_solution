package LC301_600;
import java.util.*;
public class LC334_IncreasingTripletSubsequence {
    /**
     * Given an integer array nums, return true if there exists a triple of indices (i, j, k) such that i < j < k and
     * nums[i] < nums[j] < nums[k]. If no such indices exists, return false.
     *
     * Input: nums = [1,2,3,4,5]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5 * 10^5
     * -2^31 <= nums[i] <= 2^31 - 1
     *
     * Follow up: Could you implement a solution that runs in O(n) time complexity and O(1) space complexity?
     * @param nums
     * @return
     */
    // S1: left / right merge
    // time = O(n), space = O(n)
    public boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        int[] leftMin = new int[n];
        int[] rightMax = new int[n];

        leftMin[0] = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) leftMin[i] = Math.min(leftMin[i - 1], nums[i - 1]);

        rightMax[n - 1] = Integer.MIN_VALUE;
        for (int i = n - 2; i >= 0; i--) rightMax[i] = Math.max(rightMax[i + 1], nums[i + 1]);

        for (int i = 0; i < n; i++) {
            if (leftMin[i] < nums[i] && nums[i] < rightMax[i]) return true;
        }
        return false;
    }

    // S2: LIS
    // time = O(nlog3) = O(n), space = O(1)
    public boolean increasingTriplet2(int[] nums) {
        List<Integer> buffer = new ArrayList<>();
        for (int x : nums) {
            if (buffer.size() == 0 || x > buffer.get(buffer.size() - 1)) buffer.add(x);
            else {
                if (buffer.get(0) > x) buffer.set(0, x);
                else if (buffer.get(0) < x && x < buffer.get(1)) buffer.set(1, x);
            }
            if (buffer.size() == 3) return true;
        }
        return false;
    }
}
/**
 * ref: LC300
 * 遍历中间元素，想知道j左边元素最小是多少，是的话就ok，再看j右边最大的，让不等号尽量成立
 * 都可以均摊是O(1)来实现
 * 从左往右撸一遍
 * 相当于维护一个变量，装的就是当前遇到的最小值
 * 每到一个地方，遇到的最小值
 * S2: 是否存在长度为3的LIS
 */