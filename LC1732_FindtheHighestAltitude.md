# LC1732 Find the Highest Altitude

标签（空格分隔）： LeetCode Java 

---
    /**
     * There is a biker going on a road trip. The road trip consists of n + 1 points at different altitudes.
     * The biker starts his trip on point 0 with altitude equal 0.
     *
     * You are given an integer array gain of length n where gain[i] is the net gain in altitude between points i​​​​​​
     * and i + 1 for all (0 <= i < n). Return the highest altitude of a point.
     *
     * Input: gain = [-5,1,5,0,-7]
     * Output: 1
     *
     * Input: gain = [-4,-3,-2,-1,4,3,2]
     * Output: 0
     *
     * Constraints:
     *
     * n == gain.length
     * 1 <= n <= 100
     * -100 <= gain[i] <= 100
     *
     * @param gain
     * @return
     */
     
【难点误区】

送分题，唯一要注意的只有一点，max最小是0，不能是负数。

【解题思路】

逐个遍历比较求最值。


```java     
// time = O(n), space = O(1)
public int largestAltitude(int[] gain) {
    // corner case
    if (gain == null || gain.length == 0) return 0;

    int sum = 0, max = 0;
    for (int n : gain) {
        sum += n;
        max = Math.max(sum, max);
    }
    return max;
}
```
