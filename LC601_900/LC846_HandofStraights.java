package LC601_900;
import java.util.*;
public class LC846_HandofStraights {
    /**
     * Alice has some number of cards and she wants to rearrange the cards into groups so that each group is of size
     * groupSize, and consists of groupSize consecutive cards.
     *
     * Given an integer array hand where hand[i] is the value written on the ith card and an integer groupSize, return
     * true if she can rearrange the cards, or false otherwise.
     *
     * Input: hand = [1,2,3,6,2,3,4,7,8], groupSize = 3
     * Output: true
     *
     * Constraints:
     *
     * 1 <= hand.length <= 10^4
     * 0 <= hand[i] <= 10^9
     * 1 <= groupSize <= hand.length
     * @param hand
     * @param groupSize
     * @return
     */
    // time = O(nlogn), space = O(n)
    public boolean isNStraightHand(int[] hand, int groupSize) {
        int n = hand.length, k = groupSize;
        if (n % k != 0) return false;

        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int x : hand) map.put(x, map.getOrDefault(x, 0) + 1);

        while (map.size() > 0) {
            int m = map.firstKey();
            for (int i = m; i < m + k; i++) {
                if (map.containsKey(i)) {
                    map.put(i, map.get(i) - 1);
                    if (map.get(i) == 0) map.remove(i);
                } else return false;
            }
        }
        return true;
    }
}
/**
 * same as LC1296
 */
