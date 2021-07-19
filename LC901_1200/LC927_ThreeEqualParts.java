package LC901_1200;

public class LC927_ThreeEqualParts {
    /**
     * You are given an array arr which consists of only zeros and ones, divide the array into three non-empty parts
     * such that all of these parts represent the same binary value.
     *
     * If it is possible, return any [i, j] with i + 1 < j, such that:
     *
     * arr[0], arr[1], ..., arr[i] is the first part,
     * arr[i + 1], arr[i + 2], ..., arr[j - 1] is the second part, and
     * arr[j], arr[j + 1], ..., arr[arr.length - 1] is the third part.
     * All three parts have equal binary values.
     * If it is not possible, return [-1, -1].
     *
     * Note that the entire part is used when considering what binary value it represents. For example, [1,1,0]
     * represents 6 in decimal, not 3. Also, leading zeros are allowed, so [0,1,1] and [1,1] represent the same value.
     *
     * Input: arr = [1,0,1,0,1]
     * Output: [0,3]
     *
     * Constraints:
     *
     * 3 <= arr.length <= 3 * 10^4
     * arr[i] is 0 or 1
     * @param arr
     * @return
     */
    // time = O(n), space = O(1)
    public int[] threeEqualParts(int[] arr) {
        // corner case
        if (arr == null || arr.length == 0) return new int[0];

        int[] res = new int[2];
        int count = 0;
        for (int x : arr) {
            if (x == 1) count++;
        }
        if (count % 3 != 0) return new int[]{-1, -1};
        if (count == 0) return new int[]{0, arr.length - 1};
        count /= 3;

        int j = arr.length;
        while (count > 0) {
            j--;
            if (arr[j] == 1) count--;
        }

        int i = 0;
        while (arr[i] == 0) i++;
        int k = j;
        while (k < arr.length && arr[i] == arr[k]) {
            i++;
            k++;
        }
        if (k != arr.length) return new int[]{-1, -1};
        res[0] = i - 1;

        while (arr[i] == 0) i++;
        k = j;
        while (k < arr.length && arr[i] == arr[k]) {
            i++;
            k++;
        }
        if (k != arr.length) return new int[]{-1, -1};
        res[1] = i;

        return res;
    }
}
/**
 * 000[1xxxx] 000000 (1xxxx) 000000[1xxxx]
 * 每个Part真实的number是可以确定的
 * 本题的突破口就是全局1的个数。首先，整个数组里面1的个数必须要能被3整除。其次，确定了每个part里面1的个数后（记为count），从后往前数count个1，
 * 就已经确定了这个数长什么样了（记为X）。
 *
 * 然后我们从数组最前端开始忽略若干个先导零，从第一个出现1的地方开始判断这个subarray是否等于X。如果OK，那么我们再忽略若干个先导零，在从下一个
 * 出现1的地方开始判断这个subarray是否等于X。如果再OK，那么three equal parts就已经划分好了。
 */