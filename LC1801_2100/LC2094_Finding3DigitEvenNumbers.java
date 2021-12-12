package LC1801_2100;
import java.util.*;
public class LC2094_Finding3DigitEvenNumbers {
    /**
     * You are given an integer array digits, where each element is a digit. The array may contain duplicates.
     *
     * You need to find all the unique integers that follow the given requirements:
     *
     * The integer consists of the concatenation of three elements from digits in any arbitrary order.
     * The integer does not have leading zeros.
     * The integer is even.
     * For example, if the given digits were [1, 2, 3], integers 132 and 312 follow the requirements.
     *
     * Return a sorted array of the unique integers.
     *
     * Input: digits = [2,1,3,0]
     * Output: [102,120,130,132,210,230,302,310,312,320]
     *
     * Constraints:
     *
     * 3 <= digits.length <= 100
     * 0 <= digits[i] <= 9
     * @param digits
     * @return
     */
    // time = O(1), space = O(1)
    public int[] findEvenNumbers(int[] digits) {
        int[] freq = new int[10];
        for (int x : digits) freq[x]++;

        List<Integer> res = new ArrayList<>();
        int[] count = new int[10];

        for (int k = 100; k < 1000; k += 2) {
            int val = k;
            boolean flag = true;
            Arrays.fill(count, 0);

            while (val > 0) {
                count[val % 10]++; // 统计组成val这个数的每个数字的count是否在给出的freq范围内
                val /= 10;
            }

            for (int i = 0; i < 10; i++) {
                if (count[i] > freq[i]) {
                    flag = false; // 超出了freq范围，肯定组成不了val
                    break;
                }
            }
            if (flag) res.add(k);
        }

        int[] ans = new int[res.size()];
        for (int i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }
}
