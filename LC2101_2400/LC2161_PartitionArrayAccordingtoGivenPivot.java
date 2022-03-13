package LC2101_2400;
import java.util.*;
public class LC2161_PartitionArrayAccordingtoGivenPivot {
    /**
     * You are given a 0-indexed integer array nums and an integer pivot. Rearrange nums such that the following
     * conditions are satisfied:
     *
     * Every element less than pivot appears before every element greater than pivot.
     * Every element equal to pivot appears in between the elements less than and greater than pivot.
     * The relative order of the elements less than pivot and the elements greater than pivot is maintained.
     * More formally, consider every pi, pj where pi is the new position of the ith element and pj is the new position
     * of the jth element. For elements less than pivot, if i < j and nums[i] < pivot and nums[j] < pivot, then pi < pj.
     * Similarly for elements greater than pivot, if i < j and nums[i] > pivot and nums[j] > pivot, then pi < pj.
     * Return nums after the rearrangement.
     *
     * Input: nums = [9,12,5,10,14,3,10], pivot = 10
     * Output: [9,5,3,10,10,12,14]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -10^6 <= nums[i] <= 10^6
     * pivot equals to an element of nums.
     * @param nums
     * @param pivot
     * @return
     */
    // time = O(n), space = O(n)
    public int[] pivotArray(int[] nums, int pivot) {
        int n = nums.length, same = 0;
        List<Integer> small = new ArrayList<>();
        List<Integer> large = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (nums[i] < pivot) small.add(nums[i]);
            else if (nums[i] > pivot) large.add(nums[i]);
            else same++;
        }

        int[] res = new int[n];
        int idx = 0;
        for (int x : small) res[idx++] = x;
        for (int i = 0; i < same; i++) res[idx++] = pivot;
        for (int x : large) res[idx++] = x;
        return res;
    }
}
