package LC2100_2400;
import java.util.*;
public class LC2104_SumofSubarrayRanges {
    /**
     * You are given an integer array nums. The range of a subarray of nums is the difference between the largest and
     * smallest element in the subarray.
     *
     * Return the sum of all subarray ranges of nums.
     *
     * A subarray is a contiguous non-empty sequence of elements within an array.
     *
     * Input: nums = [1,2,3]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * -10^9 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // S1: brute force
    // time = O(n^2), space = O(1)
    public long subArrayRanges2(int[] nums) {
        int n = nums.length;
        long res = 0;
        for (int i = 0; i < n; i++) {
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (int j = i; j < n; j++) {
                min = Math.min(min, nums[j]);
                max = Math.max(max, nums[j]);
                res += max - min;
            }
        }
        return res;
    }

    // S2: Monotonic Stack
    // time = O(n), space = O(n)
    public long subArrayRanges(int[] nums) {
        int n = nums.length;
        Stack<Integer> stack = new Stack<>();

        int[] nextSmaller = new int[n];
        Arrays.fill(nextSmaller, n);
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                nextSmaller[stack.pop()] = i;
            }
            stack.push(i);
        }

        int[] prevSmallerOrEqual = new int[n];
        Arrays.fill(prevSmallerOrEqual, -1);
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                prevSmallerOrEqual[stack.pop()] = i;
            }
            stack.push(i);
        }

        int[] nextGreater = new int[n];
        Arrays.fill(nextGreater, n);
        stack.clear();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                nextGreater[stack.pop()] = i;
            }
            stack.push(i);
        }

        int[] prevGreaterOrEqual = new int[n];
        Arrays.fill(prevGreaterOrEqual, -1);
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
                prevGreaterOrEqual[stack.pop()] = i;
            }
            stack.push(i);
        }

        long res = 0;
        for (int i = 0; i < n; i++) {
            int l = prevGreaterOrEqual[i];
            int r = nextGreater[i];
            res += (long) nums[i] * (i - l) * (r - i);
        }

        for (int i = 0; i < n; i++) {
            int l = prevSmallerOrEqual[i];
            int r = nextSmaller[i];
            res -= (long) nums[i] * (i - l) * (r - i);
        }
        return res;
    }
}
/**
 * ref: LC907
 * sum of subarray maximums - sum of subarray minimum
 * = sum of subarray (max - min)
 * = sum of subarray (range)
 * 单调栈
 * sum of subarray minimums
 * 看哪些子区间是以nums[i]作为最小值的 => prevSmaller, nextSmaller
 * (i-l)*(r-i)
 * l: prevSmaller, r: nextSmaller
 * O(n) 求出数组里的每个元素的prevSmaller, nextSmaller
 * 如何求nextSmaller? 维护一个单调递增栈
 * 如果有多个相同最小值，一律看最右边。
 * 类似如果有若干个最大值，看最右边的最大值
 * 向左找的就是一个prevSmaller，向右找的是nextSmallerOrEqual
 */
