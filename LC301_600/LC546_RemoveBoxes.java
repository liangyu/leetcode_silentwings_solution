package LC301_600;

public class LC546_RemoveBoxes {
    /**
     * You are given several boxes with different colors represented by different positive numbers.
     *
     * You may experience several rounds to remove boxes until there is no box left. Each time you can choose some
     * continuous boxes with the same color (i.e., composed of k boxes, k >= 1), remove them and get k * k points.
     *
     * Return the maximum points you can get.
     *
     * Input: boxes = [1,3,2,2,2,3,4,3,1]
     * Output: 23
     * Explanation:
     * [1, 3, 2, 2, 2, 3, 4, 3, 1]
     * ----> [1, 3, 3, 4, 3, 1] (3*3=9 points)
     * ----> [1, 3, 3, 3, 1] (1*1=1 points)
     * ----> [1, 1] (3*3=9 points)
     * ----> [] (2*2=4 points)
     *
     * Constraints:
     *
     * 1 <= boxes.length <= 100
     * 1 <= boxes[i] <= 100
     * @param boxes
     * @return
     */
    private int[][][] dp;
    public int removeBoxes(int[] boxes) {
        // corner case
        if (boxes == null || boxes.length == 0) return 0;

        dp = new int[100][100][100];
        return dfs(boxes, 0, boxes.length - 1, 0);
    }

    private int dfs(int[] boxes, int l, int r, int k) {
        // base case
        if (l > r) return 0;

        if (dp[l][r][k] != 0) return dp[l][r][k];

        int i = r;
        int count = k;
        while (i >= l && boxes[i] == boxes[r]) {
            i--;
            count++;
        }
        dp[l][r][k] = dfs(boxes, l, i, 0) + count * count;

        for (int j = i; j >= l; j--) {
            if (boxes[j] != boxes[r]) continue;
            if (boxes[j] == boxes[r] && boxes[j + 1] == boxes[r]) continue;
            dp[l][r][k] = Math.max(dp[l][r][k], dfs(boxes, l, j, count) + dfs(boxes, j + 1, i, 0));
        }
        return dp[l][r][k];
    }
}
/**
 * 大区间化小区间，burst balloon, merge stone
 * 区间型dp：区间型dp的目标是将大区间化成小区间，一个常见的突破口就是从最后一个元素入手，看它能够怎么处理掉。
 * dp[l][r]
 * 000 xxx  000  xxx 000   看最后一位
 *
 * ... OOO  XXX OOO XXX OOO
 * ...   ^    ^   ^   ^   ^
 * ...   j1  i1  j0  i0   r
 * 1. r itself
 * dp[l][i0][0] + count * count
 *
 * 2. r + j0
 * dp[l][i1] + (count0 + count)^2 + dp[j0+1][i0] -> 可能接下来并不是最优
 * j0这组可能与j1的000拼接起来可能更优
 * dp[l][j0][count] + dp[j0+1][i0][0]
 *
 * 3. r + j1
 * dp[l][j1][count] + [j1 + 1][i0][0]
 *
 * 4. ...
 *
 * XX O  XX   O ** O
 *    ^   ^   ^    ^
 *    j1  i0  j0   r
 *
 * 1 + 16 + 4 = 21
 *
 * XX O  XX   O ** OOO
 *    ^   ^   ^      ^
 *    j1  i0  j0     r
 * 4 + 25 + 4 = 32
 * dp[l][r][k]表示，对于区间[l,r]后面还跟着k个与boxes[r]相同的元素。
 * 注意这k个元素并不与r直接相连，而是经过其他之前的区间消除后剩下来的
 * dp[l][r][k] 受后面跟了多少个元素的影响
 * k 事先并不知道
 * 从上往下
 */
