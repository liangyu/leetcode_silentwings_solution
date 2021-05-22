package LC1501_1800;
import java.util.*;
public class LC1640_CheckArrayFormationThroughConcatenation {
    /**
     * You are given an array of distinct integers arr and an array of integer arrays pieces, where the integers in
     * pieces are distinct. Your goal is to form arr by concatenating the arrays in pieces in any order. However,
     * you are not allowed to reorder the integers in each array pieces[i].
     *
     * Return true if it is possible to form the array arr from pieces. Otherwise, return false.
     *
     * Input: arr = [91,4,64,78], pieces = [[78],[4,64],[91]]
     * Output: true
     *
     * Input: arr = [49,18,16], pieces = [[16,18,49]]
     * Output: false
     *
     * Constraints:
     *
     * 1 <= pieces.length <= arr.length <= 100
     * sum(pieces[i].length) == arr.length
     * 1 <= pieces[i].length <= arr.length
     * 1 <= arr[i], pieces[i][j] <= 100
     * The integers in arr are distinct.
     * The integers in pieces are distinct (i.e., If we flatten pieces in a 1D array, all the integers in this array
     * are distinct).
     *
     * @param arr
     * @param pieces
     * @return
     */
    // S1: HashMap
    // time = O(n), space = O(n)
    public boolean canFormArray(int[] arr, int[][] pieces) {
        HashMap<Integer, int[]> map = new HashMap<>();
        for (int[] p : pieces) map.put(p[0], p);

        int i = 0;
        while (i < arr.length) {
            if (!map.containsKey(arr[i])) return false;
            int[] piece = map.get(arr[i]);
            for (int p : piece) {
                if (p != arr[i]) return false;
                i++;
            }
        }
        return false;
    }

    // S2: HashMap + Stack
    // time = O(n), space = O(n)
    public boolean canFormArray2(int[] arr, int[][] pieces) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0 ; i < arr.length; i++) {
            map.put(arr[i], i);
        }

        for (int[] piece : pieces) {
            Stack<Integer> stack = new Stack<>();
            for (int p : piece) {
                if (!map.containsKey(p)) return false;
                if (!stack.isEmpty() && map.get(p) - stack.peek() != 1) return false;
                stack.push(map.get(p));
            }
        }
        return true;
    }
}
