package LC901_1200;
import java.util.*;
public class LC1051_HeightChecker {
    /**
     * A school is trying to take an annual photo of all the students. The students are asked to stand in a single file
     * line in non-decreasing order by height. Let this ordering be represented by the integer array expected where
     * expected[i] is the expected height of the ith student in line.
     *
     * You are given an integer array heights representing the current order that the students are standing in. Each
     * heights[i] is the height of the ith student in line (0-indexed).
     *
     * Return the number of indices where heights[i] != expected[i].
     *
     * Input: heights = [1,1,4,2,1,3]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= heights.length <= 100
     * 1 <= heights[i] <= 100
     * @param heights
     * @return
     */
    // S1: sort
    // time = O(nlogn), space = O(n)
    public int heightChecker(int[] heights) {
        int[] nums = heights.clone();
        Arrays.sort(heights);

        int n = heights.length, count = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] != heights[i]) count++;
        }
        return count;
    }

    // S2: bucket sort
    // time = O(n), space = O(1)
    public int heightChecker2(int[] heights) {
        int[] buckets = new int[101];
        for (int x : heights) buckets[x]++;

        int count = 0, n = heights.length;
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (buckets[j] == 0) j++;
            if (heights[i] != j) count++;
            buckets[j]--;
        }
        return count;
    }
}
