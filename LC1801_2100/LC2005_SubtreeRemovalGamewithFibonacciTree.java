package LC1801_2100;

public class LC2005_SubtreeRemovalGamewithFibonacciTree {
    /**
     * A Fibonacci tree is a binary tree created using the order function order(n):
     *
     * order(0) is the empty tree.
     * order(1) is a binary tree with only one node.
     * order(n) is a binary tree that consists of a root node with the left subtree as order(n - 2) and the right
     * subtree as order(n - 1).
     * Alice and Bob are playing a game with a Fibonacci tree with Alice staring first. On each turn, a player selects
     * a node and removes that node and its subtree. The player that is forced to delete root loses.
     *
     * Given the integer n, return true if Alice wins the game or false if Bob wins, assuming both players play optimally.
     *
     * A subtree of a binary tree tree is a tree that consists of a node in tree and all of this node's descendants.
     * The tree tree could also be considered as a subtree of itself.
     *
     * Input: n = 3
     * Output: true
     *
     * Constraints:
     *
     * 1 <= n <= 100
     * @param n
     * @return
     */
    // time = O(1), space = O(1)
    public boolean findGameWinner(int n) {
        return n % 6 != 1;
    }
}
