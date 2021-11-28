package LC1801_2100;
import java.util.*;
public class LC2080_RangeFrequencyQueries {
    /**
     * Design a data structure to find the frequency of a given value in a given subarray.
     *
     * The frequency of a value in a subarray is the number of occurrences of that value in the subarray.
     *
     * Implement the RangeFreqQuery class:
     *
     * RangeFreqQuery(int[] arr) Constructs an instance of the class with the given 0-indexed integer array arr.
     * int query(int left, int right, int value) Returns the frequency of value in the subarray arr[left...right].
     * A subarray is a contiguous sequence of elements within an array. arr[left...right] denotes the subarray that
     * contains the elements of nums between indices left and right (inclusive).
     *
     * Input
     * ["RangeFreqQuery", "query", "query"]
     * [[[12, 33, 4, 56, 22, 2, 34, 33, 22, 12, 34, 56]], [1, 2, 4], [0, 11, 33]]
     * Output
     * [null, 1, 2]
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^5
     * 1 <= arr[i], value <= 10^4
     * 0 <= left <= right < arr.length
     * At most 105 calls will be made to query
     * @param arr
     */
    // time = O(n), space = O(n)
    HashMap<Integer, List<Integer>> map;
    public LC2080_RangeFrequencyQueries(int[] arr) {
        map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.putIfAbsent(arr[i], new ArrayList<>());
            map.get(arr[i]).add(i);
        }
    }

    // time = O(logn), space = O(n)
    public int query(int left, int right, int value) {
        if (!map.containsKey(value)) return 0;
        List<Integer> list = map.get(value);
        int idx1 = lowerBound(list, right);
        int idx2 = lowerBound(list, left - 1); // 注意：这里是left - 1
        return idx1 - idx2;
    }

    private int lowerBound(List<Integer> nums, int t) {
        int left = 0, right = nums.size() - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (nums.get(mid) <= t) left = mid;
            else right = mid - 1;
        }
        return nums.get(left) <= t ? left : left - 1;
    }
}
