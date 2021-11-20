package LC1201_1500;
import java.util.*;
public class LC1202_SmallestStringWithSwaps {
    /**
     * You are given a string s, and an array of pairs of indices in the string pairs where pairs[i] = [a, b] indicates
     * 2 indices(0-indexed) of the string.
     *
     * You can swap the characters at any pair of indices in the given pairs any number of times.
     *
     * Return the lexicographically smallest string that s can be changed to after using the swaps.
     *
     * Input: s = "dcab", pairs = [[0,3],[1,2]]
     * Output: "bacd"
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * 0 <= pairs.length <= 10^5
     * 0 <= pairs[i][0], pairs[i][1] < s.length
     * s only contains lower case English letters.
     * @param s
     * @param pairs
     * @return
     */
    // time = O(n + m * a(n) + n^2 * logn), space = O(n)
    int[] parent;
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        int n = s.length();
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i; // O(n)

        for (List<Integer> p : pairs) { // O(m * a(n))
            int a = p.get(0), b = p.get(1);
            if (findParent(a) != findParent(b)) union(a, b);
        }

        HashMap<Integer, List<Integer>> map = new HashMap<>(); // root index -> all index
        for (int i = 0; i < n; i++) { // O(n)
            int p = findParent(i);
            map.putIfAbsent(p, new ArrayList<>());
            map.get(p).add(i);
        }

        char[] res = new char[n];
        for (int x : map.keySet()) { // O(n)
           StringBuilder sb = new StringBuilder();
           for (int idx : map.get(x)) sb.append(s.charAt(idx));
           char[] chars = sb.toString().toCharArray();
           Arrays.sort(chars); // O(nlogn)
           int k = 0;
           for (int idx : map.get(x)) {
               res[idx] = chars[k++];
           }
        }
        return String.valueOf(res);
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
 * 很显然，将所有有联通关系的index都union成一个连通图，那么这些index对应的字符其实就可以任意调换顺序，想像成“冒泡法”就不难理解了。
 * 将一个连通分量内的字符串抽取出来按照字典序重新排列，然后再依次放回这些index的位置，就是答案。
 */