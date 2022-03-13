package LC601_900;

public class LC717_1bitand2bitCharacters {
    /**
     * We have two special characters:
     *
     * The first character can be represented by one bit 0.
     * The second character can be represented by two bits (10 or 11).
     * Given a binary array bits that ends with 0, return true if the last character must be a one-bit character.
     *
     * Input: bits = [1,0,0]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= bits.length <= 1000
     * bits[i] is either 0 or 1.
     * @param bits
     * @return
     */
    // time = O(n), space = O(1)
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length, i = 0;
        for (i = 0; i < n - 1; i++) {
            if (bits[i] == 0) continue; // move 1 step
            else i++; // move 2 steps
        }
        return i == n - 1;
    }
}
/**
 * 遇到 1 跳两步，遇到 0 跳一步，
 * 利用 step 记录从上一个位置跳到当前位置的步数，
 * 当遍历完数组，如果最后一步跳了 1 步，说明是符合题意的，返回 true，否则返回 false
 */