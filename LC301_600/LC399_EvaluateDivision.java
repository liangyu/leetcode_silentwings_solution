package LC301_600;
import java.util.*;
public class LC399_EvaluateDivision {
    /**
     * You are given an array of variable pairs equations and an array of real numbers values, where
     * equations[i] = [Ai, Bi] and values[i] represent the equation Ai / Bi = values[i]. Each Ai or Bi is a string
     * that represents a single variable.
     *
     * You are also given some queries, where queries[j] = [Cj, Dj] represents the jth query where you must find the
     * answer for Cj / Dj = ?.
     *
     * Return the answers to all queries. If a single answer cannot be determined, return -1.0.
     *
     * Note: The input is always valid. You may assume that evaluating the queries will not result in division by zero
     * and that there is no contradiction.
     *
     * Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],
     * ["a","a"],["x","x"]]
     * Output: [6.00000,0.50000,-1.00000,1.00000,-1.00000]
     *
     * Constraints:
     *
     * 1 <= equations.length <= 20
     * equations[i].length == 2
     * 1 <= Ai.length, Bi.length <= 5
     * values.length == equations.length
     * 0.0 < values[i] <= 20.0
     * 1 <= queries.length <= 20
     * queries[i].length == 2
     * 1 <= Cj.length, Dj.length <= 5
     * Ai, Bi, Cj, Dj consist of lower case English letters and digits.
     * @param equations
     * @param values
     * @param queries
     * @return
     */
    // S1: DFS
    // time = O(m * n), space = O(n) m: number of queries, n: number of input equations
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        HashMap<String, List<Pair>> map = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            map.putIfAbsent(equations.get(i).get(0), new ArrayList<>());
            map.get(equations.get(i).get(0)).add(new Pair(equations.get(i).get(1), values[i]));
            map.putIfAbsent(equations.get(i).get(1), new ArrayList<>());
            map.get(equations.get(i).get(1)).add(new Pair(equations.get(i).get(0), 1.0 / values[i]));
        }

        List<Double> res = new ArrayList<>();
        for (int i = 0; i < queries.size(); i++) {
            if (!map.containsKey(queries.get(i).get(0)) || !map.containsKey(queries.get(i).get(1))) {
                res.add(-1.0);
                continue;
            }
            HashSet<String> visited = new HashSet<>();
            visited.add(queries.get(i).get(0));
            res.add(dfs(queries.get(i).get(0), queries.get(i).get(1), map, visited));
        }
        double[] ans = new double[res.size()];
        int k = 0;
        for (double x : res) ans[k++] = x;
        return ans;
    }

    private double dfs(String A, String B, HashMap<String, List<Pair>> map, HashSet<String> visited) {
        if (A.equals(B)) return 1.0; // 注意：string比较相等要用equals!!!

        if (map.containsKey(A)) {
            for (int i = 0; i < map.get(A).size(); i++) {
                String C = map.get(A).get(i).str;
                if (visited.contains(C)) continue; // 注意：去重！！！
                visited.add(C);
                double val1 = map.get(A).get(i).val;
                // check if C and B has distance
                double val2 = dfs(C, B, map, visited);
                // visited.remove(C); // 注意这里其实不需要回溯！！！
                // A -> B -> C -> D 如果所有C的path都没找到的话，也就没必要再走C了。
                if (val2 != -1) return val1 * val2;
            }
        }
        return -1;
    }

    private class Pair {
        private String str;
        private double val;
        public Pair(String str, double val) {
            this.str = str;
            this.val = val;
        }
    }

    // S2: Union Find
    // time = O(m + n) * logn, space = O(n)
    HashMap<String, String> parent;
    HashMap<String, Double> vals;
    public double[] calcEquation2(List<List<String>> equations, double[] values, List<List<String>> queries) {
        parent = new HashMap<>();
        vals = new HashMap<>();
        double[] res = new double[queries.size()];

        for (int i = 0; i < values.length; i++) {
            union(equations.get(i).get(0), equations.get(i).get(1), values[i]);
        }
        for (int i = 0; i < queries.size(); i++){
            String x = queries.get(i).get(0), y = queries.get(i).get(1);
            if (parent.containsKey(x) && parent.containsKey(y) && findParent(x) == findParent(y)) {
                res[i] = vals.get(x) / vals.get(y);
            } else res[i] = -1.0;
        }
        return res;
    }

    private void add(String x) {
        if (parent.containsKey(x)) return;
        parent.put(x, x);
        vals.put(x, 1.0);
    }

    private String findParent(String x) {
        String p = parent.getOrDefault(x, x);
        if (x != p) {
            String pp = findParent(p);
            vals.put(x, vals.get(x) * vals.get(p));
            parent.put(x, pp);
        }
        return parent.getOrDefault(x, x);
    }

    private void union(String x, String y, double v) {
        add(x);
        add(y);
        String px = findParent(x), py = findParent(y);
        parent.put(px, py);
        vals.put(px, v * vals.get(y) / vals.get(x));
    }
}
/**
 * 任何两点之间的路径是唯一的 => dfs
 * s -> t   s -> temp -> t
 * 找个中转站
 * parents: (x, root(x)), vals:(x, x/root(x)), for example, a / b = 2.0, we will have parents(a, b), vals(a, 2.0),
 * both parents and vals have the numerator (which is 'a' here)
 * so that we can search for it and get the value a / parents(a) = vals.get(a) which is a / b = 2.0.
 *
 * find the root
 * parents(x, p) = vals(x), x / p = vals(x), parents(p, pp) = vals(p), p / pp = vals(pp),
 * when we are looking for the root, we are doing a path compression here
 * parents(x, pp)  = vals(x) / vals(pp) =  (x / p) * (p / pp) = vals(x) * vals(p)
 * For example, a / b = 2.0, b / c = 3.0,  parents(a, b) = 2.0,  parents(b, c) = 3.0,
 * parents(a, c) = 2.0 * 3.0 = 6.0, now vals(a) = 6.0
 * So along the way, we get all the value of a / x where x is the parent of x all the way to root.
 * In the end, only a / root(x) is saved. This is path compression.
 * It's like putting a directly under the root(x)
 * x / px = vals.get(x), y / py = vals.get(y), so px / py =  v * vals.get(y) / vals.get(x)
 */
