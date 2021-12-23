package LC901_1200;
import java.util.*;
public class LC1200_MinimumAbsoluteDifference {
    /**
     * Given an array of distinct integers arr, find all pairs of elements with the minimum absolute difference of any
     * two elements.
     *
     * Return a list of pairs in ascending order(with respect to pairs), each pair [a, b] follows
     *
     * a, b are from arr
     * a < b
     * b - a equals to the minimum absolute difference of any two elements in arr
     *
     * Input: arr = [4,2,1,3]
     * Output: [[1,2],[2,3],[3,4]]
     *
     * Constraints:
     *
     * 2 <= arr.length <= 10^5
     * -10^6 <= arr[i] <= 10^6
     * @param arr
     * @return
     */
    // S1: Sort
    // time = O(nlogn), space = O(1)
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
       List<List<Integer>> res = new ArrayList<>();
       Arrays.sort(arr);
       int n = arr.length, min = Integer.MAX_VALUE;
       for (int i = 1; i < n; i++) {
           if (arr[i] - arr[i - 1] <= min) {
               if (arr[i] - arr[i - 1] < min) {
                   min = arr[i] - arr[i - 1];
                   res = new ArrayList<>();
               }
               res.add(Arrays.asList(arr[i - 1], arr[i]));
           }
       }
       return res;
    }

    // S2: count sort
    // time = O(n), space = O(n)
    public List<List<Integer>> minimumAbsDifference2(int[] arr) {
        List<List<Integer>> res = new ArrayList<>();

        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int x : arr) {
            min = Math.min(min, x);
            max = Math.max(max, x);
        }

        int[] nums = new int[max - min + 1];
        for (int x : arr) nums[x - min] = 1;

        int minDiff = max - min, prev = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                if (prev == -1) {
                    prev = i;
                    continue;
                }
                int diff = i - prev;
                if (diff <= minDiff) {
                    if (diff < minDiff) {
                        res = new ArrayList<>();
                        minDiff = diff;
                    }
                    res.add(Arrays.asList(prev + min, i + min));
                }
                prev = i;
            }
        }
        return res;
    }
}
