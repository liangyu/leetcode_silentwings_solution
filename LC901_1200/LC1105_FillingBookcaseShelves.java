package LC901_1200;

import java.util.Arrays;

public class LC1105_FillingBookcaseShelves {
    /**
     * You are given an array books where books[i] = [thicknessi, heighti] indicates the thickness and height of the
     * ith book. You are also given an integer shelfWidth.
     *
     * We want to place these books in order onto bookcase shelves that have a total width shelfWidth.
     *
     * We choose some of the books to place on this shelf such that the sum of their thickness is less than or equal
     * to shelfWidth, then build another level of the shelf of the bookcase so that the total height of the bookcase
     * has increased by the maximum height of the books we just put down. We repeat this process until there are no
     * more books to place.
     *
     * Note that at each step of the above process, the order of the books we place is the same order as the given
     * sequence of books.
     *
     * For example, if we have an ordered list of 5 books, we might place the first and second book onto the first
     * shelf, the third book on the second shelf, and the fourth and fifth book on the last shelf.
     * Return the minimum possible height that the total bookshelf can be after placing shelves in this manner.
     *
     * Input: books = [[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]], shelf_width = 4
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= books.length <= 1000
     * 1 <= thicknessi <= shelfWidth <= 1000
     * 1 <= heighti <= 1000
     * @param books
     * @param shelfWidth
     * @return
     */
    // time = O(n^2), space = O(n)
    public int minHeightShelves(int[][] books, int shelfWidth) {
        int n = books.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            int maxHeight = 0;
            int totalWidth = 0;
            for (int j = i; j >= 1; j--) {
                maxHeight = Math.max(maxHeight, books[j - 1][1]);
                totalWidth += books[j - 1][0];
                if (totalWidth > shelfWidth) break;
                dp[i] = Math.min(dp[i], dp[j - 1] + maxHeight);
            }
        }
        return dp[n];
    }
}
/**
 * dp第二类基本型
 * => minimize "the maximum of all the subarrays" -> dp
 * dp[i]: the minimum possible height for books[1:i]
 * 第一类区间型：明确会告诉你会分几个subarray，写成dp[i][k]
 * 但是这道题并没有告诉你分几个subarray，所以是第二类的基本型，1D就可以
 * dp[j] => j = 1, 2, 3, ..., i-1
 * x x x x j x x x i   dp[i]取决于dp[j]
 */
