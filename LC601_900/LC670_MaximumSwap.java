package LC601_900;
import java.util.*;
public class LC670_MaximumSwap {
    /**
     * You are given an integer num. You can swap two digits at most once to get the maximum valued number.
     *
     * Return the maximum valued number you can get.
     *
     * Input: num = 2736
     * Output: 7236
     *
     * Constraints:
     *
     * 0 <= num <= 10^8
     * @param num
     * @return
     */
    // time = O(n), space = O(1)
    public int maximumSwap(int num) {
        char[] chars = String.valueOf(num).toCharArray();

        int[] bucket = new int[10];
        for (int i = 0; i < chars.length; i++) {
            bucket[chars[i] - '0'] = i; // the last pos of the digit
        }

        for (int i = 0; i < chars.length; i++) {
            for (int j = 9; j > chars[i] - '0'; j--) {
                if (bucket[j] > i) { // 找到第一个比chars[i]大但是在i之后的数字与i交换
                    char temp = chars[i]; // idx = bucket[j], i
                    chars[i] = chars[bucket[j]];
                    chars[bucket[j]] = temp;
                    return Integer.parseInt(String.valueOf(chars));
                }
            }
        }
        return num;
    }
}
/**
 * 最高位与最大值交换
 * 如果有多个最大值，我们选最后一个进行交换
 * 递减序的话也不用动它
 * 99234 -> 99432
 * 9923443 -> 9944332
 */