# Toplogical Sort

我们将依赖关系表述成一条有向边。课程a需要先修课程b，那么就有一条边从a指向b。本题的本质就是在一个有向图中判断是否有环。如果有环，意味着循环依赖，就需要返回false。

在图论中，判断有向图是否有环，一般有DFS和BFS两种做法。

#### 解法1： DFS
DFS的基本思想是从任意一个未访问过的节点开始做DFS的遍历。如果在某条支路的遍历过程中（没有遍历到出度为0的端点）遇到了任何在这条支路中已经访问过的节点，那么就能判断成环。

注意，“遇到了任何在这条支路中已经访问过的节点”和“遇到了任何已经访问过的节点”，是不同的概念。比如：
```
1 -> 2 -> 3 -> 4 
          ^
5 -> 6 -> 7 -> 8 
          ^____|
```
我们从1开始依次访问1->2->3->4，然后遍历结束。然后从5开始依次访问5->6->7->3的时候，3已经被访问过了。但是这不会误判成环。因为3并不是在当前未完待续的支路中。我们再看5->6->7->8->7这条线路，此时的7已经被这条支路访问过，并且这条支路并没有走到底，这个时候就应该判断成环。

所以我们需要标记两种status[i]。如果节点i已经在其他遍历到底的支路中被访问过了，标记2.如果节点i是在当前未完待续的支路中被访问过了，标记1.只有在遍历过程中遇到了1，才算是判断有环。那么是什么时候标记1什么时候标记1呢？方法是：在某条DFS的路径上，第一次遇到的节点i的时候标记1.在回溯返回节点i的时候标记2（因为能成功返回的话，说明后续的节点都没有环，都是死胡同，此后任何任何入度指向这个节点i的话，我们都不用担心后续的遍历会遇到环）.

核心的dfs代码很简单：
```java
    private boolean containsCycle(List<List<Integer>> graph, int[] status, int cur) {
        if (status[cur] == 2) return false;
        if (status[cur] == 1) return true;

        status[cur] = 1;
        for (int next : graph.get(cur)) {
            if (containsCycle(graph, status, next)) return true;
        }
        status[cur] = 2;
        return false;
    }
```

#### 解法2： BFS
BFS的算法思想是拓扑排序：从外围往核心进发。我们每次在图中找入度为0的点，然后移除。如果最后没有入度为0的点，但是图中仍有点存在，那么这些剩下来的点一定是交错成环的。

核心的bfs代码:
```java
    int[] indegree = new int[numCourses];
    for (int[] p : prerequisites) {
        indegree[p[0]]++;
    }

    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < indegree.length; i++) {
        if (indegree[i] == 0) queue.offer(i);
    }

    int res = numCourses;
    while (!queue.isEmpty()) {
        int cur = queue.poll();
        res--;
        for (int[] p : prerequisites) {
            if (p[1] == cur) {
                indegree[p[0]]--;
                if (indegree[p[0]] == 0) queue.offer(p[0]);
            }
        }
    }
    return res == 0;
```

