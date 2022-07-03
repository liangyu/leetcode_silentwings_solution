package LC2101_2400;
import java.util.*;
public class LC2323_FindMinimumTimetoFinishAllJobsII {
    /**
     * You are given two 0-indexed integer arrays jobs and workers of equal length, where jobs[i] is the amount of time
     * needed to complete the ith job, and workers[j] is the amount of time the jth worker can work each day.
     *
     * Each job should be assigned to exactly one worker, such that each worker completes exactly one job.
     *
     * Return the minimum number of days needed to complete all the jobs after assignment.
     *
     * Input: jobs = [5,2,4], workers = [1,7,5]
     * Output: 2
     *
     * Input: jobs = [3,18,15,9], workers = [6,5,1,3]
     * Output: 3
     *
     * Constraints:
     *
     * n == jobs.length == workers.length
     * 1 <= n <= 10^5
     * 1 <= jobs[i], workers[i] <= 10^5
     * @param jobs
     * @param workers
     * @return
     */
    // S1: Greedy
    // time = O(nlogn), space = O(n)
    public int minimumTime(int[] jobs, int[] workers) {
        Arrays.sort(jobs);
        Arrays.sort(workers);
        int n = jobs.length, res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, (int) Math.ceil((double) jobs[i] / (double) workers[i]));
        }
        return res;
    }

    // S2: Sort + B.S.
    // time = O(nlogn), space = O(n)
    public int minimumTime2(int[] jobs, int[] workers) {
        int n = jobs.length;
        Arrays.sort(jobs);
        List<Integer> nums = new ArrayList<>();
        for (int x : workers) nums.add(x);
        Collections.sort(nums);

        int res = 0;
        for (int i = n - 1; i >= 0; i--) {
            int idx = helper(nums, jobs[i]);
            res = Math.max(res, (int) Math.ceil(jobs[i] * 1.0 / nums.get(idx)));
            nums.remove(idx);
        }
        return res;
    }

    private int helper(List<Integer> nums, int t) {
        int n = nums.size();
        int l = 0, r = n - 1, x = (int) Math.ceil(t * 1.0 / nums.get(r));
        while (l < r) {
            int mid = l + (r - l) / 2;
            int y = (int) Math.ceil(t * 1.0 / nums.get(mid));
            if (y > x) l = mid + 1;
            else r = mid;
        }
        return l;
    }
}