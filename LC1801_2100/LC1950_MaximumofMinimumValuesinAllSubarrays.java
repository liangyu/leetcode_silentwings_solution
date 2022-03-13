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

        // 注意：有些res的元素还是空的0，没有fill，这个时候由于subarray越大，越容易include小的元素而使min更小，所以应该向i大的方向去兼容。
        // 因为要到达下一个fill的res[i]，其长度不能超过这个i上，超过i就会落到和i+1一样的境地。
        for (int i = n - 2; i >= 0; i--) res[i] = Math.max(res[i], res[i + 1]);
        return res;
    }

    // S2: Stack
    // time = O(n), space = O(n)
    public int[] findMaximums2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();
        int[] prevSmaller = new int[n];
        Arrays.fill(prevSmaller, -1);
        int[] nextSmaller = new int[n];
        Arrays.fill(nextSmaller, n);

        // find nextSmaller
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) nextSmaller[stack.pop()] = i;
            stack.push(i);
        }

        stack.clear();
        // find prevSmaller
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) prevSmaller[stack.pop()] = i;
            stack.push(i);
        }

        for (int i = 0; i < n; i++) {
            int a = prevSmaller[i], b = nextSmaller[i];
            res[b - a - 2] = Math.max(res[b - a - 2], nums[i]); // 注意这里要求max，不能无脑更新为nums[i]
        }

        for (int i = n - 2; i >= 0; i--) res[i] = Math.max(res[i], res[i + 1]);
        return res;
    }
}
