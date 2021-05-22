package LC1201_1500;
import java.util.*;
public class LC1437_CheckIfAll1sAreatLeastLengthKPlacesAway {
    /**
     * Given an array nums of 0s and 1s and an integer k, return True if all 1's are at least k places away
     * from each other, otherwise return False.
     *
     * Input: nums = [1,0,0,0,1,0,0,1], k = 2
     * Output: true
     *
     * Input: nums = [1,0,0,1,0,1], k = 2
     * Output: false
     *
     * Constraints:
     *
     * 1 <= nums.length <= 105
     * 0 <= k <= nums.length
     * nums[i] is 0 or 1
     *
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public boolean kLengthApart(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k < 0) return false;

        int count = k; // 为了在遇到初始的第一个1时不返回false,在这里设置为k
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                if (count < k) return false;
                count = 0; // 记得遇到新的一个1后，则要开始重新计数了
            } else count++; // 如果是0则++ => 统计各个1之间0的个数是否 >= k
        }
        return true;
    }
}
