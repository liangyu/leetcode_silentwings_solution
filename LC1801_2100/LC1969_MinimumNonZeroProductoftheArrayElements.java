package LC1801_2100;

public class LC1969_MinimumNonZeroProductoftheArrayElements {
    /**
     * You are given a positive integer p. Consider an array nums (1-indexed) that consists of the integers in the
     * inclusive range [1, 2p - 1] in their binary representations. You are allowed to do the following operation any
     * number of times:
     *
     * Choose two elements x and y from nums.
     * Choose a bit in x and swap it with its corresponding bit in y. Corresponding bit refers to the bit that is in
     * the same position in the other integer.
     * For example, if x = 1101 and y = 0011, after swapping the 2nd bit from the right, we have x = 1111 and y = 0001.
     *
     * Find the minimum non-zero product of nums after performing the above operation any number of times. Return this
     * product modulo 109 + 7.
     *
     * Note: The answer should be the minimum product before the modulo operation is done.
     *
     * Input: p = 1
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= p <= 60
     * @param p
     * @return
     */
    // time = O(p), space = O(1)
    long M = (long)(1e9 + 7);
    public int minNonZeroProduct(int p) {
        long a = (1L << (p - 1)) - 1; // 指数不能取模
        long base = ((1L << p) - 2) % M; // base * base可能溢出
        return (int)(quickMul(base, a) * (base + 1) % M);
    }

    private long quickMul(long x, long N) {
        if (N == 0) return 1;
        long y = quickMul(x, N / 2);
        return N % 2 == 0 ? (y * y % M) : (y * y % M * x % M);
    }
}
/**
 * 001
 * 010
 * 011
 * 100
 * 101
 * 110
 * 111
 *
 * 和是固定的，拆解 -> 让乘积尽量小
 * 希望把它拆分得不均匀些
 * 相反，拆成一样的元素，就会让乘积尽量大
 * sum = 6 => 3 * 3 = 9
 * 2 * 4 = 8 < 9
 * 1 * 5 = 5 更小
 * 0 * 6 = 0
 * 只要拆得越均匀，乘积越大；拆解越极端，乘积就越小
 *
 * 000
 * 000
 * 000
 * 111
 * 111
 * 111
 * 111
 * 如果p是任意的，那能得到多少个全1呢？
 * 1xx
 * 2^(p-1)个全1
 * 1-index -> 2^(p-1)-1个（全00不再这个队列里）
 * 题目要求非0 -> 拆得不那么极端
 * 尽量多搞出1来
 * 001
 * 001
 * 001
 * 110
 * 110
 * 110
 * 111
 *
 * 3个110已经没有任何余地做重组了，再怎么重组都是一样的，而其他部分都是重组过后的最优解了。
 * 这就是最终的拆分方法，把原来的2^(p-1)个全0变成1
 * 贪心的思想，"差大积小"，和固定的时候拆分成差比较大的元素，乘积就会小。
 */
