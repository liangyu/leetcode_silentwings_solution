package LC1801_2100;
import java.util.*;
public class LC1964_FindtheLongestValidObstacleCourseatEachPosition {
    /**
     * You want to build some obstacle courses. You are given a 0-indexed integer array obstacles of length n, where
     * obstacles[i] describes the height of the ith obstacle.
     *
     * For every index i between 0 and n - 1 (inclusive), find the length of the longest obstacle course in obstacles
     * such that:
     *
     * You choose any number of obstacles between 0 and i inclusive.
     * You must include the ith obstacle in the course.
     * You must put the chosen obstacles in the same order as they appear in obstacles.
     * Every obstacle (except the first) is taller than or the same height as the obstacle immediately before it.
     * Return an array ans of length n, where ans[i] is the length of the longest obstacle course for index i as
     * described above.
     *
     * Input: obstacles = [1,2,3,2]
     * Output: [1,2,3,3]
     *
     * Constraints:
     *
     * n == obstacles.length
     * 1 <= n <= 10^5
     * 1 <= obstacles[i] <= 10^7
     * @param obstacles
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        // corner case
        if (obstacles == null || obstacles.length == 0) return new int[0];

        int n = obstacles.length, p = 0;
        int[] res = new int[n];
        int[] buffer = new int[n];

        for (int i = 0; i < n; i++) {
            int left = 0, right = p;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (buffer[mid] <= obstacles[i]) left = mid + 1;
                else right = mid;
            }
            res[i] = left + 1;
            if (left == p) p++;
            buffer[left] = obstacles[i];
        }
        return res;
    }

    // S1.2: B.S.
    // time = O(nlogn), space = O(n)
    public int[] longestObstacleCourseAtEachPosition2(int[] obstacles) {
        // corner case
        if (obstacles == null || obstacles.length == 0) return new int[0];

        int n = obstacles.length;
        int[] res = new int[n];
        List<Integer> buffer = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int idx = helper(buffer, obstacles[i]);
            if (idx == buffer.size()) buffer.add(obstacles[i]);
            else buffer.set(idx, obstacles[i]);
            res[i] = idx + 1;
        }
        return res;
    }

    // find 1st pos > target (note the difference from LC300 that is >=)
    // because here is not strictly increasing, it is equal or larger!!!
    // 所以，如果出现22，应该要加到最后一个2后面，因为允许重复，可以往后延伸
    private int helper(List<Integer> buffer, int target) {
        if (buffer.size() == 0) return 0;
        int left = 0, right = buffer.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (buffer.get(mid) <= target) left = mid + 1;
            else right = mid;
        }
        return buffer.get(left) > target ? left : left + 1;
    }
}
/**
 * ref: LC300 -> strictly increasing => b.s needs to find the 1st pos >= target
 * ref: LC300, LC354, LC1713
 * while for this problem, it can be increasing or equal => b.s. needs to find the 1st pos strictly > target
 * 要仔细体会下两者的差别！
 * 找一个最长的非递减序列！
 * LIS：longest increasing subsequence
 * nums:[x x x x x x x] 9 5  -> 在序列里找到第一个 >= 5的位置
 * arr:  2 4 7 9
 * if (nums[i] > arr.back()) {
 *     arr.push_back(nums[i])
 *     ret.push_back(arr.size());
 * } else {
 *     auto iter = upper_bound(arr.begin(), arr.end());
 *     ret.push_back(iter - arr.begin());
 *     *iter = nums[i];
 * }
 */
