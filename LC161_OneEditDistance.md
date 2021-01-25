# LC161 One Edit Distance

标签（空格分隔）： LeetCode Java TwoPointers

---
    /**
     * Given two strings s and t, return true if they are both one edit distance apart, otherwise return false.
     *
     * A string s is said to be one distance apart from a string t if you can:
     *
     * Insert exactly one character into s to get t.
     * Delete exactly one character from s to get t.
     * Replace exactly one character of s with a different character to get t.
     *
     * Input: s = "ab", t = "acb"
     * Output: true
     *
     * Constraints:
     *
     * 0 <= s.length <= 10^4
     * 0 <= t.length <= 10^4
     * s and t consist of lower-case letters, upper-case letters and/or digits.
     *
     * @param s
     * @param t
     * @return
     */

【难点误区】

本题难度在于能想到用双指针来实现O(n)的最优解，同时空间可以达到O(1)。基本思路就是先用checksum去排除掉大量不符合题意的case，然后根据双指针的移动方式，一旦出现字符彼此不匹配，则分2种情况讨论。最后还有一个难点在于出loop后同样要分2叉讨论，即loop里存在one edit distance，则此时两string只能同时出loop；或者是loop里不存在one edit distance，那么只可能在出loop后产生，由于长string最多比短string多一位，所以这个one edit distance就只能是由于长string比短string多一位来补上。

【解题思路】

双指针解法，两个指针 i，j 分别指向两个string, s和t。为了简化问题，首先设置确保第一个string长度不小于第二个string，即s >= t。同时利用checksum的特性，如果两者长度超过1，那肯定实现不了one edit distance，因为三种case里，1和2都是长度差1，而3是长度相等时发生的。

在遍历过程中，如果遇到两者匹配不上的话，就要分叉讨论。如果两者长度相等，那么势必i和j都要同步向后移动一位；反之，如果长度不等，那么长度较长的一方就要向后移动一位，在这里由于i >= j，所以只有是 i++。这里要注意，由于两者只能相差1次edit distance，所以一旦这唯一的一次被使用了的话，就要通过设置一个flag来记录这种使用过的状态，即设为true。一旦下次再出现彼此匹配不上的情况，一旦check到flag = true，表明之前已经失配过一次，那么肯定是return false。

最后出loop时，只可能存在以下两种情况：

1. 两者同时出loop，那么要满足return true，则之前loop里一定要mismatch一次
2. 短的一方先出loop，那么就要满足之前loop里要全部匹配，唯一的mismatch就出现在长的string比短的一方多出1个char来，也就是i当前停的位置。要不是这样，之前就被if(flag)给截住，直接return false了。


```java     
// time = O(max(m, n)), space = O(1)
public boolean isOneEditDistance(String s, String t) {
    if (s.length() < t.length()) return isOneEditDistance(t, s); // 确保第一个string长度 >= 第2个string
    if (s.length() - t.length() > 1) return false; // 长度之差超过1，肯定是false

    int i = 0, j = 0;
    boolean flag = false; // flag用来标注是否已经出现one edit，如果出现则设置为true
    while (i < s.length() && j < t.length()) {
        char c1 = s.charAt(i), c2 = t.charAt(j);
        if (c1 != c2) {
            if (flag) return false; // flag = true表明之前已经出现过1个edit diff，这里再次出现直接返回false
            flag = true; // 之前没出现过edit distance，这次是首次出现，flag设为true
            if (s.length() == t.length()) j++; // 如果两者长度相等，则对应case 3，从而i和j都要向后移动一位
            i++; // 否则就是case 1或者case 2，无论哪种都等价于较长的string向后移动一位
        } else { // c1 == c2 ==> i，j同时向后移动一位
            i++;
            j++;
        }
    }
    // 遍历完后出loop，要满足one edit distance,那么就要分2种情况：
    // 1. 两字符串长度相等且符合case 3,此时i应该也刚好出界，同时之前在loop里出现1位不同，使用replace操作，flag = true
    // 2. 两字符串不等，较长的string比短的长1位，那么此时i应该指向string最后一位，且之前loop里字符都对应相等，即flag = false
    if (flag && i == s.length() || !flag && i == s.length() - 1) return true;
    return false;
}
```
