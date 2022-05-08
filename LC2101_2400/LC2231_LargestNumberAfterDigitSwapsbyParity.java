package LC2101_2400;
import java.util.*;
public class LC2231_LargestNumberAfterDigitSwapsbyParity {
    /**
     * You are given a positive integer num. You may swap any two digits of num that have the same parity (i.e. both odd
     * digits or both even digits).
     *
     * Return the largest possible value of num after any number of swaps.
     *
     * Input: num = 1234
     * Output: 3412
     *
     * Constraints:
     *
     * 1 <= num <= 10^9
     * @param num
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int largestInteger(int num) {
        List<Integer> odd = new ArrayList<>();
        List<Integer> even = new ArrayList<>();

        int val = num;
        while (val > 0) {
            int mod = val % 10;
            val /= 10;

            if (mod % 2 == 1) odd.add(mod);
            else even.add(mod);
        }

        Collections.sort(odd);
        Collections.sort(even);

        int res = 0, pow = 1;
        val = num;
        int oddIdx = 0, evenIdx = 0;
        while (val > 0) {
            int mod = val % 10;
            val /= 10;

            if (mod % 2 == 1) res += odd.get(oddIdx++) * pow;
            else res += even.get(evenIdx++)* pow;
            pow *= 10;
        }
        return res;
    }
}
