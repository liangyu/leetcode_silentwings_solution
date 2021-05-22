package LC001_300;
import java.util.*;
public class LC135_Candy {
    /**
     * There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.
     *
     * You are giving candies to these children subjected to the following requirements:
     *
     * Each child must have at least one candy.
     * Children with a higher rating get more candies than their neighbors.
     * Return the minimum number of candies you need to have to distribute the candies to the children.
     *
     * Input: ratings = [1,0,2]
     * Output: 5
     *
     * Constraints:
     *
     * n == ratings.length
     * 1 <= n <= 2 * 10^4
     * 0 <= ratings[i] <= 2 * 10^4
     * @param ratings
     * @return
     */
    // S1： 最容易理解的解法！！！
    // time = O(n), space = O(n)
    public int candy(int[] ratings) {
        // corner case
        if (ratings == null || ratings.length == 0) return 0;

        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);

        for (int i = 1; i < n; i++) { // 从左到右扫一遍
            if (candies[i] > candies[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        for (int i = n - 2; i >= 0; i--) { // 从右到左再扫一遍
            if (candies[i] > candies[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        int res = 0;
        for (int c : candies) res += c; // 求下加和
        return res;
    }

    // S2: 最优解！！！
    // time = O(n), space = O(1)
    public int candy2(int[] ratings) {
        // corner case
        if (ratings == null || ratings.length == 0) return 0;

        int res = 1, prev = 1, down = 0;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] >= ratings[i - 1]) {
                if (down > 0) {
                   res += down * (down + 1) / 2;
                   if (down >= prev) res += down - prev + 1;
                   down = 0;
                   prev = 1;
                }
                prev = ratings[i] == ratings[i - 1] ? 1 : prev + 1;
                res += prev;
            } else down++;
        }
        if (down > 0) {
            res += down * (1 + down) / 2;
            if (down >= prev) res += down - prev + 1;
        }
        return res;
    }
}
