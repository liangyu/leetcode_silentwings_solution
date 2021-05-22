package LC001_300;
import java.util.*;
public class LC190_ReverseBits {
    /**
     * Reverse bits of a given 32 bits unsigned integer.
     *
     * Note:
     *
     * Note that in some languages such as Java, there is no unsigned integer type. In this case, both input and output
     * will be given as a signed integer type. They should not affect your implementation, as the integer's internal
     * binary representation is the same, whether it is signed or unsigned.
     * In Java, the compiler represents the signed integers using 2's complement notation. Therefore, in Example 2
     * above, the input represents the signed integer -3 and the output represents the signed integer -1073741825.
     * Follow up:
     *
     * If this function is called many times, how would you optimize it?
     *
     * Input: n = 00000010100101000001111010011100
     * Output:    964176192 (00111001011110000010100101000000)
     *
     * Constraints:
     *
     * The input must be a binary string of length 32
     * @param n
     * @return
     */
    // you need treat n as an unsigned value
    // time = O(1), space = O(1)
    public int reverseBits(int n) {
        // corner case
        if (n == 0) return 0;

        int res = 0;
        for (int i = 0; i < 32; i++) {
            res <<= 1;
            if ((n & 1) == 1) res++;
            n >>= 1;
        }
        return res;
    }
}
