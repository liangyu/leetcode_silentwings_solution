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
    // time = O(nk^2 + qk), space = O(nk^2)
    TrieNode root;
    public LC745_PrefixandSuffixSearch(String[] words) {
        root = new TrieNode();

        int n = words.length;
        for (int i = 0; i < n; i++) {
            String s = '#' + words[i];
            insert(s, i);
            int m = words[i].length();
            for (int j = m - 1; j >= 0; j--) {
                // 把suffix放在前面去做处理，prefix则通过在原来的单词后加上分隔符，再连上一个完整单词来进行prefix的查询！
                s = words[i].charAt(j) + s;
                insert(s, i);
            }
        }
    }

    public int f(String prefix, String suffix) {
        return query(suffix + "#" + prefix); // 注意：拼接的时候，这里是suffix在前！
    }

    private void insert(String s, int id) {
        TrieNode node = root;
        for (char c : s.toCharArray()) {
            int u = c == '#' ? 26 : c - 'a';
            if (node.next[u] == null) {
                node.next[u] = new TrieNode();
            }
            node = node.next[u];
            node.weight = id; // 注意沿途路过的每个结点的weight都是id
        }
    }

    private int query(String s) {
        TrieNode node = root;
        for (char c : s.toCharArray()) {
            int u = c == '#' ? 26 : c - 'a';
            if (node.next[u] == null) return -1;
            node = node.next[u];
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
/**
 * 一个巧妙的设计就是把后缀加在单词前面，中间用"{"分隔，拼成一个新的单词，将这种新的单词加入字典树中，并在querry的时候查找。
 * 需要注意的是，一个正常的单词可能有k种后缀，k就是单词的长度，所以我们需要把这些所有的变化都加进字典树里。
 * 如何快速查找weight呢？
 * 其实只要在加入字符串的时候，在每个node都标记该单词的id，而且后来的字符串id可以覆盖前面的，因为后面的认为是更大的weight。
 */
