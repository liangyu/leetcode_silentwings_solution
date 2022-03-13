package LC1501_1800;

public class LC1769_MinimumNumberofOperationstoMoveAllBallstoEachBox {
    /**
     * You have n boxes. You are given a binary string boxes of length n, where boxes[i] is '0' if the ith box is empty,
     * and '1' if it contains one ball.
     *
     * In one operation, you can move one ball from a box to an adjacent box. Box i is adjacent to box j if
     * abs(i - j) == 1. Note that after doing so, there may be more than one ball in some boxes.
     *
     * Return an array answer of size n, where answer[i] is the minimum number of operations needed to move all the
     * balls to the ith box.
     *
     * Each answer[i] is calculated considering the initial state of the boxes.
     *
     * Input: boxes = "110"
     * Output: [1,1,3]
     *
     * Constraints:
     *
     * n == boxes.length
     * 1 <= n <= 2000
     * boxes[i] is either '0' or '1'.
     * @param boxes
     * @return
     */
    // S1: brute-force
    // time = O(n^2), space = O(1)
    public int[] minOperations(String boxes) {
        int n = boxes.length();
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == i || boxes.charAt(j) == '0') continue;
                res[i] += Math.abs(j - i);
            }
        }
        return res;
    }

    // S2: 3-pass
    // time = O(n), space = O(n)
    public int[] minOperations2(String boxes) {
        int n = boxes.length();
        int[] leftMoves = new int[n], rightMoves = new int[n];
        int left = 0, right = 0;

        for (int i = 1; i < n; i++) {
            left += boxes.charAt(i - 1) == '1' ? 1 : 0; // 看当前位左边是否为1
            leftMoves[i] = leftMoves[i - 1] + left * 1;
        }

        for (int i = n - 2; i >= 0; i--) {
            right += boxes.charAt(i + 1) == '1' ? 1 : 0; // 看当前位右边是否为1
            rightMoves[i] = rightMoves[i + 1] + right * 1;
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = leftMoves[i] + rightMoves[i];
        }
        return res;
    }
}
/**
 * 重复利用搬运关系
 * leftMoves[i]: 左边所有盒子搬过来需要花多少步
 * leftMoves[i] = leftMoves[i-1] + left[i] * 1
 * [x x x x] x o
 * rightMoves[i] = rightMoves[i + 1] + right[i] * 1
 * res[i] = leftMoves[i] + rightMoves[i]
 */