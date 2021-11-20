package LC001_300;
import java.util.*;
public class LC269_AlienDictionary {
    /**
     * There is a new alien language that uses the English alphabet. However, the order among letters are unknown to you.
     *
     * You are given a list of strings words from the dictionary, where words are sorted lexicographically by
     * the rules of this new language.
     *
     * Derive the order of letters in this language, and return it. If the given input is invalid, return "".
     * If there are multiple valid solutions, return any of them.
     *
     * Input: words = ["wrt","wrf","er","ett","rftt"]
     * Output: "wertf"
     *
     * Constraints:
     *
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 100
     * words[i] consists of only lowercase English letters.
     *
     * @param words
     * @return
     */
    // S1: DFS
    // time = O(C), space = O(1)
    // C: the total length of all the words in the input list, added together
    public String alienOrder(String[] words) {
        // corner case
        if (words == null || words.length == 0) return "";
        if (words.length == 1) return words[0]; // 注意这个长度为1的edge case！！！

        HashMap<Character, List<Character>> map = new HashMap<>();
        HashMap<Character, Integer> status = new HashMap<>();

        // step 1: build graph
        for (int i = 0; i < words.length - 1; i++) { // O(n)
            String str1 = words[i], str2 = words[i + 1];
            int idx1 = 0, idx2 = 0;
            boolean flag = false;

            while (idx1 < str1.length() && idx2 < str2.length()) { // O(min(k1, k2))
                char c1 = str1.charAt(idx1++), c2 = str2.charAt(idx2++);
                map.putIfAbsent(c1, new ArrayList<>());
                map.putIfAbsent(c2, new ArrayList<>());
                status.putIfAbsent(c1, 0);
                status.putIfAbsent(c2, 0);
                if (c1 != c2) {
                    map.get(c1).add(c2);
                    flag = true;
                    break;
                }
            }

            while (idx1 < str1.length()) { // O(k1)
                if (!flag) return "";
                char c1 = str1.charAt(idx1++);
                map.putIfAbsent(c1, new ArrayList<>());
                status.putIfAbsent(c1, 0);
            }

            while (idx2 < str2.length()) { // O(k2)
                char c2 = str2.charAt(idx2++);
                map.putIfAbsent(c2, new ArrayList<>());
                status.putIfAbsent(c2, 0);
            }
        }

        // step 2: iterate the map
        StringBuilder path = new StringBuilder();
        for (char key : map.keySet()) { // O(u) <= O(26)
            if (containsCycle(map, status, key, path)) return ""; // O(V + E)
        }

        return path.reverse().toString();
    }

    // step 3: toplogical sort
    private boolean containsCycle(HashMap<Character, List<Character>> map, HashMap<Character, Integer> status, char cur, StringBuilder path) {
        if (status.get(cur) == 2) return false;
        if (status.get(cur) == 1) return true;

        status.put(cur, 1);

        for (char next : map.get(cur)) {
            if (containsCycle(map, status, next, path)) return true;
        }

        status.put(cur, 2);
        path.append(cur);
        return false;
    }

    // S2: BFS
    // time = O(C), space = O(1)
    public String alienOrder2(String[] words) {
        HashMap<Character, HashSet<Character>> map = new HashMap<>();
        HashMap<Character, Integer> indegree = new HashMap<>();

        for (String word : words) {
            for (char c : word.toCharArray()) {
                indegree.put(c, 0); // 对真实存在的char的入度初始化为0
            }
        }

        for (int i = 0; i < words.length - 1; i++) {
            String s = words[i];
            String t = words[i + 1];

            if (s.length() > t.length() && s.substring(0, t.length()).equals(t)) return "";

            for (int j = 0; j < Math.min(s.length(), t.length()); j++) {
                if (s.charAt(j) == t.charAt(j)) continue;
                // s[j] => t[j]
                map.putIfAbsent(s.charAt(j), new HashSet<>());
                if (!map.get(s.charAt(j)).contains(t.charAt(j))) {
                    map.get(s.charAt(j)).add(t.charAt(j));
                    indegree.put(t.charAt(j), indegree.getOrDefault(t.charAt(j), 0) + 1); // 只给入度非0的创建了
                }
                break;
            }
        }

        // change queue to pq if return the smallest in normal lexicographical order
        Queue<Character> queue = new LinkedList<>();
        for (char x : indegree.keySet()) {
            if (indegree.get(x) == 0) queue.offer(x);
        }

        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            char cur = queue.poll();
            sb.append(cur);

            for (char next : map.getOrDefault(cur, new HashSet<>())) {
                indegree.put(next, indegree.get(next) - 1);
                if (indegree.get(next) == 0) queue.offer(next);
            }
        }
        if (sb.length() != indegree.size()) return "";
        return sb.toString();
    }
}
/**
 * words = ["wrt","wrf","er","ett","rftt"]
 * t < f
 * w < e
 * r < t
 * e < r
 * => wertf
 * 看哪个字母之前没有约束
 * wertf
 * 根据每两个单词之间的字典序关系来推出
 * 剥洋葱 -> 先找入度为0的点 -> 层层去剥
 * DFS, BFS -> 顺序要输出出来 -> BFS更直观一些
 */
