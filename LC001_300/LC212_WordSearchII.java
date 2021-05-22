package LC001_300;
import java.util.*;
public class LC212_WordSearchII {
    /**
     * Given an m x n board of characters and a list of strings words, return all words on the board.
     *
     * Each word must be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally
     * or vertically neighboring. The same letter cell may not be used more than once in a word.
     *
     * Constraints:
     *
     * m == board.length
     * n == board[i].length
     * 1 <= m, n <= 12
     * board[i][j] is a lowercase English letter.
     * 1 <= words.length <= 3 * 104
     * 1 <= words[i].length <= 10
     * words[i] consists of lowercase English letters.
     * All the strings of words are unique.
     *
     * @param board
     * @param words
     * @return
     */
    // time = O(m * n * 4 * 3^(k - 1)), space = O(N)
    // k : max length of words, N: total number of letters in the dict
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        Trie trie = new Trie();

        for (String word : words) trie.addWord(word);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, i, j, new StringBuilder(), trie.root, res, visited, trie.root);
            }
        }
        return res;
    }

    private void dfs(char[][] board, int i, int j, StringBuilder path, TrieNode cur, List<String> res, boolean[][] visited, TrieNode root)  {
        int m = board.length, n = board[0].length;
        // base case - fail
        if (i < 0 || i >= m || j < 0 || j >= n || visited[i][j] || cur.nexts[board[i][j] - 'a'] == null || cur.nexts[board[i][j] - 'a'].count == 0) return;

        visited[i][j] = true;
        TrieNode next = cur.nexts[board[i][j] - 'a'];
        path.append(board[i][j]);
        if (next.isWord) {
            String s = path.toString();
            res.add(s);
            next.isWord = false;
            remove(root, s);
        }
        for (int[] dir : DIRECTIONS) {
            int ii = i + dir[0];
            int jj = j + dir[1];
            dfs(board, ii, jj, path, next, res, visited, root);
        }
        visited[i][j] = false;
        path.setLength(path.length() - 1);
    }

    private void remove(TrieNode cur, String word) {
        for (char ch : word.toCharArray()) {
            cur = cur.nexts[ch - 'a'];
            cur.count--;
        }
    }

    private class TrieNode {
        private char ch;
        private TrieNode[] nexts;
        private boolean isWord;
        private int count;
        public TrieNode(char ch) {
            this.ch = ch;
            this.nexts = new TrieNode[26];
            this.isWord = false;
            this.count = 0;
        }
    }

    private class Trie {
        private TrieNode root;
        public Trie() {
            root = new TrieNode('\0');
        }

        private void addWord(String word) {
            char[] chars = word.toCharArray();
            TrieNode cur = root;
            for (char ch : chars) {
                if (cur.nexts[ch - 'a'] == null) cur.nexts[ch - 'a'] = new TrieNode(ch);
                cur = cur.nexts[ch - 'a'];
                cur.count++;
            }
            cur.isWord = true;
        }
    }
}
/**
 * 不知道第一个单词有多长，分割点并不很清楚在哪里
 * 常见的方法：字典弄成trie，不把它作为一个集合
 * 好处：省空间，尽可能共用前缀；遍历第一个单词的时候可以知道什么时候可以及时终止，发觉走不动了前缀遍历就已经到头了，就不用再往前走了
 * 及时停住，效率非常高，和word break很相似
 *
 * 优化：找到一个单词后，在trie里删除掉这个单词，避免搜到重复的单词，字典树变小了，dfs更容易结束！！！ -> 不可能真删掉，否则trie就垮了！
 * 加入一个变量count
 * abxy
 * abcde
 * abcdeftgd
 * 删除 -> 把count - 1,当你结点的count == 0时，再无单词经过这个路径！所有count = 0的结点就不需要再访问了！这个技巧非常巧妙！！！
 *
 * a
 * aa
 * aaa
 * 321 -> 221  还会把a输出来，斩草除根，要把isWord = false
 */