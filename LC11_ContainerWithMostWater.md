# LC11 Container With Most Water
标签（空格分隔）： LeetCode TwoPointers Array

---
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


【难点误区】

本题唯一的难点就是双指针解法情况下，考虑到底移动哪一端才有可能带来max值的更新。通过观察总结下来的原理就是木桶短板理论，谁小移谁，储水量由短板一边的高度决定，计算当前储水量并更新max后，移动短板一边的指针来遍历完整个高度的数组。


【解题思路】

本题是LC42 Trapping Rain Water的简化版，可以对照起来看，用到的思想是类似的，通过左右两个指针相向移动，在移动过程中比较两个指针所在位置的高度，谁小移谁，因为木桶短板效应，能储存的水量由短板所在的一边高度决定。在这题中，宽度永远是right - left，而高度则是由短板高度决定，边走边算并更新max的值即可，而移动的总是出现短板的一边，因为如果移动的是长板一端的话，那么不但当前宽度right - left会减小，同时如果移动后长板变得更长或者跟当前短板一样短，则储水量依然会由当前短板高度决定，并由于宽度变小而变得比现在更小；如果变得比当前短板更短，那么储水量则会因为宽度和短板都变小而愈加变得更小，所以只有移动短板的一边才有可能因为短板的高度变大来抵消宽度缩窄带来的负面影响，而出现比当前储水量更大的结果。


```java     
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
```
