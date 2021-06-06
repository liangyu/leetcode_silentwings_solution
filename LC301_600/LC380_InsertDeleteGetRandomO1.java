package LC301_600;
import java.util.*;
public class LC380_InsertDeleteGetRandomO1 {
    /**
     * Implement the RandomizedSet class:
     *
     * RandomizedSet() Initializes the RandomizedSet object.
     * bool insert(int val) Inserts an item val into the set if not present. Returns true if the item was not present,
     * false otherwise.
     * bool remove(int val) Removes an item val from the set if present. Returns true if the item was present, false
     * otherwise.
     * int getRandom() Returns a random element from the current set of elements (it's guaranteed that at least one
     * element exists when this method is called). Each element must have the same probability of being returned.
     *
     * Input
     * ["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom"]
     * [[], [1], [2], [2], [], [1], [2], []]
     * Output
     * [null, true, false, true, 2, true, false, 2]
     *
     * Constraints:
     *
     * -2^31 <= val <= 2^31 - 1
     * At most 10^5 calls will be made to insert, remove, and getRandom.
     * There will be at least one element in the data structure when getRandom is called.
     *
     *
     * Follow up: Could you implement the functions of the class with each function works in average O(1) time?
     */
    /** Initialize your data structure here. */
    // time = O(1), space = O(n)
    private List<Integer> list;
    private HashMap<Integer, Integer> map;
    public LC380_InsertDeleteGetRandomO1() {
        list = new ArrayList<>();
        map = new HashMap<>();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val)) return false;
        list.add(val);
        map.put(val, list.size() - 1);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (!map.containsKey(val)) return false;

        int idx = map.get(val);
        int lastVal = list.get(list.size() - 1);
        // swap
        list.set(idx, lastVal);
        map.put(lastVal, idx);
        list.remove(list.size() - 1);
        map.remove(val);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        Random random = new Random();
        int idx = random.nextInt(list.size());
        return list.get(idx);
    }
}
