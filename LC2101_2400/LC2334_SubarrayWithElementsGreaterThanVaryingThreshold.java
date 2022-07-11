package LC2101_2400;
import java.util.*;
public class LC2334_SubarrayWithElementsGreaterThanVaryingThreshold {
    /**
     * You are given an integer array nums and an integer threshold.
     *
     * Find any subarray of nums of length k such that every element in the subarray is greater than threshold / k.
     *
     * Return the size of any such subarray. If there is no such subarray, return -1.
     *
     * A subarray is a contiguous non-empty sequence of elements within an array.
     *
     * Input: nums = [1,3,4,3,1], threshold = 6
     * Output: 3
     *
     * Input: nums = [6,5,6,5,8], threshold = 7
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i], threshold <= 10^9
     * @param nums
     * @param threshold
     * @return
     */
    // S1: Union Find
    // time = O(nlogn), space = O(n)
    int[] p, s;
    List<Integer> q;
    public int validSubarraySize(int[] nums, int threshold) {
        int n = nums.length;
        p = new int[n + 1];
        q = new ArrayList<>();
        s = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            p[i] = i;
            q.add(i);
            s[i] = 1;
        }

        Collections.sort(q, (o1, o2) -> nums[o2] - nums[o1]);
        for (int k = 1, i = 0; k <= n; k++) {
            while (i < n && nums[q.get(i)] > threshold / k) {
                int a = q.get(i), b = find(q.get(i) + 1);
                s[b] += s[a];
                if (s[b] - 1 >= k) return k;
                p[a] = b;
                i++;
            }
        }
        return -1;
    }

    private int find(int x) {
        if (x != p[x]) p[x] = find(p[x]);
        return p[x];
    }

    // S2: stack
    // time = O(n), space = O(n)
    public int validSubarraySize2(int[] nums, int threshold) {
        int n = nums.length;
        int[] prevSmaller = new int[n];
        int[] nextSmaller = new int[n];
        Arrays.fill(prevSmaller, -1);
        Arrays.fill(nextSmaller, n);

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                nextSmaller[stack.pop()] = i;
            }
            stack.push(i);
        }

        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                prevSmaller[stack.pop()] = i;
            }
            stack.push(i);
        }

        for (int i = 0; i < n; i++) {
            int k = nextSmaller[i] - prevSmaller[i] - 1;
            if (nums[i] > threshold / k) return k; // 只要这段长度为k的区间里的最小值满足条件，区间里的其他数一定也满足！
        }
        return -1;
    }

    // S2.2: stack (ref: LC84)
    // time = O(n), space = O(n)
    public int validSubarraySize3(int[] nums, int threshold) {
        int n = nums.length;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i <= n; i++) {
            int x = i == n ? 0 : nums[i];
            while (!stack.isEmpty() && nums[stack.peek()] > x) {
                int h = nums[stack.peek()];
                stack.pop();
                int w = stack.isEmpty() ? i : i - stack.peek() - 1;
                if (h * w > threshold) return w;
            }
            stack.push(i);
        }
        return -1;
    }
}
/**
 * ref: LC84
 * 这道题的套路隐藏地非常巧妙。如果我们将nums看成一个histogram，那么本质就是求一个rectange，其面积要大于threshold。
 * 于是这就完全转化成了84-Largest-Rectangle-in-Histogram，我们只要遍历每个元素，考察它作为矩形的高时，宽的最大范围，
 * 再查看area是否大于threshold即可。
 * nums[i] -> prev / next
 */