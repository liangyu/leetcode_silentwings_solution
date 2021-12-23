package LC1201_1500;
import java.util.*;
public class LC1348_TweetCountsPerFrequency {
    /**
     * A social media company is trying to monitor activity on their site by analyzing the number of tweets that occur
     * in select periods of time. These periods can be partitioned into smaller time chunks based on a certain frequency
     * (every minute, hour, or day).
     *
     * For example, the period [10, 10000] (in seconds) would be partitioned into the following time chunks with these
     * frequencies:
     *
     * Every minute (60-second chunks): [10,69], [70,129], [130,189], ..., [9970,10000]
     * Every hour (3600-second chunks): [10,3609], [3610,7209], [7210,10000]
     * Every day (86400-second chunks): [10,10000]
     * Notice that the last chunk may be shorter than the specified frequency's chunk size and will always end with the
     * end time of the period (10000 in the above example).
     *
     * Design and implement an API to help the company with their analysis.
     *
     * Implement the TweetCounts class:
     *
     * TweetCounts() Initializes the TweetCounts object.
     * void recordTweet(String tweetName, int time) Stores the tweetName at the recorded time (in seconds).
     * List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) Returns a
     * list of integers representing the number of tweets with tweetName in each time chunk for the given period of time
     * [startTime, endTime] (in seconds) and frequency freq.
     * freq is one of "minute", "hour", or "day" representing a frequency of every minute, hour, or day respectively.
     *
     * Input
     * ["TweetCounts","recordTweet","recordTweet","recordTweet","getTweetCountsPerFrequency","getTweetCountsPerFrequency",
     * "recordTweet","getTweetCountsPerFrequency"]
     * [[],["tweet3",0],["tweet3",60],["tweet3",10],["minute","tweet3",0,59],["minute","tweet3",0,60],["tweet3",120],
     * ["hour","tweet3",0,210]]
     *
     * Output
     * [null,null,null,null,[2],[2,1],null,[4]]
     *
     * Constraints:
     *
     * 0 <= time, startTime, endTime <= 10^9
     * 0 <= endTime - startTime <= 10^4
     * There will be at most 10^4 calls in total to recordTweet and getTweetCountsPerFrequency.
     */
    HashMap<String, TreeMap<Integer, Integer>> map;
    public LC1348_TweetCountsPerFrequency() {
        map = new HashMap<>();
    }

    // time = O(logn), space = O(n)
    public void recordTweet(String tweetName, int time) {
        map.putIfAbsent(tweetName, new TreeMap<>());
        TreeMap<Integer, Integer> tmap = map.get(tweetName);
        tmap.put(time, tmap.getOrDefault(time, 0) + 1);
        map.put(tweetName, tmap);
    }

    // time = O(logn), space = O(n)
    public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) {
        List<Integer> res = new ArrayList<>();
        if (!map.containsKey(tweetName)) return res;
        TreeMap<Integer, Integer> tmap = map.get(tweetName);
        int t = 0;
        if (freq.equals("minute")) t = 59;
        else if (freq.equals("hour")) t = 3599;
        else t = 86399;

        int i = startTime, j = Math.min(i + t, endTime);
        while (i <= endTime) {
            int count = tmap.containsKey(i) ? tmap.get(i) : 0;
            while (true) {
                Integer hk = tmap.higherKey(i);
                if (hk == null || hk > j) break;
                i = hk;
                count += tmap.get(i);
            }
            res.add(count);
            i = j + 1;
            j = Math.min(i + t, endTime);
        }
        return res;
    }
}
