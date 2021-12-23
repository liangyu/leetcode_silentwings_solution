package LC1801_2100;
import java.util.*;
public class LC2071_MaximumNumberofTasksYouCanAssign {
    /**
     * You have n tasks and m workers. Each task has a strength requirement stored in a 0-indexed integer array tasks,
     * with the ith task requiring tasks[i] strength to complete. The strength of each worker is stored in a 0-indexed
     * integer array workers, with the jth worker having workers[j] strength. Each worker can only be assigned to a
     * single task and must have a strength greater than or equal to the task's strength requirement
     * (i.e., workers[j] >= tasks[i]).
     *
     * Additionally, you have pills magical pills that will increase a worker's strength by strength. You can decide
     * which workers receive the magical pills, however, you may only give each worker at most one magical pill.
     *
     * Given the 0-indexed integer arrays tasks and workers and the integers pills and strength, return the maximum
     * number of tasks that can be completed.
     *
     * Input: tasks = [3,2,1], workers = [0,3,3], pills = 1, strength = 1
     * Output: 3
     *
     * Constraints:
     *
     * n == tasks.length
     * m == workers.length
     * 1 <= n, m <= 5 * 10^4
     * 0 <= pills <= m
     * 0 <= tasks[i], workers[j], strength <= 10^9
     * @param tasks
     * @param workers
     * @param pills
     * @param strength
     * @return
     */
    // time = O(nlogn + (m + n) * (logn * logm)), space = O(m)
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        Arrays.sort(tasks); // O(nlogn) 记得一定要先排序啊！！！
        int left = 0, right = tasks.length;
        while (left < right) { // O(logn)
            int mid = right - (right - left) / 2;
            if (checkOK(tasks, workers, pills, strength, mid)) left = mid;
            else right = mid - 1;
        }
        return left; // 一定有解！
    }

    private boolean checkOK(int[] tasks, int[] workers, int pills, int strength, int k) {
        if (k > workers.length) return false;

        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int worker : workers) { // O(mlogm)
            map.put(worker, map.getOrDefault(worker, 0) + 1);
        }

        // start from the hardest task
        for (int i = k - 1; i >= 0; i--) { // O(nlogm)
            if (map.lastKey() >= tasks[i]) { // see if most experienced worker can handle
                int worker = map.lastKey();
                map.put(worker, map.get(worker) - 1);
                if (map.get(worker) == 0) map.remove(worker);
            } else { // need to use strength, find a worker that just a bit over task difficulty after use strength
                if (pills == 0) return false;
                Integer ck = map.ceilingKey(tasks[i] - strength);
                if (ck == null) return false;
                map.put(ck, map.get(ck) - 1);
                if (map.get(ck) == 0) map.remove(ck);
                pills--;
            }
        }
        return true;
    }
}
/**
 * 要么贪心，要么完全无脑配对，各种组合，暴力配对是否有什么方便的写法，比如bitmask
 * 1 <= n, m <= 5 * 10^4 -> 贪心，排序
 * 并没有特别好的贪心解，因为并不确定跟这个task要不要去做
 * 就算这个task要做，你到底分给谁去做？分给worker或者work + strength，不太好说
 * 8   9
 * 7   5+3
 * 7   7
 * 7   1
 * 追求的是任务的数量，如果是选择任务的话，肯定会选择最简单的
 * 比如难度为8的任务，到底要不要做
 * worker：task
 *         task - strength
 * 突破口：这k件任务一定是"难度最低的k件"，如果能做成功，我可以再试试k+1件，不行就k-1件。
 * 所以这道题就是先猜，然后看看能不能完成。
 * 区间是非常明显的，所以非常适合用二分搜值，而且是有单调性的。
 * 7件任务做不好，8件肯定做不好，相反6件一定能做好。
 * k tasks
 * workers:
 * the hardest task: the most capable worker
 * 如果老师傅不能做，那谁吃大力丸？
 * 最经济的方法：找一个worker + strength 刚刚大于task就最经济 -> worker排个序
 * 2nd hardest task: the most capable worker
 *                   worker + strength
 * multi-set<int>
 * TreeMap
 */
