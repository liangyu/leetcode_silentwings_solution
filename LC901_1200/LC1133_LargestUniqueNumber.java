package LC901_1200;
import java.util.*;
public class LC1133_LargestUniqueNumber {
    /**
     * Given an array of integers A, return the largest integer that only occurs once.
     *
     * If no integer occurs once, return -1.
     *
     * Input: [5,7,3,9,4,9,8,3,1]
     * Output: 8
     *
     * Note:
     *
     * 1 <= A.length <= 2000
     * 0 <= A[i] <= 1000
     * @param A
     * @return
     */
    // time = O(n), space = O(n)
    public int largestUniqueNumber(int[] A) {
        // corner case
        if (A == null || A.length == 0) return -1;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int n : A) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        int res = Integer.MIN_VALUE;
        for (int key : map.keySet()) {
            if (map.get(key) == 1) res = Math.max(res, key);
        }
        return res == Integer.MIN_VALUE ? -1 :res;
    }
}
