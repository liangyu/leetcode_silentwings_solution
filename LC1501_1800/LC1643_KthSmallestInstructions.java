package LC1501_1800;

public class LC1643_KthSmallestInstructions {
    /**
     * Bob is standing at cell (0, 0), and he wants to reach destination: (row, column). He can only travel right and
     * down. You are going to help Bob by providing instructions for him to reach destination.
     *
     * The instructions are represented as a string, where each character is either:
     *
     * 'H', meaning move horizontally (go right), or
     * 'V', meaning move vertically (go down).
     * Multiple instructions will lead Bob to destination. For example, if destination is (2, 3), both "HHHVV" and
     * "HVHVH" are valid instructions.
     *
     * However, Bob is very picky. Bob has a lucky number k, and he wants the kth lexicographically smallest
     * instructions that will lead him to destination. k is 1-indexed.
     *
     * Given an integer array destination and an integer k, return the kth lexicographically smallest instructions
     * that will take Bob to destination.
     *
     * Input: destination = [2,3], k = 1
     * Output: "HHHVV"
     *
     * Constraints:
     *
     * destination.length == 2
     * 1 <= row, column <= 15
     * 1 <= k <= nCr(row + column, row), where nCr(a, b) denotes a choose b.
     * @param destination
     * @param k
     * @return
     */
    // time = O(n * m), space = O(n)
    public String kthSmallestPath(int[] destination, int k) {
        int v = destination[0];
        int h = destination[1];
        int n = h + v;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) { // O(n)
            if (h == 0) {
                sb.append('V');
                v--;
                continue;
            } else if (v == 0) {
                sb.append('H');
                h--;
                continue;
            }

            int c = comb(h - 1 + v, v);
            if (k <= c) {
                h--;
                sb.append('H');
            } else {
                sb.append('V');
                v--;
                k -= c;
            }
        }
        return sb.toString();
    }

    private int comb(int n, int m) {
        int cnt = 1;
        for (int i = 0; i < m; i++) { // O(m)
            cnt *= n - i;
            cnt /= i + 1;
        }
        return cnt;
    }
}
/**
 * 只能向右或者向下走 => 向右和向下走的次数都是"确定"的
 * => 编码里H和V的个数是固定的，但H和V出现的顺序不一样
 * 相当于求字典序第k小的全排列 => ref: LC60 permutation sequence
 * 解法类似二分思想：
 * HHHVV
 * H**** => C(4,2) = 6
 * V**** => 3H1V
 */