package LC601_900;

public class LC868_BinaryGap {
    /**
     * Given a positive integer n, find and return the longest distance between any two adjacent 1's in the binary
     * representation of n. If there are no two adjacent 1's, return 0.
     *
     * Two 1's are adjacent if there are only 0's separating them (possibly no 0's). The distance between two 1's is the
     * absolute difference between their bit positions. For example, the two 1's in "1001" have a distance of 3.
     *
     * Input: n = 22
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 10^9
     * @param n
     * @return
     */
    // S1: bit
    // time = O(1), space = O(1)
    public int binaryGap(int n) {
        int prev = -1, max = 0;
        for (int i = 0; i < 32; i++) {
            if (((n >> i) & 1) == 1) {
                if (prev != -1) max = Math.max(max, i - prev);
                prev = i;
            }
        }
        return max;
    }

    // S2: Binary String
    // time = O(1), space = O(1)
    public int binaryGap2(int n) {
        String s = Integer.toBinaryString(n);
        int prev = -1, max = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                if (prev != -1) max = Math.max(max, i - prev);
                prev = i;
            }
        }
        return max;
    }
}
