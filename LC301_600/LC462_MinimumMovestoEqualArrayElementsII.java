package LC301_600;
import java.util.*;
public class LC462_MinimumMovestoEqualArrayElementsII {
    /**
     * Given an integer array nums of size n, return the minimum number of moves required to make all array elements equal.
     *
     * In one move, you can increment or decrement an element of the array by 1.
     *
     * Input: nums = [1,2,3]
     * Output: 2
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= nums.length <= 10^5
     * -10^9 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // S1: find median
    // time = O(nlogn), space = O(1)
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length, res = 0;
        int median = nums[(n - 1)/ 2];
        for (int x : nums) res += Math.abs(x -  median);
        return res;
    }

    // S2: without finding median
    // time = O(nlogn), space = O(1)
    public int minMoves2_2(int[] nums) {
        Arrays.sort(nums);

        int left = 0, right = nums.length - 1;
        int res = 0;
        while (left < right) {
            res += nums[right--] - nums[left++];
        }
        return res;
    }

    // S3: quick sort (最优解!)
    // time = O(nlogn), space = O(1)
    public int minMoves2_3(int[] nums) {
        int n = nums.length, res = 0;
        int median = quick_select(nums, 0, n - 1, (n - 1) / 2 + 1);
        for (int x : nums) res += Math.abs(x - median);
        return res;
    }

    private int quick_select(int[] q, int l, int r, int k) {
        if (l == r) return q[l];

        int x = q[l + (r - l) / 2], i = l - 1, j = r + 1;
        while (i < j) {
            while (q[++i] < x);
            while (q[--j] > x);
            if (i < j) {
                int t = q[i];
                q[i] = q[j];
                q[j] = t;
            }
        }
        int sl = j - l + 1;
        if (k <= sl) return quick_select(q, l, j, k);
        return quick_select(q, j + 1, r, k - sl);
    }
}
/**
 * ref: LC296
 */
