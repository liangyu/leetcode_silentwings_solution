# LC1657 Determine if Two Strings Are Close

标签（空格分隔）： LeetCode Java Sort Greedy

---
    /**
     * Two strings are considered close if you can attain one from the other using the following operations:
     *
     * Operation 1: Swap any two existing characters.
     * For example, abcde -> aecdb
     * Operation 2: Transform every occurrence of one existing character into another existing character,
     * and do the same with the other character.
     * For example, aacabb -> bbcbaa (all a's turn into b's, and all b's turn into a's)
     * You can use the operations on either string as many times as necessary.
     *
     * Given two strings, word1 and word2, return true if word1 and word2 are close, and false otherwise.
     *
     * Input: word1 = "abc", word2 = "bca"
     * Output: true
     *
     * Input: word1 = "cabbba", word2 = "aabbss"
     * Output: false
     *
     * Constraints:
     *
     * 1 <= word1.length, word2.length <= 105
     * word1 and word2 contain only lowercase English letters.
     *
     * @param word1
     * @param word2
     * @return
     */
     
【难点误区】

本题最大的难点就是看出两条规则下的规律就是组成两个词的种类和词频必须相同。

【解题思路】


```
/**
 *     a  b  c
 *     3  2  1
 *     1  2  3
 *     1  3  2
 *     2  1  3
 *     2  3  1
 *     3  1  2
 *
 *     a  b  c  -> 不能引入新的字符 -> 种类和词频必须相同
 *     1. have the same letter types
 *     2. have the same sorted frequency array
 */
```


```java     
// time = O(m + n), space = O(1)
public boolean closeStrings(String word1, String word2) {
    HashSet<Character> set1 = new HashSet<>();
    HashSet<Character> set2 = new HashSet<>();
    int[] count1 = new int[26];
    int[] count2 = new int[26];

    // 词频统计
    for (char ch : word1.toCharArray()) { // O(m)
        set1.add(ch);
        count1[ch - 'a']++;
    }

    for (char ch : word2.toCharArray()) { // O(n)
        set2.add(ch);
        count2[ch - 'a']++;
    }

    Arrays.sort(count1); // O(1)
    Arrays.sort(count2); // O(1)
    return set1.equals(set2) && Arrays.equals(count1, count2);
}
```
