package LC601_900;
import java.util.*;
public class LC759_EmployeeFreeTime {
    /**
     * We are given a list schedule of employees, which represents the working time for each employee.
     *
     * Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.
     *
     * Return the list of finite intervals representing common, positive-length free time for all employees, also in
     * sorted order.
     *
     * (Even though we are representing Intervals in the form [x, y], the objects inside are Intervals, not lists or
     * arrays. For example, schedule[0][0].start = 1, schedule[0][0].end = 2, and schedule[0][0][0] is not defined).
     * Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.
     *
     * Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
     * Output: [[3,4]]
     *
     * Constraints:
     *
     * 1 <= schedule.length , schedule[i].length <= 50
     * 0 <= schedule[i].start < schedule[i].end <= 10^8
     * @param schedule
     * @return
     */
    // time = O(nlogn), space = O(n)
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> res = new ArrayList<>();
        List<int[]> diff = new ArrayList<>(); // {time, incremental +1 / -1}
        for (int i = 0; i < schedule.size(); i++) {
            for (Interval interval : schedule.get(i)) {
                diff.add(new int[]{interval.start, 1});
                diff.add(new int[]{interval.end, -1});
            }
        }

        Collections.sort(diff, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]);

        int count = 0, start = -1, end = -1;
        for (int[] d : diff) {
            count += d[1];
            if (d[1] == -1 && count == 0) start = d[0];
            else if (d[1] == 1 && count == 1 && start != -1) {
                end = d[0];
                res.add(new Interval(start, end));
            }
        }
        return res;
    }

    // Definition for an Interval.
    class Interval {
        public int start;
        public int end;

        public Interval() {}

        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    };
}
/**
 * merge sorted intervals
 * 区间型 -> 扫描线/差分数组，greedy, dp
 * 互相交叠 -> 扫描线
 * 每次不用考虑整个区间，只要考虑区间端点的时刻
 * 设计一个计数器，count表示任意时刻有多少员工在工作
 * 如果单独处理端点，到了start就意味着+1，到了end就需要-1
 * 每个员工的工作时段都可以这么处理
 * count增增减减，动态表示在时间长河里有多少人在工作
 * 当count = 0时的gap，把端点时刻找出来从早到晚排个序
 * 输出一个首尾 -> 从1变为0就是开头，从0变为1就是尾部
 * 每个端点有个差分值，现在的count与之前count差多少
 * 对同一个时刻会处理若干端点，有人上岗有人下岗 -> 先处理+1，再处理-1，因为gap的触发是从1变为0以防误报，有人立刻上岗
 */
