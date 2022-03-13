package LC2101_2400;

public class LC2162_MinimumCosttoSetCookingTime {
    /**
     * A generic microwave supports cooking times for:
     *
     * at least 1 second.
     * at most 99 minutes and 99 seconds.
     * To set the cooking time, you push at most four digits. The microwave normalizes what you push as four digits by
     * prepending zeroes. It interprets the first two digits as the minutes and the last two digits as the seconds. It
     * then adds them up as the cooking time. For example,
     *
     * You push 9 5 4 (three digits). It is normalized as 0954 and interpreted as 9 minutes and 54 seconds.
     * You push 0 0 0 8 (four digits). It is interpreted as 0 minutes and 8 seconds.
     * You push 8 0 9 0. It is interpreted as 80 minutes and 90 seconds.
     * You push 8 1 3 0. It is interpreted as 81 minutes and 30 seconds.
     * You are given integers startAt, moveCost, pushCost, and targetSeconds. Initially, your finger is on the digit
     * startAt. Moving the finger above any specific digit costs moveCost units of fatigue. Pushing the digit below the
     * finger once costs pushCost units of fatigue.
     *
     * There can be multiple ways to set the microwave to cook for targetSeconds seconds but you are interested in the
     * way with the minimum cost.
     *
     * Return the minimum cost to set targetSeconds seconds of cooking time.
     *
     * Remember that one minute consists of 60 seconds.
     *
     * Input: startAt = 1, moveCost = 2, pushCost = 1, targetSeconds = 600
     * Output: 6
     *
     * Input: startAt = 0, moveCost = 1, pushCost = 2, targetSeconds = 76
     * Output: 6
     *
     * Constraints:
     *
     * 0 <= startAt <= 9
     * 1 <= moveCost, pushCost <= 10^5
     * 1 <= targetSeconds <= 6039
     * @param startAt
     * @param moveCost
     * @param pushCost
     * @param targetSeconds
     * @return
     */
    // time = O(1), space = O(1)
    public int minCostSetTime(int startAt, int moveCost, int pushCost, int targetSeconds) {
        int minutes = Math.min(99, targetSeconds / 60);
        int res = Integer.MAX_VALUE;
        for (int i = minutes; i >= 0; i--) {
            int[] nums = new int[4];
            int j = targetSeconds - i * 60;
            if (j >= 100) break;
            nums[0] = i / 10;
            nums[1] = i % 10;
            nums[2] = j / 10;
            nums[3] = j % 10;
            int cost = helper(nums, startAt, moveCost, pushCost);
            res = Math.min(res, cost);
        }
        return res;
    }

    private int helper(int[] nums, int cur, int moveCost, int pushCost) {
        int cost = 0;
        for (int i = 0; i < 4; i++) {
            if (nums[i] == 0 && cost == 0) continue;
            cost += (nums[i] == cur ? 0 : moveCost) + pushCost;
            cur = nums[i];
        }
        return cost;
    }
}
