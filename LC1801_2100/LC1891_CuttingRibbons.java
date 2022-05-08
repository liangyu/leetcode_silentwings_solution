package LC1801_2100;
import java.util.*;
public class LC1891_CuttingRibbons {
    /**
     * You are given an integer array ribbons, where ribbons[i] represents the length of the ith ribbon, and an integer
     * k. You may cut any of the ribbons into any number of segments of positive integer lengths, or perform no cuts
     * at all.
     *
     * For example, if you have a ribbon of length 4, you can:
     * Keep the ribbon of length 4,
     * Cut it into one ribbon of length 3 and one ribbon of length 1,
     * Cut it into two ribbons of length 2,
     * Cut it into one ribbon of length 2 and two ribbons of length 1, or
     * Cut it into four ribbons of length 1.
     * Your goal is to obtain k ribbons of all the same positive integer length. You are allowed to throw away any
     * excess ribbon as a result of cutting.
     *
     * Return the maximum possible positive integer length that you can obtain k ribbons of, or 0 if you cannot obtain
     * k ribbons of the same length.
     *
     * Input: ribbons = [9,7,5], k = 3
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= ribbons.length <= 10^5
     * 1 <= ribbons[i] <= 10^5
     * 1 <= k <= 10^9
     * @param ribbons
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int maxLength(int[] ribbons, int k) {
        // corner case
        if (ribbons == null || ribbons.length == 0 || k <= 0) return 0;

        int n = ribbons.length;
        long sum = 0;
        for (int r : ribbons) sum += r;
        if (sum < k) return 0;

        // B.S.
        int left = 1, right = 100000;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long amount = helper(ribbons, mid);
            if (amount >= k) left = mid + 1;
            else right = mid - 1;
        }
        return right;
    }

    private long helper(int[] nums, int t) {
        long res = 0;
        for (int n : nums) res += n / t;
        return res;
    }
}
/**
 * 非常套路的二分搜值。
 * 猜测长度为len，贪心数一下每一条能割出多少块。
 * 大于等于k的话就往小猜（注意此时的len已经是一个可行解），否则就往大猜。
 */
