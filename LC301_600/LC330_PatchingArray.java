package LC301_600;

public class LC330_PatchingArray {
    /**
     * Given a sorted integer array nums and an integer n, add/patch elements to the array such that any number in the
     * range [1, n] inclusive can be formed by the sum of some elements in the array.
     *
     * Return the minimum number of patches required.
     *
     * Input: nums = [1,3], n = 6
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 10^4
     * nums is sorted in ascending order.
     * 1 <= n <= 2^31 - 1
     * @param nums
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int minPatches(int[] nums, int n) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int count = 0, i = 0;
        long miss = 1;

        while (miss <= n) {
            if (i >= nums.length || miss < nums[i]) {
                count++;
                miss += miss;
            } else miss += nums[i++];
        }
        return count;
    }
}
/**
 * 假设miss是当前无法通过nums[0]~nums[i-1]这i个数通过题述的加和规则得到的、最小的那个整数，
 * 如果nums[i]>miss，说明它的加入对于我们试图得到miss没有任何帮助，因为nums[i]太大了；并且之后的nums元素更不会有帮助，只会更大。
 * 所以miss只能是我们必须人为补充的一个数，即我们手工补上miss。
 * 这时下一个无法得到的整数是什么呢？那就是miss*2.因为miss加入前，1~miss-1我们都可以得到，
 * 所以miss加入后最大能得到 miss - 1 + miss = 2*miss-1.
 * 如果nums[i]<=miss，说明它的加入可以将miss提升至miss+nums[i].
 * 因为nums[i]加入前，1~miss-1我们都可以得到，所以miss加入后最大能得到miss-1+nums[i].
 * 如此循环重复上述的过程，不断提升miss，直至miss>n.
 * 注意这个过程中nums可以提前用完。
 * 另外，值得注意的是，miss的初始值是1，而不是0. 因为没有任何nums提供时，我们首先试图要填补的就是1.
 */
