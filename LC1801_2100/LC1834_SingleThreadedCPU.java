package LC1801_2100;
import java.util.*;
public class LC1834_SingleThreadedCPU {
    /**
     * You are given n tasks labeled from 0 to n - 1 represented by a 2D integer array tasks, where tasks[i] =
     * [enqueueTimei, processingTimei] means that the ithtask will be available to process at enqueueTimei and will take
     * processingTimei to finish processing.
     *
     * You have a single-threaded CPU that can process at most one task at a time and will act in the following way:
     *
     * If the CPU is idle and there are no available tasks to process, the CPU remains idle.
     * If the CPU is idle and there are available tasks, the CPU will choose the one with the shortest processing time.
     * If multiple tasks have the same shortest processing time, it will choose the task with the smallest index.
     * Once a task is started, the CPU will process the entire task without stopping.
     * The CPU can finish a task then start a new one instantly.
     * Return the order in which the CPU will process the tasks.
     *
     * Input: tasks = [[1,2],[2,4],[3,2],[4,1]]
     * Output: [0,2,3,1]
     *
     * Constraints:
     *
     * tasks.length == n
     * 1 <= n <= 10^5
     * 1 <= enqueueTimei, processingTimei <= 10^9
     * @param tasks
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int[] getOrder(int[][] tasks) {
        int n = tasks.length;
        int[][] jobs = new int[n][3];
        for (int i = 0; i < n; i++) {
            jobs[i][0] = tasks[i][0];
            jobs[i][1] = tasks[i][1];
            jobs[i][2] = i;
        }

        Arrays.sort(jobs, (o1, o2) -> o1[0] - o2[0]);
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]); // {processing time, id}

        int curTime = 0, idx = 0;
        int[] res = new int[n];
        for (int[] task : jobs) {
            while (!pq.isEmpty() && curTime < task[0]) {
                res[idx++] = pq.peek()[1];
                curTime += pq.poll()[0];
            }
            curTime = Math.max(curTime, task[0]);
            pq.offer(new int[]{task[1], task[2]});
        }

        while (!pq.isEmpty()) res[idx++] = pq.poll()[1];
        return res;
    }
}
/**
 * 任务池 -> 什么时候任务可以入池？
 * curTime >= enqueuTime[i] 一直到curTime < enqueuTime[i]
 * 按照时间顺序处理
 * 挑任务做，确定性规则
 * curTime +=
 */
