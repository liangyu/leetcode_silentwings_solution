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
    TrieNode root;
    int m, n;
    boolean[][] visited;
    HashSet<String> res;
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public List<String> findWords(char[][] board, String[] words) {
        res = new HashSet<>();
        root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.next[c - 'a'] == null) {
                    node.next[c - 'a'] = new TrieNode();
                }
                node = node.next[c - 'a'];
                node.count++;
            }
            node.isEnd = true;
        }

        m = board.length;
        n = board[0].length;
        visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                TrieNode node = root;
                visited[i][j] = true;
                dfs(board, i, j, node, new StringBuilder());
                visited[i][j] = false;
            }
        }
        return new ArrayList<>(res);
    }

    private void dfs(char[][] board, int i, int j, TrieNode node, StringBuilder path) {
        // base case
        if (node.next[board[i][j] - 'a'] == null) return;
        if (node.next[board[i][j] - 'a'].count == 0) return;

        node = node.next[board[i][j] - 'a']; // 注意：node本质上只是一个类似i, j的指针，不是引用，不需要进行回溯！！！
        path.append(board[i][j]);

        if (node.isEnd) {
            res.add(path.toString()); // can't return, may go down more for more matches until deadend
            remove(path.toString());
            node.isEnd = false; // 标记也给拔了 -> 真正再也搜不到了！！！因此也不需要进行回溯！
        }

        for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x < 0 || x >= m || y < 0 || y >= n) continue;
            if (visited[x][y]) continue;
            visited[x][y] = true;
            dfs(board, x, y, node, path);
            visited[x][y] = false;
        }

        // setback
        path.setLength(path.length() - 1);
    }

    private void remove(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.next[c - 'a'];
            node.count--;
        }
    }

    private class TrieNode {
        private TrieNode[] next;
        private boolean isEnd;
        private int count;
        public TrieNode() {
            this.next = new TrieNode[26];
            this.isEnd = false;
            this.count = 0;
        }
    }
}
/**
 * 不知道第一个单词有多长，分割点并不很清楚在哪里
 * 常见的方法：字典弄成trie，不把它作为一个集合!!!
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
 * 321 -> 221  还会把a输出来，斩草除根，要把isEnd = false
 * 问题：可能会有重复的单词不停的进行搜索，可能会出现很多的某个单词，重复出现，每次都能搜出来，停不下来
 * 比如100个某单词，都会遍历到，效率其实特别低。
 * 改进：如果我搜到一个单词，我把这个单词从trie里去掉
 * trie里没有这个单词后，就不会再搜到，同时trie的规模也变小了，dfs的过程就更容易结束！
 * 否则的话，如果有非常多的长单词，每次都跑100遍的字典树，从头跑到尾，效率就很低。
 * 如何在trie里删除路径呢？是件很麻烦的事。
 * 比如2个单词共用一个前缀：
 * abxy
 * abcde
 * abcdefghgjkdjfksl
 * 这个时候如果想删掉abcde，那就垮了，剩下那些怎么办？悬空？？？
 * 不可能真的删除掉结点。
 * 每个结点增加一个属性，叫count，表示建这棵树的时候，这个结点经过几次
 * 删除单词 => count - 1即可。
 * 当一个结点的count = 0的时候，就代表再没有其他单词经过这条路径了，变相认为删掉了这条路径！
 */