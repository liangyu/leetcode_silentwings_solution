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
}
