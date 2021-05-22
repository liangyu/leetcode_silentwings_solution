package LC1201_1500;
import java.util.*;
public class LC1228_MissingNumberInArithmeticProgression {
    /**
     * In some array arr, the values were in arithmetic progression: the values arr[i+1] - arr[i] are all equal for
     * every 0 <= i < arr.length - 1.
     *
     * Then, a value from arr was removed that was not the first or last value in the array.
     *
     * Return the removed value.
     *
     * Input: arr = [5,7,11,13]
     * Output: 9
     *
     * Constraints:
     *
     * 3 <= arr.length <= 1000
     * 0 <= arr[i] <= 10^5
     * @param arr
     * @return
     */
    // time = O(n), space = O(1)
    public int missingNumber(int[] arr) {
        // corner case
        if (arr == null || arr.length <= 2) throw new IllegalArgumentException();

        int diff = Math.abs(arr[1] - arr[0]);
        for (int i = 1; i < arr.length - 1; i++) {
            int val = Math.abs(arr[i + 1] - arr[i]);
            if (val == diff) continue;
            else {
                if (val < diff) return  (arr[0] + arr[1]) / 2;
                else return (arr[i] + arr[i + 1]) / 2;
            }
        }
        return arr[0];
    }
}
