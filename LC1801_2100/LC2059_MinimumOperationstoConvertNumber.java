package LC1801_2100;
import java.util.*;
public class LC2059_MinimumOperationstoConvertNumber {
    /**
     * You are given a 0-indexed integer array nums containing distinct numbers, an integer start, and an integer goal.
     * There is an integer x that is initially set to start, and you want to perform operations on x such that it is
     * converted to goal. You can perform the following operation repeatedly on the number x:
     *
     * If 0 <= x <= 1000, then for any index i in the array (0 <= i < nums.length), you can set x to any of the following:
     *
     * x + nums[i]
     * x - nums[i]
     * x ^ nums[i] (bitwise-XOR)
     * Note that you can use each nums[i] any number of times in any order. Operations that set x to be out of the
     * range 0 <= x <= 1000 are valid, but no more operations can be done afterward.
     *
     * Return the minimum number of operations needed to convert x = start into goal, and -1 if it is not possible.
     *
     * Input: nums = [1,3], start = 6, goal = 4
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * -109 <= nums[i], goal <= 10^9
     * 0 <= start <= 1000
     * start != goal
     * All the integers in nums are distinct.
     * @param nums
     * @param start
     * @param goal
     * @return
     */
    public int minimumOperations(int[] nums, int start, int goal) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        HashSet<Integer> visited = new HashSet<>();
        visited.add(start);

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();

                if (cur >= 0 && cur <= 1000) {
                    for (int num : nums) {
                        int val1 = cur + num;
                        int val2 = cur - num;
                        int val3 = (cur ^ num);
                        if (val1 == goal || val2 == goal || val3 == goal) return step + 1;
                        if (val1 >= 0 && val1 <= 1000 && visited.add(val1)) queue.offer(val1);
                        if (val2 >= 0 && val2 <= 1000 && visited.add(val2)) queue.offer(val2);
                        if (val3 >= 0 && val3 <= 1000 && visited.add(val3)) queue.offer(val3);
                    }
                }
            }
            step++;
        }
        return -1;
    }
}
