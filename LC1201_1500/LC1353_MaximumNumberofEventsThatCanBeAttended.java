package LC1201_1500;
import java.util.*;
public class LC1353_MaximumNumberofEventsThatCanBeAttended {
    /**
     * Given an array of events where events[i] = [startDayi, endDayi]. Every event i starts at startDayi and ends at
     * endDayi.
     *
     * You can attend an event i at any day d where startTimei <= d <= endTimei. Notice that you can only attend one
     * event at any time d.
     *
     * Return the maximum number of events you can attend.
     *
     * Input: events = [[1,2],[2,3],[3,4]]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= events.length <= 10^5
     * events[i].length == 2
     * 1 <= startDayi <= endDayi <= 10^5
     * @param events
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int maxEvents(int[][] events) {
        Arrays.sort(events, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        int i = 0, res = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int day = 1; day <= 100000; day++) {
            while (i < events.length && events[i][0] <= day) {
                pq.offer(events[i][1]);
                i++;
            }
            while (!pq.isEmpty() && pq.peek() < day) pq.poll(); // deadline day is passed, we no longer can join it
            if (!pq.isEmpty()) {
                pq.poll(); // complete today's event
                res++;
            }
        }
        return res;
    }
}
/**
 * sort + pq
 * 假设今天在很多活动的range里面，只要花一天就表示参加一个活动
 * 在这么多可选的活动中，选择哪一个？deadline最早的那个。
 * 哪些活动可以参加？pass the start day
 * 贪心解法
 * 特别大 -> 离散化，只要访问startDay和endDay
 */
