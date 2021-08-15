package LC901_1200;
import com.sun.source.tree.Tree;

import java.util.*;
public class LC954_ArrayofDoubledPairs {
    /**
     * Given an integer array of even length arr, return true if it is possible to reorder arr such that
     * arr[2 * i + 1] = 2 * arr[2 * i] for every 0 <= i < len(arr) / 2, or false otherwise.
     *
     * Input: arr = [3,1,3,6]
     * Output: false
     *
     * Constraints:
     *
     * 2 <= arr.length <= 3 * 10^4
     * arr.length is even.
     * -105 <= arr[i] <= 10^5
     * @param arr
     * @return
     */
    // time = O(nlogn), space = O(n)
    public boolean canReorderDoubled(int[] arr) {
        // corner case
        if (arr == null || arr.length == 0) return false;

        int n = arr.length;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int num : arr) map.put(num, map.getOrDefault(num, 0) + 1);

        for (int key : map.keySet()) {
            if (map.get(key) == 0) continue;
            int val = key < 0 ? key / 2 : key * 2;
            if (key < 0 && key % 2 != 0 || map.get(key) > map.getOrDefault(val, 0)) return false;
            map.put(val, map.get(val) - map.get(key));
        }
        return true;
    }
}
