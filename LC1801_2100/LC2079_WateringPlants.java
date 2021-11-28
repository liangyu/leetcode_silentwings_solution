package LC1801_2100;

public class LC2079_WateringPlants {
    /**
     * You want to water n plants in your garden with a watering can. The plants are arranged in a row and are labeled
     * from 0 to n - 1 from left to right where the ith plant is located at x = i. There is a river at x = -1 that you
     * can refill your watering can at.
     *
     * Each plant needs a specific amount of water. You will water the plants in the following way:
     *
     * Water the plants in order from left to right.
     * After watering the current plant, if you do not have enough water to completely water the next plant, return to
     * the river to fully refill the watering can.
     * You cannot refill the watering can early.
     * You are initially at the river (i.e., x = -1). It takes one step to move one unit on the x-axis.
     *
     * Given a 0-indexed integer array plants of n integers, where plants[i] is the amount of water the ith plant needs,
     * and an integer capacity representing the watering can capacity, return the number of steps needed to water all
     * the plants.
     *
     * Input: plants = [2,2,3,3], capacity = 5
     * Output: 14
     *
     * Constraints:
     *
     * n == plants.length
     * 1 <= n <= 1000
     * 1 <= plants[i] <= 10^6
     * max(plants[i]) <= capacity <= 10^9
     * @param plants
     * @param capacity
     * @return
     */
    // time = O(n), space = O(1)
    public int wateringPlants(int[] plants, int capacity) {
        int step = 0, cur = capacity, n = plants.length;
        for (int i = 0; i < n; i++) {
            if (cur >= plants[i]) {
                cur -= plants[i];
                step++;
            } else {
                step += i * 2;
                cur = capacity;
                i--;
            }
        }
        return step;
    }
}
