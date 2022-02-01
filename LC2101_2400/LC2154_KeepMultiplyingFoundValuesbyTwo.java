package LC2101_2400;
import java.util.*;
public class LC2154_KeepMultiplyingFoundValuesbyTwo {
    /**
     * You are given an array of integers nums. You are also given an integer original which is the first number that
     * needs to be searched for in nums.
     *
     * You then do the following steps:
     *
     * If original is found in nums, multiply it by two (i.e., set original = 2 * original).
     * Otherwise, stop the process.
     * Repeat this process with the new number as long as you keep finding the number.
     * Return the final value of original.
     *
     * Input: nums = [5,3,6,1,12], original = 3
     * Output: 24
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 1 <= nums[i], original <= 1000
     * @param nums
     * @param original
     * @return
     */
    // S1: HashSet
    // time = O(n), space = O(n)
    public int findFinalValue(int[] nums, int original) {
        HashSet<Integer> set = new HashSet<>();
        for (int x : nums) set.add(x);
        while (set.contains(original)) original *= 2;
        return original;
    }

    // S2: count sort (optimal solution!)
    // time = O(n), space = O(1)
    public int findFinalValue2(int[] nums, int original) {
        // 注意：这里要开到2001，因为如果original能取到1000，*2之后数字会到2000，这时如果你只开1001，会不够！！
        int[] count = new int[2001];
        for (int x : nums) count[x]++;
        while (count[original] > 0) original *= 2;
        return original;
    }
}
