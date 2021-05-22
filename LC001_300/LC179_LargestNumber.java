package LC001_300;
import java.util.*;
public class LC179_LargestNumber {
    /**
     * Given a list of non-negative integers nums, arrange them such that they form the largest number.
     *
     * Note: The result may be very large, so you need to return a string instead of an integer.
     *
     * Input: nums = [10,2]
     * Output: "210"
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // time = O(nlogn), space = O(n)
    public String largestNumber(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return "";

        String[] res = new String[nums.length];
        for (int i = 0; i < nums.length; i++) res[i] = String.valueOf(nums[i]);
        Arrays.sort(res, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String s1 = o1 + o2;
                String s2 = o2 + o1;
                return s2.compareTo(s1);
            }
        });

        if (res[0].charAt(0) == '0') return "0";
        StringBuilder sb = new StringBuilder();
        for (String s : res) sb.append(s);
        return sb.toString();
    }
}
