package LC1501_1800;
import java.util.*;
public class LC1792_MaximumAveragePassRatio {
    /**
     * There is a school that has classes of students and each class will be having a final exam. You are given a 2D
     * integer array classes, where classes[i] = [passi, totali]. You know beforehand that in the ith class, there are
     * totali total students, but only passi number of students will pass the exam.
     *
     * You are also given an integer extraStudents. There are another extraStudents brilliant students that are
     * guaranteed to pass the exam of any class they are assigned to. You want to assign each of the extraStudents
     * students to a class in a way that maximizes the average pass ratio across all the classes.
     *
     * The pass ratio of a class is equal to the number of students of the class that will pass the exam divided by the
     * total number of students of the class. The average pass ratio is the sum of pass ratios of all the classes
     * divided by the number of the classes.
     *
     * Return the maximum possible average pass ratio after assigning the extraStudents students. Answers within 10-5
     * of the actual answer will be accepted.
     *
     * Input: classes = [[1,2],[3,5],[2,2]], extraStudents = 2
     * Output: 0.78333
     *
     * Constraints:
     *
     * 1 <= classes.length <= 10^5
     * classes[i].length == 2
     * 1 <= passi <= totali <= 10^5
     * 1 <= extraStudents <= 10^5
     * @param classes
     * @param extraStudents
     * @return
     */
    // time = O(nlogn), space = O(n)
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        PriorityQueue<double[]> pq = new PriorityQueue<>((o1, o2) -> Double.compare(o2[0], o1[0])); // {dr, p, t}
        for (int[] c : classes) {
            double p = c[0], t = c[1];
            pq.offer(new double[]{(p + 1) / (t + 1) - p / t, p, t});
        }

        for (int i = 0; i < extraStudents; i++) {
            double[] cur = pq.poll();
            double dr = cur[0], p = cur[1], t = cur[2];
            p++;
            t++;
            pq.offer(new double[]{(p + 1) / (t + 1) - p / t, p, t});
        }

        double sum = 0;
        while (!pq.isEmpty()) {
            double[] cur = pq.poll();
            double p = cur[1], t = cur[2];
            sum += p / t;
        }
        return sum / classes.length;
    }
}
/**
 * max {(r1, r2, r3, ..., rn) / n}
 * 插班生效用最大化 -> 增量最大
 * r = p/t  => r = (p+1)/(t+1)
 * dr = (p+1)/(t+1) - p/t
 * O(nlogn)
 * A+1,B+1 > A+2=A+1,A'+1
 * B+1,A+1  => A+1: (p1+1)/(t1+1) - p1/t1
 * B+2=B+1 B'+1 => B'+1: (p2+1+1)/(t2+1+1) - (p2+1)/(t2+1) < (p2+1)/(t2+1) - p2/t2
 * => (p2+1=1)/(t2+1+1) + p2/t2 < 2*(p2+1)/(t2+1)
 * 每个都小心使用，得到的贪心解更优秀一些
 */