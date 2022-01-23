package LC2101_2400;
import java.util.*;
public class LC2107_NumberofUniqueFlavorsAfterSharingKCandies {
    /**
     * You are given a 0-indexed integer array candies, where candies[i] represents the flavor of the ith candy. Your
     * mom wants you to share these candies with your little sister by giving her k consecutive candies, but you want
     * to keep as many flavors of candies as possible.
     *
     * Return the maximum number of unique flavors of candy you can keep after sharing with your sister.
     *
     * Input: candies = [1,2,2,3,4,3], k = 3
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= candies.length <= 10^5
     * 1 <= candies[i] <= 10^5
     * 0 <= k <= candies.length
     * @param candies
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int shareCandies(int[] candies, int k) {
        int n = candies.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) map.put(candies[i], map.getOrDefault(candies[i], 0) + 1);

        int res = 0;
        for (int i = 0; i < n; i++) {
            if (i >= k) map.put(candies[i - k], map.getOrDefault(candies[i - k], 0) + 1);
            map.put(candies[i], map.get(candies[i]) - 1);
            if (map.get(candies[i]) == 0) map.remove(candies[i]);
            if (i >= k - 1) res = Math.max(res, map.size());
        }
        return res;
    }
}
