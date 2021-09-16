package LC601_900;

public class LC848_ShiftingLetters {
    /**
     * You are given a string s of lowercase English letters and an integer array shifts of the same length.
     *
     * Call the shift() of a letter, the next letter in the alphabet, (wrapping around so that 'z' becomes 'a').
     *
     * For example, shift('a') = 'b', shift('t') = 'u', and shift('z') = 'a'.
     * Now for each shifts[i] = x, we want to shift the first i + 1 letters of s, x times.
     *
     * Return the final string after all such shifts to s are applied.
     *
     * Input: s = "abc", shifts = [3,5,9]
     * Output: "rpl"
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists of lowercase English letters.
     * shifts.length == s.length
     * 0 <= shifts[i] <= 10^9
     * @param s
     * @param shifts
     * @return
     */
    // S1: prefix sum
    // time = O(n), space = O(n)
    public String shiftingLetters(String s, int[] shifts) {
        int n = s.length();
        char[] chars = s.toCharArray();
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            presum[i] = (presum[i - 1] + shifts[n - i]) % 26; // 通过 % 把大数减小！
        }

        for (int i = 1; i <= n; i++) {
            int idx = chars[i - 1] - 'a';
            chars[i - 1] = (char)((idx + presum[n + 1 - i]) % 26 + 'a');
        }
        return String.valueOf(chars);
    }
}