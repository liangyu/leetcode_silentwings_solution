package LC901_1200;
import java.util.*;
public class LC1152_AnalyzeUserWebsiteVisitPattern {
    /**
     * You are given two string arrays username and website and an integer array timestamp. All the given arrays are of
     * the same length and the tuple [username[i], website[i], timestamp[i]] indicates that the user username[i] visited
     * the website website[i] at time timestamp[i].
     *
     * A pattern is a list of three websites (not necessarily distinct).
     *
     * For example, ["home", "away", "love"], ["leetcode", "love", "leetcode"], and ["luffy", "luffy", "luffy"] are all
     * patterns.
     * The score of a pattern is the number of users that visited all the websites in the pattern in the same order they
     * appeared in the pattern.
     *
     * For example, if the pattern is ["home", "away", "love"], the score is the number of users x such that x visited
     * "home" then visited "away" and visited "love" after that.
     * Similarly, if the pattern is ["leetcode", "love", "leetcode"], the score is the number of users x such that x
     * visited "leetcode" then visited "love" and visited "leetcode" one more time after that.
     * Also, if the pattern is ["luffy", "luffy", "luffy"], the score is the number of users x such that x visited
     * "luffy" three different times at different timestamps.
     * Return the pattern with the largest score. If there is more than one pattern with the same largest score, return
     * the lexicographically smallest such pattern.
     *
     * Input: username = ["joe","joe","joe","james","james","james","james","mary","mary","mary"],
     * timestamp = [1,2,3,4,5,6,7,8,9,10], website = ["home","about","career","home","cart","maps","home","home",
     * "about","career"]
     * Output: ["home","about","career"]
     *
     * Constraints:
     *
     * 3 <= username.length <= 50
     * 1 <= username[i].length <= 10
     * timestamp.length == username.length
     * 1 <= timestamp[i] <= 10^9
     * website.length == username.length
     * 1 <= website[i].length <= 10
     * username[i] and website[i] consist of lowercase English letters.
     * It is guaranteed that there is at least one user who visited at least three websites.
     * All the tuples [username[i], timestamp[i], website[i]] are unique.
     * @param username
     * @param timestamp
     * @param website
     * @return
     */
    // time = O(nlogn + k * l^3), space = O(n * k * l)
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        List<String> res = new ArrayList<>();
        int n = username.length;
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) { // O(n)
            nodes[i] = new Node(username[i], timestamp[i], website[i]);
        }

        Arrays.sort(nodes, (o1, o2) -> o1.time - o2.time); // O(nlogn)
        HashMap<String, List<String>> map = new HashMap<>();
        for (Node node : nodes) { // O(n)
            map.putIfAbsent(node.user, new ArrayList<>());
            map.get(node.user).add(node.web);
        }

        List<HashSet<String>> setList = new ArrayList<>();
        for (List<String> webs : map.values()) findSequence(webs, setList); // O(k * l^3)

        String top = "";
        HashMap<String, Integer> freqMap = new HashMap<>();
        int max = 0;
        for (HashSet<String> set : setList) {  // O(k)
            for (String s : set) {  // O(l)
                freqMap.put(s, freqMap.getOrDefault(s, 0) + 1);
                if (freqMap.get(s) >= max) {
                    if (freqMap.get(s) > max) {
                        top = s;
                        max = freqMap.get(s);
                    } else if (s.compareTo(top) < 0) top = s;
                }
            }
        }
        String[] strs = top.split("#");
        for (String s : strs) res.add(s); // O(1)
        return res;
    }

    private void findSequence(List<String> webs, List<HashSet<String>> setList) { // O(l^3)
        int n = webs.size();
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    String hash = webs.get(i) + "#" + webs.get(j) + "#" + webs.get(k);
                    set.add(hash);
                }
            }
        }
        setList.add(set);
    }

    private class Node {
        private String user;
        private int time;
        private String web;
        public Node(String user, int time, String web) {
            this.user = user;
            this.time = time;
            this.web = web;
        }
    }
}
