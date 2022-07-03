package LC901_1200;

public class LC1053_PreviousPermutationWithOneSwap {
    /**
     * Given an array of positive integers arr (not necessarily distinct), return the lexicographically largest
     * permutation that is smaller than arr, that can be made with exactly one swap (A swap exchanges the positions of
     * two numbers arr[i] and arr[j]). If it cannot be done, then return the same array.
     *
     * Input: arr = [3,2,1]
     * Output: [3,1,2]
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^4
     * 1 <= arr[i] <= 10^4
     * @param arr
     * @return
     */
    // time = O(n), space = O(1)
    public int[] prevPermOpt1(int[] arr) {
        int n = arr.length;
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                int j = i + 1;
                while (j + 1 < n && arr[j + 1] < arr[i]) j++;
                while (arr[j] == arr[j - 1]) j--;
                swap(arr, i, j);
                return arr;
            }
        }
        return arr;
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
/**
 * ai > 其后最小值
 * ai > ai-1
 * 交换找 < ai的最大值
 * 若不唯一，取第一个
 */