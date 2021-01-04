# LC42 Trapping Rain Water
标签（空格分隔）： LeetCode Two_Pointers

---
    /**
     * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much
     * water it can trap after raining.
     *
     *      0,1,0,2,1,0,1,3,2,1,2,1
     *
     *                    *
     *            *       * *   *
     *        *   * *   * * * * * *
     *      0 1 2 3 4 5 6 7 8 9 0 1
     *      l                     r
     *
     * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * Output: 6
     *
     * Input: height = [4,2,0,3,2,5]
     * Output: 9
     *
     * Constraints:
     *
     * n == height.length
     * 0 <= n <= 3 * 104
     * 0 <= height[i] <= 105
     *
     * @param height
     * @return
     */


【难点误区】

该题最大的难点在于通过对题目的观察发现解题思路，想到通过双指针left, right，以及一边走一边track左边柱子的最高值leftMax和右边柱子的最高值rightMax，根据木桶短板效应，当前位置上能否储水以及储水量的多少由当前的高度cur与min(leftMax, rightMax)的大小决定。而leftMax与rightMax则需要根据当前cur = height[left] 或者height[right]来实时更新，计算短板那边的储水量，并进而继续让那一边向中间移动直到左右相遇，遍历完整个高度数组为止。遇到相等的情况，移动任何一边来打破平衡，也是这道题需要考量的一点。


【解题思路】

这道题用单调栈和双指针都可以做，但是双指针是最优解！

这道题的难点在于从题目特征里面观察出解题的思路来，即木桶短板效应！简单来说，就是站在当前的位置 i 上，我们就看“这个位置”上能否储水，依靠的判断是什么？

                         *
                 *     * *     *
             *   * *   * * * * * *
           0 1 2 3 4 5 6 7 8 9 0 1
                 l       r      

就上图这个情况，如果我们现在假设 l 代表leftMax，r 代表 rightMax，那么现在这种情况leftMax < rightMax，根据木桶短板理论，如果leftMax和rightMax之间4， 5， 6三个位置上没有任何障碍物的话，它们三者所能装的水都是leftMax，也就是min(leftMax, rightMax)的值。那么现在如果4， 5， 6三个位置上有障碍物的话，我们称之为cur，那么就可以分三种情况来讨论：

1. 如果cur < leftMax => water += leftMax - cur，比如4这个位置上 => water += 2 - 1 = 1，即水量 + 1。
2. 如果cur == leftMax => water += leftMax - cur = 0，比如到了6这个位置上，则水量不变。
3. 如果cur > leftMax => 比如可以到达7这个位置上，那么7的上方没有水可以储存，水量依然不变，但是leftMax则需要同时进行更新。

同样的，如果我们刚才讨论的情况变成是leftMax > rightMax，那么很简单，我们对等的把rightMax看成是刚才前面的leftMax，然后同样按照上述三种情况来讨论即可，只不过上面讨论leftMax的情况时，我们移动的方向是从左往右，即left++，而这里是从右往左，即right--。无论是左还是右，我们移动的标准始终是选择在min(leftMax, rightMax)的一边，即左右最高柱子较小值的一边，原因就在于木桶短板决定了储水量的大小，而不是高的那边来决定的。

那最后剩下的一个问题就是如果leftMax == rightMax怎么办？？？根据木桶短板，我们可以想象当相等的情况出现时，其实无论left++还是right--，效果都是一样的，因为当前没有一长一短，根据哪边作为短板来进行接下来的比照计算都没有问题，最终的效果就是选择一边来移动从而打破平衡，将双指针移动进行下去。同样的思路也被用在了LC75 Sort Colors以及LC81 Search in Rotated Sorted Array II之中。

* 时间复杂度：O(n)，两边向中间双指针移动遍历所有高度一遍
* 空间复杂度：O(1)，这里只借助了一些变量和2个指针，没有其他任何额外空间

```java     
// S1: Two Pointers
// time = O(n), space = O(1)
public int trap(int[] height) {
    // corner case
    if (height == null || height.length == 0) return 0;

    int len = height.length;
    int left = 0, right = len - 1;
    int leftMax = 0, rightMax = 0;
    int res = 0;

    while (left < right) { // 当左右相遇则表示已经遍历完整个数组，跳出loop
    	  // 这里写成 <= 也行，放在left或者right都没有问题，效果是一样的
        if (height[left] < height[right]) { // 左矮右高，计算并移动左边
            leftMax = Math.max(leftMax, height[left]); // 实时更新leftMax
            res += leftMax - height[left];
            left++;
        } else { // 相等的情况在此也一起归入右边，移动右指针
            rightMax = Math.max(rightMax, height[right]); // 实时更新rightMax
            res += rightMax - height[right];
            right--;
        }
    }
    return res;
}

```