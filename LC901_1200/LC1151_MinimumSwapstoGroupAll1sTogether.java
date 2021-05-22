package LC901_1200;
import java.util.*;
public class LC1151_MinimumSwapstoGroupAll1sTogether {
    /**
     * Given a binary array data, return the minimum number of swaps required to group all 1’s present in the array
     * together in any place in the array.
     *
     * Input: data = [1,0,1,0,1,0,1,1,1,0,1,0,0,1,1,1,0,0,1,1,1,0,1,0,1,1,0,0,0,1,1,1,1,0,0,1]
     * Output: 8
     *
     * Constraints:
     *
     * 1 <= data.length <= 10^5
     * data[i] is 0 or 1.
     * @param data
     * @return
     */
    // time = O(n), space = O(1)
    public int minSwaps(int[] data) {
        // corner case
        if (data == null || data.length == 0) return 0;

        int ones = 0;
        for (int n : data) {
            if (n == 1) ones++;
        }

        int n = data.length;
        int min = Integer.MAX_VALUE;
        int start = 0, end = 0, zeros = 0;
        while (end < n) {
            if (data[end] == 0) zeros++;
            if (end - start + 1 > ones) {
                if (data[start] == 0) zeros--;
                start++;
            }
            if (end - start + 1 == ones) min = Math.min(min, zeros);
            end++;
        }
        return min;
    }
}
/**
 * 首先统计一下 input 数组 里面一共有多少个 1，记为 count，这样我们也就知道了当 swap 完成之后，由 1 组成的子数组的长度是多少。
 * 接着我们开始滑动窗口的操作，end 指针一边移动，一边记录 0 的出现次数。当 start 和 end 指针之间的距离 == count 的时候，
 * 说明这一段的长度满足，同时又由于我们用 end 指针记录了这一路上 0 的出现次数，所以如果要把目前这一段（end - start + 1）都变成 1，
 * 需要 swap 的次数其实就等于这一段里 0 的个数。一直遍历，直到找到最小的 swap 次数。
 */