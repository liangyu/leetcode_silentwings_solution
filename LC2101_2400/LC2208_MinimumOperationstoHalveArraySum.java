package LC2101_2400;
import java.util.*;
public class LC2208_MinimumOperationstoHalveArraySum {
    /**
     * You are given an array nums of positive integers. In one operation, you can choose any number from nums and
     * reduce it to exactly half the number. (Note that you may choose this reduced number in future operations.)
     *
     * Return the minimum number of operations to reduce the sum of nums by at least half.
     *
     * Input: nums = [5,19,8,1]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^7
     * @param nums
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int halveArray(int[] nums) {
        PriorityQueue<Double> pq = new PriorityQueue<>((o1, o2) -> Double.compare(o2, o1));
        double sum = 0;
        for (int x : nums) {
            pq.offer(x * 1.0);
            sum += x;
        }

        double half = sum * 1.0 / 2;
        int count = 0;
        while (!pq.isEmpty()) {
            double cur = pq.poll();
            pq.offer(cur / 2);
            sum -= cur / 2;
            count++;
            if (sum <= half) break;
        }
        return count;
    }
}
