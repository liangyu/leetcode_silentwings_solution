# LC242 Valid Anagram

标签（空格分隔）： LeetCode Java HashTable

---

【题目】

    /**
     * Given two strings s and t , write a function to determine if t is an anagram of s.
     *
     * Input: s = "anagram", t = "nagaram"
     * Output: true
     *
     * Note:
     * You may assume the string contains only lowercase alphabets.
     *
     * Follow up:
     * What if the inputs contain unicode characters? How would you adapt your solution to such case?
     *
     * @param s
     * @param t
     * @return
     */

【思路】

用HashMap或者int[ ] 来统计词频即可。

【解答】

```java     
// time = O(n), space = O(1)
public boolean isAnagram(String s, String t) {
    // corner case
    if (s == null) return t == null;
    if (s.length() != t.length()) return false; // checksum

    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }

    for (int n : count) {
        if (n != 0) return false;
    }
    return true;
}
```
