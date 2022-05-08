package LC2101_2400;

public class LC2222_NumberofWaystoSelectBuildings {
    /**
     * You are given a 0-indexed binary string s which represents the types of buildings along a street where:
     *
     * s[i] = '0' denotes that the ith building is an office and
     * s[i] = '1' denotes that the ith building is a restaurant.
     * As a city official, you would like to select 3 buildings for random inspection. However, to ensure variety, no
     * two consecutive buildings out of the selected buildings can be of the same type.
     *
     * For example, given s = "001101", we cannot select the 1st, 3rd, and 5th buildings as that would form "011" which
     * is not allowed due to having two consecutive buildings of the same type.
     * Return the number of valid ways to select 3 buildings.
     *
     * Input: s = "001101"
     * Output: 6
     *
     * Input: s = "11100"
     * Output: 0
     *
     * Constraints:
     *
     * 3 <= s.length <= 105
     * s[i] is either '0' or '1'.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public long numberOfWays(String s) {
        int n = s.length();
        int[] pre0 = new int[n + 1];
        int[] pre1 = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            pre0[i] = pre0[i - 1] + (s.charAt(i - 1) == '0' ? 1 : 0);
            pre1[i] = pre1[i - 1] + (s.charAt(i - 1) == '1' ? 1 : 0);
        }

        long res = 0;
        for (int i = 1; i <= n; i++) {
            char c = s.charAt(i - 1);
            if (c == '0') res += pre1[i] * (pre1[n] - pre1[i]);
            if (c == '1') res += pre0[i] * (pre0[n] - pre0[i]);
        }
        return res;
    }
}
