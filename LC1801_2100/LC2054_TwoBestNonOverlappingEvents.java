package LC1801_2100;
import java.util.*;
public class LC2054_TwoBestNonOverlappingEvents {
    /**
     * You are given a 0-indexed 2D integer array of events where events[i] = [startTimei, endTimei, valuei]. The ith
     * event starts at startTimei and ends at endTimei, and if you attend this event, you will receive a value of
     * valuei. You can choose at most two non-overlapping events to attend such that the sum of their values is maximized.
     *
     * Return this maximum sum.
     *
     * Note that the start time and end time is inclusive: that is, you cannot attend two events where one of them
     * starts and the other ends at the same time. More specifically, if you attend an event with end time t, the next
     * event must start at or after t + 1.
     *
     * Input: events = [[1,3,2],[4,5,2],[2,4,3]]
     * Output: 4
     *
     * Constraints:
     *
     * 2 <= events.length <= 10^5
     * events[i].length == 3
     * 1 <= startTimei <= endTimei <= 10^9
     * 1 <= valuei <= 10^6
     * @param events
     * @return
     */
    // S1: DP
    // time = O(nlogn), space = O(n)
    public int maxTwoEvents(int[][] events) {
        Arrays.sort(events, ((o1, o2) -> o1[1] - o2[1]));

        int n = events.length, k = 2;
        int[][] dp = new int[n + 1][k + 1];

        int res = 0;
        for (int i = 1; i <= n; i++) {
            int idx = lowerBound(events, events[i - 1][0]);
            for (int t = 1; t <= k; t++) {
                dp[i][t] = Math.max(dp[i - 1][t], dp[idx + 1][t - 1] + events[i - 1][2]);
                res = Math.max(res, dp[i][t]);
            }
        }
        return res;
    }

    private int lowerBound(int[][] nums, int t) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (nums[mid][1] < t) left = mid;
            else right = mid - 1;
        }
        return nums[left][1] < t ? left : left - 1;
    }
}
