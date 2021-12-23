package LC1501_1800;

public class LC1518_WaterBottles {
    /**
     * Given numBottles full water bottles, you can exchange numExchange empty water bottles for one full water bottle.
     *
     * The operation of drinking a full water bottle turns it into an empty bottle.
     *
     * Return the maximum number of water bottles you can drink.
     *
     * Input: numBottles = 9, numExchange = 3
     * Output: 13
     *
     * Constraints:
     *
     * 1 <= numBottles <= 100
     * 2 <= numExchange <= 100
     * @param numBottles
     * @param numExchange
     * @return
     */
    // time = O(1), space = O(1)
    public int numWaterBottles(int numBottles, int numExchange) {
        int count = 0, remain = 0;
        while (numBottles > 0) {
            count += numBottles;
            numBottles += remain;
            remain = numBottles % numExchange;
            numBottles /= numExchange;
        }
        return count;
    }
}
