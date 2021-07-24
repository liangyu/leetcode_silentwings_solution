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

        // build trie
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char ch : word.toCharArray()) {
                if (node.next[ch - 'a'] == null) {
                    node.next[ch - 'a'] = new TrieNode();
                }
                node = node.next[ch - 'a'];
                node.count++;
            }
            node.isWord = true;
        }

        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                TrieNode node = root;
                visited[i][j] = true;
                dfs(board, i, j, root, node, visited, new StringBuilder(), set);
                visited[i][j] = false;
            }
        }

        for (String s : set) res.add(s);
        return res;
    }

    private void dfs(char[][] board, int i, int j, TrieNode root, TrieNode node, boolean[][] visited, StringBuilder path, HashSet<String> set) {
        int m = board.length, n = board[0].length;
        // base case
        if (node.next[board[i][j] - 'a'] == null || node.next[board[i][j] - 'a'].count == 0) return;

        TrieNode next = node.next[board[i][j] - 'a'];
        path.append(board[i][j]);

        // check if next is a word as the first node in dfs is root
        if (next.isWord) {
            String word = path.toString();
            set.add(word);
            remove(root, word);
            next.isWord = false;
        }

        for (int[] dir : DIRECTIONS) {
            int ii = i + dir[0];
            int jj = j + dir[1];
            if (ii >= 0 && ii < m && jj >= 0 && jj < n && !visited[ii][jj]) {
                visited[ii][jj] = true;
                dfs(board, ii, jj, root, next, visited, path, set);
                visited[ii][jj] = false;
            }
        }
        path.setLength(path.length() - 1);
    }

    private void remove(TrieNode root, String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.next[ch - 'a'];
            node.count--;
        }
    }

    private class TrieNode {
        private TrieNode[] next;
        private boolean isWord;
        private int count;
        public TrieNode() {
            this.next = new TrieNode[26];
            this.isWord = false;
            this.count = 0;
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