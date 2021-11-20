package LC901_1200;

public class LC1061_LexicographicallySmallestEquivalentString {
    /**
     * You are given two strings of the same length s1 and s2 and a string baseStr.
     *
     * We say s1[i] and s2[i] are equivalent characters.
     *
     * For example, if s1 = "abc" and s2 = "cde", then we have 'a' == 'c', 'b' == 'd', and 'c' == 'e'.
     * Equivalent characters follow the usual rules of any equivalence relation:
     *
     * Reflexivity: 'a' == 'a'.
     * Symmetry: 'a' == 'b' implies 'b' == 'a'.
     * Transitivity: 'a' == 'b' and 'b' == 'c' implies 'a' == 'c'.
     * For example, given the equivalency information from s1 = "abc" and s2 = "cde", "acd" and "aab" are equivalent
     * strings of baseStr = "eed", and "aab" is the lexicographically smallest equivalent string of baseStr.
     *
     * Return the lexicographically smallest equivalent string of baseStr by using the equivalency information from s1
     * and s2.
     *
     * Input: s1 = "parker", s2 = "morris", baseStr = "parser"
     * Output: "makkek"
     *
     * Constraints:
     *
     * 1 <= s1.length, s2.length, baseStr <= 1000
     * s1.length == s2.length
     * s1, s2, and baseStr consist of lowercase English letters.
     * @param s1
     * @param s2
     * @param baseStr
     * @return
     */
    // time = O(n), space = O(1)
    int[] parent;
    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        parent = new int[26];
        for (int i = 0; i < 26; i++) parent[i] = i;
        int n = s1.length();
        for (int i = 0; i < n; i++) {
            int a = s1.charAt(i) - 'a';
            int b = s2.charAt(i) - 'a';
            if (findParent(a) != findParent(b)) union(a, b);
        }

        StringBuilder sb = new StringBuilder();
        for (char c : baseStr.toCharArray()) {
            int p = findParent(c - 'a');
            sb.append((char)(p + 'a'));
        }
        return sb.toString();
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
