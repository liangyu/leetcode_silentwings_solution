package LC601_900;
import java.util.*;
public class LC820_ShortEncodingofWords {
    /**
     * A valid encoding of an array of words is any reference string s and array of indices indices such that:
     *
     * words.length == indices.length
     * The reference string s ends with the '#' character.
     * For each index indices[i], the substring of s starting from indices[i] and up to (but not including) the next
     * '#' character is equal to words[i].
     * Given an array of words, return the length of the shortest reference string s possible of any valid encoding of
     * words.
     *
     * Input: words = ["time", "me", "bell"]
     * Output: 10
     *
     * Constraints:
     *
     * 1 <= words.length <= 2000
     * 1 <= words[i].length <= 7
     * words[i] consists of only lowercase letters.
     *
     * @param words
     * @return
     */
    // S1: Store prefix
    // time = O(nk^2), space = O(n * k) k: average length of string
    public int minimumLengthEncoding(String[] words) {
        // corner case
        if (words == null || words.length == 0) return 0;

        HashSet<String> set = new HashSet<>(Arrays.asList(words));

        for (String s : words) { // O(n)
            for (int i = 1; i < s.length(); i++) { // O(k)
                set.remove(s.substring(i)); // O(k)
            }
        }

        int res = 0;
        for (String s : set) {
            res += s.length() + 1;
        }
        return res;
    }

    // S2: Trie
    // time = O(n * k), space = O(n * k) k: average length of string
    public int minimumLengthEncoding2(String[] words) {
        // corner case
        if (words == null || words.length == 0) return 0;

        Trie trie = new Trie();
        int count = 0;

        for (String word : words) { // O(n)
            trie.insert(word); // O(k)
        }

        int res = 0;
        HashSet<String> set = new HashSet<>();
        for (String word : words) { // O(n)
            if (trie.search(word) && set.add(word)) res += word.length() + 1; // O(k)
        }
        return res;
    }

    class Trie {
        TrieNode root;
        public Trie() {
            root = new TrieNode('\0');
        }

        public void insert(String word) {
            TrieNode cur = root;
            for (int i = word.length() - 1; i >= 0; i--) {
                char c = word.charAt(i);
                if (cur.nexts[c - 'a'] == null) {
                    cur.nexts[c - 'a'] = new TrieNode(c);
                }
                cur = cur.nexts[c - 'a'];
            }
        }

        public boolean search(String word) {
            TrieNode cur = root;
            for (int i = word.length() - 1; i >= 0; i--) {
                char c = word.charAt(i);
                if (cur.nexts[c - 'a'] == null) throw new RuntimeException();
                cur = cur.nexts[c - 'a'];
            }
            if (checkNull(cur.nexts)) return true;
            return false;
        }

        private boolean checkNull(TrieNode[] nexts) {
            for (TrieNode next : nexts) {
                if (next != null) return false;
            }
            return true;
        }
    }

    class TrieNode {
        char ch;
        TrieNode[] nexts;
        public TrieNode(char ch) {
            this.ch = ch;
            this.nexts = new TrieNode[26];
        }
    }
}
