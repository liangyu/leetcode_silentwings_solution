package LC1801_2100;
import java.util.*;
public class LC1936_AddMinimumNumberofRungs {
    /**
     * You are given a strictly increasing integer array rungs that represents the height of rungs on a ladder. You
     * are currently on the floor at height 0, and you want to reach the last rung.
     *
     * You are also given an integer dist. You can only climb to the next highest rung if the distance between where
     * you are currently at (the floor or on a rung) and the next rung is at most dist. You are able to insert rungs
     * at any positive integer height if a rung is not already there.
     *
     * Return the minimum number of rungs that must be added to the ladder in order for you to climb to the last rung.
     *
     * Input: rungs = [1,3,5,10], dist = 2
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= rungs.length <= 10^5
     * 1 <= rungs[i] <= 10^9
     * 1 <= dist <= 10^9
     * rungs is strictly increasing.
     * @param rungs
     * @param dist
     * @return
     */
    // S1: 最优解！！！
    // time = O(n), space = O(1)
    public int addRungs(int[] rungs, int dist) {
        // corner case
        if (rungs == null || rungs.length == 0 || dist <= 0) return -1;

        int pre = 0, res = 0;
        for (int r :  rungs) {
            // we already have the option of a+ dist using which we are unable to reach to b.
            // So we exclude this 1 count of dist.
            res += (r - pre - 1) / dist;
            pre = r;
        }
        return res;
    }

    // S2: TreeSet
    // time = O(nlogn), space = O(n)
    public int addRungs2(int[] rungs, int dist) {
        // corner case
        if (rungs == null || rungs.length == 0 || dist <= 0) return -1;

        int n = rungs.length;
        TreeSet<Integer> set = new TreeSet<>();
        for (int r :  rungs) set.add(r);

        long count = 0;
        int cur = 0;

        while (cur < rungs[n - 1]) {
            Integer ck = set.higher(cur);
            if (ck - cur <= dist) {
                cur = ck;
                continue;
            } else {
                int diff = ck - cur - dist;
                count += (diff % dist > 0) ? diff / dist + 1 : diff / dist;
                cur = ck;
            }

        }
        return (int)count;
    }
}
