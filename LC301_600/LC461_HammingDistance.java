package LC301_600;

public class LC461_HammingDistance {
    /**
     * The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
     *
     * Given two integers x and y, return the Hamming distance between them.
     *
     * Input: x = 1, y = 4
     * Output: 2
     *
     * Constraints:
     *
     * 0 <= x, y <= 2^31 - 1
     * @param x
     * @param y
     * @return
     */
    // S1: BitCount
    // time = O(1), space = O(1)
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

    // S2: Bit Operation
    // time = O(1), space = O(1)
    public int hammingDistance2(int x, int y) {
        int num = x ^ y, count = 0;
        while (num != 0) {
            if ((num & 1) == 1) count++;
            num >>= 1;
        }
        return count;
    }
}
