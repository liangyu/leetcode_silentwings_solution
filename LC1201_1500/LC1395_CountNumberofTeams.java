package LC1201_1500;

public class LC1395_CountNumberofTeams {
    /**
     * There are n soldiers standing in a line. Each soldier is assigned a unique rating value.
     *
     * You have to form a team of 3 soldiers amongst them under the following rules:
     *
     * Choose 3 soldiers with index (i, j, k) with rating (rating[i], rating[j], rating[k]).
     * A team is valid if: (rating[i] < rating[j] < rating[k]) or (rating[i] > rating[j] > rating[k]) where
     * (0 <= i < j < k < n).
     * Return the number of teams you can form given the conditions. (soldiers can be part of multiple teams).
     *
     * Input: rating = [2,5,3,4,1]
     * Output: 3
     *
     * Constraints:
     *
     * n == rating.length
     * 3 <= n <= 1000
     * 1 <= rating[i] <= 10^5
     * All the integers in rating are unique.
     * @param rating
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int numTeams(int[] rating) {
        int res = 0, n = rating.length;
        // prevSmaller
        BIT bit1 = new BIT((int) 1e5);
        int[] prevSmaller = new int[n];

        for (int i = 0; i < n; i++) {
            prevSmaller[i] = bit1.sumRange(2, rating[i]);
            bit1.update(rating[i] + 1, 1);
        }

        // nextSmaller
        BIT bit2 = new BIT((int) 1e5);
        int[] nextSmaller = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            nextSmaller[i] = bit2.sumRange(2, rating[i]);
            bit2.update(rating[i] + 1, 1);
        }

        for (int i = 0; i < n; i++) {
            res += prevSmaller[i] * (n - i - 1 - nextSmaller[i]);
            res += (i - prevSmaller[i]) * nextSmaller[i];
        }
        return res;
    }

    private class BIT {
        private int n;
        private int[] bitree;
        public BIT(int n) {
            this.n = n;
            this.bitree = new int[n + 1];
        }

        private void update(int x, int delta) {
            for (int i = x; i <= n; i += i & (-i)) {
                bitree[i] += delta;
            }
        }

        private int query(int x) {
            int res = 0;
            for (int i = x; i > 0; i -= i & (-i)) {
                res += bitree[i];
            }
            return res;
        }

        private int sumRange(int i, int j) {
            return query(j) - query(i - 1);
        }
    }
}
