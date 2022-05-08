package LC2101_2400;

public class LC2226_MaximumCandiesAllocatedtoKChildren {
    /**
     * You are given a 0-indexed integer array candies. Each element in the array denotes a pile of candies of size
     * candies[i]. You can divide each pile into any number of sub piles, but you cannot merge two piles together.
     *
     * You are also given an integer k. You should allocate piles of candies to k children such that each child gets the
     * same number of candies. Each child can take at most one pile of candies and some piles of candies may go unused.
     *
     * Return the maximum number of candies each child can get.
     *
     * Input: candies = [5,8,6], k = 3
     * Output: 5
     *
     * Input: candies = [2,5], k = 11
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= candies.length <= 10^5
     * 1 <= candies[i] <= 10^7
     * 1 <= k <= 10^12
     * @param candies
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int maximumCandies(int[] candies, long k) {
        int left = 0, right = (int) 1e7;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (helper(candies, mid, k)) left = mid;
            else right = mid - 1;
        }
        return left;
    }

    private boolean helper(int[] nums, int t, long k) {
        long count = 0;
        for (int x : nums) {
            count += x / t;
        }
        return count >= k;
    }
}
/**
 * 二分搜值
 */
