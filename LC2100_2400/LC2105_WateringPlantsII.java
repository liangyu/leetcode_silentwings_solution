package LC2100_2400;

public class LC2105_WateringPlantsII {
    /**
     * Alice and Bob want to water n plants in their garden. The plants are arranged in a row and are labeled from 0 to
     * n - 1 from left to right where the ith plant is located at x = i.
     *
     * Each plant needs a specific amount of water. Alice and Bob have a watering can each, initially full. They water
     * the plants in the following way:
     *
     * Alice waters the plants in order from left to right, starting from the 0th plant. Bob waters the plants in order
     * from right to left, starting from the (n - 1)th plant. They begin watering the plants simultaneously.
     * If one does not have enough water to completely water the current plant, he/she refills the watering can
     * instantaneously.
     * It takes the same amount of time to water each plant regardless of how much water it needs.
     * One cannot refill the watering can early.
     * Each plant can be watered either by Alice or by Bob.
     * In case both Alice and Bob reach the same plant, the one with more water currently in his/her watering can should
     * water this plant. If they have the same amount of water, then Alice should water this plant.
     * Given a 0-indexed integer array plants of n integers, where plants[i] is the amount of water the ith plant needs,
     * and two integers capacityA and capacityB representing the capacities of Alice's and Bob's watering cans
     * respectively, return the number of times they have to refill to water all the plants.
     *
     * Input: plants = [2,2,3,3], capacityA = 5, capacityB = 5
     * Output: 1
     *
     * Input: plants = [1,2,4,4,5], capacityA = 6, capacityB = 5
     * Output: 2
     *
     * Constraints:
     *
     * n == plants.length
     * 1 <= n <= 10^5
     * 1 <= plants[i] <= 10^6
     * max(plants[i]) <= capacityA, capacityB <= 10^9
     * @param plants
     * @param capacityA
     * @param capacityB
     * @return
     */
    // time = O(n), space = O(1)
    public int minimumRefill(int[] plants, int capacityA, int capacityB) {
        int i = 0, j = plants.length - 1;
        int ca = capacityA, cb = capacityB;
        int count = 0;

        while (i < j) {
            if (ca < plants[i]) {
                ca = capacityA;
                count++;
            }
            ca -= plants[i];
            i++;

            if (cb < plants[j]) {
                cb = capacityB;
                count++;
            }
            cb -= plants[j];
            j--;
        }

        if (i > j) return count;
        if (ca >= cb) {
            if (ca < plants[i]) count++;
        } else {
            if (cb < plants[j]) count++;
        }
        return count;
    }
}
