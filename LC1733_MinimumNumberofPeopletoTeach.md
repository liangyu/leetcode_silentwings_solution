# LC1733 Minimum Number of People to Teach

标签（空格分隔）： LeetCode Java Greedy

---
    /**
     * You are given an integer n, an array languages, and an array friendships where:
     *
     * There are n languages numbered 1 through n,
     * languages[i] is the set of languages the i​​​​​​th​​​​ user knows, and
     * friendships[i] = [u​​​​​​i​​​, v​​​​​​i] denotes a friendship between the users u​​​​​​​​​​​i​​​​​ and vi.
     * You can choose one language and teach it to some users so that all friends can communicate with each other.
     * Return the minimum number of users you need to teach.
     *
     * Note that friendships are not transitive, meaning if x is a friend of y and y is a friend of z,
     * this doesn't guarantee that x is a friend of z.
     *
     * Input: n = 2, languages = [[1],[2],[1,2]], friendships = [[1,2],[1,3],[2,3]]
     * Output: 1
     *
     * Input: n = 3, languages = [[2],[1,3],[1,2],[3]], friendships = [[1,4],[1,2],[3,4],[2,3]]
     * Output: 2
     *
     * Constraints:
     *
     * 2 <= n <= 500
     * languages.length == m
     * 1 <= m <= 500
     * 1 <= languages[i].length <= n
     * 1 <= languages[i][j] <= n
     * 1 <= u​​​​​​i < v​​​​​​i <= languages.length
     * 1 <= friendships.length <= 500
     * All tuples (u​​​​​i, v​​​​​​i) are unique
     * languages[i] contains only unique values
     *
     * @param n
     * @param languages
     * @param friendships
     * @return
     */
     
【难点误区】

本题最大难点是想不到一个有效的解决方案来决定哪个语言能被用来作为教授的那门语言。正因为无法确定，所以可以直观的采用最简单粗暴的方法，遍历每一种语言的可能性，把它们分别作为那门最终教授的语言，看各自需要教授多少人才能达到题目要求，然后在这一堆结果之中找出最小值。

另外要特别注意id是从1开始，遍历的时候idx从0开始，所以建图存值的时候记得要idx + 1来得到id。

【解题思路】

本题的基本思路是遍历每一种language，如果把当前遍历的language当做就是选定的最后要teach的那个语言的话，遍历每一对friendship里的2个人，首先check这2人有没有会说共同的语言，如果有则直接pass，毫无约束，对答案无影响；如果没有的话，那么就看2人会说的语言里是否包括当前设为所教的语言，如果有则无视，没有的话就表明这个人需要被教授这个语言，那么res++。总共有1 ~ n种语言，每种语言都这么来一下，得到n个res，然后求出这n个res的最小值即可。

由于人数总共才m个，所以min可以初始为最大值m，即每个人都要被教授一种新语言，然后因为要在每个人所会说的语言里去check是否会说当前的语言，要O(1)时间最大效率的check，自然就会想到用HashSet，所以一上来我们就可以给每个person和他会说的语言进行mapping建图，显然用HashMap<person_id, HashSet<>>即可。

另外这里要特别当心，所有的person和language的id都是从1开始，所以在loop的时候如果是0 ~ n，那么存id的时候记得要 + 1。


```java     
// time = O(m * n), space = O(m * n)
public int minimumTeachings(int n, int[][] languages, int[][] friendships) {
    int m = languages.length;
    HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
    // step 1: mapping each person's language into that person's hashset
    for (int i = 0; i < m; i++) { // O(m)
        map.put(i + 1, new HashSet<>());
        for (int l : languages[i]) { // O(n)
            map.get(i + 1).add(l);
        }
    }

    // step 2: check if each pair of the friendship shares the same language
    boolean[] share = new boolean[friendships.length]; // O(m)
    for (int j = 1; j <= n; j++) { // O(n)
        for (int i = 0; i < friendships.length; i++) { // O(m)
            if (map.get(friendships[i][0]).contains(j) && map.get(friendships[i][1]).contains(j)) {
                share[i] = true;
            }
        }
    }
    
    // step 3: iterate through each language, set it as the one needs to teach, find how many people should be 
    // taught for each language, then find the minimum among all of them
    int min = m;
    for (int i = 1; i <= n; i++) { // O(n)
        HashSet<Integer> set = new HashSet<>();
        for (int j = 0; j < friendships.length; j++) { // O(m)
            if (share[j]) continue;
            if (!map.get(friendships[j][0]).contains(i)) set.add(friendships[j][0]);
            if (!map.get(friendships[j][1]).contains(i)) set.add(friendships[j][1]);
        }
        min = Math.min(min, set.size());
    }
    return min;
}
```
