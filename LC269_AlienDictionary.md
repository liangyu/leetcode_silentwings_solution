# LC269 Alien Dictionary

标签（空格分隔）： LeetCode Java Graph TopologicalSort

---
    /**
     * There is a new alien language that uses the English alphabet. However, the order among letters are unknown to you.
     *
     * You are given a list of strings words from the dictionary, where words are sorted lexicographically by
     * the rules of this new language.
     *
     * Derive the order of letters in this language, and return it. If the given input is invalid, return "".
     * If there are multiple valid solutions, return any of them.
     *
     * Input: words = ["wrt","wrf","er","ett","rftt"]
     * Output: "wertf"
     *
     * Constraints:
     *
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 100
     * words[i] consists of only lowercase English letters.
     *
     * @param words
     * @return
     */

【难点误区】

对于S1: DFS 来说，难点在于：

1. words.length == 1时的edge case，下面的DFS版toplogical sort处理不了这样单独一个单词的case，所以必须单独拿出来处理！！！
2. HashMap建图，不管word里的字母有没有遍历到，都要给他们在map和status里建图，因为没有依赖关系的字母也要放入图里。
3. 拓扑排序插入StringBuilder的顺序要注意，由于我们建图的时候是把先序的作为key，后续的作为val放在List里的，所以DFS纵深遍历下去之后，先append到path上的是后续的那些char，所以最后要正序输出的话，必须做reverse()。

对于S2: BFS 来说，难点在于：

1. 首先要初始化inDegree，把单词里所有unique的char都放入，并将入度初始化为0
2. 面对 s = "ab", t = “a" 这样的case，一旦s的长度比t长并且s从头部开始的substring于t相同，那么肯定是非法的，返回空串""。
3. BFS level order traversal，从外层入度为0的点开始，每剥一层，下面点的入度就 - 1，然后再check下一层 - 1后入度为0的点有哪些，并把它们放入queue里继续遍历下一层直到队列为空。
4. BFS后，要查看是否图里的所有点都进队遍历过了，即确认图里的点是否都加入了res，如果没有则表明图里的某些点并没有收录到res里，返回空串""。

【解题思路】

经典拓扑排序题，2种做法：DFS / BFS。

其中DFS主要依靠模板HashMap建图，HashMap记录status。难点在于建图时要考虑所有的case，无论如何都要把所有的char建图和编入status里。然后对于一些edge case，比如s = "ab", t = “a" 这样的case，一旦s的长度比t长并且s从头部开始的substring于t相同，那么肯定是非法的，返回空串""。剩下的则是通过拓扑模板containsCycle来完成。

而BFS的基本套路也是先初始化所有char给inDegree这个HashMap，初始化所有入度为0，然后开始两两对比建图，把char之间的关系编入map和inDegree，同时注意上面提到的那种edge case。接着通过level order traversal把当前入度为0的点放入queue里，从外而内剥洋葱，每剥掉一层，跟剥掉点的邻接点入度就 - 1，然后看这波操作下来谁的入度为0就进queue，直至queue为空。最后一定要记得check进入res的char的个数是否和图里inDegree中的一样多，只要比所有总数小，就表明有点没进入queue，始终入度不为0，也就是有环的存在，直接返回空串。

```
/**
 * words = ["wrt","wrf","er","ett","rftt"]
 * t < f
 * w < e
 * r < t
 * e < r
 * => wertf
 * 看哪个字母之前没有约束
 * wertf
 * 根据每两个单词之间的字典序关系来推出
 * 剥洋葱 -> 先找入度为0的点 -> 层层去剥
 * DFS, BFS -> 顺序要输出出来 -> BFS更直观一些
 */
```


```java     
// S1: DFS
// time = O(C), space = O(1)
// C: the total length of all the words in the input list, added together
public String alienOrder(String[] words) {
    // corner case
    if (words == null || words.length == 0) return "";
    if (words.length == 1) return words[0]; // 注意这个长度为1的edge case！！！

    HashMap<Character, List<Character>> map = new HashMap<>();
    HashMap<Character, Integer> status = new HashMap<>();
    
    // step 1: build graph
    for (int i = 0; i < words.length - 1; i++) { // O(n)
        String str1 = words[i], str2 = words[i + 1];
        int idx1 = 0, idx2 = 0;
        boolean flag = false;

        while (idx1 < str1.length() && idx2 < str2.length()) { // O(min(k1, k2))
            char c1 = str1.charAt(idx1++), c2 = str2.charAt(idx2++);
            map.putIfAbsent(c1, new ArrayList<>());
            map.putIfAbsent(c2, new ArrayList<>());
            status.putIfAbsent(c1, 0);
            status.putIfAbsent(c2, 0);
            if (c1 != c2) {
                map.get(c1).add(c2);
                flag = true;
                break;
            }
        }

        while (idx1 < str1.length()) { // O(k1)
            if (!flag) return "";
            char c1 = str1.charAt(idx1++);
            map.putIfAbsent(c1, new ArrayList<>());
            status.putIfAbsent(c1, 0);
        }

        while (idx2 < str2.length()) { // O(k2)
            char c2 = str2.charAt(idx2++);
            map.putIfAbsent(c2, new ArrayList<>());
            status.putIfAbsent(c2, 0);
        }
    }
    
    // step 2: iterate the map
    StringBuilder path = new StringBuilder();
    for (char key : map.keySet()) { // O(u) <= O(26)
        if (containsCycle(map, status, key, path)) return ""; // O(V + E)
    }

    return path.reverse().toString();
}
    
// step 3: toplogical sort
private boolean containsCycle(HashMap<Character, List<Character>> map, HashMap<Character, Integer> status, char cur, StringBuilder path) {
    if (status.get(cur) == 2) return false;
    if (status.get(cur) == 1) return true;

    status.put(cur, 1);

    for (char next : map.get(cur)) {
        if (containsCycle(map, status, next, path)) return true;
    }

    status.put(cur, 2);
    path.append(cur);
    return false;
}
```
```java
// S2: BFS
// time = O(C), space = O(1)
public String alienOrder(String[] words) {
    // corner case
    if (words == null || words.length == 0) return "";

    HashMap<Character, HashSet<Character>> map = new HashMap<>();
    HashMap<Character, Integer> inDegree = new HashMap<>();

    // init indegree
    for (String word : words) { // O(C)
        for (char ch : word.toCharArray()) {
            inDegree.putIfAbsent(ch, 0);
        }
    }

    // 建图
    for (int i = 0; i < words.length - 1; i++) { // O(n)
        String s = words[i], t = words[i + 1];
        if (s.length() > t.length() && s.substring(0, t.length()).equals(t)) return "";

        for (int j = 0; j < Math.min(s.length(), t.length()); j++) { // O(min(k1, k2))
            char c1 = s.charAt(j), c2 = t.charAt(j);
            if (c1 == c2) continue;
            if (!map.containsKey(c1) || !map.get(c1).contains(c2)) {
                map.putIfAbsent(c1, new HashSet<>());
                map.get(c1).add(c2);
                inDegree.put(c2, inDegree.getOrDefault(c2, 0) + 1);
            }
            break;
        }
    }

    // 遍历
    Queue<Character> queue = new LinkedList<>();
    for (char key : inDegree.keySet()) { // O(u)
        if (inDegree.get(key) == 0) queue.offer(key);
    }

    StringBuilder res = new StringBuilder();
    while (!queue.isEmpty()) { // O(u)
        char cur = queue.poll(); // 当前弹出来的都是入度为0的点
        res.append(cur);

        if (map.containsKey(cur)) {
            for (char next : map.get(cur)) {
                inDegree.put(next, inDegree.get(next) - 1);
                if (inDegree.get(next) == 0) queue.offer(next); // 下一批要砍掉的节点，不停剥外层的洋葱
            }
        }
    }

    if (res.length() != inDegree.size()) return "";
    return res.toString();
}
```