package LC1201_1500;
import java.util.*;
public class LC1338_ReduceArraySizetoTheHalf {
    /**
     * Given an array arr.  You can choose a set of integers and remove all the occurrences of these integers in the array.
     *
     * Return the minimum size of the set so that at least half of the integers of the array are removed.
     *
     * Input: arr = [3,3,3,3,5,5,5,2,2,7]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^5
     * arr.length is even.
     * 1 <= arr[i] <= 10^5
     * @param arr
     * @return
     */
    // S1: hash + pq
    // time = O(nlogn), space = O(n)
    public int minSetSize(int[] arr) {
        // corner case
        if (arr == null || arr.length == 0) return 0;

        int n = arr.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) map.put(num, map.getOrDefault(num, 0) + 1);

        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int val : map.values()) pq.offer(val);

        int count = 0, res = 0;
        while (!pq.isEmpty()) {
            count += pq.poll();
            res++;
            if (count >= n / 2) break;
        }
        return res;
    }

    // S2: hash + bucket sort
    // time = O(n), space = O(n)
    public int minSetSize2(int[] arr) {
        // corner case
        if (arr == null || arr.length == 0) return 0;

        int n = arr.length, max = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.get(num) >= n / 2) return 1;
        }

        List<Integer>[] list = new List[n + 1];
        for (int key : map.keySet()) {
            int count = map.get(key);
            if (list[count] == null) list[count] = new ArrayList<>();
            list[count].add(key);
        }

        int count = 0, res = 0;
        for (int i = n; i > 0; i--) {
            List<Integer> cur = list[i];
            if (cur == null || cur.size() == 0) continue;
            for (int num : cur) {
                count += i;
                res++;
                if (count >= n / 2) return res;
            }
        }
        return n;
    }
}
