package LC2101_2400;
import java.util.*;
public class LC2191_SorttheJumbledNumbers {
    /**
     * You are given a 0-indexed integer array mapping which represents the mapping rule of a shuffled decimal system.
     * mapping[i] = j means digit i should be mapped to digit j in this system.
     *
     * The mapped value of an integer is the new integer obtained by replacing each occurrence of digit i in the integer
     * with mapping[i] for all 0 <= i <= 9.
     *
     * You are also given another integer array nums. Return the array nums sorted in non-decreasing order based on the
     * mapped values of its elements.
     *
     * Notes:
     *
     * Elements with the same mapped values should appear in the same relative order as in the input.
     * The elements of nums should only be sorted based on their mapped values and not be replaced by them.
     *
     * Input: mapping = [8,9,4,0,2,1,3,5,7,6], nums = [991,338,38]
     * Output: [338,38,991]
     *
     * Constraints:
     *
     * mapping.length == 10
     * 0 <= mapping[i] <= 9
     * All the values of mapping[i] are unique.
     * 1 <= nums.length <= 3 * 10^4
     * 0 <= nums[i] < 10^9
     * @param mapping
     * @param nums
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int[] sortJumbled(int[] mapping, int[] nums) {
        int n = nums.length;
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            int val = helper(mapping, nums[i]);
            arr[i] = new int[]{val, i};
        }

        Arrays.sort(arr, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
        int[] res = new int[n];
        for (int i = 0; i < n; i++) res[i] = nums[arr[i][1]];
        return res;
    }

    private int helper(int[] mapping, int num) {
        int val = 0;
        String s = String.valueOf(num);
        for (char c : s.toCharArray()) {
            val = val * 10 + mapping[c - '0'];
        }
        return val;
    }
}
