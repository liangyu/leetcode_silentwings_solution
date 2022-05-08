package LC001_300;
import java.util.*;
public class LC277_FindtheCelebrity {
    /**
     * Suppose you are at a party with n people (labeled from 0 to n - 1), and among them, there may exist one
     * celebrity. The definition of a celebrity is that all the other n - 1 people know him/her, but he/she does not
     * know any of them.
     *
     * Now you want to find out who the celebrity is or verify that there is not one. The only thing you are allowed
     * to do is to ask questions like: "Hi, A. Do you know B?" to get information about whether A knows B. You need to
     * find out the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic
     * sense).
     *
     * You are given a helper function bool knows(a, b) which tells you whether A knows B. Implement a function int
     * findCelebrity(n). There will be exactly one celebrity if he/she is in the party. Return the celebrity's label
     * if there is a celebrity in the party. If there is no celebrity, return -1.
     *
     * Input: graph = [[1,1,0],[0,1,0],[1,1,1]]
     * Output: 1
     *
     * Constraints:
     *
     * n == graph.length
     * n == graph[i].length
     * 2 <= n <= 100
     * graph[i][j] is 0 or 1.
     * graph[i][i] == 1
     *
     *
     * Follow up: If the maximum number of allowed calls to the API knows is 3 * n, could you find a solution without
     * exceeding the maximum number of calls?
     *
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int findCelebrity(int n) {
        // corner case
        if (n < 2) return -1;

        // step 1: find a candidate by one pass (make sure other people are not celebrity)
        int candidate = 0;
        for (int i = 0; i < n; i++) {
            if (knows(candidate, i)) candidate = i;
        }

        // step 2: make sure candidate is a celebrity by one pass
        for (int i = 0; i < n; i++) {
            if (i == candidate) continue;
            else if (!knows(i, candidate) || knows(candidate, i)) return -1;
        }
        return candidate;
    }

    // S2
    // time = O(n), space = O(1)
    public int findCelebrity2(int n) {
        int i = 0, j = n - 1;
        while (i < j) {
            if (knows(i, j)) i++;
            else j--;
        }

        for (i = 0; i < n; i++) {
            if (i == j) continue;
            if (!knows(i, j) || knows(j, i)) return -1;
        }
        return j;
    }

    /* The knows API is defined in the parent class Relation.
      boolean knows(int a, int b); */
    public boolean knows(int a, int b) {
        return true;
    }
}
