package LC301_600;

public class LC457_CircularArrayLoop {
    /**
     * You are playing a game involving a circular array of non-zero integers nums. Each nums[i] denotes the number of
     * indices forward/backward you must move if you are located at index i:
     *
     * If nums[i] is positive, move nums[i] steps forward, and
     * If nums[i] is negative, move nums[i] steps backward.
     * Since the array is circular, you may assume that moving forward from the last element puts you on the first
     * element, and moving backwards from the first element puts you on the last element.
     *
     * A cycle in the array consists of a sequence of indices seq of length k where:
     *
     * Following the movement rules above results in the repeating index sequence seq[0] -> seq[1] -> ... -> seq[k - 1]
     * -> seq[0] -> ...
     * Every nums[seq[j]] is either all positive or all negative.
     * k > 1
     * Return true if there is a cycle in nums, or false otherwise.
     *
     * Input: nums = [2,-1,1,2,2]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5000
     * -1000 <= nums[i] <= 1000
     * nums[i] != 0
     *
     * Follow up: Could you solve it in O(n) time complexity and O(1) extra space complexity?
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public boolean circularArrayLoop(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return false;

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) continue;
            int slow = i, fast = getNext(nums, i);
            // 判断非零且方向相同
            while (nums[slow] * nums[fast] > 0 && nums[slow] * nums[getNext(nums, fast)] > 0) {
                if (slow == fast) {  //当快慢指针重合的时候，即成环的时候
                    if (slow != getNext(nums, slow)) return true; //避免循环长度等于1的情况
                    else break;
                }
                slow = getNext(nums, slow);
                fast = getNext(nums, getNext(nums, fast));
            }
            //走过的链条不用再走，因此设置为0
            int add = i;
            while (nums[add] * nums[getNext(nums, add)] > 0) {
                int temp = add;
                add = getNext(nums, add);
                nums[temp] = 0;
            }
        }

        return false;
    }

    private int getNext(int[] nums, int i) {
        int n = nums.length;
        return ((i + nums[i]) % n + n) % n; // 保证返回值在 [0,n) 中
    }
}
