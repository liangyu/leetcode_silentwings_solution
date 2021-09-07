package LC901_1200;

public class LC1011_CapacityToShipPackagesWithinDDays {
    /**
     * A conveyor belt has packages that must be shipped from one port to another within days days.
     *
     * The ith package on the conveyor belt has a weight of weights[i]. Each day, we load the ship with packages on
     * the conveyor belt (in the order given by weights). We may not load more weight than the maximum weight capacity
     * of the ship.
     *
     * Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being
     * shipped within days days.
     *
     * Input: weights = [1,2,3,4,5,6,7,8,9,10], days = 5
     * Output: 15
     *
     * Constraints:
     *
     * 1 <= days <= weights.length <= 5 * 10^4
     * 1 <= weights[i] <= 500
     * @param weights
     * @param D
     * @return
     */
    // time = O(n), space = O(1)
    public int shipWithinDays(int[] weights, int D) {
        // corner case
        if (weights == null || weights.length == 0) return 0;

        int sum = 0, max = 0;
        for (int weight : weights) {
            sum += weight;
            max = Math.max(max, weight);
        }

        int left = max, right = sum;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (!checkOK(weights, mid, D)) left = mid + 1;
            else right = mid;
        }
        return left; // 一定有解！
    }

    private boolean checkOK(int[] weights, int cap, int D) {
        int count = 0;
        for (int i = 0; i < weights.length; i++) {
            int j = i, sum = 0;
            while (j < weights.length && sum + weights[j] <= cap) sum += weights[j++];
            count++;
            if (count > D) return false;
            i = j - 1;
        }
        return true;
    }
}
/**
 * ref: LC410
 * dp[i][j]: nums[0:i] divided into j groups
 * min{max{dp[k][j-1], sum[k:i]}} over k  前k个数分成j-1组
 * O(n^3)
 * BS 本质是"猜"，问啥猜啥，最多猜32次
 */