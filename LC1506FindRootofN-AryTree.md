# LC1506 Find Root of N-Ary Tree
标签（空格分隔）： LeetCode Hash 

---
    /**
     * You are given all the nodes of an N-ary tree as an array of Node objects, where each node has a unique value.
     *
     * Return the root of the N-ary tree.
     *
     * Constraints:
     *
     * The total number of nodes is between [1, 5 * 104].
     * Each node has a unique value.
     *
     *
     * Follow up:
     * Could you solve this problem in constant space complexity with a linear time algorithm?
     *
     * @param tree
     * @return
     */


【难点误区】

本题最大难点在于如何使用通过一个变量sum来找出root，以实现constant space。关键之处就在于，root与其他node相比，它是没有parent的，也就是说root只能自己作为parent，而无法成为child。那么无论用何种方法去解决这个问题，利用的都是这点特性，即root只做parent,不做child。回到最优解上来，因为只有root拥有这个特性，所以我们可以把所有能作为parent的node值加起来，再把所有能作为child的node值减去，剩下的值一定就是root对应的值，从而遍历一遍所有node就能根据这个值把root找出来，前提是所有node的val都是unique的，即不存在duplicate。否则的话，只能通过S1 HashSet的做法来解决，而不能通过这种变量加减的做法来得到root。

【解题思路】

本题S1: HashSet的方法比较容易想到，所谓的root就是入度为0的点，也就是说root只能做为parent，没有可能成为child。那么我们可以遍历整棵树，将每个node的children里的每个node存入HashSet，然后再遍历一遍所有的node，只要有node不在这个HashSet里，就表明这个node不曾作为child存入，因此一定是root。

本题最大的难点在于是用constant space去作出S2的最优解，代替HashSet的方案就是通过一个变量sum，先把所有遍历的node的值累加起来，同时再把每个node的children的值减去。除了root之外，每个node都能成为child而之后被减去，剩下的val肯定就属于root，因此只要再遍历一遍，找出哪个node的值与之相等，即是root。


```java     
// S1: HashSet
// time = O(n), space = O(n)
public Node findRoot(List<Node> tree) {
    // corner case
    if (tree == null || tree.size() == 0) return null;

    HashSet<Node> set = new HashSet<>();

    for (Node node : tree) {
        for (Node next : node.children) {
            set.add(next);
        }
    }

    for (Node node : tree) {
        if (!set.contains(node)) return node;
    }
    return null;
}

// S2: YOLO (You Only Look Once) 最优解!
// time = O(n), space = O(1)
public Node findRoot(List<Node> tree) {
    // corner case
    if (tree == null || tree.size() == 0) return null;

    int sum = 0;
    for (Node node : tree) {
        // the value is added as a parent node
        sum += node.val;
        for (Node next : node.children) {
            // the value is deducted as a child node.
            sum -= next.val;
        }
    }

    for (Node node : tree) {
        // the value of the root node is `sum`
        if (node.val == sum) return node;
    }
    return null;
}

// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;


    public Node() {
        children = new ArrayList<Node>();
    }

    public Node(int _val) {
        val = _val;
        children = new ArrayList<Node>();
    }

    public Node(int _val,ArrayList<Node> _children) {
        val = _val;
        children = _children;
    }
}
```
