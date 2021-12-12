package LC1801_2100;
import java.util.*;
public class LC2100_FindGoodDaystoRobtheBank {
    /**
     * You and a gang of thieves are planning on robbing a bank. You are given a 0-indexed integer array security, where
     * security[i] is the number of guards on duty on the ith day. The days are numbered starting from 0. You are also
     * given an integer time.
     *
     * The ith day is a good day to rob the bank if:
     *
     * There are at least time days before and after the ith day,
     * The number of guards at the bank for the time days before i are non-increasing, and
     * The number of guards at the bank for the time days after i are non-decreasing.
     * More formally, this means day i is a good day to rob the bank if and only if security[i - time] >=
     * security[i - time + 1] >= ... >= security[i] <= ... <= security[i + time - 1] <= security[i + time].
     *
     * Return a list of all days (0-indexed) that are good days to rob the bank. The order that the days are returned in
     * does not matter.
     *
     * Input: security = [5,3,3,3,5,6,2], time = 2
     * Output: [2,3]
     *
     * Constraints:
     *
     * 1 <= security.length <= 10^5
     * 0 <= security[i], time <= 10^5
     * @param security
     * @param time
     * @return
     */
    // time = O(n), space = O(n)
    public List<Integer> goodDaysToRobBank(int[] security, int time) {
        List<Integer> res = new ArrayList<>();
        int n = security.length;
        int[] pre = new int[n], after = new int[n];

        for (int i = 1; i < n; i++) {
            if (security[i] <= security[i - 1]) pre[i] = pre[i - 1] + 1;
        }

        for (int i = n - 2; i >= 0; i--) {
            if (security[i] <= security[i + 1]) after[i] = after[i + 1] + 1;
        }

        for (int i = time; i < n - time; i++) {
            if (pre[i] >= time && after[i] >= time) res.add(i);
        }
        return res;
    }
}
