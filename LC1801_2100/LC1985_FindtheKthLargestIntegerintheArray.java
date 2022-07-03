package LC1801_2100;
import java.util.*;
public class LC1985_FindtheKthLargestIntegerintheArray {
    /**
     * You are given an array of strings nums and an integer k. Each string in nums represents an integer without
     * leading zeros.
     *
     * Return the string that represents the kth largest integer in nums.
     *
     * Note: Duplicate numbers should be counted distinctly. For example, if nums is ["1","2","2"], "2" is the first
     * largest integer, "2" is the second-largest integer, and "1" is the third-largest integer.
     *
     * Input: nums = ["3","6","7","10"], k = 4
     * Output: "3"
     *
     * Constraints:
     *
     * 1 <= k <= nums.length <= 10^4
     * 1 <= nums[i].length <= 100
     * nums[i] consists of only digits.
     * nums[i] will not have any leading zeros.
     * @param nums
     * @param k
     * @return
     */
    // S1: PriorityQueue
    // time = O(nlogn), space = O(n)
    public String kthLargestNumber(String[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k < 0) return "";

        PriorityQueue<String> pq = new PriorityQueue<>((o1, o2) -> o1.length() == o2.length() ? o1.compareTo(o2) : o1.length() - o2.length());

        for (String num : nums) {
            pq.offer(num);
            if (pq.size() > k) pq.poll();
        }
        return pq.poll();
    }

    // S2: sort
    // time = O(nlogn), space = O(n)
    public String kthLargestNumber2(String[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k < 0) return "";

        Arrays.sort(nums, (o1, o2) -> o1.length() == o2.length() ? o1.compareTo(o2) : o1.length() - o2.length());
        return nums[nums.length - k];
    }

    // S3: quick select (最优解！）
    // time = O(n * k), space = O(n)
    public String kthLargestNumber3(String[] nums, int k) {
        return quickselect(nums, 0, nums.length - 1, k);
    }

    private String quickselect(String[] nums, int a, int b, int k) {
        String pivot = nums[a + (b - a) / 2];
        int i = a, j = b, t = a;
        while (t <= j) {
            if (helper(nums[t], pivot) < 0) swap(nums, t++, i++);
            else if (helper(nums[t], pivot) > 0) swap(nums, t, j--);
            else t++;
        }

        if (b - j >= k) return quickselect(nums, j + 1, b, k);
        if (b - i + 1 >= k) return pivot;
        return quickselect(nums, a, i - 1, k - (b - i + 1));
    }

    private void swap(String[] nums, int i, int j) {
        String t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    private int helper(String s, String t) {
        int m = s.length(), n = t.length();
        if (m != n) return m > n ? 1 : -1;
        return s.compareTo(t);
    }
}
