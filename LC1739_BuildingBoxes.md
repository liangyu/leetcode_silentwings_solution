# LC1739 Building Boxes

标签（空格分隔）： LeetCode Java Math BinarySearch

---
    /**
     * You have a cubic storeroom where the width, length, and height of the room are all equal to n units.
     * You are asked to place n boxes in this room where each box is a cube of unit side length.
     * There are however some rules to placing the boxes:
     *
     * You can place the boxes anywhere on the floor.
     * If box x is placed on top of the box y, then each side of the four vertical sides of the box y must either be
     * adjacent to another box or to a wall.
     * Given an integer n, return the minimum possible number of boxes touching the floor.
     *
     * Input: n = 3
     * Output: 3
     *
     * Input: n = 10
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= n <= 109
     * @param n
     * @return
     */

【难点误区】

本题最大的难点在于看出这些盒子堆叠的规律：要形成尽可能的密堆积，底面就要尽可能形成一个等腰三角形，然后在此基础上，如果还有盒子剩余，就当“零头”往后面拼，继续往下一个“大一圈”的等腰三角形发展。看出这个规律后，我们可以发现，找到这个尽可能大的等腰三角形的“腰边d”成为关键。由于是尽可能拼成等腰三角形，接下来的盒子再往后拼，所以可以把盒子总数做二分法，如果通过cal(mid)得到的底边过大，即 > n，那么我们需要缩小底边，移动right = mid - 1，反之就是底边过小，移动left = mid + 1。用这种写法，最后收敛的结果就是答案，因为这里不可能堆不出来，所以是return left。

那么现在的难点就是如何能找到这个等腰边d。根据数学公式推导，我们知道d = sqrt(2 * area)，得到一个比area小的最大值，然后找到根据两者之间的diff，把剩余的零头也按顺序加上。这么一做的话，我们不难发现每一列其实都是上一纵列去掉首元素的所有元素，所以我们只需要头元素 + 之后的surSum，因此可以从后往前，然后逐层累加即可。


【解题思路】

```
/**
 * 俯视图：
 *    3  2  1 (1)            4 3 3 2 1          5  4  3  1
 *    2  1 (1)               3 2 2 1            4  3  1
 *    1 (1)            =>    2 1 1         =>   3  1          => "后缀"重复计算
 *   (1)                     1                  1
 *    => 等腰三角形
 *    => 尽量在最底层构建一个最大的等腰三角形 => 尽量朝等腰三角形去发展 => 求底边数字是多少
 *    n => area = 13 => d = 4 + 3 => n'   n'太大 -> 缩小底边 => n' >= n => 典型二分搜值
 */
```


```java     
// time = O(logn * sqrt(n)), space = O(1)
public int minimumBoxes(int n) {  // O(logn)
    int left = 1, right = (int)1e9;

    while (left <= right) { // O(logn)
        int mid = left + (right - left) / 2;
        if (cal(mid) >= n) right = mid - 1; // O(d) = O(sqrt(n))
        else left = mid + 1;
    }
    return left;
}

// 等腰三角形 (1 + d) * d / 2 <= area
// d^2 < d * (d + 1) <= 2 * area
// => d < sqrt(2 * area)
private long cal(int area) {
    int d = (int)Math.sqrt(2 * area);
    while ((1 + d) * d / 2 > area) {
        d--;
    }
    long diff = area - (1 + d) * d / 2;
    long[] a = new long[d];
    for (int i = 0; i < d; i++) {
        a[i] = d - i; // a: 4, 3, 2, 1
    }
    for (int i = 0; i < diff; i++) { // 加上零头
        a[i] += 1;
    }

    long total = 0, sufsum = 0;
    for (int i = d - 1; i >= 0; i--) { // 从后往前算suffix sum
        sufsum += a[i];
        total += sufsum;
    }
    return total;
}
```
