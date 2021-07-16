package LC301_600;

public class LC370_RangeAddition {
    /**
     * You are given an integer length and an array updates where updates[i] = [startIdxi, endIdxi, inci].
     *
     * You have an array arr of length length with all zeros, and you have some operation to apply on arr. In the ith
     * operation, you should increment all the elements arr[startIdxi], arr[startIdxi + 1], ..., arr[endIdxi] by inci.
     *
     * Return arr after applying all the updates.
     *
     * Input: length = 5, updates = [[1,3,2],[2,4,3],[0,2,-2]]
     * Output: [-2,0,3,5,3]
     *
     * Constraints:
     *
     * 1 <= length <= 10^5
     * 0 <= updates.length <= 10^4
     * 0 <= startIdxi <= endIdxi < length
     * -1000 <= inci <= 1000
     * @param length
     * @param updates
     * @return
     */
    // S1: burte-force
    // time = O(n * k), space = O(1)
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] res = new int[length];
        for (int[] update : updates) {
            int a = update[0], b = update[1];
            for (int i = a; i <= b; i++) res[i] += update[2];
        }
        return res;
    }

    // S2: range caching
    // time = O(n + k), space = O(1)
    public int[] getModifiedArray2(int length, int[][] updates) {
        int[] res = new int[length];
        for (int[] update : updates) {
            int start = update[0], end = update[1], val = update[2];
            res[start] += val;
            if (end < length - 1) res[end + 1] -= val;
        }

        int sum = 0;
        for (int i = 0 ; i < length; i++) {
            sum += res[i];
            res[i] = sum;
        }
        return res;
    }
}
