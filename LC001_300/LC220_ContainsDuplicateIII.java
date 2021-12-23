package LC001_300;
import java.util.*;
public class LC220_ContainsDuplicateIII {
    /**
     * Given an integer array nums and two integers k and t, return true if there are two distinct indices i and j in
     * the array such that abs(nums[i] - nums[j]) <= t and abs(i - j) <= k.
     *
     * Input: nums = [1,2,3,1], k = 3, t = 0
     * Output: true
     *
     * Constraints:
     *
     * 0 <= nums.length <= 2 * 10^4
     * -2^31 <= nums[i] <= 2^31 - 1
     * 0 <= k <= 10^4
     * 0 <= t <= 2^31 - 1
     * @param nums
     * @param k
     * @param t
     * @return
     */
    // S1: TreeMap
    // time = O(nlogk), space = O(k)
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        // corner case
        if (nums == null || nums.length == 0 || k < 1 || t < 0) return false;

        TreeMap<Long, List<Integer>> map = new TreeMap<>();

        for (int i = 0; i < nums.length; i++) { // O(n)
            if (i > k) {
                List<Integer> list = map.get((long)nums[i - k - 1]);
                list.remove(0);
                if (list.size() == 0) map.remove((long)nums[i - k - 1]);
            }

            long lowerBound = (long)(nums[i] - t);
            long upperbound = (long)(nums[i] + t);

            Long val = map.floorKey(upperbound);
            if (val != null && val >= lowerBound) return true;

            map.putIfAbsent((long)nums[i], new LinkedList<>());
            map.get((long)nums[i]).add(i);
        }
        return false;
    }

    // S2: TreeSet
    // time = O(nlogk), space = O(k)
    public boolean containsNearbyAlmostDuplicate2(int[] nums, int k, int t) {
        // corner case
        if (nums == null || nums.length == 0 || k < 1 || t < 0) return false;

        TreeSet<Long> set = new TreeSet<>();
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            Long ck = set.ceiling((long) nums[i] - t);
            Long fk = set.floor((long) nums[i] + t);
            if (ck != null && ck <= nums[i] + t || fk != null && fk >= nums[i] - t) {
                return true;
            }
            set.add((long) nums[i]);
            if (set.size() > k) set.remove((long) nums[i - k]);
        }
        return false;
    }

    // S3: bucket sort (最优解!!!)
    // time = O(n), space = O(k)
    public boolean containsNearbyAlmostDuplicate3(int[] nums, int k, int t) {
        // corner case
        if (nums == null || nums.length == 0 || k < 1 || t < 0) return false;

        HashMap<Long, Long> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) { // O(n)
            long remap = (long)nums[i] - Integer.MIN_VALUE;
            long bucket = remap / ((long)t + 1); // 防止t = 0时没法被除

            if (map.containsKey(bucket) || map.containsKey(bucket - 1) && remap - map.get(bucket - 1) <= t || map.containsKey(bucket + 1) && map.get(bucket + 1) - remap <= t) {
                return true;
            }

            map.put(bucket, remap);
            if (map.size() > k) {
                long lastBucket = ((long)nums[i - k] - Integer.MIN_VALUE) / ((long)t + 1);
                map.remove(lastBucket);
            }
        }
        return false;
    }
}
/**
 * abs(nums[i] - nums[j]) <= t
 * -t < nums[i] - nums[j] < t =>
 * nums[j] - t < nums[i] < nums[j] + t => 二分搜索一个i
 * 如果这个窗口是有序的话，用二分法可以做
 * [j-k+1,j-1] 找到一个i，如果这些元素能自动排序就最好了 -> PriorityQueue / TreeSet  PQ只能保证出口的位置，剩下位置不是顺序的
 * => TreeSet
 */