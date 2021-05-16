# LC138 Copy List with Random Pointer

标签（空格分隔）： LeetCode Java HashTable LinkedList

---

【题目】

    /**
     * A linked list of length n is given such that each node contains an additional random pointer, which could point
     * to any node in the list, or null.
     *
     * Construct a deep copy of the list.
     *
     * Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
     * Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
     *
     * Constraints:
     *
     * 0 <= n <= 1000
     * -10000 <= Node.val <= 10000
     * Node.random is null or is pointing to some node in the linked list.
     *
     * @param head
     * @return
     */

【思路】

deep copy类算法题的本质核心就是使用HashMap进行一对一的copy和关联，这里由于是LinkedList的copy，所以最简单直观的方法就是采用dummy node辅助来逐个node进行边copy边用HashMap关联建图。

【解答】

```java     
// time = O(n), space = O(n)
public Node copyRandomList(Node head) {
    // corner case
    if (head == null) return head;

    Node dummy = new Node(0);
    Node cur1 = head, cur2 = dummy; // 核心思想：采用dummy node！

    HashMap<Node, Node> map = new HashMap<>();

    while (cur1 != null) {
        map.putIfAbsent(cur1, new Node(cur1.val)); // copy cur1
        cur2.next = map.get(cur1); // 连接cur2
        if (cur1.random != null) {
            map.putIfAbsent(cur1.random, new Node(cur1.random.val)); // copy cur1.random
            cur2.next.random = map.get(cur1.random); // 连接cur2.random
        }
        cur1 = cur1.next;
        cur2 = cur2.next;
    }
    return dummy.next;
}
```
