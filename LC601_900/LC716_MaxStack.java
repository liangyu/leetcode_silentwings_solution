package LC601_900;
import java.util.*;
public class LC716_MaxStack {
    /**
     * Design a max stack data structure that supports the stack operations and supports finding the stack's maximum element.
     *
     * Implement the MaxStack class:
     *
     * MaxStack() Initializes the stack object.
     * void push(int x) Pushes element x onto the stack.
     * int pop() Removes the element on top of the stack and returns it.
     * int top() Gets the element on the top of the stack without removing it.
     * int peekMax() Retrieves the maximum element in the stack without removing it.
     * int popMax() Retrieves the maximum element in the stack and removes it. If there is more than one maximum element,
     * only remove the top-most one.
     *
     * Input
     * ["MaxStack", "push", "push", "push", "top", "popMax", "top", "peekMax", "pop", "top"]
     * [[], [5], [1], [5], [], [], [], [], [], []]
     * Output
     * [null, null, null, null, 5, 5, 1, 5, 1, 5]
     *
     * Constraints:
     *
     * -10^7 <= x <= 10^7
     * At most 104 calls will be made to push, pop, top, peekMax, and popMax.
     * There will be at least one element in the stack when pop, top, peekMax, or popMax is called.
     *
     *
     * Follow up: Could you come up with a solution that supports O(1) for each top call and O(logn) for each other call?
     */
    /** initialize your data structure here. */
    TreeMap<Integer, List<Node>> map;
    Node head, tail;
    public LC716_MaxStack() {
        map = new TreeMap<>();
        head = new Node(0);
        tail = new Node(0);
        head.next = tail;
        tail.prev = head;
    }

    // time = O(logn), space = O(n)
    public void push(int x) {
        Node node = new Node(x);
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
        if (!map.containsKey(x)) map.put(x, new ArrayList<>());
        map.get(x).add(node);
    }

    // time = O(logn), space = O(n)
    public int pop() {
        int val = tail.prev.val;
        deleteNode(tail.prev);
        int size = map.get(val).size();
        map.get(val).remove(size - 1);
        if (size == 1) map.remove(val);
        return val;
    }

    // time = O(1), space = O(1)
    public int top() {
        return tail.prev.val;
    }

    // time = O(logn), space = O(n)
    public int peekMax() {
        return map.lastKey();
    }

    // time = O(logn), space = O(n)
    public int popMax() {
        int val = map.lastKey();
        int size = map.get(val).size();
        List<Node> list = map.get(val);
        Node node = list.get(size - 1);
        list.remove(node);
        deleteNode(node);
        if (size == 1) map.remove(val);
        return val;
    }

    private void deleteNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    private class Node {
        private int val;
        private Node prev, next;
        public Node(int val) {
            this.val = val;
        }
    }
}
