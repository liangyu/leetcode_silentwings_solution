package LC901_1200;
import java.util.*;
public class LC1167_MinimumCosttoConnectSticks {
    /**
     * You have some number of sticks with positive integer lengths. These lengths are given as an array sticks, where
     * sticks[i] is the length of the ith stick.
     *
     * You can connect any two sticks of lengths x and y into one stick by paying a cost of x + y. You must connect all
     * the sticks until there is only one stick remaining.
     *
     * Return the minimum cost of connecting all the given sticks into one stick in this way.
     *
     * Input: sticks = [2,4,3]
     * Output: 14
     *
     * Constraints:
     *
     * 1 <= sticks.length <= 10^4
     * 1 <= sticks[i] <= 10^4
     * @param sticks
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int connectSticks(int[] sticks) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int x : sticks) pq.offer(x);

        int res = 0;
        while (pq.size() >= 2) {
            int a = pq.poll(), b = pq.poll();
            res += a + b;
            pq.offer(a + b);
        }
        return res;
    }
}
/**
 * 让大数尽量少的参与合并，希望参与合并的次数越少越好，最好是最后一次被合并
 * 贪心解法，每一回合都是最优解 -> 全局最优解
 * [1,3,5,8]
 * [4,5,8]
 * [9,8]
 * [17]
 * 1被重复数了4次
 * [2,3,3,4,8]
 * [3,4,5,8]
 * [5,7,8]
 * [8,12]
 */
