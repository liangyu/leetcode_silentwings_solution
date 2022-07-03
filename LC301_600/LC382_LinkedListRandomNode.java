package LC301_600;
import java.util.*;
public class LC382_LinkedListRandomNode {
    /**
     * Given a singly linked list, return a random node's value from the linked list. Each node must have the same
     * probability of being chosen.
     *
     * Implement the Solution class:
     *
     * Solution(ListNode head) Initializes the object with the integer array nums.
     * int getRandom() Chooses a node randomly from the list and returns its value. All the nodes of the list should be
     * equally likely to be choosen.
     *
     * Input
     * ["Solution", "getRandom", "getRandom", "getRandom", "getRandom", "getRandom"]
     * [[[1, 2, 3]], [], [], [], [], []]
     * Output
     * [null, 1, 3, 2, 2, 3]
     *
     * Constraints:
     *
     * The number of nodes in the linked list will be in the range [1, 10^4].
     * -10^4 <= Node.val <= 10^4
     * At most 104 calls will be made to getRandom.
     *
     *
     * Follow up:
     *
     * What if the linked list is extremely large and its length is unknown to you?
     * Could you solve this efficiently without using extra space?
     */

    /** @param head The linked list's head.
    Note that the head is guaranteed to be not null, so it contains at least one node. */
    ListNode node;
    Random random;
    // time = O(1), space = O(1)
    public LC382_LinkedListRandomNode(ListNode head) {
        node = head;
        random = new Random();
    }

    /** Returns a random node's value. */
    // time = O(n), space = O(1)
    public int getRandom() {
        ListNode cur = node;
        int k = 0, x = 0;

        while (cur != null) {
            k++;
            int r = random.nextInt(k); // r:[0,k-1]
            if (r == 0) x = cur.val; // 0 ~ k-1, 1/k概率去替换x,这里选择0, r = 0是1/k中的一种可能
            cur = cur.next;
        }
        return x;
    }
}
/**
 * i = rand()%N => [0,N-1]
 * arr[i]
 * data stream -> 看过一眼就扔了
 * 没有办法全部存下来
 * 适用于data stream的均匀随机采样，不需要存下前面的数，而且随机数在任何时刻都是保证成立的
 * reservoir sampling
 * [1,2,34,5,56,7,7,67,467,4]
 * [a,b,c,d]
 * x = a
 * x = 50% x, 50% b => 50% a, 50% b
 * x = 2/3 x, 1/3 c => 1/3 a, 1/3 b, 1/3 c
 * x = 3/4 x, 1/4 d => 1/4 a, 1/4 b, 1/4 c, 1/4 d
 * k步之后，你都能得到一个对于前k步等概率的一个随机变量
 * H: x follows uniform distribution
 * p < 0.05 => x may not be uniform distribution
 * follow-up: 等概率从里面抽一个数出来 => 等概率抽k个数出来？
 * 奥妙在于，选中的库里有m个元素
 * [x1,x2,...,xm]  1/k prob replaced by new element
 * 替换谁呢？以1/m的概率抽中来替换掉
 * 以1/k的概率来决定是保留原来的数呢，还是以1/k的概率来替换成新的数
 * 如果抽中要替换新的数，那么在选中的m个数里，以1/m的概率抽中一个数，把原来这个数替换成新的数
 * 每步之后，这个pool里的随机变量的分量遵从m/k的概率被选中
 *
 * 蓄水池抽样算法：
 * 1    2   3   4   5
 * 1   1/2 1/3 1/4 1/5
 * 每个数都被随机到的概率是一样的
 * 第k个数，被选中的几率是1/n？
 * 1/k概率被选上，假设选的是k， 1/k * (k / (k+1)) * ...(n-1)/n = 1/n
 */