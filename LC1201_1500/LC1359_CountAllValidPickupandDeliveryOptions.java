package LC1201_1500;

public class LC1359_CountAllValidPickupandDeliveryOptions {
    /**
     * Given n orders, each order consist in pickup and delivery services.
     *
     * Count all valid pickup/delivery possible sequences such that delivery(i) is always after of pickup(i).
     *
     * Since the answer may be too large, return it modulo 10^9 + 7.
     *
     * Input: n = 1
     * Output: 1
     * Explanation: Unique order (P1, D1), Delivery 1 always is after of Pickup 1.
     *
     * Constraints:
     *
     * 1 <= n <= 500
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int countOrders(int n) {
        long res = 1;
        long M = (long)(1e9 + 7);
        for (int i = 1; i <= n; i++) res = res * i % M;
        for (int i = 2 * n - 1; i >= 1; i -= 2) res = res * i % M;
        return (int) res;
    }
}
