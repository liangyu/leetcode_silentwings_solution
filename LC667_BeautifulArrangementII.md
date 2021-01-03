# LC667 Beautiful Arrangement II
标签（空格分隔）： LeetCode Array Math

---
    /**
     * Given two integers n and k, you need to construct a list which contains n different positive integers ranging
     * from 1 to n and obeys the following requirement:
     * Suppose this list is [a1, a2, a3, ... , an], then the list [|a1 - a2|, |a2 - a3|, |a3 - a4|, ... , |an-1 - an|]
     * has exactly k distinct integers.
     *
     * If there are multiple answers, print any of them.
     *
     * Input: n = 3, k = 1
     * Output: [1, 2, 3]
     *
     * Input: n = 3, k = 2
     * Output: [1, 3, 2]
     *
     * The n and k are in the range 1 <= k < n <= 104.
     *
     * @param n
     * @param k
     * @return
     */
     
     
【难点误区】

该题的难点之一就是寻找到构建的规律，想不到如何通过首尾搭配的方式成对构建出k - 1个distinct差值的array，然后最后将第k个distinct差值设置为1，然后继续最后停留的那个数字处在left还是right一边继续向前拿数字顺序填值来保证前后数差值绝对值为1，从而解决问题。


【解题思路】

这道题的难点在于寻找到规律，由于只要求输出任何一种可能的结果而不是all possible solution，所以产生了一种奇技淫巧——如何巧妙的构造这k个不同的绝对值差值。

这里采用的技巧是首先通过双指针，一头一尾的去成对一一搭配，比如1和n，2和n - 1等等，这样的话就出现了这么一个序列：

1, n, 2, n - 1, 3, n - 2, 4, n - 3, ......

这样构造的话，我们看到了一个显著的形成k个distinct difference的结果，从左到右，我们看到上述的array前后数字形成的差值的绝对值是：[n - 1, n - 2, n - 3, n - 4, n - 5, n - 6, n - 7, .......]，即上面8个数形成了7个完全不同的差值，这样做的话，当我们得到了k - 1个distinct difference integers之后，还剩下最后的一个distinct差值，我们可以把它设计成1，只需要把后面列举的所有数字，保证它们前后的差值都变成1即可。那到底怎么才能保证这点呢？

由于是使用双指针，所以上面形成k - 1个不同差值的做法是通过首尾left, right指针相向移动一对对产生的，那么每边移动都是移动1格，即left++ 或者 right--，也就是差值为1的移动，那么现在我们要保证接下来放入的数字前后差值保证为1的话，只要在当前产生的k - 1个不同差值的基础上，根据最后一个数字是来自left还是right这一边的情况，继续沿着原来前进的方向移动到填满整个数组即可。比如，如果当前最后一位是停在了left一边的数字上，比如4，那么后面我们要填的数字就是5， 6， 7，....；而相反，如果停在了right一边，比如n - 3，那么接下来我们要填入的数字就应该是n - 4, n - 5, n - 6来保证都是差值为1。所以到了这里，判断的症结点就在于如何判断最后停在了left还是right的一边。

由于最后跳出while loop完成了k - 1个distinct差值的构建时，index i 进行了++，也就是说如果最后一位停在了left一边的话，目前的index i 其实是比实际停的位置向前移动了一位，同理如果最后停在了right一边的话也是如此，这样的话在判断究竟当前是在left还是right一边的时候就会出现反转。因为我们看到在上述例子的偶数位上，比如 i = 0, 2, 4, 6, ...填入的是1， 2， 3， 4这种left一边的数字，而现在由于 i++，这样的话目前的 i 就停在了上述right 一边的奇数位上，但是我们当前填入的应该还是left一边的数字，即5， 6， 7， 8，........，所以实现了反转。同理，如果最后一位停在了right一边，而当前 i 却实际上停在了偶数位上，填入的却依然应该是right一边的数字，出现反转。搞清楚这个反转之后，整个问题也就迎刃而解了。

* 时间复杂度：O(n)，从左到右总共遍历了n个位置进行填值
* 空间复杂度：O(1)，不考虑output所占用的空间，即res[ ]的空间不计入的话，没有用到任何额外的空间

```java
// time = O(n), space = O(1)
public int[] constructArray(int n, int k) {
    // corner case
    if (n <= k) return null; // 题目给定限制条件 n > k

    int[] res = new int[n];
    // 1, n, 2, n - 1, 3, ...
    int i = 0, left = 1, right = n;
    while (i < k) {
        res[i++] = left++;
        if (i < k) res[i++] = right--;
    }
    if (i % 2 == 0) { // 已经找到 k - 1个distinct之后，下面的只要保持前后两个数差值为1作为第k个distinct差值即可！！！
        // 如果当前i是偶数位，那么之前最后一位摆放的都应该是right的数字，这时依然要继续放right
        while (i < res.length) res[i++] = right--;
    } else {
        // 同样，如果当前是奇数位，由于上一位放的是left的数字，那么这时后面都要放left，使得差值为1
        while (i < res.length) res[i++] = left++; 
    }
    return res;
}
```