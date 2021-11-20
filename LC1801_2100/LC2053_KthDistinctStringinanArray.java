package LC1801_2100;
import java.util.*;
public class LC2053_KthDistinctStringinanArray {
    /**
     * A distinct string is a string that is present only once in an array.
     *
     * Given an array of strings arr, and an integer k, return the kth distinct string present in arr. If there are
     * fewer than k distinct strings, return an empty string "".
     *
     * Note that the strings are considered in the order in which they appear in the array.
     *
     * Input: arr = ["d","b","c","b","c","a"], k = 2
     * Output: "a"
     *
     * Constraints:
     *
     * 1 <= k <= arr.length <= 1000
     * 1 <= arr[i].length <= 5
     * arr[i] consists of lowercase English letters.
     * @param arr
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public String kthDistinct(String[] arr, int k) {
        // corner case
        if (arr == null || arr.length == 0 || k <= 0) return "";

        HashMap<String, Integer> map = new HashMap<>();
        for (String s : arr) map.put(s, map.getOrDefault(s, 0) + 1);

        for (String s : arr) {
            if (map.get(s) == 1) k--;
            if (k == 0) return s;
        }
        return "";
    }
}
