# LC211 Design Add and Search Words Data Structure

标签（空格分隔）： LeetCode Java DFS Trie

---
    /**
     * Design a data structure that supports adding new words and finding if a string matches
     * any previously added string.
     *
     * Implement the WordDictionary class:
     *
     * WordDictionary() Initializes the object.
     * void addWord(word) Adds word to the data structure, it can be matched later.
     * bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise.
     * word may contain dots '.' where dots can be matched with any letter.
     *
     * Constraints:
     *
     * 1 <= word.length <= 500
     * word in addWord consists lower-case English letters.
     * word in search consist of  '.' or lower-case English letters.
     * At most 50000 calls will be made to addWord and search.
     */

【难点误区】

1. 记住TrieNode和Trie的constructor以及addWord()的模板
2. DFS分叉搜索，边界条件想清楚，TrieNode为null时是false，走到尽头了要check isWord
3. 遇到"."时分26叉，一通百通

【解题思路】

Trie的经典题之一，套用模板来建立TrieNode，这里的class WordDictionary就是Trie。建Trie add word的过程也比较模板化，本题关键在于使用DFS搜索，分两叉来考虑：

1. 如果是a ~ z，那没啥好说的，直接cur.nexts[c - 'a']下去继续往深度方向去接力
2. 如果是"."，那么就分26叉一一去试，一通百通即可，全部试完都没有找到，只能返回false。
3. 注意dfs的base case是2个，一个是当TrieNode遍历到尽头为空时，表明下面没有了，那就是一个失败的case，return false。反之，成功的case就是遍历到了头，但是不能直接返回true，而是要看当前字典树里这遍历下来的是否是一个建Trie时定义好的一个词，即要check cur.isWord。



```java     
class WordDictionary {
    private TrieNode root;
    public WordDictionary() {
        root = new TrieNode('\0');
    }

    // time = O(k) = O(1), space = O(k)
    public void addWord(String word) {
        char[] chars = word.toCharArray();
        TrieNode cur = root;
        for (char ch : chars) {
            if (cur.nexts[ch - 'a'] == null) cur.nexts[ch - 'a'] = new TrieNode(ch);
            cur = cur.nexts[ch - 'a'];
        }
        cur.isWord = true;
    }

    // time = O(26^k) = O(1), space = O(k)
    public boolean search(String word) {
        return dfs(word, root, 0);
    }

    private boolean dfs(String word, TrieNode cur, int idx) {
        int len = word.length();
        if (cur == null) return false;
        if (idx == len) return cur.isWord;

        char ch = word.charAt(idx);
        if (ch >= 'a' && ch <= 'z') {  // case 1: lower case 'a' ~ 'z'
            return dfs(word, cur.nexts[ch - 'a'], idx + 1);
        } else { // case 2: '.'
            for (TrieNode next : cur.nexts) {
                if (dfs(word, next, idx + 1)) return true;
            }
            return false;
        }
    }

    private class TrieNode {
        private char ch;
        private TrieNode[] nexts;
        private boolean isWord;
        public TrieNode(char ch) {
            this.ch = ch;
            this.nexts = new TrieNode[26];
            this.isWord = false;
        }
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
```
