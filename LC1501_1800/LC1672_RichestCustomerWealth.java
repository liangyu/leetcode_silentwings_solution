package LC1501_1800;

public class LC1672_RichestCustomerWealth {
    /**
     * You are given an m x n integer grid accounts where accounts[i][j] is the amount of money the ith customer has in
     * the jth bank. Return the wealth that the richest customer has.
     *
     * A customer's wealth is the amount of money they have in all their bank accounts. The richest customer is the
     * customer that has the maximum wealth.
     *
     * Input: accounts = [[1,2,3],[3,2,1]]
     * Output: 6
     *
     * Constraints:
     *
     * m == accounts.length
     * n == accounts[i].length
     * 1 <= m, n <= 50
     * 1 <= accounts[i][j] <= 100
     * @param accounts
     * @return
     */
    // time = O(m * n), space = O(1)
    public int maximumWealth(int[][] accounts) {
        int m = accounts.length, n = accounts[0].length, max = 0;
        for (int i = 0; i < m; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += accounts[i][j];
            }
            max = Math.max(max, sum);
        }
        return max;
    }
}
