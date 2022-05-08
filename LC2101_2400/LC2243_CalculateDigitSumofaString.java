package LC2101_2400;

public class LC2243_CalculateDigitSumofaString {
    /**
     * You are given a string s consisting of digits and an integer k.
     *
     * A round can be completed if the length of s is greater than k. In one round, do the following:
     *
     * Divide s into consecutive groups of size k such that the first k characters are in the first group, the next k
     * characters are in the second group, and so on. Note that the size of the last group can be smaller than k.
     * Replace each group of s with a string representing the sum of all its digits. For example, "346" is replaced with
     * "13" because 3 + 4 + 6 = 13.
     * Merge consecutive groups together to form a new string. If the length of the string is greater than k, repeat
     * from step 1.
     * Return s after all rounds have been completed.
     *
     * Input: s = "11111222223", k = 3
     * Output: "135"
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * 2 <= k <= 100
     * s consists of digits only.
     * @param s
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public String digitSum(String s, int k) {
        while (s.length() > k) {
            StringBuilder sb = new StringBuilder();
            int n = s.length();
            for (int i = 0; i < n; i++) {
                int j = Math.min(i + k, n);
                String str = s.substring(i, j);
                int sum = 0;
                for (int t = 0; t < j - i; t++) {
                    sum += str.charAt(t) - '0';
                }
                sb.append(sum);
                i = j - 1;
            }
            s = sb.toString();
        }
        return s;
    }
}
