package LC2101_2400;

import java.util.HashMap;

public class LC2284_SenderWithLargestWordCount {
    /**
     * You have a chat log of n messages. You are given two string arrays messages and senders where messages[i] is a
     * message sent by senders[i].
     *
     * A message is list of words that are separated by a single space with no leading or trailing spaces. The word
     * count of a sender is the total number of words sent by the sender. Note that a sender may send more than one
     * message.
     *
     * Return the sender with the largest word count. If there is more than one sender with the largest word count,
     * return the one with the lexicographically largest name.
     *
     * Note:
     *
     * Uppercase letters come before lowercase letters in lexicographical order.
     * "Alice" and "alice" are distinct.
     *
     * Input: messages = ["Hello userTwooo","Hi userThree","Wonderful day Alice","Nice day userThree"],
     * senders = ["Alice","userTwo","userThree","Alice"]
     * Output: "Alice"
     *
     * Input: messages = ["How is leetcode for everyone","Leetcode is useful for practice"], senders = ["Bob","Charlie"]
     * Output: "Charlie"
     *
     * Constraints:
     *
     * n == messages.length == senders.length
     * 1 <= n <= 104
     * 1 <= messages[i].length <= 100
     * 1 <= senders[i].length <= 10
     * messages[i] consists of uppercase and lowercase English letters and ' '.
     * All the words in messages[i] are separated by a single space.
     * messages[i] does not have leading or trailing spaces.
     * senders[i] consists of uppercase and lowercase English letters only.
     * @param messages
     * @param senders
     * @return
     */
    // time = O(n * k), space = O(n * k)
    public String largestWordCount(String[] messages, String[] senders) {
        HashMap<String, Integer> map = new HashMap<>();
        int n = senders.length;
        for (int i = 0; i < n; i++) {
            String s = messages[i].trim();
            String[] strs = s.split(" ");
            map.put(senders[i], map.getOrDefault(senders[i], 0) + strs.length);
        }

        int max = 0;
        String res = "";
        for (String key : map.keySet()) {
            if (map.get(key) > max) {
                max = map.get(key);
                res = key;
            } else if (max == map.get(key)) {
                if (res.compareTo(key) < 0) res = key;
            }
        }
        return res;
    }
}
