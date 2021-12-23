package LC601_900;
import java.util.*;
public class LC642_DesignSearchAutocompleteSystem {
    /**
     * Design a search autocomplete system for a search engine. Users may input a sentence (at least one word and end
     * with a special character '#').
     *
     * You are given a string array sentences and an integer array times both of length n where sentences[i] is a
     * previously typed sentence and times[i] is the corresponding number of times the sentence was typed. For each
     * input character except '#', return the top 3 historical hot sentences that have the same prefix as the part of
     * the sentence already typed.
     *
     * Here are the specific rules:
     *
     * The hot degree for a sentence is defined as the number of times a user typed the exactly same sentence before.
     * The returned top 3 hot sentences should be sorted by hot degree (The first is the hottest one). If several
     * sentences have the same hot degree, use ASCII-code order (smaller one appears first).
     * If less than 3 hot sentences exist, return as many as you can.
     * When the input is a special character, it means the sentence ends, and in this case, you need to return an empty
     * list.
     * Implement the AutocompleteSystem class:
     *
     * AutocompleteSystem(String[] sentences, int[] times) Initializes the object with the sentences and times arrays.
     * List<String> input(char c) This indicates that the user typed the character c.
     * Returns an empty array [] if c == '#' and stores the inputted sentence in the system.
     * Returns the top 3 historical hot sentences that have the same prefix as the part of the sentence already typed.
     * If there are fewer than 3 matches, return them all.
     *
     * Input
     * ["AutocompleteSystem", "input", "input", "input", "input"]
     * [[["i love you", "island", "iroman", "i love leetcode"], [5, 3, 2, 2]], ["i"], [" "], ["a"], ["#"]]
     * Output
     * [null, ["i love you", "island", "i love leetcode"], ["i love you", "i love leetcode"], [], []]
     *
     * Constraints:
     *
     * n == sentences.length
     * n == times.length
     * 1 <= n <= 100
     * 1 <= sentences[i].length <= 100
     * 1 <= times[i] <= 50
     * c is a lowercase English letter, a hash '#', or space ' '.
     * Each tested sentence will be a sequence of characters c that end with the character '#'.
     * Each tested sentence will have a length in the range [1, 200].
     * The words in each input sentence are separated by single spaces.
     * At most 5000 calls will be made to input.
     * @param sentences
     * @param times
     */
    // time = O(nlogn), space = O(n)
    HashMap<String, Integer> map;
    StringBuilder sb;
    public LC642_DesignSearchAutocompleteSystem(String[] sentences, int[] times) {
        map = new HashMap<>();
        sb = new StringBuilder();
        int n = sentences.length;
        for (int i = 0; i < n; i++) map.put(sentences[i], times[i]);
    }

    public List<String> input(char c) {
        List<String> res = new LinkedList<>();
        if (c == '#') {
            String data = sb.toString();
            map.put(data, map.getOrDefault(data, 0) + 1);
            sb = new StringBuilder();
            return res;
        }

        sb.append(c);
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>((o1, o2) -> o1.freq != o2.freq ? o1.freq - o2.freq : o2.word.compareTo(o1.word));
        for (String x : map.keySet()) {
            if (match(sb.toString(), x)) {
                pq.offer(new Pair(x, map.get(x)));
                if (pq.size() > 3) pq.poll();
            }
        }

        while (!pq.isEmpty()) res.add(0, pq.poll().word);
        return res;
    }

    private boolean match(String a, String b) {
        for (int i = 0; i < a.length(); i++) {
            if (i >= b.length() || a.charAt(i) != b.charAt(i)) return false;
        }
        return true;
    }

    private class Pair {
        private String word;
        private int freq;
        public Pair(String name, int freq) {
            this.word = name;
            this.freq =freq;
        }
    }
}
