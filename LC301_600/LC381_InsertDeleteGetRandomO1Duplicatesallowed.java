package LC301_600;
import java.util.*;
public class LC381_InsertDeleteGetRandomO1Duplicatesallowed {
    /**
     * Implement the RandomizedCollection class:
     *
     * RandomizedCollection() Initializes the RandomizedCollection object.
     * bool insert(int val) Inserts an item val into the multiset if not present. Returns true if the item was not
     * present, false otherwise.
     * bool remove(int val) Removes an item val from the multiset if present. Returns true if the item was present,
     * false otherwise. Note that if val has multiple occurrences in the multiset, we only remove one of them.
     * int getRandom() Returns a random element from the current multiset of elements (it's guaranteed that at least one
     * element exists when this method is called). The probability of each element being returned is linearly related
     * to the number of same values the multiset contains.
     *
     * Input
     * ["RandomizedCollection", "insert", "insert", "insert", "getRandom", "remove", "getRandom"]
     * [[], [1], [1], [2], [], [1], []]
     * Output
     * [null, true, false, true, 2, true, 1]
     */
    /** Initialize your data structure here. */
    // time = O(n), space = O(n)
    private List<Integer> list;
    private HashMap<Integer, HashSet<Integer>> map;
    public LC381_InsertDeleteGetRandomO1Duplicatesallowed() {
        list = new ArrayList<>();
        map = new HashMap<>();
    }

    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            list.add(val);
            map.get(val).add(list.size() - 1);
            return false;
        }
        list.add(val);
        map.put(val, new HashSet<>());
        map.get(val).add(list.size() - 1);
        return true;
    }

    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        if (!map.containsKey(val)) return false;

        int idx = map.get(val).iterator().next();
        map.get(val).remove(idx);
        if (map.get(val).size() == 0) map.remove(val);

        int lastVal = list.remove(list.size() - 1);
        if (idx != list.size()) {
            list.set(idx, lastVal);
            map.get(lastVal).remove(list.size());
            map.get(lastVal).add(idx);
        }
        return true;
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        Random random = new Random();
        int idx = random.nextInt(list.size());
        return list.get(idx);
    }
}
