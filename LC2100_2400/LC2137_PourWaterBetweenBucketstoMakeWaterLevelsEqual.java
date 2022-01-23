package LC2100_2400;

public class LC2137_PourWaterBetweenBucketstoMakeWaterLevelsEqual {
    /**
     * You have n buckets each containing some gallons of water in it, represented by a 0-indexed integer array buckets,
     * where the ith bucket contains buckets[i] gallons of water. You are also given an integer loss.
     *
     * You want to make the amount of water in each bucket equal. You can pour any amount of water from one bucket to
     * another bucket (not necessarily an integer). However, every time you pour k gallons of water, you spill loss
     * percent of k.
     *
     * Return the maximum amount of water in each bucket after making the amount of water equal. Answers within 10-5 of
     * the actual answer will be accepted.
     *
     * Input: buckets = [1,2,7], loss = 80
     * Output: 2.00000
     *
     * Constraints:
     *
     * 1 <= buckets.length <= 10^5
     * 0 <= buckets[i] <= 10^5
     * 0 <= loss <= 99
     * @param buckets
     * @param loss
     * @return
     */
    // time = O(nlogn), space = O(1)
    public double equalizeWater(int[] buckets, int loss) {
        int sum = 0;
        for (int x : buckets) sum += x;
        double left = 0, right = sum;
        while (left + 1e-5 < right) {
            double mid = left + (right - left) / 2;
            if (helper(buckets, mid, loss * 1.0 / 100)) left = mid;
            else right = mid;
        }
        return left;
    }

    private boolean helper(int[] nums, double t, double loss) {
        double accept = 0, give = 0;
        for (int x : nums) {
            if (t > x) {
                accept += (t - x) / (1 - loss);
            } else {
                give += x - t;
            }
        }
        if (accept <= give) return true;
        return false;
    }
}
