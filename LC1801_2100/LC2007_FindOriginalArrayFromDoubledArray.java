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
    // S1: TreeMap
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

    // S2: TreeMap
    // time = O(nlogk), space = O(k)
    public int[] findOriginalArray2(int[] changed) {
        int n = changed.length;
        if (n % 2 == 1) return new int[0];

        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int x : changed) map.put(x, map.getOrDefault(x, 0) + 1);

        int[] res = new int[n / 2];
        for (int i = 0; i < n / 2; i++) {
            Integer lk = map.lastKey();
            if (lk % 2 == 1) return new int[0];
            if (!map.containsKey(lk / 2)) return new int[0];
            map.put(lk, map.get(lk) - 1);
            map.put(lk / 2, map.get(lk / 2) - 1);
            if (map.get(lk) == 0) map.remove(lk);
            if (map.containsKey(lk / 2) && map.get(lk / 2) == 0) map.remove(lk / 2); // 注意：lk可能与lk/2相等，比如0！！！
            res[i] = lk / 2;
        }
        return res;
    }

    // S3: Two Pointers
    public int[] findOriginalArray3(int[] changed) {
        int n = changed.length;
        if (n % 2 == 1) return new int[0];

        Arrays.sort(changed);
        int[] res = new int[n / 2];
        int left = 0, right = 0;
        boolean[] used = new boolean[n];

        for (int i = 0; i < n / 2; i++) {
            while (left < n && used[left]) left++;
            if (left == n) return new int[0];
            res[i] = changed[left];
            used[left] = true;
            while (right < n && (used[right] || changed[right] != changed[left] * 2)) right++;
            if (right == n) return new int[0];
            used[right] = true;
        }
        return res;
    }
}
/**
 * constructive problem
 * original [1,3,4]
 * changed [1,3,2,6,8]  -> original里不可能有8
 * 同理也不可能有6
 * 在change里面实时找到最大值 -> multiSet
 */