package LC1501_1800;
import java.util.*;
public class LC1733_MinimumNumberofPeopletoTeach {
    /**
     * You are given an integer n, an array languages, and an array friendships where:
     *
     * There are n languages numbered 1 through n, languages[i] is the set of languages the ith user knows, and
     * friendships[i] = [ui, vi] denotes a friendship between the users ui and vi.
     * You can choose one language and teach it to some users so that all friends can communicate with each other.
     * Return the minimum number of users you need to teach.
     *
     * Note that friendships are not transitive, meaning if x is a friend of y and y is a friend of z,
     * this doesn't guarantee that x is a friend of z.
     *
     * Input: n = 2, languages = [[1],[2],[1,2]], friendships = [[1,2],[1,3],[2,3]]
     * Output: 1
     *
     * Input: n = 3, languages = [[2],[1,3],[1,2],[3]], friendships = [[1,4],[1,2],[3,4],[2,3]]
     * Output: 2
     *
     * Constraints:
     *
     * 2 <= n <= 500
     * languages.length == m
     * 1 <= m <= 500
     * 1 <= languages[i].length <= n
     * 1 <= languages[i][j] <= n
     * 1 <= ui < vi <= languages.length
     * 1 <= friendships.length <= 500
     * All tuples (ui, vi) are unique
     * languages[i] contains only unique values
     *
     * @param n
     * @param languages
     * @param friendships
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int minimumTeachings(int n, int[][] languages, int[][] friendships) {
        int m = languages.length;
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        // step 1: mapping each person's language into that person's hashset
        for (int i = 0; i < m; i++) { // O(m)
            map.put(i + 1, new HashSet<>());
            for (int l : languages[i]) { // O(n)
                map.get(i + 1).add(l);
            }
        }

        // step 2: check if each pair of the friendship shares the same language
        boolean[] share = new boolean[friendships.length]; // O(m)
        for (int j = 1; j <= n; j++) { // O(n)
            for (int i = 0; i < friendships.length; i++) { // O(m)
                if (map.get(friendships[i][0]).contains(j) && map.get(friendships[i][1]).contains(j)) {
                    share[i] = true;
                }
            }
        }

        // step 3: iterate through each language, set it as the one needs to teach, find how many people should be
        // taught for each language, then find the minimum among all of them
        int min = m;
        for (int i = 1; i <= n; i++) { // O(n)
            HashSet<Integer> set = new HashSet<>();
            for (int j = 0; j < friendships.length; j++) { // O(m)
                if (share[j]) continue;
                if (!map.get(friendships[j][0]).contains(i)) set.add(friendships[j][0]);
                if (!map.get(friendships[j][1]).contains(i)) set.add(friendships[j][1]);
            }
            min = Math.min(min, set.size());
        }
        return min;
    }
}
