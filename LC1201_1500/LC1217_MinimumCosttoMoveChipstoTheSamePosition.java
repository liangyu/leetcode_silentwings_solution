package LC1201_1500;

public class LC1217_MinimumCosttoMoveChipstoTheSamePosition {
    /**
     * We have n chips, where the position of the ith chip is position[i].
     *
     * We need to move all the chips to the same position. In one step, we can change the position of the ith chip
     * from position[i] to:
     *
     * position[i] + 2 or position[i] - 2 with cost = 0.
     * position[i] + 1 or position[i] - 1 with cost = 1.
     * Return the minimum cost needed to move all the chips to the same position.
     *
     * Input: position = [1,2,3]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= position.length <= 100
     * 1 <= position[i] <= 10^9
     * @param position
     * @return
     */
    // time = O(n), space = O(1)
    public int minCostToMoveChips(int[] position) {
        int n = position.length, odd = 0, even = 0;
        for (int i = 0; i < n; i++) {
            if (position[i] % 2 == 0) even++;
            else odd++;
        }
        return Math.min(odd, even);
    }
}
/**
 * We can consolidate all odd and all even chips together at no cost.
 * So, we will have two piles in the end, and we move chips from the smaller pile to the larger one.
 */