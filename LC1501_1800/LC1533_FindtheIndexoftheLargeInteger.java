package LC1501_1800;

public class LC1533_FindtheIndexoftheLargeInteger {
    /**
     * We have an integer array arr, where all the integers in arr are equal except for one integer which is larger
     * than the rest of the integers. You will not be given direct access to the array, instead, you will have an API
     * ArrayReader which have the following functions:
     *
     * int compareSub(int l, int r, int x, int y): where 0 <= l, r, x, y < ArrayReader.length(), l <= r and x <= y.
     * The function compares the sum of sub-array arr[l..r] with the sum of the sub-array arr[x..y] and returns:
     * 1 if arr[l]+arr[l+1]+...+arr[r] > arr[x]+arr[x+1]+...+arr[y].
     * 0 if arr[l]+arr[l+1]+...+arr[r] == arr[x]+arr[x+1]+...+arr[y].
     * -1 if arr[l]+arr[l+1]+...+arr[r] < arr[x]+arr[x+1]+...+arr[y].
     * int length(): Returns the size of the array.
     * You are allowed to call compareSub() 20 times at most. You can assume both functions work in O(1) time.
     *
     * Return the index of the array arr which has the largest integer.
     *
     * Follow-up:
     *
     * What if there are two numbers in arr that are bigger than all other numbers?
     * What if there is one number that is bigger than other numbers and one number that is smaller than other numbers?
     *
     * Input: arr = [7,7,7,7,10,7,7,7]
     * Output: 4
     *
     * Constraints:
     *
     * 2 <= arr.length <= 5 * 10^5
     * 1 <= arr[i] <= 100
     * All elements of arr are equal except for one element which is larger than all other elements.
     * @param reader
     * @return
     */
    // time = O(logn), space = O(1)
    public int getIndex(ArrayReader reader) {
        int n = reader.length();
        int left = 0, right = n - 1;
        while (right - left + 1 >= 3) {
            int mid = (right - left + 1) / 3;
            int res = reader.compareSub(left, left + mid - 1, left + mid, left + 2 * mid - 1);
            if (res == 0) left = left + 2 * mid;
            else if (res == 1) right = left + mid - 1;
            else {
                left = left + mid;
                right = left + 2 * mid - 1;
            }
        }
        // 出loop时，2种情况：1个元素 or 2个元素
        if (left == right) return left;
        else {
            if (reader.compareSub(left, left, right, right) == 1) return left;
            return right;
        }
    }
}
/**
 * // This is ArrayReader's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface ArrayReader {
 *     // Compares the sum of arr[l..r] with the sum of arr[x..y]
 *     // return 1 if sum(arr[l..r]) > sum(arr[x..y])
 *     // return 0 if sum(arr[l..r]) == sum(arr[x..y])
 *     // return -1 if sum(arr[l..r]) < sum(arr[x..y])
 *     public int compareSub(int l, int r, int x, int y) {}
 *
 *     // Returns the length of the array
 *     public int length() {}
 * }
 *
 * 本题本质是三分搜索。我们将一个区间分为三分ABC，其中A和B的区间大小相等。
 * 如果A、B区间和相等，那么异类就在区间C里面。
 * 如果A、B区间和不相等，那么异类就在A、B较大的区间里面。
 *
 * 注意外层循环的条件变成了while (right-left+1 >= 3)。原因是区间长度小于3的时候无法成功三分区间。
 */