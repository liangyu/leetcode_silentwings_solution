package LC301_600;
import java.util.Random;
public class LC398_RandomPickIndex {
    /**
     * Given an integer array nums with possible duplicates, randomly output the index of a given target number. You
     * can assume that the given target number must exist in the array.
     *
     * Implement the Solution class:
     *
     * Solution(int[] nums) Initializes the object with the array nums.
     * int pick(int target) Picks a random index i from nums where nums[i] == target. If there are multiple valid i's,
     * then each index should have an equal probability of returning.
     *
     * Input
     * ["Solution", "pick", "pick", "pick"]
     * [[[1, 2, 3, 3, 3]], [3], [1], [3]]
     * Output
     * [null, 4, 0, 2]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 10^4
     * -2^31 <= nums[i] <= 2^31 - 1
     * target is an integer from nums.
     * At most 10^4 calls will be made to pick.
     * @param nums
     */
    int[] nums;
    Random random;
    public LC398_RandomPickIndex(int[] nums) {
        this.nums = nums;
        random = new Random();
    }

    // time = O(n), space = O(1)
    public int pick(int target) {
        int n = nums.length, k = 0, x = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] != target) continue;
            k++;
            if (random.nextInt(k) == 0) x = i;
        }
        return x;
    }
}
/**
 * reservoir sampling
 * 适用于stream data,看过一个数据就可以扔掉
 * 就算之前的数都不记得，但也能给出一个指定概率的数
 */
