package LC001_300;
import java.util.*;
public class LC170_TwoSumIIIDatastructuredesign {
    /**
     * Design a data structure that accepts a stream of integers and checks if it has a pair of integers that sum up to
     * a particular value.
     *
     * Implement the TwoSum class:
     *
     * TwoSum() Initializes the TwoSum object, with an empty array initially.
     * void add(int number) Adds number to the data structure.
     * boolean find(int value) Returns true if there exists any pair of numbers whose sum is equal to value, otherwise,
     * it returns false.
     *
     * Input
     * ["TwoSum", "add", "add", "add", "find", "find"]
     * [[], [1], [3], [5], [4], [7]]
     * Output
     * [null, null, null, null, true, false]
     *
     * Constraints:
     *
     * -10^5 <= number <= 10^5
     * -2^31 <= value <= 2^31 - 1
     * At most 5 * 10^4 calls will be made to add and find.
     */
    /** Initialize your data structure here. */
    // time = O(n), space = O(n)
    private HashMap<Integer, Integer> map;
    public LC170_TwoSumIIIDatastructuredesign() {
        map = new HashMap<>();
    }

    /** Add the number to an internal data structure.. */
    public void add(int number) {
        map.put(number, map.getOrDefault(number, 0) + 1);
    }

    /** Find if there exists any pair of numbers which sum is equal to the value. */
    public boolean find(int value) {
        for (int key : map.keySet()) {
            if (map.containsKey(value - key)) {
                if (value - key != key) return true;
                else if (map.get(key) > 1) return true;
            }
        }
        return false;
    }
}
