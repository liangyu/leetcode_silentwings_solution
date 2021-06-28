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
            bucket[chars[i] - '0'] = i;
        }

        for (int i = 0; i < chars.length; i++) {
            for (int j = 9; j > chars[i] - '0'; j--) {
                if (bucket[j] > i) {
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
