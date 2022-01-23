package LC1201_1500;
import java.util.*;
public class LC1438_LongestContinuousSubarrayWithAbsoluteDiffLessThanorEqualtoLimit {
    /**
     * Given an array of integers nums and an integer limit, return the size of the longest non-empty subarray such that
     * the absolute difference between any two elements of this subarray is less than or equal to limit.
     *
     * Input: nums = [8,2,4,7], limit = 4
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 0 <= limit <= 10^9
     * @param nums
     * @param limit
     * @return
     */
    // S1: TreeSet
    // time = O(nlogn), space = O(n)
    public int longestSubarray(int[] nums, int limit) {
        TreeSet<int[]> set = new TreeSet<>((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
        set.add(new int[]{nums[0], 0});
        int n= nums.length;

        int res = 0, j = 0; // right pointer
        for (int i = 0; i < n; i++) {
            while (j < n && set.last()[0] - set.first()[0] <= limit) {
                res = Math.max(res, j - i + 1);
                j++;
                if (j < n) set.add(new int[]{nums[j], j});
            }
            if (j == n) break; // j走到底了，i再移动已经毫无意义了
            set.remove(new int[]{nums[i], i});
        }
        return res;
    }

    // S2: deque
    // time = O(n), space = O(n)
    public int longestSubarray2(int[] nums, int limit) {
        Deque<Integer> qmax = new LinkedList<>();
        Deque<Integer> qmin = new LinkedList<>();
        qmax.offer(0);
        qmin.offer(0);

        int n = nums.length, j = 0, res = 0, max = nums[0], min = nums[0];
        for (int i = 0; i < n; i++) {
            while (max - min <= limit) {
                res = Math.max(res, j - i + 1);
                j++;
                if (j == n) break;
                // add nums[i] to qmax and qmin
                // get updated with max and min
                while (!qmax.isEmpty() && nums[qmax.peekLast()] <= nums[j]) qmax.pollLast();
                qmax.offer(j);
                max = nums[qmax.peekFirst()]; // not move i yet, so do not need to check if the head is outdated or not

                while (!qmin.isEmpty() && nums[qmin.peekLast()] >= nums[j]) qmin.pollLast();
                qmin.offer(j);
                min = nums[qmin.peekFirst()];
            }
            if (j == n) break;
            if (!qmax.isEmpty() && qmax.peekFirst() == i) {
                qmax.pollFirst();
                if (!qmax.isEmpty()) max = nums[qmax.peekFirst()];
            }
            if (!qmin.isEmpty() && qmin.peekFirst() == i) {
                qmin.pollFirst();
                if (!qmin.isEmpty()) min = nums[qmin.peekFirst()];
            }
        }
        return res;
    }
}
/**
 * xx v x p xxx v xx
 * xxxxxxxxxx
 * ^   ^
 * 为什么左端点移动后，右端点不归0
 * 因为我们要求的是个longest subarray
 * res = k
 * 左端点移动后，已经只有k-1，而我们res的长度至少为k，所以没必要让j回到i再从头开始
 * => 右端点不用回退，因为没有意义
 * 左右端点都是单向，O(n)
 * 如何高效求出这个区间里的最大和最小值
 * 最naive的做法就是全部遍历一遍 => O(n^2)
 * 如果是自动排序的容器 => O(nlogn)
 * 能不能用O(1) => deque 来实现一个单调队列，专治sliding window maximum/minimum => diff <= limit
 * 大框架双指针，本质也是个sliding window maximum
 */