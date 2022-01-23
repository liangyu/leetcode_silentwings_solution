package LC2100_2400;

public class LC2139_MinimumMovestoReachTargetScore {
    /**
     * You are playing a game with integers. You start with the integer 1 and you want to reach the integer target.
     *
     * In one move, you can either:
     *
     * Increment the current integer by one (i.e., x = x + 1).
     * Double the current integer (i.e., x = 2 * x).
     * You can use the increment operation any number of times, however, you can only use the double operation at most
     * maxDoubles times.
     *
     * Given the two integers target and maxDoubles, return the minimum number of moves needed to reach target starting
     * with 1.
     *
     * Input: target = 5, maxDoubles = 0
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= target <= 10^9
     * 0 <= maxDoubles <= 100
     * @param target
     * @param maxDoubles
     * @return
     */
    // time = O(1), space = O(1)
    public int minMoves(int target, int maxDoubles) {
        int res = 0;
        while (target > 1) {
            if (target % 2 == 1) {
                target--;
                res++;
            } else {
                if (maxDoubles > 0) {
                    target /= 2;
                    res++;
                    maxDoubles--;
                } else return res + target - 1;
            }
        }
        return res;
    }
}
