# LC658 Find K Closest Elements
标签（空格分隔）： LeetCode Java BinarySearch

---
    /**
     * Given a sorted integer array arr, two integers k and x, return the k closest integers to x in the array.
     * The result should also be sorted in ascending order.
     *
     * An integer a is closer to x than an integer b if:
     *
     * |a - x| < |b - x|, or
     * |a - x| == |b - x| and a < b
     *
     * Input: arr = [1,2,3,4,5], k = 4, x = 3
     * Output: [1,2,3,4]
     *
     * Constraints:
     *
     * 1 <= k <= arr.length
     * 1 <= arr.length <= 10^4
     * arr is sorted in ascending order.
     * -10^4 <= arr[i], x <= 10^4
     *
     * @param arr
     * @param k
     * @param x
     * @return
     */


【难点误区】

本题的难点主要有以下几点：

1. 对于S1的传统B.S.模板做法来说，主要出错的地方在于定出closest之后，在之后遍历寻找k个的时候，由于可能存在一边先出界而另一边尚未出界的情况，所以如果在遍历中用k--来追踪loop的话，很容易会在最后不符合loop条件的最后一次check中将k多减一次而造成漏解。这个时候就要将k++，或者直接在loop里对k进行操作，而不是在check loop条件时对其进行操作。
2. S2的k + 1 size window做法，则比较难想，其要点是将window的左端点可能取值的范围加上后面的k-size变成一个k + 1 size的window来进行考虑，就左端点来进行二分查找，从而最终定出最后结果k-size window的左端点，包括自己的往后k个就是答案。其中，一定要特别注意 start + k出界的情况，必须在前面check挡住，以免报错。


【解题思路】

本题2种做法，S1就是传统的用B.S.模板去做，先找到closest的位置，不管夹没夹住，都在post-processing里定位出这个closest position一定在start或者end处，然后就开始进行谁近加谁，然后相应作出移动。这里要注意的是由于在一边出界之后，可能仍然没达到k个closest的解，所以依然要在出loop后继续从未出界的另一头里继续填充到k个，也就是说k可能在接下来要继续沿用直至填满k个，所以在上面这个while loop里就要特别注意k的变化，如果使用k--来loop，那么最后一次check之后，即使进不了loop，k也会再次 - 1，导致接下来在另一头中填充剩下的k个时会漏掉一个解，因此如果是while (k-- > 0)这么写的话，一定要在出loop后，check k的值，如果k >= 0，表示下面仍然要从未出界的一头继续填充，则 k++；否则如果k < 0，表示已经找满k个，这个时候就不用再去未出界的一头去填充，而是直接排序后输出res list。

方法2采用k + 1 size的window去处理，能实现O(logn)的二分排序时间复杂度。其原理是只针对左端点来进行讨论，把左端点可能取的值的范围用二分定出来，即start在这里表示k + 1 size window左端点最小可以取的值，而end表示左端点最大可以取的值。这样来看的话，由于最后我们要求出的解是k个，所以左端点最大可能可以取到 arr.length - k这个位置，这样才能保证一定能从这个左端点开始形成一个k-size的window作为最终的解。

有了这个思想的话，接下来就是应用二分法来找到最终k-size window的左端点。同样利用B.S.二分模板，直到左右相邻。这个时候出loop之后要做post-processing来决定究竟是取start还是end来作为最终k-size window的左端点。在检验两点与x之间区间大小之前，由于要check arr[start + k]，我们必须要保证当前 start + k没有越界。之所以上面while loop里不用check是因为我们有start + 1 < end把非法越界的情况给挡住了，所以不用check，而在这里如果出现上面while loop根本没有进入而直接来到这里并未做出界check的话，就会出现index out of boundary的报错，所以一定要先check是否出界，出界的话即表明上面while loop的情况也未进入，直接就从下面原始的start开始拿出k个即可。

```
/**
 *  S2: K + 1 size window
 *
 *      |__________|     k + 1个元素的window，一定有一个不合格的，在头部或者尾部，离x远的不合格
 *           x
 *      ==> 真正k-size的window的右端点不会超过此时k + 1 size的window的右端点，
 *      而左端点也不会越过此时k + 1 size window的左端点
 *      这样就可以砍掉一半的空间，最后肯定有个收敛值，肯定有解
 */
```


```java
// S1: B.S.
// time = O(nlogn), space = O(1)
public List<Integer> findClosestElements(int[] arr, int k, int x) {
    List<Integer> res = new ArrayList<>();
    // corner case
    if (arr == null || arr.length == 0) return res;

    int start = 0, end = arr.length - 1;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (arr[mid] <= x) start = mid;
        else end = mid;
    }
    while (k > 0 && start >= 0 && end < arr.length) {
        if (Math.abs(arr[start] - x) <= Math.abs(arr[end] - x)) res.add(arr[start--]);
        else res.add(arr[end++]);
        k--;
    }

    if (start < 0 && end < arr.length) {
        while (k-- > 0) res.add(arr[end++]);
    } else if (end >= arr.length && start >= 0) {
        while (k-- > 0) res.add(arr[start--]);
    }
    Collections.sort(res);
    return res;
}
```
```java
// S2: k + 1 size window (最优解！！！）
// time = O(logn), space = O(1)
public List<Integer> findClosestElements2(int[] arr, int k, int x) {
    List<Integer> res = new ArrayList<>();
    // corner case
    if (arr == null || arr.length == 0) return res;

    int start = 0, end = arr.length - k; // start, end指的都是左端点的两端取值范围
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (x - arr[mid] > arr[mid + k] - x) { // 左半部分比右半部分多，所以要右移start = mid
            start = mid;
        } else { // 右半部分比左半部分多，所以要左移，end = mid。注意相等的情况，依然是左移，因为同样距离，取小值更优先！
            end = mid;
        }
    }

    // post-processing，跳出loop后再次比较左右两端点来判断哪个作为最后k-size window的左端点
    if (start + k < arr.length && x - arr[start] > arr[start + k] - x) { // 注意start + k 不能越界！！！
        start = end; // 左半部分大，那么明显窗口要右移，选择end来作为左端点；反之选择start作为左端点。
    }

    // 输出从start开始的k个元素一定是正确解
    for (int i = start; i < start + k; i++) {
        res.add(arr[i]);
    }
    return res;
}
```
