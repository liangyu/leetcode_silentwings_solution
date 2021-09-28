package LC1801_2100;
import java.util.*;
public class LC2007_FindOriginalArrayFromDoubledArray {
    /**
     * An integer array original is transformed into a doubled array changed by appending twice the value of every
     * element in original, and then randomly shuffling the resulting array.
     *
     * Given an array changed, return original if changed is a doubled array. If changed is not a doubled array, return
     * an empty array. The elements in original may be returned in any order.
     *
     * Input: changed = [1,3,4,2,6,8]
     * Output: [1,3,4]
     *
     * Constraints:
     *
     * 1 <= changed.length <= 10^5
     * 0 <= changed[i] <= 10^5
     * @param changed
     * @return
     */
    // time = O(nlogk), space = O(k)
    public int[] findOriginalArray(int[] changed) {
        // corner case
        if (changed == null || changed.length == 0) return new int[0];

        int n = changed.length, i = 0;
        if (n % 2 != 0) return new int[0];
        int[] res = new int[n / 2];

        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int num : changed) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int key : map.keySet()) {
            if (map.get(key) > map.getOrDefault(key * 2, 0)) {
                return new int[0];
            }
            for (int j = 0; j < map.get(key); j++) {
                res[i++] = key;
                // 注意：不能把这个写到for loop外一起更新，因为map.get(key)可能为0，map不需要更新！
                map.put(key * 2, map.get(key * 2) - 1);
            }
        }
        return res;
    }
}
