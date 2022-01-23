package LC1501_1800;
import java.util.*;
public class LC1562_FindLatestGroupofSizeM {
    /**
     * Given an array arr that represents a permutation of numbers from 1 to n.
     *
     * You have a binary string of size n that initially has all its bits set to zero. At each step i (assuming both
     * the binary string and arr are 1-indexed) from 1 to n, the bit at position arr[i] is set to 1.
     *
     * You are also given an integer m. Find the latest step at which there exists a group of ones of length m. A group
     * of ones is a contiguous substring of 1's such that it cannot be extended in either direction.
     *
     * Return the latest step at which there exists a group of ones of length exactly m. If no such group exists,
     * return -1.
     *
     * Input: arr = [3,5,1,2,4], m = 1
     * Output: 4
     *
     * Constraints:
     *
     * n == arr.length
     * 1 <= m <= n <= 10^5
     * 1 <= arr[i] <= n
     * All integers in arr are distinct.
     * @param arr
     * @param m
     * @return
     */
    // time = O(n), space = O(n)
    public int findLatestStep(int[] arr, int m) {
        int n = arr.length, res = -1;
        int[] day = new int[n];
        for (int i = 0; i < n; i++) day[arr[i] - 1] = i + 1;

        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            while (!deque.isEmpty() && day[deque.peekLast()] < day[i]) deque.pollLast();
            while (!deque.isEmpty() && i - deque.peekFirst() >= m) deque.pollFirst();
            deque.offer(i);
            if (i < m - 1) continue; // i - m + 1 >= 0 -> i >= m - 1
            int t = day[deque.peekFirst()];
            // i-m, [i-m+1,...i],i+1
            int left = Integer.MAX_VALUE, right = Integer.MAX_VALUE;
            if (i - m >= 0) left = day[i - m]; // 注意：i-m和i+1可能没有，所以预设为Integer.MAX_VALUE
            if (i + 1 < n) right = day[i + 1];
            if (left > t && right > t) res = Math.max(res, Math.min(left, right) - 1); // 注意：res可能为Integer.MAX_VALUE - 1!
        }
        return res == Integer.MAX_VALUE - 1 ? n : res; // i-m和i+1都没有 -> m == n => 每天翻1位，共需要n天！
    }
}
/**
 * 010[111]001
 * arr[3] = 4, on the 3rd day, we flip 4th bit to 1
 * day[4] = 3, the day when we flip 4th bit to one is 3
 * 要求恰好出现m个1，并不是时间往后推，出现m个1的概率就大
 * 所以不能用二分搜值！
 * 假如第5天m连1恰好出现，表明其左边和右边不能出现
 * 否则这个m连1绝对不可能是答案
 * t: the earliest day when the interval becomes all ones
 * [i,j]
 * day[i-1] > t
 * day[j+1] > t
 * min(day[i-1],day[j+1])-1  -> 恰好是保证m连1的最后一天
 * [t ~ min(day[i-1], day[j+1])-1]
 * sliding window
 * 大循环O(n)
 * 如何求出一个固定区间里的最大值 => day数组的最大值
 * sliding window maximum => deque
 *
 */
