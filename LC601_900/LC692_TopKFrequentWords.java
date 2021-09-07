package LC601_900;
import java.util.*;
public class LC692_TopKFrequentWords {
    /**
     * Given a non-empty list of words, return the k most frequent elements.
     *
     * Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency, then the
     * word with the lower alphabetical order comes first.
     *
     * Try to solve it in O(n log k) time and O(n) extra space.
     *
     * @param words
     * @param k
     * @return
     */
    // S1: PQ
    // time = O(nlogk), space = O(n)
    public List<String> topKFrequent(String[] words, int k) {
        List<String> res = new LinkedList<>();
        // corner case
        if (words == null || words.length == 0 || k <= 0) return new ArrayList<>();

        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        PriorityQueue<String> minHeap = new PriorityQueue<>((o1, o2) -> map.get(o1) != map.get(o2) ? map.get(o1) -
                map.get(o2) : o2.compareTo(o1)); // O(klogk)
        for (String key : map.keySet()) { // O(n)
            minHeap.offer(key);
            if (minHeap.size() > k) minHeap.poll(); // log(k)  一旦超过k个就往外poll()掉排序最小的，保证当前前k个有效！
        }

        while (!minHeap.isEmpty()) res.add(0, minHeap.poll()); // 用LinkedList方便在头部O(1)添加
        return res;
    }

    // S2: bucket sort
    // time = O(nlogk), space = O(n)
    public List<String> topKFrequent2(String[] words, int k) {
        List<String> res = new ArrayList<>();
        // corner case
        if (words == null || words.length == 0 || k <= 0) return res;

        int n = words.length;
        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        List<String>[] bucket = new List[n + 1];
        for (String key : map.keySet()) {
            int freq = map.get(key);
            if (bucket[freq] == null) bucket[freq] = new ArrayList<>();
            bucket[freq].add(key);
        }

        for (int i = n - 1; i >= 0; i--) {
            if (bucket[i] != null) {
                Collections.sort(bucket[i]);
                for (String next : bucket[i]) {
                    if (res.size() == k) return res;
                    res.add(next);
                }
            }
        }
        return res;
    }
}
/**
 * Top K 问题基本就是HashMap + PriorityQueue来解决，通过改写comparator(lambda表达式)来实现前K个元素的提取，其中的关键在于前K个与第K
 * 个直接的区别，而heap则要保持住K size，方法是一旦超过K则开始向外poll()即可！！！
 */
