package LC301_600;
import java.util.*;
public class LC502_IPO {
    /**
     * Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital,
     * LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited
     * resources, it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to
     * maximize its total capital after finishing at most k distinct projects.
     *
     * You are given n projects where the ith project has a pure profit profits[i] and a minimum capital of capital[i]
     * is needed to start it.
     *
     * Initially, you have w capital. When you finish a project, you will obtain its pure profit and the profit will be
     * added to your total capital.
     *
     * Pick a list of at most k distinct projects from given projects to maximize your final capital, and return the
     * final maximized capital.
     *
     * The answer is guaranteed to fit in a 32-bit signed integer.
     *
     * Input: k = 2, w = 0, profits = [1,2,3], capital = [0,1,1]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= k <= 10^5
     * 0 <= w <= 10^9
     * n == profits.length
     * n == capital.length
     * 1 <= n <= 10^5
     * 0 <= profits[i] <= 10^4
     * 0 <= capital[i] <= 10^9
     * @param k
     * @param w
     * @param profits
     * @param capital
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        int[][] tasks = new int[n][2];

        for (int i = 0; i < n; i++) tasks[i] = new int[]{capital[i], profits[i]};

        Arrays.sort(tasks, (o1, o2) -> o1[0] - o2[0]);

        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int count = 0, i = 0;
        while (count < k) {
            while (i < n && tasks[i][0] <= w) {
                pq.offer(tasks[i][1]);
                i++;
            }
            if (pq.isEmpty()) break;
            w += pq.poll();
            count++;
        }
        return w;
    }
}
/**
 * Greedy(sort) + PQ 组合拳
 * 策略问题：dp + 贪心法
 * 直觉 贪心法是可行的
 * 没有这种直觉，直觉上判断出不行的话，则用dp
 * capitol 从小到大排一遍
 * k -> 会吃能够吃下的利润最大的
 * 在力所能及范围内，每次都吃最大的
 * w: capital[i] <= w
 * w + p
 * 项目按照capital 排序
 * 常见贪心法 sort  +  pq  针对不同属性进行操作
 *         capital  profit
 */
