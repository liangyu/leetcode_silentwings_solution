package LC2101_2400;
import java.util.*;
public class LC2157_GroupsofStrings {
    /**
     * You are given a 0-indexed array of strings words. Each string consists of lowercase English letters only. No
     * letter occurs more than once in any string of words.
     *
     * Two strings s1 and s2 are said to be connected if the set of letters of s2 can be obtained from the set of
     * letters of s1 by any one of the following operations:
     *
     * Adding exactly one letter to the set of the letters of s1.
     * Deleting exactly one letter from the set of the letters of s1.
     * Replacing exactly one letter from the set of the letters of s1 with any letter, including itself.
     * The array words can be divided into one or more non-intersecting groups. A string belongs to a group if any one
     * of the following is true:
     *
     * It is connected to at least one other string of the group.
     * It is the only string present in the group.
     * Note that the strings in words should be grouped in such a manner that a string belonging to a group cannot be
     * connected to a string present in any other group. It can be proved that such an arrangement is always unique.
     *
     * Return an array ans of size 2 where:
     *
     * ans[0] is the total number of groups words can be divided into, and
     * ans[1] is the size of the largest group.
     *
     * Input: words = ["a","b","ab","cde"]
     * Output: [2,3]
     *
     * Input: words = ["a","ab","abc"]
     * Output: [1,3]
     *
     * Constraints:
     *
     * 1 <= words.length <= 2 * 10^4
     * 1 <= words[i].length <= 26
     * words[i] consists of lowercase English letters only.
     * No letter occurs more than once in words[i].
     * @param words
     * @return
     */
    // S1: bitmaks + union find
    // time = O(26n), space = O(n)
    int[] parent;
    public int[] groupStrings(String[] words) {
        int n = words.length;
        int[] states = new int[n];
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        HashMap<Integer, Integer> map = new HashMap<>(); // encoding -> index
        for (int i = 0; i < n; i++) {
            String word = words[i];
            int state = 0;
            for (int j = 0; j < word.length(); j++) {
                int ch = word.charAt(j) - 'a';
                state |= (1 << ch);
            }
            states[i] = state;
            if (!map.containsKey(state)) map.put(state, i);
            else {
                int k = map.get(state);
                if (findParent(i) != findParent(k)) union(i, k); // 编码相同的单词，一定在同一个家族里了！
            }
        }

        for (int i = 0; i < n; i++) {
            int a = states[i];
            for (int j = 0; j < 26; j++) {
                if (((a >> j) & 1) == 0) continue;
                int b = states[i] - (1 << j);
                if (!map.containsKey(b)) map.put(b, i); // 家族b还未被创建，那么b则是第一人
                else {
                    int k = map.get(b);
                    if (findParent(i) != findParent(k)) union(i, k);
                }
            }
        }

        HashMap<Integer, Integer> group = new HashMap<>();
        int maxSize = 0;
        for (int i = 0; i < n; i++) {
            int p = findParent(i);
            group.put(p, group.getOrDefault(p, 0) + 1);
            maxSize = Math.max(maxSize, group.get(p));
        }
        return new int[]{group.size(), maxSize};
    }

    private int findParent(int x) {
        if (x != parent[x]) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }

    // S2: bitmaks + union find
    // time = O(nlogn), space = O(n)
    class Solution {
        HashMap<Integer, Integer> parent;
        public int[] groupStrings(String[] words) {
            int n = words.length;
            parent = new HashMap<>();

            TreeMap<Integer, HashMap<Integer, Integer>> map = new TreeMap<>();
            for (int i = 0; i < n; i++) {
                String word = words[i];
                int state = 0;
                for (char c : word.toCharArray()) {
                    state |= (1 << (c - 'a'));
                }
                int oneBit = Integer.bitCount(state);
                map.putIfAbsent(oneBit, new HashMap<>());
                HashMap<Integer, Integer> freq = map.get(oneBit);
                freq.put(state, freq.getOrDefault(state, 0) + 1);
                map.put(oneBit, freq);
                parent.put(state, state);
            }

            for (int key : map.keySet()) {
                HashMap<Integer, Integer> freq = map.get(key);
                for (int state : freq.keySet()) {
                    List<Integer> remList = new ArrayList<>();
                    List<Integer> addList = new ArrayList<>();
                    List<Integer> repList = new ArrayList<>();
                    for (int k = 0; k < 26; k++) {
                        if (((state >> k) & 1) == 1) remList.add(state ^ (1 << k));
                        else addList.add(state ^ (1 << k));
                    }
                    for (int x : remList) {
                        for (int y : addList) {
                            int mask = (state ^ x);
                            repList.add(y - mask);
                        }
                    }
                    for (int x : remList) {
                        HashMap<Integer, Integer> tmap = map.getOrDefault(key - 1, new HashMap<>());
                        if (tmap.size() > 0) {
                            if (tmap.containsKey(x)) {
                                if (findParent(state) != findParent(x)) union(state, x);
                            }
                        }
                    }
                    for (int x : addList) {
                        HashMap<Integer, Integer> tmap = map.getOrDefault(key + 1, new HashMap<>());
                        if (tmap.size() > 0) {
                            if (tmap.containsKey(x)) {
                                if (findParent(state) != findParent(x)) union(state, x);
                            }
                        }
                    }

                    for (int x : repList) {
                        if (freq.containsKey(x)) {
                            if (findParent(state) != findParent(x)) union(state, x);
                        }
                    }
                }
            }

            int max = 0;
            TreeMap<Integer, Integer> group = new TreeMap<>();
            for (HashMap<Integer, Integer> freq : map.values()) {
                for (int state : freq.keySet()) {
                    int p = findParent(state);
                    int amount = freq.get(state);
                    group.put(p, group.getOrDefault(p, 0) + amount);
                }
            }
            for (int val : group.values()) {
                max = Math.max(max, val);
            }
            return new int[]{group.size(), max};
        }

        private int findParent(int x) {
            if (x != parent.get(x)) parent.put(x, findParent(parent.get(x)));
            return parent.get(x);
        }

        private void union(int x, int y) {
            x = parent.get(x);
            y = parent.get(y);
            if (x < y) parent.put(y, x);
            else parent.put(x, y);
        }
    }
}
/**
 * 有传递性的connected -> Union Find
 * 本质是个union find的模板题
 * 仔细考察哪些单词是能够connected或者union起来的
 * O(n^2) -> TLE
 * Union(words[i],words[j])
 * 怎么判定union???
 * bitmask，因为集合里只包含字母，只有26个
 * 用二进制数来表示里面有哪些字母存在
 * 任何一个words[i]可以变成一串编码
 * word[i] => A 010101011101001
 * word[j] => B 010101110111101
 * O(n^2) * 26
 * A + 1 => B    * 26
 * A - 1 => B    * 26
 * A -1+1 => B  (26 * 26)
 * set {}
 * O(n * 256)
 * map: encoding -> index
 * words[i] -> A -> B  => union(i, map[B])
 * 优化：
 * words[i] -> A -> (-x) -> B  (<= words[j])
 * words[j] -> C -> (-y) -> B  (<= (-y) C <= words[j])
 * A与C一定可以union在一起 => 把编码A和B union在一起，把B和C union在一起，
 * 这样一旦B相等，本质上A和C一定可以union起来 => O(n * 26)
 */