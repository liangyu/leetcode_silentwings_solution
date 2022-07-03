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
    TrieNode root;
    public int minimumLengthEncoding2(String[] words) {
        root = new TrieNode();

        for (String word : words) insert(word);

        int res = 0;
        HashSet<String> set = new HashSet<>();
        for (String word : words) {
            if (search(word) && set.add(word)) res += word.length() + 1;
        }
        return res;
    }

    private void insert(String s) {
        TrieNode node = root;
        int n = s.length();
        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (node.next[c - 'a'] == null) {
                node.next[c - 'a'] = new TrieNode();
            }
            node = node.next[c - 'a'];
        }
        node.isEnd = true;
    }

    private boolean search(String s) {
        TrieNode node = root;
        int n = s.length();
        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (node.next[c - 'a'] == null) return false;
            node = node.next[c - 'a'];
        }
        return checkNull(node.next);
    }

    private boolean checkNull(TrieNode[] next) {
        for (TrieNode x : next) {
            if (x != null) return false;
        }
        return true;
    }

    private class TrieNode {
        private TrieNode[] next;
        private boolean isEnd;
        public TrieNode() {
            this.next = new TrieNode[26];
            this.isEnd = false;
        }
    }

    // S3: Trie
    // time = O(n * k), space = O(n * k) k: average length of string
    final int N = 2000 * 7 + 10;
    int[][] son;
    int[] cnt, len;
    int idx;
    public int minimumLengthEncoding3(String[] words) {
        son = new int[N][26];
        cnt = new int[N];
        len = new int[N];
        idx = 0;

        for (String s : words) {
            int p = 0, n = s.length();
            for (int i = n - 1; i >= 0; i--) {
                int u = s.charAt(i) - 'a';
                if (son[p][u] == 0) son[p][u] = ++idx;
                cnt[p]++; // 在p结点下有多少个子节点！！！ cnt[p] == 0 代表叶子结点！
                p = son[p][u];
            }
            len[p] = n;
        }

        int res = 0;
        for (int i = 1; i <= idx; i++) {
            if (cnt[i] == 0) res += len[i] + 1;
        }
        return res;
    }
}
