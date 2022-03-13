package LC1801_2100;
import java.util.*;
public class LC1950_MaximumofMinimumValuesinAllSubarrays {
    /**
     * You are given an integer array nums of size n. You are asked to solve n queries for each integer i in the
     * range 0 <= i < n.
     *
     * To solve the ith query:
     *
     * Find the minimum value in each possible subarray of size i + 1 of the array nums.
     * Find the maximum of those minimum values. This maximum is the answer to the query.
     * Return a 0-indexed integer array ans of size n such that ans[i] is the answer to the ith query.
     *
     * A subarray is a contiguous sequence of elements in an array.
     *
     * Input: nums = [0,1,2,4]
     * Output: [4,2,1,0]
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 10^5
     * 0 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // S1: PQ + TreeSet
    // time = O(nlogn), space = O(n)
    public int[] findMaximums(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] res = new int[n];

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        for (int i = 0; i < n; i++) pq.offer(new int[]{nums[i], i});
        TreeSet<Integer> set = new TreeSet<>();

        for (int i = 0; i < n; i++) {
            int[] cur = pq.poll();
            Integer fk = set.floor(cur[1]);
            if (fk == null) fk = -1;
            Integer ck = set.ceiling(cur[1]);
            if (ck == null) ck = n;
            res[ck - fk - 2] = cur[0];
            set.add(cur[1]);
        }

        for (int i = n - 2; i >= 0; i--) res[i] = Math.max(res[i], res[i + 1]);
        return res;
    }

    // S2: Stack
    // time = O(n), space = O(n)
    public int[] findMaximums2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] left = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) stack.pop();
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();
        int[] right = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) stack.pop();
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            int len = right[i] - left[i] - 2;
            res[len] = Math.max(res[len], nums[i]);
        }
        for (int i = n - 2; i >= 0; i--) {
            res[i] = Math.max(res[i], res[i + 1]);
        }
        return res;
    }
}
