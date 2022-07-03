package LC601_900;

public class LC839_SimilarStringGroups {
    /**
     * Two strings X and Y are similar if we can swap two letters (in different positions) of X, so that it equals Y.
     * Also two strings X and Y are similar if they are equal.
     *
     * For example, "tars" and "rats" are similar (swapping at positions 0 and 2), and "rats" and "arts" are similar,
     * but "star" is not similar to "tars", "rats", or "arts".
     *
     * Together, these form two connected groups by similarity: {"tars", "rats", "arts"} and {"star"}.  Notice that
     * "tars" and "arts" are in the same group even though they are not similar.  Formally, each group is such that a
     * word is in the group if and only if it is similar to at least one other word in the group.
     *
     * We are given a list strs of strings where every string in strs is an anagram of every other string in strs. How
     * many groups are there?
     *
     * Input: strs = ["tars","rats","arts","star"]
     * Output: 2
     *
     * Input: strs = ["omv","ovm"]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= strs.length <= 300
     * 1 <= strs[i].length <= 300
     * strs[i] consists of lowercase letters only.
     * All words in strs have the same length and are anagrams of each other.
     * @param strs
     * @return
     */
    // time = O(n^2 * k + nlogn), space = O(n)
    int[] parent;
    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        int count = n;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (check(strs[i], strs[j])) {
                    if (findParent(i) != findParent(j)) {
                        union(i, j);
                        count--;
                    }
                }
            }
        }
        return count;
    }

    private boolean check(String a, String b) {
        if (a.equals(b)) return true;
        int n = a.length(), count = 0;
        for (int i = 0; i < n; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                count++;
                if (count > 2) return false;
            }
        }
        return true;
    }

    private int findParent(int x) {
        if (x != parent[x]) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }
}
