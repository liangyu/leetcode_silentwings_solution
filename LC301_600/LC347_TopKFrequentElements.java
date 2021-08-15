package LC301_600;
import java.util.*;
public class LC347_TopKFrequentElements {
    /**
     * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in
     * any order.
     *
     * Input: nums = [1,1,1,2,2,3], k = 2
     * Output: [1,2]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * k is in the range [1, the number of unique elements in the array].
     * It is guaranteed that the answer is unique.
     *
     *
     * Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
     * @param nums
     * @param k
     * @return
     */
    // S1: bucket sort
    // time = O(n), space = O(n)
    public int[] topKFrequent(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int n = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        List<Integer>[] bucket = new List[n + 1];
        for (int key : map.keySet()) {
            int freq = map.get(key);
            if (bucket[freq] == null) bucket[freq] = new ArrayList<>();
            bucket[freq].add(key);
        }

        int[] res = new int[k];
        int j = 0;
        for (int i = n; i >= 0; i--) {
            if (bucket[i] != null) {
                for (int num : bucket[i]) {
                    if (j >= k) return res;
                    res[j++] = num;
                }
            }
        }
        return res;
    }

    // S2: PriorityQueue
    // time = O(nlogk), space = O(n)
    public int[] topKFrequent2(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int n = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);

        for (int key : map.keySet()) {
            if (pq.size() == k) {
                if (map.get(key) > pq.peek()[0]) pq.poll();
                else continue;
            }
            pq.offer(new int[]{map.get(key), key});
        }

        int[] res = new int[k];
        for (int i = 0; i < k; i++) res[i] = pq.poll()[1];
        return res;
    }
}
