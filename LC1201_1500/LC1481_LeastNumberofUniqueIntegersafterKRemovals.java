package LC1201_1500;
import java.util.*;
public class LC1481_LeastNumberofUniqueIntegersafterKRemovals {
    /**
     * Given an array of integers arr and an integer k. Find the least number of unique integers after removing exactly
     * k elements.
     *
     * Input: arr = [5,5,4], k = 1
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^5
     * 1 <= arr[i] <= 10^9
     * @param arr
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : arr) map.put(x, map.getOrDefault(x, 0) + 1);
        int total = map.size();

        TreeMap<Integer, Integer> freq = new TreeMap<>();
        for (int key : map.keySet()) {
            int f = map.get(key);
            freq.put(f, freq.getOrDefault(f, 0) + 1);
        }

        while (k > 0) {
            int fk = freq.firstKey();
            if (k > fk) {
                k -= fk;
                freq.put(fk, freq.get(fk) - 1);
                if (freq.get(fk) == 0) freq.remove(fk);
                total--;
            } else if (k == fk) return total - 1;
            else return total;
        }
        return total;
    }
}
