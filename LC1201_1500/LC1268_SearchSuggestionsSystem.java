package LC1201_1500;
import java.util.*;
public class LC1268_SearchSuggestionsSystem {
    /**
     * Given an array of strings products and a string searchWord. We want to design a system that suggests at most
     * three product names from products after each character of searchWord is typed. Suggested products should have
     * common prefix with the searchWord. If there are more than three products with a common prefix return the three
     * lexicographically minimums products.
     *
     * Return list of lists of the suggested products after each character of searchWord is typed.
     *
     * Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
     * Output: [
     * ["mobile","moneypot","monitor"],
     * ["mobile","moneypot","monitor"],
     * ["mouse","mousepad"],
     * ["mouse","mousepad"],
     * ["mouse","mousepad"]
     * ]
     *
     * Constraints:
     *
     * 1 <= products.length <= 1000
     * There are no repeated elements in products.
     * 1 <= Σ products[i].length <= 2 * 10^4
     * All characters of products[i] are lower-case English letters.
     * 1 <= searchWord.length <= 1000
     * All characters of searchWord are lower-case English letters.
     * @param products
     * @param searchWord
     * @return
     */
    // S1: Trie
    // time = O(n * k + m), space = O(n * k)  n * k: 所有字符串的长度之和
    TrieNode root;
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();
        root = new TrieNode();

        // build trie
        for (String s : products) {
            TrieNode node = root;
            for (char c : s.toCharArray()) {
                if (node.next[c - 'a'] == null) {
                    node.next[c - 'a'] = new TrieNode();
                }
                node = node.next[c - 'a'];
            }
            node.count++;
        }

        // traverse the trie with all possibilities
        TrieNode node = root;
        StringBuilder sb = new StringBuilder();
        int n = searchWord.length();
        for (int i = 0; i < n; i++) {
            char c = searchWord.charAt(i);

            // can't find the next char
            if (node.next[c - 'a'] == null) {
                for (int j = i; j < n; j++) res.add(new ArrayList<>());
                break;
            }

            // can find the next char
            node = node.next[c - 'a']; // node need to be kept at the curent level, as it needs to move to next char level
            sb.append(c);

            // dfs -> find all possible words based on the current prefix in the sb
            List<String> path = new ArrayList<>();
            dfs(node, path, sb);

            while (path.size() > 3) path.remove(path.size() - 1);
            res.add(new ArrayList<>(path));
        }
        return res;
    }

    private void dfs(TrieNode node, List<String> path, StringBuilder sb) {
        if (node.count > 0) {
            for (int k = 0; k < node.count; k++) path.add(sb.toString());
        }

        for (int i = 0; i < 26; i++) {
            if (path.size() > 3) break;
            if (node.next[i] == null) continue;
            sb.append((char)('a' + i));
            dfs(node.next[i], path, sb);
            sb.setLength(sb.length() - 1);
        }
    }

    private class TrieNode {
        private TrieNode[] next;
        private int count;
        public TrieNode() {
            this.next = new TrieNode[26];
            this.count = 0;
        }
    }

    // S2: Sort + B.S.
    // time = O((m + n) * logn), space = O(n * k + m)
    public List<List<String>> suggestedProducts2(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();

        Arrays.sort(products); // O(nlogn)

        int n = products.length;
        StringBuilder sb = new StringBuilder();
        for (char c : searchWord.toCharArray()) { // O(m)
            sb.append(c);
            int idx = upperBound(products, sb.toString()); // O(logn)
            List<String> path = new ArrayList<>();
            for (int i = idx; i < Math.min(idx + 3, n); i++) {
                if (products[i].length() < sb.length()) break;
                if (products[i].substring(0, sb.length()).equals(sb.toString())) path.add(products[i]);
            }
            res.add(new ArrayList<>(path));
        }
        return res;
    }

    private int upperBound(String[] nums, String t) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid].compareTo(t) < 0) left = mid + 1;
            else right = mid;
        }
        return nums[left].compareTo(t) >= 0 ? left : left + 1;
    }

    // S3: TreeMap
    // time = O((m + n) * logn + m^2), space = O(n)
    public List<List<String>> suggestedProducts3(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();
        // corner case
        if (products == null || products.length == 0 || searchWord == null || searchWord.length() == 0) return res;

        TreeMap<String, Integer> map = new TreeMap<>();
        Arrays.sort(products); // O(nlogn)

        for (int i = 0; i < products.length; i++) map.put(products[i], i); // O(n)

        String word = "";
        for (char c : searchWord.toCharArray()) { // O(m)
            word += c; // O(m)
            String ck = map.ceilingKey(word); // O(logn)
            String fk = map.floorKey(word + "~"); // 加上一个额外字符"~"来确保word本身不会成为fk
            if (ck == null || fk == null) break;

            int start = map.get(ck), end = Math.min(map.get(ck) + 3, map.get(fk) + 1);
            List<String> ans = new ArrayList<>();
            for (int i = start; i < end; i++) ans.add(products[i]);
            res.add(ans);
        }
        while (res.size() < searchWord.length()) res.add(new ArrayList<>());
        return res;
    }
}
