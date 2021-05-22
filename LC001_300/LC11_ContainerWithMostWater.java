package LC001_300;
import java.util.*;
public class LC11_ContainerWithMostWater {
    /**
     * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical
     * lines are drawn such that the two endpoints of the line i is at (i, ai) and (i, 0). Find two lines, which,
     * together with the x-axis forms a container, such that the container contains the most water.
     *
     * Input: height = [1,8,6,2,5,4,8,3,7]
     * Output: 49
     *
     * Constraints:
     *
     * n = height.length
     * 2 <= n <= 3 * 104
     * 0 <= height[i] <= 3 * 104
     *
     * @param height
     * @return
     */
    // time = O(n), space = O(1)
    public int maxArea(int[] height) {
        // corner case
        if (height == null || height.length == 0) return 0;

        int left = 0, right = height.length - 1;
        int max = 0, area = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                area = (right - left) * height[left];
                left++; // 谁小移谁
            } else {
                area = (right - left) * height[right];
                right--;
            }
            max = Math.max(max, area);
        }
        return max;
    }
}
