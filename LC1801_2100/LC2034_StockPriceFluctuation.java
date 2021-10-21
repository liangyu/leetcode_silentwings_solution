package LC1801_2100;
import java.util.*;
public class LC2034_StockPriceFluctuation {
    /**
     * You are given a stream of records about a particular stock. Each record contains a timestamp and the
     * corresponding price of the stock at that timestamp.
     *
     * Unfortunately due to the volatile nature of the stock market, the records do not come in order. Even worse, some
     * records may be incorrect. Another record with the same timestamp may appear later in the stream correcting the
     * price of the previous wrong record.
     *
     * Design an algorithm that:
     *
     * Updates the price of the stock at a particular timestamp, correcting the price from any previous records at the
     * timestamp.
     * Finds the latest price of the stock based on the current records. The latest price is the price at the latest
     * timestamp recorded.
     * Finds the maximum price the stock has been based on the current records.
     * Finds the minimum price the stock has been based on the current records.
     * Implement the StockPrice class:
     *
     * StockPrice() Initializes the object with no price records.
     * void update(int timestamp, int price) Updates the price of the stock at the given timestamp.
     * int current() Returns the latest price of the stock.
     * int maximum() Returns the maximum price of the stock.
     * int minimum() Returns the minimum price of the stock.
     *
     * Input
     * ["StockPrice", "update", "update", "current", "maximum", "update", "maximum", "update", "minimum"]
     * [[], [1, 10], [2, 5], [], [], [1, 3], [], [4, 2], []]
     * Output
     * [null, null, null, 5, 10, null, 5, null, 2]
     *
     * Constraints:
     *
     * 1 <= timestamp, price <= 10^9
     * At most 10^5 calls will be made in total to update, current, maximum, and minimum.
     * current, maximum, and minimum will be called only after update has been called at least once.
     */
    // time = O(logn), space = O(n)
    TreeMap<Integer, Integer> timeToPrice = new TreeMap<>();
    TreeMap<Integer, Integer> priceToCount = new TreeMap<>();
    public LC2034_StockPriceFluctuation() {
        timeToPrice = new TreeMap<>();
        priceToCount = new TreeMap<>();
    }

    public void update(int timestamp, int price) {
        if (timeToPrice.containsKey(timestamp)) {
            int val = timeToPrice.get(timestamp);
            priceToCount.put(val, priceToCount.get(val)- 1);
            if (priceToCount.get(val) == 0) priceToCount.remove(val);
        }
        timeToPrice.put(timestamp, price);
        priceToCount.put(price, priceToCount.getOrDefault(price, 0) + 1);
    }

    public int current() {
        return timeToPrice.get(timeToPrice.lastKey());
    }

    public int maximum() {
        return priceToCount.lastKey();
    }

    public int minimum() {
        return priceToCount.firstKey();
    }
}
