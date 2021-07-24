package LC1801_2100;
import java.util.*;
public class LC1940_LongestCommonSubsequenceBetweenSortedArrays  {
    /**
     * Given an array of integer arrays arrays where each arrays[i] is sorted in strictly increasing order, return an
     * integer array representing the longest common subsequence between all the arrays.
     *
     * A subsequence is a sequence that can be derived from another sequence by deleting some elements (possibly none)
     * without changing the order of the remaining elements.
     *
     * Input: arrays = [[1,3,4],
     *                  [1,4,7,9]]
     * Output: [1,4]
     *
     * Constraints:
     *
     * 2 <= arrays.length <= 100
     * 1 <= arrays[i].length <= 100
     * 1 <= arrays[i][j] <= 100
     * arrays[i] is sorted in strictly increasing order.
     * @param arrays
     * @return
     */
    // time = O(m * n), space = O(1)
    public List<Integer> longestCommomSubsequence(int[][] arrays) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (arrays == null || arrays.length == 0 || arrays[0] == null || arrays[0].length == 0) return res;

        for (int num : arrays[0]) res.add(num);
        for (int i = 1; i < arrays.length; i++) { // O(n)
            res = LCS(res, arrays[i]);
        }
        return res;
    }

    private List<Integer> LCS(List<Integer> list, int[] arr) {
        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;
        while (i < list.size() && j < arr.length) {  // O(m)
            if (list.get(i) == arr[j]) {
                res.add(list.get(i));
                i++;
                j++;
            } else if (list.get(i) < arr[j]) i++;
            else j++;
        }
        return res;
    }
}
