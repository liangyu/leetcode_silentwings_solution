package LC901_1200;
import java.util.*;
public class LC986_IntervalListIntersections {
    /**
     * You are given two lists of closed intervals, firstList and secondList, where firstList[i] = [starti, endi] and
     * secondList[j] = [startj, endj]. Each list of intervals is pairwise disjoint and in sorted order.
     *
     * Return the intersection of these two interval lists.
     *
     * A closed interval [a, b] (with a < b) denotes the set of real numbers x with a <= x <= b.
     *
     * The intersection of two closed intervals is a set of real numbers that are either empty or represented as a
     * closed interval. For example, the intersection of [1, 3] and [2, 4] is [2, 3].
     *
     * Input: firstList = [[0,2],[5,10],[13,23],[24,25]], secondList = [[1,5],[8,12],[15,24],[25,26]]
     * Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
     *
     * Constraints:
     *
     * 0 <= firstList.length, secondList.length <= 1000
     * firstList.length + secondList.length >= 1
     * 0 <= starti < endi <= 10^9
     * endi < starti+1
     * 0 <= startj < endj <= 10^9
     * endj < startj+1
     * @param firstList
     * @param secondList
     * @return
     */
    // S1: Sweep Line
    // time = O((m + n) * log(m + n)), space = O(m + n)
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> list = new ArrayList<>();

        for (int[] x : firstList) {
            list.add(new int[]{x[0], 1});
            list.add(new int[]{x[1], -1});
        }

        for (int[] x : secondList) {
            list.add(new int[]{x[0], 1});
            list.add(new int[]{x[1], -1});
        }

        Collections.sort(list, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]); // 先+后-

        int count = 0, start = -1, end = -1;
        List<int[]> res = new ArrayList<>();

        for (int[] x : list) {
            count += x[1];
            if (x[1] == 1 && count == 2) start = x[0];
            else if (x[1] == -1 && count == 1) {
                end = x[0];
                res.add(new int[]{start, end});
            }
        }
        int[][] ans = new int[res.size()][2];
        for (int i = 0; i < res.size(); i++) {
            ans[i] = res.get(i).clone();
        }
        return ans;
    }

    // S2: Two Pointers
    // time = O(m + n), space = O(m + n)
    public int[][] intervalIntersection2(int[][] firstList, int[][] secondList) {
        int m = firstList.length, n = secondList.length;
        int i = 0, j = 0;
        List<int[]> res = new ArrayList<>();

        while (i < m && j < n) {
            if (firstList[i][1] < secondList[j][0]) i++;
            else if (firstList[i][0] > secondList[j][1]) j++;
            else {
                int start = Math.max(firstList[i][0], secondList[j][0]);
                int end = Math.min(firstList[i][1], secondList[j][1]);
                res.add(new int[]{start, end});
                if (firstList[i][1] < secondList[j][1]) i++;
                else j++;
            }
        }
        return res.toArray(new int[res.size()][]);
    }
}
/**
 * sorted
 * 相切的交点也是个交集
 * count计数的时候，遇到同一个时刻点，应该先+1 or -1。
 * 变为2 => 交集
 * => 先加+1，再-1
 */
