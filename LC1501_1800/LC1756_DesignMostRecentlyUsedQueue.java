package LC1501_1800;
import java.util.*;
public class LC1756_DesignMostRecentlyUsedQueue {
    /**
     * Design a queue-like data structure that moves the most recently used element to the end of the queue.
     *
     * Implement the MRUQueue class:
     *
     * MRUQueue(int n) constructs the MRUQueue with n elements: [1,2,3,...,n].
     * int fetch(int k) moves the kth element (1-indexed) to the end of the queue and returns it.
     *
     * Input:
     * ["MRUQueue", "fetch", "fetch", "fetch", "fetch"]
     * [[8], [3], [5], [2], [8]]
     * Output:
     * [null, 3, 6, 2, 2]
     *
     * Constraints:
     *
     * 1 <= n <= 2000
     * 1 <= k <= n
     * At most 2000 calls will be made to fetch.
     *
     *
     * Follow up: Finding an O(n) algorithm per fetch is a bit easy. Can you find an algorithm with a better complexity
     * for each fetch call?
     * @param n
     */
    // S1: brute-force
    List<Integer> list;
    public LC1756_DesignMostRecentlyUsedQueue(int n) {
        list = new ArrayList<>();
        for (int i = 1; i <= n; i++) list.add(i);
    }

    // time = O(n), space = O(n)
    public int fetch(int k) {
        int val = list.get(k - 1);
        list.remove(k - 1);
        list.add(val);
        return val;
    }

    // S2: BIT
    class MRUQueue {
        BIT bit;
        int[] nums;
        int n;
        public MRUQueue(int n) {
            this.n = n;
            bit = new BIT(n + 2000);
            nums = new int[n + 2000];

            // init
            for (int i = 0; i < n; i++) {
                bit.update(i + 1, 1);
                nums[i] = i + 1;
            }
        }

        // time = O((logn)^2), space = O(n)
        public int fetch(int k) {
            int left = 0, right = n - 1;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (bit.query(mid + 1) < k) left = mid + 1;
                else right = mid;
            }
            nums[n] = nums[left];
            nums[left] = 0;
            bit.update(left + 1, -1); // disable
            bit.update(n + 1, 1); // enable
            n++;
            return nums[n - 1];
        }
    }

    class BIT {
        int n;
        int[] bitree;
        public BIT(int n) {
            this.n = n;
            this.bitree = new int[n + 1];
        }

        public void update(int x, int delta) {
            for (int i = x; i <= n; i += i & (-i)) {
                bitree[i] += delta;
            }
        }

        public int query(int x) {
            int res = 0;
            for (int i = x; i > 0; i -= i & (-i)) {
                res += bitree[i];
            }
            return res;
        }
    }

    // S3: double linked list
    class MRUQueue2 {
        Node[] skipNodes;
        Node head;
        int n, step;
        public MRUQueue2(int n) {
            this.n = n;
            this.step = (int) Math.sqrt(n) + 1;
            int m = n / step;
            skipNodes = new Node[m];
            head = new Node(0);
            Node cur = head;

            int j = 1, idx = 0;
            for (int i = n; i > 0; i--) {
                cur.next = new Node(i);
                cur.next.prev = cur;

                if (j == step) {
                    skipNodes[idx++] = cur.next;
                    j = 0;
                }
                cur = cur.next;
                j++;
            }
        }

        // time = O(sqrt(n)), space = O(n)
        public int fetch(int k) {
            int idx = 0, nodeIdx = n - k + 1;
            Node cur = head;

            while (nodeIdx >= step) {
                nodeIdx -= step;
                cur = skipNodes[idx];
                skipNodes[idx] = skipNodes[idx].prev;
                idx++;
            }

            while (nodeIdx-- > 0) cur = cur.next;

            // remove kth node (cur) from the linked list
            if (cur.next != null) cur.next.prev = cur.prev;
            cur.prev.next = cur.next;

            // insert cur after the head node
            cur.next = head.next;
            if (head.next != null) head.next.prev = cur;
            cur.prev = head;
            head.next = cur;
            return cur.val;
        }

        private class Node {
            private int val;
            private Node prev, next;
            public Node(int val) {
                this.val = val;
            }
        }
    }
}
