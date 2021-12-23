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
    // S1: TreeMap + B.S.
//    TreeMap<Integer, List<String>> map;
//    int count = 0;
//    public LC2102_SequentiallyOrdinalRankTracker() {
//        map = new TreeMap<>((o1, o2) -> o2 - o1);
//    }
//
//    // time = O(logn), space = O(n)
//    public void add(String name, int score) {
//        map.putIfAbsent(score, new ArrayList<>());
//        List<String> list = map.get(score);
//        if (list.size() == 0) list.add(name);
//        else {
//            int idx = upperBound(list, name); // O(logn)
//            list.add(idx, name);
//        }
//        map.put(score, list);
//    }
//
//    // time = O(n), space = O(1)
//    public String get() {
//        int temp = count;
//        String res = "";
//        for (int x : map.keySet()) {
//            if (count < map.get(x).size()) {
//                res = map.get(x).get(count);
//                break;
//            } else count -= map.get(x).size();
//        }
//        count = temp + 1;
//        return res;
//    }
//
//    private int upperBound(List<String> list, String t) {
//        int left = 0, right = list.size() - 1;
//        while (left < right) {
//            int mid = left + (right - left) / 2;
//            if (list.get(mid).compareTo(t) < 0) left = mid + 1;
//            else right = mid;
//        }
//        return list.get(left).compareTo(t) >= 0 ? left : left + 1;
//    }

    // S2
    int count = 1;
    PriorityQueue<Pair> minHeap;
    PriorityQueue<Pair> maxHeap;
    public LC2102_SequentiallyOrdinalRankTracker() {
        minHeap = new PriorityQueue<>((o1, o2) -> o1.score != o2.score ? o1.score - o2.score : o2.name.compareTo(o1.name));
        maxHeap = new PriorityQueue<>((o1, o2) -> o1.score != o2.score ? o2.score - o1.score : o1.name.compareTo(o2.name));
    }

    // time = O(logn), space = O(n)
    public void add(String name, int score) {
        Pair p = new Pair(score, name);

        if (minHeap.isEmpty()) maxHeap.offer(p);
        else if (maxHeap.isEmpty()) minHeap.offer(p);
        else {
            Pair top = minHeap.peek();
            if (helper(top, p)) minHeap.offer(p);
            else maxHeap.offer(p);
        }
    }

    // time = O(logn), space = O(n)
    public String get() {
        while (minHeap.size() > count) maxHeap.offer(minHeap.poll());
        while (minHeap.size() < count && maxHeap.size() > 0) minHeap.offer(maxHeap.poll());

        count++;
        Pair p = minHeap.peek();
        return p.name;
    }

    private boolean helper(Pair p1, Pair p2) {
        if (p2.score > p1.score) return true;
        if (p2.score < p1.score) return false;
        if (p2.name.compareTo(p1.name) > 0) return false;
        return true;
    }

    private class Pair {
        private int score;
        private String name;
        public Pair(int score, String name) {
            this.score = score;
            this.name = name;
        }
    }
}
/**
 * 如果要实时排序并同时要随机访问的话，几乎是不可能同时实现的
 * set<int> -> 二叉搜索树
 * int[] q   q[index] => 内存的跳转，按指定顺序排列的 q[0 + 10*4] 在内存上是连续排列的
 * 但二叉搜索树无法保证在内存上是连续的，都不是连续存取的，不支持随机读取，只能支持它的首位置，
 * => for (int i = 0; i < 20; i++) iter = next(iter)  类似链表  prev(iter)
 * pbds
 * 最大tricky的地方在于并不要求真正的随机读取 => 按照一定顺序读取
 * i-th => 4 5 指针往前移动一格
 * add(4)
 * add(3)
 * add(6)
 * get() => next(iter)
 * 求 median of the stream
 *
 * pbds
 */