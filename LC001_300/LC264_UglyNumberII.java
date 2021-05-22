package LC001_300;
import java.util.*;
public class LC264_UglyNumberII {
    /**
     * An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
     *
     * Given an integer n, return the nth ugly number.
     *
     * Input: n = 10
     * Output: 12
     *
     * Constraints:
     *
     * 1 <= n <= 1690
     * @param n
     * @return
     */
    // time = O(n), space = O(n)
    public int nthUglyNumber(int n) {
        int[] nums = new int[n];
        int idx2 = 0, idx3 = 0, idx5 = 0;
        nums[0] = 1;

        for (int i = 1; i < n; i++) {
            nums[i] = Math.min(nums[idx2] * 2, Math.min(nums[idx3] * 3, nums[idx5] * 5));
            if (nums[i] == nums[idx2] * 2) idx2++;
            if (nums[i] == nums[idx3] * 3) idx3++;
            if (nums[i] == nums[idx5] * 5) idx5++;
        }
        return nums[n - 1];
    }
}
