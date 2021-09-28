package LC1201_1500;
import java.util.*;
public class LC1326_MinimumNumberofTapstoOpentoWateraGarden {
    /**
     * There is a one-dimensional garden on the x-axis. The garden starts at the point 0 and ends at the point n.
     * (i.e The length of the garden is n).
     *
     * There are n + 1 taps located at points [0, 1, ..., n] in the garden.
     *
     * Given an integer n and an integer array ranges of length n + 1 where ranges[i] (0-indexed) means the i-th tap
     * can water the area [i - ranges[i], i + ranges[i]] if it was open.
     *
     * Return the minimum number of taps that should be open to water the whole garden, If the garden cannot be watered
     * return -1.
     *
     * Input: n = 5, ranges = [3,4,1,1,0,0]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= n <= 10^4
     * ranges.length == n + 1
     * 0 <= ranges[i] <= 100
     * @param n
     * @param ranges
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int minTaps(int n, int[] ranges) {
        int[][] intervals = new int[n + 1][2];
        for (int i = 0; i <= n; i++) {
            intervals[i] = new int[]{i - ranges[i], i + ranges[i]};
        }

        Arrays.sort(intervals, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]); // 起点相等时取区间较长的那个

        int far = 0, i = 0, count = 0;
        while (i < n + 1) {
            int nextFar = far;
            while (i < n + 1 && intervals[i][0] <= far) {
                nextFar = Math.max(nextFar, intervals[i][1]);
                i++;
            }
            count++; // 每一个回合通过贪心法确定一个区间，找一个右端最远的区间
            if (nextFar >= n) return count;
            else if (nextFar == far) return -1; // 注意这里必须是cover整个区间，而不仅仅只是单个点
            far = nextFar;
        }
        return -1;
    }
}
/**
 * ref: LC1024
 * 贪心法思想
 * 区间类的问题，2大做法：
 * 1. 扫描线，无非是把区间起始点和终结点给一个权重，1，-1。进入一个区间+1，离开一个区间-1，处理区间交叠的问题。
 * 2. 贪心，把区间按照某种方法去排序再去做处理，不把区间的起点和终点打散。
 * sort by starting points => the minimum number of intervals to cover the whole range
 * sort by ending points => the maximum number of non-overlapping intervals
 * 选择ending point越远越好，向后跨越越多，就越有可能少用几个区间
 * 先找一个starting point里找一个0前面的就好，找一个ending point最远的区间 => 开始设定一个虚拟的区间做开端
 */
