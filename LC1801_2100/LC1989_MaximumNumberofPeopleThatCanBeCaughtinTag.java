package LC1801_2100;
import java.util.*;
public class LC1989_MaximumNumberofPeopleThatCanBeCaughtinTag {
    /**
     * You are playing a game of tag with your friends. In tag, people are divided into two teams: people who are "it",
     * and people who are not "it". The people who are "it" want to catch as many people as possible who are not "it".
     *
     * You are given a 0-indexed integer array team containing only zeros (denoting people who are not "it") and ones
     * (denoting people who are "it"), and an integer dist. A person who is "it" at index i can catch any one person
     * whose index is in the range [i - dist, i + dist] (inclusive) and is not "it".
     *
     * Return the maximum number of people that the people who are "it" can catch.
     *
     * Input: team = [0,1,0,1,0], dist = 3
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= team.length <= 10^5
     * 0 <= team[i] <= 1
     * 1 <= dist <= team.length
     * @param team
     * @param dist
     * @return
     */
    // S1: TreeSet
    // time = O(nlogn), space = O(n)
    public int catchMaximumAmountofPeople(int[] team, int dist) {
        // corner case
        if (team == null || team.length == 0 || dist < 0) return 0;

        int n = team.length;
        TreeSet<Integer> set = new TreeSet<>();
        TreeSet<Integer> set0 = new TreeSet<>();
        for (int i = 0; i < n; i++) { // O(nlogn)
            if (team[i] == 1) set.add(i);
            else set0.add(i);
        }

        int count = 0;
        for (int t : set) {
            if (helper(team, Math.max(0, t - dist), Math.min(n - 1, t + dist), set0)) count++;
        }
        return count;
    }

    private boolean helper(int[] teams, int left, int right, TreeSet<Integer> set0) {
        Integer ck = set0.ceiling(left);
        Integer fk = set0.floor(right);
        if (ck == null && fk == null) return false;
        if (ck != null && fk != null) {
            teams[Math.min(ck, fk)] = 1;
            set0.remove(Math.min(ck, fk));
            return true;
        }
        if (ck == null) {
            teams[fk] = 1;
            set0.remove(fk);
        } else {
            teams[ck] = 1;
            set0.remove(ck);
        }
        return true;
    }

    // S2: Two Pointers
    // time = O(n), space = O(1)
    public int catchMaximumAmountofPeople2(int[] team, int dist) {
        // corner case
        if (team == null || team.length == 0 || dist < 0) return 0;

        int n = team.length, j = 0, count = 0;
        for (int i = 0; i < n; i++) {
            if (team[i] == 1) {
                while (j < i - dist) j++; // j >= i - dist
                int end = Math.min(i + dist, n - 1);
                while (j <= end && team[j] == 1) j++;
                if (j <= end) {
                    count++;
                    j++; // catch the leftmost one and jump over it
                }
            }
        }
        return count;
    }
}
