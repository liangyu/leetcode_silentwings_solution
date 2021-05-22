package LC001_300;
import java.util.*;
public class LC164_MaximumGap {
    /**
     * Given an integer array nums, return the maximum difference between two successive elements in its sorted form.
     * If the array contains less than two elements, return 0.
     *
     * Input: nums = [3,6,9,1]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^4
     * 0 <= nums[i] <= 10^9
     *
     *
     * Follow up: Could you solve it in linear time/space?
     * @param nums
     * @return
     */
    // S1: Sort
    // time = O(nlogn), space = O(1)
    public int maximumGap(int[] nums) {
        // corner case
        if (nums == null || nums.length < 2) return 0;

        Arrays.sort(nums);
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) max = Math.max(nums[i] - nums[i - 1], max);
        return max;
    }

    // S2: bucket sort
    // time = O(n), space = O(n)
    public static int maximumGap2(int[] nums) {
        // corner case
        if (nums == null || nums.length < 2) return 0;

        // step 1: find min and max of the array
        int len = nums.length, min = nums[0], max = nums[0];
        for (int num : nums) {
            max = Math.max(num, max);
            min = Math.min(num, min);
        }

        // step 2: find out the min and max values of each bucket
        // calculate the gap between buckets，n numbers have n - 1 gaps
        int gap = (int)Math.ceil((double)(max - min) / (len - 1));
        int[] bucketsMin = new int[len - 1]; // the min value of each bucket
        int[] bucketsMax = new int[len - 1]; // the max value of each bucket
        Arrays.fill(bucketsMax, Integer.MIN_VALUE); // init the min value of each bucket
        Arrays.fill(bucketsMin, Integer.MAX_VALUE); // init the max value of each bucket

        for (int num : nums) {
            if (num == min || num == max) continue; // if num is at both ends, then it won't fall into any bucket
            int bucket = (num - min) / gap; // calculate which bucket num will fall into
            // after figuring out which bucket to fill num, we update the min and max of that bucket
            bucketsMin[bucket] = Math.min(num, bucketsMin[bucket]);
            bucketsMax[bucket] = Math.max(num, bucketsMax[bucket]);
        }

        // step 3: find out the biggest difference between each bucket
        int res = 0, pre = min; // pre 记录上一个桶的max value，在起点位置时就是min
        for (int i = 0; i < len - 1; i++) {
            if (bucketsMin[i] == Integer.MAX_VALUE && bucketsMax[i] == Integer.MIN_VALUE) continue; // 空桶，直接pass
            res = Math.max(res, bucketsMin[i] - pre);
            pre = bucketsMax[i];
        }
        res = Math.max(res, max - pre); // 记得处理最后的终点，看是否有更大的gap
        return res;
    }
}
