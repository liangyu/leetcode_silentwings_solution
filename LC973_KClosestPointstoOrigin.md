# LC973 K Closest Points to Origin
标签（空格分隔）： LeetCode Java Sort Divide&Conquer

---
    /**
     * We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
     *
     * (Here, the distance between two points on a plane is the Euclidean distance.)
     *
     * You may return the answer in any order.
     * The answer is guaranteed to be unique (except for the order that it is in.)
     *
     * Input: points = [[1,3],[-2,2]], K = 1
     * Output: [[-2,2]]
     *
     * Input: points = [[3,3],[5,-1],[-2,4]], K = 2
     * Output: [[3,3],[-2,4]]
     *
     * Note:
     *
     * 1 <= K <= points.length <= 10000
     * -10000 < points[i][0] < 10000
     * -10000 < points[i][1] < 10000
     *
     * @param points
     * @param K
     * @return
     */

【难点误区】

本题S1 heap的解法相对简单直观，只需要考虑heap里装入的是什么，以及comparator怎么改写即可。而S2 quickSelect相对较难，主要难点在于选定最左边作为定出pivot的参照体后，start, end双指针相向移动出loop的边界条件究竟是什么。在这里记住这个套路和规律，相向移动时一定保证左右越过，即 i == j 时依然继续进入loop移动使得两者越过，而只有 i > j 保证越过时才能出loop，最终让进入小区域一端的 j 与最左边的start进行交换。从而最终的pivot = j。

【解题思路】

凡是求K个之类的题目，常规解法就2种：

1. k-size heap
2. quick select

对于k-size heap解法来说，只需要考虑把什么放入heap去比较，以及如何通过改写comparator去进行比较，然后只要往heap里丢，满k个之后，比较堆顶与后来元素的关系，考虑是否要pop出堆顶元素，然后放入新来的元素，最后留在heap里的k个元素或者堆顶元素就是答案。

对于quick select来说，基本模板就是input，start, end，以及idx，其中idx的范围是[0, k - 1]表示所要求的k个。然后通过quickSelect的helper函数，采用quickSort的partition策略，找出一个pivot，根据pivot与idx的大小关系来决定下一步。如果相等，则表示[0, pivot]就是所要求的区域；反之，则要move start = pivot + 1或者end = pivot - 1来recursion求解。

这其中最重要的部分就是partition的helper function，我们一般可以采用选择最左边的start作为pivot定位参照体，然后所有比其小的都放在最终pivot的左边，而比其大的都在最终pivot的右边。这样的话，左边就从start + 1开始，右边就从end开始，双指针i, j相向而动，一旦i遇到比points[start]大的就停住，而右边j一旦遇到比points[start]小的也停住。一旦i, j都停住之后，就可以互相swap，这样小的都在i的一边，大的都在j的一边，然后继续相向而行直到i和j左右越过。最终i会停留在从左到右第一个比points[start]大的值上面，而j会停留在从左到右最后一个比points[start]小的值上面。这个时候，由于points[start]在最左边，所以我们要和points[j]swap，这样points[start]， 也就是参照体就被挪到了j的位置上，而j所指的从左到右最后一个比points[start]小的值就会被挪到最左边。这时站在原来j的位置上，向左看全是比当前j位置上的points[start]值小的元素，向右看全是比points[start]大的元素，完美实现了以pivot为中心点的左右partition。

那i和j是否会出现相等的情况呢？答案是不会。因为假设i一路移动，只有当遇到比points[start]大的点或者越过j才会停下，因为我们控制loop的边界条件是 i <= j，即使i == j依然要进入loop。这个时候如果遇到j，肯定是在一个比points[start]大的位置上，因为上一轮结束而没有出loop，肯定是在完成了i, j位置上的值进行swap之后，这个时候j指向的是上一轮交换过来的points[i]，也就是一个比points[start]大的值，因此i就会停住在j目前的位置上。而紧接着下面j由于所处位置比points[start]要大，所以会j--，从而i, j左右越过而出loop，从而下面i > j而break出while loop。

但是还有一种可能，就是i一上来第一次就一路向右移动到了j，而此前j还没移动过，依然指向的是end的位置，这个时候分2种情况考虑:

1. 如果points[end]比points[start]大，就回到了刚才上面我们讨论的情况，满足条件，没有问题。
2. 如果Points[end] <= points[start]的话，那么i会继续++，越过j，从而下面跳出loop。这个时候我们可以知道，从左边start + 1开始的所有元素都是小于points[start]的，points[start]刚好是最大值，这时如果和points[j]，也就是points[end]进行swap，那刚好同样可以实现pivot = end的左边全是比其小的值，满足条件。

因此，综上所述，我们采用这种quick select的方案是正确的，即设置两头loop的边界条件为i <= j，一旦越过 i > j则跳出整个while loop。注意，我们不能设置两头推进loop的边界条件为 i < j，因为如果停留在i == j的话，一旦j是经由上一轮swap后当前指向的是比points[start]大的值的话，最后points[start]与points[j]进行swap，就会把比points[start]大的值交换到最左边去，就不满足partition的要求了，破坏了左边区域要求全部比points[start]小的要求，因此当i == j的时候依然要进loop继续移动，i++或者j--使这两指针越过。

```java
// S1: maxHeap
// time = O(nlogk), space = O(k)
public int[][] kClosest(int[][] points, int K) {
    // corner case
    if (points == null || points.length == 0 || points[0] == null || points[0].length == 0 || K <= 0) {
        return null;
    }

    int[][] res = new int[K][2];
    PriorityQueue<Pair> maxHeap = new PriorityQueue<>(K, (o1, o2) -> o2.dist - o1.dist);

    // 10^10 = 10 * 10^9 = 10 * 2^30 > 2^32
    for(int[] p : points) { // O(n)
        Pair pair = new Pair(p[0], p[1]);
        if (maxHeap.size() >= K) {
            if (maxHeap.peek().dist > pair.dist) { // O(logk)
                maxHeap.poll();
            }
        }
        maxHeap.offer(pair); // O(logk)
    }

    int i = 0;
    while (!maxHeap.isEmpty()) { // O(k)
        Pair p = maxHeap.poll();
        res[i][0] = p.x;
        res[i++][1] = p.y;
    }
    return res;
}

private class Pair {
    private int x;
    private int y;
    private int dist;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
        dist = x * x + y * y;
    }
}
```
```java
// S2: Divide & Conquer 最优解！！！
// time = O(n), space = O(n)
public int[][] kClosest2(int[][] points, int K) {
    // corner case
    if (points == null || points.length == 0 || points[0] == null || points[0].length == 0 || K <= 0) return new int[0][0];

    return quickSelect(points, 0, points.length - 1, K - 1);
}

private int[][] quickSelect(int[][] points, int start, int end, int idx) {
    if (start > end) return new int[0][0];

    int pivot = partition(points, start, end); // O(n)
    if (pivot == idx) return Arrays.copyOf(points, idx + 1); // O(k)
    return pivot < idx ? quickSelect(points, pivot + 1, end, idx) : quickSelect(points, start, pivot - 1, idx); // O(n)
}

private int partition(int[][] points, int start, int end) { // O(n)
    int[] p = points[start];
    int dist = p[0] * p[0] + p[1] * p[1];
    int i = start + 1, j = end; // start选定作为起始比较对象和最后被swap的对象，左边就从start + 1开始向内遍历

    while (true) {
        while (i <= j && points[i][0] * points[i][0] + points[i][1] * points[i][1] <= dist) i++; // == 既可以并入左边，也可以并入右边，都没有关系
        while (i <= j && points[j][0] * points[j][0] + points[j][1] * points[j][1] > dist) j--;

        if (i > j) break; // i, j 越过表明所有元素已经遍历完，可以跳出loop

        // points[i]与points[j]交换，保证比points[start]小的都在左边，而比points[start]大的都在右边
        int[] temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }

    // 最后break出loop，肯定就是i, j越过，由于是和最左边的points[start]进行swap，所以对应的swap对象是j，而不是i
    // 因为此刻j指向比points[start]小的那片区域，而i指向比points[start]大的那片区域，所以要和j进行swap
    points[start] = points[j];
    points[j] = p;
    return j;
}
```

