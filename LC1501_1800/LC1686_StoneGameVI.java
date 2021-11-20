package LC1501_1800;
import java.util.*;
public class LC1686_StoneGameVI {
    /**
     * Alice and Bob take turns playing a game, with Alice starting first.
     *
     * There are n stones in a pile. On each player's turn, they can remove a stone from the pile and receive points
     * based on the stone's value. Alice and Bob may value the stones differently.
     *
     * You are given two integer arrays of length n, aliceValues and bobValues. Each aliceValues[i] and bobValues[i]
     * represents how Alice and Bob, respectively, value the ith stone.
     *
     * The winner is the person with the most points after all the stones are chosen. If both players have the same
     * amount of points, the game results in a draw. Both players will play optimally. Both players know the other's
     * values.
     *
     * Determine the result of the game, and:
     *
     * If Alice wins, return 1.
     * If Bob wins, return -1.
     * If the game results in a draw, return 0.
     *
     * Input: aliceValues = [1,3], bobValues = [2,1]
     * Output: 1
     *
     * Constraints:
     *
     * n == aliceValues.length == bobValues.length
     * 1 <= n <= 10^5
     * 1 <= aliceValues[i], bobValues[i] <= 100
     * @param aliceValues
     * @param bobValues
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        // corner case
        if (aliceValues == null || aliceValues.length == 0 || bobValues == null || bobValues.length == 0) return 0;

        int n = aliceValues.length;
        int[][] temp = new int[n][2];
        for (int i = 0; i < n; i++) {
            temp[i] = new int[]{aliceValues[i] + bobValues[i], i};
        }
        Arrays.sort(temp, (o1, o2) -> o2[0] - o1[0]);

        int x = 0, y = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                x += aliceValues[temp[i][1]];
            } else {
                y += bobValues[temp[i][1]]; // Bob其实和alice是等价的，对应的策略也是可以互换的
            }
        }
        if (x > y) return 1;
        if (x < y) return -1;
        return 0;
    }
}
/**
 * 优先取分数最高的石头？？？
 * A：1  2
 * B: 3  1
 * => 既要兼顾自己得高分，又要兼顾对手不能得到高分 => (A+1)-(B-3) = A+4-B
 * 把一正一反加起来
 * 把所有元素按照value之和排个序
 * 本题的算法很简单，将所有元素按照AliceValues[i]+BobValues[i]的大小排列。然后Alice和Bob轮流选取，对它们而言就是最优的决策。
 * 博弈问题，不但要想自己的收益，还要考虑对方的损失，当彼此value不同时，暴露出博弈问题的本质！
 */
