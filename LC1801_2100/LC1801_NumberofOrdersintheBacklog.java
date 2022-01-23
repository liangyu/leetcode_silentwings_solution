package LC1801_2100;
import java.util.*;
public class LC1801_NumberofOrdersintheBacklog {
    /**
     * You are given a 2D integer array orders, where each orders[i] = [pricei, amounti, orderTypei] denotes that
     * amounti orders have been placed of type orderTypei at the price pricei. The orderTypei is:
     *
     * 0 if it is a batch of buy orders, or
     * 1 if it is a batch of sell orders.
     * Note that orders[i] represents a batch of amounti independent orders with the same price and order type. All
     * orders represented by orders[i] will be placed before all orders represented by orders[i+1] for all valid i.
     *
     * There is a backlog that consists of orders that have not been executed. The backlog is initially empty. When an
     * order is placed, the following happens:
     *
     * If the order is a buy order, you look at the sell order with the smallest price in the backlog. If that sell
     * order's price is smaller than or equal to the current buy order's price, they will match and be executed, and
     * that sell order will be removed from the backlog. Else, the buy order is added to the backlog.
     * Vice versa, if the order is a sell order, you look at the buy order with the largest price in the backlog. If
     * that buy order's price is larger than or equal to the current sell order's price, they will match and be executed,
     * and that buy order will be removed from the backlog. Else, the sell order is added to the backlog.
     * Return the total amount of orders in the backlog after placing all the orders from the input. Since this number
     * can be large, return it modulo 10^9 + 7.
     *
     * Input: orders = [[10,5,0],[15,2,1],[25,1,1],[30,4,0]]
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= orders.length <= 10^5
     * orders[i].length == 3
     * 1 <= pricei, amounti <= 10^9
     * orderTypei is either 0 or 1.
     * @param orders
     * @return
     */
    // S1: PQ
    // time = O(nlogn), space = O(n)
    public int getNumberOfBacklogOrders(int[][] orders) {
        PriorityQueue<int[]> buy = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]); // {price, amount}
        PriorityQueue<int[]> sell = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);

        long res = 0, M = (long)(1e9 + 7);
        for (int[] order : orders) { // O(n)
            res = (res + order[1]) % M;
            if (order[2] == 0) {
                while (!sell.isEmpty() && sell.peek()[0] <= order[0] && order[1] > 0) {
                    int[] cur = sell.poll(); // O(logn)
                    int num = Math.min(cur[1], order[1]);
                    cur[1] -= num;
                    order[1] -= num;
                    res = (res - num * 2 + M) % M; // 可能会减出负数，必须要+M来避免
                    if (cur[1] > 0) sell.offer(new int[]{cur[0], cur[1]});
                }
                if (order[1] > 0) buy.offer(new int[]{order[0], order[1]});
            } else {
                while (!buy.isEmpty() && buy.peek()[0] >= order[0] && order[1] > 0) {
                    int[] cur = buy.poll();
                    int num = Math.min(cur[1], order[1]);
                    cur[1] -= num;
                    order[1] -= num;
                    res = (res - num * 2 + M) % M;
                    if (cur[1] > 0) buy.offer(new int[]{cur[0], cur[1]});
                }
                if (order[1] > 0) sell.offer(new int[]{order[0], order[1]});
            }
        }
        return (int)res;
    }

    // S1.2: PQ
    // time = O(nlogn), space = O(n)
    public int getNumberOfBacklogOrders2(int[][] orders) {
        // corner case
        if (orders == null || orders.length == 0 || orders[0] == null || orders[0].length == 0) return 0;

        PriorityQueue<int[]> sell = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        PriorityQueue<int[]> buy = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);

        for (int[] order : orders) {
            if (order[2] == 0) buy.offer(order);
            else sell.offer(order);

            while (!buy.isEmpty() && !sell.isEmpty() && sell.peek()[0] <= buy.peek()[0]) {
                int num = Math.min(sell.peek()[1], buy.peek()[1]);
                buy.peek()[1] -= num;
                sell.peek()[1] -= num;
                if (buy.peek()[1] == 0) buy.poll();
                if (sell.peek()[1] == 0) sell.poll();
            }
        }

        int res = 0, M = (int)(1e9 + 7);
        for (int[] cur : sell) res = (res + cur[1]) % M;
        for (int[] cur : buy) res = (res + cur[1]) % M;
        return res;
    }
}
/**
 * 优先队列给待卖出的订单从小到大排
 * 卖出订单，待买入的订单里价格从高到低排
 */
