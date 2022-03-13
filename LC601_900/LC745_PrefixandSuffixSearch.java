package LC601_900;
import java.util.*;
public class LC745_PrefixandSuffixSearch {
    /**
     * Design a special dictionary which has some words and allows you to search the words in it by a prefix and a
     * suffix.
     *
     * Implement the WordFilter class:
     *
     * WordFilter(string[] words) Initializes the object with the words in the dictionary.
     * f(string prefix, string suffix) Returns the index of the word in the dictionary which has the prefix prefix and
     * the suffix suffix. If there is more than one valid index, return the largest of them. If there is no such word
     * in the dictionary, return -1.
     *
     * Input
     * ["WordFilter", "f"]
     * [[["apple"]], ["a", "e"]]
     * Output
     * [null, 0]
     *
     * Constraints:
     *
     * 1 <= words.length <= 15000
     * 1 <= words[i].length <= 10
     * 1 <= prefix.length, suffix.length <= 10
     * words[i], prefix and suffix consist of lower-case English letters only.
     * At most 15000 calls will be made to the function f.
     * @param words
     */
    // time = O(nk^2), space = O(nk^2)
    TrieNode root;
    public LC745_PrefixandSuffixSearch(String[] words) {
        root = new TrieNode();
        int n = words.length;
        for (int i = 0; i < n; i++) {
            String word = words[i] + "{";
            int m = word.length();
            for (int j = 0; j < m; j++) {
                TrieNode node = root;
                node.weight = i;
                // 把suffix放在前面去做处理，prefix则通过在原来的单词后加上分隔符，再连上一个完整单词来进行prefix的查询！
                // add "apple{apple", "pple{apple", "ple{apple", "le{apple", "e{apple", "{apple" into the Trie Tree
                for (int k = j; k < 2 * m - 1; k++) { // 2*m-1去掉的就是最后一个"{"!!!
                    char c = word.charAt(k % m); // next只有27种字符，走到底m之后要绕回来，所以必须要%m
                    if (node.next[c - 'a'] == null) {
                        node.next[c - 'a'] = new TrieNode();
                    }
                    node = node.next[c - 'a'];
                    node.weight = i; // 注意沿途路过的每个结点的weight都是i
                }
            }
        }
    }
    // time = O(k), space = O(nk^2)
    public int f(String prefix, String suffix) {
        TrieNode node = root;
        for (char c : (suffix + "{" + prefix).toCharArray()) { // 注意：拼接的时候，这里是suffix在前！
            if (node.next[c - 'a'] == null) return -1;
            node = node.next[c - 'a'];
        }
        return node.weight;
    }

    private class TrieNode {
        private TrieNode[] next;
        private int weight;
        public TrieNode() {
            this.next = new TrieNode[27];
            this.weight = 0;
        }
    }
}

class WordFilter {
    TrieNode root;
    public WordFilter(String[] words) {
        root = new TrieNode();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < word.length(); j++) {
                sb.append(word.substring(j));
                String rev = sb.reverse().toString();
                buildTree(rev + "{" + word, i);
                sb = new StringBuilder();
            }
            buildTree("{" + word, i);
        }
    }

    public int f(String prefix, String suffix) {
        StringBuilder sb = new StringBuilder();
        sb.append(suffix);
        suffix = sb.reverse().toString();
        String s = suffix + "{" + prefix;
        TrieNode node = root;

        for (char c : s.toCharArray()) {
            if (node.next[c - 'a'] == null) return -1;
            else node = node.next[c - 'a'];
        }
        return node.ids.get(node.ids.size() - 1);
    }

    private void buildTree(String s, int id) {
        TrieNode node = root;
        for (char c : s.toCharArray()) {
            if (node.next[c - 'a'] == null) {
                node.next[c - 'a'] = new TrieNode();
            }
            node = node.next[c - 'a'];
            node.ids.add(id);
        }
    }

    private class TrieNode {
        private TrieNode[] next;
        private List<Integer> ids;
        private boolean isEnd;
        public TrieNode() {
            this.next = new TrieNode[27];
            this.ids = new ArrayList<>();
            this.isEnd = false;
        }
    }
}
