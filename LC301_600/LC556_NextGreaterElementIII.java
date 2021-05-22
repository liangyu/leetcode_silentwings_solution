package LC301_600;
import java.util.*;
public class LC556_NextGreaterElementIII {
    /**
     * Given a positive integer n, find the smallest integer which has exactly the same digits existing in the integer
     * n and is greater in value than n. If no such positive integer exists, return -1.
     *
     * Note that the returned integer should fit in 32-bit integer, if there is a valid answer but it does not fit in
     * 32-bit integer, return -1.
     *
     * Example 1:
     *
     * Input: n = 12
     * Output: 21
     *
     * Example 2:
     *
     * Input: n = 21
     * Output: -1
     *
     * 230241  --> 230421 --> 230412
     * @param n
     * @return
     */
    // time = O(n), space = O(n)
    public int nextGreaterElement(int n) {
        char[] chars = ("" + n).toCharArray();
        int i = chars.length - 2;
        while (i >= 0 && chars[i + 1] <= chars[i]) i--; // chars[i] = 2
        if (i < 0) return -1;
        int j = chars.length - 1;
        while (j >= 0 && chars[j] <= chars[i]) j--; // chars[j] = 4
        swap(chars, i, j);
        reverse(chars, i + 1);
        long val = Long.valueOf(new String(chars));
        return val > Integer.MAX_VALUE ? -1 : (int)val;
    }

    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    private void reverse(char[] chars, int i) {
        int start = i, end = chars.length - 1;
        while (start < end) swap(chars, start++, end--);
    }
}

// 2 2 4 3 2 1
//   i   j
// step 1: 出现swap的条件 -> 从尾部开始，nums[i] < nums[i + 1]  2 < 4
// step 2: nums[i]与之前访问过的哪个进行swap??? --> 从尾部开始向前看，> nums[i]的最小值，因为尾部是低位，尽量swap低位的数字以实现结果
// 最小
// step 3: swap nums[i] and nums[j] --> 2 3 4 2 2 1
// step 4: 因为在[j, len - 1]之间的值都是 <= nums[i] ==> reverse[j, len - 1]已实现把最小位换到最高位上去，由于原先是单调的，所以
// 直接reverse就能实现最小！！！