package LC1801_2100;
import java.util.*;
public class LC1993_OperationsonTree {
    /**
     * You are given a tree with n nodes numbered from 0 to n - 1 in the form of a parent array parent where parent[i]
     * is the parent of the ith node. The root of the tree is node 0, so parent[0] = -1 since it has no parent. You want
     * to design a data structure that allows users to lock, unlock, and upgrade nodes in the tree.
     *
     * The data structure should support the following functions:
     *
     * Lock: Locks the given node for the given user and prevents other users from locking the same node. You may only
     * lock a node if the node is unlocked.
     * Unlock: Unlocks the given node for the given user. You may only unlock a node if it is currently locked by the
     * same user.
     * Upgrade: Locks the given node for the given user and unlocks all of its descendants. You may only upgrade a node
     * if all 3 conditions are true:
     * The node is unlocked,
     * It has at least one locked descendant (by any user), and
     * It does not have any locked ancestors.
     * Implement the LockingTree class:
     *
     * LockingTree(int[] parent) initializes the data structure with the parent array.
     * lock(int num, int user) returns true if it is possible for the user with id user to lock the node num, or false
     * otherwise. If it is possible, the node num will become locked by the user with id user.
     * unlock(int num, int user) returns true if it is possible for the user with id user to unlock the node num, or
     * false otherwise. If it is possible, the node num will become unlocked.
     * upgrade(int num, int user) returns true if it is possible for the user with id user to upgrade the node num, or
     * false otherwise. If it is possible, the node num will be upgraded.
     *
     * Input
     * ["LockingTree", "lock", "unlock", "unlock", "lock", "upgrade", "lock"]
     * [[[-1, 0, 0, 1, 1, 2, 2]], [2, 2], [2, 3], [2, 2], [4, 5], [0, 1], [0, 1]]
     *
     * Constraints:
     *
     * n == parent.length
     * 2 <= n <= 2000
     * 0 <= parent[i] <= n - 1 for i != 0
     * parent[0] == -1
     * 0 <= num <= n - 1
     * 1 <= user <= 10^4
     * parent represents a valid tree.
     * At most 2000 calls in total will be made to lock, unlock, and upgrade.
     */
    HashMap<Integer, Integer> map; // record the locking node
    HashMap<Integer, List<Integer>> graph1;
    HashMap<Integer, Integer> graph2;
    public LC1993_OperationsonTree(int[] parent) {
        map = new HashMap<>();
        graph1 = new HashMap<>();
        graph2 = new HashMap<>();

        // build graph
        for (int i = 0; i < parent.length; i++) {
            graph1.putIfAbsent(parent[i], new ArrayList<>());
            graph1.get(parent[i]).add(i);
            graph2.put(i, parent[i]);
        }
    }

    public boolean lock(int num, int user) {
        if (map.containsKey(num)) return false;
        map.put(num, user);
        return true;
    }

    public boolean unlock(int num, int user) {
        if (!map.containsKey(num)) return false;
        if (map.get(num) != user) return false;
        map.remove(num);
        return true;
    }

    public boolean upgrade(int num, int user) {
        if (map.containsKey(num)) return false;
        if (!checkParent(num)) return false;

        List<Integer> list = new ArrayList<>();
        checkChildren(num, list);
        if (list.size() == 0) return false;
        for (int node : list) map.remove(node);
        map.put(num, user);
        return true;
    }

    private void checkChildren(int cur, List<Integer> list) {
        if (!graph1.containsKey(cur)) return;
        for (int next : graph1.get(cur)) {
            if (map.containsKey(next)) list.add(next);
            checkChildren(next, list);
        }
    }

    private boolean checkParent(int cur) {
        if (!graph2.containsKey(cur)) return true;

        int p = graph2.get(cur);
        if (map.containsKey(p)) return false;
        if (!checkParent(p)) return false;
        return true;
    }
}
