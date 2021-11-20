package LC1801_2100;
import java.util.*;
public class LC2070_MostBeautifulItemforEachQuery {
    /**
     * You are given a 2D integer array items where items[i] = [pricei, beautyi] denotes the price and beauty of an item
     * respectively.
     *
     * You are also given a 0-indexed integer array queries. For each queries[j], you want to determine the maximum
     * beauty of an item whose price is less than or equal to queries[j]. If no such item exists, then the answer to
     * this query is 0.
     *
     * Return an array answer of the same length as queries where answer[j] is the answer to the jth query.
     *
     * Input: items = [[1,2],[3,2],[2,4],[5,6],[3,5]], queries = [1,2,3,4,5,6]
     * Output: [2,4,5,5,6,6]
     *
     * Constraints:
     *
     * 1 <= items.length, queries.length <= 10^5
     * items[i].length == 2
     * 1 <= pricei, beautyi, queries[j] <= 10^9
     * @param items
     * @param queries
     * @return
     */
    // time = O((m + n) * logn), space = O(n)
    public int[] maximumBeauty(int[][] items, int[] queries) {
        Arrays.sort(items, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]); // O(nlogn)
        int[] res = new int[queries.length];

        int n = items.length, max = 0;
        int[] maxVal = new int[n];
        for (int i = 0; i < n; i++) {
            max = Math.max(max, items[i][1]);
            maxVal[i] = max;
        }

        for (int i = 0; i < queries.length; i++) { // O(mlogn)
            int idx = lowerBound(items, queries[i]);
            res[i] = (idx >= 0 && idx < n ? maxVal[idx] : 0);
        }
        return res;
    }

    private int lowerBound(int[][] nums, int t) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (nums[mid][0] <= t) left = mid;
            else right = mid - 1;
        }
        return nums[left][0] <= t ? left : left - 1;
    }
}
