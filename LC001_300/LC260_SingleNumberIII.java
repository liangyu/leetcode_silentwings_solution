package LC001_300;
import java.util.*;
public class LC260_SingleNumberIII {
    /**
     * Given an integer array nums, in which exactly two elements appear only once and all the other elements appear
     * exactly twice. Find the two elements that appear only once. You can return the answer in any order.
     *
     * You must write an algorithm that runs in linear runtime complexity and uses only constant extra space.
     *
     * Input: nums = [1,2,1,3,2,5]
     * Output: [3,5]
     *
     * Constraints:
     *
     * 2 <= nums.length <= 3 * 10^4
     * -2^31 <= nums[i] <= 2^31 - 1
     * Each integer in nums will appear twice, only two integers will appear once.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int[] singleNumber(int[] nums) {
        // corner case
        if (nums == null || nums.length < 2) return new int[0];

        int s = 0;
        for (int x : nums) s = s ^ x;
        int t = s & (-s); // isolate the rightmost 1-bit

        int a = 0, b = 0;
        for (int x : nums) {
            if ((x & t) != 0) a = a ^ x; // two numbers differentiate at the lth digit, one for 0 and the other for 1
            else b = b ^ x;
        }
        return new int[]{a, b};
    }
}
/**
 * 这是一道经典题．
 * 首先，我们容易想到把所有数字亦或起来，看看得到什么．假设最后的结果是x和y，那么我们最后得到的是x和y．这有什么用呢？
 * 显然x,y不相等的话，那么x^y不可能为零．
 * 所以必然有一个bit位置上，x和y是不同的．
 * 那么我们就可以把所有的数字按照这个位置的bit值分为两类，
 * 其中一类和x相同（偶数个），另一类和y相同（也是偶数个）．
 * 这两类各自做亦或和的操作，相同的数都会cancel掉，剩下来的就是x和y本身了．
 * 此题需要用到的一些bit操作技巧是：
 * x&(x-1)：　表示unset the rightmost set bit
 * x^(x&(x-1))：　表示只保留 the rightmost set bit
 * log2(x^(x&(x-1)))：　表示the rightmost set bit的位置
 */
