package LC2100_2400;

public class LC2147_NumberofWaystoDivideaLongCorridor {
    /**
     * Along a long library corridor, there is a line of seats and decorative plants. You are given a 0-indexed string
     * corridor of length n consisting of letters 'S' and 'P' where each 'S' represents a seat and each 'P' represents
     * a plant.
     *
     * One room divider has already been installed to the left of index 0, and another to the right of index n - 1.
     * Additional room dividers can be installed. For each position between indices i - 1 and i (1 <= i <= n - 1), at
     * most one divider can be installed.
     *
     * Divide the corridor into non-overlapping sections, where each section has exactly two seats with any number of
     * plants. There may be multiple ways to perform the division. Two ways are different if there is a position with a
     * room divider installed in the first way but not in the second way.
     *
     * Return the number of ways to divide the corridor. Since the answer may be very large, return it modulo 109 + 7.
     * If there is no way, return 0.
     *
     * Input: corridor = "SSPPSPS"
     * Output: 3
     *
     * Input: corridor = "PPSPSP"
     * Output: 1
     *
     * Constraints:
     *
     * n == corridor.length
     * 1 <= n <= 10^5
     * corridor[i] is either 'S' or 'P'.
     * @param corridor
     * @return
     */
    // time = O(n), space = O(n)
    public int numberOfWays(String corridor) {
        int n = corridor.length(), count = 0;
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            if (corridor.charAt(i) == 'S') count++;
            arr[i] = count;
        }
        if (count == 0 || count % 2 == 1) return 0;

        long M = (long)(1e9 + 7), res = 1;
        int j = -1, th = 2;
        for (int i = 0; i < n; i++) {
            if (arr[i] == th && j == -1) j = i;
            if (arr[i] == th + 1) {
                res = res * (i - j) % M;
                j = -1;
                th += 2;
            }
        }
        return (int) res;
    }
}
