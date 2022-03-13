package LC301_600;
import java.util.*;
public class LC460_LFUCache {
    /**
     * Design and implement a data structure for a Least Frequently Used (LFU) cache.
     *
     * Implement the LFUCache class:
     *
     * LFUCache(int capacity) Initializes the object with the capacity of the data structure.
     * int get(int key) Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
     * void put(int key, int value) Update the value of the key if present, or inserts the key if not already present.
     * When the cache reaches its capacity, it should invalidate and remove the least frequently used key before
     * inserting a new item. For this problem, when there is a tie (i.e., two or more keys with the same frequency),
     * the least recently used key would be invalidated.
     * To determine the least frequently used key, a use counter is maintained for each key in the cache. The key with
     * the smallest use counter is the least frequently used key.
     *
     * When a key is first inserted into the cache, its use counter is set to 1 (due to the put operation). The use
     * counter for a key in the cache is incremented either a get or put operation is called on it.
     *
     * The functions get and put must each run in O(1) average time complexity.
     *
     * Input
     * ["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
     * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
     * Output
     * [null, null, null, 1, null, -1, 3, null, -1, 3, 4]
     *
     * Constraints:
     *
     * 0 <= capacity <= 10^4
     * 0 <= key <= 10^5
     * 0 <= value <= 10^9
     * At most 2 * 10^5 calls will be made to get and put.
     * @param capacity
     */
    // time = O(1), space = O(n)  n: capacity
    HashMap<Integer, Integer> map;
    HashMap<Integer, Integer> count;
    HashMap<Integer, LinkedHashSet<Integer>> lists;
    int capacity, minCount;
    public LC460_LFUCache(int capacity) {
        map = new HashMap<>();
        count = new HashMap<>();
        lists = new HashMap<>();
        this.capacity = capacity;
        this.minCount = -1;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;

        int cnt = count.get(key);
        count.put(key, cnt + 1);

        lists.get(cnt).remove(key);

        // update minCount, happen to remove the current minCount
        if (minCount == cnt && lists.get(cnt).size() == 0) minCount++;
        if (!lists.containsKey(cnt + 1)) lists.put(cnt + 1, new LinkedHashSet<>());
        lists.get(cnt + 1).add(key);
        return map.get(key);
    }

    public void put(int key, int value) {
        // corner case
        if (capacity <= 0) return; // be careful about thsi corner case! capacity might be 0!!!

        if (map.containsKey(key)) {
            map.put(key, value);
            get(key);
            return;
        }

        if (map.size() >= capacity) {
            int evictKey = lists.get(minCount).iterator().next();
            lists.get(minCount).remove(evictKey);
            map.remove(evictKey);
        }

        map.put(key, value);
        count.put(key, 1);
        minCount = 1;
        lists.putIfAbsent(1, new LinkedHashSet<>());
        lists.get(1).add(key);
    }
}
