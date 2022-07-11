package LC2101_2400;
import java.util.*;
public class LC2336_SmallestNumberinInfiniteSet {
    /**
     * You have a set which contains all positive integers [1, 2, 3, 4, 5, ...].
     *
     * Implement the SmallestInfiniteSet class:
     *
     * SmallestInfiniteSet() Initializes the SmallestInfiniteSet object to contain all positive integers.
     * int popSmallest() Removes and returns the smallest integer contained in the infinite set.
     * void addBack(int num) Adds a positive integer num back into the infinite set, if it is not already in the
     * infinite set.
     *
     * Input
     * ["SmallestInfiniteSet", "addBack", "popSmallest", "popSmallest", "popSmallest", "addBack", "popSmallest",
     * "popSmallest", "popSmallest"]
     * [[], [2], [], [], [], [1], [], [], []]
     * Output
     * [null, null, 1, 2, 3, null, 1, 4, 5]
     *
     * Constraints:
     *
     * 1 <= num <= 1000
     * At most 1000 calls will be made in total to popSmallest and addBack.
     */
    // time = O(nlogn), space = O(n)
    TreeSet<Integer> set;
    public LC2336_SmallestNumberinInfiniteSet() {
        set = new TreeSet<>();
        for (int i = 1; i <= 1000; i++) set.add(i);
    }

    public int popSmallest() {
        int res = set.first();
        set.remove(res);
        return res;
    }

    public void addBack(int num) {
        if (set.contains(num)) return;
        set.add(num);
    }
}
