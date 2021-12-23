package LC1501_1800;
import javax.security.auth.login.AccountLockedException;
import java.util.*;
public class LC1797_DesignAuthenticationManager {
    /**
     * There is an authentication system that works with authentication tokens. For each session, the user will receive
     * a new authentication token that will expire timeToLive seconds after the currentTime. If the token is renewed,
     * the expiry time will be extended to expire timeToLive seconds after the (potentially different) currentTime.
     *
     * Implement the AuthenticationManager class:
     *
     * AuthenticationManager(int timeToLive) constructs the AuthenticationManager and sets the timeToLive.
     * generate(string tokenId, int currentTime) generates a new token with the given tokenId at the given currentTime
     * in seconds.
     * renew(string tokenId, int currentTime) renews the unexpired token with the given tokenId at the given currentTime
     * in seconds. If there are no unexpired tokens with the given tokenId, the request is ignored, and nothing happens.
     * countUnexpiredTokens(int currentTime) returns the number of unexpired tokens at the given currentTime.
     * Note that if a token expires at time t, and another action happens on time t (renew or countUnexpiredTokens), the
     * expiration takes place before the other actions.
     *
     * Input
     * ["AuthenticationManager", "renew", "generate", "countUnexpiredTokens", "generate", "renew", "renew",
     * "countUnexpiredTokens"]
     * [[5], ["aaa", 1], ["aaa", 2], [6], ["bbb", 7], ["aaa", 8], ["bbb", 10], [15]]
     * Output
     * [null, null, null, 1, null, null, null, 0]
     *
     * Constraints:
     *
     * 1 <= timeToLive <= 108
     * 1 <= currentTime <= 108
     * 1 <= tokenId.length <= 5
     * tokenId consists only of lowercase letters.
     * All calls to generate will contain unique values of tokenId.
     * The values of currentTime across all the function calls will be strictly increasing.
     * At most 2000 calls will be made to all functions combined.
     */
    // S1: HashMap
//    HashMap<String, Integer> map;
//    int ttl;
//    public LC1797_DesignAuthenticationManager(int timeToLive) {
//        map = new HashMap<>();
//        ttl = timeToLive;
//    }
//
//    // time = O(1), space = O(n)
//    public void generate(String tokenId, int currentTime) {
//        map.put(tokenId, currentTime + ttl);
//    }
//
//    // time = O(1), space = O(n)
//    public void renew(String tokenId, int currentTime) {
//        if (!map.containsKey(tokenId)) return;
//        if (map.get(tokenId) <= currentTime) {
//            map.remove(tokenId);
//            return;
//        } else {
//            map.put(tokenId, currentTime + ttl);
//        }
//    }
//
//    // time = O(n), space = O(n)
//    public int countUnexpiredTokens(int currentTime) {
//        int count = 0;
//        HashSet<String> set = new HashSet<>();
//        for (String key : map.keySet()) {
//            int et = map.get(key);
//            if (et <= currentTime) set.add(key);
//            else count++;
//        }
//        for (String key : set) map.remove(key);
//        return count;
//    }

    // S2: Double LinkedList + HashMap (LinkedHashMap) (最优解!)
    Node head, tail;
    HashMap<String, Node> map;
    int ttl;
    public LC1797_DesignAuthenticationManager(int timeToLive) {
        map = new HashMap<>();
        ttl = timeToLive;
        head = new Node("first", Integer.MAX_VALUE);
        tail = new Node("last", Integer.MAX_VALUE);
        head.next = tail;
        tail.prev = head;
    }

    // time = O(n), space = O(n)
    public void generate(String tokenId, int currentTime) {
        clear(currentTime);
        Node node = new Node(tokenId, currentTime + ttl);


        // insert node to tail
        node.next = tail;
        node.prev = tail.prev;
        tail.prev.next = node;
        tail.prev = node;
        map.put(tokenId, node);
    }

    // time = O(n), space = O(n)
    public void renew(String tokenId, int currentTime) {
        clear(currentTime);
        if (!map.containsKey(tokenId)) return;
        Node node = map.get(tokenId);
        remove(node);
        node.expTime = currentTime + ttl;

        // insert node to tail
        node.next = tail;
        node.prev = tail.prev;
        tail.prev.next = node;
        tail.prev = node;
        map.put(tokenId, node);
    }

    // time = O(1), space = O(n)
    public int countUnexpiredTokens(int currentTime) {
        clear(currentTime);
        return map.size();
    }

    private void clear(int currentTime) {
        while (head.next != tail && head.next.expTime <= currentTime) remove(head.next);
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
        map.remove(node.id);
    }

    private class Node {
        private String id;
        private int expTime;
        Node prev, next;
        public Node(String id, int expTime) {
            this.id = id;
            this.expTime = expTime;
        }
    }
}
