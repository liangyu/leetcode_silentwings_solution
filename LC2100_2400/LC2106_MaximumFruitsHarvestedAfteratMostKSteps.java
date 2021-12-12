package LC2100_2400;
import java.util.*;
public class LC2106_MaximumFruitsHarvestedAfteratMostKSteps {
    /**
     * Fruits are available at some positions on an infinite x-axis. You are given a 2D integer array fruits where
     * fruits[i] = [positioni, amounti] depicts amounti fruits at the position positioni. fruits is already sorted by
     * positioni in ascending order, and each positioni is unique.
     *
     * You are also given an integer startPos and an integer k. Initially, you are at the position startPos. From any
     * position, you can either walk to the left or right. It takes one step to move one unit on the x-axis, and you can
     * walk at most k steps in total. For every position you reach, you harvest all the fruits at that position, and the
     * fruits will disappear from that position.
     *
     * Return the maximum total number of fruits you can harvest.
     *
     * Input: fruits = [[2,8],[6,3],[8,6]], startPos = 5, k = 4
     * Output: 9
     *
     * Input: fruits = [[0,9],[4,1],[5,7],[6,2],[7,4],[10,9]], startPos = 5, k = 4
     * Output: 14
     *
     * Constraints:
     *
     * 1 <= fruits.length <= 10^5
     * fruits[i].length == 2
     * 0 <= startPos, positioni <= 2 * 10^5
     * positioni-1 < positioni for any i > 0 (0-indexed)
     * 1 <= amounti <= 10^4
     * 0 <= k <= 2 * 10^5
     * @param fruits
     * @param startPos
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int[] presum = new int[200005];
        int n = presum.length;
        for (int[] f : fruits) presum[f[0] + 1] = f[1];
        for (int i = 1; i < n; i++) presum[i] += presum[i - 1];
        startPos++;

        int res = 0;
        // option 1: first go left, then right
        for (int left = 0; left <= k; left++) {
            if (startPos - left - 1 < 0) break;
            int right = startPos + k - 2 * left;
            if (right >= n) right = n - 1;
            if (k - 2 * left < 0) right = startPos;
            res = Math.max(res, presum[right] - presum[startPos - left - 1]);
        }

        // option 2: first go right, then left
        for (int right = 0; right <= k; right++) {
            if (startPos + right >= n) break;
            int left = startPos - (k - 2 * right);
            if (left < 1) left = 1;
            if (k - 2 * right < 0) left = startPos;
            res = Math.max(res, presum[startPos + right] - presum[left - 1]);
        }
        return res;
    }
}
