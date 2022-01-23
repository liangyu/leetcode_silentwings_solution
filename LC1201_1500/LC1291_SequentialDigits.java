package LC1201_1500;
import java.util.*;
public class LC1291_SequentialDigits {
    /**
     * n integer has sequential digits if and only if each digit in the number is one more than the previous digit.
     *
     * Return a sorted list of all the integers in the range [low, high] inclusive that have sequential digits.
     *
     * Input: low = 100, high = 300
     * Output: [123,234]
     *
     * Constraints:
     *
     * 10 <= low <= high <= 10^9
     * @param low
     * @param high
     * @return
     */
    // S1
    // time = O(1), space = O(1)
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> res = new ArrayList<>();
        String s1 = String.valueOf(low), s2 = String.valueOf(high);


        for (int i = s1.length(); i <= s2.length(); i++) {
            for (int j = 1; j + i - 1 <= 9; j++) {
                StringBuilder sb = new StringBuilder();
                for (int k = j; k < j + i; k++) {
                    sb.append(k);
                }
                int num = Integer.parseInt(sb.toString());
                if (num >= low && num <= high) res.add(num);
            }
        }
        return res;
    }

    // S2:
    // time = O(1), space = O(1)
    public List<Integer> sequentialDigits2(int low, int high) {
        List<Integer> res = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            int num = i;
            for (int j = i + 1; j <= 9; j++) {
                num = num * 10 + j;
                if (num >= low && num <= high) res.add(num);
            }
        }
        Collections.sort(res);
        return res;
    }
}
