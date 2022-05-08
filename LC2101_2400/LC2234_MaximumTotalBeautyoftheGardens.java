package LC2101_2400;
import java.util.*;
public class LC2234_MaximumTotalBeautyoftheGardens {
    /**
     * Alice is a caretaker of n gardens and she wants to plant flowers to maximize the total beauty of all her gardens.
     *
     * You are given a 0-indexed integer array flowers of size n, where flowers[i] is the number of flowers already
     * planted in the ith garden. Flowers that are already planted cannot be removed. You are then given another integer
     * newFlowers, which is the maximum number of flowers that Alice can additionally plant. You are also given the
     * integers target, full, and partial.
     *
     * A garden is considered complete if it has at least target flowers. The total beauty of the gardens is then
     * determined as the sum of the following:
     *
     * The number of complete gardens multiplied by full.
     * The minimum number of flowers in any of the incomplete gardens multiplied by partial. If there are no incomplete
     * gardens, then this value will be 0.
     * Return the maximum total beauty that Alice can obtain after planting at most newFlowers flowers.
     *
     * Input: flowers = [1,3,1,1], newFlowers = 7, target = 6, full = 12, partial = 1
     * Output: 14
     *
     * Input: flowers = [2,4,5,3], newFlowers = 10, target = 5, full = 2, partial = 6
     * Output: 30
     *
     * Constraints:
     *
     * 1 <= flowers.length <= 10^5
     * 1 <= flowers[i], target <= 10^5
     * 1 <= newFlowers <= 10^10
     * 1 <= full, partial <= 10^5
     * @param flowers
     * @param newFlowers
     * @param target
     * @param full
     * @param partial
     * @return
     */
    // S1
    // time = O(nlogn), space = O(n)
    public long maximumBeauty(int[] flowers, long newFlowers, int target, int full, int partial) {
        Arrays.sort(flowers);

        int n = flowers.length;
        long res = 0;
        long[] presum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            flowers[i] = Math.min(flowers[i], target);
            presum[i + 1] = presum[i] + flowers[i];
        }

        int j = 0;
        for (int i = 0; i <= n; i++) {
            long remain = newFlowers - ((long) target * (n - i) - (presum[n] - presum[i]));
            if (remain >= 0) {
                while (j < i && remain >= (long) flowers[j] * j - presum[j]) j++;
                long fullVal = (long) full * (n - i);
                long partialVal = j == 0 ? 0 : (long) partial * Math.min(target - 1, (remain + presum[j]) / j);
                res = Math.max(res, fullVal + partialVal);
            }
            if (i < n && flowers[i] == target) break;
        }
        return res;
    }

    // S2
    // time = O(nlogn), space = O(n)
    public long maximumBeauty2(int[] flowers, long newFlowers, int target, int full, int partial) {
        Arrays.sort(flowers);

        int n = flowers.length, k = n - 1;
        while (k >= 0 && flowers[k] >= target) k--; // remove the flowers already being complete
        if (k < 0) return (long) n * full;

        n = k + 1;
        long[] presum = new long[n];
        for (int i = 0; i < n; i++) presum[i] = (i == 0 ? 0 : presum[i - 1]) + flowers[i];

        long[] diff = new long[n];
        for (int i = 0; i < n; i++) diff[i] = (long) flowers[i] * (i + 1) - presum[i];

        long res = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (newFlowers <= 0) break;
            if (presum[i] + newFlowers >= (long)(i + 1) * (target - 1)) {
                res = Math.max(res, (long)(target - 1) * partial + (long)(n - 1 - i) * full);
            } else {
                int p = lowerBound(diff, i, newFlowers);
                long total = presum[p] + newFlowers;
                long each = total / (p + 1);
                res = Math.max(res, each * partial + (long)(n - 1 - i) * full);
            }
            newFlowers -= (target - flowers[i]);
        }

        if (newFlowers > 0) res = Math.max(res, (long) n * full);
        return res + (long)(flowers.length - k - 1) * full;
    }

    private int lowerBound(long[] nums, int right, long t) {
        int left = 0;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (nums[mid] <= t) left = mid;
            else right = mid - 1;
        }
        return nums[left] <= t ? left : left - 1;
    }
}
/**
 * maximize: complete_garden_numbers * full + smallest_value_in_incomplete_gardens * partial
 * 把花园分成2部分，一部分补足complete,另一部分要补足短板
 * 把若干个最短的统一拔长
 * 只选那些数值比较大的补足为complete garden
 * presum[p] + NewFlorwrs >= nums[p] * (p + 1)   -> 找一个最大的p
 * nums[p]*(p+1) - presum[p] <= newFlower
 * diff[p] <= newFlower
 */