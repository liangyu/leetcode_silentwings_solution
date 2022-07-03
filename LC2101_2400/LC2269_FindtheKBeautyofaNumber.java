package LC2101_2400;

public class LC2269_FindtheKBeautyofaNumber {
    /**
     * The k-beauty of an integer num is defined as the number of substrings of num when it is read as a string that
     * meet the following conditions:
     *
     * It has a length of k.
     * It is a divisor of num.
     * Given integers num and k, return the k-beauty of num.
     *
     * Note:
     *
     * Leading zeros are allowed.
     * 0 is not a divisor of any value.
     * A substring is a contiguous sequence of characters in a string.
     *
     * Input: num = 240, k = 2
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= num <= 10^9
     * 1 <= k <= num.length (taking num as a string)
     * @param num
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int divisorSubstrings(int num, int k) {
        String s = String.valueOf(num);
        int n = s.length(), count = 0;
        for (int i = 0; i + k - 1 < n; i++) {
            String sub = s.substring(i, i + k);
            int val = Integer.parseInt(sub);
            if (val == 0) continue;
            if (num % val == 0) count++;
        }
        return count;
    }
}
