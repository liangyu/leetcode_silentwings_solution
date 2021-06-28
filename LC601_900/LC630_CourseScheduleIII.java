package LC601_900;
import java.util.*;
public class LC630_CourseScheduleIII {
    /**
     * There are n different online courses numbered from 1 to n. You are given an array courses where
     * courses[i] = [durationi, lastDayi] indicate that the ith course should be taken continuously for durationi
     * days and must be finished before or on lastDayi.
     *
     * You will start on the 1st day and you cannot take two or more courses simultaneously.
     *
     * Return the maximum number of courses that you can take.
     *
     * Input: courses = [[100,200],[200,1300],[1000,1250],[2000,3200]]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= courses.length <= 10^4
     * 1 <= durationi, lastDayi <= 10^4
     * @param courses
     * @return
     */
    // S1: Greedy
    // time = O(nlogn), space = O(n)
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (o1, o2) -> o1[1] - o2[1]); // sort by deadline
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);

        int start = 0;
        for (int[] course : courses) {
            start += course[0];
            maxHeap.offer(course[0]);
            if (start > course[1]) start -= maxHeap.poll(); // drop the course that takes longest durations
        }
        return maxHeap.size();
    }
}
/**
 * Sort the courses by their deadlines (Greedy! We have to deal with courses with early deadlines first)
 * If time exceeds, drop the previous course which costs the most time. (That must be the best choice!)
 *
 * sort + pq
 * => 凡是有deadline属性出现的，优先满足deadline
 *          Day 5, Day 7, Day 8, Day X
 *            3      4      2     +10
 *  +3+4+2 > 8   无论如何完不成  -> +3+2+?
 *  pq 在过往里面最长的都扔掉，换成轻巧的 -> 每一天都充分利用 优化时间
 *  贪心策略
 *  1. 先处理deadline
 *  2. 精华：赶不上deadline,就优化days，把上的最长的课cancel掉，把短的提上来，优化了days，有更多充裕时间完成更多的课
 */

