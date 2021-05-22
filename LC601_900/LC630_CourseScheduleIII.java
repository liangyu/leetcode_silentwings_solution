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
 */

