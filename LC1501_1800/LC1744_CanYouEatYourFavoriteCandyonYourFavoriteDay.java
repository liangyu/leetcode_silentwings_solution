package LC1501_1800;

public class LC1744_CanYouEatYourFavoriteCandyonYourFavoriteDay {
    /**
     * You are given a (0-indexed) array of positive integers candiesCount where candiesCount[i] represents the number
     * of candies of the ith type you have. You are also given a 2D array queries where queries[i] = [favoriteTypei,
     * favoriteDayi, dailyCapi].
     *
     * You play a game with the following rules:
     *
     * You start eating candies on day 0.
     * You cannot eat any candy of type i unless you have eaten all candies of type i - 1.
     * You must eat at least one candy per day until you have eaten all the candies.
     * Construct a boolean array answer such that answer.length == queries.length and answer[i] is true if you can eat
     * a candy of type favoriteTypei on day favoriteDayi without eating more than dailyCapi candies on any day,
     * and false otherwise. Note that you can eat different types of candy on the same day, provided that you follow rule 2.
     *
     * Return the constructed array answer.
     *
     * Input: candiesCount = [7,4,5,3,8], queries = [[0,2,2],[4,2,4],[2,13,1000000000]]
     * Output: [true,false,true]
     *
     * Constraints:
     *
     * 1 <= candiesCount.length <= 10^5
     * 1 <= candiesCount[i] <= 10^5
     * 1 <= queries.length <= 10^5
     * queries[i].length == 3
     * 0 <= favoriteTypei < candiesCount.length
     * 0 <= favoriteDayi <= 10^9
     * 1 <= dailyCapi <= 10^9
     * @param candiesCount
     * @param queries
     * @return
     */
    // time = O(n + m), space = O(n)
    public boolean[] canEat(int[] candiesCount, int[][] queries) {
        int n = candiesCount.length;
        long[] presum = new long[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + candiesCount[i - 1];

        boolean[] res = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int t = queries[i][0] + 1;
            long d = queries[i][1] + 1, cap = queries[i][2];

            if (1 * (d - 1) >= presum[t]) res[i] = false;
            else if (cap * d <= presum[t - 1]) res[i] = false;
            else res[i] = true;
        }
        return res;
    }
}
/**
 * [t,d,cap]
 * 1. eat 1 candy per day: d-1 days we have eaten all the first t types
 *      1 * (d - 1) >= presum[t]
 * 2. eat cap candies per day: after d days, we can only eat up to the first t-1 types
 *      cap * d <= presum[t-1]
 * 在两者之间，可以自由调节，保证可以至少吃到一颗type = t的糖
 */