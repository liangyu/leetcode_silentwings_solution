package LC2101_2400;
import java.util.*;
public class LC2307_CheckforContradictionsinEquations {
    /**
     * You are given a 2D array of strings equations and an array of real numbers values, where equations[i] = [Ai, Bi]
     * and values[i] means that Ai / Bi = values[i].
     *
     * Determine if there exists a contradiction in the equations. Return true if there is a contradiction, or false
     * otherwise.
     *
     * Note: Two floating point numbers are considered equal if their absolute difference is less than 10^-5.
     *
     * Input: equations = [["a","b"],["b","c"],["a","c"]], values = [3,0.5,1.5]
     * Output: false
     *
     * Input: equations = [["le","et"],["le","code"],["code","et"]], values = [2,5,0.5]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= equations.length <= 500
     * equations[i].length == 2
     * 1 <= Ai.length, Bi.length <= 5
     * Ai, Bi consist of lower case English letters.
     * equations.length == values.length
     * 0.0 < values[i] <= 20.0
     * @param equations
     * @param values
     * @return
     */
    // time = O(nlogn), space = O(n)
    HashMap<String, String> parent;
    HashMap<String, Double> dist;
    public boolean checkContradictions(List<List<String>> equations, double[] values) {
        parent = new HashMap<>();
        dist = new HashMap<>();

        int n = equations.size();
        for (int i = 0; i < n; i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);
            double c = values[i];

            parent.putIfAbsent(a, a);
            parent.putIfAbsent(b, b);

            if (!findParent(a).equals(findParent(b))) union(a, b, c);
            else {
                double val = dist.getOrDefault(a, 1.0) / dist.getOrDefault(b, 1.0);
                if (Math.abs(val - c) > 1e-5) return true;
            }
        }
        return false;
    }

    private String findParent(String x) {
        if (!x.equals(parent.getOrDefault(x, x))) {
            String p = parent.getOrDefault(x, x);
            parent.put(x, findParent(p));
            dist.put(x, dist.getOrDefault(x, 1.0) * dist.getOrDefault(p, 1.0));
        }
        return parent.getOrDefault(x, x);
    }

    private void union(String x, String y, double val) {
        String px = findParent(x);
        String py = findParent(y);
        parent.put(px, py);
        dist.put(px, val / dist.getOrDefault(x, 1.0) * dist.getOrDefault(y, 1.0));
    }
}
