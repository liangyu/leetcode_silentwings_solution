package LC901_1200;

public class LC1009_ComplementofBase10Integer {
    /**
     * The complement of an integer is the integer you get when you flip all the 0's to 1's and all the 1's to 0's in
     * its binary representation.
     *
     * For example, The integer 5 is "101" in binary and its complement is "010" which is the integer 2.
     * Given an integer n, return its complement.
     *
     * Input: n = 5
     * Output: 2
     *
     * Constraints:
     *
     * 0 <= n < 10^9
     *
     *
     * Note: This question is the same as 476: https://leetcode.com/problems/number-complement/
     * @param n
     * @return
     */
    // S1
    // time = O(1), space = O(1)
    public int bitwiseComplement(int n) {
        // corner case
        if (n == 0) return 1;

        int res = 0, i = 0;
        while (n > 0) {
            if ((n & 1) == 0) res += (int) Math.pow(2, i);
            i++;
            n >>= 1;
        }
        return res;
    }

    // S2
    // time = O(1), space = O(1)
    public int bitwiseComplement2(int N) {
        String s = Integer.toBinaryString(N);
        int n = s.length();
        int res = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '0') res += (int) Math.pow(2, n - 1 - i);
        }
        return res;
    }
}
