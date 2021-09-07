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

    // S3: BS
    // time = O(nlogn), space = O(n)
    public int[] topKFrequent3(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : nums) map.put(x, map.getOrDefault(x, 0) + 1);

        int left = 0, right = nums.length; // 猜freq => freq最小是0，最大就是数组长度nums.length
        while (left < right) { // O(logn)
            int mid = right - (right - left) / 2;
            if (countFreqGreaterOrEqual(map, mid) >= k) left = mid;
            else right = mid - 1;
        }

        int f = left, idx = 0;
        int[] res = new int[k];
        for (int key : map.keySet()) {
            if (map.get(key) >= f) res[idx++] = key;
        }
        return res;
    }

    private int countFreqGreaterOrEqual(HashMap<Integer, Integer> map, int f) {
        int count = 0;
        for (int key : map.keySet()) {
            if (map.get(key) >= f) count++;
        }
        return count;
    }

    // S4: Quick Select
    // time = O(n), space = O(n)  worst case O(n^2)
    public int[] topKFrequent4(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : nums) map.put(x, map.getOrDefault(x, 0) + 1);

        List<int[]> arr = new ArrayList<>();
        for (int key : map.keySet()) arr.add(new int[]{key, map.get(key)});

        int f = quickselect(arr, 0, arr.size() - 1, k);
        int idx = 0;
        int[] res = new int[k];
        for (int key : map.keySet()) {
            if (map.get(key) >= f) res[idx++] = key;
        }
        return res;
    }

    private int quickselect(List<int[]> arr, int a, int b, int k) {
        int pivot = arr.get(a + (b - a) / 2)[1];
        int i = a, j = b, t = a;
        while (t <= j) {
            if (arr.get(t)[1] < pivot) swap(arr, t++, i++);
            else if (arr.get(t)[1] > pivot) swap(arr, t, j--);
            else t++;
        }
        if (b - j >= k) return quickselect(arr, j + 1, b, k);
        else if (b - i + 1 >= k) return pivot;
        else return quickselect(arr, a, i -1, k - (b - i + 1));
    }

    private void swap(List<int[]> nums, int i, int j) {
        int[] temp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, temp);
    }
}
