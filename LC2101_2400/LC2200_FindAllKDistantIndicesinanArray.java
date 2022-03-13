package LC2101_2400;
import java.util.*;
public class LC2200_FindAllKDistantIndicesinanArray {
    /**
     * You are given a 0-indexed integer array nums and two integers key and k. A k-distant index is an index i of nums
     * for which there exists at least one index j such that |i - j| <= k and nums[j] == key.
     *
     * Return a list of all k-distant indices sorted in increasing order.
     *
     * Input: nums = [3,4,9,1,3,9,5], key = 9, k = 1
     * Output: [1,2,3,4,5,6]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 1000
     * key is an integer from the array nums.
     * 1 <= k <= nums.length
     * @param nums
     * @param key
     * @param k
     * @return
     */
    // S1: sort
    // time = O(nlogn), space = O(n)
    public List<Integer> findKDistantIndices(int[] nums, int key, int k) {
        List<Integer> res = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == key) {
                for (int j = Math.max(0, i - k); j <= Math.min(n - 1, i + k); j++) set.add(j);
            }
        }
        res.addAll(set);
        Collections.sort(res);
        return res;
    }

    // S2: use flag array (optimal solution!)
    // time = O(n), space = O(n)
    public List<Integer> findKDistantIndices2(int[] nums, int key, int k) {
        List<Integer> res = new ArrayList<>();
        int n = nums.length;
        boolean[] flag = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (nums[i] == key) {
                for (int j = Math.max(0, i - k); j <= Math.min(n - 1, i + k); j++) flag[j] = true;
            }
        }

        for (int i = 0; i < n; i++) {
            if (flag[i]) res.add(i);
        }
        return res;
    }
}
