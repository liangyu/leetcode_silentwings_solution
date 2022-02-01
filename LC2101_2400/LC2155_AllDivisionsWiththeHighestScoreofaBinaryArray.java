package LC2101_2400;
import java.util.*;
public class LC2155_AllDivisionsWiththeHighestScoreofaBinaryArray {
    /**
     * You are given a 0-indexed binary array nums of length n. nums can be divided at index i (where 0 <= i <= n) into
     * two arrays (possibly empty) numsleft and numsright:
     *
     * numsleft has all the elements of nums between index 0 and i - 1 (inclusive), while numsright has all the elements
     * of nums between index i and n - 1 (inclusive).
     * If i == 0, numsleft is empty, while numsright has all the elements of nums.
     * If i == n, numsleft has all the elements of nums, while numsright is empty.
     * The division score of an index i is the sum of the number of 0's in numsleft and the number of 1's in numsright.
     *
     * Return all distinct indices that have the highest possible division score. You may return the answer in any order.
     *
     * Input: nums = [0,0,1,0]
     * Output: [2,4]
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 10^5
     * nums[i] is either 0 or 1.
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public List<Integer> maxScoreIndices(int[] nums) {
        int n = nums.length;
        int[] pre = new int[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) count++;
            pre[i] = count;
        }

        int[] suf = new int[n];
        count = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] == 1) count++;
            suf[i] = count;
        }

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i <= n; i++) {
            count = (i - 1 >= 0 ? pre[i - 1] : 0) + (i < n ? suf[i] : 0);
            map.putIfAbsent(count, new ArrayList<>());
            map.get(count).add(i);
        }

        int max = 0;
        List<Integer> res = new ArrayList<>();
        for (int key : map.keySet()) {
            if (key > max) {
                max = key;
                res = map.get(key);
            }
        }
        return res;
    }
}
