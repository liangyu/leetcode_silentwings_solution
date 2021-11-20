package LC901_1200;

public class LC990_SatisfiabilityofEqualityEquations {
    /**
     * You are given an array of strings equations that represent relationships between variables where each string
     * equations[i] is of length 4 and takes one of two different forms: "xi==yi" or "xi!=yi".Here, xi and yi are
     * lowercase letters (not necessarily different) that represent one-letter variable names.
     *
     * Return true if it is possible to assign integers to variable names so as to satisfy all the given equations, or
     * false otherwise.
     *
     * Input: equations = ["a==b","b!=a"]
     * Output: false
     *
     * Constraints:
     *
     * 1 <= equations.length <= 500
     * equations[i].length == 4
     * equations[i][0] is a lowercase letter.
     * equations[i][1] is either '=' or '!'.
     * equations[i][2] is '='.
     * equations[i][3] is a lowercase letter.
     * @param equations
     * @return
     */
    // time = O(n), space = O(1)
    int[] parent;
    public boolean equationsPossible(String[] equations) {
        parent = new int[26];
        for (int i = 0; i < 26; i++) parent[i] = i;

        for (String eq : equations) { // O(n)
            if (eq.charAt(1) == '=') {
                int a = eq.charAt(0) - 'a';
                int b = eq.charAt(3) - 'a';
                if (findParent(a) != findParent(b)) union(a, b);
            }
        }

        for (String eq : equations) {
            if (eq.charAt(1) == '!') {
                int a = eq.charAt(0) - 'a';
                int b = eq.charAt(3) - 'a';
                if (findParent(a) == findParent(b)) return false;
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
/**
 * a != b, a == b
 * 把所有的等号放前面，先聚集起来，再处理不等号，看能否导出矛盾
 * trick: 先聚类，再看有无矛盾 -> two pass
 */