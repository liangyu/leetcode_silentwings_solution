package LC2101_2400;

public class LC2216_MinimumDeletionstoMakeArrayBeautiful {
    /**
     * You are given a 0-indexed integer array nums. The array nums is beautiful if:
     *
     * nums.length is even.
     * nums[i] != nums[i + 1] for all i % 2 == 0.
     * Note that an empty array is considered beautiful.
     *
     * You can delete any number of elements from nums. When you delete an element, all the elements to the right of the
     * deleted element will be shifted one unit to the left to fill the gap created and all the elements to the left of
     * the deleted element will remain unchanged.
     *
     * Return the minimum number of elements to delete from nums to make it beautiful.
     *
     * Input: nums = [1,1,2,3,5]
     * Output: 1
     *
     * Input: nums = [1,1,2,2,3,3]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // S1: Greedy
    // time = O(n), space = O(1)
    public int minDeletion(int[] nums) {
        int n = nums.length, count = 0;

        for (int i = 0; i < n - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                if ((count + i) % 2 == 0) count++;
            }
        }
        return (n - count) % 2 == 0 ? count : count + 1;
    }

    // S2: Greedy
    // time = O(n), space = O(1)
    public int minDeletion2(int[] nums) {
        int n = nums.length, i = 0, count = 0;
        while (i < n) {
            int j = i + 1;
            while (j < n && nums[j] == nums[i]) {
                count++;
                j++;
            }
            if (j < n) i = j + 1;
            else {
                count++;
                break;
            }
        }
        return count;
    }
}
/**
 * greedy
 * 1 2 | 3 5 | 1 8 | 6 6 |
 * .. X Y | 6 6 | Z W ...
 * 1. .. X Y | 6 Z W ...
 * 2. .. X 6 | 6 Z W ...
 * 前面都是OK的，66是遇到的第一个要处理的情况
 * 第二种方案不及第一种，可能要对前半部分需要重新调整，第一种方案则是0操作
 * 我们就无脑删掉一个东西，只对后半部分操作，移过来之后再继续处理
 */