package LC2100_2400;

public class LC2109_AddingSpacestoaString {
    /**
     * You are given a 0-indexed string s and a 0-indexed integer array spaces that describes the indices in the
     * original string where spaces will be added. Each space should be inserted before the character at the given index.
     *
     * For example, given s = "EnjoyYourCoffee" and spaces = [5, 9], we place spaces before 'Y' and 'C', which are at
     * indices 5 and 9 respectively. Thus, we obtain "Enjoy Your Coffee".
     * Return the modified string after the spaces have been added.
     *
     * Input: s = "LeetcodeHelpsMeLearn", spaces = [8,13,15]
     * Output: "Leetcode Helps Me Learn"
     *
     * Constraints:
     *
     * 1 <= s.length <= 3 * 10^5
     * s consists only of lowercase and uppercase English letters.
     * 1 <= spaces.length <= 3 * 10^5
     * 0 <= spaces[i] <= s.length - 1
     * All the values of spaces are strictly increasing.
     * @param s
     * @param spaces
     * @return
     */
    // time = O(n), space = O(n)
    public String addSpaces(String s, int[] spaces) {
        StringBuilder sb = new StringBuilder();
        int n = s.length(), m = spaces.length, idx = 0;

        for (int i = 0; i < n; i++) {
            if (idx < m && i == spaces[idx]) {
                sb.append(' ');
                idx++;
            }
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }
}
