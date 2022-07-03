package LC901_1200;

public class LC1089_DuplicateZeros {
    /**
     * Given a fixed-length integer array arr, duplicate each occurrence of zero, shifting the remaining elements to
     * the right.
     *
     * Note that elements beyond the length of the original array are not written. Do the above modifications to the
     * input array in place and do not return anything.
     *
     * Input: arr = [1,0,2,3,0,4,5,0]
     * Output: [1,0,0,2,3,0,0,4]
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^4
     * 0 <= arr[i] <= 9
     * @param arr
     */
    // time = O(n), space = O(1)
    public void duplicateZeros(int[] arr) {
        int n = arr.length, count = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] != 0) count++;
            else count += 2;
            if (count >= n) {
                boolean flag = false;
                if (count == n + 1) flag = true;
                for (int j = count - 1; i >= 0; i--) {
                    if (arr[i] != 0) arr[j--] = arr[i];
                    else {
                        if (flag) {
                            j--;
                            arr[j--] = 0;
                            flag = false;
                        } else {
                            arr[j--] = 0;
                            arr[j--] = 0;
                        }
                    }
                }
                break;
            }
        }
    }
}
