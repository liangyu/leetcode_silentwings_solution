package LC601_900;
import java.util.*;
public class LC632_SmallestRangeCoveringElementsfromKLists {
    /**
     * You have k lists of sorted integers in non-decreasing order. Find the smallest range that includes at least one
     * number from each of the k lists.
     *
     * We define the range [a, b] is smaller than range [c, d] if b - a < d - c or a < c if b - a == d - c.
     *
     * Input: nums = [[4,10,15,24,26],[0,9,12,20],[5,18,22,30]]
     * Output: [20,24]
     *
     * Constraints:
     *
     * nums.length == k
     * 1 <= k <= 3500
     * 1 <= nums[i].length <= 50
     * -10^5 <= nums[i][j] <= 10^5
     * nums[i] is sorted in non-decreasing order.
     * @param nums
     * @return
     */
    // S1: PQ
    // time = O(nlogn), space = O(n)
    public int[] smallestRange(List<List<Integer>> nums) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        int n = nums.size(), max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            pq.offer(new int[]{nums.get(i).get(0), i});
            max = Math.max(max, nums.get(i).get(0));
        }

        int[] res = new int[2];
        res[0] = pq.peek()[0];
        res[1] = max;

        int[] pointers = new int[n];

        while (true) {
            int a = pq.peek()[0];
            int idx = pq.poll()[1];
            // find next
            pointers[idx]++;
            if (pointers[idx] == nums.get(idx).size()) break;
            int pt = pointers[idx];
            pq.offer(new int[]{nums.get(idx).get(pt), idx});

            min = pq.peek()[0];
            max = Math.max(max, nums.get(idx).get(pt));
            if (max - min < res[1] - res[0]) {
                res[0] = min;
                res[1] = max;
            }
        }
        return res;
    }

    // S2: TreeSet
    // time = O((k + n) * logn), space = O(n)
    public int[] smallestRange2(List<List<Integer>> nums) {
        TreeSet<int[]> set = new TreeSet<>((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]); // {val, groupId}
        int n = nums.size();
        int[] pointers = new int[n];
        for (int i = 0; i < n; i++) set.add(new int[]{nums.get(i).get(0), i}); // O(nlogn)

        int[] res = new int[2];
        int range = Integer.MAX_VALUE;
        while (true) { // O(k)
            if (set.last()[0] - set.first()[0] < range) {
                range = set.last()[0] - set.first()[0];
                res[0] = set.first()[0];
                res[1] = set.last()[0];
            }

            int i = set.first()[1];
            pointers[i]++;
            if (pointers[i] == nums.get(i).size()) break;
            set.remove(set.first());
            set.add(new int[]{nums.get(i).get(pointers[i]), i});
        }
        return res;
    }
}
/**
 * [4 0 5] 0 - 5
 * [4 9 5] 4 - 9
 * [10 9 5] 5 - 10
 * [10 9 18] 9 - 18
 * [a b c] -> 只能选择a
 * 需要一个自动排序的容器
 * S2: TreeSet
 * [4,10,15,24,26]
 * [0,9,12,20]
 * [5,18,22,30]
 * a和b一定是其中的数字，否则一定还有往里面收缩的余地
 * [0,4,5] 0是全局最小的元素 a = 0 -> 挑里面最小的，组成一个range
 * 1. this range covers all nums
 * 2. a is the smallest of all  -> 去确定b，candidate肯定在4和5里面选
 * [4,5,9]
 * 1. this range covers all nums
 * 2. a is the current smallest of all (excluding old smallest)
 * 注意：用TreeSet时，由于可能有重复值的存在，不仅要对val排序，还要对group也进行排序，
 * 才能保证在有重复值的时候，永远都是先移除最小的group里的那个值
 */