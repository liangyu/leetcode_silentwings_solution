package LC601_900;

public class LC748_ShortestCompletingWord {
    /**
     * Given a string licensePlate and an array of strings words, find the shortest completing word in words.
     *
     * A completing word is a word that contains all the letters in licensePlate. Ignore numbers and spaces in
     * licensePlate, and treat letters as case insensitive. If a letter appears more than once in licensePlate, then it
     * must appear in the word the same number of times or more.
     *
     * For example, if licensePlate = "aBc 12c", then it contains letters 'a', 'b' (ignoring case), and 'c' twice.
     * Possible completing words are "abccdef", "caaacab", and "cbca".
     *
     * Return the shortest completing word in words. It is guaranteed an answer exists. If there are multiple shortest
     * completing words, return the first one that occurs in words.
     *
     * Input: licensePlate = "1s3 PSt", words = ["step","steps","stripe","stepple"]
     * Output: "steps"
     *
     * Constraints:
     *
     * 1 <= licensePlate.length <= 7
     * licensePlate contains digits, letters (uppercase or lowercase), or space ' '.
     * 1 <= words.length <= 1000
     * 1 <= words[i].length <= 15
     * words[i] consists of lower case English letters.
     * @param licensePlate
     * @param words
     * @return
     */
    // time = O(m + n * k), space = O(1)  k: length of longest word in the words array
    public String shortestCompletingWord(String licensePlate, String[] words) {
        int[] freq = new int[26];
        for (char c : licensePlate.toCharArray()) { // O(m)
            if (Character.isAlphabetic(c)) freq[Character.toLowerCase(c) - 'a']++;
        }

        String res = "";
        int minLen = Integer.MAX_VALUE;
        for (String word : words) { // O(n * k)
            boolean flag = true;
            int[] arr = new int[26];
            for (char c : word.toCharArray()) {
                arr[c - 'a']++;
            }
            for (int i = 0; i < 26; i++) {
                if (freq[i] > 0 && freq[i] > arr[i]) {
                    flag = false;
                    break;
                }
            }
            if (flag && word.length() < minLen) {
                res = word;
                minLen = word.length();
            }
        }
        return res;
    }
}
