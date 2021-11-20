package LC001_300;
import java.util.*;
public class LC128_LongestConsecutiveSequence {
    /**
     * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
     *
     * Input: nums = [100,4,200,1,3,2]
     * Output: 4
     *
     * Constraints:
     *
     * 0 <= nums.length <= 10^4
     * -10^9 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // S1: set
    // time = O(n), space = O(n)
    public int longestConsecutive(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        HashSet<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);

        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int down = nums[i] - 1, up = nums[i] + 1;
            while (set.contains(down)) set.remove(down--);
            while (set.contains(up)) set.remove(up++);
            res = Math.max(res, up - down - 1);
        }
        return res;
    }

    // S2: union find
    // time = O(nlogn), space = O(1)
    HashMap<Integer, Integer> parent;
    public int longestConsecutive2(int[] nums) {
        parent = new HashMap<>();
        for (int x : nums) {
            if (!parent.containsKey(x)) parent.put(x, x);
            if (parent.containsKey(x - 1) && findParent(x) != findParent(x - 1)) union(x, x - 1);
            if (parent.containsKey(x + 1) && findParent(x) != findParent(x + 1)) union(x, x + 1);
        }

        for (int x : nums) parent.put(x, findParent(x));

        HashMap<Integer, HashSet<Integer>> count = new HashMap<>();
        for (int x : nums) {
            int p = parent.get(x);
            count.putIfAbsent(p, new HashSet<>());
            count.get(p).add(x);
        }

        int res = 0;
        for (int key : count.keySet()) {
            res = Math.max(res, count.get(key).size());
        }
        return res;
    }

    private int findParent(int x) {
        if (parent.get(x) != x) {
            parent.put(x, findParent(parent.get(x)));
        }
        return parent.get(x);
    }

    private void union(int x, int y) {
        x = parent.get(x);
        y = parent.get(y);
        if (x < y) parent.put(y, x);
        else parent.put(x, y);
    }
}
/**
 * union find 没有特殊处理的话，是O(nlogn)
 * 挨个扫一遍，看我们的新元素是不是跟之前的数字会不会连着
 * 看这个数-1或者+1在不在里面
 * 找group最大的一个
 */