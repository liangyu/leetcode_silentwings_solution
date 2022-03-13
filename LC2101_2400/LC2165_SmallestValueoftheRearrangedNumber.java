package LC2101_2400;
import java.util.*;
public class LC2165_SmallestValueoftheRearrangedNumber {
    /**
     * You are given an integer num. Rearrange the digits of num such that its value is minimized and it does not
     * contain any leading zeros.
     *
     * Return the rearranged number with minimal value.
     *
     * Note that the sign of the number does not change after rearranging the digits.
     *
     * Input: num = 310
     * Output: 103
     *
     * Input: num = -7605
     * Output: -7650
     * Constraints:
     *
     * -101^5 <= num <= 10^15
     * @param num
     * @return
     */
    // time = O(nlogn), space = O(n)
    public long smallestNumber(long num) {
        int sign = num < 0 ? -1 : 1;
        List<Long> nums = new ArrayList<>();
        if (num < 0) num = -num;
        while (num > 0) {
            nums.add(num % 10);
            num /= 10;
        }

        long res = 0;
        int n = nums.size();
        if (sign == 1) {
            Collections.sort(nums);
            int j = 0;
            while (j < n && nums.get(j) == 0) j++;
            if (j == n) res = 0;
            else {
                int zero = j;
                res = nums.get(j);
                for (int i = 0; i < zero; i++) res = res * 10 + 0;
                for (int i = j + 1; i < n; i++) res = res * 10 + nums.get(i);
            }
        } else {
            Collections.sort(nums, (o1, o2) -> Long.compare(o2, o1));
            for (int i = 0; i < n; i++) res = res * 10 + nums.get(i);
        }
        return res * sign;
    }
}
