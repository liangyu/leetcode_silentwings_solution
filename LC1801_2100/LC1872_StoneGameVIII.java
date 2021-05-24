package LC1801_2100;
import java.util.*;
public class LC1872_StoneGameVIII {
    /**
     * Alice and Bob take turns playing a game, with Alice starting first.
     *
     * There are n stones arranged in a row. On each player's turn, while the number of stones is more than one, they
     * will do the following:
     *
     * Choose an integer x > 1, and remove the leftmost x stones from the row.
     * Add the sum of the removed stones' values to the player's score.
     * Place a new stone, whose value is equal to that sum, on the left side of the row.
     * The game stops when only one stone is left in the row.
     *
     * The score difference between Alice and Bob is (Alice's score - Bob's score). Alice's goal is to maximize the
     * score difference, and Bob's goal is the minimize the score difference.
     *
     * Given an integer array stones of length n where stones[i] represents the value of the ith stone from the left,
     * return the score difference between Alice and Bob if they both play optimally.
     *
     * Input: stones = [-1,2,-3,4,-5]
     * Output: 5
     *
     * Constraints:
     *
     * n == stones.length
     * 2 <= n <= 10^5
     * -10^4 <= stones[i] <= 10^4
     * @param stones
     * @return
     */
    public int stoneGameVIII(int[] stones) {

    }
}
