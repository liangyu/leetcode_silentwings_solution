package LC2101_2400;
import java.util.*;
public class LC2171_RemovingMinimumNumberofMagicBeans {
    /**
     * You are given an array of positive integers beans, where each integer represents the number of magic beans found
     * in a particular magic bag.
     *
     * Remove any number of beans (possibly none) from each bag such that the number of beans in each remaining
     * non-empty bag (still containing at least one bean) is equal. Once a bean has been removed from a bag, you are not
     * allowed to return it to any of the bags.
     *
     * Return the minimum number of magic beans that you have to remove.
     *
     * Input: beans = [4,1,6,5]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= beans.length <= 10^5
     * 1 <= beans[i] <= 10^5
     * @param beans
     * @return
     */
    // time = O(nlogn), space = O(n)
    public long minimumRemoval(int[] beans) {
        Arrays.sort(beans);
        int n = beans.length;
        long[] presum = new long[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + beans[i - 1];

        long res = presum[n];
        for (int i = 1; i <= n; i++) {
            long count = presum[i - 1] + presum[n] - presum[i] - (long)(n - i) * beans[i - 1];
            res = Math.min(res, count);
        }
        return res;
    }
}
