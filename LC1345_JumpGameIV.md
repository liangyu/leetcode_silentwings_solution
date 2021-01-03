# LC1345: Jump Game IV

标签（空格分隔）： LeetCode BFS

---

```java
// time = O(n), space = O(n)
public int minJumps(int[] arr) {
    // corner case
    if (arr == null || arr.length == 0) return 0;

    int n = arr.length;
    // step 1: build a graph of n nodes
    HashMap<Integer, List<Integer>> map = new HashMap<>();
    for (int i = 0; i < n; i++) {
        map.putIfAbsent(arr[i], new ArrayList<>());
        map.get(arr[i]).add(i);
    }

    Queue<Integer> queue = new LinkedList<>();
    HashSet<Integer> set = new HashSet<>();
    queue.offer(n - 1);
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[n - 1] = 0;

    while (!queue.isEmpty()) {
        int cur = queue.poll();
        if (cur - 1 >= 0 && dist[cur - 1] == Integer.MAX_VALUE) {
            dist[cur - 1] = dist[cur] + 1;
            queue.offer(cur - 1);
        }
        if (cur + 1 < n && dist[cur + 1] == Integer.MAX_VALUE) {
            dist[cur + 1] = dist[cur] + 1;
            queue.offer(cur + 1);
        }
        if (set.add(cur)) {
            for (int next : map.get(arr[cur])) {
                if (dist[next] == Integer.MAX_VALUE) {
                    dist[next] = dist[cur] + 1;
                    queue.offer(next);
                    set.add(next); 
                    // 相同的arr[i]只需要遍历一次就行，不用重复遍历，因为都聚集在同一个list
                }
            }
        }
    }
    return dist[0];
}
```
【解题思路】

1. 从后往前找，找到位置0即为终点，起点是最后的index = n - 1，dist[n - 1] = 0，因为不需要任何操作。
2. 先对arr[i]以及所有值为arr[i]的index进行HashMap建图，然后利用BFS从n - 1出发遍历三种可能的情况进行填值和入队的操作
3. 对于重复遍历的情况，利用HashSet进行查重。要注意的是，因为所有值为arr[i]的index都在map.get(arr[i])的list里，所以一次for loop就能访问所有的点，因此可以把访问过的全部加到HashSet里，下次就不需要再分别访问和加入到set里了，从而剪枝而节省时间！
