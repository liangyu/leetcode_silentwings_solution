# LC953 Verifying an Alien Dictionary
标签（空格分隔）： LeetCode Java HashTable

---
    /**
     * In an alien language, surprisingly they also use english lowercase letters, but possibly in a different order.
     * The order of the alphabet is some permutation of lowercase letters.
     *
     * Given a sequence of words written in the alien language, and the order of the alphabet, return true if and
     * only if the given words are sorted lexicographicaly in this alien language.
     *
     * Input: words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"
     * Output: true
     * Explanation: As 'h' comes before 'l' in this language, then the sequence is sorted.
     *
     * Constraints:
     *
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 20
     * order.length == 26
     * All characters in words[i] and order are English lowercase letters.
     *
     * @param words
     * @param order
     * @return
     */

【难点误区】

本题难点在于如何区分是由于字符不同但满足字典序而break出loop还是由于一方已经遍历完而出的loop，需要在break loop之前设置一个flag来进行有效的区分。

【解题思路】

涉及到“查字典”来确定先后顺序，那就先要把规则做成字典，最直观的方法就是使用一个HashMap来做，同样也可以使用char[ ]来做，如果只是涉及到大小写字母或者ASCII以及extended ASCII。然后逐个两两遍历比较，只要出现不同字符，就按照字典序进行比较。满足条件则进入下一对比较，不满足立刻返回false。如果在比较过程中，一方已经先遍历完出了loop，那这时就要check究竟是前一个遍历完了还是后一个，如果是前一个则满足条件继续下一对，否则就是return false。但由于上面讨论过，如果两者出现不同字符且符合字典序，同样可以立即break出loop，所以这时出loop其实就包含两种情况，究竟是因为break出了loop还是因为一方遍历完而出loop，如果是后者则要继续看究竟是前者还是后者遍历完，而如果是前者则可以直接遍历下一对。因此，必须在loop里设置一个flag来区分这两种情况。最简单的做法就是在break之前反转flag，这样出loop之后只要看flag究竟是true还是false即可区分二者。

```java
// time = O(C), space = O(1)   C: total contents of words
public boolean isAlienSorted(String[] words, String order) {
    int[] chars = new int[26];
    for (int i = 0; i < order.length(); i++) {
        char c = order.charAt(i);
        chars[c - 'a'] = i;
    }

    for (int i = 0; i < words.length - 1; i++) { // O(n)
        String str1 = words[i], str2 = words[i + 1];
        int idx1 = 0, idx2 = 0;
        boolean flag = false;
        while (idx1 < str1.length() && idx2 < str2.length()) {
            char c1 = str1.charAt(idx1++), c2 = str2.charAt(idx2++);
            if (c1 != c2) {
                if (chars[c1 - 'a'] > chars[c2 - 'a']) return false;
                else {
                    flag = true; // 表明出loop是因为两者出现不同字符且满足order条件
                    break;
                }
            }
        }
        if (!flag && idx1 < str1.length()) return false; // 表明是以上loop中两者所有表的字符都相等才到达这里的
    }
    return true;
}
```

