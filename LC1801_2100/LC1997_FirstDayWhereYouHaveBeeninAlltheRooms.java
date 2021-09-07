package LC1801_2100;

public class LC1997_FirstDayWhereYouHaveBeeninAlltheRooms {
    /**
     * There are n rooms you need to visit, labeled from 0 to n - 1. Each day is labeled, starting from 0. You will go
     * in and visit one room a day.
     *
     * Initially on day 0, you visit room 0. The order you visit the rooms for the coming days is determined by the
     * following rules and a given 0-indexed array nextVisit of length n:
     *
     * Assuming that on a day, you visit room i,
     * if you have been in room i an odd number of times (including the current visit), on the next day you will visit
     * the room specified by nextVisit[i] where 0 <= nextVisit[i] <= i;
     * if you have been in room i an even number of times (including the current visit), on the next day you will visit
     * room (i + 1) mod n.
     * Return the label of the first day where you have been in all the rooms. It can be shown that such a day exists.
     * Since the answer may be very large, return it modulo 10^9 + 7.
     *
     * Input: nextVisit = [0,0]
     * Output: 2
     *
     * Constraints:
     *
     * n == nextVisit.length
     * 2 <= n <= 10^5
     * 0 <= nextVisit[i] <= i
     * @param nextVisit
     * @return
     */
    // time = O(n), space = O(n)
    public int firstDayBeenInAllRooms(int[] nextVisit) {
        // corner case
        if (nextVisit == null || nextVisit.length == 0) return 0;

        int n = nextVisit.length;
        long M = (long)(1e9 + 7);
        long[] dp = new long[n];
        dp[0] = 0;

        for (int i = 0; i < n - 1; i++) {
            int j = nextVisit[i];
            dp[i + 1] = (dp[i] + 1 + dp[i] - dp[j] + M) % M + 1;
        }
        return (int)(dp[n - 1] % M);
    }
}
/**
 * 智商题
 * 游戏规则：0 <= nextVisit[i] <= i  => 必须回退
 * 偶数次到达房间，则继续前进
 * 什么时候到达最后一个房间
 * 所有新房间都是偶数次访问得来的，一点点爬过去的
 * 访问第n-1个房间的时候，就是答案。
 * 最关键突破口：第一次走到i这个位置，意味着前面所有位置都是经过偶数次，才第一次到达这里。
 * [x x x j x x x x] i
 * dp[i]: the days when you first arrive at i
 * dp[i + 1] = dp[i] + 1 + (dp[i] - dp[j]) + 1
 * 关键：站在上帝视角看清穿越的时间线
 */