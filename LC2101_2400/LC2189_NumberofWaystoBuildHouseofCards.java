package LC2101_2400;

public class LC2189_NumberofWaystoBuildHouseofCards {
    /**
     * You are given an integer n representing the number of playing cards you have. A house of cards meets the
     * following conditions:
     *
     * A house of cards consists of one or more rows of triangles and horizontal cards.
     * Triangles are created by leaning two cards against each other.
     * One card must be placed horizontally between all adjacent triangles in a row.
     * Any triangle on a row higher than the first must be placed on a horizontal card from the previous row.
     * Each triangle is placed in the leftmost available spot in the row.
     * Return the number of distinct house of cards you can build using all n cards. Two houses of cards are considered
     * distinct if there exists a row where the two houses contain a different number of cards.
     *
     * Input: n = 16
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 500
     * @param n
     * @return
     */
    // time = O(n^2), space = O(n^2)
    Integer[][] memo;
    public int houseOfCards(int n) {
        memo = new Integer[n + 1][n + 1];
        return dfs(n, 0);
    }

    private int dfs(int total, int cur) {
        if (total < 2 || cur >= total) return 0;
        if (memo[total][cur] != null) return memo[total][cur];

        int res = total % 3 == 2 ? 1 : 0;
        for (int i = cur + 1; i < total; i++) {
            if (i % 3 == 2) res += dfs(total - i, i);
        }
        memo[total][cur] = res;
        return res;
    }
}
