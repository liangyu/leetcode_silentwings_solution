package LC2100_2400;
import com.sun.source.tree.Tree;

import java.sql.Array;
import java.util.*;
public class LC2102_SequentiallyOrdinalRankTracker {
    /**
     * A scenic location is represented by its name and attractiveness score, where name is a unique string among all
     * locations and score is an integer. Locations can be ranked from the best to the worst. The higher the score, the
     * better the location. If the scores of two locations are equal, then the location with the lexicographically
     * smaller name is better.
     *
     * You are building a system that tracks the ranking of locations with the system initially starting with no
     * locations. It supports:
     *
     * Adding scenic locations, one at a time.
     * Querying the ith best location of all locations already added, where i is the number of times the system has been
     * queried (including the current query).
     * For example, when the system is queried for the 4th time, it returns the 4th best location of all locations
     * already added.
     * Note that the test data are generated so that at any time, the number of queries does not exceed the number of
     * locations added to the system.
     *
     * Implement the SORTracker class:
     *
     * SORTracker() Initializes the tracker system.
     * void add(string name, int score) Adds a scenic location with name and score to the system.
     * string get() Queries and returns the ith best location, where i is the number of times this method has been
     * invoked (including this invocation).
     *
     * Input
     * ["SORTracker", "add", "add", "get", "add", "get", "add", "get", "add", "get", "add", "get", "get"]
     * [[], ["bradford", 2], ["branford", 3], [], ["alps", 2], [], ["orland", 2], [], ["orlando", 3], [], ["alpine", 2],
     * [], []]
     * Output
     * [null, null, null, "branford", null, "alps", null, "bradford", null, "bradford", null, "bradford", "orland"]
     *
     * Constraints:
     *
     * name consists of lowercase English letters, and is unique among all locations.
     * 1 <= name.length <= 10
     * 1 <= score <= 10^5
     * At any time, the number of calls to get does not exceed the number of calls to add.
     * At most 4 * 104 calls in total will be made to add and get.
     */
    TreeMap<Integer, List<String>> map;
    int count = 0;
    public LC2102_SequentiallyOrdinalRankTracker() {
        map = new TreeMap<>((o1, o2) -> o2 - o1);
    }

    // time = O(logn), space = O(n)
    public void add(String name, int score) {
        map.putIfAbsent(score, new ArrayList<>());
        List<String> list = map.get(score);
        if (list.size() == 0) list.add(name);
        else {
            int idx = upperBound(list, name); // O(logn)
            list.add(idx, name);
        }
        map.put(score, list);
    }

    // time = O(n), space = O(1)
    public String get() {
        int temp = count;
        String res = "";
        for (int x : map.keySet()) {
            if (count < map.get(x).size()) {
                res = map.get(x).get(count);
                break;
            } else count -= map.get(x).size();
        }
        count = temp + 1;
        return res;
    }

    private int upperBound(List<String> list, String t) {
        int left = 0, right = list.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid).compareTo(t) < 0) left = mid + 1;
            else right = mid;
        }
        return list.get(left).compareTo(t) >= 0 ? left : left + 1;
    }
}
