package LC901_1200;

public class LC1060_MissingElementinSortedArray {
    /**
     * Given an integer array nums which is sorted in ascending order and all of its elements are unique and given also
     * an integer k, return the kth missing number starting from the leftmost number of the array.
     *
     * Input: nums = [4,7,9,10], k = 1
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5 * 10^4
     * 1 <= nums[i] <= 10^7
     * nums is sorted in ascending order, and all the elements are unique.
     * 1 <= k <= 10^8
     * @param nums
     * @param k
     * @return
     */
    // time = O(logn), space = O(1)
    public int missingElement(int[] nums, int k) {
        int n = nums.length;
        int left = 0, right = n - 1;
        int missing = nums[right] - nums[left] + 1 - (right - left + 1);
        if (missing  < k) return nums[right] + k - missing;
        while (left < right) {
            int mid = right - (right - left) / 2;
            missing = nums[mid] - nums[left] - (mid - left); // 不包含mid本身，总共在[left, mid)区间上缺少的个数！
            if (missing >= k) right = mid - 1; // 不包含mid自己，缺少的数字已经 >= k的话，那表示第k个缺少的数一定在mid左边！
            else {  // 不包含mid本身，缺少的数字少于k个，那么有可能mid就是第k个缺少的数字(如果mid前面缺少k-1个数字的话),也有可能在mid之后！
                left = mid;
                k -= missing; // 减掉[left,mid)里缺少掉的数字个数，这样才能把[left,mid)放心砍掉，看 >= mid的区间里还缺少的k-missing个！
            }
        }
        // 记得要加上剩余的k,因为最后left == right的时候，k还没减到0，缺少的数字在nums[left: left + 1]里，所以需要加上k!
        return nums[left] + k;
    }
}
