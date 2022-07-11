package LC601_900;

public class LC676_ImplementMagicDictionary {
    /**
     * Design a data structure that is initialized with a list of different words. Provided a string, you should
     * determine if you can change exactly one character in this string to match any word in the data structure.
     *
     * Implement the MagicDictionary class:
     *
     * MagicDictionary() Initializes the object.
     * void buildDict(String[] dictionary) Sets the data structure with an array of distinct strings dictionary.
     * bool search(String searchWord) Returns true if you can change exactly one character in searchWord to match any
     * string in the data structure, otherwise returns false.
     *
     * Input
     * ["MagicDictionary", "buildDict", "search", "search", "search", "search"]
     * [[], [["hello", "leetcode"]], ["hello"], ["hhllo"], ["hell"], ["leetcoded"]]
     * Output
     * [null, null, false, true, false, false]
     *
     * Constraints:
     *
     * 1 <= dictionary.length <= 100
     * 1 <= dictionary[i].length <= 100
     * dictionary[i] consists of only lower-case English letters.
     * All the strings in dictionary are distinct.
     * 1 <= searchWord.length <= 100
     * searchWord consists of only lower-case English letters.
     * buildDict will be called only once before search.
     * At most 100 calls will be made to search.
     */
    // S1
    // time = O(nl + ql), space = O(nl)  n: dict长度, l: 字符串长度，q: search调用次数，
    TrieNode root;
    public LC676_ImplementMagicDictionary() {
        root = new TrieNode();
    }

    public void buildDict(String[] dictionary) {
        for (String word : dictionary) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.next[c - 'a'] == null) {
                    node.next[c - 'a'] = new TrieNode();
                }
                node = node.next[c - 'a'];
            }
            node.isEnd = true;
        }
    }

    public boolean search(String searchWord) {
        return dfs(root, searchWord);
    }

    private boolean dfs(TrieNode node, String s) {
        if (s.length() == 0) return false;
        if (node == null) return false;

        for (char c = 'a'; c <= 'z'; c++) {
            if (c == s.charAt(0)) continue;
            if (inDict(s.substring(1), node.next[c - 'a'])) return true;
        }
        return dfs(node.next[s.charAt(0) - 'a'], s.substring(1));
    }

    private boolean inDict(String s, TrieNode node) {
        if (node == null) return false;
        for (char c : s.toCharArray()) {
            if (node.next[c - 'a'] == null) return false;
            node = node.next[c - 'a'];
        }
        return node.isEnd;
    }

    private class TrieNode {
        private TrieNode[] next;
        private boolean isEnd;
        public TrieNode() {
            this.next = new TrieNode[26];
            this.isEnd = false;
        }
    }

    // S2: Trie
    // time = O(nl + ql), space = O(nl)  n: dict长度, l: 字符串长度，q: search调用次数，
    class MagicDictionary {
        final int N = 10010;
        int[][] son;
        boolean[] is_end;
        int idx;

        public MagicDictionary() {
            son = new int[N][26];
            is_end = new boolean[N];
            idx = 0;
        }

        public void buildDict(String[] dictionary) {
            for (String s : dictionary) insert(s);
        }

        public boolean search(String searchWord) {
            return dfs(searchWord, 0, 0, 0);
        }

        private boolean dfs(String s, int p, int u, int c) {
            if (is_end[p] && u == s.length() && c == 1) return true;
            if (c > 1 || u == s.length()) return false;

            for (int i = 0; i < 26; i++) {
                if (son[p][i] == 0) continue;
                if (dfs(s, son[p][i], u + 1, c + (s.charAt(u) - 'a' != i ? 1 : 0))) {
                    return true;
                }
            }
            return false;
        }

        private void insert(String s) {
            int p = 0;
            for (char c : s.toCharArray()) {
                int u = c - 'a';
                if (son[p][u] == 0) son[p][u] = ++idx;
                p = son[p][u];
            }
            is_end[p] = true;
        }
    }
}
