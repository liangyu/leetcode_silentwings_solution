package LC1201_1500;

public class LC1482_MinimumNumberofDaystoMakemBouquets {
    /**
     * Given an integer array bloomDay, an integer m and an integer k.
     *
     * We need to make m bouquets. To make a bouquet, you need to use k adjacent flowers from the garden.
     *
     * The garden consists of n flowers, the ith flower will bloom in the bloomDay[i] and then can be used in exactly
     * one bouquet.
     *
     * Return the minimum number of days you need to wait to be able to make m bouquets from the garden. If it is
     * impossible to make m bouquets return -1.
     *
     * Input: bloomDay = [1,10,3,10,2], m = 3, k = 1
     * Output: 3
     *
     * Constraints:
     *
     * bloomDay.length == n
     * 1 <= n <= 10^5
     * 1 <= bloomDay[i] <= 10^9
     * 1 <= m <= 10^6
     * 1 <= k <= n
     * @param bloomDay
     * @param m
     * @param k
     * @return
     */
    // time = O(nlogS), space = O(1)
    public int minDays(int[] bloomDay, int m, int k) {
        // corner case
        if (bloomDay == null || bloomDay.length == 0) return -1;

        int n = bloomDay.length;
        if (n < m * k) return -1; // flower is not enough

        int left = 1, right = (int) 1e9;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isOK(bloomDay, m, k, mid)) right = mid;
            else left = mid + 1;
        }
        return left; // 也可以最后才check判断：return checkOK(bloomDay, m, k, left) ? left : -1;
    }

    private boolean isOK(int[] bloomDay, int m, int k, int day) {
        int n = bloomDay.length, consective = 0, bouquets = 0;
        for (int i = 0; i < n; i++) {
            if (bloomDay[i] > day) consective = 0;
            else {
                consective++;
                if (consective == k) {
                    bouquets++;
                    consective = 0;
                }
            }
        }
        return bouquets >= m;
    }
}
/**
 * BS
 * 单调性问题，越往后越容易达成目标，越往前越不容易 => bs
 * 先猜一天，然后扫一遍数组，看里面是不是有连续的k滑窗
 * 如果办不到 => 意味着日子太早，再等等，日子往后猜
 * 反之，能够达到 => 再贪心点，日子更早点？
 * 通过one pass可以验证的
 */
